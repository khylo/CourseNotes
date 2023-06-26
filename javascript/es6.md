See https://www.youtube.com/watch?v=uaBNBWwjzV8&ab_channel=Udemy

# Variables
var now has 2 extras
let and const
let just solidifies the use of var
let has block scope. It doesn't allow hoisting etc
const is a constant

## Functions using fat pipe
```
# These are all equivilent
var fn = function (a,b) {
    return a+b;
}
console.log(fn(3,8));

var fn  = (a,b) => {
    return a+b;
}
console.log(fn(3,8));

 # This can be written in simpler format


```

fat pipe syntax is subtly different to normal function syntax, especially in relation to 'this' usage

In fat pipe syntax, this will take the value of the calling element. See
