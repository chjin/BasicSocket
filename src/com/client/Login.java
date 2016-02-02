package com.client;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by hojin on 16. 1. 31.
 */
public class Login extends JPanel {
    JLabel label1,label2;
    JTextField textField1,textField2;

    JButton jButton1,jButton2;

    public Login(){
        label1=new JLabel("아이디");
        label2=new JLabel("이 름");

        textField1=new JTextField();
        textField2=new JTextField();

        jButton1=new JButton("로그인");
        jButton2=new JButton("취소");

        //배치
        setLayout(null);

        label1.setBounds(10,15,50,20);
        textField1.setBounds(65,15,150,20);

        label2.setBounds(10,40,50,20);
        textField2.setBounds(65,40,150,20);

        JPanel jPanel=new JPanel();
        jPanel.add(jButton1);
        jPanel.add(jButton2);

        jPanel.setBounds(10,165,205,35);

        add(label1);
        add(label2);
        add(textField1);
        add(textField2);
        add(jPanel);

    }

}





















