package com.apress.management.model

import java.util.UUID

data class Company(
    val companyId : UUID?,
    val companyName : String,
    val industry : String,
    val website : String
)
