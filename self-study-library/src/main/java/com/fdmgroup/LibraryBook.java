package com.fdmgroup;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "LIBRARY_BOOKS")
public class LibraryBook extends Book {
    @Column(name = "DUE_DATE")
    private Date dueDate;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "LIBRARY_BOOK_ID")
    private Library library;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PATRON_ID")
    private Patron checkedOutBy;

    public LibraryBook() {

    }

    public LibraryBook(String title, List<Author> authors, BigDecimal price) {
        super(title, authors, price);
    }

    public LibraryBook(String title, List<Author> authors, BigDecimal price, Library library) {
        super(title, authors, price);
        this.library = library;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public Library getLibrary() {
        return library;
    }

    public void setLibrary(Library library) {
        this.library = library;
    }

    public Patron getCheckedOutBy() {
        return checkedOutBy;
    }

    public void setCheckedOutBy(Patron checkedOutBy) {
        this.checkedOutBy = checkedOutBy;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    @Override
    public String toString() {
        return "LibraryBook [dueDate=" + dueDate + ", library=" + library + "]";
    }

}
