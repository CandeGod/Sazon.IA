package com.proyecto.SazonIA.controller;

import static org.hamcrest.CoreMatchers.is;
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
public class CommentRecipeTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private CommentRecipeController commentRecipeController;

    @Test
    public void contextLoads() throws Exception {
        assertThat(commentRecipeController).isNotNull();
    }

    @Test
    public void getCommentRecipeByIdTest() throws Exception {
        int idComment = 1; // Assuming a comment recipe with ID 1 exists
        mvc.perform(get("/comment/{idComment}", idComment)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.comment_id", is(idComment)));
    }

    @Test
    public void saveCommentRecipeTest() throws Exception {
        String newCommentRecipeJson = "{"
                + "\"content\":\"Test Comment\""
                + "}";
        mvc.perform(post("/comment/{idRecipe}/{idUser}", 1, 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(newCommentRecipeJson))
                .andExpect(status().isOk());
    }

    @Test
    public void updateCommentRecipeTest() throws Exception {
        String updatedCommentRecipeJson = "{"
                + "\"comment_id\":\"4\","
                + "\"content\":\"Updated Test Comment\""
                + "}";
        mvc.perform(put("/comment/{idComment}", 4)
                .contentType(MediaType.APPLICATION_JSON)
                .content(updatedCommentRecipeJson))
                .andExpect(status().isOk());
    }

    @Test
    public void deleteCommentRecipeTest() throws Exception {
        int idComment = 4;
        mvc.perform(get("/comment/{idComment}", idComment)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

}
