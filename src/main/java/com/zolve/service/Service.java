package com.zolve.service;

import com.zolve.model.User;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface Service {

    List<User> getBalancesForUsers(List<String> userIds);

    List<User> getAllUsersBalance();

    Double getUserBalance(String userId);
}
