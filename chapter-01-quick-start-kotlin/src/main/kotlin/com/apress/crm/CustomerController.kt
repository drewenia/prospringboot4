package com.apress.crm

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@RequestMapping("/api/v1/customers")
class CustomerController(private val repository: Repository<Customer, UUID>) {

    @GetMapping("/{id}")
    fun findById(@PathVariable id: UUID): ResponseEntity<Customer> {
        return repository.findById(id)?.let { customer ->
            ResponseEntity.ok(customer)
        } ?: ResponseEntity.notFound().build()
    }

    @GetMapping
    fun findAll(): Iterable<Customer> {
        return repository.findAll()
    }

    @PostMapping
    fun save(@RequestBody customer: Customer): Customer {
        return repository.save(customer)
    }

    @DeleteMapping("/{id}")
    fun deleteById(@PathVariable id: UUID) : ResponseEntity<Void> {
        repository.deleteById(id);
        return ResponseEntity.noContent().build()
    }
}
