package org.javaloong.kongmink.petclinic.visits.service;

import java.util.List;

import org.javaloong.kongmink.petclinic.api.extension.PluginRegister;
import org.javaloong.kongmink.petclinic.visits.model.Visit;
import org.javaloong.kongmink.petclinic.visits.repository.VisitRepository;
import org.pf4j.Extension;
import org.springframework.stereotype.Service;

@Extension
@Service("visitService")
public class VisitServiceImpl implements VisitService, PluginRegister {

    private final VisitRepository visitRepository;
    
    public VisitServiceImpl(VisitRepository visitRepository) {
        this.visitRepository = visitRepository;
    }

    @Override
    public List<Visit> findByPetId(Integer petId) {
        return visitRepository.findByPetId(petId);
    }

    @Override
    public String name() {
        return "visit";
    }

}
