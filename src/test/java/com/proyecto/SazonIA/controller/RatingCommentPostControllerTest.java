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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.is;

@SpringBootTest
@AutoConfigureMockMvc
public class RatingCommentPostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private RatingCommentPostController ratingCommentPostController;

    @Test
    public void createRatingCommentTest() throws Exception {
        String requestBody = "{\n" +
                "  \"commentId\": \"fcecfffd-b1a5-4b09-93b7-9c693008c2e4\",\n" +
                "  \"userId\": 4,\n" +
                "  \"value\": 4\n" +
                "}";

        mockMvc.perform(post("/ratings/comments")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.commentId", is("fcecfffd-b1a5-4b09-93b7-9c693008c2e4")))
                .andExpect(jsonPath("$.ratingId").exists())
                .andExpect(jsonPath("$.userId", is(4    )))
                .andExpect(jsonPath("$.value", is(4)));
    }

    @Test
    public void updateRatingCommentTest() throws Exception {
        String ratingId = "0cc74526-b2bf-4012-8bf4-abcde71b2f65";
        String updateRequestBody = "{\n" +
                "  \"value\": 5\n" +
                "}";

        mockMvc.perform(put("/ratings/comments/" + ratingId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(updateRequestBody))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.ratingId", is(ratingId)))
                .andExpect(jsonPath("$.value", is(5)));
    }

    @Test
    public void deleteRatingCommentTest() throws Exception {
        String ratingId = "7b4dbef2-c0b6-43eb-930e-9724f0ab9997";

        mockMvc.perform(delete("/ratings/comments/" + ratingId))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    public void getRatingByIdTest() throws Exception {
        String ratingId = "240d9ea4-73a4-4b07-ab08-ae34c9b5d255";

        mockMvc.perform(get("/ratings/comments/" + ratingId)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.ratingId", is(ratingId)))
                .andExpect(jsonPath("$.commentId").exists())
                .andExpect(jsonPath("$.userId").exists())
                .andExpect(jsonPath("$.value").exists());
    }
}
