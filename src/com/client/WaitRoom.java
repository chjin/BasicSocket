package com.client;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;

/**
 * Created by hojin on 16. 1. 31.
 */
public class WaitRoom extends JPanel{
    JTable jTable1,jTable2;
    DefaultTableModel defaultTableModel1,defaultTableModel2;
    JTextArea jTextArea;
    JComboBox jComboBox;
    JTextField jTextField;
    JButton jButton1,jButton2;
    JScrollBar jScrollBar;
    TableColumn tableColumn;

    public WaitRoom(){
        String[] col1={"방이름","공개/비공개","인원"};
        String[][] row1=new String[0][3];

        defaultTableModel1=new DefaultTableModel(row1,col1){
          public boolean isCellEditable(int r,int c){
              return false;
          }
        };
        jTable1=new JTable(defaultTableModel1);
        jTable1.getTableHeader().setReorderingAllowed(false);
        jTable1.setRowHeight(30);
        jTable1.setShowVerticalLines(false);
        jTable1.setIntercellSpacing(new Dimension(0,0));
        JScrollPane jScrollPane1=new JScrollPane(jTable1);
        jTable1.getTableHeader().setReorderingAllowed(false);
        jTable1.getTableHeader().setResizingAllowed(false);

        String[] col2={"ID","대화명"};
        Object[][] row2=new Object[0][1];
        defaultTableModel2=new DefaultTableModel(row2,col2){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }

            @Override
            public Class getColumnClass(int columnIndex) {
                return getValueAt(0,columnIndex).getClass();
            }
        };
        jTable2=new JTable(defaultTableModel2);
        jTable2.setRowHeight(30);
        JScrollPane jScrollPane2=new JScrollPane(jTable2);


        jButton1=new JButton("방만들기");
        jButton2=new JButton("나가기");

        setLayout(null);
        jScrollPane1.setBounds(10,15,450,300);
        jScrollPane2.setBounds(10,320,450,230);

        JPanel jPanel=new JPanel();
        GridLayout gridLayout=new GridLayout(3,2,5,5);
        jPanel.setLayout(gridLayout);
        jPanel.add(jButton1);
        jPanel.add(jButton2);

        jPanel.setBounds(465,320,320,230);

        add(jScrollPane1);
        add(jScrollPane2);
        add(jPanel);
    }

}

































