import math
import random
import numpy as np
import pandas as pd
import nltk
nltk.download('punkt')

import w3_unittest
nltk.data.path.append('.')

'''
NOte must run the follwoing to import punkt library from ntlk
import ntlk
ntlk.download()
# THen select d (download) and then select "punkt_tab"
'''

with open("./data/en_US.twitter.txt", "r") as f:
    data = f.read()
print("Data type:", type(data))
print("Number of letters:", len(data))
print("First 300 letters of the data")
print("-------")
# disply only available in Jupyter environemnts
# display(data[0:300])
print("-------")

print("Last 300 letters of the data")
print("-------")
# disply only available in Jupyter environemnts
#display(data[-300:])
print("-------")

# UNIT TEST COMMENT: Candidate for Table Driven Tests 
### UNQ_C1 GRADED_FUNCTION: split_to_sentences ###
def split_to_sentences(data):
    """
    Split data by linebreak "\n"
    
    Args:
        data: str   
    Returns:
        A list of sentences
    """
    ### START CODE HERE ###
    sentences = data.split("\n")
    ### END CODE HERE ###
    
    # Additional clearning (This part is already implemented)
    # - Remove leading and trailing spaces from each sentence
    # - Drop sentences if they are empty strings.
    sentences = [s.strip() for s in sentences]
    sentences = [s for s in sentences if len(s) > 0]
    
    return sentences    

    # test your code
x = """
I have a pen.\nI have an apple. \nAh\nApple pen.\n
"""
print(x)

split_to_sentences(x)

# Test your function
w3_unittest.test_split_to_sentences(split_to_sentences)

'''
Exercise 2 - tokenize_sentences
The next step is to tokenize sentences (split a sentence into a list of words).

Convert all tokens into lower case so that words which are capitalized (for example, at the start of a sentence) in the original text are treated the same as the lowercase versions of the words.
Append each tokenized list of words into a list of tokenized sentences.
Please use nltk.word_tokenize to split sentences into tokens.
If you used str.split instead of nltk.word_tokenize, there are additional edge cases to handle, such as the punctuation (comma, period) that follows a word.
'''
# UNIT TEST COMMENT: Candidate for Table Driven Tests 
### UNQ_C2 GRADED_FUNCTION: tokenize_sentences ###
def tokenize_sentences(sentences):
    """
    Tokenize sentences into tokens (words)
    
    Args:
        sentences: List of strings
    
    Returns:
        List of lists of tokens
    """
    
    # Initialize the list of lists of tokenized sentences
    tokenized_sentences = []
    ### START CODE HERE ###
    
    # Go through each sentence
    for sentence in sentences: # complete this line
        
        # Convert to lowercase letters
        sentence = sentence.lower()
        
        # Convert into a list of words
        tokenized = nltk.word_tokenize(sentence)
        
        # append the list of words to the list of lists
        tokenized_sentences.append(tokenized)
    
    ### END CODE HERE ###
    
    return tokenized_sentences

# test your code
sentences = ["Sky is blue.", "Leaves are green.", "Roses are red."]
tokenize_sentences(sentences)

# Test your function
w3_unittest.test_tokenize_sentences(tokenize_sentences)

# UNIT TEST COMMENT: Candidate for Table Driven Tests 
### UNQ_C3 GRADED_FUNCTION: get_tokenized_data ###
def get_tokenized_data(data):
    """
    Make a list of tokenized sentences
    
    Args:
        data: String
    
    Returns:
        List of lists of tokens
    """
    ### START CODE HERE ###
    
    # Get the sentences by splitting up the data
    sentences = split_to_sentences(data)
    
    # Get the list of lists of tokens by tokenizing the sentences
    tokenized_sentences = tokenize_sentences(sentences)
    
    ### END CODE HERE ###
    
    return tokenized_sentences

# test your function
x = "Sky is blue.\nLeaves are green\nRoses are red."
get_tokenized_data(x)

# Test your function
w3_unittest.test_get_tokenized_data(get_tokenized_data)

'''
Split into train and test sets
'''
tokenized_data = get_tokenized_data(data)
random.seed(87)
random.shuffle(tokenized_data)

train_size = int(len(tokenized_data) * 0.8)
train_data = tokenized_data[0:train_size]
test_data = tokenized_data[train_size:]

print("{} data are split into {} train and {} test set".format(
    len(tokenized_data), len(train_data), len(test_data)))

print("First training sample:")
print(train_data[0])
      
print("First test sample")
print(test_data[0])
'''
Exercise 4 - count_words
You won't use all the tokens (words) appearing in the data for training. Instead, you will use the more frequently used words.

You will focus on the words that appear at least N times in the data.
First count how many times each word appears in the data.
You will need a double for-loop, one for sentences and the other for tokens within a sentence.
'''
# UNIT TEST COMMENT: Candidate for Table Driven Tests 
### UNQ_C4 GRADED_FUNCTION: count_words ###
def count_words(tokenized_sentences):
    """
    Count the number of word appearence in the tokenized sentences
    
    Args:
        tokenized_sentences: List of lists of strings
    
    Returns:
        dict that maps word (str) to the frequency (int)
    """
            
    word_counts = {}
    ### START CODE HERE ###
    
    # Loop through each sentence
    for sentence in tokenized_sentences: # complete this line
        
        # Go through each token in the sentence
        for token in sentence: # complete this line

            # If the token is not in the dictionary yet, set the count to 1
            if token not in word_counts: # complete this line with the proper condition
                word_counts[token] = 1
            
            # If the token is already in the dictionary, increment the count by 1
            else:
                word_counts[token] += 1

    ### END CODE HERE ###
    
    return word_counts

    # test your code
tokenized_sentences = [['sky', 'is', 'blue', '.'],
                       ['leaves', 'are', 'green', '.'],
                       ['roses', 'are', 'red', '.']]
count_words(tokenized_sentences)

'''
Handling 'Out of Vocabulary' words
If your model is performing autocomplete, but encounters a word that it never saw during training, it won't have an input word to help it determine the next word to suggest. The model will not be able to predict the next word because there are no counts for the current word.

This 'new' word is called an 'unknown word', or out of vocabulary (OOV) words.
The percentage of unknown words in the test set is called the OOV rate.
To handle unknown words during prediction, use a special token to represent all unknown words 'unk'.

Modify the training data so that it has some 'unknown' words to train on.
Words to convert into "unknown" words are those that do not occur very frequently in the training set.
Create a list of the most frequent words in the training set, called the closed vocabulary .
Convert all the other words that are not part of the closed vocabulary to the token 'unk'.
'''
# UNIT TEST COMMENT: Candidate for Table Driven Tests 
### UNQ_C5 GRADED_FUNCTION: get_words_with_nplus_frequency ###
def get_words_with_nplus_frequency(tokenized_sentences, count_threshold):
    """
    Find the words that appear N times or more
    
    Args:
        tokenized_sentences: List of lists of sentences
        count_threshold: minimum number of occurrences for a word to be in the closed vocabulary.
    
    Returns:
        List of words that appear N times or more
    """
    # Initialize an empty list to contain the words that
    # appear at least 'minimum_freq' times.
    closed_vocab = []
    
    # Get the word couts of the tokenized sentences
    # Use the function that you defined earlier to count the words
    word_counts = count_words(tokenized_sentences)
    
    ### START CODE HERE ###
#   UNIT TEST COMMENT: Whole thing can be one-lined with list comprehension
#   filtered_words = None

    # for each word and its count
    for word, cnt in word_counts.items(): # complete this line
        
        # check that the word's count
        # is at least as great as the minimum count
        if cnt >= count_threshold: # complete this line with the proper condition
            
            # append the word to the list
            closed_vocab.append(word)
    ### END CODE HERE ###
    
    return closed_vocab

# test your code
tokenized_sentences = [['sky', 'is', 'blue', '.'],
                       ['leaves', 'are', 'green', '.'],
                       ['roses', 'are', 'red', '.']]
tmp_closed_vocab = get_words_with_nplus_frequency(tokenized_sentences, count_threshold=2)
print(f"Closed vocabulary:")
print(tmp_closed_vocab)

# Test your function
w3_unittest.test_get_words_with_nplus_frequency(get_words_with_nplus_frequency)

# UNIT TEST COMMENT: Candidate for Table Driven Tests 
### UNQ_C6 GRADED_FUNCTION: replace_oov_words_by_unk ###
def replace_oov_words_by_unk(tokenized_sentences, vocabulary, unknown_token="<unk>"):
    """
    Replace words not in the given vocabulary with '<unk>' token.
    
    Args:
        tokenized_sentences: List of lists of strings
        vocabulary: List of strings that we will use
        unknown_token: A string representing unknown (out-of-vocabulary) words
    
    Returns:
        List of lists of strings, with words not in the vocabulary replaced
    """
    
    # Place vocabulary into a set for faster search
    vocabulary = set(vocabulary)
    
    # Initialize a list that will hold the sentences
    # after less frequent words are replaced by the unknown token
    replaced_tokenized_sentences = []
    
    # Go through each sentence
    for sentence in tokenized_sentences:
        
        # Initialize the list that will contain
        # a single sentence with "unknown_token" replacements
        replaced_sentence = []
        ### START CODE HERE (Replace instances of 'None' with your code) ###

        # for each token in the sentence
        for token in sentence: # complete this line
            
            # Check if the token is in the closed vocabulary
            if token in vocabulary: # complete this line with the proper condition
                # If so, append the word to the replaced_sentence
                replaced_sentence.append(token)
            else:
                # otherwise, append the unknown token instead
                replaced_sentence.append(unknown_token)
        ### END CODE HERE ###
        
        # Append the list of tokens to the list of lists
        replaced_tokenized_sentences.append(replaced_sentence)
    return replaced_tokenized_sentences