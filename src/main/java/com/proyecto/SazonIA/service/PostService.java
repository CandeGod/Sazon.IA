package com.proyecto.SazonIA.service;

import com.proyecto.SazonIA.model.Post;
import com.proyecto.SazonIA.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    // Obtener todas las publicaciones sin paginación
    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    // Obtener todas las publicaciones con paginación
    public List<Post> getAllPosts(int page, int pageSize) {
        PageRequest pageReq = PageRequest.of(page, pageSize);
        Page<Post> posts = postRepository.findAll(pageReq);
        return posts.getContent();
    }

    // Obtener una publicación por ID
    public Optional<Post> getPostById(String postId) {
        return postRepository.findById(postId);
    }

    // Crear una nueva publicación
    public Post createPost(Post post) {
        post.setPostId(UUID.randomUUID().toString()); // Generar el ID antes de guardar
        return postRepository.save(post);
    }
    

    // Actualizar una publicación existente
    public Post updatePost(String postId, Post postDetails) {
        Optional<Post> existingPost = postRepository.findById(postId);
        if (existingPost.isPresent()) {
            Post post = existingPost.get();
            post.setTitle(postDetails.getTitle());
            post.setContent(postDetails.getContent());
            post.setMediaUrls(postDetails.getMediaUrls());
            post.setRating(postDetails.getRating());
            post.setComments(postDetails.getComments());
            return postRepository.save(post);
        }
        return null; // Maneja el caso donde el post no existe
    }

    // Eliminar una publicación
    public boolean deletePost(String postId) {
        if (postRepository.existsById(postId)) {
            postRepository.deleteById(postId);
            return true;
        }
        return false; // Maneja el caso donde el post no existe
    }
}
