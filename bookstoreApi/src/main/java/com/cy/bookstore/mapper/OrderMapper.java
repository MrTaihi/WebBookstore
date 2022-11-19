package com.cy.bookstore.mapper;

import com.cy.bookstore.entity.Order;
import com.cy.bookstore.entity.OrderItem;

import javax.xml.crypto.Data;
import java.util.Date;
import java.util.List;

public interface OrderMapper {
    /**
     * 添加订单数据
     * @param order 订单对象
     * @return 受影响的行数
     */
    Integer insertOrder(Order order);

    /**
     * 插入订单项
     * @param orderItem 订单项对象
     * @return 受影响的行数
     */
    Integer insertOrderItem(OrderItem orderItem);

    /**
     * 通过用户id获得所有的订单
     * @param uid 用户id
     * @return 订单列表
     */
    List<Order> getOrderListByUid(Integer uid);

    /**
     * 通过订单id获得所有的订单项
     * @param oid 订单id
     * @return 订单项列表
     */
    List<OrderItem> getOrderItemListByOid(Integer oid);

    /**
     * 改变订单状态
     * @param statusId 状态id
     * @param oid 订单id
     * @param username 修改人姓名
     * @return 受影响的行数
     */
    Integer changeStatus(Integer statusId,Integer oid, String username,Date modifiedTime);

    /**
     * 修改订单收货地址（在发货之后不能修改）
     * @param order 订单对象
     * @return 受影响的行数
     */
    Integer updateRecvAddress(Order order);
}
