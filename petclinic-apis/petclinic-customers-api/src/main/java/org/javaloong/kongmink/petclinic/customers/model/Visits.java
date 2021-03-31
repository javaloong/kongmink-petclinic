package org.javaloong.kongmink.petclinic.customers.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.format.annotation.DateTimeFormat;

public class Visits {

    private final List<Visit> items;

    public Visits(Collection<? extends Object> visits) {
        items = new ArrayList<>(visits.size());
        for (Object source : visits) {
            Visit visit = new Visit();
            BeanUtils.copyProperties(source, visit);
            items.add(visit);
        }
    }
    
    public List<Visit> get(Integer petId) {
        List<Visit> visits = items.stream().filter(v -> v.getPetId().equals(petId))
            .sorted(Comparator.comparing(Visit::getDate).reversed())
            .collect(Collectors.toList());
        return Collections.unmodifiableList(visits);
    }
    
    static class Visit {
        
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        private LocalDate date;
        
        private String description;
        
        private Integer petId;

        public LocalDate getDate() {
            return date;
        }

        public void setDate(LocalDate date) {
            this.date = date;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public Integer getPetId() {
            return petId;
        }

        public void setPetId(Integer petId) {
            this.petId = petId;
        }
    }
}
