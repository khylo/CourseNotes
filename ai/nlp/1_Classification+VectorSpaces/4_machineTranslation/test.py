import numpy as np

P = np.array([1,1])
V1 = np.array([1,1])
V2 = np.array([2,2])
V3 = np.array([-1,1])

print('P.V1 = ', P.dot(V1))
print('P.V2 = ', P.dot(V2))
print('P.V3 = ', P.dot(V3))

''' 
The projection of a vector onto a basis is the sum of the projections of the vector onto each basis vector.
'''

import numpy as np                # library for array and matrix manipulation
import pprint                     # utilities for console printing 
from utils_nb import plot_vectors # helper function to plot vectors
import matplotlib.pyplot as plt   # visualization library

pp = pprint.PrettyPrinter(indent=4) # Instantiate a pretty printer

def basic_hash_table(value_l, n_buckets):
    
    def hash_function(value, n_buckets): # mod #buckets
        return int(value) % n_buckets
    
    hash_table = {i:[] for i in range(n_buckets)} # Initialize all the buckets in the hash table as empty lists

    for value in value_l:
        hash_value = hash_function(value,n_buckets) # Get the hash key for the given value
        hash_table[hash_value].append(value) # Add the element to the corresponding bucket
    
    return hash_table

value_l = [100, 10, 14, 17, 97] # Set of values to hash
hash_table_example = basic_hash_table(value_l, n_buckets=10)
pp.pprint(hash_table_example)


P = np.array([[1, 1]]) # Define a single plane. 
#fig, ax1 = plt.subplots(figsize=(8, 8)) # Create a plot

#plot_vectors([P], axes=[2, 2], ax=ax1) # Plot the plane P as a vector

# Plot  random points. 
for i in range(0, 10):
        v1 = np.array(np.random.uniform(-2, 2, 2)) # Get a pair of random numbers between -2 and 2
        side_of_plane = np.sign(np.dot(P, v1.T)) 
        
        # Color the points depending on the sign of the result of np.dot(P, point.T)
        if side_of_plane == 1:
            print(f"i={i} sifeOfPLane=true")
        else:
            print(f"i={i} sifeOfPLane=false")
            #ax1.plot([v1[0]], [v1[1]], 'ro') # Plot red points

#plt.show()

