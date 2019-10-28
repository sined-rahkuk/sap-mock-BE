package com.mwaysolution.sapMock.service;

import com.mwaysolution.sapMock.model.User;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class UserServiceImpl implements UserService {

    private List<User> userList = new ArrayList<>();

    @Override
    public Optional<User> get(int id) {
        return Optional.ofNullable(userList.get(id));
    }

    @Override
    public Collection<User> getAll() {
        return userList.stream().filter(Objects::nonNull).collect(Collectors.collectingAndThen(Collectors.toList(), Collections::unmodifiableList));
    }

    @Override
    public void save(User user) {
        userList.add(user);
    }

    @Override
    public void update(User user) {
        userList.set(user.getId(), user);
    }

    @Override
    public void delete(User user) {
        userList.set(user.getId(), null);
    }
}
