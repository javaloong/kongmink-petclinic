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

/**
 * @author Xu Cheng
 */
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
