package com.bm.frame;

import com.bm.entity.Admin;
import com.bm.factory.ServiceFactory;
import com.bm.utils.ResultEntity;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.List;

public class LoginFrame extends JFrame{
    private JPanel rootPanel;
    private JTextField accountField;
    private JPasswordField passwordField;
    private JComboBox<Admin> chooseComboBox;
    private String roleName;
    private JButton 登录Button;
    private JButton 取消Button;

    public LoginFrame(){
        setTitle("图书管理登录");
        setContentPane(rootPanel);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500,600);
        setVisible(true);

        //
        Admin tip = new Admin();
        tip.setRoleName("请选择登录的角色");
        chooseComboBox.addItem(tip);
        List<Admin>adminList = ServiceFactory.getAdminServiceInstance().selectAll();
        for (Admin admin:adminList) {
            chooseComboBox.addItem(admin);
        }
        chooseComboBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED){
                    int index = chooseComboBox.getSelectedIndex();
                    roleName = chooseComboBox.getItemAt(index).getRoleName();
                }
            }
        });

        登录Button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //获得输入的账号和密码，注意密码框组件的使用方法
                String account = accountField.getText().trim();
                String password = new String(passwordField.getPassword()).trim();

                ResultEntity resultEntity = ServiceFactory.getAdminServiceInstance().adminLogin(account,password);
                JOptionPane.showMessageDialog(rootPanel,resultEntity.getMessage());
                //登录成功，进入主界面，并关闭登录窗体
                if (resultEntity.getCode() == 0){
                    new AdminMainFrame((Admin)resultEntity.getData());
                    LoginFrame.this.dispose();
                }else if (resultEntity.getCode() == 1){
                    //密码错误，清空密码框
                    passwordField.setText("");
                }else {
                    //账号错误，清楚两个输入框
                    accountField.setText("");
                    passwordField.setText("");
                }
            }
        });
        取消Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                accountField.setText("");
                passwordField.setText("");
            }
        });
    }

    public static void main(String[] args) {
       new  LoginFrame();
    }
}
