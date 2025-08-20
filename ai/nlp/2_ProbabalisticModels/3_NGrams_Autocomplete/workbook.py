import math
import random
import numpy as np
import pandas as pd
import nltk
nltk.download('punkt')

nltk.data.path.append('.')

a="Test DAta.\nSplit by line breaks,\nCan I split it?"
words = a.split()
print(f"words = {words}")
nwords = nltk.word_tokenize(a);
print(f"nwords = {nwords}")

lines = a.split("\n")
print(f"lines = {lines}")

lwords = a.lower().split()
print(f"lwords = {lwords}")

biglist = []
for line in lines:
  w = line.lower().split()
  biglist.append(w)
print(f"biglist = {biglist}" )
