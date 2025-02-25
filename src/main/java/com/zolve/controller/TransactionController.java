package com.zolve.controller;

import com.zolve.model.User;
import com.zolve.service.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TransactionController {

    @Autowired
    Service service;

    @GetMapping("/user/{userId}/balance")
    public ResponseEntity<Double> getUserBalance(@PathVariable String userId) {
        return ResponseEntity.ok(service.getUserBalance(userId));
    }

    @PostMapping("/users/balances")
    public ResponseEntity<List<User>> getBalancesForUsers(@RequestBody List<String> userIds) {
        return ResponseEntity.ok(service.getBalancesForUsers(userIds));
    }

    @GetMapping("/users/all/balances")
    public ResponseEntity<List<User>> getBalancesForUsers() {
        return ResponseEntity.ok(service.getAllUsersBalance());
    }
}
