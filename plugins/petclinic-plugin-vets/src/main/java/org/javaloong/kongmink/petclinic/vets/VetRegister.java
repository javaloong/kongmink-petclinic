package org.javaloong.kongmink.petclinic.vets;

import org.javaloong.kongmink.petclinic.api.extension.PluginRegister;
import org.pf4j.Extension;

/**
 * @author Xu Cheng
 */
@Extension
public class VetRegister implements PluginRegister {

    @Override
    public String name() {
        return "vet";
    }
}
