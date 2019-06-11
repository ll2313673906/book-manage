package com.bm.frame;

import com.bm.entity.Admin;
import com.bm.factory.DAOFactory;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static com.sun.glass.ui.Cursor.setVisible;

public class AdminMainFrame extends JFrame{
    private JPanel rootPanel;
    private JPanel topPanel;
    private JPanel centerPanel;
    private JPanel studentCard;
    private JPanel bookCard;
    private JPanel fuzzyCard;
    private JButton 学生管理Button;
    private JButton 图书管理Button;
    private JButton 图书查询Button;
    private JSplitPane studentJSplitPanel;
    private JPanel studentRight;
    private JPanel studentLeft;


    public  AdminMainFrame(Admin admin){
        setTitle("管理员登录");
        setContentPane(rootPanel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setVisible(true);
    }

    public static void main(String[] args) throws Exception {
        String lookAndFeel = UIManager.getSystemLookAndFeelClassName();
        UIManager.setLookAndFeel(lookAndFeel);
        new AdminMainFrame(DAOFactory.getAdminDAOInstance().getAdminByAccount("aaa@qq.com"));

    }
}
