import logging
import random
import sqlite3
import time

from sklearn.cluster import DBSCAN
import scipy.cluster.hierarchy as hierarchy_clustering
from scipy.spatial import distance
import numpy as np
from sacred import Experiment

from distances import fuzzy_distance


logger = logging.getLogger(__name__)
logger.handlers = []
ch = logging.StreamHandler()
formatter = logging.Formatter('[%(levelname)-7s] %(name)s >> "%(message)s"')
ch.setFormatter(formatter)
logger.addHandler(ch)
logger.setLevel('DEBUG')

ex = Experiment('experiment')
ex.logger = logger


@ex.config
def base_config():
    db_path = 'pubmed.db'
    n_of_authors = 5 * 10 ** 2


def group_authors_hca(authors, distance=fuzzy_distance):
    """
    The idea and the code from http://codereview.stackexchange.com/a/37059/99441
    and https://joernhees.de/blog/2015/08/26/scipy-hierarchical-clustering-and-dendrogram-tutorial/

    :type authors: list[sqlite3.Row]
    :type distance: callable
    :rtype: list[list[sqlite3.Row]]
    """
    def distance_func(coord):
        i, j = coord
        return distance(authors[i]['surname'], authors[j]['surname'])

    triu_distances = np.apply_along_axis(distance_func, 0, np.triu_indices(len(authors), 1))
    Z = hierarchy_clustering.linkage(triu_distances)
    max_d = 0.1
    clusters = hierarchy_clustering.fcluster(Z, max_d, criterion='distance')
    np_authors = np.array(authors)
    clustered = [np_authors[clusters == i] for i in range(1, max(clusters) + 1)]
    return clustered


def dbscan(items, distance_matrix, eps=0.1, min_samples=1):
    if len(items) == 1:
        return [items]

    db = DBSCAN(metric='precomputed', eps=eps, min_samples=min_samples)
    labels = db.fit_predict(distance_matrix)

    n_clusters_ = len(set(labels)) - (1 if -1 in labels else 0)
    np_authors = np.array(items)
    clusters = [np_authors[labels == i] for i in range(n_clusters_)]
    return clusters


def get_primitive_authors(cursor, limit=1000):
    """

    :type cursor: sqlite3.Cursor
    :type limit: int
    :return: list
    """
    cursor.execute('SELECT * FROM primitive_author LIMIT %d' % limit)
    primitive_authors = cursor.fetchall()
    return primitive_authors


def average_name_length(authors):
    lengths = []
    for author in authors:
        full_name = author['surname']
        full_name = full_name.replace(',', '')
        lengths += [len(item) for item in full_name.split() if len(item) > 1]
    return sum(lengths) / len(lengths)


def calculate_distance(data, distance_func):
    def metric(a, b):
        a = int(a[0])
        b = int(b[0])
        return distance_func(data[a], data[b])

    indices = np.arange(len(data)).reshape(-1, 1)
    distance_vector = distance.pdist(indices, metric)
    distance_matrix = distance.squareform(distance_vector)
    return distance_matrix


@ex.command
def test_custom_distance():
    data = [
        'sergey demurin', 'sergey ivanov', 'sergey ivonov',
        'ivanov sergey', 'sergei demurin', 'dmitry medvedev',
        'sergei ivanof'
    ]
    logger.debug('Calculating distance matrix for %d items...' % len(data))
    tic = time.time()
    distance_matrix = calculate_distance(data, fuzzy_distance)
    logger.debug('Calculated distance matrix in %f s'
                 % (time.time() - tic))
    clusters = dbscan(data, distance_matrix)
    print(clusters)


@ex.automain
def main(db_path):
    conn = sqlite3.connect(db_path)
    conn.row_factory = sqlite3.Row
    cursor = conn.cursor()

    n_authors = 1030
    primitive_authors = get_primitive_authors(cursor, limit=n_authors)

    full_names = [row['surname'] for row in primitive_authors]

    logger.debug('Calculating distance matrix for %d items...' % len(full_names))
    tic = time.time()
    distance_matrix = calculate_distance(full_names, fuzzy_distance)
    logger.debug('Calculated distance matrix in %f s'
                 % (time.time() - tic))

    for n_items in (2**k for k in range(1, 10)):
    # for n_items in [10]:
        items_indices = random.sample(range(len(primitive_authors)), n_items)

        ixgrid = np.ix_(items_indices, items_indices)
        local_distance_matrix = distance_matrix[ixgrid]

        tic = time.time()
        clusters = dbscan(items_indices, local_distance_matrix)
        working_time = (time.time() - tic) * 1000
        logger.info("%4d authors: %f ms" % (n_items, working_time))
