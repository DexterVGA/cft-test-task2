package handler;

import entity.Record;
import org.hibernate.Session;
import ui.MainFrame;
import ui.RecordFrame;

import java.util.List;

public class RecordHandler {
    public static void addRecord() {
        new RecordFrame();
    }

    public static List<Record> showAllRecords() {
        Session session = MainFrame.sessionFactory.openSession();
        @SuppressWarnings("unchecked")
        List<Record> listOfRecords = session.createQuery("FROM Record").list();
        session.close();

        return listOfRecords;
    }
}
