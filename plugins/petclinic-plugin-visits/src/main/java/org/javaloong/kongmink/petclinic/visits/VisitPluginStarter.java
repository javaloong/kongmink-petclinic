package org.javaloong.kongmink.petclinic.visits;

import org.javaloong.kongmink.petclinic.customers.service.PetService;
import org.javaloong.kongmink.petclinic.visits.service.PetServiceMock;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;

/**
 * @author Xu Cheng
 */
@SpringBootApplication(proxyBeanMethods = false)
public class VisitPluginStarter {

    public static void main(String[] args) {
        SpringApplication.run(VisitPluginStarter.class, args);
    }
    
    @Bean
    @ConditionalOnMissingBean(name = "petService")
    public PetService petService() {
        return new PetServiceMock();
    }
}
