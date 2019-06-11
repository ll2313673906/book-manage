package com.bm.factory;

import com.bm.service.AdminService;
import com.bm.service.UserService;
import com.bm.service.impl.AdminServiceImpl;
import com.bm.service.impl.UserServiceImpl;

public class ServiceFactory {
    public static AdminService getAdminServiceInstance(){
        return new AdminServiceImpl();
    }
    public static UserService getUserServiceInstance(){
        return new UserServiceImpl();
    }
}
