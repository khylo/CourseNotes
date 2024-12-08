package com.khylo.salary.service;

public abstract class SalaryCalculatorService {

    public abstract boolean isValidForYear(int year);
    public abstract Salary calcSalary(Salary salary);
}
