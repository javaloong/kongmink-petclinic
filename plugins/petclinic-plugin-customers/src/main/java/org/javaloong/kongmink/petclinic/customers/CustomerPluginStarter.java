package org.javaloong.kongmink.petclinic.customers;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author Xu Cheng
 */
@SpringBootApplication(proxyBeanMethods = false)
public class CustomerPluginStarter {

    public static void main(String[] args) {
        SpringApplication.run(CustomerPluginStarter.class, args);
    }
}
