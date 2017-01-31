from __future__ import division

# import sys

import itertools
# from jellyfish import jaro_distance, jaro_winkler, levenshtein_distance, damerau_levenshtein_distance
# from tabulate import tabulate
from fuzzywuzzy import fuzz


def lcs(sequence1, sequence2):
    m = len(sequence1)
    n = len(sequence2)
    # An (m+1) times (n+1) matrix
    lsc_matrix = [[0] * (n + 1) for _ in range(m + 1)]
    for i in range(1, m+1):
        for j in range(1, n+1):
            if sequence1[i-1] == sequence2[j-1]:
                lsc_matrix[i][j] = lsc_matrix[i-1][j-1] + 1
            else:
                lsc_matrix[i][j] = max(lsc_matrix[i][j-1], lsc_matrix[i-1][j])
    return lsc_matrix


def back_track(lcs_matrix, sequence1, sequence2, i, j):
    if i == 0 or j == 0:
        return ""
    elif sequence1[i-1] == sequence2[j-1]:
        return back_track(lcs_matrix, sequence1, sequence2, i - 1, j - 1) + sequence1[i - 1]
    else:
        if lcs_matrix[i][j-1] > lcs_matrix[i-1][j]:
            return back_track(lcs_matrix, sequence1, sequence2, i, j - 1)
        else:
            return back_track(lcs_matrix, sequence1, sequence2, i - 1, j)


def prepare_string(s):
    s = s.replace(',', '').strip()
    s = sorted(s.split())
    s = ' '.join(s)
    return s


def lcs_distance(string1, string2):
    """

    :param string1: str
    :param string2: str
    :return: float
    """

    string1 = prepare_string(string1)
    string2 = prepare_string(string2)

    # if len(string1.split()) < len(string2.split()):
    #     string1, string2 = string2, string1

    # sys.exit(0)

    subsequence = back_track(lcs(string1, string2), string1, string2, len(string1), len(string2))
    # print subsequence
    # distance = len(subsequence) / max(len(string1), len(string2))
    distance = max(len(string1), len(string2)) - len(subsequence)
    # print '\t', string1, '-', string2, distance
    return distance


def name_variants(string):
    variants = [string]
    original_sequence = string.split()
    r = range(len(original_sequence))
    for indices in itertools.chain(*[itertools.combinations(r, n) for n in r[1:]]):
        s = original_sequence[:]
        for i in indices:
            s[i] = s[i][0]
        variants.append(' '.join(s))
    return variants


def average_item_length(sequence):
    return sum(len(i) for i in sequence) / len(sequence)


def fuzzy_distance(string1, string2, partial=False):
    if partial:
        distance_func = fuzz.partial_token_set_ratio
    else:
        distance_func = fuzz.token_set_ratio
    distance = 1 - distance_func(string1, string2) / 100

    # min_average_word_length = min(average_item_length(string1.split()), average_item_length(string2.split()))
    # max_average_word_length = max(average_item_length(string1.split()), average_item_length(string2.split()))
    # 7 is average name length
    # distance *= 5.5 / min_average_word_length
    return distance


if __name__ == '__main__':
    s1 = u'Sergey Kenterbutian Demurin'
    s2 = u'K D Sergey '

    # print get_name_variants(s1)

    # distances = [
    #     # ['Jaro', jaro_distance],
    #     ['Jaro-Winkler', jaro_winkler],
    #     # ['Levenstein', levenshtein_distance],
    #     ['Damerau-Levenstein', damerau_levenshtein_distance],
    #     ['Fuzzy distance', fuzzy_distance]
    # ]
    # table = [[name, distance(s1, s2)] for (name, distance) in distances]
    #
    # print "{} <-> {}".format(s1, s2)
    # print tabulate(table)

    variants = name_variants(s1)

    table = [["{} <-> {}".format(s1, s2), fuzzy_distance(s1, s2), fuzzy_distance(s1, s2, partial=True)]
             for (s1, s2)
             in itertools.combinations(variants, 2)]
    table = sorted(table, key=lambda r: r[1])
    print tabulate(table, headers=['string', 'not partial', 'partial'])
