package com.cy.bookstore.service.impl;

import com.cy.bookstore.entity.User;
import com.cy.bookstore.mapper.UserMapper;
import com.cy.bookstore.service.IUserService;
import com.cy.bookstore.service.ex.*;
import com.cy.bookstore.util.FileHandlerUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.UUID;

/**
 * 用户模块实现类
 */
@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserMapper userMapper;
    @Value("D:/bookstore/upload/")
    private String publicDirectory;

    @Override
    public void reg(User user) {
        String username = user.getUsername();
        User result = userMapper.findByUsername(username);
        if(result != null){
            throw new UsernameDuplicatedException("该用户名已被占用");
        }
//        result = userMapper.findByPhone(user.getPhone());
//        if (result != null){
//            throw new PhoneDuplicatedException("该号码已被注册！");
//        }

        //进行密码的加密
        String truePassword = user.getPassword();
        String salt = UUID.randomUUID().toString().toUpperCase();
        String md5Password = getMD5Password(truePassword,salt);
        user.setSalt(salt);
        user.setPassword(md5Password);

        //补全数据
        user.setIsDelete(0);
        user.setCreatedUser(user.getUsername());
        user.setModifiedUser(user.getUsername());
        Date date = new Date();
        user.setCreatedTime(date);   //这里的时间会自动解析
        user.setModifiedTime(date);

        Integer rows = userMapper.addUser(user);
        if(rows != 1){
            throw new InsertException("数据库或服务器出现未知异常！");
        }
    }

    @Override
    public User login(String username, String password) {
        User user = userMapper.findByUsername(username);
        if(user == null){
            throw new UsernameNotFindException("该用户不存在");
        }
        if(user.getIsDelete() == 1){
            throw new UsernameNotFindException("该用户不存在");
        }
        String md5Password = user.getPassword();
        String salt = user.getSalt();
        String newMd5Password = getMD5Password(password,salt);
        if(!md5Password.equals(newMd5Password)) {
            throw new PasswordNotMatchException("账号密码不匹配");
        }
        //这里可以返回一个新的user对象，需要省去除uid，username，avatar等字段，起到节省运行空间的作用
        return user;
    }

    @Override
    public void updatePassword(Integer uid, String username, String oldPassword, String newPassword) {
        User result = userMapper.findByUid(uid);
        if(result == null || result.getIsDelete() == 1){
            throw new UsernameNotFindException("用户数据不存在");
        }
        String oldMd5Password = getMD5Password(oldPassword,result.getSalt());
        if (!result.getPassword().equals(oldMd5Password)){
            throw new PasswordNotMatchException("旧密码匹配错误");
        }
        String newMd5Password = getMD5Password(newPassword,result.getSalt());
        Integer rows = userMapper.updatePassword(uid,newMd5Password,username,new Date());
        if (rows != 1){
            throw new UpdateException("修改密码时出现未知的异常");
        }
    }

    @Override
    public User getInfoByUid(Integer uid) {
        User result = userMapper.findByUid(uid);
        if(result == null || result.getIsDelete() == 1){
            throw new UsernameNotFindException("用户数据不存在");
        }
        User user = new User();
        user.setUid(result.getUid());
        user.setUsername(result.getUsername());   //前端页面呈现的四个字段 好像也确实是这四个就好了
        user.setPhone(result.getPhone());
        user.setEmail(result.getEmail());
        user.setSex(result.getSex());
        user.setAvatar(result.getAvatar());
        return user;
    }

    @Override
    public void updateInfo(User user) {
        User result = userMapper.findByUid(user.getUid());
        System.out.print(result);
        if (result == null || result.getIsDelete() == 1){
            throw new UsernameNotFindException("用户数据不存在");
        }
        user.setUid(user.getUid());
        user.setModifiedUser(user.getUsername());
        user.setModifiedTime(new Date());

        Integer rows = userMapper.updateInfoByUid(user);
        if (rows != 1){
            throw new InsertException("用户在插入数据时出现了未知的异常");
        }
    }

    @Override
    public String updateAvatar(Integer uid, MultipartFile avatarFile, String username) throws IOException {
        if (!FileHandlerUtil.isImg(avatarFile)) {
            throw new IsNotImage("上传的文件不是图片");
        }
        String avatarPath = FileHandlerUtil.transfer(avatarFile, publicDirectory, "user/" + uid + "/avatar", System.currentTimeMillis() + "-avatar");
        if (userMapper.updateAvatarByUid(uid,avatarPath,username,new Date()) > 0) {
            return avatarPath;
        }
        return "";
    }

    //md5加密处理函数
    private String getMD5Password(String password,String salt){
        for(int i=0;i<3;i++) {
            password = DigestUtils.md5DigestAsHex((salt+password+salt).getBytes()).toUpperCase();
        }
        return password;
    }
}
