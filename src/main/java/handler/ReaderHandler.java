package handler;

import entity.Book;
import entity.Reader;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import ui.MainFrame;
import ui.ReaderFrame;

import java.util.List;

public class ReaderHandler {
    public static Reader showReader(long id) {
        Session session = MainFrame.sessionFactory.openSession();
        @SuppressWarnings("unchecked")
        Query<Reader> query = session.createQuery("FROM Reader WHERE id=:readerId");
        query.setParameter("readerId", id);
        Reader reader = query.uniqueResult();
        System.out.println(reader);
        session.close();

        return reader;
    }

    public static List<Reader> showAllReaders() {
        Session session = MainFrame.sessionFactory.openSession();
        @SuppressWarnings("unchecked")
        List<Reader> listOfReaders = session.createQuery("FROM Reader").list();
        session.close();

        return listOfReaders;
    }

    public static void updateReader(long id) {
        new ReaderFrame("Изменить", id);
    }

    public static void deleteReader(long id) {
        Session session = MainFrame.sessionFactory.openSession();
        String hql = "DELETE FROM Reader WHERE id=:readerId";
        Transaction transaction = session.beginTransaction();
        @SuppressWarnings("unchecked")
        Query<Reader> query = session.createQuery(hql);
        query.setParameter("readerId", id);
        int result = query.executeUpdate();
        transaction.commit();
        System.out.println("Rows affected: " + result);
    }

    public static void addReader() {
        new ReaderFrame("Добавить", null);
    }
}
