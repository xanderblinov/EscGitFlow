import sqlite3
import logging

import scipy.cluster.hierarchy as hierarchy_clustering
import numpy as np
from jellyfish import jaro_distance


logging.basicConfig(level=logging.INFO)


def cluster_authors_hac(authors):
    """
    The idea and the code from http://codereview.stackexchange.com/a/37059/99441
    and https://joernhees.de/blog/2015/08/26/scipy-hierarchical-clustering-and-dendrogram-tutorial/
    :type authors: list[sqlite3.Row]
    :return: list[list[sqlite3.Row]]
    """
    def d(coord):
        i, j = coord
        return 1 - jaro_distance(authors[i]['surname'], authors[j]['surname'])

    triu_distances = np.apply_along_axis(d, 0, np.triu_indices(len(authors), 1))
    Z = hierarchy_clustering.linkage(triu_distances)
    max_d = 0.1
    clusters = hierarchy_clustering.fcluster(Z, max_d, criterion='distance')
    np_authors = np.array(authors)
    clustered = [np_authors[clusters == i] for i in xrange(1, max(clusters) + 1)]
    return clustered


def get_primitive_authors(cursor):
    """

    :type cursor: sqlite3.Cursor
    :return: list
    """
    cursor.execute('SELECT * FROM primitive_author')
    primitive_authors = cursor.fetchall()
    return primitive_authors


def main():
    db_name = 'Database/src/main/resources/test.db'
    conn = sqlite3.connect(db_name)
    conn.row_factory = sqlite3.Row
    cursor = conn.cursor()
    primitive_authors = get_primitive_authors(cursor)

    clusters = cluster_authors_hac(primitive_authors)
    logging.info('Found %d clusters' % len(clusters))
    # for c in clusters:
    #     print c, '\n'

if __name__ == '__main__':
    main()
