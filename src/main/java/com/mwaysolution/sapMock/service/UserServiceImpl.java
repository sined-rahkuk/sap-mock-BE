package com.mwaysolution.sapMock.service;

import com.mwaysolution.sapMock.model.User;
import org.springframework.beans.BeanUtils;
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


    public User save(User user) throws RuntimeException {
        if (userService.findBySapUsername(user.getSapUsername()) == null){
            user.setCreationDate(ZonedDateTime.now());
            user.setModificationDate(ZonedDateTime.now());
            return userService.save(user);
        }
        throw new RuntimeException("Current user already created");
    }

    public void deleteById(Integer id){
        userService.delete(userService.findById(id));
    }


    public User update(String sapUsername, User user) {
        user.setModificationDate(ZonedDateTime.now());
        BeanUtils.copyProperties(user, userService.findBySapUsername(sapUsername), "id", "creationDate");
        return userService.save(userService.findBySapUsername(sapUsername));
    }

}
