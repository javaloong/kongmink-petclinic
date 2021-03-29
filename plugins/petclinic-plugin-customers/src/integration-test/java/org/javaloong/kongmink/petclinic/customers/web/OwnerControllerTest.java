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
package org.javaloong.kongmink.petclinic.customers.web;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.startsWith;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;

/**
 * @author Xu Cheng
 */
@DatabaseSetup({"ownerData.xml", "petData.xml"})
public class OwnerControllerTest extends MockMvcTestSupport {

    @Test
    public void initCreationForm_ShouldAddOwnerToModelAndRenderView() throws Exception {
        mockMvc.perform(get("/owners/new"))
            .andExpect(status().isOk())
            .andExpect(view().name("owners/createOrUpdateOwnerForm"))
            .andExpect(model().size(1))
            .andExpect(model().attributeExists("owner"));
    }
    
    @Test
    @ExpectedDatabase(value="createOwnerDataExpected.xml", assertionMode=DatabaseAssertionMode.NON_STRICT)
    public void processCreationForm_ShouldAddOwnerAndRedirectDetailView() throws Exception{
        mockMvc.perform(post("/owners/new")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("firstName", "fn3")
                .param("lastName", "ln3")
                .param("address", "addr3")
                .param("city", "city3")
                .param("telephone", "222222"))
            .andExpect(status().isFound())
            .andExpect(view().name(startsWith("redirect:/owners/")));
    }
    
    @Test
    public void initFindForm_ShouldAddOwnerToModelAndRenderView() throws Exception {
        mockMvc.perform(get("/owners/find"))
            .andExpect(status().isOk())
            .andExpect(view().name("owners/findOwners"))
            .andExpect(model().size(1))
            .andExpect(model().attributeExists("owner"));
    }
    
    @Test
    public void processFindForm_OwnerNotFound_ShouldReturnValidationErrors() throws Exception {
        mockMvc.perform(get("/owners")
                .param("lastName", "x"))
            .andExpect(status().isOk())
            .andExpect(view().name("owners/findOwners"))
            .andExpect(model().size(1))
            .andExpect(model().attributeExists("owner"))
            .andExpect(model().attributeHasFieldErrorCode("owner", "lastName", is("notFound")));
    }
    
    @Test
    public void processFindForm_OwnerFound_ShouldRedirectDetailView() throws Exception {
        mockMvc.perform(get("/owners")
                .param("lastName", "Franklin"))
            .andExpect(status().isFound())
            .andExpect(view().name(startsWith("redirect:/owners/")));
    }
    
    @Test
    public void processFindForm_OwnersFound_ShouldRedirectListView() throws Exception {
        mockMvc.perform(get("/owners")
                .param("lastName", ""))
            .andExpect(status().isOk())
            .andExpect(view().name("owners/ownersList"))
            .andExpect(model().attributeExists("selections"));;
    }
    
    @Test
    public void initUpdateOwnerForm_ShouldAddOwnerToModelAndRenderView() throws Exception {
        mockMvc.perform(get("/owners/1/edit"))
            .andExpect(status().isOk())
            .andExpect(view().name("owners/createOrUpdateOwnerForm"))
            .andExpect(model().size(1))
            .andExpect(model().attributeExists("owner"))
            .andExpect(model().attribute("owner", hasProperty("lastName", is("Franklin"))));
    }
    
    @Test
    public void processUpdateOwnerForm_ShouldReturnValidationErrors() throws Exception{
        mockMvc.perform(post("/owners/1/edit")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("firstName", "George")
                .param("lastName", "Franklin")
                .param("address", "110 W. Liberty St.")
                .param("city", "Madison")
                .param("telephone", "xxx"))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(view().name("owners/createOrUpdateOwnerForm"))
            .andExpect(model().attributeHasFieldErrorCode("owner", "telephone", is("Digits")));
    }
    
    @Test
    @ExpectedDatabase(value="updateOwnerDataExpected.xml", assertionMode=DatabaseAssertionMode.NON_STRICT)
    public void processUpdateOwnerForm_ShouldUpdateOwnerAndRedirectDetailView() throws Exception{
        mockMvc.perform(post("/owners/1/edit")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("firstName", "George")
                .param("lastName", "Franklin")
                .param("address", "110 W. Liberty St.")
                .param("city", "Madison")
                .param("telephone", "222222"))
            .andExpect(status().isFound())
            .andExpect(view().name(startsWith("redirect:/owners/")));
    }
    
    @Transactional
    @Test
    public void showOwner_ShouldAddOwnerToModelAndRenderView() throws Exception {
        mockMvc.perform(get("/owners/1"))
            .andExpect(status().isOk())
            .andExpect(view().name("owners/ownerDetails"))
            .andExpect(model().size(1))
            .andExpect(model().attributeExists("owner"));
    }
}
