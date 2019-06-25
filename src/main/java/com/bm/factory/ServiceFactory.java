package com.bm.factory;

import com.bm.service.*;
import com.bm.service.impl.*;


public class ServiceFactory {
    public static AdminService getAdminServiceInstance(){
        return new AdminServiceImpl();
    }
    public static UserService getUserServiceInstance(){ return new UserServiceImpl(); }
    public static BookService getBookServiceInstance(){return new BookServiceImpl(); }
    public static StudentService getStudentServiceInstance(){return new StudentServiceImpl(); }
    public static BookTypeService getBookTypeServiceInstance(){return new BookTypeServiceImpl();}
    public static BorrowBookService getBorrowBookServiceInstance(){return new BorrowBookServiceImpl(); }
    public static BookCarService getBookCarServiceInstance(){return new BookCarServiceImpl();}
}
