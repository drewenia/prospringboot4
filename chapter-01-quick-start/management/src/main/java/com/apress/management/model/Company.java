package com.apress.management.model;

import java.util.UUID;

public record Company(
        UUID companyId,
        String companyName,
        String industry,
        String website
) {
}
