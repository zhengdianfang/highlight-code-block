# Simple Python test
import os

def factorial(n):
    if n == 0:
        return 1
    else:
        return n * factorial(n-1)

@decorator
class MyClass(object):
    """This is a class docstring."""
    def __init__(self, value):
        self.value = value
        
    def get_value(self):
        return self.value

if __name__ == "__main__":
    print("Factorial of 5 is:", factorial(5))
    obj = MyClass(10)
    print(obj.get_value())
