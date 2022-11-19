package com.cy.bookstore.service;

import com.cy.bookstore.VO.OrderVO;
import com.cy.bookstore.entity.Address;
import com.cy.bookstore.entity.Order;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;

/**
 * UserService测试类
 */
@SpringBootTest   // 测试类，不会随同项目一块打包
@RunWith(SpringRunner.class)  // 表示启动这个单元测试类 单元测试类是不饿能够运行的，需要传递一个参数，必须是springrunner的实例类型
public class OrderServiceTests {
    @Autowired
    private IOrderService orderService;   // 注意：这里一定要自动注入

    @Test
   public void addOrder(){
        Integer[] cids = {1,2,3};
        orderService.createOrder(1,7,"管理员",cids);
    }

    @Test
    public void getOrders(){
        List<OrderVO> orderVOList = orderService.getOrderInfo(10);
        System.out.println(orderVOList);
    }

    @Test
    public void changeStatus(){
        orderService.changeStatus(16,3,"管理员");
    }
}
