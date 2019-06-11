package com.bm.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * JDBC连接工具类，使用了单例模式，封装了连接和关闭方法
 */
public class JDBCUtil {
    private static String url = "jdbc:mysql://127.0.0.1:3306/db_bookmanage?useUnicode=true&characterEncoding=utf8";
    private static String name = "root";
    private static String password = "root";
    private static Connection connnection = null;
    private static JDBCUtil jdbcUtil = null;

    public static JDBCUtil getInitJDBCUtil() {
        if (jdbcUtil == null) {
            synchronized (JDBCUtil.class) {   //线程加锁
                if (jdbcUtil == null) {
                    jdbcUtil = new JDBCUtil();   //懒汉式加载
                }
            }
        }
        return jdbcUtil;
    }

    private JDBCUtil() {
    }

    // 通过静态代码块注册数据库驱动，保证注册只执行一次
    static {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    // 获得连接
    public Connection getConnection() {
        try {
            connnection = DriverManager.getConnection(url, name, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connnection;

    }

    //关闭连接
    public void closeConnection() {
        if (connnection != null) {
            try {
                connnection.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }

  public static void main(String[] args) {
      Connection connection = JDBCUtil.getInitJDBCUtil().getConnection();
      if (connection != null) {
          System.out.println("连接成功");
      }
  }
}
