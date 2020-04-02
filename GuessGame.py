import random
class Game:
    max = 100
    min=0
    guesses = 0
    target=0

    def start(self, max=100, min=0):
        self.max=max
        self.min=min
        self.guesses=0
        self.target = random.randrange(min,max,1)
        return "Guess a number between "+str(min)+" and "+str(max)

    def quit(self):
        return "Sorry to see you go. THe number was "+str(self.target)

    def guess(self, guessed):
        self.guesses = self.guesses +1
        if (guessed == self.target):
            return "You got it. It took you "+str(self.guesses)+" guess."
        if (guessed < self.target):
            return "Try a higher number"
        return "Try a lower number"


g = Game()
print (g.start(6,5))
print ("Guessing 5 "+g.guess(5))
print (g.quit())
