package com.fdmgroup;


import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@NamedQuery(name = "findPatronByName", query = "SELECT p FROM Patron p WHERE p.name LIKE :name")
@Entity
@Table(name = "PATRONS")
public class Patron extends Person implements SearchForBook {
    @OneToOne
    @JoinColumn(name = "FK_ACCOUNT_ID")
    private Account account;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PATRON_ID")
    private Library library;

    public Patron() {
    }

    public Patron(String name, Library library) {
        super(name);
        this.library = library;
    }

    public Library getLibrary() {
        return library;
    }

    public void setLibrary(Library library) {
        this.library = library;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public List<LibraryBook> getBorrowedBooks() {
        return account.getBorrowedBooks();
    }

    public void checkOutBook(Account account, LibraryBook book) {
        Librarian librarian = new Librarian("Betty", "Manager", library);
        librarian.manageAccount(account, Action.ADD_BOOK_TO_BORROWED_BOOKS, book);
    }

    public void returnBook(Account account, LibraryBook book) {
        Librarian librarian = new Librarian("Betty", "Manager", library);
        librarian.manageAccount(account, Action.REMOVE_BOOK_FROM_BORROWED_BOOKS, book);
    }

    public void payFees(Account account) {
        Librarian librarian = new Librarian("Betty", "Manager", library);
        librarian.manageAccount(account, Action.REMOVE_CHARGES_FROM_ACCOUNT);
    }

    @Override
    public LibraryBook searchLibrary(String searchCriteria) {
        for (LibraryBook book : library.getLibraryBooks()) {
            for (Author author : book.getAuthors()) {
                if (author.getName() == searchCriteria) {
                    return book;
                }
            }
            if (book.getTitle() == searchCriteria) {
                return book;
            } else if (book.getBookId() == Integer.parseInt(searchCriteria)) {
                return book;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return "Patron [account=" + account + ", library=" + library + "] [" + super.toString() + "]";
    }

}
