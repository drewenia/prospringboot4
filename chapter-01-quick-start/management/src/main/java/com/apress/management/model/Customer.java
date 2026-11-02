package com.apress.management.model;

import java.util.UUID;

public record Customer(
        UUID customerId,
        UUID companyId,
        String firstName,
        String lastName,
        String jobTitle,
        String email,
        String phone
) {
}
