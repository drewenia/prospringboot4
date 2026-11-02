package com.apress.management.model

import java.util.UUID

data class Communication(
    val communicationId : UUID?,
    val customerId : UUID?,
    val communicationType : String,
    val communicationValue : String
)
