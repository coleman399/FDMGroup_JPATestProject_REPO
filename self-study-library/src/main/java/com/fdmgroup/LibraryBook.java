package com.fdmgroup;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "LIBRARY_BOOKS")
public class LibraryBook extends Book {
    @Column(name = "DUE_DATE")
    private Date dueDate;
    @ManyToOne
    @JoinColumn(name = "LIBRARY_ID")
    private Library library;
    @OneToOne
    @JoinColumn(name = "CHECKED_OUT_BY")
    private Patron checkedOutBy;
    @ElementCollection
    @CollectionTable(name = "AUTHORS", joinColumns = @JoinColumn(name = "id", referencedColumnName = "ID"))
    @Column(name = "AUTHOR")
    private List<Author> authors;

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

    public List<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(List<Author> authors) {
        this.authors = authors;
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
