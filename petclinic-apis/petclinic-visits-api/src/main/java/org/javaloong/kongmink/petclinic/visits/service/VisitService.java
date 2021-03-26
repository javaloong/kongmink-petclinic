package org.javaloong.kongmink.petclinic.visits.service;

import java.util.List;

import org.javaloong.kongmink.petclinic.visits.model.Visit;

public interface VisitService {

    List<Visit> findByPetId(Integer petId);
}
