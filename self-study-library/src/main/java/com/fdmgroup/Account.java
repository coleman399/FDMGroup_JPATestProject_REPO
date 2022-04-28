package com.fdmgroup;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "ACCOUNT")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ACCOUNT_NUMBER")
    private int accountNumber;
    @Column(name = "CREATION_DATE")
    private Date creationDate;
    @ElementCollection
    @CollectionTable(name = "BORROWED_BOOKS", joinColumns = @JoinColumn(name = "ACCOUNT_NUMBER"))
    @Column(name = "LIBRARY_BOOK")
    private List<LibraryBook> borrowedBooks;
    @Column(name = "BALANCE")
    private BigDecimal balance;
    @OneToOne(mappedBy = "account")
    private Patron accountOwner;
    @ManyToOne
    @JoinColumn(name = "FK_LIBRARY_ID")
    private Library library;

    public Account() {
        
    }

    public Account(Patron accountOwner, Library library) {
        Date date = new Date(System.currentTimeMillis());
        this.creationDate = date;
        this.borrowedBooks = new ArrayList<LibraryBook>();
        this.balance = new BigDecimal(0);
        this.accountOwner = accountOwner;
        this.library = library;
    }

    public void addToBalance(BigDecimal amount) {
        balance.add(amount);
    }

    public void removeFromBalance(BigDecimal amount) {
        balance.subtract(amount);
    }

    public void removeBookFromBorrowedBooks(LibraryBook book) {
        borrowedBooks.remove(book);
    }

    public void addBookToBorrowedBooks(LibraryBook book) {
        borrowedBooks.add(book);
    }

    public int getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(int accountNumber) {
        this.accountNumber = accountNumber;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public List<LibraryBook> getBorrowedBooks() {
        return borrowedBooks;
    }

    public void setBorrowedBooks(List<LibraryBook> borrowedBooks) {
        this.borrowedBooks = borrowedBooks;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public Patron getAccountOwner() {
        return accountOwner;
    }

    public void setAccountOwner(Patron accountOwner) {
        this.accountOwner = accountOwner;
    }

    @Override
    public String toString() {
        return "Account [accountNumber=" + accountNumber + ", accountOwner=" + accountOwner + ", balance=" + balance
                + ", borrowedBooks=" + borrowedBooks + ", creationDate=" + creationDate + ", library=" + library + "]";
    }

}
