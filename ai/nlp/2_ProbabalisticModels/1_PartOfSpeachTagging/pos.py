import string
from collections import defaultdict

# Read lines from 'WSJ_02-21.pos' file and save them into the 'lines' variable
with open("./WSJ_02-21.pos", 'r') as f:
    lines = f.readlines()

# Print columns for reference
print("\t\tWord", "\tTag\n")

# Print first five lines of the dataset
for i in range(5):
    print(f'line number {i+1}: {lines[i]}')

'''
Things line Noun (nn) and so on
So we can use the same tag for both.

		Word 	Tag
line number 1: In	IN
line number 2: an	DT
line number 3: Oct.	NNP
line number 4: 19	CD
line number 5: review	NN
'''

# Print first line (unformatted)
lines[0]  # 'In\tIN\n'

#create vocabulary
# Get the words from each line in the dataset
words = [line.split('\t')[0] for line in lines]

# Define defaultdict of type 'int'.. it  defaults to 0 so no need for if not exists add else append 
freq = defaultdict(int)

# Count frequency of ocurrence for each word in the dataset
for word in words:
    freq[word] += 1

# Create the vocabulary by filtering the 'freq' dictionary.. USing python list comprehensio
vocab = [k for k, v in freq.items() if (v > 1 and k != '\n')]

# Sort the vocabulary
vocab.sort()

# Print some random values of the vocabulary
for i in range(4000, 4005):
    print(vocab[i])

def assign_unk(word):
    """
    Assign tokens to unknown words
    """
    
    # Punctuation characters
    # Try printing them out in a new cell!
    punct = set(string.punctuation)
    
    # Suffixes
    noun_suffix = ["action", "age", "ance", "cy", "dom", "ee", "ence", "er", "hood", "ion", "ism", "ist", "ity", "ling", "ment", "ness", "or", "ry", "scape", "ship", "ty"]
    verb_suffix = ["ate", "ify", "ise", "ize"]
    adj_suffix = ["able", "ese", "ful", "i", "ian", "ible", "ic", "ish", "ive", "less", "ly", "ous"]
    adv_suffix = ["ward", "wards", "wise"]

    # Loop the characters in the word, check if any is a digit
    if any(char.isdigit() for char in word):
        return "--unk_digit--"

    # Loop the characters in the word, check if any is a punctuation character
    elif any(char in punct for char in word):
        return "--unk_punct--"

    # Loop the characters in the word, check if any is an upper case character
    elif any(char.isupper() for char in word):
        return "--unk_upper--"

    # Check if word ends with any noun suffix
    elif any(word.endswith(suffix) for suffix in noun_suffix):
        return "--unk_noun--"

    # Check if word ends with any verb suffix
    elif any(word.endswith(suffix) for suffix in verb_suffix):
        return "--unk_verb--"

    # Check if word ends with any adjective suffix
    elif any(word.endswith(suffix) for suffix in adj_suffix):
        return "--unk_adj--"

    # Check if word ends with any adverb suffix
    elif any(word.endswith(suffix) for suffix in adv_suffix):
        return "--unk_adv--"
    
    # If none of the previous criteria is met, return plain unknown
    return "--unk--"

def get_word_tag(line, vocab):
    # If line is empty return placeholders for word and tag
    if not line.split():
        word = "--n--"
        tag = "--s--"
    else:
        # Split line to separate word and tag
        word, tag = line.split()
        # Check if word is not in vocabulary
        if word not in vocab: 
            # Handle unknown word
            tag = assign_unk(word)
    return word, tag

tags=get_word_tag('\n', vocab)
print(f"MT line => {tags}")

tags=get_word_tag('In\tIN\n', vocab)
print(f"In\\tIN\\n => {tags}")

tags=get_word_tag('tardigrade\tNN\n', vocab)
print(f"tardigrade\\tNN\\n => {tags}")

tags=get_word_tag('scrutinize\tVB\n', vocab)
print(f"scrutinize\\tVB\\n' => {tags}")
