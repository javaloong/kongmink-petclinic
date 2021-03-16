package org.javaloong.kongmink.petclinic.vets;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author Xu Cheng
 */
@SpringBootApplication(proxyBeanMethods = false)
public class VetPluginStarter {

    public static void main(String[] args) {
        SpringApplication.run(VetPluginStarter.class, args);
    }
}
