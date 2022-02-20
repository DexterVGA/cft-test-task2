package handler;

import entity.Record;
import org.hibernate.Session;
import ui.MainFrame;
import ui.RecordAddFrame;
import ui.RecordShowFrame;

import java.util.List;

public class RecordHandler {
    public static void addRecord() {
        new RecordAddFrame();
    }

    public static void showAllRecords() {
        Session session = MainFrame.sessionFactory.openSession();
        @SuppressWarnings("unchecked")
        List<Record> listOfRecords = session.createQuery("FROM Record").list();
        session.close();

        new RecordShowFrame(listOfRecords);
    }
}
