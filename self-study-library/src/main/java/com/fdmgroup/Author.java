package com.fdmgroup;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQuery;

@NamedQuery(name = "findAuthorByName", query = "SELECT a FROM Author a WHERE a.name LIKE :name")
@Entity
public class Author extends Person {
    @ManyToMany
    @JoinTable(name = "AUTHORS_BOOKS", 
    joinColumns=@JoinColumn(name = "AUTHOR_ID", referencedColumnName = "ID"),
    inverseJoinColumns = @JoinColumn(name = "BOOK_ID"))
    private List<LibraryBook> writtenWorks = new ArrayList<LibraryBook>();

    public Author() {
    }

    public Author(String name) {
        super(name);
    }

    public List<LibraryBook> getWrittenBooks() {
        return writtenWorks;
    }

    public void setBooks(List<LibraryBook> books) {
        this.writtenWorks = books;
    }

    public void addBookToWorks(LibraryBook book) {
        writtenWorks.add(book);
    }

    public LibraryBook writeBook(String title, BigDecimal price, Author author, Library library) {
        List<Author> authors = new ArrayList<Author>();
        authors.add(author);
        LibraryBook book = new LibraryBook(title, authors, price, library);
        writtenWorks.add(book);
        return book;
    }

    @Override
    public String toString() {
        return "Author [writtenWorks=" + writtenWorks + "] [" + super.toString();
    }

    
}
