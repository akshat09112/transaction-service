package com.zolve;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TransactionApplication {
    public static void main(String[] args) {
        int a=1;
        int random =(int) (Math.random() * a) + 1;
        SpringApplication.run(TransactionApplication.class, args);
    }
}