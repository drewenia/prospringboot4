package com.apress.customer;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Locale;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/customers")
public class CustomerController {
    private final CrmProperties crmProperties;
    private final Repository<Customer, UUID> repository;
    private final MessageSource messageSource;

    public CustomerController(CrmProperties crmProperties, Repository<Customer, UUID> repository, MessageSource messageSource) {
        this.crmProperties = crmProperties;
        this.repository = repository;
        this.messageSource = messageSource;
    }

    @GetMapping("/greet/{name}")
    public String greet(@PathVariable String name){
        Locale locale = LocaleContextHolder.getLocale();
        return messageSource.getMessage("welcome.message",new Object[]{name},locale);
    }

    @GetMapping("/welcome")
    public String sayHello() {
        return this.crmProperties.welcomeMessage();
    }

    @GetMapping
    public Iterable<Customer> findAll() {
        return this.repository.findAll();
    }

    @GetMapping("/{id}")
    public Customer findById(@PathVariable UUID id) {
        return this.repository.findById(id);
    }

    @PostMapping
    public Customer save(@RequestBody Customer customer) {
        return this.repository.save(customer);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable UUID id) {
        this.repository.delete(id);
    }
}
