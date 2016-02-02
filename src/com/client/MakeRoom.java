package com.client;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by hojin on 16. 1. 31.
 */
public class MakeRoom extends JFrame{
    JLabel label1,label2;
    JTextField textField;
    JButton jButton1,jButton2;

    public MakeRoom(){
        label1=new JLabel("방이름");
        label2=new JLabel("상태");

        textField=new JTextField();

        jButton1=new JButton("확인");
        jButton2=new JButton("취소");

        //배치
        setLayout(null);
        label1.setBounds(10,15,50,20);
        textField.setBounds(65,15,150,20);

        JPanel jPanel=new JPanel();
        jPanel.add(jButton1);
        jPanel.add(jButton2);
        jPanel.setBounds(10,115,195,35);

        add(label1);
        add(label2);
        add(textField);
        add(jPanel);

        setBounds(550,150,235,195);


    }

}































