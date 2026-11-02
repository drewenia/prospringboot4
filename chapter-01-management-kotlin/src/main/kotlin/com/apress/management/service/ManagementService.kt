package com.apress.management.service

import com.apress.management.dto.CustomerDetailsDTO
import com.apress.management.model.Address
import com.apress.management.model.Communication
import com.apress.management.model.Company
import com.apress.management.model.Customer
import com.apress.management.repository.AddressRepository
import com.apress.management.repository.CommunicationRepository
import com.apress.management.repository.CompanyRepository
import com.apress.management.repository.CustomerRepository
import org.springframework.stereotype.Service
import java.util.UUID
import java.util.stream.StreamSupport

@Service
class ManagementService(
    private val addressRepository: AddressRepository,
    private val communicationRepository: CommunicationRepository,
    private val companyRepository: CompanyRepository,
    private val customerRepository: CustomerRepository
) {
    fun createCustomerWithDetails(
        address: Address,
        communication: Communication,
        company: Company,
        customer: Customer
    ): CustomerDetailsDTO {
        val savedCompany = companyRepository.save(company)

        val customerToSave = customer.copy(
            customerId = null,
            companyId = savedCompany.companyId
        )
        val savedCustomer = customerRepository.save(customerToSave)

        val addressToSave = address.copy(
            addressId = null,
            customerId = savedCustomer.customerId
        )
        val savedAddress = addressRepository.save(addressToSave)

        val communicationToSave = communication.copy(
            communicationId = null,
            customerId = savedCustomer.customerId
        )
        val savedCommunication = communicationRepository.save(communicationToSave)

        return CustomerDetailsDTO(
            customer = savedCustomer,
            company = savedCompany,
            addresses = listOf(savedAddress),
            communications = listOf(savedCommunication)
        )
    }

    fun getCustomerWithDetails(customerId: UUID): CustomerDetailsDTO? {
        val customer = customerRepository.findById(customerId) ?: return null

        val company = customer.companyId?.let { companyId ->
            companyRepository.findById(companyId)
        } ?: return null

        val addresses = StreamSupport.stream(
            this.addressRepository
                .findAllByCustomerId(customerId).spliterator(), false
        ).toList()

        val communications = StreamSupport.stream(
            this.communicationRepository
                .findAllByCustomerId(customerId).spliterator(), false
        ).toList()

        return CustomerDetailsDTO(customer, company, addresses, communications)
    }
}