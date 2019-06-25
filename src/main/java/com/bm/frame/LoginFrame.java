package com.bm.frame;

import com.bm.entity.Admin;
import com.bm.entity.User;
import com.bm.factory.ServiceFactory;
import com.bm.ui.ImgPanel;
import com.bm.utils.ResultEntity;

import javax.swing.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class LoginFrame extends JFrame {
    private ImgPanel rootPanel;
    private JTextField accountField;
    private JPasswordField passwordField;
    private String roleName;
    private JButton 登录Button;
    private JButton 取消Button;
    private JRadioButton 学生RadioButton;
    private JRadioButton 管理员RadioButton;

    public LoginFrame() {
        setTitle("图书管理登录");
        setContentPane(rootPanel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 700);
        setLocationRelativeTo(null);
        setVisible(true);
        rootPanel.setFileName("1.jpg");
        rootPanel.repaint();



        ButtonGroup group = new ButtonGroup();
        group.add(学生RadioButton);
        group.add(管理员RadioButton);

        登录Button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                if (学生RadioButton.isSelected()) {
                    //获得输入的账号和密码，注意密码框组件的使用方法
                    String account = accountField.getText().trim();
                    String password = new String(passwordField.getPassword()).trim();

                    ResultEntity resultEntity = ServiceFactory.getUserServiceInstance().adminLogin(account, password);
                    JOptionPane.showMessageDialog(rootPanel, resultEntity.getMessage());

                    //登录成功，进入主界面，并关闭登录窗体
                    if (resultEntity.getCode() == 0) {
                        new StudentFrame((User) resultEntity.getData());
                        LoginFrame.this.dispose();


                    } else if (resultEntity.getCode() == 1) {
                        //密码错误，清空密码框
                        passwordField.setText("");
                    } else {
                        //账号错误，清楚两个输入框
                        accountField.setText("");
                        passwordField.setText("");
                    }

                } else {
                    //获得输入账号和密码
                    String account = accountField.getText().trim();
                    String password = new String(passwordField.getPassword()).trim();
                    //学生用户登录
                    ResultEntity resultEntity1 = ServiceFactory.getAdminServiceInstance().adminLogin(account, password);
                    JOptionPane.showMessageDialog(rootPanel, resultEntity1.getMessage());
                    //登录成功，进入主界面，并关闭登录界面
                    if (resultEntity1.getCode() == 0) {
                        new AdminMainFrame((Admin) resultEntity1.getData());
                        LoginFrame.this.dispose();
                    } else if (resultEntity1.getCode() == 1) {
                        //密码错误，清空密码框
                        passwordField.setText("");
                    } else {
                        //账号错误，清空两个输入框
                        accountField.setText("");
                        passwordField.setText("");
                    }
                }
            }
        });

    //取消按钮监听
        取消Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                accountField.setText("");
                passwordField.setText("");
            }
        });
    }

    public static void main(String[] args) throws Exception {
String lookAndFeel = UIManager.getSystemLookAndFeelClassName();
UIManager.setLookAndFeel(lookAndFeel);
new LoginFrame();
    }
}