package com.apress.management.reactive

import com.apress.management.service.ManagementService
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono
import java.util.UUID

@Component
class ManagementHandlers(private val managementService: ManagementService) {
    fun getCustomerDetails(request: ServerRequest): Mono<ServerResponse> {
        val id = request.pathVariable("id")
        val customerWithDetails = managementService.getCustomerWithDetails(UUID.fromString(id))

        return if (customerWithDetails != null){
            ServerResponse.ok().bodyValue(customerWithDetails)
        } else {
            ServerResponse.notFound().build()
        }
    }
}