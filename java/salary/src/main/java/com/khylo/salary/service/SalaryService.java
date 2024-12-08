package com.khylo.salary.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class SalaryService {

    Logger log = LoggerFactory.getLogger(this.getClass());
    public SalaryCalculatorService getSalaryCalculator(){
        return getSalaryCalculator(LocalDate.now());
    }
    public SalaryCalculatorService getSalaryCalculator(LocalDate date){
        for(SalaryCalculatorService scs:salaryCalculatorServiceImpls){
            if(scs.isValidForYear(date.getYear()))
                return scs;
        }
        log.warn("No SalaryCalculator impl for date "+date);
        return null;
    }

    @Autowired
    private List<SalaryCalculatorService> salaryCalculatorServiceImpls;

}