package ui;

import entity.Book;
import entity.Record;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

public class RecordShowFrame extends JFrame {

    public RecordShowFrame(List<Record> listOfRecords) {
        String[] columnNames = {"ID книги", "ID читателя", "Дата выдачи", "Дата возврата"};
        DefaultTableModel model = new DefaultTableModel();
        JTable table = new JTable(model);
        table.setModel(model);
        table.setFillsViewportHeight(true);
        model.setColumnIdentifiers(columnNames);

        model.addRow(columnNames);
        int rowCount = model.getRowCount();
        for (int i = 1; i < rowCount; i++) {
            model.removeRow(1);
        }
        for (Record record : listOfRecords) {
            Long bookId = record.getBookId();
            Long readerId = record.getReaderId();
            String dateOfIssue = record.getDateOfIssue();
            String returnDate = record.getReturnDate();
            model.addRow(new Object[]{bookId, readerId, dateOfIssue, returnDate});
        }

        table.setBounds(5, 5, 475, 250);

        this.add(table);

        this.setLayout(new FlowLayout());
        this.setResizable(false);
        this.setBounds(1000, 350, 500, 300);
        this.setTitle("Выдача книги");
        this.setLayout(null);
        this.setVisible(true);

        addWindowListener(new WindowAdapter() {
                              public void windowClosing(WindowEvent we) {
                                  dispose();
                              }
                          }
        );
    }
}
