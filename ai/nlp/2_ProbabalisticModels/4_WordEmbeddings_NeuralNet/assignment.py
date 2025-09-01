import re
import nltk

nltk.download('punkt')

import emoji
import numpy as np
from nltk.tokenize import word_tokenize
from utils2 import get_dict

# Define a corpus
corpus = 'Who ❤️ "word embeddings" in 2020? I do!!!'

# Print original corpus
print(f'Corpus:  {corpus}')

# Do the substitution
data = re.sub(r'[,!?;-]+', '.', corpus)

# Print cleaned corpus
print(f'After cleaning punctuation:  {data}')

# Print cleaned corpus
print(f'Initial string:  {data}')

# Tokenize the cleaned corpus
data = nltk.word_tokenize(data)

# Print the tokenized version of the corpus
print(f'After tokenization:  {data}')

# Print the tokenized version of the corpus
print(f'Initial list of tokens:  {data}')

# Filter tokenized corpus using list comprehension
data = [ ch.lower() for ch in data
         if ch.isalpha()
         or ch == '.'
         #or emoji.get_emoji_regexp().search(ch)
         or emoji.is_emoji(ch)
       ]

# Print the tokenized and filtered version of the corpus
print(f'After cleaning:  {data}')

# Define the 'tokenize' function that will include the steps previously seen
def tokenize(corpus):
    data = re.sub(r'[,!?;-]+', '.', corpus)
    data = nltk.word_tokenize(data)  # tokenize string to words
    data = [ ch.lower() for ch in data
             if ch.isalpha()
             or ch == '.'
             or emoji.is_emoji(ch)
           ]
    return data

# Define new corpus
corpus = 'I am happy because I am learning'

# Print new corpus
print(f'Corpus:  {corpus}')

# Save tokenized version of corpus into 'words' variable
words = tokenize(corpus)

# Print the tokenized version of the corpus
print(f'Words (tokens):  {words}')

# Run this with any sentence
tokenize("Now it's your turn: try with your own sentence!")
tokenize("What the helly? (I'm not sure if this will work), but let's give it a go")
nltk.word_tokenize("I'm not sure")

# Define the 'get_windows' function
def get_windows(words, C):
    i = C
    while i < len(words) - C:
        center_word = words[i]
        context_words = words[(i - C):i] + words[(i+1):(i+C+1)]
        yield context_words, center_word
        i += 1

# Print 'context_words' and 'center_word' for the new corpus with a 'context half-size' of 2
for x, y in get_windows(['i', 'am', 'happy', 'because', 'i', 'am', 'learning'], 1):
    print(f'{x}\t{y}')

# Print 'context_words' and 'center_word' for any sentence with a 'context half-size' of 1
for x, y in get_windows(tokenize("Now it's your turn: try with your own sentence! What the helly? (I'm not sure if this will work), but let's give it a go"), 3):
    print(f'{x}\t{y}')

# Get 'word2Ind' and 'Ind2word' dictionaries for the tokenized corpus
word2Ind, Ind2word = get_dict(words)

# Print 'word2Ind' dictionary
word2Ind

# Print value for the key 'i' within word2Ind dictionary
print("Index of the word 'i':  ",word2Ind['i'])

# Print 'Ind2word' dictionary
Ind2word

# Print value for the key '2' within Ind2word dictionary
print("Word which has index 2:  ",Ind2word[2] )

# Save length of word2Ind dictionary into the 'V' variable
V = len(word2Ind)

# Print length of word2Ind dictionary
print("Size of vocabulary: ", V)

# Save index of word 'happy' into the 'n' variable
n = word2Ind['happy']

# Print index of word 'happy'
n

# Create vector with the same length as the vocabulary, filled with zeros
center_word_vector = np.zeros(V)

# Print vector
center_word_vector

# Assert that the length of the vector is the same as the size of the vocabulary
len(center_word_vector) == V

# Replace element number 'n' with a 1
center_word_vector[n] = 1

# Print vector
center_word_vector

# Define the 'word_to_one_hot_vector' function that will include the steps previously seen
def word_to_one_hot_vector(word, word2Ind, V):
    one_hot_vector = np.zeros(V)
    one_hot_vector[word2Ind[word]] = 1
    return one_hot_vector

    # Print output of 'word_to_one_hot_vector' function for word 'happy'
word_to_one_hot_vector('happy', word2Ind, V)

# Print output of 'word_to_one_hot_vector' function for word 'learning'
word_to_one_hot_vector('learning', word2Ind, V)

''' 
GEtting context word vectors
'''
# Define list containing context words
context_words = ['i', 'am', 'because', 'i']

# Create one-hot vectors for each context word using list comprehension
context_words_vectors = [word_to_one_hot_vector(w, word2Ind, V) for w in context_words]

# Print one-hot vectors for each context word
context_words_vectors

# Compute mean of the vectors using numpy
np.mean(context_words_vectors, axis=0)

# Define the 'context_words_to_vector' function that will include the steps previously seen
def context_words_to_vector(context_words, word2Ind, V):
    context_words_vectors = [word_to_one_hot_vector(w, word2Ind, V) for w in context_words]
    context_words_vectors = np.mean(context_words_vectors, axis=0)
    return context_words_vectors

# Print output of 'context_words_to_vector' function for context words: 'i', 'am', 'because', 'i'
context_words_to_vector(['i', 'am', 'because', 'i'], word2Ind, V)

# Print output of 'context_words_to_vector' function for context words: 'am', 'happy', 'i', 'am'
context_words_to_vector(['am', 'happy', 'i', 'am'], word2Ind, V)

'''
Building the trainine set
You can now combine the functions that you created in the previous sections, to build a training set for the CBOW model, starting from the following tokenized corpus.
'''
words

# Print vectors associated to center and context words for corpus
for context_words, center_word in get_windows(words, 2):  # reminder: 2 is the context half-size
    print(f'Context words:  {context_words} -> {context_words_to_vector(context_words, word2Ind, V)}')
    print(f'Center word:  {center_word} -> {word_to_one_hot_vector(center_word, word2Ind, V)}')
    print()

# Define the generator function 'get_training_example'
def get_training_example(words, C, word2Ind, V):
    for context_words, center_word in get_windows(words, C):
        yield context_words_to_vector(context_words, word2Ind, V), word_to_one_hot_vector(center_word, word2Ind, V)

# Print vectors associated to center and context words for corpus using the generator function
for context_words_vector, center_word_vector in get_training_example(words, 2, word2Ind, V):
    print(f'Context words vector:  {context_words_vector}')
    print(f'Center word vector:  {center_word_vector}')
    print()            
