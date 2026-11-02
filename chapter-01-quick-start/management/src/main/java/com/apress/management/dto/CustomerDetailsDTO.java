package com.apress.management.dto;

import com.apress.management.model.Address;
import com.apress.management.model.Communication;
import com.apress.management.model.Company;
import com.apress.management.model.Customer;

import java.util.List;

public record CustomerDetailsDTO(
        Customer customer,
        Company company,
        List<Address> addresses,
        List<Communication> communications
) {
}
