package com.bm.factory;

import com.bm.dao.AdminDAO;
import com.bm.dao.UserDAO;
import com.bm.dao.impl.AdminDAOImpl;
import com.bm.dao.impl.UserDAOImpl;

public class DAOFactory {
    public static AdminDAO getAdminDAOInstance(){
        return new AdminDAOImpl();
    }
    public static UserDAO getUserDAOInstance(){
        return new UserDAOImpl();
    }
}
