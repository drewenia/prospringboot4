package com.apress.customer.controller;

import com.apress.customer.model.Customer;
import com.apress.customer.repository.Repository;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/customers")
public class CustomerController {
    private final Repository<Customer, UUID> repository;

    public CustomerController(Repository<Customer, UUID> repository) {
        this.repository = repository;
    }

    @GetMapping
    Iterable<Customer> findAll() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Customer> findById(@PathVariable UUID id) {
        Customer customer = repository.findById(id);
        return customer.id() != null ? ResponseEntity.ok(customer) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Customer> create(@Valid @RequestBody Customer customer) {
        Customer saved = repository.save(customer);

        /* ServletUriComponentsBuilder...: Yeni oluşturulan resource'a erişim adresini (URI) dinamik olarak üretir.
        Örneğin istek http://localhost:8080/customers adresine yapıldıysa ve saved.id() değeri 123 ise:

        fromCurrentRequest() -> http://localhost:8080/customers alır.
        .path("/{id}") -> sonuna /{id} ekler.
        .buildAndExpand(saved.id()) -> {id} yerine 123 yazar.
        .toUri() -> URI nesnesine çevirir (http://localhost:8080/customers/123).*/
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(saved.id())
                .toUri();

        /* Response header'ina Location: http://localhost:8080/customers/123 bilgisini ekler (REST API'lerde yeni
        kaynağın adresini client'a bildirmek yaygın ve standart bir pratiktir). */
        return ResponseEntity.created(location).body(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Customer> update(@PathVariable UUID id, @Valid @RequestBody Customer customer) {
        Customer existing = repository.findById(id);
        if (existing == null) {
            return ResponseEntity.notFound().build();
        }
        Customer toUpdate = new Customer(id, customer.name(), customer.email(), customer.phone());
        repository.save(toUpdate);
        return ResponseEntity.ok(toUpdate);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        if (repository.findById(id) == null) {
            return ResponseEntity.notFound().build();
        }
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
