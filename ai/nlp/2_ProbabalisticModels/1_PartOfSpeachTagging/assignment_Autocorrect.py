import re
from collections import Counter
import numpy as np
import pandas as pd

import w1_unittest

# UNQ_C1 GRADED FUNCTION: process_data
def process_data(file_name):
    """
    Input: 
        A file_name which is found in your current directory. You just have to 
        read it in. 
    Output: 
        words: a list containing all the words in the corpus (text file you read) in lower case. 
    """
    words = [] # return this variable correctly

    ### START CODE HERE ### 
    #open file
    try:
        with open(file_name, 'r', encoding='utf-8') as f:
            data = f.read()
            words = re.findall(r'\b\w+\b', data.lower())
            return words
    except FileNotFoundError:
        print(f"Error: File '{filename}' not found.")
        return []
    except Exception as e:
        print(f"An error occurred: {e}")
        return []
    ### END CODE HERE ###
    
    return words

word_l = process_data('shakespeare.txt')
print(word_l[:10])
vocab = set(word_l)  # this will be your new vocabulary
print(len(word_l))
print(f"Num of unique words {len(vocab)}")

# Test your function
#w1_unittest.test_process_data(process_data)

# UNIT TEST COMMENT: Candidate for Table Driven Tests
# UNQ_C2 GRADED FUNCTION: get_count
def get_count(word_l):
    '''
    Input:
        word_l: a set of words representing the corpus. 
    Output:
        word_count_dict: The wordcount dictionary where key is the word and value is its frequency.
    '''
    
    word_count_dict = {}  # fill this with word counts
     ### START CODE HERE 
    #word_count_dict = Counter(word_l)
    for word in word_l:
        if word in word_count_dict:
            word_count_dict[word] += 1
        else:
            word_count_dict[word] = 1 

    ### END CODE HERE ### 
    return word_count_dict

#DO NOT MODIFY THIS CELL
word_count_dict = get_count(word_l)
print(f"First set of words and count")
#print first 1000 entries in word_count_dict
#print(list(word_count_dict.items())[:1000])
print(f"There are {len(word_count_dict)} key values pairs")
print(f"The count for the word 'thee' should be 240 {word_count_dict.get('thee',0)}")

# Test your function
#w1_unittest.test_get_count(get_count, word_l)


# UNQ_C3 GRADED FUNCTION: get_probs
def get_probs(word_count_dict):
    '''
    Input:
        word_count_dict: The wordcount dictionary where key is the word and value is its frequency.
    Output:
        probs: A dictionary where keys are the words and the values are the probability that a word will occur. 
    '''
    probs = {}  # return this variable correctly
    
    ### START CODE HERE ###
    #total_words = sum(word_count_dict.values())
    #probs = {word: count / total_words for word, count in word_count_dict.items()}
    total_words = sum(word_count_dict.values())
    probs = {word: count / total_words for word, count in word_count_dict.items()}
    #for( k,v in word_count_dict.items
   
    ### END CODE HERE ###
    return probs


#DO NOT MODIFY THIS CELL
probs = get_probs(word_count_dict)
print(f"Length of probs is {len(probs)}")
print(f"P('thee') is {probs['thee']:.4f}")

#mList comprehensions
#Split words for manipulation

def split_words(word):
    return     [(word[:i], word[i:]) for i in range(len(word)+1)]

splits = split_words('test')
print(splits)
    
'''
 Delete letter
'''
 # UNIT TEST COMMENT: Candidate for Table Driven Tests
# UNQ_C4 GRADED FUNCTION: deletes
def delete_letter(word, verbose=False):
    '''
    Input:
        word: the string/word for which you will generate all possible words 
                in the vocabulary which have 1 missing character
    Output:
        delete_l: a list of all possible strings obtained by deleting 1 character from word
    '''
    
    delete_l = []
    split_l = []
    
    ### START CODE HERE ###
    split_l=[(word[:i],word[i:]) for i in range(len(word)+1)]  
    # could skip +1 for deletes as in last case we have 'cans' '' so nothing to delete
    for L,R in split_l:
        if R:
            delete_l.append(L+R[1:])
    ### END CODE HERE ###

    if verbose: print(f"input word {word}, \nsplit_l = {split_l}, \ndelete_l = {delete_l}")

    return  delete_l   

delete_word_l = delete_letter(word="cans", verbose=True)    

'''
Switch words
Now implement a function that switches two letters in a word. It takes in a word and returns a list of all the possible switches of two letters that are adjacent to each other.

For example, given the word 'eta', it returns {'eat', 'tea'}, but does not return 'ate'.
Step 1: is the same as in delete_letter()
Step 2: A list comprehension or for loop which forms strings by swapping adjacent letters. This is of the form:
[f(L,R) for L, R in splits if condition] where 'condition' will test the length of R in a given iteration. 
'''
# UNIT TEST COMMENT: Candidate for Table Driven Tests
# UNQ_C5 GRADED FUNCTION: switches
def switch_letter(word, verbose=False):
    '''
    Input:
        word: input string
     Output:
        switches: a list of all possible strings with one adjacent charater switched
    ''' 
    
    switch_l = []
    split_l = []
    
    ### START CODE HERE ###
    split_l = [(word[:i], word[i:]) for i in range(len(word)+1)]
    #switch_l = [L[:len(L)-1]+R[0]+L[len(L)-1]+R[1:] for L, R in split_l if L and R]

    #probs = {word: count / total_words for word, count in word_count_dict.items()}
    #

    for L,R in split_l:
        if L and R:
            switch_l.append(L[:len(L)-1]+R[0]+L[len(L)-1]+R[1:])

    ### END CODE HERE ###
    
    if verbose: print(f"Input word = {word} \nsplit_l = {split_l} \nswitch_l = {switch_l}") 
    
    return switch_l

# test # 2
print(f"Number of outputs of switch_letter('at') is {len(switch_letter('at'))}")

# Test your function
w1_unittest.test_switch_letter(switch_letter)

'''
Exercise 6 - replace_letter
Instructions for replace_letter(): Now implement a function that takes in a word and returns a list of strings with one replaced letter from the original word.

Step 1: is the same as in delete_letter()

Step 2: A list comprehension or for loop which form strings by replacing letters. This can be of the form:
[f(a,b,c) for a, b in splits if condition for c in string] Note the use of the second for loop.
It is expected in this routine that one or more of the replacements will include the original word. For example, replacing the first letter of 'ear' with 'e' will return 'ear'.

Step 3: Remove the original input letter from the output.
'''
# UNIT TEST COMMENT: Candidate for Table Driven Tests
# UNQ_C6 GRADED FUNCTION: replaces
def replace_letter(word, verbose=False):
    '''
    Input:
        word: the input string/word 
    Output:
        replaces: a list of all possible strings where we replaced one letter from the original word. 
    ''' 
    
    letters = 'abcdefghijklmnopqrstuvwxyz'
    
    replace_l = []
    split_l = []
    
    ### START CODE HERE ###
    split_l = [(word[:i], word[i:]) for i in range(len(word)+1)]
    #replace_l = [L+letter+(R[1:] if R else '') for L, R in split_l if R for letter in letters]
    for L,R in split_l:
        if R:
            for letter in letters:
                if R[0]!=letter:
                    replace_l.append(L+letter+R[1:])
    
    ### END CODE HERE ###
    
    # Sort the list, for easier viewing
    replace_l = sorted(replace_l)
    
    if verbose: print(f"Input word = {word} \nsplit_l = {split_l} \nreplace_l {replace_l}")   
    
    return replace_l

replace_l = replace_letter(word='can',  verbose=True)

                            

'''
Exercise 7 - insert_letter
Instructions for insert_letter(): Now implement a function that takes in a word and returns a list with a letter inserted at every offset.

Step 1: is the same as in delete_letter()

Step 2: This can be a list comprehension of the form:
[f(a,b,c) for a, b in splits if condition for c in string]
'''
# UNIT TEST COMMENT: Candidate for Table Driven Tests
# UNQ_C7 GRADED FUNCTION: inserts
def insert_letter(word, verbose=False):
    '''
    Input:
        word: the input string/word 
    Output:
        inserts: a set of all possible strings with one new letter inserted at every offset
    ''' 
    letters = 'abcdefghijklmnopqrstuvwxyz'
    insert_l = []
    split_l = []
    
    ### START CODE HERE ###
    split_l = [(word[:i], word[i:]) for i in range(len(word)+1)]
    #insert_l = [L+letter+R for L, R in split_l for letter in letters]
    for L, R in split_l:
        for letter in letters:
            insert_l.append(L+letter+R)
        
    ### END CODE HERE ###
    
    if verbose: print(f"Input word {word} \nsplit_l = {split_l} \ninsert_l = {insert_l}")
    
    return insert_l

insert_l = insert_letter('at', True)
print(f"Number of strings output by insert_letter('at') is {len(insert_l)}")

# Test your function
w1_unittest.test_insert_letter(insert_letter)
'''
Exercise 8 - edit_one_letter
Instructions: Implement the edit_one_letter function to get all the possible edits that are one edit away from a word. The edits consist of the replace, insert, delete, and optionally the switch operation. You should use the previous functions you have already implemented to complete this function. The 'switch' function is a less common edit function, so its use will be selected by an "allow_switches" input argument.

Note that those functions return lists while this function should return a python set. Utilizing a set eliminates any duplicate entries.
'''
# UNIT TEST COMMENT: Candidate for Table Driven Tests
# UNQ_C8 GRADED FUNCTION: edit_one_letter
def edit_one_letter(word, allow_switches = True):
    """
    Input:
        word: the string/word for which we will generate all possible wordsthat are one edit away.
    Output:
        edit_one_set: a set of words with one possible edit. Please return a set. and not a list.
    """
    
    edit_one_set = set()
    
    ### START CODE HERE ###
    edit_one_set=delete_letter(word)+replace_letter(word)+insert_letter(word)
    if allow_switches:
        edit_one_set+=switch_letter(word)
    ### END CODE HERE ###
    
    # return this as a set and not a list
    return set(edit_one_set)

res=edit_one_letter('can',False)
print(f"Cans = {len(res)} \n{res}")

# Test your function
w1_unittest.test_edit_one_letter(edit_one_letter)
'''
Exercise 9 - edit_two_letters
Now you can generalize this to implement to get two edits on a word. To do so, you would have to get all the
 possible edits on a single word and then for each modified word, you would have to modify it again.

Instructions: Implement the edit_two_letters function that returns a set of words that are two edits away. Note 
that creating additional edits based on the edit_one_letter function may 'restore' some one_edits to zero or
 one edits. That is allowed here. This is accounted for in get_corrections.
'''
# UNIT TEST COMMENT: Candidate for Table Driven Tests
# UNQ_C9 GRADED FUNCTION: edit_two_letters
def edit_two_letters(word, allow_switches = True):
    '''
    Input:
        word: the input string/word 
    Output:
        edit_two_set: a set of strings with all possible two edits
    '''
    
    edit_two_set = set()
    
    ### START CODE HERE ###
    oneset=edit_one_letter(word, allow_switches)
    for word in oneset:
        edit_two_set |= edit_one_letter(word, allow_switches)
    ### END CODE HERE ###
    
    # return this as a set instead of a list
    return set(edit_two_set)

tmp_edit_two_set = edit_two_letters("a")
tmp_edit_two_l = sorted(list(tmp_edit_two_set))
print(f"Number of strings with edit distance of two: {len(tmp_edit_two_l)}")
print(f"First 10 strings {tmp_edit_two_l[:10]}")
print(f"Last 10 strings {tmp_edit_two_l[-10:]}")
print(f"The data type of the returned object should be a set {type(tmp_edit_two_set)}")
print(f"Number of strings that are 2 edit distances from 'at' is {len(edit_two_letters('at'))}")

# example of logical operation on lists or sets
print( [] and ["a","b"] )
print( [] or ["a","b"] )
#example of Short circuit behavior
val1 =  ["Most","Likely"] or ["Less","so"] or ["least","of","all"]  # selects first, does not evalute remainder
print(val1)
val2 =  [] or [] or ["least","of","all"] # continues evaluation until there is a non-empty list
print(val2)

'''
Exercise 10 - get_corrections
Instructions: Implement get_corrections, which returns a list of zero to n possible suggestion tuples of the 
form (word, probability_of_word).

Step 1: Generate suggestions for a supplied word: You'll use the edit functions you have developed. The 
'suggestion algorithm' should follow this logic:

If the word is in the vocabulary, suggest the word.
Otherwise, if there are suggestions from edit_one_letter that are in the vocabulary, use those.
Otherwise, if there are suggestions from edit_two_letters that are in the vocabulary, use those.
Otherwise, suggest the input word.*
The idea is that words generated from fewer edits are more likely than words with more edits.
Note:

Edits of two letters may 'restore' strings to either zero or one edit. This algorithm accounts for this by 
preferentially selecting lower distance edits first.
'''

# UNIT TEST COMMENT: Candidate for Table Driven Tests
# UNQ_C10 GRADED FUNCTION: get_corrections
'''
todo apply hints
Hints

edit_one_letter and edit_two_letters return *python sets*.
Sets have a handy set.intersection feature
To find the keys that have the highest values in a dictionary, you can use the Counter dictionary to create a Counter object from a regular dictionary. Then you can use Counter.most_common(n) to get the n most common keys.
To find the intersection of two sets, you can use set.intersection or the & operator.
If you are not as familiar with short circuit syntax (as shown above), feel free to use if else statements instead.
To use an if statement to check of a set is empty, use 'if not x:' syntax
'''
def get_corrections(word, probs, vocab, n=2, verbose = False):
    '''
    Input: 
        word: a user entered string to check for suggestions
        probs: a dictionary that maps each word to its probability in the corpus
        vocab: a set containing all the vocabulary
        n: number of possible word corrections you want returned in the dictionary
    Output: 
        n_best: a list of tuples with the most probable n corrected words and their probabilities.
    '''
    
    suggestions = []
    n_best = []
    
    ### START CODE HERE ###
    #Step 1: create suggestions as described above    
    #suggestions += (set(word) if word in vocab else set()).intersection(vocab)
    if word in vocab:
        return [(word,1)]
    
    #if verbose: print("suggestions1 = ", suggestions)
    if len(suggestions) < n:
        suggestions += edit_one_letter(word).intersection(vocab) 
    #    if verbose: print("suggestions2 = ", suggestions)
    if len(suggestions) < n:
        suggestions += edit_two_letters(word).intersection(vocab) 
    #    if verbose: print("suggestions3 = ", suggestions)
    #if verbose: print("suggestions4 = ", suggestions)

    #Step 2: determine probability of suggestions
    wordProbs = [[s, probs[s]] for s in suggestions]
    
    #Step 3: Get all your best words and return the most probable top n_suggested words as n_best
    
    wordProbs = sorted(wordProbs, key=lambda entry: entry[1], reverse=True)
    
    n_best = wordProbs[:n]
    
    ### END CODE HERE ###
    
    if verbose: print("entered word = ", word, "\nsuggestions = ", suggestions)
    #print("entered word = ", word, "\nsuggestions = ", suggestions)


    return n_best

# Test your implementation - feel free to try other words in my word
#my_word = 'dys' 
my_word = 'can'
tmp_corrections = get_corrections(my_word, probs, vocab, 2, verbose=True) # keep verbose=True
for i, word_prob in enumerate(tmp_corrections):
    print(f"word {i}: {word_prob[0]}, probability {word_prob[1]:.6f}")

# CODE REVIEW COMMENT: using "tmp_corrections" insteads of "cors". "cors" is not defined
print(f"data type of corrections {type(tmp_corrections)}")

# Test your function
w1_unittest.test_get_corrections(get_corrections, probs, vocab)

'''
4.1 - Dynamic Programming
Dynamic Programming breaks a problem down into subproblems which can be combined to form the final solution. 
Here, given a string source[0..i] and a string target[0..j], we will compute all the combinations of 
substrings[i, j] and calculate their edit distance. To do this efficiently, we will use a table to maintain the 
previously computed substrings and use those to calculate larger substrings.

You have to create a matrix and update each element in the matrix as follows:
Initialization
𝐷[0,0] = 0
𝐷[𝑖,0] = 𝐷[𝑖−1,0]+𝑑𝑒𝑙_𝑐𝑜𝑠𝑡(𝑠𝑜𝑢𝑟𝑐𝑒[𝑖])
𝐷[0,𝑗] = 𝐷[0,𝑗−1]+𝑖𝑛𝑠_𝑐𝑜𝑠𝑡(𝑡𝑎𝑟𝑔𝑒𝑡[𝑗])(4)

Per Cell Operations
         D[𝑖−1,𝑗]+𝑑𝑒𝑙_𝑐𝑜𝑠𝑡  
𝐷[i,𝑗] = 𝐷[𝑖,𝑗−1]+𝑖𝑛𝑠_𝑐𝑜𝑠𝑡
                     𝑟𝑒𝑝_𝑐𝑜𝑠𝑡; 𝑖𝑓𝑠𝑟𝑐[𝑖]≠𝑡𝑎𝑟[𝑗]   replace cost
         𝐷[𝑖−1,𝑗−1]+{    0   ; 𝑖𝑓𝑠𝑟𝑐[𝑖]=𝑡𝑎𝑟[𝑗]

build from outside row and col in  e.g.start with play, how to get to stay  p-> '' =1  pl -> '' =2s
this gives us d[i,0] and d[0,j]
  	#	s	t	a	y
#	0	1	2	3	4
p	1	2	3	4	5
l	2	3	4	5	6
a	3	4	5	4	5
y	4	5	6	5	4

'''
# UNQ_C11 GRADED FUNCTION: min_edit_distance
def min_edit_distance(source, target, ins_cost = 1, del_cost = 1, rep_cost = 2):
    '''
    Input: 
        source: a string corresponding to the string you are starting with
        target: a string corresponding to the string you want to end with
        ins_cost: an integer setting the insert cost
        del_cost: an integer setting the delete cost
        rep_cost: an integer setting the replace cost
    Output:
        D: a matrix of len(source)+1 by len(target)+1 containing minimum edit distances
        med: the minimum edit distance (med) required to convert the source string to the target
    '''
    # use deletion and insert cost as  1
    m = len(source) 
    n = len(target) 
    #initialize cost matrix with zeros and dimensions (m+1,n+1) 
    D = np.zeros((m+1, n+1), dtype=int) 
    
    ### START CODE HERE (Replace instances of 'None' with your code) ###
    
    # Fill in column 0, from row 1 to row m, both inclusive
    for row in range(1,m+1): # Replace None with the proper range
        D[row,0] = row*ins_cost
        
    # Fill in row 0, for all columns from 1 to n, both inclusive
    for col in range(1,n+1): # Replace None with the proper range
        D[0,col] = col*del_cost
        
    # Loop through row 1 to row m, both inclusive
    for row in range(1,m+1):
        
        # Loop through column 1 to column n, both inclusive
        for col in range(1,n+1):
            
            # Intialize r_cost to the 'replace' cost that is passed into this function
            r_cost = rep_cost
            
            # Check to see if source character at the previous row
            # matches the target character at the previous column, 
            if source[row-1]==target[col-1]: # Replace None with a proper comparison
                # Update the replacement cost to 0 if source and target are the same
                r_cost = 0
                
            # Update the cost at row, col based on previous entries in the cost matrix
            # Refer to the equation calculate for D[i,j] (the minimum of three calculated costs)
            D[row,col] = min(D[row-1,col]+del_cost, D[row,col-1]+ins_cost,D[row-1,col-1]+r_cost)
            
    # Set the minimum edit distance with the cost found at row m, column n 
    med = D[m,n]
    
    ### END CODE HERE ###
    return D, med
    
#DO NOT MODIFY THIS CELL
# testing your implementation 
source =  'play'
target = 'stay'
matrix, min_edits = min_edit_distance(source, target)
print("minimum edits: ",min_edits, "\n")
idx = list('#' + source)
cols = list('#' + target)
df = pd.DataFrame(matrix, index=idx, columns= cols)
print(df)

#DO NOT MODIFY THIS CELL
# testing your implementation 
source =  'eer'
target = 'near'
matrix, min_edits = min_edit_distance(source, target)
print("minimum edits: ",min_edits, "\n")
idx = list(source)
idx.insert(0, '#')
cols = list(target)
cols.insert(0, '#')
df = pd.DataFrame(matrix, index=idx, columns= cols)
print(df)

# Test your function
w1_unittest.test_min_edit_distance(min_edit_distance)