# Buggy version
class Stack:
    def __init__(self):
        self.items = []

    def push(self, item):
        self.items.append(item)

    def pop(self):
        if self.items:
            return self.items.pop()
        else:
            return None  

    def peek(self):
        if self.items:
            return self.items[-1]
        else:
            return None  

    def is_empty(self):
        return len(self.items) == 0

    def size(self):
        return len(self.items)