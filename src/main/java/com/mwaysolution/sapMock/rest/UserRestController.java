package com.mwaysolution.sapMock.rest;


import com.mwaysolution.sapMock.model.User;
import com.mwaysolution.sapMock.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("users")
public class UserRestController {

    @Autowired
    private UserServiceImpl userService;

    @GetMapping
    public List<User> get() {
        return userService.get();
    }

    @GetMapping("{id}")
    public User getById(@PathVariable("id") Integer id) {
        return userService.getById(id);
    }

    @PostMapping
    public User create(@RequestBody User user) {
        return userService.save(user);
    }

    @DeleteMapping("{id}")
    public void deleteById(@PathVariable("id") Integer id) {
        userService.deleteById(id);
    }

    @PutMapping
    public User update(@RequestBody User user) {
        return userService.save(user);
    }

    @PutMapping("{id}/register")
    public User register(@PathVariable("id") Integer id) {
        return userService.register(userService.getById(id));
    }

    @PutMapping("{id}/unregister")
    public User unregister(@PathVariable("id") Integer id) {
        return userService.unregister(userService.getById(id));
    }
}
