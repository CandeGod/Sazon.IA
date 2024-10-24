package com.proyecto.SazonIA.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.proyecto.SazonIA.model.Comment;
import com.proyecto.SazonIA.model.Recipe;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureMockMvc
public class RecipeControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private RecipeController recipeController;

    @Test
    void contextLoads() {
        assertThat(recipeController).isNotNull();
    }

    @Test
    public void getAllRecipesTest() throws Exception {
        mvc.perform(get("/recipe").accept(MediaType.APPLICATION_JSON)).andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(greaterThan(0))));
    }

    @Test
    public void getRecipeByIdTest() throws Exception {
        mvc.perform(get("/recipe/67188e470a9b8c19a72c9d45").accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", is("67188e470a9b8c19a72c9d45")));
    }

    @Test
    public void getRecipeByIdNoFoundTest() throws Exception {
        mvc.perform(get("/recipe/67168d20868962378c9c5115").accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(content().string(containsString("The requested item is not registered")));
    }

    @Test
    public void putRecipeTest() throws Exception {
        Recipe recipe = new Recipe();
        recipe.setRecipeId(300);
        recipe.setName("Receta de prueba");
        recipe.setInstructions("Instrucciones de prueba");
        recipe.setPreparationTime("30 minutos");
        recipe.setDifficulty("Fácil");
        mvc.perform(put("/recipe/67168d20868962378c9c5114")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                        "{\"recipeId\":300,\"name\":\"Receta de prueba\",\"instructions\":\"Instrucciones de prueba\",\"preparationTime\":\"30 minutos\",\"difficulty\":\"Fácil\"}"))
                .andDo(print())
                .andExpect(status().is(200))
                .andExpect(content().string(containsString("")));
    }

    @Test
    public void updateRecipeTest() throws Exception {
        Recipe recipe = new Recipe();
        recipe.setRecipeId(300);
        recipe.setName("Updated Recipe");
        recipe.setInstructions("Updated Instructions");
        recipe.setPreparationTime("45 minutes");
        recipe.setDifficulty("Medium");
        mvc.perform(put("/recipe/67168d20868962378c9c5114")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                        "{\"recipeId\":300,\"name\":\"Receta actualizada\",\"instructions\":\"Instrucciones actualizadas\",\"preparationTime\":\"45 minutos\",\"difficulty\":\"Alta\"}"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("")));
    }

    @Test
    public void postRecipeTest() throws Exception {
        mvc.perform(post("/recipe")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                        "{\"name\":\"New Recipe\",\"instructions\":\"New Instructions\",\"preparationTime\":\"30 minutes\",\"difficulty\":\"Easy\"}"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void deleteRecipeTest() throws Exception {
        mvc.perform(delete("/recipe/67168d20868962378c9c5114"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void addCommentToRecipeTest() throws Exception {
        mvc.perform(put("/recipe/addComment/67188e470a9b8c19a72c9d45/comment")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"commentId\":\"12345\",\"text\":\"This is a comment\"}"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void addReplyToCommentTest() throws Exception {
        Comment reply = new Comment();
        reply.setCommentId(312);
        reply.setContent("This is a reply");
        reply.setAuthor("Yo");
        reply.setTimestamp("Timestamp");
        mvc.perform(put("/recipe/addReply/67189d87fd7323372c3d0335/comment/67189f049d58067f88d71ab4/reply")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"content\":\"This is a reply\",\"author\":\"Yo\",\"timestamp\":\"Timestamp\"}"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void deleteCommentFromRecipeTest() throws Exception {
        mvc.perform(delete("/recipe/deleteComment/67189d87fd7323372c3d0335/comment/67189f049d58067f88d71ab4"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void deleteReplyFromCommentTest() throws Exception {
        mvc.perform(delete("/recipe/deleteReply/67189d87fd7323372c3d0335/comment/67189f049d58067f88d71ab4/reply/6718a367dab6b649ec205744"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void updateCommentFromRecipeTest() throws Exception {
        mvc.perform(put("/recipe/updateComment/67189d87fd7323372c3d0335/comment/67189f049d58067f88d71ab4")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"content\":\"updated comment\",\"author\":\"updated\",\"timestamp\":\"Timestamp\"}"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void updateReplyFromCommentTest() throws Exception {
        mvc.perform(put("/recipe/updateReply/67168d20868962378c9c5114/comment/12345/reply")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"commentId\":\"67890\",\"text\":\"Updated reply\"}"))
                .andDo(print())
                .andExpect(status().isOk());
    }
}