package com.lambdaschool.todos.repository;

import com.lambdaschool.todos.models.User;
import com.lambdaschool.todos.views.JustTheCount;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserRepository extends CrudRepository<User, Long> {

    List<User> findByUsernameContainingIgnoreCase(String name);

    User findByUsername(String username);

    @Query(value = "SELECT u.username as username, COUNT(td.todoid) as count FROM users u JOIN todos td on u.userid = td.userid WHERE td.completed = 0 GROUP BY u.username ORDER BY u.username", nativeQuery = true)
    List<JustTheCount> countTodos();
}
