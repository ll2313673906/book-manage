package com.bm.dao.impl;

import com.bm.dao.AdminDAO;
import com.bm.entity.Admin;
import com.bm.factory.DAOFactory;
import org.junit.Test;

import java.sql.SQLException;
import java.util.List;

import static org.junit.Assert.*;

public class AdminDAOImplTest {
private AdminDAO adminDAO = DAOFactory.getAdminDAOInstance();
    @Test
    public void getAdminByAccount() {
        try{
            Admin admin = adminDAO.getAdminByAccount("aaa@qq.com");
            if (admin != null){
                System.out.println(admin);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Test
    public void selectAll() {
        List<Admin> adminList = null;
        try {
            adminList = adminDAO.selectAll();
        }catch (SQLException e){
            e.printStackTrace();
        }
        adminList.forEach(admin-> System.out.println(admin));
    }
}