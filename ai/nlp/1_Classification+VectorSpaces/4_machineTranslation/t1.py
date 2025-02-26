### START CODE HERE ###

    # X_l and Y_l are lists of the english and french word embeddings
    X_l = list()
    Y_l = list()

    # get the english words (the keys in the dictionary) and store in a set()
    english_set = None

    # get the french words (keys in the dictionary) and store in a set()
    french_set = None

    # store the french words that are part of the english-french dictionary (these are the values of the dictionary)
    french_words = set(en_fr.values())

    # loop through all english, french word pairs in the english french dictionary
    for en_word, fr_word in en_fr.items():

        # check that the french word has an embedding and that the english word has an embedding
        if fr_word in french_set and en_word in english_set:

            # get the english embedding
            en_vec = english_vecs[en_word]

            # get the french embedding
            fr_vec = None

            # add the english embedding to the list
            X_l.append(en_vec)

            # add the french embedding to the list
            None

    # stack the vectors of X_l into a matrix X
    X = None

    # stack the vectors of Y_l into a matrix Y
    Y = None
    ### END CODE HERE ###