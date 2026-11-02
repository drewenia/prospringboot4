package com.apress.management.model;

import java.util.UUID;

public record Address(
        UUID addressId,
        UUID customerId,
        String street,
        String city,
        String state,
        String zip
) {
}
