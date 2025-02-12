# Naive bayes
Naive bayes is an algorithm that could be used for sentiment analysis. It takes a short time to train and also has a short prediction time.

The first part of training a naive bayes classifier is to identify the number of classes that you have.
You will create a probability for each class.  𝑃(𝐷𝑝𝑜𝑠)
  is the probability that the document is positive.  𝑃(𝐷𝑛𝑒𝑔)
  is the probability that the document is negative. Use the formulas as follows and store the values in a dictionary:
𝑃(𝐷𝑝𝑜𝑠)=𝐷𝑝𝑜𝑠𝐷(1)

𝑃(𝐷𝑛𝑒𝑔)=𝐷𝑛𝑒𝑔𝐷(2)

Where  𝐷
  is the total number of documents, or tweets in this case,  𝐷𝑝𝑜𝑠
  is the total number of positive tweets and  𝐷𝑛𝑒𝑔
  is the total number of negative tweets.

  Prior and Logprior
The prior probability represents the underlying probability in the target population that a tweet is positive versus negative. In other words, if we had no specific information and blindly picked a tweet out of the population set, what is the probability that it will be positive versus that it will be negative? That is the "prior".

The prior is the ratio of the probabilities  𝑃(𝐷𝑝𝑜𝑠)𝑃(𝐷𝑛𝑒𝑔)
 . We can take the log of the prior to rescale it, and we'll call this the logprior

logprior=𝑙𝑜𝑔(𝑃(𝐷𝑝𝑜𝑠)𝑃(𝐷𝑛𝑒𝑔))=𝑙𝑜𝑔(𝐷𝑝𝑜𝑠𝐷𝑛𝑒𝑔)
 
.

Note that  𝑙𝑜𝑔(𝐴𝐵)
  is the same as  𝑙𝑜𝑔(𝐴)−𝑙𝑜𝑔(𝐵)
 . So the logprior can also be calculated as the difference between two logs:

logprior=log(𝑃(𝐷𝑝𝑜𝑠))−log(𝑃(𝐷𝑛𝑒𝑔))=log(𝐷𝑝𝑜𝑠)−log(𝐷𝑛𝑒𝑔)(3)

Positive and Negative Probability of a Word
To compute the positive probability and the negative probability for a specific word in the vocabulary, we'll use the following inputs:

# 3 - Test your Naive Bayes
Now that we have the logprior and loglikelihood, we can test the naive bayes function by making predicting on some tweets!


Exercise 3 - naive_bayes_predict
Implement naive_bayes_predict.

Instructions: Implement the naive_bayes_predict function to make predictions on tweets.

The function takes in the tweet, logprior, loglikelihood.
It returns the probability that the tweet belongs to the positive or negative class.
For each tweet, sum up loglikelihoods of each word in the tweet.
Also add the logprior to this sum to get the predicted sentiment of that tweet.
𝑝=𝑙𝑜𝑔𝑝𝑟𝑖𝑜𝑟+∑𝑖𝑁(𝑙𝑜𝑔𝑙𝑖𝑘𝑒𝑙𝑖ℎ𝑜𝑜𝑑𝑖)

𝑓𝑟𝑒𝑞𝑝𝑜𝑠
 and 𝑓𝑟𝑒𝑞𝑛𝑒𝑔
 are the frequencies of that specific word in the positive or negative class. In other words, the positive frequency of a word is the number of times the word is counted with the label of 1.
𝑁𝑝𝑜𝑠
 and 𝑁𝑛𝑒𝑔
 are the total number of positive and negative words for all documents (for all tweets), respectively.
𝑉
 is the number of unique words in the entire set of documents, for all classes, whether positive or negative.
We'll use these to compute the positive and negative probability for a specific word using this formula:

𝑃(𝑊𝑝𝑜𝑠)=𝑓𝑟𝑒𝑞𝑝𝑜𝑠+1𝑁𝑝𝑜𝑠+𝑉(4)
𝑃(𝑊𝑛𝑒𝑔)=𝑓𝑟𝑒𝑞𝑛𝑒𝑔+1𝑁𝑛𝑒𝑔+𝑉(5)

Notice that we add the "+1" in the numerator for additive smoothing. This wiki article explains more about additive smoothing.

Log likelihood
To compute the loglikelihood of that very same word, we can implement the following equations:

loglikelihood=log(𝑃(𝑊𝑝𝑜𝑠)𝑃(𝑊𝑛𝑒𝑔))(6)
`

# 4 - Filter words by Ratio of Positive to Negative Counts
Some words have more positive counts than others, and can be considered "more positive". Likewise, some words can be considered more negative than others.
One way for us to define the level of positiveness or negativeness, without calculating the log likelihood, is to compare the positive to negative frequency of the word.
Note that we can also use the log likelihood calculations to compare relative positivity or negativity of words.
We can calculate the ratio of positive to negative frequencies of a word.
Once we're able to calculate these ratios, we can also filter a subset of words that have a minimum ratio of positivity / negativity or higher.
Similarly, we can also filter a subset of words that have a maximum ratio of positivity / negativity or lower (words that are at least as negative, or even more negative than a given threshold).

Exercise 5 - get_ratio
Implement get_ratio.

Given the freqs dictionary of words and a particular word, use lookup(freqs,word,1) to get the positive count of the word.
Similarly, use the lookup function to get the negative count of that word.
Calculate the ratio of positive divided by negative counts
𝑟𝑎𝑡𝑖𝑜=pos_words+1neg_words+1
 

Where pos_words and neg_words correspond to the frequency of the words in their respective classes.

# Sample question
Suppose that in your dataset, 25% of the positive tweets contain the word ‘happy’. You also know that a total of 13% of the tweets in your dataset contain the word 'happy', and that 40% of the total number of tweets are positive. You observe the tweet: ''happy to learn NLP'. What is the probability that this tweet is positive? (Please, round your answer up to two decimal places. Remember that 0.578 = 0.58 and 0.572 = 0.57)

P happy = 0.13
P happy |Pos = 0.25x
P pos = 0.4
P n = .6

given a sentence with happy in it what i th eprobablity that it is poitive#

We wnt P pos| happy using bayes
    A = Positive, B = contains happy
    Formula
    P a|b = P(b|a) * P a / P b
       = P happy | pos * P happy / P pos
       = 0.25 * 0.4 / 0.13 = .77