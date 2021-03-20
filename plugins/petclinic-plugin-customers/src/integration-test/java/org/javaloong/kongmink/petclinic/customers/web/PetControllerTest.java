package org.javaloong.kongmink.petclinic.customers.web;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.startsWith;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;

@DatabaseSetup({"ownerData.xml", "petData.xml"})
public class PetControllerTest extends MockMvcTestSupport {

    @Test
    public void initCreationForm_ShouldAddPetToModelAndRenderView() throws Exception {
        mockMvc.perform(get("/owners/1/pets/new"))
            .andExpect(status().isOk())
            .andExpect(view().name("pets/createOrUpdatePetForm"))
            .andExpect(model().attributeExists("pet"));
    }
    
    @Test
    public void processCreationForm_NameExists_ShouldReturnValidationErrors() throws Exception{
        mockMvc.perform(post("/owners/1/pets/new")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("name", "Leo"))
            .andExpect(status().isOk())
            .andExpect(view().name("pets/createOrUpdatePetForm"))
            .andExpect(model().attributeExists("pet"))
            .andExpect(model().attributeHasFieldErrorCode("pet", "name", is("duplicate")));
    }
    
    @Test
    @ExpectedDatabase(value="createPetDataExpected.xml", assertionMode=DatabaseAssertionMode.NON_STRICT)
    public void processCreationForm_ShouldAddPetAndRedirectDetailView() throws Exception{
        mockMvc.perform(post("/owners/1/pets/new")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("name", "Leo2")
                .param("birthDate", "2021-01-01")
                .param("type", "cat"))
            .andExpect(status().isFound())
            .andExpect(view().name(startsWith("redirect:/owners/")));
    }
    
    @Test
    public void initUpdateForm_ShouldAddPetToModelAndRenderView() throws Exception {
        mockMvc.perform(get("/owners/1/pets/1/edit"))
            .andExpect(status().isOk())
            .andExpect(view().name("pets/createOrUpdatePetForm"))
            .andExpect(model().attributeExists("pet"))
            .andExpect(model().attribute("pet", hasProperty("name", is("Leo"))));
    }
    
    @Test
    public void processUpdateOwnerForm_ShouldReturnValidationErrors() throws Exception{
        mockMvc.perform(post("/owners/1/pets/1/edit")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("name", "")
                .param("birthDate", "2010-09-07")
                .param("type", "cat"))
            .andExpect(status().isOk())
            .andExpect(view().name("pets/createOrUpdatePetForm"))
            .andExpect(model().attributeExists("pet"))
            .andExpect(model().attributeHasFieldErrorCode("pet", "name", is("required")));
    }
    
    @Test
    @ExpectedDatabase(value="updatePetDataExpected.xml", assertionMode=DatabaseAssertionMode.NON_STRICT)
    public void processUpdateOwnerForm_ShouldUpdatePetAndRedirectDetailView() throws Exception{
        mockMvc.perform(post("/owners/1/pets/1/edit")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("id", "1")
                .param("name", "Leo")
                .param("birthDate", "2010-09-07")
                .param("type", "dog"))
            .andExpect(status().isFound())
            .andExpect(view().name(startsWith("redirect:/owners/")));
    }
}
