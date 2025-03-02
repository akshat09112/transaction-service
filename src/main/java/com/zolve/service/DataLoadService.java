package com.zolve.service;

import com.zolve.model.Transaction;
import com.zolve.model.TransactionType;
import com.zolve.model.User;
import com.zolve.repository.TransactionRepository;
import com.zolve.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Objects;

import static com.mysql.cj.util.TimeUtil.DATE_FORMATTER;

@Service
public class DataLoadService {
    @Value("${transaction.file.path}")
    private String transactionFilePath;

    @Autowired
    UserRepository userRepository;

    @Autowired
    TransactionRepository transactionRepository;

    @PostConstruct
    public void loadTransactionsOnStartup() {
        File file = new File(transactionFilePath);
        if (file.exists()) {
            System.out.println("Loading transactions from: " + transactionFilePath);
            processTransactionFile(file);
        } else {
            System.out.println("Transaction file not found at startup: " + transactionFilePath);
        }
    }

    public void processTransactionFile(File file) {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;

            reader.readLine();

            while ((line = reader.readLine()) != null) {
                String[] fields = line.split(",");
                if (fields.length < 5) continue;
                try {

                    String userId = fields[0].trim();
                    double amount = Double.parseDouble(fields[1].trim());
                    TransactionType txnType = TransactionType.valueOf(fields[2].trim().toUpperCase());
                    String txnId = fields[4].trim();
                    String merchant = fields[3].trim();
                    LocalDate txnDate = LocalDate.parse(fields[5], DATE_FORMATTER);
                    Transaction transaction = transactionRepository.findById(txnId).orElse(null);
                    if(Objects.nonNull(transaction)){
                        continue;
                    }

                    transaction=new Transaction();
                    transaction.setTransactionId(txnId);
                    transaction.setAmount(amount);
                    transaction.setMerchant(merchant);
                    transaction.setUserId(userId);
                    transaction.setTransactionType(txnType);
                    transaction.setTimestamp(txnDate);

                    if (txnType.equals(TransactionType.DEBIT)) {
                        amount = -amount;
                    }

                    User user = userRepository.findById(userId)
                            .orElse(new User(userId, 0.0));

                    user.updateBalance(amount);
                    userRepository.save(user);
                    transactionRepository.save(transaction);
                    System.out.println(user.getUserId()+" inserted into db");
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
            System.out.println("Transaction file processed successfully.");
        } catch (IOException e) {
            throw new RuntimeException("Error reading transaction file", e);
        }
    }
}
