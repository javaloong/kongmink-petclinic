package org.javaloong.kongmink.petclinic.customers.service;

import java.util.ArrayList;
import java.util.List;

import org.javaloong.kongmink.petclinic.visits.model.Visit;
import org.javaloong.kongmink.petclinic.visits.service.VisitService;

/**
 * @author Xu Cheng
 */
public class VisitServiceMock implements VisitService {

    @Override
    public List<Visit> findByPetId(Integer petId) {
        return new ArrayList<>();
    }

}
