package com.lambdaschool.todos.controllers;

import com.lambdaschool.todos.models.User;
import com.lambdaschool.todos.services.UserService;
import com.lambdaschool.todos.views.JustTheCount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;


    //  GET /users/users - return all of the users and their todos

    @GetMapping(value = "/users",
            produces = {"application/json"})
    public ResponseEntity<?> listAllUsers()
    {
        List<User> myUsers = userService.findAll();
        return new ResponseEntity<>(myUsers,
                HttpStatus.OK);
    }

    //  GET /users/user/{userid} - return the user and their todos based off of id

    @GetMapping(value = "/user/{userId}",
            produces = {"application/json"})
    public ResponseEntity<?> getUserById(
            @PathVariable
                    Long userId)
    {
        User u = userService.findUserById(userId);
        return new ResponseEntity<>(u,
                HttpStatus.OK);
    }

    //  POST /users/user - adds a user.

    @PostMapping(value = "/user",
            consumes = {"application/json"})
    public ResponseEntity<?> addNewUser(
            @Validated
            @RequestBody
                    User newuser) throws URISyntaxException
    {
        newuser.setUserid(0);
        newuser = userService.save(newuser);

        // set the location header for the newly created resource
        HttpHeaders responseHeaders = new HttpHeaders();
        URI newUserURI = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{userid}")
                .buildAndExpand(newuser.getUserid())
                .toUri();
        responseHeaders.setLocation(newUserURI);

        return new ResponseEntity<>(null,
                responseHeaders,
                HttpStatus.CREATED);
    }

    //  DELETE /users/userid/{userid} - Deletes a user based off of their userid and deletes all their associated todos.

    @DeleteMapping(value = "/user/{id}")
    public ResponseEntity<?> deleteUserById(
            @PathVariable
                    long id)
    {
        userService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    //  GET /users/users/todos - lists the number of todos each user has that are NOT completed. Use a custom query to accomplish this!
    @GetMapping(value = "/users/todos")
    public ResponseEntity<?> viewTodos()
    {
        List<JustTheCount> todoList = userService.countTodos();
        return new ResponseEntity<>(todoList, HttpStatus.OK);
    }

}
