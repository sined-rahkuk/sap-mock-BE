package com.mwaysolution.sapMock.service;

import com.mwaysolution.sapMock.model.User;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;

@Repository
public interface UserService{
    Optional<User> get(int id);
    Collection<User> getAll();
    void save(User user);
    void update(User user);
    void delete(User user);

}
