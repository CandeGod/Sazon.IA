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
//import static org.hamcrest.Matchers.is;
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
                int userId = 1;
                String content = "Este es un comentario de prueba.";

                mockMvc.perform(post("/commentsPost/7a7d9920-5dc9-4dfd-8489-83d67f38d240")
                                .param("userId", String.valueOf(userId)) // Enviar userId como parámetro en la URL
                                .param("content", content) // Enviar content como parámetro en la URL
                                .contentType(MediaType.APPLICATION_FORM_URLENCODED)) // Tipo de contenido: formulario
                                .andDo(print())
                                .andExpect(status().isCreated());
        }

        @Test
        public void updateCommentPostTest() throws Exception {
                String postId = "7a7d9920-5dc9-4dfd-8489-83d67f38d240";
                String commentId = "0659a023-da1b-402d-8846-aad4445de9a7";
                String content = "Contenido actualizado del comentario.";

                mockMvc.perform(put("/commentsPost/" + postId)
                                .param("commentId", commentId) // Enviar commentId como parámetro en la URL
                                .param("content", content) // Enviar contenido como parámetro en la URL
                                .contentType(MediaType.APPLICATION_FORM_URLENCODED)) // Tipo de contenido: formulario
                                .andDo(print())
                                .andExpect(status().isOk());
        }

        @Test
        public void deleteCommentPostTest() throws Exception {
                String postId = "7a7d9920-5dc9-4dfd-8489-83d67f38d240";
                String commentId = "9576a9e1-151f-4a8e-9799-90dd7ff4d436";

                mockMvc.perform(delete("/commentsPost/" + postId)
                                .param("commentId", commentId))
                                .andDo(print())
                                .andExpect(status().isOk());
        }

        @Test
        public void getCommentsByPostIdPaginatedTest() throws Exception {
                String postId = "7a7d9920-5dc9-4dfd-8489-83d67f38d240";
                int page = 0;
                int pageSize = 1;

                mockMvc.perform(get("/commentsPost/" + postId)
                                .param("page", String.valueOf(page))
                                .param("pageSize", String.valueOf(pageSize))
                                .accept(MediaType.APPLICATION_JSON))
                                .andDo(print())
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$", hasSize(pageSize)));
        }
}
