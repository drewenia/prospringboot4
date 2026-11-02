package com.apress.management.configuration

import com.apress.management.model.Address
import com.apress.management.model.Communication
import com.apress.management.model.Company
import com.apress.management.model.Customer
import com.apress.management.reactive.ManagementHandlers
import com.apress.management.service.ManagementService
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.ApplicationListener
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.server.RequestPredicates
import org.springframework.web.reactive.function.server.RouterFunction
import org.springframework.web.reactive.function.server.RouterFunctions
import org.springframework.web.reactive.function.server.ServerResponse

@Configuration
class ManagementConfiguration {
    @Bean
    fun managementReady(managementService: ManagementService): ApplicationListener<ApplicationReadyEvent> {
        return ApplicationListener {
            val address = Address(
                null,
                null,
                "123 Main St.",
                "New York",
                "NY",
                "10001"
            )
            val communication = Communication(
                null,
                null,
                "email",
                "felipe.gomez@apress.com",
            )
            val company = Company(
                null,
                "Apress",
                "Publishing",
                "apress.com"
            )
            val customer = Customer(
                null,
                null,
                "Felipe",
                "Gomez",
                "Author",
                "felipe.gomez@apress.com",
                "123-456789"
            )

            managementService.createCustomerWithDetails(
                address = address,
                communication = communication,
                company = company,
                customer = customer
            )
        }
    }

    @Bean
    fun managementRoutes(handlers : ManagementHandlers) : RouterFunction<ServerResponse>{
        return RouterFunctions.route(
            RequestPredicates.GET("/api/v1/customers/{id}"),
            handlers::getCustomerDetails
        )
    }
}