package com.cy.bookstore.controller;

import com.cy.bookstore.controller.ex.*;
import com.cy.bookstore.service.ex.*;
import com.cy.bookstore.util.JsonResult;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpSession;

/**
 * 控制层的基类 用于处理异常
 * 当控制层产生了异常 会统一被拦截到了此方法中
 */
public class BaseController {
    public static final int OK = 200;

    @ExceptionHandler({ServiceException.class,FileUploadException.class})
    public JsonResult<Void> handleException(Throwable e){
        JsonResult<Void> result = new JsonResult<>(e);
        if (e instanceof InsertException){
            result.setState(4000);
        }else if (e instanceof UsernameNotFindException){
            result.setState(4001);
        }else if (e instanceof PasswordNotMatchException){
            result.setState(4002);
        }else if (e instanceof AddressCountLimitException){
            result.setState(4003);
        }else if (e instanceof AddressNotFoundException){
            result.setState(4004);
        }else if (e instanceof AccessDeniedException){  // 非法数据访问异常
            result.setState(4005);
        }else if (e instanceof UsernameDuplicatedException){
            result.setState(5000);
        }else if (e instanceof PhoneDuplicatedException){
            result.setState(5003);
        }else if (e instanceof UpdateException){
            result.setState(5001);
        }else if (e instanceof DeleteException){  // 删除异常
            result.setState(5002);
        }else if (e instanceof FileEmptyException){
            result.setState(6000);
        }else if (e instanceof FileSizeException){
            result.setState(6001);
        }else if (e instanceof FileTypeException){
            result.setState(6002);
        }else if (e instanceof FileUploadIOException){
            result.setState(6003);
        }else if (e instanceof FileStateException){
            result.setState(6004);
        }
        return result;
    }

    protected final Integer getUidFromSession(HttpSession session){
        return Integer.valueOf(session.getAttribute("uid").toString());
    }

    protected final String getUsernameFromSession(HttpSession session){
        return session.getAttribute("username").toString();
    }
}
