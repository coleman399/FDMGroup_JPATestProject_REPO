package com.fdmgroup;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "LIBRARIANS")
public class Librarian extends Person implements SearchForBook, ManageAccounts {
    @Column(name = "POSITION")
    private String position;
    @ManyToOne
    private Library library;

    public Librarian() {

    }

    public Librarian(String name, String position, Library library) {
        super(name);
        this.position = position;
        this.library = library;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public Library getLibrary() {
        return library;
    }

    public void setLibrary(Library library) {
        this.library = library;
    }

    @Override
    public void manageAccount(Account account, Action action) {
        java.util.Date date = new java.util.Date();
        java.sql.Date sqlDate = new Date(date.getTime());
        if (action == Action.CHECK_FOR_LATE_BOOKS) {
            List<LibraryBook> books = account.getBorrowedBooks();
            for (LibraryBook book : books) {
                if (book.getDueDate().after(sqlDate)) {
                    account.addToBalance(book.getPrice());
                }
            }
        } else if (action == Action.REMOVE_CHARGES_FROM_ACCOUNT) {
            account.setBalance(new BigDecimal(0));
        }
    }

    public void manageAccount(Account account, Action action, LibraryBook libraryBook) {
        java.util.Date date = new java.util.Date();
        java.sql.Date sqlDate = new Date(date.getTime());
        if (action == Action.CHECK_FOR_LATE_BOOKS) {
            List<LibraryBook> books = account.getBorrowedBooks();
            for (LibraryBook book : books) {
                if (book.getDueDate().after(sqlDate)) {
                    account.addToBalance(book.getPrice());
                }
            }
        } else if (action == Action.REMOVE_CHARGES_FROM_ACCOUNT) {
            account.setBalance(new BigDecimal(0));
        } else if (action == Action.ADD_BOOK_TO_BORROWED_BOOKS) {
            account.addBookToBorrowedBooks(libraryBook);
            libraryBook.setCheckedOutBy(account.getAccountOwner());
        } else if (action == Action.REMOVE_BOOK_FROM_BORROWED_BOOKS) {
            account.removeBookFromBorrowedBooks(libraryBook);
            libraryBook.setCheckedOutBy(null);
        }
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
        return "Librarian [library=" + library + ", position=" + position + "]";
    }
}
