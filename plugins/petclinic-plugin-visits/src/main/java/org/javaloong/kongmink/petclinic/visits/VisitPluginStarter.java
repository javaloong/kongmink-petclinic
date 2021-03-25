package org.javaloong.kongmink.petclinic.visits;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author Xu Cheng
 */
@SpringBootApplication(proxyBeanMethods = false)
public class VisitPluginStarter {

    public static void main(String[] args) {
        SpringApplication.run(VisitPluginStarter.class, args);
    }
}
