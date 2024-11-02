package com.proyecto.SazonIA.controller;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.hamcrest.Matchers.hasSize;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class PostControllerTest {

        @Autowired
        private MockMvc mockMvc;

        @Mock
        private PostController postController;

        @Test
        public void createPostTest() throws Exception {
                // Crea el cuerpo de la solicitud como un String JSON
                String requestBody = "{\n" +
                                "  \"userId\": 1,\n" + // Asegúrate de que el userId sea un número sin comillas
                                "  \"title\": \"Título de Prueba\",\n" +
                                "  \"content\": \"Contenido de prueba para la publicación.\"\n" +
                                "}";

                // Realiza la solicitud POST para crear una nueva publicación
                mockMvc.perform(post("/posts")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(requestBody))
                                .andDo(print()) // Imprime la solicitud y respuesta en la consola
                                .andExpect(status().isCreated()) // Verifica que el estado sea 201 Created
                                .andExpect(jsonPath("$.userId", is(1))) // Verifica el userId
                                .andExpect(jsonPath("$.postId").exists()) // Verifica que el postId existe
                                .andExpect(jsonPath("$.title", is("Título de Prueba"))) // Verifica el título
                                .andExpect(jsonPath("$.content", is("Contenido de prueba para la publicación."))); // Verifica
                                                                                                                   // el
                                                                                                                   // contenido
        }

        @Test
        public void getPostByIdTest() throws Exception {
                mockMvc.perform(get("/posts/dbe12ba2-7f73-43cc-b7c5-e2343032377a").accept(MediaType.APPLICATION_JSON))
                                .andDo(print())
                                .andExpect(status().isOk())
                                .andExpect(MockMvcResultMatchers.jsonPath("$.postId",
                                                is("dbe12ba2-7f73-43cc-b7c5-e2343032377a")));
        }

        @Test
        public void updatePostTest() throws Exception {
                // ID de la publicación a actualizar
                String postId = "dbe12ba2-7f73-43cc-b7c5-e2343032377a";

                // Cuerpo de la solicitud JSON con los nuevos valores para la actualización
                String updateRequestBody = "{\n" +
                                "  \"title\": \"Título Actualizado\",\n" +
                                "  \"content\": \"Contenido actualizado de la publicación 2.\"\n" +
                                "}";

                // Realiza la solicitud PUT para actualizar la publicación
                mockMvc.perform(put("/posts/" + postId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(updateRequestBody))
                                .andDo(print()) // Imprime la solicitud y la respuesta en la consola
                                .andExpect(status().isOk()) // Verifica que el estado sea 200 OK
                                .andExpect(jsonPath("$.postId", is(postId))) // Verifica que el postId sea el mismo
                                .andExpect(jsonPath("$.title", is("Título Actualizado"))) // Verifica el nuevo título
                                .andExpect(jsonPath("$.content", is("Contenido actualizado de la publicación 2."))); // Verifica
                                                                                                                   // el
                                                                                                                   // nuevo
                                                                                                                   // contenido
        }

        @Test
        public void deletePostTest() throws Exception {
                // ID de la publicación a eliminar
                String postId = "80223b41-2f91-4a25-a6cb-47fceed82950";

                // Realiza la solicitud DELETE para eliminar la publicación
                mockMvc.perform(delete("/posts/" + postId))
                                .andDo(print()) // Imprime la solicitud y la respuesta en la consola
                                .andExpect(status().isNoContent()); // Verifica que el estado sea 204 No Content
        }

        @Test
        public void getRandomPostsTest() throws Exception {
                mockMvc.perform(get("/posts/random?count=5").accept(MediaType.APPLICATION_JSON))
                                .andDo(print())
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$", hasSize(5))); // Verifica que se devuelven 5 publicaciones
                                                                       // aleatorias
        }

        @Test
        public void getPostsByUserPaginatedTest() throws Exception {
                int userId = 2;
                int page = 0;
                int pageSize = 2;

                mockMvc.perform(get("/posts/user/" + userId)
                                .param("page", String.valueOf(page))
                                .param("pageSize", String.valueOf(pageSize))
                                .accept(MediaType.APPLICATION_JSON))
                                .andDo(print())
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$", hasSize(pageSize))); // Verifica que el tamaño de la página
                                                                              // coincide con pageSize
        }

}
