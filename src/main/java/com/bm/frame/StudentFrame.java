package com.bm.frame;

import javax.swing.*;

public class StudentFrame extends JFrame{
    private JPanel rootPanel;
    public StudentFrame(){
        setContentPane(rootPanel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500,600);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        new StudentFrame();
    }
}
