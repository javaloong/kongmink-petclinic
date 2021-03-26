package org.javaloong.kongmink.petclinic.visits.web;

import static org.hamcrest.CoreMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;

@DatabaseSetup({"visitData.xml"})
public class VisitControllerTest extends MockMvcTestSupport {

    @Test
    public void initNewVisitForm_ShouldAddPetToModelAndRenderView() throws Exception {
        mockMvc.perform(get("/owners/1/pets/1/visits/new"))
            .andExpect(status().isOk())
            .andExpect(view().name("visits/createOrUpdateVisitForm"))
            .andExpect(model().attributeExists("pet"));
    }
    
    @Test
    public void processNewVisitForm_ShouldReturnValidationErrors() throws Exception{
        mockMvc.perform(post("/owners/1/pets/1/visits/new")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("date", "2020-01-01"))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(view().name("visits/createOrUpdateVisitForm"))
            .andExpect(model().attributeExists("pet"))
            .andExpect(model().attributeHasFieldErrorCode("visit", "description", is("NotEmpty")));
    }
    
    @Test
    @ExpectedDatabase(value="createVisitDataExpected.xml", assertionMode=DatabaseAssertionMode.NON_STRICT)
    public void processNewVisitForm_ShouldAddVisitAndRedirectDetailView() throws Exception{
        mockMvc.perform(post("/owners/1/pets/1/visits/new")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("date", "2020-01-01")
                .param("description", "hello")
                .param("petId", "1"))
            .andExpect(status().isFound())
            .andExpect(view().name(startsWith("redirect:/owners/")));
    }
}
