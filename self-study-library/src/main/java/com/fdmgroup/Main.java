package com.fdmgroup;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

public class Main {

    static EntityManagerFactory emf;

    public static List<Library> findAllBooksInLibrary() {
        final EntityManager em = emf.createEntityManager();
        final String jpql = "SELECT l FROM Library l";
        final TypedQuery<Library> query = em.createQuery(jpql, Library.class);
        final List<Library> results = query.getResultList();
        em.close();
        return results;
    }

    public static List<Author> getAuthorByName(String name) {
        EntityManager em = emf.createEntityManager();
        final TypedQuery<Author> query = em.createNamedQuery("findAuthorByName", Author.class);
        query.setParameter("name", name);
        final List<Author> results = query.getResultList();
        em.close();
        return results;
    }

    public static List<LibraryBook> findAllBooksByPatron(Patron patron) {
        final EntityManager em = emf.createEntityManager();
        final String jpql = "SELECT l FROM LibraryBook l WHERE l.checkedOutBy = :patron";
        final TypedQuery<LibraryBook> query = em.createQuery(jpql, LibraryBook.class);
        query.setParameter("patron", patron);
        final List<LibraryBook> results = query.getResultList();
        em.close();
        return results;
    }

    public static List<String> findAllAuthors() {
        final EntityManager em = emf.createEntityManager();
        final String jpql = "SELECT a.name FROM Author a";
        final TypedQuery<String> query = em.createQuery(jpql, String.class);
        final List<String> results = query.getResultList();
        em.close();
        return results;
    }

    public static List<Patron> findAllPatrons() {
        final EntityManager em = emf.createEntityManager();
        final String jpql = "SELECT p FROM Patron p";
        final TypedQuery<Patron> query = em.createQuery(jpql, Patron.class);
        final List<Patron> results = query.getResultList();
        em.close();
        return results;
    }

    public static Optional<Patron> findPatronByLibraryBook(LibraryBook book) {
        Optional<Patron> foundPatron = Optional.empty();
        final EntityManager em = emf.createEntityManager();
        final String jpql = "SELECT book.checkOutBy FROM LibraryBook lb WHERE lb = :book";
        final TypedQuery<Patron> query = em.createQuery(jpql, Patron.class);
        query.setParameter("book", book);
        final List<Patron> results = query.getResultList();
        if (!results.isEmpty())
            foundPatron = Optional.of(results.get(0));
        em.close();
        return foundPatron;
    }

    public static Optional<Patron> findPatronByName(final String name) {
        Optional<Patron> foundPatron = Optional.empty();
        final EntityManager em = emf.createEntityManager();
        final TypedQuery<Patron> query = em.createNamedQuery("findPatronByName", Patron.class);
        query.setParameter("name", name);
        final List<Patron> results = query.getResultList();
        if (!results.isEmpty())
            foundPatron = Optional.of(results.get(0));
        em.close();
        return foundPatron;
    }

    public static void main(String[] args) {

        List<Author> authors = new ArrayList<Author>();
        Author JKR = new Author("JK Rowling");
        authors.add(JKR);
        Library library = new Library("The Library");
        LibraryBook book1 = new LibraryBook("Harry Potter 1", authors, new BigDecimal(20));
        LibraryBook book2 = new LibraryBook("Harry Potter 2", authors, new BigDecimal(20));

        emf = Persistence.createEntityManagerFactory("JPA");

        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.merge(JKR);
        em.getTransaction().commit();
        em.close();

        System.out.println("getAuthorByName: " + getAuthorByName("JK Rowling"));

        em = emf.createEntityManager();
        em.getTransaction().begin();
        library = em.merge(library);
        book1 = em.merge(book1);
        library.addBookToLibrary(book1);
        book2 = em.merge(book2);
        library.addBookToLibrary(book2);
        em.getTransaction().commit();
        em.close();

        System.out.println("findAllBooksInLibrary: " + findAllBooksInLibrary());
        System.out.println("findAllAuthors: " + findAllAuthors());


        em = emf.createEntityManager();
        em.getTransaction().begin();
        book1.setLibrary(library);
        book2.setLibrary(library);
        em.merge(book1);
        em.merge(book2);
        em.getTransaction().commit();
        em.close();

        System.out.println("findAllBooksInLibrary: " + findAllBooksInLibrary());

        emf.close();

    }
}
