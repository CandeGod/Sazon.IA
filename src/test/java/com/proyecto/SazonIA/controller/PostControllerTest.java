package com.proyecto.SazonIA.controller;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.proyecto.SazonIA.service.PostService;

import com.proyecto.SazonIA.model.Post;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;

@WebMvcTest(PostController.class)
public class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private PostService postService;

    @InjectMocks
    private PostController postController; // Opcional, a veces no es necesario.

    private Post post; // Asumiendo que tienes una clase Post.

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // Inicializa los mocks.
        post = new Post(); // Inicializa el objeto Post.
        post.setTitle("Test Title"); // Establece un título de prueba.
        // Puedes inicializar otros campos del objeto post según sea necesario.
    }

    @Test
    void createPost_shouldReturnCreatedPost() throws Exception {
        when(postService.createPost(any(Integer.class), any(String.class), any(String.class), any()))
                .thenReturn(post);

        mockMvc.perform(post("/posts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(post)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value("Test Title"));
    }

    @Test
    void getPostById_shouldReturnPost() throws Exception {
        when(postService.getPostById(any())).thenReturn(Optional.of(post));

        mockMvc.perform(get("/posts/{postId}", "00644766-7153-4559-8870-6f4cfba2029b"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Test Title"));
    }
}
