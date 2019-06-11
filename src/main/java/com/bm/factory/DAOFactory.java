package com.bm.factory;

import com.bm.dao.AdminDAO;
import com.bm.dao.impl.AdminDAOImpl;

public class DAOFactory {
    public static AdminDAO getAdminDAOInstance(){
        return new AdminDAOImpl();
    }
}
