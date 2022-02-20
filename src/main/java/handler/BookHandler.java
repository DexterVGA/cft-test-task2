package handler;

import entity.Book;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import ui.BookFrame;
import ui.MainFrame;

import java.util.List;

public class BookHandler {
    /**
     * Находит книгу в базе данных и возвращает её для вывода на экран
     *
     * @param id идентификатор книги, которую необходимо вывести на экран
     * @return Book - книга, которая будет отображена на экране
     */
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

    /**
     * Получает из базы данных все книги
     *
     * @return List<Book> - список всех книг
     */
    public static List<Book> showAllBooks() {
        Session session = MainFrame.sessionFactory.openSession();
        @SuppressWarnings("unchecked")
        List<Book> listOfBooks = session.createQuery("FROM Book").list();
        session.close();

        return listOfBooks;
    }

    /**
     * Открывает новое окно для редактирования существующей книги
     *
     * @param id идентификатор книги, которую необходимо редактировать
     */
    public static void updateBook(long id) {
        new BookFrame("Изменить", id);
    }

    /**
     * Удаляет книгу из базы данных
     *
     * @param id идентификатор книги, которую неодходимо удалить
     */
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

    /**
     * Открывает новое окно для создания и добавления книги в базу данных
     */
    public static void addBook() {
        new BookFrame("Добавить", null);
    }
}
