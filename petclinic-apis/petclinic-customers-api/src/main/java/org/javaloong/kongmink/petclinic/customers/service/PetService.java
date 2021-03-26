package org.javaloong.kongmink.petclinic.customers.service;

import org.javaloong.kongmink.petclinic.customers.model.Pet;

public interface PetService {

    Pet findById(Integer id);
}
