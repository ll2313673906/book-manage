package com.bm.service.impl;

import com.bm.dao.AdminDAO;
import com.bm.entity.Admin;
import com.bm.factory.DAOFactory;
import com.bm.service.AdminService;
import com.bm.utils.ResultEntity;
import org.apache.commons.codec.digest.DigestUtils;

import java.sql.SQLException;
import java.util.List;

public class AdminServiceImpl implements AdminService {
    private AdminDAO adminDAO = DAOFactory.getAdminDAOInstance();

    public ResultEntity adminLogin(String account, String password) {
        ResultEntity resultEntity = new ResultEntity();
        Admin admin = null;
        try {
            //根据账号查找管理员信息，捕获异常
            admin = adminDAO.getAdminByAccount(account);
        }catch (SQLException e){
            System.err.println("根据账号查找管理员信息出现异常");
        }
        //根据账号查找到了记录
        if (admin != null){
            //比较密码，此时需要将客户端传过来的密码进行MD5加密后才能进行比对
            if (DigestUtils.md5Hex(password).equals(admin.getPassword())) {
                resultEntity.setCode(0);
                resultEntity.setMessage("登录成功");
                resultEntity.setData(admin);
             }
            }else {
                //账号不存在
                resultEntity.setCode(2);
                resultEntity.setMessage("账号密码");
            }
            return resultEntity;
        }

    @Override
    public List<Admin> selectAll() {
        List<Admin> adminList = null;
        try {
            adminList = adminDAO.selectAll();
        }catch (SQLException e){
            System.out.println("查询异常");
        }
        return adminList;
    }
}

