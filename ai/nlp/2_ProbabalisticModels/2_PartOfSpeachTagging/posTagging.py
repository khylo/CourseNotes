import numpy as np
import pandas as pd

'''
Toy example using only 3 parts of speech. In real world examples can use 36 types e.g
https://www.ling.upenn.edu/courses/Fall_2003/ling001/penn_treebank_pos.html
'''
# Define tags for Adverb, Noun and To (the preposition) , respectively
tags = ['RB', 'NN', 'TO']

'''
In this week's assignment you will construct some dictionaries that provide useful information of the tags and words you will be working with.

One of these dictionaries is the transition_counts which counts the number of times a particular tag happened next to another. The keys of this dictionary have the form (previous_tag, tag) and the values are the frequency of occurrences.

Another one is the emission_counts dictionary which will count the number of times a particular pair of (tag, word) appeared in the training dataset.

In general think of transition when working with tags only and of emission when working with tags and words.
'''
# Define 'transition_counts' dictionary.. Note this is a dict of transitions, but could be stored in matrix(below)
# Note: values are the same as the ones in the assignment
transition_counts = {
    ('NN', 'NN'): 16241,
    ('RB', 'RB'): 2263,
    ('TO', 'TO'): 2,
    ('NN', 'TO'): 5256,
    ('RB', 'TO'): 855,
    ('TO', 'NN'): 734,
    ('NN', 'RB'): 2431,
    ('RB', 'NN'): 358,
    ('TO', 'RB'): 200
}

# Store the number of tags in the 'num_tags' variable
num_tags = len(tags)

# Initialize a 3X3 numpy array with zeros
transition_matrix = np.zeros((num_tags, num_tags))

# Print matrix
transition_matrix

# Print shape of the matrix (3x3)
transition_matrix.shape

# Create sorted version of the tag's list
sorted_tags = sorted(tags)

# Print sorted list
sorted_tags

'''
Convert to matrix, using keys to access dict
'''
# Loop rows
for i in range(num_tags):
    # Loop columns
    for j in range(num_tags):
        # Define tag pair
        tag_tuple = (sorted_tags[i], sorted_tags[j])
        # Get frequency from transition_counts dict and assign to (i, j) position in the matrix
        transition_matrix[i, j] = transition_counts.get(tag_tuple)

# Print matrix
transition_matrix

'''
For this you can use a Pandas DataFrame. In particular, a function that takes the matrix as input and prints out a pretty version of it will be very useful:
'''
# Define 'print_matrix' function
def print_matrix(matrix):
    print(pd.DataFrame(matrix, index=sorted_tags, columns=sorted_tags))

# Print the 'transition_matrix' by calling the 'print_matrix' function
print_matrix(transition_matrix)

'''
Numpy examples
'''
# Scale transition matrix
transition_matrix = transition_matrix/10

# Print scaled matrix
print_matrix(transition_matrix)

'''
Another trickier example is to normalize each row so that each value is equal to  𝑣𝑎𝑙𝑢𝑒𝑠𝑢𝑚𝑜𝑓𝑟𝑜𝑤


This can be easily done with vectorization. First you will compute the sum of each row:

Notice that the `sum()` method was used. This method does exactly what its name implies. Since the sum of the 
rows was desired the axis was set to `1`. In Numpy `axis=1` refers to the columns so the sum is done by summing 
each column of a particular row, for each row. 

Also the `keepdims` parameter was set to `True` so the resulting array had shape `(3, 1)` rather than `(3,)`. 
This was done so that the axes were consistent with the desired operation. 

When working with Numpy, always remember to check the shape of the arrays you are working with, many unexpected 
errors happen because of axes not being consistent. The `shape` attribute is your friend for these cases.
'''

# Compute sum of row for each row
rows_sum = transition_matrix.sum(axis=1, keepdims=True) #axis=1  == fix columns, sum over row

# Print sum of rows
rows_sum

# Normalize transition matrix
transition_matrix = transition_matrix / rows_sum

# Print normalized matrix
print_matrix(transition_matrix)

transition_matrix.sum(axis=1, keepdims=True)

import math

# Copy transition matrix for for-loop example
t_matrix_for = np.copy(transition_matrix)

# Copy transition matrix for numpy functions example
t_matrix_np = np.copy(transition_matrix)

# Loop values in the diagonal
for i in range(num_tags):
    t_matrix_for[i, i] =  t_matrix_for[i, i] + math.log(rows_sum[i])

# Print matrix
print_matrix(t_matrix_for)

# Save diagonal in a numpy array
d = np.diag(t_matrix_np)

# Print shape of diagonal
d.shape

# Reshape diagonal numpy array
d = np.reshape(d, (3,1))

# Print shape of diagonal
d.shape

# Perform the vectorized operation
d = d + np.vectorize(math.log)(rows_sum)

# Use numpy's 'fill_diagonal' function to update the diagonal
np.fill_diagonal(t_matrix_np, d)

# Print the matrix
print_matrix(t_matrix_np)

# Check for equality
t_matrix_for == t_matrix_np

