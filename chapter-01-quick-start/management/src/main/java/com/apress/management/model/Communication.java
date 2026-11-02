package com.apress.management.model;

import java.util.UUID;

public record Communication(
        UUID communicationId,
        UUID customerId,
        String communicationType,
        String communicationValue
) {
}
