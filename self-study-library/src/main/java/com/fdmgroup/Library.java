package com.fdmgroup;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "LIBRARIES")
public class Library {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "LIBRARY_ID")
    private int id;
    @Column(name = "LIBRARY_NAME")
    private String libraryName;
    @OneToMany(mappedBy = "library", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<LibraryBook> libraryBooks = new ArrayList<LibraryBook>();
    @OneToMany(mappedBy = "library")
    private List<Librarian> librarians = new ArrayList<Librarian>();
    @OneToMany(mappedBy = "library")
    private List<Account> accounts = new ArrayList<Account>();
    @OneToMany(mappedBy = "library")
    private List<Patron> patrons = new ArrayList<Patron>();

    public Library() {
    }

    public Library(String libraryName) {
        this.libraryName = libraryName;
    }

    public void addBookToLibrary(LibraryBook libraryBook) {
        libraryBooks.add(libraryBook);
    }

    public void removeBookFromLibrary(LibraryBook libraryBook) {
        libraryBooks.remove(libraryBook);
    }

    public List<LibraryBook> getLibraryBooks() {
        return libraryBooks;
    }

    public void setLibraryBooks(List<LibraryBook> libraryBooks) {
        this.libraryBooks = libraryBooks;
    }

    public String getLibraryName() {
        return libraryName;
    }

    public void setLibraryName(String libraryName) {
        this.libraryName = libraryName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Librarian> getLibrarians() {
        return librarians;
    }

    public void setLibrarians(List<Librarian> librarians) {
        this.librarians = librarians;
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }

    public List<Patron> getPatrons() {
        return patrons;
    }

    public void setPatrons(List<Patron> patrons) {
        this.patrons = patrons;
    }

    public void addLibrarian(Librarian librarian) {
        librarians.add(librarian);
    }

    public void addPatron(Patron patron) {
        patrons.add(patron);
    }

    public void addAccount(Account account) {
        accounts.add(account);
    }

    @Override
    public String toString() {
        return "Library [accounts=" + accounts + ", id=" + id + ", librarians=" + librarians + ", libraryBooks="
                + libraryBooks + ", libraryName=" + libraryName + ", patrons=" + patrons + "]";
    }

}
