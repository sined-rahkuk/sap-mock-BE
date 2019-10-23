package com.mwaysolution.sapMock.rest;


import com.mwaysolution.sapMock.model.User;
import com.mwaysolution.sapMock.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("users")
public class UserRestController {

    @Autowired
    private UserService userService;

    @GetMapping
    public List<User> get(){
        return userService.findAll();
    }

    @GetMapping("{id}")
    public User getById(@PathVariable("id") Integer id) {
        return userService.findById(id);
    }

    @PostMapping
    public User create(@RequestBody User user){
        return userService.save(user);
    }

    @DeleteMapping("{id}")
    public void deleteById(@PathVariable ("id") Integer id){
        userService.delete(userService.findById(id));
    }

    @PutMapping("{id}")
    public User updateContact(@PathVariable("id") User contactFromDB, @RequestBody User contact) {
        BeanUtils.copyProperties(contact, contactFromDB, "id");
        return userService.save(contactFromDB);
    }
}
