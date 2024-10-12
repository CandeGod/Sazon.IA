package com.proyecto.SazonIA.controller;

import java.util.List;
import java.util.Optional;

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
    // Validar que el usuario no se siga a sí mismo
    if (userId == followedId) {
        return new ResponseEntity<>("A user cannot follow themselves", HttpStatus.BAD_REQUEST);
    }

    // Cargar usuarios existentes
    Optional<User> followerOpt = service.findUserById(userId);
    Optional<User> followedOpt = service.findUserById(followedId);

    if (followerOpt.isEmpty() || followedOpt.isEmpty()) {
        return new ResponseEntity<>("One of the users does not exist", HttpStatus.NOT_FOUND);
    }

    User follower = followerOpt.get();
    User followed = followedOpt.get();

    // Verificar si el usuario ya sigue al otro usuario
    Optional<Follower> existingFollower = service.findByUserAndFollowed(follower, followed);
    if (existingFollower.isPresent()) {
        return new ResponseEntity<>("User is already following the specified user", HttpStatus.BAD_REQUEST);
    }

    // Proceder a seguir al usuario
    service.followUser(follower, followed);
    return new ResponseEntity<>("User followed successfully", HttpStatus.CREATED);
}

 
     // Dejar de seguir a un usuario
     @Operation(summary = "Unfollow a user")
     @ApiResponse(responseCode = "200", description = "User unfollowed", content = @Content(mediaType = "application/json"))
     @DeleteMapping("/unfollow/{userId}/{followedId}")
     public ResponseEntity<String> unfollowUser(@PathVariable int userId, @PathVariable int followedId) {
         // Validar que los usuarios existan
         if (!service.userExists(userId) || !service.userExists(followedId)) {
             return new ResponseEntity<>("One of the users does not exist", HttpStatus.BAD_REQUEST);
         }
 
         // Validar que el usuario no se siga a sí mismo
         if (userId == followedId) {
             return new ResponseEntity<>("A user cannot unfollow themselves", HttpStatus.BAD_REQUEST);
         }
 
         User follower = new User();
         follower.setUserId(userId);
 
         User followed = new User();
         followed.setUserId(followedId);
 
         Optional<Follower> existingFollower = service.findByUserAndFollowed(follower, followed);
         if (!existingFollower.isPresent()) {
             return new ResponseEntity<>("User is not following the specified user", HttpStatus.BAD_REQUEST);
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
         User followed = new User();
         followed.setUserId(followedId);
 
         // Validar que el usuario existe
         if (!service.userExists(followedId)) {
             return new ResponseEntity<>(HttpStatus.NOT_FOUND);
         }
 
         List<Follower> followers = service.getFollowers(followed);
         return new ResponseEntity<>(followers, HttpStatus.OK);
     }
 
     // Obtener a quién sigue un usuario
     @Operation(summary = "Get following of a user")
     @ApiResponse(responseCode = "200", description = "Following users retrieved", content = {
             @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Follower.class))) })
     @GetMapping("/following/{followerId}")
     public ResponseEntity<List<Follower>> getFollowing(@PathVariable int followerId) {
         User follower = new User();
         follower.setUserId(followerId);
 
         // Validar que el usuario existe
         if (!service.userExists(followerId)) {
             return new ResponseEntity<>(HttpStatus.NOT_FOUND);
         }
 
         List<Follower> following = service.getFollowing(follower);
         return new ResponseEntity<>(following, HttpStatus.OK);
     }

}
