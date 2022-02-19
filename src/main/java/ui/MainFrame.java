package ui;

import entity.Book;
import entity.Reader;
import handler.BookHandler;
import exception.ExceptionFrame;
import handler.ReaderHandler;
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
    private final DefaultTableModel model;
    private String[] columnBookNames = {"id", "Название", "Автор", "Год публикации"};
    private String[] columnReaderNames = {"id", "Имя", "Фамилия", "Пол", "Возраст"};
    private JTable table;
    public static SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();

    public MainFrame() {
        model = new DefaultTableModel();
        table = new JTable(model);
        model.setColumnIdentifiers(columnReaderNames);
        table.setModel(model);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        table.setFillsViewportHeight(true);
        JScrollPane scroll = new JScrollPane(table);
        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

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

        bookLabel.setBounds(480, 35, 150, 20);
        bookIdLabel.setBounds(480, 65, 200, 30);
        bookIdField.setBounds(685, 65, 150, 25);
        showBookButton.setBounds(480, 100, 150, 30);
        updateBookButton.setBounds(635, 100, 150, 30);
        deleteBookButton.setBounds(790, 100, 150, 30);
        addBookButton.setBounds(635, 135, 150, 30);
        showAllBooksButton.setBounds(790, 135, 150, 30);

        readerLabel.setBounds(480, 175, 150, 20);
        readerIdLabel.setBounds(480, 205, 200, 30);
        readerIdField.setBounds(685, 205, 150, 25);
        showReaderButton.setBounds(480, 240, 150, 30);
        updateReaderButton.setBounds(635, 240, 150, 30);
        deleteReaderButton.setBounds(790, 240, 150, 30);
        addReaderButton.setBounds(635, 275, 150, 30);
        showAllReadersButton.setBounds(790, 275, 150, 30);

        table.setBounds(5, 5, 450, 345);
        model.addRow(columnReaderNames);

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

        this.add(table);
        this.add(scroll);
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
                int rowCount = model.getRowCount();
                for (int i = 1; i < rowCount; i++) {
                    model.removeRow(1);
                }
                Book book = BookHandler.showBook(id);
                if (book != null) {
                    model.addRow(new Object[]{book.getId(), book.getName(), book.getAuthor(),
                            book.getYearOfPublishing()});
                }
            }
            case "Показать все книги" -> {
                List<Book> listOfBooks = BookHandler.showAllBooks();
                int rowCount = model.getRowCount();
                for (int i = 1; i < rowCount; i++) {
                    model.removeRow(1);
                }
                for (Book book : listOfBooks) {
                    Long id = book.getId();
                    String name = book.getName();
                    String author = book.getAuthor();
                    String year = book.getYearOfPublishing();
                    model.addRow(new Object[]{id, name, author, year});
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
                int rowCount = model.getRowCount();
                for (int i = 1; i < rowCount; i++) {
                    model.removeRow(1);
                }
                Reader reader = ReaderHandler.showReader(id);
                if (reader != null) {
                    model.addRow(new Object[]{reader.getId(), reader.getFirstName(), reader.getLastName(),
                            reader.getGender(), reader.getAge()});
                }
            }
            case "Показать всех читателей" -> {
                List<Reader> listOfReaders = ReaderHandler.showAllReaders();
                int rowCount = model.getRowCount();
                for (int i = 1; i < rowCount; i++) {
                    model.removeRow(1);
                }
                for (Reader reader : listOfReaders) {
                    Long id = reader.getId();
                    String firstName = reader.getFirstName();
                    String lastName = reader.getLastName();
                    String gender = reader.getGender();
                    Integer age = reader.getAge();
                    model.addRow(new Object[]{id, firstName, lastName, gender, age});
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
