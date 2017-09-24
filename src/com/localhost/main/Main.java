package com.localhost.main;

import com.localhost.crypt.BCrypt;
import com.localhost.db.Database;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.io.InputStream;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Main {
    private JFrame frame = new JFrame("Localhost 시큐어 코딩 예제");
    private JPanel panel = new JPanel();
    private JButton loginButton = new JButton("로그인");
    private JTextField idField = new JTextField("");
    private JPasswordField pwField = new JPasswordField("");
    private GridBagLayout layout = new GridBagLayout();

    private static boolean useBcrypt = true;
    private static Database database;

    private void createFrame() throws Exception {
        System.setProperty("awt.useSystemAAFontSettings","on");
        System.setProperty("swing.aatext", "true");

        InputStream stream = Main.class.getResourceAsStream("notosans.ttf");
        Font font = Font.createFont(Font.TRUETYPE_FONT, stream);
        font = font.deriveFont(24f);

        database = new Database("jdbc:mysql://localhost/sql_injection_demo", "root", "devonnuri");

        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

        GridBagConstraints con = new GridBagConstraints();

        JLabel idLabel = new JLabel("아이디");
        idLabel.setFont(font);
        idLabel.setHorizontalAlignment(JLabel.CENTER);
        con.fill = GridBagConstraints.BOTH;
        con.weightx = 0.1;
        panel.add(idLabel);
        layout.setConstraints(idLabel, con);

        idField.setFont(font);
        con.weightx = 0.9;
        con.gridwidth = GridBagConstraints.REMAINDER;
        panel.add(idField);
        layout.setConstraints(idField, con);

        JLabel pwLabel = new JLabel("비밀번호");
        pwLabel.setFont(font);
        pwLabel.setHorizontalAlignment(JLabel.CENTER);
        con.weightx = 0.1;
        con.gridwidth = GridBagConstraints.RELATIVE;
        panel.add(pwLabel);
        layout.setConstraints(pwLabel, con);

        pwField.setEchoChar('·');
        pwField.setFont(font);
        con.weightx = 0.9;
        con.gridwidth = GridBagConstraints.REMAINDER;
        panel.add(pwField);
        layout.setConstraints(pwField, con);

        JCheckBox pwCheckBox = new JCheckBox("클릭시 비밀번호 보기");
        pwCheckBox.setFont(font);
        pwCheckBox.setHorizontalAlignment(JCheckBox.CENTER);
        con.weightx = 0.0;
        con.gridwidth = GridBagConstraints.REMAINDER;
        panel.add(pwCheckBox);
        layout.setConstraints(pwCheckBox, con);
        pwCheckBox.addItemListener(e -> {
            if(e.getStateChange() == ItemEvent.SELECTED) {
                pwField.setEchoChar((char) 0);
            } else {
                pwField.setEchoChar('·');
            }
        });

        JCheckBox bcryptCheckBox = new JCheckBox("해쉬함수(bcrypt) 사용");
        bcryptCheckBox.setFont(font);
        bcryptCheckBox.setHorizontalAlignment(JCheckBox.CENTER);
        con.weightx = 0.0;
        con.gridwidth = GridBagConstraints.REMAINDER;
        panel.add(bcryptCheckBox);
        layout.setConstraints(bcryptCheckBox, con);
        bcryptCheckBox.addItemListener(e -> useBcrypt = e.getStateChange() == ItemEvent.SELECTED);

        loginButton.setFont(font);
        loginButton.addActionListener(e -> {
            if(useBcrypt) {
                ResultSet result = database.executeQuery("SELECT password FROM `users` WHERE `username` = \""+idField.getText()+"\"");
                try {
                    if(result.next()) {
                        if(BCrypt.checkpw(String.copyValueOf(pwField.getPassword()), result.getString("password"))) {
                            JOptionPane.showMessageDialog(null, "로그인 성공!", "localhost 시큐어코딩 예제", JOptionPane.INFORMATION_MESSAGE);
                        } else {
                            JOptionPane.showMessageDialog(null, "아이디나 비밀번호가 올바르지 않습니다.", "localhost 시큐어코딩 예제", JOptionPane.ERROR_MESSAGE);
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "아이디나 비밀번호가 올바르지 않습니다.", "localhost 시큐어코딩 예제", JOptionPane.ERROR_MESSAGE);
                    }
                } catch(SQLException e1) {
                    e1.printStackTrace();
                }
            } else {
                ResultSet result = database.executeQuery(String.format("SELECT password FROM `users` WHERE `username` = \"%s\" AND `password` = \"%s\"", idField.getText(), String.copyValueOf(pwField.getPassword())));
                if(Database.getResultSetLength(result) == 1) {
                    JOptionPane.showMessageDialog(null, "로그인 성공!", "localhost 시큐어코딩 예제", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "아이디나 비밀번호가 올바르지 않습니다.", "localhost 시큐어코딩 예제", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        con.weightx = 0.0;
        con.gridwidth = GridBagConstraints.REMAINDER;
        panel.add(loginButton);
        layout.setConstraints(loginButton, con);

        panel.setLayout(layout);

        frame.add(panel);
        frame.setSize(400, 150);
        frame.setVisible(true);

        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) throws Exception {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> database.close()));

        Main main = new Main();
        main.createFrame();
    }
}
