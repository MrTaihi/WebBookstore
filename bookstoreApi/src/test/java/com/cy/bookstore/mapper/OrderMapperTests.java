package com.cy.bookstore.mapper;

import com.cy.bookstore.entity.Order;
import com.cy.bookstore.entity.OrderItem;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class OrderMapperTests {
    @Autowired
    private OrderMapper orderMapper;

    @Test
    public void insertOrder(){  // 插入订单
        Order order = new Order();
        order.setUid(7);
        order.setRecvName("张三");
        order.setRecvPhone("123344325");
        orderMapper.insertOrder(order);
    }

    @Test
    public void insertOrderItem(){    // 插入订单项
        OrderItem orderItem = new OrderItem();
        orderItem.setPid(1);
        orderItem.setOid(1);
        orderItem.setTitle("xxxxx");
        orderMapper.insertOrderItem(orderItem);
    }

    @Test
    public void getOrders(){
        List<Order> list = orderMapper.getOrderListByUid(10);
        System.out.println(list);
    }

    @Test
    public void getOrderItems(){
        List<OrderItem> list = orderMapper.getOrderItemListByOid(3);
        System.out.println(list);
    }

    @Test
    public void updateOrders(){
        Order order = new Order();
        order.setRecvName("随便");
        order.setRecvArea("xxx");
        order.setModifiedUser("管理员");
        order.setModifiedTime(new Date());
        order.setOid(16);
        orderMapper.updateRecvAddress(order);
    }
}
