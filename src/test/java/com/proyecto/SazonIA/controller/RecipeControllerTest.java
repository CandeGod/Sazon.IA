package com.proyecto.SazonIA.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;



import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureMockMvc
public class RecipeControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private RecipeController recipeController;

    @Test
    public void contextLoads() throws Exception {
        assertThat(recipeController).isNotNull();
    }

    @Test
    public void getRecipesByPagination() throws Exception {
        mvc.perform(get("/recipes/pagination")
                .param("page", "0")
                .param("pageSize", "10")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(greaterThan(0))));
    }

    @Test
    public void getRecipesFromUser() throws Exception {
        int idUser = 1;
        mvc.perform(get("/recipes/FromUser/" + idUser)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(greaterThan(0))));
    }

    
    @Test
    public void getRecipeByIdTest() throws Exception {
        int idRecipe = 1;
        mvc.perform(get("/recipes/" + idRecipe)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.recipe_id", is(idRecipe)));
    }

    @Test
    public void saveRecipeTest() throws Exception {
        String newRecipeJson = "{"
                + "\"recipe_name\":\"Test Recipe\","
                + "\"ingredients\":\"Test Ingredients\","
                + "\"instructions\":\"Test Instructions\","
                + "\"preparation_time\":\"30 minutes\","
                + "\"difficulty\":\"Easy\""
                + "}";
        int idUser = 1;
        mvc.perform(post("/recipes")
                .param("idUser", String.valueOf(idUser))
                .contentType(MediaType.APPLICATION_JSON)
                .content(newRecipeJson))
                .andExpect(status().isOk());
    }

    @Test
    public void updateRecipeTest() throws Exception {
        int idRecipe = 4;
        String updatedRecipeJson = "{"
                + "\"recipe_id\":\"4\","
                + "\"recipe_name\":\"Test updated\","
                + "\"ingredients\":\"Test updated\","
                + "\"instructions\":\"Test updated\","
                + "\"preparation_time\":\"60 minutes\","
                + "\"difficulty\":\"high\""
                + "}";
        mvc.perform(put("/recipes/" + idRecipe)
                .contentType(MediaType.APPLICATION_JSON)
                .content(updatedRecipeJson))
                .andExpect(status().isOk());
    }

    @Test
    public void deleteRecipeTest() throws Exception {
        int idRecipe = 5;
        mvc.perform(delete("/recipes/" + idRecipe)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
