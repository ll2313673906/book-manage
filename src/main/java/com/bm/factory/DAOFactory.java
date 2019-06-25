package com.bm.factory;

import com.bm.dao.*;
import com.bm.dao.impl.*;

public class DAOFactory {
    public static AdminDAO getAdminDAOInstance(){
        return new AdminDAOImpl();
    }
    public static UserDAO getUserDAOInstance(){ return new UserDAOImpl(); }
    public static BookDAO getBookDAOInstance(){return new BookDAOImpl(); }
    public static StudentDAO getStudentDAOInstance(){return new StudentDAOImpl(); }
    public static BookTypeDAO getBookTypeDAOInstance(){return new BookTypeDAOImpl();}
    public static BorrowBookDAOImpl getBorrowBookDAOInstance(){return new BorrowBookDAOImpl();}
    public static BookCarDAOImpl getBookCarDAOInstance(){return new BookCarDAOImpl();}

}
