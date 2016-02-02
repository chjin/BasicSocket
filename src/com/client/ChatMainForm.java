package com.client;

import com.common.Protocol;
import jdk.nashorn.internal.scripts.JO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.util.StringTokenizer;

/**
 * Created by hojin on 16. 1. 31.
 */
public class ChatMainForm extends JFrame implements ActionListener,Runnable,MouseListener{
    //add 2.2
    Socket socket;
    BufferedReader bufferedReader;
    OutputStream outputStream;

    String myId;

    Login login=new Login();
    WaitRoom waitRoom=new WaitRoom();
    MakeRoom makeRoom=new MakeRoom();

    CardLayout cardLayout=new CardLayout();

    public ChatMainForm(){
        setLayout(cardLayout);

        add("LOGIN",login);         //이름을 부여함.
        add("WR",waitRoom);         //이름을 부여함.
        setSize(820,600);
        setVisible(true);

        login.jButton1.addActionListener(this);
        login.jButton2.addActionListener(this);
        waitRoom.jButton1.addActionListener(this);
        waitRoom.jButton2.addActionListener(this);

        makeRoom.jButton1.addActionListener(this);
        makeRoom.jButton2.addActionListener(this);

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                JOptionPane.showMessageDialog(ChatMainForm.this,"나가기 버튼을 클릭하세요.");
            }
        });

        login.textField1.addKeyListener(new KeyAdapter() {

            @Override
            public void keyPressed(KeyEvent e) {
                // TODO Auto-generated method stub
                super.keyPressed(e);
                if(!(e.getKeyChar()==27||e.getKeyChar()==65535))//유저가 JTextField를 수정했을때 실행됨
                {
                    System.out.println("유저가 JTextField를 수정했을때 실행됨");
                }
            }

        });

        login.textField1.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                login.textField2.requestFocusInWindow();
            }
        });

        login.textField2.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                login.jButton1.requestFocusInWindow();
            }
        });
    }

    public static void main(String[] args){
        try{
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        }catch(Exception e){
            e.printStackTrace();
        }

        new ChatMainForm();
    }


    @Override
    public void actionPerformed(ActionEvent e) {

        if(e.getSource()==login.jButton1) {
            String id = login.textField1.getText().trim();
            if (id.length() < 1) {
                JOptionPane.showMessageDialog(this, "ID입력");
                login.textField1.requestFocus();
                return;
            }

            String name = login.textField2.getText().trim();
            if (name.length() < 1) {
                JOptionPane.showMessageDialog(this, "대화명입력");
                login.textField2.requestFocus();
                return;
            }

            connection(id,name);
        }
        else if(e.getSource()==waitRoom.jButton1){
            makeRoom.textField.setText("");
            makeRoom.setVisible(true);
        }else if(e.getSource()==makeRoom.jButton1){
            String rname=makeRoom.textField.getText().trim();
            if(rname.length()<1){
                JOptionPane.showMessageDialog(this,"방이름을 입력하세요.");
                makeRoom.textField.requestFocus();
                return;
            }

        }else if(e.getSource()==waitRoom.jButton2){
            waitRoom.setVisible(false);

        }else if(e.getSource()==makeRoom.jButton2){
            makeRoom.setVisible(false);
        }else if(e.getSource()==login.jButton2){
            dispose();
            System.exit(0);
        }

    }


    public void connection(String id, String name){
        try{
            //입력 데이터 읽어들이기//

            //읽어들인 데이터는 서버와 공유한다.
            socket=new Socket("localhost",3030);
            //서버에 입력된 데이터를 보내는 객체가 필요함.
            outputStream=socket.getOutputStream();
            //서버로부터 데이터를 읽어들이는 객체가 필요함.
            bufferedReader=new BufferedReader(new InputStreamReader(socket.getInputStream()));

            //서버로 데이터 전송
            outputStream.write((Protocol.LOGIN +"||"+ id +"||"+ name +"\n").getBytes());
        }catch(Exception ex){
            ex.printStackTrace();
        }

        //서버에서 오는 데이터 읽기위해 스레드가동함.
        new Thread(this).start();
    }

    @Override
    public void run() {
        try {
            while(true){
                String message=bufferedReader.readLine();
                System.out.println(message);
                StringTokenizer stringTokenizer=new StringTokenizer(message, "||");
                int protocol=Integer.parseInt(stringTokenizer.nextToken());
                switch(protocol){
                    case Protocol.LOGIN: {
                        Object[] objects={
                                stringTokenizer.nextToken(),
                                stringTokenizer.nextToken()
                        };
                        waitRoom.defaultTableModel2.addRow(objects);
                    }
                    break;
                    case Protocol.WAITROOMLOGIN: {
                        String id=stringTokenizer.nextToken();
                        setTitle(id);
                        myId=id;
                        cardLayout.show(getContentPane(),"WR");
                    }
                    break;
                }
            }
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

}













































