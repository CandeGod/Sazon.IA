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
        Recipe recipe = recipeRepository.findById(id).get();
        List<Comment> comments = recipe.getComments();
        for (Comment comment : comments) {
            List<Comment> replies = comment.getReplies();
            for (Comment reply : replies) {
                deleteComments(reply.getReplies());
            }
            deleteComments(comments);
        }
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
        deleteComments(comment.getReplies());
        recipe.getComments().remove(comment);
        recipeRepository.save(recipe);
        commentRepository.deleteById(commentId);
    }

    public void updateCommentFromRecipe(String idRecipe, String idComment, Comment comment) {
        Recipe recipe = recipeRepository.findById(idRecipe).get();
        int index = -1;
        for (Comment c : recipe.getComments()) {
            if (c.getId().equals(idComment)) {
                Comment aux = c;
                index = recipe.getComments().indexOf(aux);
                break;
            }

        }
        recipe.getComments().remove(index);
        recipe.getComments().set(index, comment);
        Comment commentToUpdate = commentRepository.findById(comment.getId()).get();
        commentToUpdate.setCommentId(comment.getCommentId());
        commentToUpdate.setContent(comment.getContent());
        commentToUpdate.setAuthor(comment.getAuthor());
        commentToUpdate.setTimestamp(comment.getTimestamp());
        commentToUpdate.setReplies(comment.getReplies());
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
        int index = -1;
        for (Comment c : comment.getReplies()) {
            if (reply.getId() == c.getId()) {
                index = comment.getReplies().indexOf(c);
                break;
            }
        }
        comment.getReplies().remove(index);
        commentRepository.save(comment);
        commentRepository.deleteById(replyId);
        List<Comment> comments = recipe.getComments();
        for (Comment c : comments) {
            List<Comment> replies = c.getReplies();
            replies.removeIf(r -> r.getId().equals(replyId));
            deleteComments(replies);
        }
        recipe.setComments(comments);
        recipeRepository.save(recipe);
    }

    public void updateReplyFromComment(String idRecipe, String commentId, Comment reply) {
        Recipe recipe = recipeRepository.findById(idRecipe).get();
        Comment comment = commentRepository.findById(commentId).get();
        int index = -1;
        for (Comment c : comment.getReplies()) {
            if (reply.getId() == c.getId()) {
                index = comment.getReplies().indexOf(c);
                break;
            }
        }
        comment.getReplies().remove(index);
        comment.getReplies().set(index, reply);
        int index2 = -1;
        for (Comment c2 : recipe.getComments()) {
            if (comment.getId() == c2.getId()) {
                index2 = recipe.getComments().indexOf(c2);
                break;
            }
        }
        recipe.getComments().remove(index2);
        recipe.getComments().set(index2, comment);
        Comment replyToUpdate = commentRepository.findById(reply.getId()).get();
        replyToUpdate.setCommentId(reply.getCommentId());
        replyToUpdate.setContent(reply.getContent());
        replyToUpdate.setAuthor(reply.getAuthor());
        replyToUpdate.setTimestamp(reply.getTimestamp());
        replyToUpdate.setReplies(reply.getReplies());
        commentRepository.save(replyToUpdate);
        commentRepository.save(comment);
        recipeRepository.save(recipe);
    }

    public void deleteComments(List<Comment> comments) {
        for (Comment c : comments) {
            commentRepository.deleteById(c.getId());
        }
    }
}