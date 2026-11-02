package com.apress.management.reactive;

import com.apress.management.dto.CustomerDetailsDTO;
import com.apress.management.service.ManagementService;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Component
public class ManagementHandlers {
    private final ManagementService managementService;

    public ManagementHandlers(ManagementService managementService) {
        this.managementService = managementService;
    }

    public Mono<ServerResponse> getCustomerDetails(ServerRequest request) {
        String id = request.pathVariable("id");
        CustomerDetailsDTO customerDetails = managementService.getCustomerDetails(UUID.fromString(id));
        if (customerDetails != null) {
            return ServerResponse.ok().bodyValue(customerDetails);
        } else {
            return ServerResponse.notFound().build();
        }
    }
}
