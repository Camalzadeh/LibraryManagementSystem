package models;

import enums.TransactionType;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class Transaction {
    String transactionId;
    String userId;
    String bookId;
    String borrowDate;
    String returnDate;
    TransactionType transactionType;
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public Transaction() {}

    public Transaction(String transactionId, String userId, String bookId, String borrowDate) {
        this.transactionId = transactionId;
        this.userId = userId;
        this.bookId = bookId;
        this.borrowDate = borrowDate;
        returnDate = null;
        this.transactionType = TransactionType.BORROW;
    }

    public void setBookId(String bookId) {this.bookId = bookId;}
    public void setUserId(String userId) {this.userId = userId;}
    public void setTransactionId(String transactionId) {this.transactionId = transactionId;}
    public void setTransactionType(TransactionType transactionType) {this.transactionType = transactionType;}
    public void setBorrowDate(String borrowDate) {this.borrowDate = borrowDate;}
    public void setReturnDate(String returnDate) {this.returnDate = returnDate;}
    public String getBookId() {return bookId;}
    public String getUserId() {return userId;}
    public String getTransactionId() {return transactionId;}
    public TransactionType getTransactionType() {return transactionType;}
    public String getBorrowDate() {return borrowDate;}
    public String getReturnDate() {return returnDate;}

    @Override
    public String toString() {
        return "Transaction [bookId=" + bookId + ", borrowDate=" + borrowDate + ", returnDate=" + returnDate
                + ", transactionId=" + transactionId + ", transactionType=" + transactionType + ", userId=" + userId
                + "]";
    }

    public void transactionFees(){
        LocalDate date = LocalDate.now();
        LocalDate borrowDate = LocalDate.parse(this.borrowDate, formatter);
        if(transactionType.equals(TransactionType.BORROW)) {
            long days = ChronoUnit.DAYS.between(date, borrowDate);
            if(days>10) {
                System.out.println("Transaction fees: " + (5+(days-10)*3));
            }
            else {
                System.out.println("Transaction fees: 5");
            }
        }
        else if(transactionType.equals(TransactionType.RETURN)) {
            LocalDate returnDate = LocalDate.parse(this.returnDate, formatter);
            long days = ChronoUnit.DAYS.between(borrowDate, returnDate);
            if(days>10) {
                System.out.println("Transaction fees: " + (5+(days-10)*3));
            }
            else {
                System.out.println("Transaction fees: 5");
            }
        }
    }
}
