package org.javaloong.kongmink.petclinic.customers;

import org.javaloong.kongmink.pf4j.spring.boot.SharedDataSourceSpringBootstrap;
import org.javaloong.kongmink.pf4j.spring.boot.SpringBootPlugin;
import org.javaloong.kongmink.pf4j.spring.boot.SpringBootstrap;
import org.pf4j.PluginWrapper;
import org.springframework.format.support.FormattingConversionService;

/**
 * @author Xu Cheng
 */
public class CustomerPlugin extends SpringBootPlugin {

    public CustomerPlugin(PluginWrapper wrapper) {
        super(wrapper);
    }

    @Override
    protected SpringBootstrap createSpringBootstrap() {
        return new SharedDataSourceSpringBootstrap(this, CustomerPluginStarter.class)
                .importBean(FormattingConversionService.class);
    }
}
