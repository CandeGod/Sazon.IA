package com.proyecto.SazonIA.service;

import com.proyecto.SazonIA.model.CommentPost;
import com.proyecto.SazonIA.model.Post;
import com.proyecto.SazonIA.model.User; // Importar el modelo de usuario
import com.proyecto.SazonIA.repository.CommentPostRepository;
import com.proyecto.SazonIA.repository.PostRepository;
import com.proyecto.SazonIA.repository.UserRepository; // Importar el repositorio de usuarios
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import com.proyecto.SazonIA.exception.UserNotFoundException;
import java.util.NoSuchElementException;


import java.util.ArrayList;
import java.util.List;

@Service
public class CommentPostService {

    @Autowired
    private CommentPostRepository commentRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository; // Inyección del repositorio de usuarios

    public CommentPost addComment(CommentPost comment) {
        // Verificar si el usuario está registrado
        User user = userRepository.findById(comment.getUserId()).orElse(null);
        if (user == null) {
            throw new UserNotFoundException("User not found");
        }

        // commentId y commentDate, se manejan automáticamente
        CommentPost savedComment = commentRepository.save(comment);

        Post post = postRepository.findById(comment.getPostId()).orElse(null);
        if (post != null) {
            if (post.getComments() == null) {
                post.setComments(new ArrayList<>());
            }
            post.getComments().add(savedComment);
            postRepository.save(post);
        }
        return savedComment;
    }

    public List<CommentPost> getCommentsByPostId(String postId) {
        return commentRepository.findByPostId(postId);
    }

    // Obtener comentarios paginados por postId
    public List<CommentPost> getCommentsByPostId(String postId, int page, int pageSize) {
        PageRequest pageReq = PageRequest.of(page, pageSize);
        Page<CommentPost> comments = commentRepository.findByPostId(postId, pageReq);
        return comments.getContent();
    }


    public CommentPost editComment(String postId, String commentId, Integer userId, CommentPost updatedComment) {
        // Verificar si el comentario existe
        CommentPost existingComment = commentRepository.findById(commentId).orElse(null);
        if (existingComment == null) {
            throw new NoSuchElementException("Comment not found");
        }
    
        // Verificar si el comentario pertenece al post correcto
        if (!existingComment.getPostId().equals(postId)) {
            throw new IllegalArgumentException("Comment does not belong to the specified post");
        }
    
        // Verificar si el usuario es el propietario del comentario
        if (!existingComment.getUserId().equals(userId)) {
            throw new IllegalArgumentException("User is not the owner of the comment");
        }
    
        // Actualizar el contenido del comentario
        existingComment.setContent(updatedComment.getContent());
        CommentPost savedComment = commentRepository.save(existingComment);
    
        // Actualizar el comentario dentro de la publicación
        Post post = postRepository.findById(postId).orElse(null);
        if (post != null) {
            List<CommentPost> postComments = post.getComments();
            if (postComments != null) {
                for (int i = 0; i < postComments.size(); i++) {
                    if (postComments.get(i).getCommentId().equals(commentId)) {
                        postComments.set(i, savedComment); // Actualiza el comentario en la lista de la publicación
                        break;
                    }
                }
                postRepository.save(post); // Guardar los cambios en la publicación
            }
        }
    
        return savedComment;
    }
    
    

    public boolean deleteComment(String commentId) {
        CommentPost comment = commentRepository.findById(commentId).orElse(null);
        if (comment != null) {
            commentRepository.deleteById(commentId);

            // Actualizar la publicación para eliminar el comentario
            Post post = postRepository.findById(comment.getPostId()).orElse(null);
            if (post != null) {
                post.getComments().removeIf(c -> c.getCommentId().equals(commentId));
                postRepository.save(post);
            }
            return true; // Eliminación exitosa
        } else {
            return false; // Comentario no encontrado
        }
    }
}
