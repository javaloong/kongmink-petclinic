package org.javaloong.kongmink.petclinic.customers.service;

import java.util.Collection;

import org.javaloong.kongmink.petclinic.customers.model.Visits;

/**
 * @author Xu Cheng
 */
public interface VisitService {

    Visits getVisits(Collection<Integer> petIds);
}
