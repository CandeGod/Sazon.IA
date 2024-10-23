package com.proyecto.SazonIA.controller;

import java.util.List;

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
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;

@RestController
@RequestMapping("followers")
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE,
        RequestMethod.PUT })
public class FollowerController {
    @Autowired
    private FollowerService service;

    // Seguir a un usuario
    @Operation(summary = "Follow a user")
    @ApiResponse(responseCode = "201", description = "User followed", content = @Content(mediaType = "application/json"))
    @PostMapping("/follow/{userId}/{followedId}")
    public ResponseEntity<String> followUser(@PathVariable int userId, @PathVariable int followedId) {
        User follower = service.findUserById(userId);
        User followed = service.findUserById(followedId);

        // Validaciones
        if (follower == null || followed == null) {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }
        if (follower.getUserId() == followed.getUserId()) {
            return new ResponseEntity<>("You cannot follow yourself", HttpStatus.BAD_REQUEST);
        }
        if (service.isFollowing(follower, followed)) {
            return new ResponseEntity<>("You are already following this user", HttpStatus.CONFLICT);
        }

        service.followUser(follower, followed);
        return new ResponseEntity<>("User followed successfully", HttpStatus.CREATED);
    }

    // Dejar de seguir a un usuario
    @Operation(summary = "Unfollow a user")
    @ApiResponse(responseCode = "200", description = "User unfollowed", content = @Content(mediaType = "application/json"))
    @DeleteMapping("/unfollow/{userId}/{followedId}")
    public ResponseEntity<String> unfollowUser(@PathVariable int userId, @PathVariable int followedId) {
        User follower = service.findUserById(userId);
        User followed = service.findUserById(followedId);

        // Validaciones
        if (follower == null || followed == null) {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }
        if (follower.getUserId() == followed.getUserId()) {
            return new ResponseEntity<>("You cannot unfollow yourself", HttpStatus.BAD_REQUEST);
        }
        if (!service.isFollowing(follower, followed)) {
            return new ResponseEntity<>("You are not following this user", HttpStatus.CONFLICT);
        }

        service.unfollowUser(follower, followed);
        return new ResponseEntity<>("User unfollowed successfully", HttpStatus.OK);
    }

    // Obtener los seguidores de un usuario
    @Operation(summary = "Get followers of a user")
    @ApiResponse(responseCode = "200", description = "Followers retrieved", content = {
            @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Follower.class))) })
    @GetMapping("/followers/{followedId}")
    public ResponseEntity<List<Follower>> getFollowers(@PathVariable int followedId) {
        User followed = service.findUserById(followedId);

        if (followed == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        List<Follower> followers = service.getFollowers(followed);
        return new ResponseEntity<>(followers, HttpStatus.OK);
    }

    @Operation(summary = "Get followers of a user with pagination")
    @GetMapping(value = "paginationFollowers", params = { "page", "size" })
    
    public List<Follower> getFollowersPaginated(
            @RequestParam(value = "page", defaultValue = "0", required = false) int page,
            @RequestParam(value = "size", defaultValue = "2", required = false) int pageSize) {
        List<Follower> followers = service.getFollowers(page, pageSize);
        return followers;
    }

    // Obtener a quién sigue un usuario
    @Operation(summary = "Get following of a user")
    @ApiResponse(responseCode = "200", description = "Following users retrieved", content = {
            @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Follower.class))) })
    @GetMapping("/following/{followerId}")
    public ResponseEntity<List<Follower>> getFollowing(@PathVariable int followerId) {
        User follower = service.findUserById(followerId);

        if (follower == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        List<Follower> following = service.getFollowing(follower);
        return new ResponseEntity<>(following, HttpStatus.OK);
    }

    @Operation(summary = "Get following of a user with pagination")
    @GetMapping(value = "paginationFollowing", params = {"page", "size"})
    public List<Follower> getFollowingPaginated(
        @RequestParam(value = "page", defaultValue = "0", required = false)int page,
        @RequestParam(value = "size", defaultValue = "2", required = false)int pageSize){
            List<Follower> followers = service.getFollowing(page, pageSize);
            return followers;
        }
}
