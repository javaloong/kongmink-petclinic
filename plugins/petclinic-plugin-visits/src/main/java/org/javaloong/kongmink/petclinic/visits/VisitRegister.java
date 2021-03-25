package org.javaloong.kongmink.petclinic.visits;

import org.javaloong.kongmink.petclinic.api.extension.PluginRegister;
import org.pf4j.Extension;

/**
 * @author Xu Cheng
 */
@Extension
public class VisitRegister implements PluginRegister {

    @Override
    public String name() {
        return "visit";
    }
}
