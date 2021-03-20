package org.javaloong.kongmink.petclinic.customers;

import org.javaloong.kongmink.petclinic.api.extension.PluginRegister;
import org.pf4j.Extension;

/**
 * @author Xu Cheng
 */
@Extension
public class CustomerRegister implements PluginRegister {

    @Override
    public String name() {
        return "customer";
    }
}
