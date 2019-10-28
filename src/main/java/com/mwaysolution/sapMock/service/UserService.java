package com.mwaysolution.sapMock.service;

import com.mwaysolution.sapMock.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserService extends JpaRepository<User, Long> {
    //Optional<User> get(int id);
    //Collection<User> getAll();
    //void save(User user);
    //void update(User user);
    //void delete(User user);
    User findById(Integer id);
    User findBySapUsername(String sapUsername);
}
