package com.proyecto.SazonIA.controller;


import org.springframework.data.domain.Page;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.proyecto.SazonIA.model.Follower;
import com.proyecto.SazonIA.model.User;
import com.proyecto.SazonIA.service.FollowerService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;

@RestController
@RequestMapping("follows")
@Tag(name = "Followers", description = "Operations for managing user following relationships")
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE,
        RequestMethod.PUT })
public class FollowerController {
    @Autowired
    private FollowerService service;

    // Seguir a un usuario
    @Operation(summary = "Follow a user")
    @ApiResponse(responseCode = "201", description = "User followed", content = @Content(mediaType = "application/json"))
    @PostMapping("/follow")
    public ResponseEntity<String> followUser(
            @RequestParam("userId") int userId,
            @RequestParam("followedId") int followedId) {
        User follower = service.findUserById(userId);
        User followed = service.findUserById(followedId);

        // Validaciones
        if (follower == null || followed == null) {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }
        if (follower.getUser_id() == followed.getUser_id()) {
            return new ResponseEntity<>("You cannot follow yourself", HttpStatus.BAD_REQUEST);
        }
        if (service.isFollowing(follower, followed)) {
            return new ResponseEntity<>("You are already following this user", HttpStatus.CONFLICT);
        }

        service.followUser(follower, followed);
        return new ResponseEntity<>("Successfully followed the user", HttpStatus.CREATED);
    }

    // Dejar de seguir a un usuario
    @Operation(summary = "Unfollow a user")
    @ApiResponse(responseCode = "200", description = "User unfollowed", content = @Content(mediaType = "application/json"))
    @DeleteMapping("/unfollow")
    public ResponseEntity<String> unfollowUser(
            @RequestParam("userId") int userId,
            @RequestParam("followedId") int followedId) {
        User follower = service.findUserById(userId);
        User followed = service.findUserById(followedId);

        // Validaciones
        if (follower == null || followed == null) {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }
        if (follower.getUser_id() == followed.getUser_id()) {
            return new ResponseEntity<>("You cannot unfollow your own account", HttpStatus.BAD_REQUEST);
        }
        if (!service.isFollowing(follower, followed)) {
            return new ResponseEntity<>("You are currently not following this user", HttpStatus.CONFLICT);
        }

        service.unfollowUser(follower, followed);
        return new ResponseEntity<>("Successfully unfollowed the user", HttpStatus.OK);
    }

    // Obtener los seguidores de un usuario con paginación
    @Operation(summary = "Get followers of a user with pagination")
    @ApiResponse(responseCode = "200", description = "Followers retrieved", content = {
            @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Follower.class))) })
    @GetMapping("/followers/{userId}")
    public ResponseEntity<Page<Follower>> getFollowers(
            @PathVariable int userId,
            @RequestParam(value = "page", defaultValue = "0", required = false) int page,
            @RequestParam(value = "size", defaultValue = "10", required = false) int pageSize) { 
        User followed = service.findUserById(userId);

        if (followed == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Page<Follower> followers = service.getFollowers(followed, page, pageSize);
        return new ResponseEntity<>(followers, HttpStatus.OK);
    }

    // Obtener los seguidos de un usuario con paginación
    @Operation(summary = "Get followings of a user with pagination")
    @ApiResponse(responseCode = "200", description = "Following users retrieved", content = {
            @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Follower.class))) })
    @GetMapping("/followings/{userId}")
    public ResponseEntity<Page<Follower>> getFollowing(
            @PathVariable int userId,
            @RequestParam(value = "page", defaultValue = "0", required = false) int page,
            @RequestParam(value = "size", defaultValue = "10", required = false) int pageSize) {
        User follower = service.findUserById(userId);

        if (follower == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Page<Follower> following = service.getFollowing(follower, page, pageSize);
        return new ResponseEntity<>(following, HttpStatus.OK);
    }

}
