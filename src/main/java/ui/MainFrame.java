package ui;

import entity.Book;
import entity.Reader;
import handler.BookHandler;
import exception.ExceptionFrame;
import handler.ReaderHandler;
import handler.RecordHandler;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

public class MainFrame extends JFrame implements ActionListener {

    private final TextField bookIdField;
    private final TextField readerIdField;
    private final DefaultTableModel bookModel;
    private final DefaultTableModel readerModel;
    public static SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();

    public MainFrame() {
        bookModel = new DefaultTableModel();
        JTable bookTable = new JTable(bookModel);
        String[] columnBookNames = {"id", "Название", "Автор", "Год публикации"};
        bookModel.setColumnIdentifiers(columnBookNames);
        bookTable.setModel(bookModel);
        bookTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        bookTable.setFillsViewportHeight(true);
        readerModel = new DefaultTableModel();
        JTable readerTable = new JTable(readerModel);
        String[] columnReaderNames = {"id", "Имя", "Фамилия", "Пол", "Возраст"};
        readerModel.setColumnIdentifiers(columnReaderNames);
        readerTable.setModel(readerModel);
        readerTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        readerTable.setFillsViewportHeight(true);

        JScrollPane scrollBook = new JScrollPane(bookTable);
        scrollBook.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollBook.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        JScrollPane scrollReader = new JScrollPane(readerTable);
        scrollReader.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollReader.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        bookIdField = new TextField();
        readerIdField = new TextField();

        Label bookLabel = new Label("Книги");
        Label readerLabel = new Label("Читатели");
        Label bookIdLabel = new Label("Введите идентификатор книги:");
        Label readerIdLabel = new Label("Введите идентификатор читателя:");

        Button showBookButton = new Button("Показать книгу");
        Button showAllBooksButton = new Button("Показать все книги");
        Button updateBookButton = new Button("Обновить книгу");
        Button deleteBookButton = new Button("Удалить книгу");
        Button addBookButton = new Button("Добавить книгу");
        Button showReaderButton = new Button("Показать читателя");
        Button showAllReadersButton = new Button("Показать всех читателей");
        Button updateReaderButton = new Button("Обновить читателя");
        Button deleteReaderButton = new Button("Удалить читателя");
        Button addReaderButton = new Button("Добавить читателя");
        Button addRecordButton = new Button("Выдать книгу читателю");
        Button showRecordsButton = new Button("Показать выданные книги");

        bookLabel.setBounds(480, 5, 150, 20);
        bookIdLabel.setBounds(480, 35, 200, 30);
        bookIdField.setBounds(685, 35, 150, 25);
        showBookButton.setBounds(480, 70, 150, 30);
        updateBookButton.setBounds(635, 70, 150, 30);
        deleteBookButton.setBounds(790, 70, 150, 30);
        addBookButton.setBounds(635, 105, 150, 30);
        showAllBooksButton.setBounds(790, 105, 150, 30);

        readerLabel.setBounds(480, 155, 150, 20);
        readerIdLabel.setBounds(480, 185, 200, 30);
        readerIdField.setBounds(685, 185, 150, 25);
        showReaderButton.setBounds(480, 220, 150, 30);
        updateReaderButton.setBounds(635, 220, 150, 30);
        deleteReaderButton.setBounds(790, 220, 150, 30);
        addReaderButton.setBounds(635, 255, 150, 30);
        showAllReadersButton.setBounds(790, 255, 150, 30);
        addRecordButton.setBounds(555, 300, 155, 40);
        showRecordsButton.setBounds(720, 300, 155, 40);

        bookTable.setBounds(5, 5, 450, 170);
        bookModel.addRow(columnBookNames);

        readerTable.setBounds(5, 175, 450, 170);
        readerModel.addRow(columnReaderNames);

        showBookButton.addActionListener(this);
        showAllBooksButton.addActionListener(this);
        updateBookButton.addActionListener(this);
        deleteBookButton.addActionListener(this);
        addBookButton.addActionListener(this);
        showReaderButton.addActionListener(this);
        showAllReadersButton.addActionListener(this);
        updateReaderButton.addActionListener(this);
        deleteReaderButton.addActionListener(this);
        addReaderButton.addActionListener(this);
        addRecordButton.addActionListener(this);
        showRecordsButton.addActionListener(this);

        this.add(bookTable);
        this.add(scrollBook);
        this.add(readerTable);
        this.add(scrollReader);
        this.add(bookLabel);
        this.add(bookIdField);
        this.add(bookIdLabel);
        this.add(showBookButton);
        this.add(updateBookButton);
        this.add(deleteBookButton);
        this.add(addBookButton);
        this.add(showAllBooksButton);

        this.add(readerLabel);
        this.add(readerIdField);
        this.add(readerIdLabel);
        this.add(showReaderButton);
        this.add(updateReaderButton);
        this.add(deleteReaderButton);
        this.add(addReaderButton);
        this.add(showAllReadersButton);
        this.add(addRecordButton);
        this.add(showRecordsButton);

        this.setResizable(false);
        this.setBounds(600, 250, 1000, 390);
        this.setTitle("Тестовое задание");
        this.setLayout(null);
        this.setVisible(true);

        addWindowListener(new WindowAdapter() {
                              public void windowClosing(WindowEvent we) {
                                  dispose();
                              }
                          }
        );
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "Показать книгу" -> {
                Long id = parseBookId();
                if (id == null) return;
                int rowCount = bookModel.getRowCount();
                for (int i = 1; i < rowCount; i++) {
                    bookModel.removeRow(1);
                }
                Book book = BookHandler.showBook(id);
                if (book != null) {
                    bookModel.addRow(new Object[]{book.getId(), book.getName(), book.getAuthor(),
                            book.getYearOfPublishing()});
                }
            }
            case "Показать все книги" -> {
                List<Book> listOfBooks = BookHandler.showAllBooks();
                int rowCount = bookModel.getRowCount();
                for (int i = 1; i < rowCount; i++) {
                    bookModel.removeRow(1);
                }
                for (Book book : listOfBooks) {
                    Long id = book.getId();
                    String name = book.getName();
                    String author = book.getAuthor();
                    String year = book.getYearOfPublishing();
                    bookModel.addRow(new Object[]{id, name, author, year});
                }
            }
            case "Обновить книгу" -> {
                Long id = parseBookId();
                if (id == null) return;

                BookHandler.updateBook(id);
            }
            case "Удалить книгу" -> {
                Long id = parseBookId();
                if (id == null) return;

                BookHandler.deleteBook(id);
            }
            case "Добавить книгу" -> BookHandler.addBook();
            case "Показать читателя" -> {
                Long id = parseReaderId();
                if (id == null) return;
                int rowCount = readerModel.getRowCount();
                for (int i = 1; i < rowCount; i++) {
                    readerModel.removeRow(1);
                }
                Reader reader = ReaderHandler.showReader(id);
                if (reader != null) {
                    readerModel.addRow(new Object[]{reader.getId(), reader.getFirstName(), reader.getLastName(),
                            reader.getGender(), reader.getAge()});
                }
            }
            case "Показать всех читателей" -> {
                List<Reader> listOfReaders = ReaderHandler.showAllReaders();
                int rowCount = readerModel.getRowCount();
                for (int i = 1; i < rowCount; i++) {
                    readerModel.removeRow(1);
                }
                for (Reader reader : listOfReaders) {
                    Long id = reader.getId();
                    String firstName = reader.getFirstName();
                    String lastName = reader.getLastName();
                    String gender = reader.getGender();
                    Integer age = reader.getAge();
                    readerModel.addRow(new Object[]{id, firstName, lastName, gender, age});
                }
            }
            case "Обновить читателя" -> {
                Long id = parseReaderId();
                if (id == null) return;

                ReaderHandler.updateReader(id);
            }
            case "Удалить читателя" -> {
                Long id = parseReaderId();
                if (id == null) return;

                ReaderHandler.deleteReader(id);
            }
            case "Добавить читателя" -> ReaderHandler.addReader();
            case "Выдать книгу читателю" -> RecordHandler.addRecord();
            default -> System.err.println("Неизвестное событие");
        }
    }

    private Long parseBookId() {
        long id;
        try {
            id = Long.parseLong(bookIdField.getText());
        } catch (NumberFormatException ex) {
            new ExceptionFrame("Неверный идентификатор книги!");
            return null;
        }

        return id;
    }

    private Long parseReaderId() {
        long id;
        try {
            id = Long.parseLong(readerIdField.getText());
        } catch (NumberFormatException ex) {
            new ExceptionFrame("Неверный идентификатор читателя!");
            return null;
        }

        return id;
    }
}
