package org.javaloong.kongmink.petclinic.vets;

import org.javaloong.kongmink.pf4j.spring.boot.SharedDataSourceSpringBootstrap;
import org.javaloong.kongmink.pf4j.spring.boot.SpringBootPlugin;
import org.javaloong.kongmink.pf4j.spring.boot.SpringBootstrap;
import org.pf4j.PluginWrapper;

/**
 * @author Xu Cheng
 */
public class VetPlugin extends SpringBootPlugin {

    public VetPlugin(PluginWrapper wrapper) {
        super(wrapper);
    }

    @Override
    protected SpringBootstrap createSpringBootstrap() {
        return new SharedDataSourceSpringBootstrap(this, VetPluginStarter.class);
    }
}
