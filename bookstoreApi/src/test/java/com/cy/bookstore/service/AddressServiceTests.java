package com.cy.bookstore.service;

import com.cy.bookstore.entity.Address;
import com.cy.bookstore.entity.User;
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
public class AddressServiceTests {
    @Autowired
    private IAddressService addressService;   // 注意：这里一定要自动注入

    @Test
    public void addNewAddress(){
        Address address = new Address();
        address.setTel("xxx");
        address.setIsDefault(1);
        addressService.addNewAddress(address);
    }

    @Test
    public void setDefaultAddress(){
        addressService.setDefault(3,7,"管理员");
    }

    @Test
    public void deleteAddress(){
        addressService.deleteAddress(3,7,"管理员");
    }
}
