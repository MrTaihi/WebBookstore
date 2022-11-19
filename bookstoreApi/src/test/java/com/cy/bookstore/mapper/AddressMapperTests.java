package com.cy.bookstore.mapper;

import com.cy.bookstore.entity.Address;
import com.cy.bookstore.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;

@SpringBootTest   // 测试类，不会随同项目一块打包
@RunWith(SpringRunner.class)  // 表示启动这个单元测试类 单元测试类是不饿能够运行的，需要传递一个参数，必须是springrunner的实例类型
public class AddressMapperTests {
    // idea有检测的功能 接口是不能够直接创建Bean的（动态代理技术来解决）
    @Autowired
    private AddressMapper addressMapper;
    /**
     * 单元测试方法：就可以单独独立运行 不用启动整个项目 可以做单元测试
     * 1、必须被@Test注解修饰
     * 2、返回值类型必须是void
     * 3、方法的参数列表不指定任何类型
     * 4、方法的访问修饰符必须是public
     */

    @Test
    public void insert(){
        Address address = new Address();
        address.setName("管理员");
        address.setUid(7);
        addressMapper.addAddress(address);
    }

    @Test
    public void select(){
        System.out.println(addressMapper.getAddressNum(7));
    }

    @Test
    public void getAllAddressData(){
        List<Address> list = addressMapper.getAddressByUid(7);
        System.out.println(list);
    }

}
