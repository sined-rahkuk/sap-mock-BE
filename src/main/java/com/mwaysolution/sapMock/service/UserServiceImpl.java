package com.mwaysolution.sapMock.service;

import com.mwaysolution.sapMock.model.User;
import com.mwaysolution.sapMock.model.UserRegistrationStatus;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.List;

@Service
public class UserServiceImpl {

    @Value("${syncItem.register.url}")
    private String registerUrl;

    @Value("${syncItem.unregister.url}")
    private String unregisterUrl;

    @Value("${syncItem.hostname}")
    private String hostname;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private UserService userService;

    private static final Logger logger = LogManager.getLogger(UserServiceImpl.class);

    public List<User> get(){
        return userService.findAll();
    }


    public User getById(Integer id) {
        return userService.findById(id);
    }


    public User save(User user) {

        if (user.getId() == null) {
            return create(user);
        }
        return update(user);
    }

    private User create(User user) throws IllegalArgumentException {
        if (userService.findBySapUsername(user.getSapUsername()) != null) {
            throw new IllegalArgumentException("Current user " + user.getSapUsername() + " already created");
        }
        user.setCreationDate(ZonedDateTime.now());
        user.setModificationDate(ZonedDateTime.now());
        return userService.save(user);
    }

    public void deleteById(Integer id){
        userService.delete(userService.findById(id));
    }


    private User update(User user) {
        User userSaved = getById(user.getId());
        userSaved.setModificationDate(ZonedDateTime.now());
        userSaved.setLastName(user.getLastName());
        userSaved.setFirstName(user.getFirstName());
        userSaved.setTimeZone(user.getTimeZone());
        userSaved.setRegistrationStatus(user.getRegistrationStatus());

        return userService.save(userSaved);
    }

    public User register(User user){
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        HttpEntity<User> entity = new HttpEntity<>(user, headers);
        if (restTemplate.exchange(
                hostname + registerUrl +
                        "?username=" + user.getSapUsername() +
                        "&email=" + user.getExchangeUsername() +
                        "&domain=" + user.getExchangeDomain() +
                        "&timezone=" + user.getTimeZone(),
                HttpMethod.GET, entity, String.class).getStatusCodeValue() == 200) {
            logger.info("User " + user.getSapUsername().toUpperCase() + " was registered!");
            user.setRegistrationStatus(UserRegistrationStatus.REGISTERED);
            return userService.save(user);
        } else {
            logger.error("Something went wrong. User " + user.getSapUsername().toUpperCase() + " wasn't registered!");
            return user;
        }
    }

    public User unregister(User user){
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        HttpEntity<User> entity = new HttpEntity<>(user, headers);
        if (restTemplate.exchange(
                hostname + unregisterUrl +
                        "?username=" + user.getSapUsername(),
                HttpMethod.GET, entity, String.class).getStatusCodeValue() == 200) {
            logger.info("User " + user.getSapUsername().toUpperCase() + " was unregistered!");
            user.setRegistrationStatus(UserRegistrationStatus.UNREGISTERED);
            return userService.save(user);
        } else {
            logger.error("Something went wrong. User " + user.getSapUsername().toUpperCase() + " wasn't unregistered!");
            return user;
        }
    }

}
