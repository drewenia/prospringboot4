package com.apress.management.model

import java.util.UUID

data class Address (
    val addressId : UUID?,
    val customerId : UUID?,
    val street : String,
    val city : String,
    val state : String,
    val zip : String
)