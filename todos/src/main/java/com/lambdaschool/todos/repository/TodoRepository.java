package com.lambdaschool.todos.repository;

import com.lambdaschool.todos.models.Todos;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TodoRepository extends CrudRepository<Todos, Long> {

    List<Todos> findAllByUser_Username(String name);
}
