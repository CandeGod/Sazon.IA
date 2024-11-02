package com.proyecto.SazonIA.controller;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class CommentPostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private CommentPostController commentPostController;

    @Test
    public void createCommentPostTest() throws Exception {
        String requestBody = "{\n" +
                "  \"userId\": 1,\n" +
                "  \"content\": \"Este es un comentario de prueba.\"\n" +
                "}";

        mockMvc.perform(post("/commentsPost/91063c6d-e438-4fb0-8c4e-456ebd0cce99")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.commentId").exists())
                .andExpect(jsonPath("$.postId", is("91063c6d-e438-4fb0-8c4e-456ebd0cce99")))
                .andExpect(jsonPath("$.userId", is(1)))
                .andExpect(jsonPath("$.content", is("Este es un comentario de prueba.")));
    }

    @Test
    public void updateCommentPostTest() throws Exception {
        String postId = "91063c6d-e438-4fb0-8c4e-456ebd0cce99";
        String commentId = "d46304cf-6c69-495a-9348-5a6d06ca5aec";

        // Cuerpo de la solicitud con los datos actualizados del comentario
        String updateRequestBody = "{\n" +
                "  \"content\": \"Contenido actualizado del comentario.\"\n" +
                "}";

        mockMvc.perform(put("/commentsPost/" + postId)
                .param("commentId", commentId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(updateRequestBody))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.commentId", is(commentId)))
                .andExpect(jsonPath("$.content", is("Contenido actualizado del comentario.")));
    }

    @Test
    public void deleteCommentPostTest() throws Exception {
        String postId = "91063c6d-e438-4fb0-8c4e-456ebd0cce99";
        String commentId = "a3418d53-4739-4e16-bd15-ce96fe171f38";

        mockMvc.perform(delete("/commentsPost/" + postId)
                .param("commentId", commentId))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    public void getCommentsByPostIdPaginatedTest() throws Exception {
        String postId = "91063c6d-e438-4fb0-8c4e-456ebd0cce99";
        int page = 0;
        int pageSize = 1;

        mockMvc.perform(get("/commentsPost/" + postId)
                .param("page", String.valueOf(page))
                .param("pageSize", String.valueOf(pageSize))
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(pageSize))); // Verifica que el tamaño coincide con pageSize
    }
}
