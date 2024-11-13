package com.proyecto.SazonIA.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.proyecto.SazonIA.exception.PostNotFoundException;
import com.proyecto.SazonIA.exception.UserNotFoundException;
import com.proyecto.SazonIA.model.CommentPost;
import com.proyecto.SazonIA.model.User;
import com.proyecto.SazonIA.repository.CommentPostRepository;
import com.proyecto.SazonIA.repository.PostRepository;
import com.proyecto.SazonIA.repository.UserRepository;

import java.util.NoSuchElementException;

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

        // Verificar si el post al que se va a añadir el comentario existe
        boolean postExists = postRepository.existsById(comment.getPostId());
        if (!postExists) {
            throw new PostNotFoundException("Post not found");
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

    public CommentPost editComment(String postId, String commentId, String content) {
        // Verificar si el comentario existe
        CommentPost existingComment = commentRepository.findById(commentId).orElseThrow(
            () -> new NoSuchElementException("Comment not found")
        );
    
        // Verificar si el comentario pertenece al post correcto
        if (!existingComment.getPostId().equals(postId)) {
            throw new IllegalArgumentException("Comment does not belong to the specified post");
        }
    
        // Actualizar solo el contenido del comentario
        existingComment.setContent(content);
        return commentRepository.save(existingComment);
    }
    

    public boolean deleteComment(String postId, String commentId) {
        // Buscar el comentario por su ID
        CommentPost comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new NoSuchElementException("Comment not found"));

        // Verificar si el comentario pertenece al post proporcionado
        if (!comment.getPostId().equals(postId)) {
            throw new IllegalArgumentException("Comment does not belong to the specified post");
        }

        // Eliminar el comentario
        commentRepository.deleteById(commentId);
        return true; // Eliminación exitosa
    }

}
