package org.javaloong.kongmink.petclinic.visits;

import org.javaloong.kongmink.pf4j.spring.boot.SharedDataSourceSpringBootstrap;
import org.javaloong.kongmink.pf4j.spring.boot.SpringBootPlugin;
import org.javaloong.kongmink.pf4j.spring.boot.SpringBootstrap;
import org.pf4j.PluginWrapper;

/**
 * @author Xu Cheng
 */
public class VisitPlugin extends SpringBootPlugin {

    public VisitPlugin(PluginWrapper wrapper) {
        super(wrapper);
    }

    @Override
    protected SpringBootstrap createSpringBootstrap() {
        return new SharedDataSourceSpringBootstrap(this, VisitPluginStarter.class);
    }
}
