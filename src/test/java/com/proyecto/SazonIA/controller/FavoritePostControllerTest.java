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
        String postId = "ceda944f-db85-41e3-8af5-6adb30a5c509";

        mockMvc.perform(post("/favoritePosts/" + userId)
                .param("postId", postId)
                .param("userId", userId.toString())
                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    public void deleteFavoritePostTest() throws Exception {
        Integer userId = 4;
        String postId = "ceda944f-db85-41e3-8af5-6adb30a5c509";

        mockMvc.perform(delete("/favoritePosts/" + userId)
                .param("postId", postId)) 
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void getFavoritePostsByUserIdTest() throws Exception {
        Integer userId = 4;
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
        Integer userId = 3;
        String postId = "ceda944f-db85-41e3-8af5-6adb30a5c509";

        mockMvc.perform(get("/favoritePosts/post/" + userId)
                .param("postId", postId)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

}
