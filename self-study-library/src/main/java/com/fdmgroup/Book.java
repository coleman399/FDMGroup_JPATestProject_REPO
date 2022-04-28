package com.fdmgroup;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

@Inheritance(strategy = InheritanceType.JOINED)
@Entity
@Table(name = "BOOK")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "BOOK_ID")
    private int bookId;
    @Column(name = "BOOK_TITLE")
    private String title;
    @ElementCollection
    @CollectionTable(name = "AUTHORS", joinColumns = @JoinColumn(name = "AUTHOR_ID"))
    @Column(name = "AUTHOR")
    private List<String> authors;
    @Column(name = "PRICE")
    private BigDecimal price;

    public Book() {
        
    }

    public Book(String title, List<String> authors, BigDecimal price) {
        this.title = title;
        this.authors = authors;
        this.price = price;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getAuthors() {
        return authors;
    }

    public void setAuthors(List<String> authors) {
        this.authors = authors;
    }

    public void addAuthor(String author) {
        authors.add(author);
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Book [authors=" + authors + ", bookId=" + bookId + ", title=" + title + ", price=" + price + "]";
    }

}
