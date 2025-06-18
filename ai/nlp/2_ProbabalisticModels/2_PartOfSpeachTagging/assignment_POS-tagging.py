'''
POS taggin is assigning a Part Of Speech to a given work. Tagging is difficult because some words can represent more than one part of speech at different times. They are Ambiguous. Let's look at the following example:

The whole team played well. [adverb]
You are doing well for yourself. [adjective]
Well, this assignment took me forever to complete. [interjection]
The well is dry. [noun]
Tears were beginning to well in her eyes. [verb]

Distinguishing the parts-of-speech of a word in a sentence will help you better understand the meaning of a sentence. This would be critically important in search queries. Identifying the proper noun, the organization, the stock symbol, or anything similar would greatly improve everything ranging from speech recognition to search. By completing this assignment, you will:

Learn how parts-of-speech tagging works
Compute the transition matrix A in a Hidden Markov Model
Compute the emission matrix B in a Hidden Markov Model
Compute the Viterbi algorithm
Compute the accuracy of your own model
'''

# Importing packages and loading in the data set 
from utils_pos import get_word_tag, preprocess  
import pandas as pd
from collections import defaultdict
import math
import numpy as np
import w2_unittest


'''
One data set (WSJ-2_21.pos) will be used for training. it wil create matrix y
The other (WSJ-24.pos) for testing.
The tagged training data has been preprocessed to form a vocabulary (hmm_vocab.txt).
The words in the vocabulary are words from the training set that were used two or more times.
The vocabulary is augmented with a set of 'unknown word tokens', described below.

The training set will be used to create the emission, transition and tag counts.
The test set has also been preprocessed to remove the tags to form test_words.txt.
This is read in and further processed to identify the end of sentences and handle words not in the vocabulary using functions provided in utils_pos.py.
This forms the list prep, the preprocessed text used to test our POS taggers.
A POS tagger will necessarily encounter words that are not in its datasets.

To improve accuracy, these words are further analyzed during preprocessing to extract available hints as to their appropriate tag.
For example, the suffix 'ize' is a hint that the word is a verb, as in 'final-ize' or 'character-ize'.
A set of unknown-tokens, such as '--unk-verb--' or '--unk-noun--' will replace the unknown words in both the training and test corpus and will appear in the emission, transition and tag data structures.

Implementation note:

For python 3.6 and beyond, dictionaries retain the insertion order.
Furthermore, their hash-based lookup makes them suitable for rapid membership tests.
If di is a dictionary, key in di will return True if di has a key _key_, else False.
The dictionary vocab will utilize these features.
'''

# load in the training corpus
with open("./data/WSJ_02-21.pos", 'r') as f:
    training_corpus = f.readlines()

print(f"A few items of the training corpus list")
print(training_corpus[0:5])

# read the vocabulary data, split by each line of text, and save the list
with open("./data/hmm_vocab.txt", 'r') as f:
    voc_l = f.read().split('\n')

print("A few items of the vocabulary list")  # just words
print(voc_l[0:50])
print()
print("A few items at the end of the vocabulary list")
print(voc_l[-50:])

# vocab: dictionary that has the index of the corresponding words
vocab = {}

# Get the index of the corresponding words. 
for i, word in enumerate(sorted(voc_l)): 
    vocab[word] = i       
    
print("Vocabulary dictionary, key is the word, value is a unique integer")
cnt = 0
for k,v in vocab.items():
    print(f"{k}:{v}")
    cnt += 1
    if cnt > 20:
        break

# load in the test corpus
with open("./data/WSJ_24.pos", 'r') as f:
    y = f.readlines()
    
print("A sample of the test corpus")
print(y[0:10])

#corpus without tags, preprocessed
_, prep = preprocess(vocab, "./data/test.words")     

print('The length of the preprocessed test corpus: ', len(prep))
print('This is a sample of the test_corpus: ')
print(prep[0:10])

# we now do some prep work before calculating POS tags
#   Calc 
#        - transmission count:  number of times each tag happened next to another tag.  (Prob of t given t-1)
#                to get this we create  a dict of transition counts of key = (prevTag, tag), value - count
#        - emission count:  probability of a word given its tag.
#                to get this we calc a dict of key (tag, word) and val count
#        - tag counts
# In POS tags we atart at the most simple (unambiguous words ike is (verb) which accounts for 86% of words and builf from there)
#

# UNQ_C1 GRADED FUNCTION: create_dictionaries
def create_dictionaries(training_corpus, vocab, verbose=True):
    """
    Input: 
        training_corpus: a corpus where each line has a word followed by its tag.
        vocab: a dictionary where keys are words in vocabulary and value is an index
    Output: 
        emission_counts: a dictionary where the keys are (tag, word) and the values are the counts
        transition_counts: a dictionary where the keys are (prev_tag, tag) and the values are the counts
        tag_counts: a dictionary where the keys are the tags and the values are the counts
    """
    
    # initialize the dictionaries using defaultdict
    emission_counts = defaultdict(int)
    transition_counts = defaultdict(int)
    tag_counts = defaultdict(int)
    
    # Initialize "prev_tag" (previous tag) with the start state, denoted by '--s--'
    prev_tag = '--s--' 
    
    # use 'i' to track the line number in the corpus
    i = 0 
    
    # Each item in the training corpus contains a word and its POS tag
    # Go through each word and its tag in the training corpus
    for word_tag in training_corpus:
        
        # Increment the word_tag count
        i += 1
        
        # Every 50,000 words, print the word count
        if i % 50000 == 0 and verbose:
            print(f"word count = {i}")
            
        ### START CODE HERE ###
        # get the word and tag using the get_word_tag helper function (imported from utils_pos.py)
        # the function is defined as: get_word_tag(line, vocab)
        word, tag = get_word_tag(word_tag, vocab)
        
        # Increment the transition count for the previous word and tag
        transition_counts[(prev_tag, tag)] += 1
        
        # Increment the emission count for the tag and word
        emission_counts[(tag, word)] += 1

        # Increment the tag count
        tag_counts[tag] += 1

        # Set the previous tag to this tag (for the next iteration of the loop)
        prev_tag = tag
        
        ### END CODE HERE ###
        
    return emission_counts, transition_counts, tag_counts

emission_counts, transition_counts, tag_counts = create_dictionaries(training_corpus, vocab)

# get all the POS states
states = sorted(tag_counts.keys())
print(f"Number of POS tags (number of 'states'): {len(states)}")
print("View these POS tags (states)")
print(states)

# Test your function
w2_unittest.test_create_dictionaries(create_dictionaries, training_corpus, vocab)

## next

print("transition examples: ")
for ex in list(transition_counts.items())[:10]:
    print(ex)
print()

print("emission examples: ")
for ex in list(emission_counts.items())[0:10]:
    print (ex)
print()

print("ambiguous word example: ")# e.g. well, or back, or can
for tup,cnt in emission_counts.items():
    if tup[1] == 'back': print (tup, cnt) 

# Now test our data against the test data, note y = the original tagged test corpus
# UNQ_C2 GRADED FUNCTION: predict_pos
def predict_pos(prep, y, emission_counts, vocab, states):
    '''
    Input: 
        prep: a preprocessed version of 'y'. A list with the 'word' component of the tuples.
        y: a corpus composed of a list of tuples where each tuple consists of (word, POS) (The correct values )
        emission_counts: a dictionary where the keys are (tag,word) tuples and the value is the count
        vocab: a dictionary where keys are words in vocabulary and value is an index
        states: a sorted list of all possible tags for this assignment
    Output: 
        accuracy: Number of times you classified a word correctly
    '''
    
    # Initialize the number of correct predictions to zero
    num_correct = 0
    
    # Get the (tag, word) tuples, stored as a set
    all_words = set(emission_counts.keys())
    
    # Initialize total count to 0 
    total = 0
    for word, y_tup in zip(prep, y): 

        # Split the (word, POS) string into a list of two items
        y_tup_l = y_tup.split()
        
        # Verify that y_tup contain both word and POS
        if len(y_tup_l) == 2:
            
            # Set the true POS label for this word
            true_label = y_tup_l[1]

        else:
            # If the y_tup didn't contain word and POS, go to next word
            continue
    
        count_final = 0
        pos_final = ''
        
        # If the word is in the vocabulary...
        if word in vocab:
            for pos in states:

            ### START CODE HERE (Replace instances of 'None' with your code) ###
            
                # define the key as the tuple containing the POS and word
                key = (pos, word)

                # check if the (pos, word) key exists in the emission_counts dictionary
                if key in emission_counts: # Replace None in this line with the proper condition.

                # get the emission count of the (pos,word) tuple 
                    count = emission_counts.get(key)

                    # keep track of the POS with the largest count
                    if count>count_final: # Replace None in this line with the proper condition.

                        # update the final count (largest count)
                        count_final = count

                        # update the final POS
                        pos_final = key[0]

            # If the final POS (with the largest count) matches the true POS:
            if pos_final == true_label: # Replace None in this line with the proper condition.
                # Update the number of correct predictions
                num_correct += 1
        # Keep track of the total number of examples (that have valid labels)
        total += 1        
    ### END CODE HERE ###
    accuracy = num_correct / total
    
    return accuracy


accuracy_predict_pos = predict_pos(prep, y, emission_counts, vocab, states)
print(f"Accuracy of prediction using predict_pos is {accuracy_predict_pos:.4f}")

# Expected Accuracy of prediction using predict_pos is 0.9253, 92.5% is really good for this warm up exercise. 
#mWith hidden markov models, you should be able to get 95% accuracy.

# Test your function
w2_unittest.test_predict_pos(predict_pos, prep, y, emission_counts, vocab, states)


# 2 - Hidden Markov Models HMM for POS
# Now you will build something more context specific. Concretely, you will be implementing a Hidden Markov Model (HMM) with a Viterbi decoder
# The HMM is one of the most commonly used algorithms in Natural Language Processing, and is a foundation to many deep learning techniques you will see in this specialization.
# In addition to parts-of-speech tagging, HMM is used in speech recognition, speech synthesis, etc.

'''
The Markov Model contains a number of states and the probability of transition between those states.

In this case, the states are the parts-of-speech.
A Markov Model utilizes a transition matrix, A.
A Hidden Markov Model adds an observation or emission matrix B which describes the probability of a visible observation when we are in a particular state.
In this case, the emissions are the words in the corpus
The state, which is hidden, is the POS tag of that word.
'''

'''
Now that you have your emission_counts, transition_counts, and tag_counts, you will start implementing the Hidden Markov Model.

This will allow you to quickly construct the

A transition probabilities matrix.
and the B emission probabilities matrix.
You will also use some smoothing when computing these matrices.
'''
# UNQ_C3 GRADED FUNCTION: create_transition_matrix
def create_transition_matrix(alpha, tag_counts, transition_counts):
    ''' 
    Input: 
        alpha: number used for smoothing
        tag_counts: a dictionary mapping each tag to its respective count
        transition_counts: a dictionary where the keys are (prev_tag, tag) and the values are the counts
    Output:
        A: matrix of dimension (num_tags,num_tags)
    '''
    # Get a sorted list of unique POS tags
    all_tags = sorted(tag_counts.keys())
    
    # Count the number of unique POS tags
    num_tags = len(all_tags)
    
    # Initialize the transition matrix 'A'
    A = np.zeros((num_tags,num_tags))
    
    # Get the unique transition tuples (previous POS, current POS)
    trans_keys = set(transition_counts.keys())
    
    ### START CODE HERE ### 
    
    # Go through each row of the transition matrix A
    for i in range(num_tags):
        
        # Go through each column of the transition matrix A
        for j in range(num_tags):

            # Initialize the count of the (prev POS, current POS) to zero
            count = 0
        
            # Define the tuple (prev POS, current POS)
            # Get the tag at position i and tag at position j (from the all_tags list)
            key = (all_tags[i], all_tags[j]) # tuple of form (tag,tag)

            # Check if the (prev POS, current POS) tuple 
            # exists in the transition counts dictionary
            if key in transition_counts: # Replace None in this line with the proper condition.
                
                # Get count from the transition_counts dictionary 
                # for the (prev POS, current POS) tuple
                count = transition_counts.get(key)

            # Get the count of the previous tag (index position i) from tag_counts
            count_prev_tag = tag_counts.get(all_tags[i])
            
            # Apply smoothing using count of the tuple, alpha, 
            # count of previous tag, alpha, and total number of tags
            A[i,j] = (count+alpha)/(count_prev_tag+(alpha*num_tags))

    ### END CODE HERE ###
    return A

alpha = 0.001
A = create_transition_matrix(alpha, tag_counts, transition_counts)
# Testing your function
print(f"A at row 0, col 0: {A[0,0]:.9f}")
print(f"A at row 3, col 1: {A[3,1]:.4f}")

print("View a subset of transition matrix A")
A_sub = pd.DataFrame(A[30:35,30:35], index=states[30:35], columns = states[30:35] )
print(A_sub)

# Test your function
w2_unittest.test_create_transition_matrix(create_transition_matrix, tag_counts, transition_counts)

'''
Generaye emission matrix
'''
# UNQ_C4 GRADED FUNCTION: create_emission_matrix

def create_emission_matrix(alpha, tag_counts, emission_counts, vocab):
    '''
    Input: 
        alpha: tuning parameter used in smoothing 
        tag_counts: a dictionary mapping each tag to its respective count
        emission_counts: a dictionary where the keys are (tag, word) and the values are the counts
        vocab: a dictionary where keys are words in vocabulary and value is an index.
               within the function it'll be treated as a list
    Output:
        B: a matrix of dimension (num_tags, len(vocab))
    '''
    
    # get the number of POS tag
    num_tags = len(tag_counts)
    
    # Get a list of all POS tags
    all_tags = sorted(tag_counts.keys())
    
    # Get the total number of unique words in the vocabulary
    num_words = len(vocab)
    
    # Initialize the emission matrix B with places for
    # tags in the rows and words in the columns
    B = np.zeros((num_tags, num_words))
    
    # Get a set of all (POS, word) tuples 
    # from the keys of the emission_counts dictionary
    emis_keys = set(list(emission_counts.keys()))
    
    ### START CODE HERE (Replace instances of 'None' with your code) ###
    
    # Go through each row (POS tags)
    for i in range(num_tags): # Replace None in this line with the proper range.
        
        # Go through each column (words)
        for j in range(num_words): # Replace None in this line with the proper range.

            # Initialize the emission count for the (POS tag, word) to zero
            count = 0 
                    
            # Define the (POS tag, word) tuple for this row and column
            key = (all_tags[i], vocab[j]) # tuple of form (tag,word)

            # check if the (POS tag, word) tuple exists as a key in emission counts
            if key in emis_keys: # Replace None in this line with the proper condition.
        
                # Get the count of (POS tag, word) from the emission_counts d
                count = emission_counts[key]
                
            # Get the count of the POS tag
            count_tag = tag_counts[all_tags[i]]
                
            # Apply smoothing and store the smoothed value 
            # into the emission matrix B for this row and column
            B[i,j] = (count+alpha)/(count_tag   + alpha * num_words)

    ### END CODE HERE ###
    return B

# creating your emission probability matrix. this takes a few minutes to run. 
alpha = 0.001
B = create_emission_matrix(alpha, tag_counts, emission_counts, list(vocab))

print(f"View Matrix position at row 0, column 0: {B[0,0]:.9f}")
print(f"View Matrix position at row 3, column 1: {B[3,1]:.9f}")

# Try viewing emissions for a few words in a sample dataframe
cidx  = ['725','adroitly','engineers', 'promoted', 'synergy']

# Get the integer ID for each word
cols = [vocab[a] for a in cidx]

# Choose POS tags to show in a sample dataframe
rvals =['CD','NN','NNS', 'VB','RB','RP']

# For each POS tag, get the row number from the 'states' list
rows = [states.index(a) for a in rvals]

# Get the emissions for the sample of words, and the sample of POS tags
B_sub = pd.DataFrame(B[np.ix_(rows,cols)], index=rvals, columns = cidx )
print(B_sub)

# Test your function
w2_unittest.test_create_emission_matrix(create_emission_matrix, tag_counts, emission_counts, list(vocab))