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

    public static List<Librarian> findAllLibrarians() {
        final EntityManager em = emf.createEntityManager();
        final String jpql = "SELECT l FROM librarian l";
        final TypedQuery<Librarian> query = em.createQuery(jpql, Librarian.class);
        final List<Librarian> results = query.getResultList();
        em.close();
        return results;
    }

    public static List<LibraryBook> findAllLibraryBooks() {
        final EntityManager em = emf.createEntityManager();
        final String jpql = "SELECT l FROM LibraryBook l";
        final TypedQuery<LibraryBook> query = em.createQuery(jpql, LibraryBook.class);
        final List<LibraryBook> results = query.getResultList();
        em.close();
        return results;
    }

    public static List<LibraryBook> findAllLibraryBooksByPatron(Patron patron) {
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

    public static Optional<Patron> findByLibraryBook(LibraryBook book) {
        Optional<Patron> foundPatron = Optional.empty();
        final EntityManager em = emf.createEntityManager();
        final String jpql = "SELECT book.checkOutBy FROM LibraryBook l WHERE l = :book";
        final TypedQuery<Patron> query = em.createQuery(jpql, Patron.class);
        query.setParameter("book", book);
        final List<Patron> results = query.getResultList();
        if (!results.isEmpty())
            foundPatron = Optional.of(results.get(0));
        em.close();
        return foundPatron;
    }

    public static Optional<Patron> findByName(final String name) {
        Optional<Patron> foundPatron = Optional.empty();
        final EntityManager em = emf.createEntityManager();
        final TypedQuery<Patron> query = em.createNamedQuery("findByName", Patron.class);
        query.setParameter("name", name);
        final List<Patron> results = query.getResultList();
        if (!results.isEmpty())
            foundPatron = Optional.of(results.get(0));
        em.close();
        return foundPatron;
    }

    public static void main(String[] args) {

        emf = Persistence.createEntityManagerFactory("JPA");

        EntityManager em = emf.createEntityManager();

        Library library = new Library("The Great Library of Alexandria");
        Patron patron1 = new Patron("Nick", library);
        List<String> authorList = new ArrayList<String>();
        authorList.add("JK Rowling");
        LibraryBook hp1 = new LibraryBook("Harry Potter and the Sorcerer's Stone", authorList, new BigDecimal(20),
                library);
        LibraryBook hp2 = new LibraryBook("Harry Potter and the Sorcerer's Stone", authorList, new BigDecimal(20),
                library);

        hp1 = em.merge(hp1);
        hp2 = em.merge(hp2);
        patron1.checkOutBook(hp1);
        patron1.checkOutBook(hp2);
        patron1 = em.merge(patron1);
        hp1.setCheckedOutBy(patron1);
        hp2.setCheckedOutBy(patron1);

        em.getTransaction().commit();

        em.close();

        em = emf.createEntityManager();

        final List<Patron> patronResults = findAllPatrons();
        System.out.println(patronResults);

        final List<LibraryBook> bookResults = findAllLibraryBooksByPatron(patron1);
        System.out.println(bookResults);

        final Optional<Patron> foundPatron = findByLibraryBook(hp1);
        if (foundPatron.isPresent())
            System.out.println(foundPatron.get());

        em.close();

        emf.close();

    }
}
