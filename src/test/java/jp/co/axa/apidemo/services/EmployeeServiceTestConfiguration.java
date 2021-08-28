package jp.co.axa.apidemo.services;

import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

/**
 * EmployeeServiceTestConfiguration
 * Allows us to inject EmployeeServiceImpl into our EmployeeServiceImplConfiguration.
 */
@Profile("test")
@Configuration
public class EmployeeServiceTestConfiguration {

    @Bean
    @Primary
    public EmployeeServiceImpl employeeServiceImpl() {
        return Mockito.mock(EmployeeServiceImpl.class);
    }
}
