package com.proyecto.SazonIA.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proyecto.SazonIA.model.Comment;
import com.proyecto.SazonIA.model.Recipe;
import com.proyecto.SazonIA.repository.CommentRepository;
import com.proyecto.SazonIA.repository.RecipeRepository;



@Service
public class RecipeService {

    @Autowired
    private RecipeRepository recipeRepository;

    @Autowired
    private CommentRepository commentRepository;

    public List<Recipe> getAll() {
        return recipeRepository.findAll();
    }

    public void save(Recipe receta) {
        recipeRepository.save(receta);
    }

    public Recipe getById(String id) {
        return recipeRepository.findById(id).get();
    }

    public void delete(String id) {
        recipeRepository.deleteById(id);
    }

    public void addCommentToRecipe(String id, Comment comment) {
        Recipe recipe = recipeRepository.findById(id).get();
        Comment savedComment = commentRepository.save(comment);
        recipe.getComents().add(savedComment);
        recipeRepository.save(recipe);
    }

    public void deleteCommentFromRecipe(String id, String commentId) {
        Recipe recipe = recipeRepository.findById(id).get();
        Comment comment = commentRepository.findById(commentId).get();
        recipe.getComents().remove(comment);
        recipeRepository.save(recipe);
        commentRepository.deleteById(commentId);
    }

    public void updateCommentFromRecipe(String idRecipe, String idComment, Comment comment) {
        Recipe recipe = recipeRepository.findById(idRecipe).get();
        Comment newComment = commentRepository.findById(idComment).get();
        int index = recipe.getComents().indexOf(newComment);
        recipe.getComents().remove(index);
        recipe.getComents().set(index, comment);
        Comment commentToUpdate = commentRepository.findById(comment.getId()).get();
        commentRepository.save(commentToUpdate);
        recipeRepository.save(recipe);
    }

    public void addReplyToComment(String idRecipe, String commentId, Comment reply) {
        Recipe recipe = recipeRepository.findById(idRecipe).get();
        Comment comment = commentRepository.findById(commentId).get();
        Comment newReply = commentRepository.save(reply);
        int index = recipe.getComents().indexOf(comment);
        recipe.getComents().remove(index);
        recipe.getComents().set(index, comment);
        comment.getReplies().add(newReply);
        commentRepository.save(comment);
        recipeRepository.save(recipe);
    }

    public void deleteReplyFromComment(String idRecipe, String commentId, String replyId) {
        Recipe recipe = recipeRepository.findById(idRecipe).get();
        Comment comment = commentRepository.findById(commentId).get();
        Comment reply = commentRepository.findById(replyId).get();
        comment.getReplies().remove(reply);
        commentRepository.deleteById(replyId);
        commentRepository.save(comment);
        recipeRepository.save(recipe);
    }

    public void updateReplyFromComment(String idRecipe, String commentId, Comment reply) {
        Recipe recipe = recipeRepository.findById(idRecipe).get();
        Comment comment = commentRepository.findById(commentId).get();
        int index = comment.getReplies().indexOf(reply);
        comment.getReplies().remove(index);
        comment.getReplies().set(index, reply);
        Comment replyToUpdate = commentRepository.findById(reply.getId()).get();
        commentRepository.save(replyToUpdate);
        commentRepository.save(comment);
        recipeRepository.save(recipe);
    }
}