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
public class RatingPostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private RatingPostController ratingPostController;

    @Test
    public void createRatingPostTest() throws Exception {

        String postId = "8e5ed664-c557-4adf-9a20-0f74c69d4c99";
        Integer userId = 2;
        Integer value = 4;

        mockMvc.perform(post("/ratings")
                .param("postId", postId)
                .param("userId", userId.toString())
                .param("value", value.toString())
                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    public void updateRatingPostTest() throws Exception {
        String postId = "8e5ed664-c557-4adf-9a20-0f74c69d4c99";
        Integer userId = 1;
        String ratingId = "254ae290-e000-47de-a1c5-4c17dff1d03b";
        Integer value = 5;

        mockMvc.perform(put("/ratings/" + ratingId)
                .param("ratingId", ratingId)
                .param("postId", postId)
                .param("userId", userId.toString())
                .param("value", value.toString())
                .param("value", value.toString())
                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void deleteRatingPostTest() throws Exception {
        String ratingId = "b290770f-ede3-4a32-95d8-a92fb468b491";

        mockMvc.perform(delete("/ratings/" + ratingId))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void getRatingByIdTest() throws Exception {
        String ratingId = "53ee049a-340a-451e-9ab7-826a599cff88";

        mockMvc.perform(get("/ratings/" + ratingId)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }
}
