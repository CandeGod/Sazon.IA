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

        mockMvc.perform(post("/commentsPost/post/dbe12ba2-7f73-43cc-b7c5-e2343032377a")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.commentId").exists())
                .andExpect(jsonPath("$.postId", is("dbe12ba2-7f73-43cc-b7c5-e2343032377a")))
                .andExpect(jsonPath("$.userId", is(1)))
                .andExpect(jsonPath("$.content", is("Este es un comentario de prueba.")));
    }

    @Test
    public void updateCommentPostTest() throws Exception {
        Integer userId = 1;
        String postId = "dbe12ba2-7f73-43cc-b7c5-e2343032377a";
        String commentId = "46f832d4-70d0-4d75-b2ef-8c0f06e8d58a";

        // Cuerpo de la solicitud con los datos actualizados del comentario
        String updateRequestBody = "{\n" +
                "  \"content\": \"Contenido actualizado del comentario.\"\n" +
                "}";

        mockMvc.perform(put("/commentsPost/user/" + userId)
                .param("postId", postId)
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
        Integer userId = 1;
        String commentId = "cbd8c073-22df-4145-baa0-3b60b1f10941";

        mockMvc.perform(delete("/commentsPost/user/" + userId)
                .param("commentId", commentId))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    public void getCommentsByPostIdPaginatedTest() throws Exception {
        String postId = "dbe12ba2-7f73-43cc-b7c5-e2343032377a";
        int page = 0;
        int pageSize = 1;

        mockMvc.perform(get("/commentsPost/post/" + postId)
                .param("page", String.valueOf(page))
                .param("pageSize", String.valueOf(pageSize))
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(pageSize))); // Verifica que el tamaño coincide con pageSize
    }
}
