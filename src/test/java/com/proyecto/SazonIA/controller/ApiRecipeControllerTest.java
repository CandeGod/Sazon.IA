package com.proyecto.SazonIA.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class ApiRecipeControllerTest {
    
    @Autowired
    private MockMvc mvc;

    

    @Test
    public void getRandomRecipeTest() throws Exception{
        mvc.perform(get("/randomRecipes").accept(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isOk());
    }
}
