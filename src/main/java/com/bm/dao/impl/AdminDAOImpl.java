package com.bm.dao.impl;

import com.bm.dao.AdminDAO;
import com.bm.entity.Admin;
import com.bm.utils.JDBCUtil;
import jdk.nashorn.internal.scripts.JD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AdminDAOImpl implements AdminDAO {
    public Admin getAdminByAccount(String account) throws SQLException {
        JDBCUtil jdbcUtil = JDBCUtil.getInitJDBCUtil();
        Connection connection = jdbcUtil.getConnection();
        String sql = "SELECT * FROM t_admin WHERE account = ?";
        PreparedStatement pstmt = connection.prepareStatement(sql);
        pstmt.setString(1,account);
        ResultSet rs = pstmt.executeQuery();
        Admin admin = null;
        while (rs.next()){
            int id = rs.getInt("id");
            String adminAccount = rs.getString("account");
            String password = rs.getString("password");
            String adminName = rs.getString("admin_name");
            admin = new Admin();
            admin.setAccount(adminAccount);
            admin.setPassword(password);
            admin.setAdminName(adminName);
        }
        rs.close();
        pstmt.close();
        jdbcUtil.closeConnection();
        return admin;
    }
    //查询用户角色

    public List<Admin> selectAll() throws SQLException {
        JDBCUtil jdbcUtil = JDBCUtil.getInitJDBCUtil();
        Connection connection = jdbcUtil.getConnection();
        String sql = "SELECT t1.*,t2.role_name\n"+
                "FROM t_admin t1\n"+
                "LEFT JOIN t_role t2\n"+
                "ON t1.role_id = t2.id\n";
        PreparedStatement pstmt = connection.prepareStatement(sql);
        ResultSet rs = pstmt.executeQuery();
        List<Admin> adminList = convert(rs);
        rs.close();
        pstmt.close();
        jdbcUtil.closeConnection();
        return  adminList;

    }
    private List<Admin> convert(ResultSet rs)throws SQLException{
        List<Admin> adminList = new ArrayList<Admin>();
        while (rs.next()){
            Admin admin = new Admin();
            admin.setId(rs.getInt("id"));
            admin.setAccount(rs.getString("account"));
            admin.setPassword(rs.getString("password"));
            admin.setAdminName(rs.getString("admin_name"));
            admin.setRoleName(rs.getString("role_name"));
            adminList.add(admin);
        }
        return adminList;

    }
}
