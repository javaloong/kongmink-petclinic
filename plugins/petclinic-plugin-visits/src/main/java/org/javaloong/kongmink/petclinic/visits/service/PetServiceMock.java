package org.javaloong.kongmink.petclinic.visits.service;

import org.javaloong.kongmink.petclinic.customers.model.Owner;
import org.javaloong.kongmink.petclinic.customers.model.Pet;
import org.javaloong.kongmink.petclinic.customers.service.PetService;

/**
 * @author Xu Cheng
 */
public class PetServiceMock implements PetService {

    @Override
    public Pet findById(Integer id) {
        Pet pet = new Pet();
        Owner owner = new Owner();
        owner.setId(1);
        pet.setOwner(owner);
        return pet;
    }

}
