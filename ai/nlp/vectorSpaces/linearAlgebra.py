import numpy as np  # The swiss knife of the data scientist.
'''
Note diff between np array and std python array
'''
alist = [1, 2, 3, 4, 5]   # Define a python list. It looks like an np array
narray = np.array([1, 2, 3, 4]) # Define a numpy array
print(alist)
print(narray)

print(type(alist))
print(type(narray))

'''
+ operator diff
'''
print(narray + narray) # = 2,4,6,8
print(alist + alist) # = 1,2,3,4,5,1,2,3,4,5

'''
Element wise multiplication
'''
print(narray * 3) # = 3,6,9,12
print(alist * 3) # = 1,2,3,4,5,1,2,3,4,5,1,2,3,4,5

'''
Matrix manipulation
''' 
npmatrix1 = np.array([narray, narray, narray]) # Matrix initialized with NumPy arrays
npmatrix2 = np.array([alist, alist, alist]) # Matrix initialized with lists
npmatrix3 = np.array([narray, [1, 1, 1, 1], narray]) # Matrix initialized with both types

print(npmatrix1)
print(npmatrix2)
print(npmatrix3)

# Example 1:
okmatrix = np.array([[1, 2], [3, 4]]) # Define a 2x2 matrix
print(okmatrix) # Print okmatrix
print(okmatrix * 2) # Print a scaled version of okmatrix

# Example 2:
#badmatrix = np.array([[1, 2], [3, 4], [5, 6, 7]]) # Define a matrix. Note the third row contains 3 elements
#print(badmatrix) # Print the malformed matrix made up of pyhton lists
#print(badmatrix * 2) # It is supposed to scale the whole matrix but instead acts like python array appending

# Scale by 2 and translate 1 unit the matrix
result = okmatrix * 2 + 1 # For each element in the matrix, multiply by 2 and add 1
print(result)

# Add two compatible matrices
result1 = okmatrix + okmatrix
print(result1)

# Subtract two compatible matrices. This is called the difference vector
result2 = okmatrix - okmatrix
print(result2)

'''
The product operator `*` when used on arrays or matrices indicates element-wise multiplications.
Do not confuse it with the dot product.
'''
result = okmatrix * okmatrix # Multiply each element by itself
print(result) # = [[1 4][9 16]]
print(okmatrix.dot(okmatrix))  # = [[7 10][15 22]]

'''
The sum operator `+` when used on arrays or matrices indicates element-wise additions.
Do not confuse it with the dot product.
'''
result = okmatrix + okmatrix # Add each element by itself
print(result) # = [[2 4][6 8]]
print(okmatrix + okmatrix) # = [[2 4][6 8]]
'''
transpose (flip on diagonal)
'''
matrix3x2 = np.array([[1, 2], [3, 4], [5, 6]]) # Define a 3x2 matrix
print('Original matrix 3 x 2')
print(matrix3x2)
print('Transposed matrix 2 x 3')
print(matrix3x2.T)

'''
However, note that the transpose operation does not affect 1D arrays.
'''
nparray = np.array([1, 2, 3, 4]) # Define an array
print('Original array')
print(nparray)
print('Transposed array')
print(nparray.T) # same as original

'''
correct way
'''
nparray = np.array([[1, 2, 3, 4]]) # Define a 1 x 4 matrix. Note the 2 level of square brackets
print('Original array')
print(nparray)
print('Transposed array')
print(nparray.T)   

'''
In linear algebra, the norm of an n-dimensional vector  𝑎⃗ 
  is defined as:

sqrt(SUM(squares of list))  # like generalized pythagoras theorem
'''
nparray1 = np.array([1, 2, 3, 4]) # Define an array
norm1 = np.linalg.norm(nparray1)

nparray2 = np.array([[1, 2], [3, 4]]) # Define a 2 x 2 matrix. Note the 2 level of square brackets
norm2 = np.linalg.norm(nparray2) 

print(norm1)
print(norm2) # both same = 5.477
'''
Can get norm by rows or columns 
axis=0 means get the norm of each column
axis=1 means get the norm of each row.
'''
nparray2 = np.array([[1, 1], [2, 2], [3, 3]]) # Define a 3 x 2 matrix. 

normByCols = np.linalg.norm(nparray2, axis=0) # Get the norm for each column. Returns 2 elements
normByRows = np.linalg.norm(nparray2, axis=1) # get the norm for each row. Returns 3 elements

print(normByCols)
print(normByRows)
'''
Dot mnay ways.. We strongly recommend using np.dot, since it is the only method that accepts arrays and lists without problems
'''
nparray1 = np.array([0, 1, 2, 3]) # Define an array
nparray2 = np.array([4, 5, 6, 7]) # Define an array

flavor1 = np.dot(nparray1, nparray2) # Recommended way
print(flavor1)

flavor2 = np.sum(nparray1 * nparray2) # Ok way
print(flavor2)

flavor3 = nparray1 @ nparray2         # Geeks way
print(flavor3)

# As you never should do:             # Noobs way
flavor4 = 0
for a, b in zip(nparray1, nparray2):
    flavor4 += a * b
    
print(flavor4)

#
norm1 = np.dot(np.array([1, 2]), np.array([3, 4])) # Dot product on nparrays
norm2 = np.dot([1, 2], [3, 4]) # Dot product on python lists

print(norm1, '=', norm2 )#Finally, note that the norm is the square root of the dot product of the vector with itself. That gives many options to w
'''
Sum by row and col
'''
nparray2 = np.array([[1, -1], [2, -2], [3, -3]]) # Define a 3 x 2 matrix. 

sumByCols = np.sum(nparray2, axis=0) # Get the sum for each column. Returns 2 elements
sumByRows = np.sum(nparray2, axis=1) # get the sum for each row. Returns 3 elements

print('Sum by columns: ')
print(sumByCols)
print('Sum by rows:')
print(sumByRows)
'''
Mean
'''
nparray2 = np.array([[1, -1], [2, -2], [3, -3]]) # Define a 3 x 2 matrix. Chosen to be a matrix with 0 mean

mean = np.mean(nparray2) # Get the mean for the whole matrix
meanByCols = np.mean(nparray2, axis=0) # Get the mean for each column. Returns 2 elements
meanByRows = np.mean(nparray2, axis=1) # get the mean for each row. Returns 3 elements

print('Matrix mean: ')
print(mean)
print('Mean by columns: ')
print(meanByCols)
print('Mean by rows:')
print(meanByRows)

'''
center the columns
Centering the attributes of a data matrix is another essential preprocessing step. Centering a matrix means to remove the column mean to each element inside the column. The mean by columns of a centered matrix is always 0.
'''
nparray2 = np.array([[1, 1], [2, 2], [3, 3]]) # Define a 3 x 2 matrix. 

nparrayCentered = nparray2 - np.mean(nparray2, axis=0) # Remove the mean for each column

print('Original matrix')
print(nparray2)
print('Centered by columns matrix')
print(nparrayCentered)

print('New mean by column')
print(nparrayCentered.mean(axis=0))
'''
**Warning:** This process does not apply for row centering. In such cases, consider transposing the matrix, centering by columns, and then transpose back the result. 
'''
nparray2 = np.array([[1, 3], [2, 4], [3, 5]]) # Define a 3 x 2 matrix. 

nparrayCentered = nparray2.T - np.mean(nparray2, axis=1) # Remove the mean for each row
nparrayCentered = nparrayCentered.T # Transpose back the result

print('Original matrix')
print(nparray2)
print('Centered by rows matrix')
print(nparrayCentered)

print('New mean by rows')
print(nparrayCentered.mean(axis=1))
'''
some operations can use static fns
'''
nparray2 = np.array([[1, 3], [2, 4], [3, 5]]) # Define a 3 x 2 matrix. 

mean1 = np.mean(nparray2) # Static way
mean2 = nparray2.mean()   # Dynamic way

print(mean1, ' == ', mean2)
'''
euclidiian distance
'''
nparray1 = np.array([1, 6 , 8])
nparray2 = np.array([0, 4, 6])
nparray2 = nparray2.astype('float') # Change type to allow subtractions
s = np.sqrt(np.sum((nparray1 - nparray2)**2)) # Euclidian distance
s2 = np.linalg.norm(nparray1 - nparray2) # Method 2
print (s)
print(s2)

'''
cosine similarity
'''
nparray1 = np.array([1, 0, -1]) # Define an array
nparray2 = np.array([2,8,1]) # Define an array
cosSimilarity = np.dot(nparray1, nparray2) / (np.linalg.norm(nparray1) * np.linalg.norm(nparray2)) # Cosine similarity
print ("cosine SIm = ", cosSimilarity)