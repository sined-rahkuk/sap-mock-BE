package com.mwaysolution.sapMock.rest;


import com.mwaysolution.sapMock.model.User;
import com.mwaysolution.sapMock.model.UserRegistrationStatus;
import com.mwaysolution.sapMock.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("users")
public class UserRestController {

    @Value("${syncItem.register.url}")
    private String registerUrl;

    @Value("${syncItem.unregister.url}")
    private String unregisterUrl;

    @Autowired
    private RestTemplate restTemplate;

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
        User user = userService.getById(id);
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        HttpEntity<User> entity = new HttpEntity<>(user, headers);
        if (restTemplate.exchange(
                registerUrl +
                        "?username=" + user.getSapUsername() +
                        "&email=" + user.getExchangeUsername() +
                        "&domain=" + user.getExchangeDomain(),
                HttpMethod.GET, entity, String.class).getStatusCodeValue() == 200) {
            user.setRegistrationStatus(UserRegistrationStatus.REGISTERED);
            return userService.save(user);
        }
        return user;
    }

    @PutMapping("{id}/unregister")
    public User unregister(@PathVariable("id") Integer id) {
        User user = userService.getById(id);
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        HttpEntity<User> entity = new HttpEntity<>(user, headers);
        if (restTemplate.exchange(
                unregisterUrl +
                        "?username=" + user.getSapUsername(),
                HttpMethod.GET, entity, String.class).getStatusCodeValue() == 200) {
            user.setRegistrationStatus(UserRegistrationStatus.UNREGISTERED);
            return userService.save(user);
        }
        return user;
    }
}
