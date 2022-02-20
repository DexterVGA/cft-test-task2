package handler;

import entity.Reader;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import ui.MainFrame;
import ui.ReaderFrame;

import java.util.List;

public class ReaderHandler {
    /**
     * Находит читателя в базе данных и возвращает его для вывода на экран
     *
     * @param id идентификатор читателя, которого необходимо вывести на экран
     * @return Reader - читатель, который будет отображен на экране
     */
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

    /**
     * Получает из базы данных всех читателей
     *
     * @return List<Reader> - список всех читателей
     */
    public static List<Reader> showAllReaders() {
        Session session = MainFrame.sessionFactory.openSession();
        @SuppressWarnings("unchecked")
        List<Reader> listOfReaders = session.createQuery("FROM Reader").list();
        session.close();

        return listOfReaders;
    }

    /**
     * Открывает новое окно для редактирования существующго читателя
     *
     * @param id идентификатор читателя, которого необходимо редактировать
     */
    public static void updateReader(long id) {
        new ReaderFrame("Изменить", id);
    }

    /**
     * Удаляет читателя из базы данных
     *
     * @param id идентификатор читателя, которого необходимо удалить
     */
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

    /**
     * Открывает новое окно для создания и добавления читателя в базу данных
     */
    public static void addReader() {
        new ReaderFrame("Добавить", null);
    }
}
