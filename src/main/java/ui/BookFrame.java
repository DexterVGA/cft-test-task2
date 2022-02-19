package ui;

import entity.Book;
import exception.ExceptionFrame;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class BookFrame extends Frame implements ActionListener {
    private final TextField nameField;
    private final TextField authorField;
    private final TextField yearField;
    private static Long bookId = null;


    public BookFrame(String method, Long bookId) {
        Label nameLabel = new Label("Введите название книги:");
        Label authorLabel = new Label("Введите имя автора:");
        Label yearLabel = new Label("Введите год публикации:");
        nameField = new TextField();
        authorField = new TextField();
        yearField = new TextField();
        Button buttonAdd;
        if (method.equals("Добавить")) {
            buttonAdd = new Button("Добавить");
        } else {
            buttonAdd = new Button("Изменить");
        }

        if (bookId != null) {
            BookFrame.bookId = bookId;
        }

        nameLabel.setBounds(10, 30, 150, 20);
        nameField.setBounds(10, 55, 280, 20);
        authorLabel.setBounds(10, 80, 150, 20);
        authorField.setBounds(10, 105, 280, 20);
        yearLabel.setBounds(10, 130, 150, 20);
        yearField.setBounds(10, 155, 280, 20);
        buttonAdd.setBounds(100, 180, 100, 30);

        buttonAdd.addActionListener(this);

        this.add(nameLabel);
        this.add(nameField);
        this.add(authorLabel);
        this.add(authorField);
        this.add(yearLabel);
        this.add(yearField);
        this.add(buttonAdd);

        this.setResizable(false);
        this.setBounds(1000, 350, 300, 220);
        if (method.equals("Добавить")) {
            this.setTitle("Добавление книги");
        } else {
            this.setTitle("Изменение книги");
        }
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
            case "Добавить" -> {
                if (nameField.getText().isEmpty() || authorField.getText().isEmpty() || yearField.getText().isEmpty()) {
                    new ExceptionFrame("Все поля должны быть заполнены!");
                    return;
                }

                Book newBook = new Book(nameField.getText(), authorField.getText(), yearField.getText());
                create(MainFrame.sessionFactory, newBook);

                this.dispose();
            }
            case "Изменить" -> {
                if (nameField.getText().isEmpty() || authorField.getText().isEmpty() || yearField.getText().isEmpty()) {
                    new ExceptionFrame("Все поля должны быть заполнены!");
                    return;
                }

                Book updatedBook = new Book(nameField.getText(), authorField.getText(), yearField.getText());
                update(MainFrame.sessionFactory, updatedBook);

                this.dispose();
            }
        }
    }

    private static void create(SessionFactory sessionFactory, Book book) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.save(book);
        session.getTransaction().commit();
        System.out.println("Книга успешно добавлена!");
    }

    private static void update(SessionFactory sessionFactory, Book book) {
        Session session = sessionFactory.openSession();
        String hql = "UPDATE Book SET name=:nameParam, author=:authorParam, yearOfPublishing=:yearParam WHERE id=:bookId";
        Transaction transaction = session.beginTransaction();
        @SuppressWarnings("unchecked")
        Query<Book> query = session.createQuery(hql);
        query.setParameter("bookId", BookFrame.bookId);
        query.setParameter("nameParam", book.getName());
        query.setParameter("authorParam", book.getAuthor());
        query.setParameter("yearParam", book.getYearOfPublishing());
        int result = query.executeUpdate();
        transaction.commit();
        System.out.println("Rows affected: " + result);
    }
}
