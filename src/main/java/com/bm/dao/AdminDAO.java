package com.bm.dao;

import com.bm.entity.Admin;

import java.sql.SQLException;
import java.util.List;

public interface AdminDAO {
    /**
     * 根据账号查找管理员
     * @param account
     * @return Admin
     * @throws SQLException
     */
    Admin getAdminByAccount(String account)throws SQLException;


    /**
     * 查询所有用户角色
     * @return
     * @throws SQLException
     */
    List<Admin> selectAll() throws SQLException;


}
