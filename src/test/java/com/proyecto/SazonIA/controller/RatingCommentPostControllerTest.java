package com.proyecto.SazonIA.controller;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;



import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class RatingCommentPostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private RatingCommentPostController ratingCommentPostController;

    @Test
    public void createRatingCommentTest() throws Exception {
        String commentId = "ef823c20-d8c3-47e0-beb5-a4f2ce37e492";
        Integer userId = 3;
        Integer value = 4;

        mockMvc.perform(post("/ratings/comments")
                .param("commentId", commentId)
                .param("userId", userId.toString())
                .param("value", value.toString())
                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    public void updateRatingCommentTest() throws Exception {
        String commentId = "ef823c20-d8c3-47e0-beb5-a4f2ce37e492";
        Integer userId = 1;
        String ratingId = "0fc778d3-1749-454a-ad47-0439a0db6148";
        Integer value = 5;

        mockMvc.perform(put("/ratings/comments/" + ratingId)
                .param("ratingId", ratingId)
                .param("commentId", commentId)
                .param("userId", userId.toString())
                .param("value", value.toString())
                .param("value", value.toString())
                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void deleteRatingCommentTest() throws Exception {
        String ratingId = "ca631072-3fc3-41e8-9d65-06cadf03b0b8";

        mockMvc.perform(delete("/ratings/comments/" + ratingId))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void getRatingByIdTest() throws Exception {
        String ratingId = "0f2ed7a0-8623-462f-9fce-bba1ae31f707";

        mockMvc.perform(get("/ratings/comments/" + ratingId)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }
}
