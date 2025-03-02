# transaction-service

Install Java

1. brew install openjdk@17
2. java -version
3. nano ~/.zshrc
4. export JAVA_HOME=$(/usr/libexec/java_home -v 17)
export PATH=$JAVA_HOME/bin:$PATH
5. source ~/.zshrc

Install mysql

1. brew install mysql
2. brew services start mysql
3. mysql -u root
4. Create database: CREATE DATABASE <db_name>;
5. Create user: CREATE USER '<user_name>'@'localhost' IDENTIFIED BY '<password>';
6. GRANT ALL PRIVILEGES ON <db_name>.* TO '<user_name>'@'localhost';
7. Create user table: CREATE TABLE user (user_id VARCHAR(255) NOT NULL,balance DOUBLE,PRIMARY KEY (user_id));
8. Create transaction table: CREATE TABLE transaction (transaction_id VARCHAR(255) NOT NULL,merchant VARCHAR(255),user_id VARCHAR(255) NOT NULL,transaction_type ENUM('CREDIT', 'DEBIT') NOT NULL,timestamp DATE NOT NULL,amount DOUBLE,PRIMARY KEY (transaction_id),FOREIGN KEY (user_id) REFERENCES user(user_id));

Running the application

application.properties:
transaction.file.path=<path_to_csv_transaction_file>

spring.datasource.url=jdbc:mysql://localhost:3306/<db_name>
spring.datasource.username=<user_name>
spring.datasource.password=<password>
