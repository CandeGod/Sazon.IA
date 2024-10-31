package com.proyecto.SazonIA.controller;

import static org.hamcrest.CoreMatchers.is;
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
        int idComment = 1;
        mvc.perform(get("/comments/GetById")
                .param("idComment", String.valueOf(idComment))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.comment_id", is(idComment)));
    }

    @Test
    public void saveCommentRecipeTest() throws Exception {
        int idRecipe = 2;
        int idUser = 1;
        String newCommentRecipeJson = "{"
                + "\"content\":\"Test Comment\""
                + "}";
        mvc.perform(post("/comments/SaveComment")
                .param("idRecipe", String.valueOf(idRecipe))
                .param("idUser", String.valueOf(idUser))
                .contentType(MediaType.APPLICATION_JSON)
                .content(newCommentRecipeJson))
                .andExpect(status().isOk());
    }

    @Test
    public void updateCommentRecipeTest() throws Exception {
        String updatedCommentRecipeJson = "{"
                + "\"comment_id\":\"3\","
                + "\"content\":\"Updated Test Comment\""
                + "}";
        mvc.perform(put("/comments/UpdateComment")
                .param("idComment", "3")
                .contentType(MediaType.APPLICATION_JSON)
                .content(updatedCommentRecipeJson))
                .andExpect(status().isOk());
    }

    @Test
    public void deleteCommentRecipeTest() throws Exception {
        int idComment = 6;
        mvc.perform(delete("/comments/DeleteComment")
                .param( "idComment", String.valueOf(idComment))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

}
