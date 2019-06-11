package com.bm.factory;

import com.bm.service.AdminService;
import com.bm.service.impl.AdminServiceImpl;

public class ServiceFactory {
    public static AdminService getAdminServiceInstance(){
        return new AdminServiceImpl();
    }
}
