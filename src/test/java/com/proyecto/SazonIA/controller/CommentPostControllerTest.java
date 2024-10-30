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

        mockMvc.perform(post("/commentsPost/post/9a3b8339-4eec-40da-bfd1-c6d172449010")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.commentId").exists())
                .andExpect(jsonPath("$.postId", is("9a3b8339-4eec-40da-bfd1-c6d172449010")))
                .andExpect(jsonPath("$.userId", is(1)))
                .andExpect(jsonPath("$.content", is("Este es un comentario de prueba.")));
    }

    @Test
    public void updateCommentPostTest() throws Exception {
        Integer userId = 1;
        String postId = "9a3b8339-4eec-40da-bfd1-c6d172449010";
        String commentId = "8181903e-711c-4511-bd60-648c0317b633";

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
        String commentId = "c79b0f9c-e5b6-444e-97ef-5e3ded06bb28";

        mockMvc.perform(delete("/commentsPost/user/" + userId)
                .param("commentId", commentId))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    public void getCommentsByPostIdPaginatedTest() throws Exception {
        String postId = "9a3b8339-4eec-40da-bfd1-c6d172449010";
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
