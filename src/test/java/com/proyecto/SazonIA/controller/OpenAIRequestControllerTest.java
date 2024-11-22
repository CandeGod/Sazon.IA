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
import static org.hamcrest.Matchers.lessThanOrEqualTo;
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
        mvc.perform(post("/chatbot/recommendations")
                .param("user_id", "1")
                .param("prompt", "What is the best recipe?")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("recipe")));
    }

    @Test
    public void getRecommendationsUserNotFoundTest() throws Exception {
        mvc.perform(post("/chatbot/recommendations")
                .param("user_id", "10")
                .param("prompt", "What is the best recipe?")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(content().string(containsString("User with specified ID not found")));
    }

    // Pruebas para el método getAll
    // Pruebas para el método getHistoryByUserId
    @Test
public void getHistoryByUserIdWithPaginationTest() throws Exception {
    mvc.perform(get("/chatbot/history/1")
            .param("page", "0")  // Solicita la primera página
            .param("size", "5")   // Con un tamaño de página de 5 elementos
            .accept(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.content", hasSize(lessThanOrEqualTo(5)))) // Verifica que el tamaño de la página no supere 5 elementos
            .andExpect(jsonPath("$.content", hasSize(greaterThan(0)))) // Confirma que hay al menos un elemento en la respuesta
            .andExpect(jsonPath("$.totalPages", greaterThan(0))) // Asegura que hay al menos una página total
            .andExpect(jsonPath("$.totalElements", greaterThan(0))); // Verifica que hay elementos totales en la respuesta
}




    // Pruebas para el método deleteHistoryById
    @Test
    public void deleteHistoryByIdTest() throws Exception {
        mvc.perform(delete("/chatbot/history/1")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Users history deleted successfully")));
    }

    @Test
    public void deleteHistoryByIdUserNotFoundTest() throws Exception {
        mvc.perform(delete("/chatbot/history/10")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(content().string(containsString("User with specified ID not found")));
    }
}
