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
public class RatingPostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private RatingPostController ratingPostController;

    @Test
    public void createRatingPostTest() throws Exception {
        String requestBody = "{\n" +
                "  \"postId\": \"288295b2-8c8f-4c87-93d6-da3925d322af\",\n" +
                "  \"userId\": 4,\n" +
                "  \"value\": 4\n" +
                "}";

        mockMvc.perform(post("/ratings")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.postId", is("288295b2-8c8f-4c87-93d6-da3925d322af")))
                .andExpect(jsonPath("$.ratingId").exists()) // Verifica que el raiting existe
                .andExpect(jsonPath("$.userId", is(4)))
                .andExpect(jsonPath("$.value", is(4)));
    }

    @Test
    public void updateRatingPostTest() throws Exception {
        String ratingId = "6137921f-45af-4028-8447-25a8750d957a";
        String updateRequestBody = "{\n" +
                "  \"value\": 5\n" +
                "}";

        mockMvc.perform(put("/ratings/" + ratingId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(updateRequestBody))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.ratingId", is(ratingId)))
                .andExpect(jsonPath("$.value", is(5)));
    }

    @Test
    public void deleteRatingPostTest() throws Exception {
        String ratingId = "e6b4a091-6497-40b5-9332-218c936695ac";

        mockMvc.perform(delete("/ratings/" + ratingId))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    public void getRatingByIdTest() throws Exception {
        String ratingId = "6137921f-45af-4028-8447-25a8750d957a";

        mockMvc.perform(get("/ratings/" + ratingId)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.ratingId", is(ratingId)))
                .andExpect(jsonPath("$.postId").exists())
                .andExpect(jsonPath("$.userId").exists())
                .andExpect(jsonPath("$.value").exists());
    }
}
