package com.cy.bookstore.service;

import com.cy.bookstore.entity.User;
import com.cy.bookstore.service.ex.PhoneDuplicatedException;
import com.cy.bookstore.service.ex.ServiceException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * UserService测试类
 */
@SpringBootTest   // 测试类，不会随同项目一块打包
@RunWith(SpringRunner.class)  // 表示启动这个单元测试类 单元测试类是不饿能够运行的，需要传递一个参数，必须是springrunner的实例类型
public class UserServiceTests {
    @Autowired
    private IUserService userService;   // 注意：这里一定要自动注入

    @Test
    public void reg(){
        try {
            User user = new User();
            user.setUsername("admin");
            user.setPassword("123");
            userService.reg(user);
            System.out.print("OK");
        } catch (ServiceException e) {
            System.out.print(e.getClass().getSimpleName());
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void login(){
        User user = userService.login("admin","123");
        System.out.println(user);
    }

    @Test
    public void changePassword(){
        userService.updatePassword(8,"管理员","123","321");
    }

    @Test
    public void getInfo(){
        User user = userService.getInfoByUid(7);
        System.out.println(user);
    }

    @Test
    public void updateUserInfo(){
        User user = new User();
        user.setPhone("432432432");
        user.setSex(1);
        user.setEmail("24243432@qq.com");
        userService.updateInfo(user);
    }

//    @Test
//    public void updateAvatar(){
//        userService.updateAvatar(7,"/xxx/yyy/png","超级管理员");
//    }
}
