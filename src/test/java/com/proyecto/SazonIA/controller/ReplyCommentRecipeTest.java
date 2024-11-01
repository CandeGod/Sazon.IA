package com.proyecto.SazonIA.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasSize;
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
    public void getRepliesIdTest() throws Exception {
        int idReply = 1;
        mvc.perform(get("/repliescomments")
                .param("idReply", String.valueOf(idReply))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.reply_id", is(idReply)));
    }

    @Test
    public void getRepliesByComment() throws Exception {
        int idComment = 1;
        mvc.perform(get("/repliescomments/FromComment")
                .param("idComment", String.valueOf(idComment))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(greaterThan(0))));
    }

    @Test
    public void saveCommentRecipeTest() throws Exception {
        String newCommentRecipeJson = "{"
                + "\"content\":\"Test Comment\""
                + "}";
        mvc.perform(post("/repliescomments")
                .param("idComment", "2")
                .param("idUser", "1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(newCommentRecipeJson))
                .andExpect(status().isOk());
    }

    @Test
    public void updateCommentRecipeTest() throws Exception {
        String updatedCommentRecipeJson = "{"
                + "\"reply_id\":\"2\","
                + "\"content\":\"Updated Test Comment\""
                + "}";
        mvc.perform(put("/repliescomments")
                .param("idReply", "2")
                .contentType(MediaType.APPLICATION_JSON)
                .content(updatedCommentRecipeJson))
                .andExpect(status().isOk());
    }

    @Test
    public void deleteCommentRecipeTest() throws Exception {
        int idReplyComment = 6;
        mvc.perform(delete("/repliescomments")
                .param("idReply", String.valueOf(idReplyComment))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
