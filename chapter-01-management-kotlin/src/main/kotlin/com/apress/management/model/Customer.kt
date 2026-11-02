package com.apress.management.model

import java.util.UUID

data class Customer(
    val customerId: UUID?,
    val companyId: UUID?,
    val firstName: String,
    val lastName: String,
    val jobTitle: String,
    val email: String,
    val phone: String
)
