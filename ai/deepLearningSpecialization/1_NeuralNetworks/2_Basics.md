
Example given of  Binary classification.
Is there a cat or not?
Input is 64 x 64 pixels  so matrix is 64 * 64 * 3 (RGB) = 12288

nx = 12288

(x,y) =>   (element of R (size of nx), y = {0, 1} 1 if cat 0 if not



m = training example , sometime mtrain, or mtest


so we have Input matrix X = m columns of nx rows

X =   m cols each of nx.. representing one col per example 

X.shape = (nx, m)   (cols, rows))

Y = [ y1, y2 ... ym]]     Y.shape = (1,m)

# Logistic Regression
Output is either 0 or 1

Given x want  y^ (y hat) = Probability that y=1 given x..

y^ = P(y=1|x)  (probability that y is 1 given x)

We use sigmod function

y^ = sigmoid(w.T x + b)   (b is bias (just a R number), w is weight vector)

We have to caculate w and b through training

in this training we keep w and b as seperate (spme other trinaing uses b as the zeroth param)

# Cost function
We want to minimize the cost function where 

Logistic regression cost function
y^ =   sigmoid()


cost = -1/m * sum( y log(y^) + (1-y)log(1-y^))  (cross entropy cost function)

In python ..
def sigmoid(z):
    s = 1/(1+np.exp(-z))
    return s

Relu 
def relu(z):
    s = np.maximum(0,z)
    return s

# Gradient descent
We want to minimize the cost function by changing w and b
dw = 1/m * X (y^ - y).T
db = 1/m * sum(y^ - y)
w = w - alpha * dw
b = b - alpha * db
alpha is learning rate (hyperparameter)

