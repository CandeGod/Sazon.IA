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

    public Recipe getBy_Id(int _id) {
        return recipeRepository.findAll().stream().filter(recipe -> recipe.getRecipeId() == _id).findFirst().get();
    }

    public int getIdRecipe() {
        List<Recipe> recipes = recipeRepository.findAll();
        if (recipes.size() > 0) {
            return recipes.get(recipes.size() - 1).getRecipeId() + 1;
        } else {
            return 1;
        }
    }

    public void delete(String id) {
        recipeRepository.deleteById(id);
    }

    public void addCommentToRecipe(String id, Comment comment) {
        Recipe recipe = recipeRepository.findById(id).get();
        Comment savedComment = commentRepository.save(comment);
        recipe.getComments().add(savedComment);
        recipeRepository.save(recipe);
    }

    public void deleteCommentFromRecipe(String id, String commentId) {
        Recipe recipe = recipeRepository.findById(id).get();
        Comment comment = commentRepository.findById(commentId).get();
        recipe.getComments().remove(comment);
        recipeRepository.save(recipe);
        commentRepository.deleteById(commentId);
    }

    public void updateCommentFromRecipe(String idRecipe, String idComment, Comment comment) {
        Recipe recipe = recipeRepository.findById(idRecipe).get();
        Comment newComment = commentRepository.findById(idComment).get();
        int index = recipe.getComments().indexOf(newComment);
        recipe.getComments().remove(index);
        recipe.getComments().set(index, comment);
        Comment commentToUpdate = commentRepository.findById(comment.getId()).get();
        commentRepository.save(commentToUpdate);
        recipeRepository.save(recipe);
    }

    public void addReplyToComment(String idRecipe, String commentId, Comment reply) {
        Recipe recipe = recipeRepository.findById(idRecipe).get();
        Comment comment = commentRepository.findById(commentId).get();
        Comment newReply = commentRepository.save(reply);
        comment.getReplies().add(newReply);
        recipe.getComments().removeIf(r -> r.getId().equals(comment.getId()));
        recipe.getComments().add(comment);
        commentRepository.save(comment);
        recipeRepository.save(recipe);
    }

    public void deleteReplyFromComment(String idRecipe, String commentId, String replyId) {
        Recipe recipe = recipeRepository.findById(idRecipe).get();
        Comment comment = commentRepository.findById(commentId).get();
        Comment reply = commentRepository.findById(replyId).get();
        comment.getReplies().remove(reply);
        commentRepository.save(comment);
        commentRepository.deleteById(replyId);
        List<Comment> comments = recipe.getComments();
        for (Comment c : comments) {
            List<Comment> replies = c.getReplies();
            replies.removeIf(r -> r.getId().equals(replyId));
        }
        recipe.setComments(comments);
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