package com.apress.management.reactive;

import com.apress.management.dto.CustomerDetailsDTO;
import com.apress.management.model.Customer;
import com.apress.management.service.ManagementService;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
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

    public Mono<ServerResponse> getCustomers(ServerRequest request){
        Flux<CustomerDetailsDTO> customersWithDetails = this.managementService.getCustomersWithDetails();
        if (customersWithDetails != null){
            return ServerResponse.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    /*.body(publisher, class): Flux gibi reaktif yayıncıları, arka planda parça parça (chunked) bir akış
                    olarak istemciye göndermek istediğinizde kullanılır.*/
                    .body(customersWithDetails,CustomerDetailsDTO.class);
        } else {
            return ServerResponse.notFound().build();
        }
    }
}
