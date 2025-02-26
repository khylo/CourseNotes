import numpy as np                     # Import numpy for array manipulation
import matplotlib.pyplot as plt        # Import matplotlib for charts
from utils_nb import plot_vectors      # Function to plot vectors (arrows)

# Create a 2 x 2 matrix
R = np.array([[-2, 0],
              [0, 2]])
R

x = np.array([[1, 1]]) # Create a row vector as a NumPy array with a single row
x
print(f"shape(x)={np.shape(x)}") # shape(x)=(1, 2)

# Note this would work without Trnaspose using a vecor instead of a full matric
x2 = np.array([1, 1])
print(f"shape(x2)={np.shape(x2)}") # shape(x2)=(2,)

y = np.dot(R, x.T) # Apply the dot product between R and x.T
y                  # Column vector as a NumPy array with a single column

'''
We are going to use Pyplot to visually inspect the effect of the rotation on 2D vectors. For that, we have created a function plot_vectors() that takes care of all the intricate parts of the visual formatting. The code for this function is inside the utils_nb.py file.
'''
plot_vectors([x], axes=[4, 4], fname='transform_x.svg')


# Below transforms x to a new vector Y (in blue,  almost perpendicular and bigger)
plot_vectors([x, y], axes=[4, 4], fname='transformx_and_y.svg')

'''
Rotation
simarish effects, but norm (length is not changed as this is pure rotation)
'''
angle = 100 * (np.pi / 180) # Convert degrees to radians

Ro = np.array([[np.cos(angle), -np.sin(angle)],
              [np.sin(angle), np.cos(angle)]])

x2 = np.array([[2, 2]])    # Row vector as a NumPy array
y2 = np.dot(Ro, x2.T)

print('Rotation matrix')
print(Ro)
print('\nRotated vector')
print(y2)

print('\n x2 norm', np.linalg.norm(x2))
print('\n y2 norm', np.linalg.norm(y2))
print('\n Rotation matrix norm', np.linalg.norm(Ro))

plot_vectors([x2, y2], fname='transform_02.svg')

'''
Frobenius Norm
'''
A = np.array([[2, 2],
              [2, 2]])
# np.square() is a way to square each element of a matrix. Its outcome is equivalent to that of using the * operator with numpy arrays.
A_squared = np.square(A)
A_squared

A_squared = A * A
A_squared

A_Frobenius = np.sqrt(np.sum(A_squared))
A_Frobenius

# or shorter
print('Frobenius norm of the Rotation matrix')
print(np.sqrt(np.sum(Ro * Ro)), '== ', np.linalg.norm(Ro))