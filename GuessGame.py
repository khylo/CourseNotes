import random
class Game:
    max = 100
    min=0
    guesses = 0
    target=0
    solved=False

    def start(self, max=100, min=0):
        self.max=max
        self.min=min
        self.guesses=0
        self.target = random.randrange(min,max,1)
        self.solved=False
        return "Guess a number between "+str(min)+" and "+str(max)

    def quit(self):
        if(self.solved):
            return "You solved it. It took you "+str(self.guesses)+" guess. Would you like to play again?"
        print("Not  Solved")
        return "Sorry to see you go. The number was "+str(self.target)

    def guess(self, guessed):
        if(self.solved):
            return self.quit()
        self.guesses = self.guesses +1
        if (guessed == self.target):
            self.solved=True
            return self.quit()
        if (guessed < self.target):
            return "Try a higher number"
        return "Try a lower number"


g = Game()
print (g.start(6,5))
print ("Guessing 5 ")
print (g.guess(5))
print (g.guess(5))
print (g.quit())
