import sys
import numpy

def magic_summation(n, seed = None):
	### DO NOT REMOVE OR CHANGE THE COMMAND BELOW 
	### AS IT WON'T BE POSSIBLE TO CORRECTLY GRADE YOUR SOLUTION
	numpy.random.seed(seed)
    # Input validation
	if n <= 2:
		return 'n cannot be less than or equal to 2'
	elif not isinstance(n,int):
		return "n must be an integer"
	magic_list = list(range(1,n+1))
    # Generate unique indices to remove
	indices_to_remove_not_unique = [int(numpy.random.random() * n) + 1 for _ in range(int(numpy.random.random() * n) + 1)]
	indices_to_remove = []
    # Check if all indices are to be removed
	for x in indices_to_remove_not_unique:
		if x not in indices_to_remove:
			indices_to_remove.append(x)
	if len(indices_to_remove) == len(magic_list):
		print("Magic summation is equal to 0.")
		return 0
	def iterator():
        # Remove elements from magic_list at specified indices
		for idx in indices_to_remove:
			if idx < len(magic_list):
				del magic_list[idx]
        # Update magic_list elements
		for i in range(len(magic_list)):
			if i < len(magic_list) - 1:
				magic_list[i] = magic_list[i+1]//magic_list[i]
        # Yield elements in magic_list
		for el in magic_list:
			yield el
	it = iterator()
	magic_summation_value = 0
    # Summation of elements from iterator
	while True:
		try:
			magic_summation_value += next(it)
		except StopIteration:
			break
	print("Magic summation is equal to: {0}.".format(magic_summation_value))
	return magic_summation_value


#######################################
# ###### DO NOT EDIT THIS PART #########
# ######################################

if __name__ == "__main__":
    if len(sys.argv) > 3:
        print("You must pass at most two arguments, the value for n and/or the random seed")
        sys.exit()
    elif len(sys.argv) == 1:
        print("You must pass at least one argument, the value for n")
        sys.exit()
    elif len(sys.argv) == 3:
        n = int(sys.argv[1])
        seed = int(sys.argv[2])
    else:
        n = int(sys.argv[1])
        seed = None

    magic_summation(n, seed=seed)
