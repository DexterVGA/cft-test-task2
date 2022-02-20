package ui;

import entity.Book;
import entity.Reader;
import entity.Record;
import exception.ExceptionFrame;
import handler.BookHandler;
import handler.ReaderHandler;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.SqlDateModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Properties;

public class RecordAddFrame extends Frame implements ActionListener {
    private final TextField bookIdField;
    private final TextField readerIdField;
    private final JDatePickerImpl dateOfIssuePicker;
    private final JDatePickerImpl returnDatePicker;

    public RecordAddFrame() {
        Label bookIdLabel = new Label("Введите идентификатор книги:");
        Label readerIdLabel = new Label("Введите идентификатор читателя:");
        Label dateOfIssueLabel = new Label("Введите дату выдачи:");
        Label returnDateLabel = new Label("Введите дату возврата:");
        bookIdField = new TextField();
        readerIdField = new TextField();
        Button buttonAdd = new Button("Добавить");

        SqlDateModel dateOfIssueModel = new SqlDateModel();
        SqlDateModel returnDateModel = new SqlDateModel();
        Properties properties = new Properties();
        properties.put("text.day", "Day");
        properties.put("text.month", "Month");
        properties.put("text.year", "Year");
        JDatePanelImpl dateOfIssuePanel = new JDatePanelImpl(dateOfIssueModel, properties);
        JDatePanelImpl returnDatePanel = new JDatePanelImpl(returnDateModel, properties);
        dateOfIssuePicker = new JDatePickerImpl(dateOfIssuePanel, new CustomFormatter());
        returnDatePicker = new JDatePickerImpl(returnDatePanel, new CustomFormatter());

        bookIdLabel.setBounds(10, 30, 200, 20);
        bookIdField.setBounds(10, 55, 280, 20);
        readerIdLabel.setBounds(10, 80, 200, 20);
        readerIdField.setBounds(10, 105, 280, 20);
        dateOfIssueLabel.setBounds(10, 130, 150, 20);
        dateOfIssuePicker.setBounds(10, 155, 280, 30);
        returnDateLabel.setBounds(10, 190, 150, 20);
        returnDatePicker.setBounds(10, 215, 280, 30);
        buttonAdd.setBounds(100, 250, 100, 30);

        buttonAdd.addActionListener(this);

        this.add(bookIdLabel);
        this.add(bookIdField);
        this.add(readerIdLabel);
        this.add(readerIdField);
        this.add(dateOfIssueLabel);
        this.add(returnDateLabel);
        this.add(dateOfIssuePicker);
        this.add(returnDatePicker);
        this.add(buttonAdd);

        this.setResizable(false);
        this.setBounds(1000, 350, 300, 290);
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

    static class CustomFormatter extends JFormattedTextField.AbstractFormatter {
        @Override
        public Object stringToValue(String text) {
            return null;
        }

        @Override
        public String valueToString(Object value) {
            if (value == null) {
                return "";
            }
            Calendar calendar = (Calendar) value;
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
            return dateFormat.format(calendar.getTime());
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if ("Добавить".equals(e.getActionCommand())) {
            long bookId;
            try {
                bookId = Long.parseLong(bookIdField.getText());
            } catch (NumberFormatException ex) {
                new ExceptionFrame("Неверный идентификатор книги!");
                return;
            }

            long readerId;
            try {
                readerId = Long.parseLong(readerIdField.getText());
            } catch (NumberFormatException ex) {
                new ExceptionFrame("Неверный идентификатор читателя!");
                return;
            }

            Book book = BookHandler.showBook(bookId);
            if (book == null) {
                new ExceptionFrame("Данной книги не существует!");
                return;
            }
            Reader reader = ReaderHandler.showReader(readerId);
            if (reader == null) {
                new ExceptionFrame("Данного читателя не существует!");
                return;
            }

            JTextField dateOfIssueField = dateOfIssuePicker.getJFormattedTextField();
            JTextField returnDateField = returnDatePicker.getJFormattedTextField();

            Record record = new Record(book.getId(), reader.getId(), dateOfIssueField.getText(), returnDateField.getText());
            create(MainFrame.sessionFactory, record);

            this.dispose();
        }
    }

    private static void create(SessionFactory sessionFactory, Record record) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.save(record);
        session.getTransaction().commit();
        System.out.println("Запись успешно добавлена!");
    }
}
