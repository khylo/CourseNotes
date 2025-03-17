'''
Split a word into as many ways
'''

# data
word = 'dearz' # 🦌

# splits with a loop
splits_a = []
for i in range(len(word)+1):   # for i in range (5):  # this goes from 0->4
    print(f"i={i} word[:i]={word[:i]}")
    splits_a.append([word[:i],word[i:]])

for i in splits_a:
    print(i)


# same splits, done using a list comprehension
splits_b = [(word[:i], word[i:]) for i in range(len(word) + 1)]

for i in splits_b:
    print(i)

'''
Delete letter from splits
'''
# deletes with a loop
splits = splits_a
deletes = []

print('word : ', word)
for L,R in splits:
    if R:
        print(L + R[1:], ' <-- delete ', R[0])

# breaking it down
print('word : ', word)
one_split = splits[0]
print('first item from the splits list : ', one_split)
L = one_split[0]
R = one_split[1]
print('L : ', L)
print('R : ', R)
print('*** now implicit delete by excluding the leading letter ***')
print('L + R[1:] : ',L + R[1:], ' <-- delete ', R[0])

# deletes with a list comprehension
splits = splits_a
deletes = [L + R[1:] for L, R in splits if R]

print(deletes)
print('*** which is the same as ***')
for i in deletes:
    print(i)

'''
You now have a list of candidate strings created after performing a delete edit.
Next step will be to filter this list for candidate words found in a vocabulary.
Given the example vocab below, can you think of a way to create a list of 
candidate words ?
Remember, you already have a list of candidate strings, some of which are
 certainly not actual words you might find in your vocabulary !

So from the above list earz, darz, derz, deaz, dear.
You're really only interested in dear.
'''
vocab = ['dean','deer','dear','fries','and','coke']
edits = list(deletes)

print('vocab : ', vocab)
print('edits : ', edits)

candidates=[]

### START CODE HERE ###
#candidates = ??  # hint: 'set.intersection'
candidates = set(vocab).intersection(set(edits))
print('candidate words : ', candidates)
#alt
for word in edits:
    if word in vocab:
        candidates.add(word)	
### END CODE HERE ###

print('candidate words oldskool : ', candidates)