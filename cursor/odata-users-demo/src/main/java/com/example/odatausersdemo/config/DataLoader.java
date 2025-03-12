package com.example.odatausersdemo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.example.odatausersdemo.model.User;
import com.example.odatausersdemo.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    private static final String[] FIRST_NAMES = {"John", "Jane", "Michael", "Emily", "David", "Sarah", "James", "Emma", "William", "Olivia"};
    private static final String[] LAST_NAMES = {"Smith", "Johnson", "Williams", "Brown", "Jones", "Garcia", "Miller", "Davis", "Rodriguez", "Martinez"};
    private static final String[] TITLES = {"Software Engineer", "Product Manager", "Data Scientist", "UX Designer", "DevOps Engineer", "Project Manager", "Business Analyst", "QA Engineer"};
    private static final String[] ROLES = {"Developer", "Manager", "Analyst", "Designer", "Administrator", "Architect", "Lead", "Senior"};
    private static final String[] LOCATIONS = {"New York", "San Francisco", "London", "Berlin", "Tokyo", "Singapore", "Sydney", "Toronto", "Paris", "Amsterdam"};

    @Override
    public void run(String... args) {
        if (userRepository.count() == 0) {
            List<User> users = new ArrayList<>();
            Random random = new Random();

            for (int i = 0; i < 500; i++) {
                String firstName = FIRST_NAMES[random.nextInt(FIRST_NAMES.length)];
                String lastName = LAST_NAMES[random.nextInt(LAST_NAMES.length)];
                String title = TITLES[random.nextInt(TITLES.length)];
                String role = ROLES[random.nextInt(ROLES.length)];
                String location = LOCATIONS[random.nextInt(LOCATIONS.length)];
                String email = firstName.toLowerCase() + "." + lastName.toLowerCase() + "@example.com";

                User user = new User();
                user.setFirstName(firstName);
                user.setLastName(lastName);
                user.setTitle(title);
                user.setRole(role);
                user.setEmail(email);
                user.setLocation(location);

                users.add(user);
            }

            userRepository.saveAll(users);
        }
    }
} 