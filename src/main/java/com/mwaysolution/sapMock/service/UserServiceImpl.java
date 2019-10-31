package com.mwaysolution.sapMock.service;

import com.mwaysolution.sapMock.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.List;

@Service
public class UserServiceImpl {

    @Autowired
    private UserService userService;

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

        return userService.save(userSaved);
    }

}
