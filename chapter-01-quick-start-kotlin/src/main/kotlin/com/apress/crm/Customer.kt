package com.apress.crm

import java.util.UUID

data class Customer(
    val name: String,
    val email: String,
    val phone: String,
    val id: UUID = UUID.randomUUID()
)