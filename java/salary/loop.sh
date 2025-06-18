#!/bin/bash
for salary in $(seq 50000 100 56000); do
    ./salaryCalc.sh --salary $salary
done