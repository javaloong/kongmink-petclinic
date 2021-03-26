package org.javaloong.kongmink.petclinic.customers.service;

import org.javaloong.kongmink.petclinic.api.extension.PluginRegister;
import org.javaloong.kongmink.petclinic.customers.model.Pet;
import org.javaloong.kongmink.petclinic.customers.repository.PetRepository;
import org.pf4j.Extension;
import org.springframework.stereotype.Service;

@Extension
@Service("petService")
public class PetServiceImpl implements PetService, PluginRegister {

    private final PetRepository petRepository;
    
    public PetServiceImpl(PetRepository petRepository) {
        this.petRepository = petRepository;
    }

    @Override
    public Pet findById(Integer id) {
        return petRepository.findById(id);
    }

    @Override
    public String name() {
        return "pet";
    }

}
