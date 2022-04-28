package com.fdmgroup;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;

@Entity
public class Author extends Person {
    @ManyToMany
    private List<Book> writtenWorks;

    public Author(String name) {
        super(name);
        this.writtenWorks = new ArrayList<Book>();
    }

    public List<Book> getWrittenBooks() {
        return writtenWorks;
    }

    public void setBooks(List<Book> books) {
        this.writtenWorks = books;
    }

    public Book writeBook(String title, BigDecimal price) {
        List<String> authors = new ArrayList<String>();
        authors.add(super.getName());
        Book book = new Book(title, authors, price);
        writtenWorks.add(book);
        return book;
    }

    @Override
    public String toString() {
        return "Author  writtenWorks=" + writtenWorks + "]";
    }

}
