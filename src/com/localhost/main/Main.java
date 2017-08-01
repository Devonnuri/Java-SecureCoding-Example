package com.localhost.main;

import javax.swing.*;
import java.awt.*;

public class Main {
    private JFrame frame = new JFrame("Localhost 시큐어 코딩 예제");
    private JPanel panel = new JPanel();
    private JButton loginButton = new JButton("로그인");
    private JTextField idField = new JTextField("");
    private JPasswordField pwField = new JPasswordField("");
    private GridBagLayout layout = new GridBagLayout();

    private void createFrame() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch(Exception e) {
            e.printStackTrace();
        }

        GridBagConstraints con = new GridBagConstraints();

        JLabel idLabel = new JLabel("아이디");
        idLabel.setFont(new Font("나눔바른고딕", Font.PLAIN, 20));
        idLabel.setHorizontalAlignment(JLabel.CENTER);
        con.fill = GridBagConstraints.BOTH;
        con.weightx = 0.1;
        panel.add(idLabel);
        layout.setConstraints(idLabel, con);

        idField.setFont(new Font("나눔바른고딕", Font.PLAIN, 20));
        con.weightx = 0.9;
        con.gridwidth = GridBagConstraints.REMAINDER;
        panel.add(idField);
        layout.setConstraints(idField, con);

        JLabel pwLabel = new JLabel("비밀번호");
        pwLabel.setFont(new Font("나눔바른고딕", Font.PLAIN, 20));
        pwLabel.setHorizontalAlignment(JLabel.CENTER);
        con.weightx = 0.1;
        con.gridwidth = GridBagConstraints.RELATIVE;
        panel.add(pwLabel);
        layout.setConstraints(pwLabel, con);

        pwField.setFont(new Font("나눔바른고딕", Font.PLAIN, 20));
        con.weightx = 0.9;
        con.gridwidth = GridBagConstraints.REMAINDER;
        panel.add(pwField);
        layout.setConstraints(pwField, con);

        loginButton.setFont(new Font("나눔바른고딕", Font.PLAIN, 20));
        con.weightx = 0.0;
        con.gridwidth = GridBagConstraints.REMAINDER;
        panel.add(loginButton);
        layout.setConstraints(loginButton, con);

        panel.setLayout(layout);

        frame.add(panel);
        frame.setSize(400, 142);
        frame.setVisible(true);

        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        Main main = new Main();
        main.createFrame();
    }
}
