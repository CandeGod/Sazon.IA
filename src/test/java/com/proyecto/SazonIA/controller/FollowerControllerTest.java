package com.proyecto.SazonIA.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@SpringBootTest
@AutoConfigureMockMvc
public class FollowerControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private FollowerController controller;

    // Prueba de carga de contexto
    @Test
    void contextLoads() throws Exception {
        assertThat(controller).isNotNull();
    }

    // Pruebas para followUser
    @Test
    public void followUserTest() throws Exception {
        mvc.perform(post("/follows/follow")
                .param("userId", "1")
                .param("followedId", "2")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().string(containsString("Successfully followed the user")));
    }

    @Test
    public void followUserNotFoundTest() throws Exception {
        mvc.perform(post("/follows/follow")
                .param("userId", "1")
                .param("followedId", "10")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(content().string(containsString("User not found")));
    }

    @Test
    public void followUserSelfFollowTest() throws Exception {
        mvc.perform(post("/follows/follow")
                .param("userId", "1")
                .param("followedId", "1")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("You cannot follow yourself")));
    }

    @Test
    public void followUserAlreadyFollowingTest() throws Exception {
        mvc.perform(post("/follows/follow")
                .param("userId", "1")
                .param("followedId", "2")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isConflict())
                .andExpect(content().string(containsString("You are already following this user")));
    }

    // Pruebas para unfollowUser
    @Test
    public void unfollowUserTest() throws Exception {
        mvc.perform(delete("/follows/unfollow")
                .param("userId", "1")
                .param("followedId", "2")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("You are currently not following this user")));
    }

    @Test
    public void unfollowUserNotFoundTest() throws Exception {
        mvc.perform(delete("/follows/unfollow")
                .param("userId", "1")
                .param("followedId", "10")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(content().string(containsString("User not found")));
    }

    @Test
    public void unfollowUserSelfUnfollowTest() throws Exception {
        mvc.perform(delete("/follows/unfollow")
                .param("userId", "1")
                .param("followedId", "1")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("You cannot unfollow your own account")));
    }

    @Test
    public void unfollowUserNotFollowingTest() throws Exception {
        mvc.perform(delete("/follows/unfollow")
                .param("userId", "3")
                .param("followedId", "1")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isConflict())
                .andExpect(content().string(containsString("You are currently not following this user")));
    }

    // Pruebas para getFollowers
    @Test
    public void getFollowersTest() throws Exception {
        mvc.perform(get("/follows/followers/4").accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content", hasSize(greaterThan(0))));
    }

    @Test
    public void getFollowersNotFoundTest() throws Exception {
        mvc.perform(get("/follows/followers/10").accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    // Pruebas para getFollowing
    @Test
    public void getFollowingTest() throws Exception {
        mvc.perform(get("/follows/followings/1").accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content", hasSize(greaterThan(0))));
    }

    @Test
    public void getFollowingNotFoundTest() throws Exception {
        mvc.perform(get("/follows/followings/10").accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());
    }
}
