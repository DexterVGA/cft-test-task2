package handler;

import entity.Record;
import org.hibernate.Session;
import ui.MainFrame;
import ui.RecordAddFrame;
import ui.RecordShowFrame;

import java.util.List;

public class RecordHandler {
    /**
     * Открывает новое окно для создания и добавления записи о выдаче книги
     */
    public static void addRecord() {
        new RecordAddFrame();
    }

    /**
     * Получает из базы данных все записи о выдаче книг
     */
    public static void showAllRecords() {
        Session session = MainFrame.sessionFactory.openSession();
        @SuppressWarnings("unchecked")
        List<Record> listOfRecords = session.createQuery("FROM Record").list();
        session.close();

        new RecordShowFrame(listOfRecords);
    }
}
