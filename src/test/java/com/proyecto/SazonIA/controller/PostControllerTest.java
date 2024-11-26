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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
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
                // Datos de prueba
                int userId = 1;
                String title = "Opiniones sobre los suplementos de proteínas";
                String content = "Estoy pensando en empezar a tomar suplementos de proteínas para complementar mi dieta y mejorar mi rendimiento en el gimnasio. ¿Alguien tiene recomendaciones sobre marcas o tipos de suplementos?";

                // Realiza la solicitud POST para crear una nueva publicación con parámetros
                mockMvc.perform(post("/posts/{userId}", userId)
                                .param("title", title)
                                .param("content", content)
                                .contentType(MediaType.APPLICATION_FORM_URLENCODED)) // Define el tipo de contenido como formulario
                                .andDo(print()) // Imprime la solicitud y respuesta en la consola
                                .andExpect(status().isCreated()); // Verifica que el estado sea 201 Created
        }

        @Test
        public void getPostByIdTest() throws Exception {
                mockMvc.perform(get("/posts/7a7d9920-5dc9-4dfd-8489-83d67f38d240").accept(MediaType.APPLICATION_JSON))
                                .andDo(print())
                                .andExpect(status().isOk());
        }

        @Test
        public void updatePostTest() throws Exception {
                // ID de la publicación a actualizar
                String postId = "7a7d9920-5dc9-4dfd-8489-83d67f38d240";

                // Nuevos valores para la actualización
                String newTitle = "¿Qué opinan de los sustitutos de carne de res OK?";
                String newContent = "Últimamente he probado varios sustitutos de carne, como las hamburguesas vegetales y el tofu, y me pregunto si realmente están a la altura de la carne tradicional. ¿Algún vegetariano o vegano que pueda darme su opinión sobre los mejores sustitutos?";

                // Realiza la solicitud PUT para actualizar la publicación con parámetros
                mockMvc.perform(put("/posts/" + postId)
                                .param("title", newTitle)
                                .param("content", newContent)
                                .contentType(MediaType.APPLICATION_FORM_URLENCODED)) // Asegúrate de usar este
                                                                                     // contentType
                                .andDo(print()) // Imprime la solicitud y la respuesta en la consola
                                .andExpect(status().isOk()); // Verifica que el estado sea 200 OK
        }

        @Test
        public void deletePostTest() throws Exception {
                // ID de la publicación a eliminar
                String postId = "14ff235a-bda5-44f0-b6e4-0848c420b02f";

                // Realiza la solicitud DELETE para eliminar la publicación
                mockMvc.perform(delete("/posts/" + postId))
                                .andDo(print()) // Imprime la solicitud y la respuesta en la consola
                                .andExpect(status().isOk()); // Verifica que el estado sea 200
        }

        @Test
        public void getRandomPostsTest() throws Exception {
                mockMvc.perform(get("/posts/random?count=5").accept(MediaType.APPLICATION_JSON))
                                .andDo(print())
                                .andExpect(status().isOk());
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
                                .andExpect(status().isOk());
        }

}
