package com.apress.management.service;

import com.apress.management.dto.CustomerDetailsDTO;
import com.apress.management.model.Address;
import com.apress.management.model.Communication;
import com.apress.management.model.Company;
import com.apress.management.model.Customer;
import com.apress.management.repository.AddressRepository;
import com.apress.management.repository.CommunicationRepository;
import com.apress.management.repository.CompanyRepository;
import com.apress.management.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.StreamSupport;

@Service
public class ManagementService {
    private final AddressRepository addressRepository;
    private final CommunicationRepository communicationRepository;
    private final CompanyRepository companyRepository;
    private final CustomerRepository customerRepository;

    public ManagementService(
            AddressRepository addressRepository,
            CommunicationRepository communicationRepository,
            CompanyRepository companyRepository,
            CustomerRepository customerRepository) {

        this.addressRepository = addressRepository;
        this.communicationRepository = communicationRepository;
        this.companyRepository = companyRepository;
        this.customerRepository = customerRepository;
    }

    public CustomerDetailsDTO createCustomerWithDetails(
            Address address,
            Communication communication,
            Company company,
            Customer customer
    ) {
        Company savedCompany = this.companyRepository.save(company);

        Customer customerToSaved = new Customer(
                null,
                savedCompany.companyId(),
                customer.firstName(),
                customer.lastName(),
                customer.jobTitle(),
                customer.email(),
                customer.phone()
        );
        Customer savedCustomer = this.customerRepository.save(customerToSaved);

        Address addressToSaved = new Address(
                null,
                savedCustomer.customerId(),
                address.street(),
                address.city(),
                address.state(),
                address.zip()
        );
        Address savedAddress = this.addressRepository.save(addressToSaved);

        Communication communicationToSaved = new Communication(
                null,
                savedCustomer.customerId(),
                communication.communicationType(),
                communication.communicationValue()
        );
        Communication savedCommunication = this.communicationRepository.save(communicationToSaved);

        return new CustomerDetailsDTO(savedCustomer, savedCompany, List.of(savedAddress), List.of(savedCommunication));
    }

    public CustomerDetailsDTO getCustomerDetails(UUID customerId) {
        Customer customer = this.customerRepository.findById(customerId);
        if (customer == null) {
            return null;
        }

        Company company = this.companyRepository.findById(customer.companyId());

        List<Address> addresses = StreamSupport.stream(
                this.addressRepository.findAllByCustomerId(customerId)
                        .spliterator(), false).toList();

        List<Communication> communications = StreamSupport.stream(
                this.communicationRepository.findAllByCustomerId(customerId)
                        .spliterator(), false).toList();

        return new CustomerDetailsDTO(customer, company, addresses, communications);
    }
}
