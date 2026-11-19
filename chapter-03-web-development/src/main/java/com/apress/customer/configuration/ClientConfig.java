package com.apress.customer.configuration;

import com.apress.customer.client.CurrencyClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.service.registry.ImportHttpServices;

@Configuration
@ImportHttpServices(CurrencyClient.class)
public class ClientConfig {
}
