# Vector Spaces
For vector spaces can plot graph of word mappings e.g. use of data (x) / film (y). FRomt his it should give clear areas to differentiate different topics.. e.g. machine learning would be heavy on data and light on film. Whereas entertainment would be the opposite.

## Word by Word Design
Assume that you are trying to come up with a vector that will represent a certain word.  One possible design would be to create a matrix where each row and column corresponds to a word in your vocabulary. Then you can iterate over a document and see the number of times each word shows up next each other word. You can keep track of the number in the matrix. 
You can think of K as the bandwidth that decides whether two words are next to each other or not. 
I like simple data
I prefer imple raw data
                                                        simple raw like I
here the vector of 'data' for words k=2 words away is      2    1   1   0

## Word by Document Design
You can now apply the same concept and map words to documents. The rows could correspond to words and the columns to documents. The numbers in the matrix correspond to the number of times each word showed up in the document. 

# Euclidean distance

np.linalg.norm(v1-v2)

# Cosine similarity
can be better to compare angles between points on plane instead of distace, especially if number of elements differs

the norm of a vector is defined as:                                                                            
∥⃗v∥ = sqrt (  ∑v^2)

Dot prod
v.w =  ∑ v.w
​   
cos ang = np.dot(a, b) / (np.linalg.norm(a) * np.linalg.norm(b))


# Principal Component Analys PCA
Map mulitple dimensions down

Principal component analysis is an unsupervised learning algorithm which can be used to reduce the dimension of your data. As a result, it allows you to visualize your data. It tries to combine variances across features. Here is a concrete example of PCA: 

From wiki:
PCA is defined as an orthogonal linear transformation on a real inner product space that transforms the data to a new coordinate system such that the greatest variance by some scalar projection of the data comes to lie on the first coordinate (called the first principal component), the second greatest variance on the second coordinate, and so on.

So basically tranform dataset to use custom x / y corords that maximise information

use eigan vectors (unorrelated features of data) and eigan values (the amount of inf retained by each feature)

## Steps to Compute PCA: 

* Mean normalize your data
* Compute the covariance matrix
* Compute SVD on your covariance matrix. This returns 

[USV]=svd(Σ). The three matrices U, S, V are drawn in course notes. U is labelled with eigenvectors, and S is labelled with eigenvalues. 

You can then use the first n columns of vector 
U, to get your new data by multiplying 
XU[:,0:n].

Eigenvector: the resulting vectors, also known as the uncorrelated features of your data

Eigenvalue: the amount of information retained by each new feature. You can think of it as the variance in the eigenvector. 

Also each eigenvalue has a corresponding eigenvector. The eigenvalue tells you how much variance there is in the eigenvector. Here are the steps required to compute PCA: 

ALternative 
PCA is based on the Singular Value Decomposition (SVD) of the Covariance Matrix of the original dataset. The Eigenvectors of such decomposition are used as a rotation matrix. The Eigenvectors are arranged in the rotation matrix in decreasing order according to its explained variance. This last term is related to the EigenValues of the SVD.

# Extra maths identities
Counterclockwise Rotation
If you want to rotate a vector 

r with coordinates (x,y) and angle α counterclockwise over an angle β to get vector 
r’ with coordinates (x’,y’) then the following holds:

x=r∗cos(α)
y=r∗sin(α)

x’=r’∗cos(α+β)
y’=r’∗sin(α+β)

* Trigonometric addition gives us:
cos(α+β)=cos(α)cos(β)−sin(α)sin(β)
sin(α+β)=cos(α)sin(β)+sin(α)cos(β)

For proof, see this 
Wikipedia page section

See alos https://www.coursera.org/learn/classification-vector-spaces-in-nlp/supplement/fwEUM/the-rotation-matrix-optional-reading