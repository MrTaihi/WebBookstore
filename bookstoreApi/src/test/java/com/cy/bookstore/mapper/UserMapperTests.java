package com.cy.bookstore.mapper;

import com.cy.bookstore.entity.User;
import com.cy.bookstore.service.ex.PhoneDuplicatedException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

@SpringBootTest   // 测试类，不会随同项目一块打包
@RunWith(SpringRunner.class)  // 表示启动这个单元测试类 单元测试类是不饿能够运行的，需要传递一个参数，必须是springrunner的实例类型
public class UserMapperTests {
    // idea有检测的功能 接口是不能够直接创建Bean的（动态代理技术来解决）
    @Autowired
    private UserMapper userMapper;
    /**
     * 单元测试方法：就可以单独独立运行 不用启动整个项目 可以做单元测试
     * 1、必须被@Test注解修饰
     * 2、返回值类型必须是void
     * 3、方法的参数列表不指定任何类型
     * 4、方法的访问修饰符必须是public
     */
    @Test
    public void insert(){   // 该函数只能测试一次 因为用户名要求不可重复
        User user = new User();
        user.setUsername("xxx");
        user.setPassword("123456");
        Integer rows = userMapper.addUser(user);   // 将一整个对象加入
        System.out.print(rows);
    }

    @Test
    public void reg2(){
        User user = new User();
        user.setPhone("15017827264");
        User user1 = userMapper.findByPhone(user.getPhone());
        if (user1 != null){
            System.out.println("该号码已被注册");
        }
    }

    @Test
    public void testSelect(){
        User user = userMapper.findByUsername("XXX");
        System.out.print(user);
    }

    @Test
    public void updatePassword(){
        userMapper.updatePassword(6,"6666666","管理员",new Date());
    }

    @Test
    public void findByUid(){
        User user = userMapper.findByUid(6);
        System.out.println(user);
    }

    @Test
    public void updateUserInfo(){
        User user = new User();
        user.setUid(7);
        user.setPhone("1222323");
        user.setSex(1);
        user.setEmail("12213232@qq.com");
        userMapper.updateInfoByUid(user);
    }

    @Test
    public void findByUsername(){
        User user = userMapper.findByUsername("admin");
        System.out.println(user);
    }

    @Test
    public void updataAvatar(){
        userMapper.updateAvatarByUid(7,"upload/avatar.png","管理员",new Date());
    }
}
