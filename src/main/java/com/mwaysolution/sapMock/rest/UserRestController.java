package com.mwaysolution.sapMock.rest;


import com.mwaysolution.sapMock.model.User;
import com.mwaysolution.sapMock.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("users")
public class UserRestController {

    @Autowired
    RestTemplate restTemplate;

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
    public User update(@PathVariable("id") Integer userID, @RequestBody User user) {
        BeanUtils.copyProperties(user, userService.findById(userID), "id");
        return userService.save(userService.findById(userID));
    }

    @RequestMapping(value = "/users/{id}/register")
    public String register(@PathVariable("id") Integer id){
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        HttpEntity<User> entity = new HttpEntity<>(userService.findById(id),headers);

        return restTemplate.exchange(
                "http://localhost:8080/users/" + id, HttpMethod.POST, entity, String.class).getBody();
    }
}
