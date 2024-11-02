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
public class CommentRecipesTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private CommentRecipeController commentRecipeController;

    @Test
    public void contextLoads() throws Exception {
        assertThat(commentRecipeController).isNotNull();
    }

    @Test
    public void getCommentRecipesByIdTest() throws Exception {
        int idComment = 1;
        mvc.perform(get("/commentsRecipes/" + idComment)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.comment_id", is(idComment)));
    }

    @Test
    public void getCommentsByRecipes() throws Exception {
        int idRecipes = 1;
        mvc.perform(get("/commentsRecipes/FromRecipe/" + idRecipes)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(greaterThan(0))));
    }

    @Test
    public void saveCommentRecipesTest() throws Exception {
        int idRecipes = 2;
        int idUser = 1;
        String newCommentRecipesJson = "{"
                + "\"content\":\"Test Comment\""
                + "}";
        mvc.perform(post("/commentsRecipes")
                .param("idRecipe", String.valueOf(idRecipes))
                .param("idUser", String.valueOf(idUser))
                .contentType(MediaType.APPLICATION_JSON)
                .content(newCommentRecipesJson))
                .andExpect(status().isOk());
    }

    @Test
    public void updateCommentRecipesTest() throws Exception {
        int idComment = 3;
        String updatedCommentRecipesJson = "{"
                + "\"comment_id\":\"3\","
                + "\"content\":\"Updated Test Comment\""
                + "}";
        mvc.perform(put("/commentsRecipes/" + idComment)
                .contentType(MediaType.APPLICATION_JSON)
                .content(updatedCommentRecipesJson))
                .andExpect(status().isOk());
    }

    @Test
    public void deleteCommentRecipesTest() throws Exception {
        int idComment = 6;
        mvc.perform(delete("/commentsRecipes/" + idComment)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

}
