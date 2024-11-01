package com.proyecto.SazonIA.controller;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;



import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.is;

@SpringBootTest
@AutoConfigureMockMvc
public class FavoritePostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private FavoritePostController favoritePostController;

    @Test
    public void createFavoritePostTest() throws Exception {
        Integer userId = 4;
        String requestBody = "{\n" +
                "  \"id\": {\n" +
                "    \"postId\": \"dbe12ba2-7f73-43cc-b7c5-e2343032377a\"\n" +
                "  }\n" +
                "}";

        mockMvc.perform(post("/favoritePosts/" + userId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id.userId", is(userId)))
                .andExpect(jsonPath("$.id.postId", is("dbe12ba2-7f73-43cc-b7c5-e2343032377a")))
                .andExpect(jsonPath("$.createdAt").exists());
    }

    @Test
    public void deleteFavoritePostTest() throws Exception {
        Integer userId = 4;
        String postId = "dbe12ba2-7f73-43cc-b7c5-e2343032377a";

        mockMvc.perform(delete("/favoritePosts/" + userId)
                .param("postId", postId)) // Eliminar el paréntesis extra aquí
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    public void getFavoritePostsByUserIdTest() throws Exception {
        Integer userId = 3;
        int page = 0;
        int size = 1;

        mockMvc.perform(get("/favoritePosts/" + userId)
                .param("page", String.valueOf(page))
                .param("size", String.valueOf(size))
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(size)));
    }

    @Test
    public void getContentFavoritePostByUserAndPostIdTest() throws Exception {
        Integer userId = 2;
        String postId = "dbe12ba2-7f73-43cc-b7c5-e2343032377a";

        mockMvc.perform(get("/favoritePosts/post/" + userId)
                .param("postId", postId)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.postId", is(postId)))
                .andExpect(jsonPath("$.userId").exists())
                .andExpect(jsonPath("$.title").exists())
                .andExpect(jsonPath("$.content").exists())
                .andExpect(jsonPath("$.postDate").exists())
                .andExpect(jsonPath("$.ratingAverage").exists());
    }

}
