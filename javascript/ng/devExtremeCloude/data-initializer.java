package com.example.devextremespringboot.config;

import com.example.devextremespringboot.model.Employee;
import com.example.devextremespringboot.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public void run(String... args) {
        // Clear existing data
        employeeRepository.deleteAll();

        // Create sample employees
        Employee[] employees = {
            new Employee("John", "Doe", "Software Engineer", "Engineering", 
                         new BigDecimal("85000.00"), LocalDate.of(2019, 4, 15),
                         "john.doe@example.com", "555-123-4567", "123 Main St", "Seattle", "WA", "98101"),
            new Employee("Jane", "Smith", "Product Manager", "Product", 
                         new BigDecimal("95000.00"), LocalDate.of(2018, 7, 21),
                         "jane.smith@example.com", "555-234-5678", "456 Pine St", "Seattle", "WA", "98102"),
            new Employee("Michael", "Johnson", "Senior Developer", "Engineering", 
                         new BigDecimal("110000.00"), LocalDate.of(2017, 2, 10),
                         "michael.johnson@example.com", "555-345-6789", "789 Oak St", "Bellevue", "WA", "98004"),
            new Employee("Emily", "Williams", "QA Engineer", "Engineering", 
                         new BigDecimal("75000.00"), LocalDate.of(2020, 9, 5),
                         "emily.williams@example.com", "555-456-7890", "321 Elm St", "Redmond", "WA", "98052"),
            new Employee("Robert", "Brown", "UX Designer", "Design", 
                         new BigDecimal("82000.00"), LocalDate.of(2019, 11, 18),
                         "robert.brown@example.com", "555-567-8901", "654 Birch St", "Seattle", "WA", "98103"),
            new Employee("Sarah", "Davis", "Marketing Specialist", "Marketing", 
                         new BigDecimal("72000.00"), LocalDate.of(2020, 3, 25),
                         "sarah.davis@example.com", "555-678-9012", "987 Cedar St", "Kirkland", "WA", "98033"),
            new Employee("David", "Miller", "HR Manager", "Human Resources", 
                         new BigDecimal("88000.00"), LocalDate.of(2018, 5, 12),
                         "david.miller@example.com", "555-789-0123", "159 Walnut St", "Seattle", "WA", "98104"),
            new Employee("Lisa", "Wilson", "Sales Representative", "Sales", 
                         new BigDecimal("78000.00"), LocalDate.of(2019, 8, 30),
                         "lisa.wilson@example.com", "555-890-1234", "357 Maple St", "Bellevue", "WA", "98005"),
            new Employee("James", "Moore", "Database Administrator", "Engineering", 
                         new BigDecimal("92000.00"), LocalDate.of(2017, 10, 22),
                         "james.moore@example.com", "555-901-2345", "246 Spruce St", "Seattle", "WA", "98106"),
            new Employee("Patricia", "Taylor", "Finance Analyst", "Finance", 
                         new BigDecimal("80000.00"), LocalDate.of(2020, 1, 8),
                         "patricia.taylor@example.com", "555-012-3456", "753 Ash St", "Redmond", "WA", "98053")
        };

        // Save all employees
        employeeRepository.saveAll(Arrays.asList(employees));
        
        System.out.println("Sample data initialized with " + employeeRepository.count() + " employees");
    }
}
