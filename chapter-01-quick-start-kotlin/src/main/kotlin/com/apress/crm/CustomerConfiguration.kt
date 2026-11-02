package com.apress.crm

import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.util.*

@Configuration
class CustomerConfiguration {
    @Bean
    fun initializer(repository: Repository<Customer, UUID>): CommandLineRunner {
        return CommandLineRunner {
            repository.save(Customer("John", "john@example.com", "1111-PHONE"))
            repository.save(Customer("Jack", "jack@example.com", "2222-PHONE"))
            repository.save(Customer("Ori", "ori@example.com", "3333-PHONE"))
            repository.findAll().forEach(::println)
        }
    }

//    @Bean
//    fun initializer2(repository: Repository<Customer, UUID>) = ApplicationListener<ApplicationReadyEvent> {
//        repository.save(Customer("John", "john@example.com", "1111-PHONE"))
//        repository.save(Customer("Jack", "jack@example.com", "2222-PHONE"))
//        repository.save(Customer("Ori", "ori@example.com", "3333-PHONE"))
//        repository.findAll().forEach(::println)
//    }
}