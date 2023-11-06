# Notes from Course
Unit Teting for Typescript & NodeJs developers with Jest

# Setup
Code in this folder was installed on wsl, using nvm
Working folder /mnt/c/dev/CourseNotes/javascript/jest

```
nvm install node
nvm install npm
npm init -y
npm i -D typescript jest ts-jest @types/jest ts-node
# Next line create jest.config.js, but we want a ts version
# npx ts-jest config:init
# Manually create ts
# Write code and test
# Update package.json test to call jest
npm test
# Create tsconfig.json
```
# Intro
## Testing Basics
AAA principe = Arrange, Act, assert

So we want to add Setup nd Teardown

describe.. is callback for grouping suites
it or test is a callb ack for indicidual tests

Add *.only* (also *.xit*) to tests to only run this test
Add *.skip* to tests to skip this test
Add *.toso* to tests to mark them as todo
Add *.concurrant* to tests to only run this test

watch mode
add to jest.config.ts (may not work)..or to pachage.json, e.g.`
```
"scripts": {
    "test": "jest --watch"
  },
```

## Matchers
Note ToBe is ony for primitives, fo objects it checks object ref 
toEqual is better for Objects
arrayContaining (['s', 'S'])  # checks array entries regardless of order#

## Paramatrized tests
Use array of actual and expected. See example

# Intermediate testing
## FIRST princials
 * Fast, 
 * independent 
 May contradict Fast
 * repeatable 
 Issues with random test, or dates
 * self Validating
 Tests should be clear. PAss fail
 * Thorough