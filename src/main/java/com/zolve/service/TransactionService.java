package com.zolve.service;

import com.zolve.model.Transaction;
import com.zolve.model.TransactionType;
import com.zolve.model.User;
import com.zolve.repository.TransactionRepository;
import com.zolve.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.mysql.cj.util.TimeUtil.DATE_FORMATTER;

@Component
public class TransactionService implements Service{

    @Autowired
    UserRepository userRepository;

    @Autowired
    TransactionRepository transactionRepository;
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

    public Double getUserBalanceAsOfDate(String userId, String date) {
        LocalDate parsedDate = LocalDate.parse(date, DATE_FORMATTER);
        List<Transaction> transactions = transactionRepository.findByUserIdAndTransactionDateBefore(userId, parsedDate.plusDays(1));

        double balance = 0.0;
        for (Transaction txn : transactions) {
            if (txn.getTransactionType() == TransactionType.CREDIT) {
                balance += txn.getAmount();
            } else {
                balance -= txn.getAmount();
            }
        }

        return balance;
    }

    private User getUser(String userId){
        return userRepository.findById(userId).orElse(null);
    }
}
