
HASTIE_NAIVEBAYES: from the Hastie_10_2.csv file obtained by the procedure described in
 https://scikit-learn.org/stable/modules/generated/sklearn.datasets.make_hastie_10_2.html, used both as test and training,
 obtains a success rate in the training of 88% and 84% in the test. These rates are slightly below that obtained by other procedures (see reference at the end), but they can be accepted, taking into account that a large number of the records do not meet the condition:
y [i] = 1 if np.sum (X [i] ** 2)> 9.34 else -1
that is, there are erroneous data in the training.


Resources: Java 8

Functioning:

Hastie_NaiveBayes.jar and Hastie_NaiveBayes.bat are downloaded to any directory. The test file Hastie10_2.csv is downloaded to the C: directory.

It runs: Hastie_NaiveBayes.bat

The training and test file margins go as parameters, initially they are from register 0 to 9600 for training and from 9601 to 12000 for test, but they can be changed.

You can also use a test file that has the same structure as Hastie10_2.csv, in which the class goes in the first position of the record.

On screen it offers the results and produces a file c: HastieTestWithClassAsigned.txt with the classes assigned to the test file.

Cite this software as:

** Alfonso Blanco García ** HASTIE_NAIVEBAYES


References:

https://scikit-learn.org/stable/modules/generated/sklearn.datasets.make_hastie_10_2.html

Comparison:

Implementation of AdaBoost classifier

https://github.com/jaimeps/adaboost-implementation