package com.lambdaschool.todos.services;

import com.lambdaschool.todos.models.Todos;

import java.util.List;

public interface TodoService {

    List<Todos> findAll();

    Todos findTodoById(long id);

    void delete(long id);

    Todos update(
            long todoid,
            Todos todo);

    Todos save(
            long userid,
            String description);

    List<Todos> findByUserName(String username);
}
