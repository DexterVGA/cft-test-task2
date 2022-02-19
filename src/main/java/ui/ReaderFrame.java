package ui;

import entity.Reader;
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

public class ReaderFrame extends Frame implements ActionListener {
    private final TextField firstNameField;
    private final TextField lastNameField;
    private final TextField genderField;
    private final TextField ageField;
    private static Long readerId = null;

    public ReaderFrame(String method, Long bookId) {
        Label firstNameLabel = new Label("Введите имя читателя:");
        Label lastNameLabel = new Label("Введите фамилию читателя:");
        Label genderLabel = new Label("Введите пол читателя:");
        Label ageLabel = new Label("Введите возраст читателя:");
        firstNameField = new TextField();
        lastNameField = new TextField();
        genderField = new TextField();
        ageField = new TextField();
        Button buttonAdd;
        if (method.equals("Добавить")) {
            buttonAdd = new Button("Добавить");
        } else {
            buttonAdd = new Button("Изменить");
        }

        if (bookId != null) {
            ReaderFrame.readerId = bookId;
        }

        firstNameLabel.setBounds(10, 30, 150, 20);
        firstNameField.setBounds(10, 55, 280, 20);
        lastNameLabel.setBounds(10, 80, 170, 20);
        lastNameField.setBounds(10, 105, 280, 20);
        genderLabel.setBounds(10, 130, 150, 20);
        genderField.setBounds(10, 155, 280, 20);
        ageLabel.setBounds(10, 180, 150, 20);
        ageField.setBounds(10, 205, 280, 20);
        buttonAdd.setBounds(100, 230, 100, 30);

        buttonAdd.addActionListener(this);

        this.add(firstNameLabel);
        this.add(firstNameField);
        this.add(lastNameLabel);
        this.add(lastNameField);
        this.add(genderLabel);
        this.add(genderField);
        this.add(ageLabel);
        this.add(ageField);
        this.add(buttonAdd);

        this.setResizable(false);
        this.setBounds(1000, 350, 300, 270);
        if (method.equals("Добавить")) {
            this.setTitle("Добавление читателя");
        } else {
            this.setTitle("Изменение данных читателя");
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
                if (firstNameField.getText().isEmpty() || lastNameField.getText().isEmpty() ||
                        genderField.getText().isEmpty() || ageField.getText().isEmpty()) {
                    new ExceptionFrame("Все поля должны быть заполнены!");
                    return;
                }

                Reader newReader = new Reader(firstNameField.getText(), lastNameField.getText(), genderField.getText(),
                        Integer.parseInt(ageField.getText()));
                create(MainFrame.sessionFactory, newReader);

                this.dispose();
            }
            case "Изменить" -> {
                if (firstNameField.getText().isEmpty() || lastNameField.getText().isEmpty() ||
                        genderField.getText().isEmpty() || ageField.getText().isEmpty()) {
                    new ExceptionFrame("Все поля должны быть заполнены!");
                    return;
                }

                Reader updatedReader = new Reader(firstNameField.getText(), lastNameField.getText(),
                        genderField.getText(), Integer.parseInt(ageField.getText()));
                update(MainFrame.sessionFactory, updatedReader);

                this.dispose();
            }
        }
    }

    private static void create(SessionFactory sessionFactory, Reader reader) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.save(reader);
        session.getTransaction().commit();
        System.out.println("Читатель успешно добавлен!");
    }

    private static void update(SessionFactory sessionFactory, Reader reader) {
        Session session = sessionFactory.openSession();
        String hql = "UPDATE Reader SET firstName=:firstNameParam, lastName=:lastNameParam, gender=:genderParam, " +
                "age=:ageParam WHERE id=:readerId";
        Transaction transaction = session.beginTransaction();
        @SuppressWarnings("unchecked")
        Query<Reader> query = session.createQuery(hql);
        query.setParameter("readerId", ReaderFrame.readerId);
        query.setParameter("firstNameParam", reader.getFirstName());
        query.setParameter("lastNameParam", reader.getLastName());
        query.setParameter("genderParam", reader.getGender());
        query.setParameter("ageParam", reader.getAge());
        int result = query.executeUpdate();
        transaction.commit();
        System.out.println("Rows affected: " + result);
    }
}
