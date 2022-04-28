package com.fdmgroup;


import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "PATRONS")
public class Patron extends Person implements SearchForBook {
    @OneToOne
    @JoinColumn(name = "FK_ACCOUNT_ID")
    private Account account;
    @ManyToOne
    @JoinColumn(name = "FK_LIBRARY_ID")
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

    public void checkOutBook(LibraryBook book) {
        Librarian librarian = new Librarian("Betty", "Manager", library);
        librarian.manageAccount(account, Action.ADD_BOOK_TO_BORROWED_BOOKS, book);
    }

    public void returnBook(LibraryBook book) {
        Librarian librarian = new Librarian("Betty", "Manager", library);
        librarian.manageAccount(account, Action.REMOVE_BOOK_FROM_BORROWED_BOOKS, book);
    }

    public void payFees() {
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
        return "Patron [account=" + account + ", library=" + library + "]";
    }

}
