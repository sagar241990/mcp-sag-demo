package com.example.apiserver.api_server.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

// Need to write a RestController to handle API requests based on api-swagger-definition.yaml with test data 

@Slf4j
@RestController
@RequestMapping("/api")
public class APIController {

    @GetMapping("/customers")
    public List<Customer> getAllCustomers() {
        log.info("Received request to fetch all customers");

        try {
            // Return test data or fetch from a service
            List<Customer> customers = Arrays.asList(
                    new Customer(1, "John Doe", "john@example.com"),
                    new Customer(2, "Jane Smith", "jane@example.com"));

            log.info("Successfully retrieved {} customers", customers.size());
            log.debug("Customer details: {}", customers);

            return customers;

        } catch (Exception e) {
            log.error("Error occurred while fetching customers", e);
            throw new RuntimeException("Failed to fetch customers", e);
        }
    }

}
