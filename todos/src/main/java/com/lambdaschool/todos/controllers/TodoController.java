package com.lambdaschool.todos.controllers;

import com.lambdaschool.todos.models.Todos;
import com.lambdaschool.todos.services.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.net.URISyntaxException;

@RestController
@RequestMapping("/todos")
public class TodoController {

    @Autowired
    private TodoService todoService;


    //  POST /todos/user/{userid} - adds a todo to the user.

    @PostMapping(value = "/user/{userid}",
        consumes = {"application/json"})
    public ResponseEntity<?> addNewUserEmail(
            @PathVariable long userid, @RequestBody Todos newTodo) throws URISyntaxException
    {
        Todos newUserTodo = todoService.save(userid, newTodo.getDescription());

        // set the location header for the newly created resource
        HttpHeaders responseHeaders = new HttpHeaders();
        URI newUserEmailURI = ServletUriComponentsBuilder.fromCurrentServletMapping()
                .path("/useremails/useremail/{useremailid}")
                .buildAndExpand(newUserTodo.getTodoid())
                .toUri();
        responseHeaders.setLocation(newUserEmailURI);

        return new ResponseEntity<>(null,
                responseHeaders,
                HttpStatus.CREATED);
    }

    //  PATCH /todos/todo/{todoid} - mark a todo as completed.

    @PatchMapping(value = "/todo/{id}",
            consumes = {"application/json"})
    public ResponseEntity<?> updateTodo(
            @RequestBody
                    Todos updatedTodo,
            @PathVariable
                    long id)
    {
        todoService.update(id, updatedTodo);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
