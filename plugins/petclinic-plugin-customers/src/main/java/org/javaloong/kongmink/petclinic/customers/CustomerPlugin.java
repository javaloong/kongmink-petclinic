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

import org.javaloong.kongmink.pf4j.spring.boot.SharedDataSourceSpringBootstrap;
import org.javaloong.kongmink.pf4j.spring.boot.SpringBootPlugin;
import org.javaloong.kongmink.pf4j.spring.boot.SpringBootstrap;
import org.pf4j.PluginWrapper;
import org.springframework.format.support.FormattingConversionService;

/**
 * @author Xu Cheng
 */
public class CustomerPlugin extends SpringBootPlugin {

    public CustomerPlugin(PluginWrapper wrapper) {
        super(wrapper);
    }

    @Override
    protected SpringBootstrap createSpringBootstrap() {
        return new SharedDataSourceSpringBootstrap(this, CustomerPluginStarter.class)
                .importBean(FormattingConversionService.class);
    }
}
