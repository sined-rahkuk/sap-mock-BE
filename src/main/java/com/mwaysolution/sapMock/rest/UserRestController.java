package com.mwaysolution.sapMock.rest;


import com.mwaysolution.sapMock.model.User;
import com.mwaysolution.sapMock.model.UserRegistrationStatus;
import com.mwaysolution.sapMock.service.UserService;
import org.springframework.beans.BeanUtils;
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

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private UserService userService;

    @GetMapping
    public List<User> get() {
        return userService.findAll();
    }

    @GetMapping("{id}")
    public User getById(@PathVariable("id") Integer id) {
        return userService.findById(id);
    }

    @PostMapping
    public User create(@RequestBody User user) {
        return userService.save(user);
    }

    @DeleteMapping("{id}")
    public void deleteById(@PathVariable("id") Integer id) {
        userService.deleteById(Long.valueOf(id));
    }

    @PutMapping("{id}")
    public User update(@PathVariable("id") Integer userID, @RequestBody User user) {
        BeanUtils.copyProperties(user, userService.findById(userID), "id");
        return userService.save(userService.findById(userID));
    }

    @PutMapping("{id}/register")
    public String register(@PathVariable("id") Integer id) {
        User user = userService.findById(id);
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        HttpEntity<User> entity = new HttpEntity<>(user, headers);

        ResponseEntity<String> responseEntity = restTemplate.exchange(
                registerUrl + "/" + id, HttpMethod.POST, entity, String.class);

        if (responseEntity.getStatusCodeValue() == 200) {
            user.setRegistrationStatus(UserRegistrationStatus.REGISTERED);
            userService.save(user);
            return responseEntity.getBody();
        }
        return null;
    }
}
