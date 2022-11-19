package com.cy.bookstore.mapper;

import com.cy.bookstore.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.Date;

/**
 * 用户模块的持久层接口
 * */
//@Mapper 可以 但不建议这么做 建议直接在main函数上面写上mapperscan
public interface UserMapper {
    /**
     * 新增用户
     * @param user
     * @return 插入的行数
     */
    Integer addUser(User user);

    /**
     * 查找用户名是否已经存在
     * @param username 新增的用户名
     * @return 如果对应的用户 返回user 否则 返回null
     */
    User findByUsername(String username);

    /**
     * 查找手机号码是否已经存在
     * @param phone
     * @return
     */
    User findByPhone(String phone);

    /**
     * 更改密码
     * @param uid 用户id
     * @param password 新密码
     * @param modifiedUser 修改者
     * @param modifiedTime 修改时间
     * @return 受影响的行数
     */
    Integer updatePassword(Integer uid, String password, String modifiedUser, Date modifiedTime);

    /**
     * 通过uid找到用户
     * @param uid 用户id
     * @return 找到的User对象
     */
    User findByUid(Integer uid);

    /**
     * 通过uid修改用户的信息
     * @param user 用户信息
     * @return 返回受影响的行数
     */
    Integer updateInfoByUid(User user);

    /**
     * 更新头像
     * @param uid 用户id
     * @param avatar 头像地址
     * @param modifiedUser 修改人
     * @param modifiedTime 修改时间
     * @return 受影响的行数
     * 其中Param注解相当于起别名的作用
     */
    Integer updateAvatarByUid(@Param("uid") Integer uid, String avatar, String modifiedUser, Date modifiedTime);
}
