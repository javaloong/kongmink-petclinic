/*
 * Copyright (C) 2020-present the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.javaloong.kongmink.petclinic.visits.service;

import java.util.Collection;
import java.util.List;

import org.javaloong.kongmink.petclinic.api.extension.PluginRegister;
import org.javaloong.kongmink.petclinic.customers.model.Visits;
import org.javaloong.kongmink.petclinic.customers.service.VisitService;
import org.javaloong.kongmink.petclinic.visits.model.Visit;
import org.javaloong.kongmink.petclinic.visits.repository.VisitRepository;
import org.pf4j.Extension;
import org.springframework.stereotype.Service;

/**
 * @author Xu Cheng
 */
@Extension
@Service("visitService")
public class VisitServiceImpl implements VisitService, PluginRegister {

    private final VisitRepository visitRepository;
    
    public VisitServiceImpl(VisitRepository visitRepository) {
        this.visitRepository = visitRepository;
    }

    public Visits getVisits(Collection<Integer> petIds) {
        List<Visit> items = visitRepository.findByPetIdIn(petIds);
        return new Visits(items);
    }
    
    @Override
    public String name() {
        return "visit";
    }
}
