package com.mwaysolution.sapMock.service;

import com.mwaysolution.sapMock.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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


    public User create(User user){
        if (userService.findBySapUsername(user.getSapUsername()) == null){
            return userService.save(user);
        }
        return null;
    }


    public void deleteById(Integer id){
        userService.delete(userService.findById(id));
    }


    public User update(Integer userID,User user) {
        BeanUtils.copyProperties(user, userService.findById(userID), "id");
        return userService.save(userService.findById(userID));
    }

}
