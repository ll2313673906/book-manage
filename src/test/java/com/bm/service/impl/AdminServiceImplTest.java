package com.bm.service.impl;

import com.bm.entity.Admin;
import com.bm.factory.ServiceFactory;
import com.bm.service.AdminService;
import com.bm.utils.ResultEntity;
import com.sun.xml.internal.ws.developer.SerializationFeature;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class AdminServiceImplTest {
private AdminService adminService = ServiceFactory.getAdminServiceInstance();
    @Test
    public void adminLogin() {
        ResultEntity resultEntity = adminService.adminLogin("aaa@qq.com","123");
        System.out.println(resultEntity);
    }

    @Test
    public void selectAll() {
        List<Admin> adminList = adminService.selectAll();
        adminList.forEach(admin -> System.out.println(admin));
    }
}