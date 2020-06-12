package com.lambdaschool.todos.services;

import com.lambdaschool.todos.models.Todos;
import com.lambdaschool.todos.models.User;
import com.lambdaschool.todos.repository.UserRepository;
import com.lambdaschool.todos.views.JustTheCount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Transactional
@Service(value = "userService")
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserAuditing userAuditing;

    @Override
    public List<User> findAll() {
        List<User> list = new ArrayList<>();
        userRepository.findAll()
                .iterator()
                .forEachRemaining(list::add);
        return list;
    }

    @Override
    public List<User> findByNameContaining(String username) {
        return userRepository.findByUsernameContainingIgnoreCase(username.toLowerCase());
    }

    @Override
    public User findUserById(long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User id " + id + " not found!"));
    }

    @Override
    public User findByName(String name) {
        User uu = userRepository.findByUsername(name.toLowerCase());
        if (uu == null)
        {
            throw new EntityNotFoundException("User name " + name + " not found!");
        }
        return uu;
    }

    @Override
    public void delete(long id) {
        userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User id " + id + " not found!"));
        userRepository.deleteById(id);
    }

    @Override
    public User save(User user) {
        User newUser = new User();

        if (user.getUserid() != 0)
        {
            User oldUser = userRepository.findById(user.getUserid())
                    .orElseThrow(() -> new EntityNotFoundException("User id " + user.getUserid() + " not found!"));

            newUser.setUserid(user.getUserid());
        }

        newUser.setUsername(user.getUsername()
                .toLowerCase());
        newUser.setPassword(user.getPassword());
        newUser.setPrimaryemail(user.getPrimaryemail()
                .toLowerCase());

        newUser.getTodos().clear();

        for (Todos ue : user.getTodos())
        {
            newUser.getTodos()
                    .add(new Todos(newUser, ue.getDescription()));
        }

        return userRepository.save(newUser);
    }

    @Override
    public User update(User user, long id) {
        User currentUser = findUserById(id);

        if (user.getUsername() != null)
        {
            currentUser.setUsername(user.getUsername()
                    .toLowerCase());
        }

        if (user.getPassword() != null)
        {
            currentUser.setPassword(user.getPassword());
        }

        if (user.getPrimaryemail() != null)
        {
            currentUser.setPrimaryemail(user.getPrimaryemail()
                    .toLowerCase());
        }

        if (user.getTodos()
                .size() > 0)
        {
            currentUser.getTodos()
                    .clear();
            for (Todos ue : user.getTodos())
            {
                currentUser.getTodos()
                        .add(new Todos(currentUser, ue.getDescription()));
            }
        }

        return userRepository.save(currentUser);
    }

    @Override
    public List<JustTheCount> countTodos() {
        return userRepository.countTodos();
    }
}
