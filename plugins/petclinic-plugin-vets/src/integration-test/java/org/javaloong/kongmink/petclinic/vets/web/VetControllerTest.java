package org.javaloong.kongmink.petclinic.vets.web;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.hasItems;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import com.github.springtestdbunit.annotation.DatabaseSetup;

public class VetControllerTest extends MockMvcTestSupport {

    @Test
    @DatabaseSetup("vetData.xml")
    public void showVetList_ShouldAddVetsToModelAndRenderListView() throws Exception{
        mockMvc.perform(get("/vets.html"))
            .andExpect(status().isOk())
            .andExpect(view().name("vets/vetList"))
            .andExpect(model().attribute("vets", hasProperty("vetList", hasSize(3))))
            .andExpect(model().attribute("vets", hasProperty("vetList", hasItems(
                            allOf(hasProperty("id", is(1)), hasProperty("firstName", is("James")),
                                    hasProperty("lastName", is("Carter"))),
                            allOf(hasProperty("id", is(2)), hasProperty("firstName", is("Helen"))),
                            allOf(hasProperty("id", is(3)), hasProperty("firstName", is("Linda"))))
            )));
    }
    
    @Test
    @DatabaseSetup("vetData.xml")
    public void showResourcesVetList__ShouldReturnVetResources() throws Exception{
        mockMvc.perform(get("/vets"))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.vetList.length()", is(3)));
    }
}
