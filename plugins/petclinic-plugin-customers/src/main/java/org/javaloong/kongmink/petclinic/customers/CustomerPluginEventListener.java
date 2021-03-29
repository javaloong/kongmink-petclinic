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
package org.javaloong.kongmink.petclinic.customers;

import org.javaloong.kongmink.petclinic.customers.model.PetType;
import org.javaloong.kongmink.petclinic.customers.web.PetTypeFormatter;
import org.javaloong.kongmink.pf4j.spring.boot.context.PluginStartedEvent;
import org.javaloong.kongmink.pf4j.spring.boot.context.PluginStoppedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.event.EventListener;
import org.springframework.format.support.FormattingConversionService;
import org.springframework.stereotype.Component;

@Component
public class CustomerPluginEventListener {

    private static final Logger logger = LoggerFactory.getLogger(CustomerPluginEventListener.class);
    
    @Autowired
    private FormattingConversionService conversionService;
    
    @EventListener(PluginStartedEvent.class)
    public void onPluginStarted(PluginStartedEvent event) {
        ApplicationContext context = (ApplicationContext)event.getSource();
        PetTypeFormatter petTypeFormatter = context.getBean(PetTypeFormatter.class);
        conversionService.addFormatter(petTypeFormatter);
        
        if (logger.isDebugEnabled()) {
            logger.debug("Add formatter '{}' to 'conversionService'", petTypeFormatter);
        }
    }
    
    @EventListener(PluginStoppedEvent.class)
    public void onPluginStopped(PluginStoppedEvent event) {
        conversionService.removeConvertible(String.class, PetType.class);
        
        if (logger.isDebugEnabled()) {
            logger.debug("Remove converter from '{}' to '{}'", String.class, PetType.class);
        }
    }
}
