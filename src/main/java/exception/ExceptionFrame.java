package exception;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class ExceptionFrame extends Frame implements ActionListener {

    public ExceptionFrame(String errorMessage) {
        JTextField messageField = new JTextField(errorMessage);
        messageField.setHorizontalAlignment(JTextField.CENTER);
        messageField.setForeground(Color.RED);
        messageField.setEditable(false);
        Button buttonOk = new Button("Ок");

        messageField.setBounds(10, 35, 280, 100);
        buttonOk.setBounds(100, 140, 100, 30);

        buttonOk.addActionListener(this);

        this.add(messageField);
        this.add(buttonOk);

        this.setResizable(false);
        this.setBounds(1000, 350, 300, 190);
        this.setTitle("Ошибка");
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
        if ("Ок".equals(e.getActionCommand())) {
            this.dispose();
        }
    }
}
