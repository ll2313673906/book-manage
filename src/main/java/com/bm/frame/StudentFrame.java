package com.bm.frame;

import com.bm.entity.Admin;
import com.bm.entity.User;
import com.bm.factory.DAOFactory;

import javax.swing.*;

public class StudentFrame extends JFrame{
    private JPanel rootPanel;
    public StudentFrame(User user){
        setContentPane(rootPanel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500,600);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) throws Exception {
        String lookAndFeel = UIManager.getSystemLookAndFeelClassName();
        UIManager.setLookAndFeel(lookAndFeel);
        new StudentFrame(DAOFactory.getUserDAOInstance().getAdminByAccount("aaa"));

    }
}
