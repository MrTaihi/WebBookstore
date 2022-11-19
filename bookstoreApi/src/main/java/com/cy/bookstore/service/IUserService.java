package com.cy.bookstore.service;

import com.cy.bookstore.entity.User;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * user服务接口
 */
public interface IUserService {
    //用户注册接口
    void reg(User user);
    //用户登录接口
    User login(String username,String password);
    //用户更改密码接口
    void updatePassword(Integer uid,String username,String oldPassword,String newPassword);
    //通过uid获取用户信息
    User getInfoByUid(Integer uid);
    //用户修改细节信息
    void updateInfo(User user);
    //更新用户头像
    String updateAvatar(Integer uid, MultipartFile avatarFile, String username) throws IOException;
}
