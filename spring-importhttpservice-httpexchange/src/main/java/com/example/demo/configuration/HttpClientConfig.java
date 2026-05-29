package com.example.demo.configuration;

import com.example.demo.service.TodoExchangeService;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.service.registry.ImportHttpServices;

@Configuration(proxyBeanMethods = false)
@ImportHttpServices(TodoExchangeService.class)
public class HttpClientConfig {
}
