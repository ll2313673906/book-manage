package com.bm.service;

import com.bm.entity.Admin;
import com.bm.utils.ResultEntity;

import java.util.List;

public interface AdminService {
    /**
     * 管理员登录
     * @param account
     * @param password
     * @return ResultEntity
     */
    ResultEntity adminLogin(String account,String password);

    /**
     * 查询所有
     * @return
     */
    List<Admin> selectAll();
}
