package com.zolve.service;

import com.zolve.model.User;
import com.zolve.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
public class TransactionService implements Service{

    @Autowired
    UserRepository userRepository;
    @Override
    public List<User> getBalancesForUsers(List<String> userIds) {
        List<User> users=new ArrayList<>();
        for(String userId: userIds){
            User user = getUser(userId);
            if(Objects.isNull(user)){
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User "+userId+ " not found");
            }
            users.add(user);
        }
        return users;
    }

    @Override
    public List<User> getAllUsersBalance() {
        return userRepository.findAll();
    }

    @Override
    public Double getUserBalance(String userId) {
        User user = getUser(userId);
        if(Objects.isNull(user)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User "+userId+ " not found");
        }
        return user.getBalance();
    }

    public User getUser(String userId){
        return userRepository.findById(userId).orElse(null);
    }
}
