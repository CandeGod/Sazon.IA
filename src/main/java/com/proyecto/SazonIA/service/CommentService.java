package com.proyecto.SazonIA.service;

import com.proyecto.SazonIA.model.Comment;
import com.proyecto.SazonIA.model.Post;
import com.proyecto.SazonIA.repository.CommentRepository;
import com.proyecto.SazonIA.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PostRepository postRepository;

    public Comment addComment(Comment comment) {
        Comment savedComment = commentRepository.save(comment);
        Post post = postRepository.findById(comment.getPostId()).orElse(null);
        if (post != null) {
            // Verifica si el post ya tiene comentarios y añade el nuevo
            if (post.getComments() == null) {
                post.setComments(new ArrayList<>());
            }
            post.getComments().add(savedComment);
            postRepository.save(post);
        }
        return savedComment;
    }
    
    public List<Comment> getCommentsByPostId(String postId) {
        return commentRepository.findByPostId(postId);
    }

    // Obtener comentarios paginados por postId
    public List<Comment> getCommentsByPostId(String postId, int page, int pageSize) {
        PageRequest pageReq = PageRequest.of(page, pageSize);
        Page<Comment> comments = commentRepository.findByPostId(postId, pageReq);
        return comments.getContent();
    }

    public void deleteComment(String commentId) {
        Comment comment = commentRepository.findById(commentId).orElse(null);
        if (comment != null) {
            commentRepository.deleteById(commentId);

            // Actualizar la publicación para eliminar el comentario
            Post post = postRepository.findById(comment.getPostId()).orElse(null);
            if (post != null) {
                post.getComments().removeIf(c -> c.getCommentId().equals(commentId));
                postRepository.save(post);
            }
        }
    }
}