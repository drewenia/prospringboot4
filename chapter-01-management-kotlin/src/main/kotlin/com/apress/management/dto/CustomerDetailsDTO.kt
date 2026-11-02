package com.apress.management.dto

import com.apress.management.model.Address
import com.apress.management.model.Communication
import com.apress.management.model.Company
import com.apress.management.model.Customer

data class CustomerDetailsDTO(
    val customer : Customer,
    val company: Company,
    val addresses : List<Address>,
    val communications : List<Communication>
)
