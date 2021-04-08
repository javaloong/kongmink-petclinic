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
package org.javaloong.kongmink.petclinic.app.config;

import java.time.Duration;
import java.util.Map;

import org.javaloong.kongmink.pf4j.spring.boot.SpringBootPlugin;
import org.javaloong.kongmink.pf4j.spring.util.ApplicationContextProvider;
import org.pf4j.PluginManager;
import org.pf4j.PluginState;
import org.pf4j.PluginWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.thymeleaf.ThymeleafProperties;
import org.springframework.boot.autoconfigure.web.WebProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.CacheControl;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.WebJarsResourceResolver;
import org.thymeleaf.IEngineConfiguration;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring5.templateresource.SpringResourceTemplateResource;
import org.thymeleaf.templateresource.ITemplateResource;

@Configuration(proxyBeanMethods = false)
public class WebMvcConfig implements WebMvcConfigurer {

    private final WebProperties webProperties;

    WebMvcConfig(WebProperties webProperties) {
        this.webProperties = webProperties;
    }
    
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        Duration cachePeriod = this.webProperties.getResources().getCache().getPeriod();
        CacheControl cacheControl = this.webProperties.getResources().getCache().getCachecontrol().toHttpCacheControl();
        registry.addResourceHandler("/webjars/**")
            .addResourceLocations("classpath:/META-INF/resources/webjars/")
            .setCachePeriod(getSeconds(cachePeriod)).setCacheControl(cacheControl)
            .resourceChain(true).addResolver(new WebJarsResourceResolver());
    }
    
    private Integer getSeconds(Duration cachePeriod) {
        return (cachePeriod != null) ? (int) cachePeriod.getSeconds() : null;
    }
    
    @Configuration(proxyBeanMethods = false)
    static class ThymeleafConfig {
        
        private final ThymeleafProperties properties;

        private final ApplicationContext applicationContext;

        ThymeleafConfig(ThymeleafProperties properties, ApplicationContext applicationContext) {
            this.properties = properties;
            this.applicationContext = applicationContext;
        }
        
        @Bean
        SpringResourceTemplateResolver defaultTemplateResolver() {
            PluginResourceTemplateResolver resolver = new PluginResourceTemplateResolver();
            resolver.setApplicationContext(this.applicationContext);
            resolver.setPrefix(this.properties.getPrefix());
            resolver.setSuffix(this.properties.getSuffix());
            resolver.setTemplateMode(this.properties.getMode());
            if (this.properties.getEncoding() != null) {
                resolver.setCharacterEncoding(this.properties.getEncoding().name());
            }
            resolver.setCacheable(this.properties.isCache());
            Integer order = this.properties.getTemplateResolverOrder();
            if (order != null) {
                resolver.setOrder(order);
            }
            resolver.setCheckExistence(this.properties.isCheckTemplate());
            return resolver;
        }
    }
    
    static class PluginResourceTemplateResolver extends SpringResourceTemplateResolver {

        @Autowired @Lazy
        private PluginManager pluginManager;
        
        @Override
        protected ITemplateResource computeTemplateResource(IEngineConfiguration configuration,
                String ownerTemplate, String template, String resourceName,
                String characterEncoding, Map<String, Object> templateResolutionAttributes) {
            
            // pluginManager might not be auto wired correctly because resolve bean
            // is instantiated before PluginManager.
            if (pluginManager == null) {
                pluginManager = ApplicationContextProvider.getBean(PluginManager.class);
            }
            
            for (PluginWrapper wrapper : pluginManager.getPlugins(PluginState.STARTED)) {
                SpringBootPlugin plugin = (SpringBootPlugin) wrapper.getPlugin();
                SpringResourceTemplateResource resource = new SpringResourceTemplateResource(
                        plugin.getApplicationContext(), resourceName, characterEncoding);
                if(resource.exists()) {
                    return resource;
                }
            }
            return super.computeTemplateResource(configuration, ownerTemplate, template, resourceName,
                    characterEncoding, templateResolutionAttributes);
        }
    }
}
