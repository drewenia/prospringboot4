package com.apress.crm;

import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/customers")
public class CustomerController {
    private final Repository<Customer, UUID> repository;

    public CustomerController(Repository<Customer, UUID> repository) {
        this.repository = repository;
    }

    @PostMapping
    Customer save(@RequestBody Customer customer) {
        return repository.save(customer);
    }

    @GetMapping("/{id}")
    Customer findById(@PathVariable UUID id) {
        return repository.findById(id);
    }

    @GetMapping
    Iterable<Customer> findAll() {
        return repository.findAll();
    }

    @DeleteMapping("/{id}")
    void deleteById(@PathVariable UUID id){
        repository.deleteById(id);
    }
}
