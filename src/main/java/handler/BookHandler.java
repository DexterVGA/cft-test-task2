package handler;

import entity.Book;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import ui.BookFrame;
import ui.MainFrame;

import java.util.List;

public class BookHandler {
    public static Book showBook(long id) {
        Session session = MainFrame.sessionFactory.openSession();
        @SuppressWarnings("unchecked")
        Query<Book> query = session.createQuery("FROM Book WHERE id=:bookId");
        query.setParameter("bookId", id);
        Book book = query.uniqueResult();
        System.out.println(book);
        session.close();

        return book;
    }

    public static List<Book> showAllBooks() {
        Session session = MainFrame.sessionFactory.openSession();
        @SuppressWarnings("unchecked")
        List<Book> listOfBooks = session.createQuery("FROM Book").list();
        session.close();

        return listOfBooks;
    }

    public static void updateBook(long id) {
        new BookFrame("Изменить", id);
    }

    public static void deleteBook(long id) {
        Session session = MainFrame.sessionFactory.openSession();
        String hql = "DELETE FROM Book WHERE id=:bookId";
        Transaction transaction = session.beginTransaction();
        @SuppressWarnings("unchecked")
        Query<Book> query = session.createQuery(hql);
        query.setParameter("bookId", id);
        int result = query.executeUpdate();
        transaction.commit();
        System.out.println("Rows affected: " + result);
    }

    public static void addBook() {
        new BookFrame("Добавить", null);
    }
}
