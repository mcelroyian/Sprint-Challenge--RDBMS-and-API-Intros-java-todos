package com.lambdaschool.todos.services;

import com.lambdaschool.todos.models.Todos;
import com.lambdaschool.todos.models.User;
import com.lambdaschool.todos.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Transactional
@Service(value = "todoService")
public class TodoServiceImpl implements TodoService {

    @Autowired
    private UserService userService;

    @Autowired
    private TodoRepository todoRepository;

    @Override
    public List<Todos> findAll() {
        List<Todos> list = new ArrayList<>();
        todoRepository.findAll().iterator().forEachRemaining(list::add);
        return list;
    }

    @Override
    public Todos findTodoById(long id) {
        return  todoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Todo with id " + id + " Not Found!")) ;
    }

    @Transactional
    @Override
    public void delete(long id) {
        if (todoRepository.findById(id)
                .isPresent())
        {
            todoRepository.deleteById(id);
        } else
        {
            throw new EntityNotFoundException("Todo with id " + id + " Not Found!");
        }
    }

    @Transactional
    @Override
    public Todos update(long todoid, Todos todo) {
        if (todoRepository.findById(todoid)
                .isPresent())
        {
            Todos myTodo = findTodoById(todoid);
            if(todo.getDescription() != null){
                myTodo.setDescription(todo.getDescription());
            }

            return todoRepository.save(myTodo);
        } else
        {
            throw new EntityNotFoundException("Todo with id " + todoid + " Not Found!");
        }
    }

    @Transactional
    @Override
    public Todos save(long userid, String description) {
        User currentUser = userService.findUserById(userid);

        Todos newUserTodo = new Todos(currentUser, description);
        return todoRepository.save(newUserTodo);
    }

    @Override
    public List<Todos> findByUserName(String username) {
        return todoRepository.findAllByUser_Username(username.toLowerCase());
    }
}
