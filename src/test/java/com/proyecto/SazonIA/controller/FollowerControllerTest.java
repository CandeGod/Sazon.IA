package com.proyecto.SazonIA.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
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


    //Pruebas para followUser
    @Test
    void contextLoads() throws Exception {
        assertThat(controller).isNotNull();
    }

    @Test
    public void followUserTest() throws Exception {
        mvc.perform(post("/followers/follow/1/2").accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().string(containsString("User followed successfully")));
    }

    @Test
    public void followUserNotFoundTest() throws Exception {
        mvc.perform(post("/followers/follow/1/0").accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(content().string(containsString("User not found")));
    }

    @Test
    public void followUserSelfFollowTest() throws Exception {
        mvc.perform(post("/followers/follow/1/1").accept(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isBadRequest())
        .andExpect(content().string(containsString("You cannot follow yourself")));
    }

    @Test
    public void followUserAlreadyFollowingTest() throws Exception {
        mvc.perform(post("/followers/follow/1/2").accept(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isConflict())
        .andExpect(content().string(containsString("You are already following this user")));
    }

    //Pruebas para unfollowUser
    @Test
    public void unfollowUserTest() throws Exception {
        mvc.perform(delete("/followers/unfollow/1/2").accept(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(content().string(containsString("User unfollowed successfully")));
    }

    @Test
    public void unfollowUserNotFoundTest() throws Exception{
        mvc.perform(delete("/followers/unfollow/1/0").accept(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isNotFound())
        .andExpect(content().string(containsString("User not found")));
    }

    @Test
    public void unfollowUserSelfUnfollowTest() throws Exception{
        mvc.perform(delete("/followers/unfollow/1/1").accept(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isBadRequest())
        .andExpect(content().string(containsString("You cannot unfollow yourself")));
    }

    @Test
    public void unfollowUserNotFollowingTest() throws Exception{
        mvc.perform(delete("/followers/unfollow/1/3").accept(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isConflict())
        .andExpect(content().string(containsString("You are not following this user")));
    }
 

}
