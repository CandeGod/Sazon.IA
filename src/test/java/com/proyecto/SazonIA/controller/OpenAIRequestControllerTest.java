package com.proyecto.SazonIA.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
public class OpenAIRequestControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private OpenAIRequestController controller;

    // Test para verificar que el controlador se carga correctamente
    @Test
    void contextLoads() throws Exception {
        assertThat(controller).isNotNull();
    }

    // Pruebas para el método getRecommendations
    @Test
    public void getRecommendationsTest() throws Exception {
        mvc.perform(post("/api/openai/recommendations")
                .param("userId", "1")
                .param("prompt", "What is the best recipe?")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("recipe")));
    }

    @Test
    public void getRecommendationsUserNotFoundTest() throws Exception {
        mvc.perform(post("/api/openai/recommendations")
                .param("userId", "0")
                .param("prompt", "What is the best recipe?")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(content().string(containsString("User not found")));
    }

    // Pruebas para el método getAll
    @Test
    public void getAllRecommendationsTest() throws Exception {
        mvc.perform(get("/api/openai")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(greaterThan(0))));
    }

    // Pruebas para el método getHistoryByUserId
    @Test
    public void getHistoryByUserIdTest() throws Exception {
        mvc.perform(get("/api/openai/user/1")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(greaterThan(0))));
    }

    @Test
    public void getHistoryByUserIdUserNotFoundTest() throws Exception {
        mvc.perform(get("/api/openai/user/0")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(content().string(containsString("User not found")));
    }

    @Test
    public void getHistoryByUserIdNoRecommendationsTest() throws Exception {
        mvc.perform(get("/api/openai/user/2")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("No recommendations found for this user")));
    }

    // Pruebas para el método deleteHistoryById
    @Test
    public void deleteHistoryByIdTest() throws Exception {
        mvc.perform(delete("/api/openai/1")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("History deleted successfully")));
    }

    @Test
    public void deleteHistoryByIdUserNotFoundTest() throws Exception {
        mvc.perform(delete("/api/openai/0")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(content().string(containsString("User not found")));
    }
}
