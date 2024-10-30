package com.proyecto.SazonIA.controller;

import static org.assertj.core.api.Assertions.assertThat;
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

@SpringBootTest
@AutoConfigureMockMvc
public class ReplyCommentRecipeTest {
    
    @Autowired
    private MockMvc mvc;

    @Autowired
    private ReplyCommentRecipeController replyCommentRecipeController;

    @Test
    public void contextLoads() throws Exception {
        assertThat(replyCommentRecipeController).isNotNull();
    }

    @Test
    public void getCommentRecipeByIdTest() throws Exception {
        int idReply = 2;
        mvc.perform(get("/replycomment/{idReply}", idReply)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.reply_id", is(idReply)));
    }

    @Test
    public void saveCommentRecipeTest() throws Exception {
        String newCommentRecipeJson = "{"
                + "\"content\":\"Test Comment\""
                + "}";
        mvc.perform(post("/replycomment/{idRecipe}/{idUser}", 1, 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(newCommentRecipeJson))
                .andExpect(status().isOk());
    }

    @Test
    public void updateCommentRecipeTest() throws Exception {
        String updatedCommentRecipeJson = "{"
                + "\"reply_id\":\"3\","
                + "\"content\":\"Updated Test Comment\""
                + "}";
        mvc.perform(put("/replycomment/{idComment}", 3)
                .contentType(MediaType.APPLICATION_JSON)
                .content(updatedCommentRecipeJson))
                .andExpect(status().isOk());
    }

    @Test
    public void deleteCommentRecipeTest() throws Exception {
        int idReplyComment = 4;
        mvc.perform(get("/replycomment/{idComment}", idReplyComment)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
