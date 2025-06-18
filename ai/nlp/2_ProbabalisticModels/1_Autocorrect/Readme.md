Part of Speech Tagging
Part of Speech Tagging (POS) is the process of assigning a part of speech to a word. By doing so, you will learn the following: 

* Markov Chains
Into to markov chains
* Hidden Markov Models
Since computer do not understand naturally what type a word is we use hidden M\rkov models to model them and caculate probabilities of transition.
+ Viterbi algorithm
  Traine algorithm consisting of 3 stage process. Init, forward, backward
  + Init 
  Create 3 matrices
    * POS tags (e.g. noun verb etc)
    * A, Transition , Probability of going from 1 POS state to another
    * B, Emission Matric. Prob of going from 1 state to a particular word. this  will be v large matrix for a std corpus. each row repesents  a state + cols are the porob of going to that word from that state. these are baysian conditional probs. caulate by counting and dividing
  + Forward Pass
  We have trsition to  POS tags and then multiply by emission to get prob of next word
  A . B 


Here is a concrete example:


You can use part of speech tagging for: 

Identifying named entities

Speech recognition

