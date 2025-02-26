# Intro
USe vector spaces to translate

We used word embeddings in section 3. USing these same values we can train a model so that 

XR ~ Y    where X is the word embeddings for our english corpus, and Y is our target lang.

R is the translation matrix.. which we train by minimizing the diff 

Note that 
X corresponds to the matrix of english word vectors and 
Y corresponds to the matrix of french word vectors. 
R is the mapping matrix.

## Steps required to learn
* Initialize R 
* For loop
Loss=∥XR−Y∥  (frobenius norm)
    np.linalg.norm()  is the quick way :)
                   
g = d/dR (Loss)
differentiate over loss to get rate of change
​
R=R−α∗g     where α is the learning rate

Here is an example to show you how the frobenius norm works.

## Transforming vectors
There are three main vector transformations:

Scaling
Translation
Rotation

## K-nearest neighbors
After you have computed the output of 
XR  you get a vector (Y). 
However Y will probably not match a read French word exactly.  You then need to find the most similar vectors to your output. 

### Hash tables and hash functions
In the example they shoudl a simple hashing function that takes the modulo of a number divided by 10. THis will divide the number space into 10 buckets

For K nearest neighbour we will need a different operation to has based on distance

### Locality sensetive hashing
* Choose a plane 
* Get the normal vector of it P
* Get dot product of vector V to test with P
* This will tell you what side of the plane V is on above the plane (+) , 0 (on the plane), below the plane(-)
From the notes:
Given some point located at (1,1) and three vectors 
V1 = np.array([1,2])
V2 = np.array([-2,-1])
V3 = np.array([-1,1])

(−2,−1) you will see what happens when we take the dot product. First note that the dashed line is our plane. The vector with point P=(1,1) is perpendicular to that line (plane). Now any vector above the dashed line that is multiplied by (1,1) would have a positive number. Any vector below the dashed line when dotted with (1,1) will have a negative number. Any vector on the dashed line multiplied by (1,1) will give you a dot product of 0.  

Once we have the dot products we say 1 for positive, 0 for all else and then calculate the hash


hash=2^0×h1 + 2^1×h2 + 2^2×h3 ...
​
