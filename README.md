# HASTIE_NAIVEBAYES
HASTIE_NAIVEBAYES: from the Hastie_10_2.csv file obtained by the procedure described in  https://scikit-learn.org/stable/modules/generated/sklearn.datasets.make_hastie_10_2.html, used both as test and training,  obtains a success rate in the training of 88% and 84% in the test. These rates are slightly below that obtained by other procedures (see reference at the end), but they can be accepted, taking into account that a large number of the records do not meet the condition: y [i] = 1 if np.sum (X [i] ** 2)> 9.34 else -1 that is, there are erroneous data in the training.
