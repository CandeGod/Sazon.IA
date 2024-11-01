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
                "  \"commentId\": \"46f832d4-70d0-4d75-b2ef-8c0f06e8d58a\",\n" +
                "  \"userId\": 4,\n" +
                "  \"value\": 4\n" +
                "}";

        mockMvc.perform(post("/ratings/comments")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.commentId", is("46f832d4-70d0-4d75-b2ef-8c0f06e8d58a")))
                .andExpect(jsonPath("$.ratingId").exists())
                .andExpect(jsonPath("$.userId", is(4    )))
                .andExpect(jsonPath("$.value", is(4)));
    }

    @Test
    public void updateRatingCommentTest() throws Exception {
        String ratingId = "33b3a09b-f223-45e6-900f-7f29e554f2ae";
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
        String ratingId = "fdaabae5-3a5e-48c9-b276-b7d95ddfe528";

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
