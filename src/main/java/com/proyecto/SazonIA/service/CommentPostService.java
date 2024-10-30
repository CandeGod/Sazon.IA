package com.proyecto.SazonIA.service;

import com.proyecto.SazonIA.model.CommentPost;
import com.proyecto.SazonIA.model.User; // Importar el modelo de usuario
import com.proyecto.SazonIA.repository.CommentPostRepository;
import com.proyecto.SazonIA.repository.UserRepository; // Importar el repositorio de usuarios
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import com.proyecto.SazonIA.exception.UserNotFoundException;
import java.util.NoSuchElementException;

import java.util.List;

@Service
public class CommentPostService {

    @Autowired
    private CommentPostRepository commentRepository;

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
        return savedComment;
    }
    
    

    public boolean deleteComment(String commentId, Integer userId) {
        // Buscar el comentario por su ID
        CommentPost comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new NoSuchElementException("Comment not found"));
        
        // Verificar si el comentario pertenece al usuario proporcionado
        if (!comment.getUserId().equals(userId)) {
            throw new IllegalArgumentException("User is not the owner of the comment");
        }
        
        // Eliminar el comentario
        commentRepository.deleteById(commentId);
        return true; // Eliminación exitosa
    }
    
}
