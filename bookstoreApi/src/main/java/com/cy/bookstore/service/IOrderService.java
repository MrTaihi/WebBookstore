package com.cy.bookstore.service;

import com.cy.bookstore.VO.OrderVO;
import com.cy.bookstore.entity.Order;

import java.util.List;

public interface IOrderService {
    /**
     * 创建订单
     * @param aid 地址id
     * @param uid 用户id
     * @param username 用户名
     * @param cids 多个购物车id
     * @return 返回生成的订单对象
     */
    Order createOrder(Integer aid,Integer uid,String username,Integer[] cids);

    /**
     * 通过uid获得该用户所有的订单
     * @param uid 用户id
     * @return 返回订单列表
     */
    List<OrderVO> getOrderInfo(Integer uid);

    /**
     * 通过立即购买创建订单
     * @param aid 地址id
     * @param uid 用户id
     * @param username 用户名
     * @param pid 商品id
     * @return 创建的订单
     */
    Order createOrderByPid(Integer aid,Integer uid,String username,Integer pid,Integer amount);

    /**
     * 改变订单状态
     * @param oid 订单id
     * @param statusId 状态id
     * @param username 用户名
     */
    void changeStatus(Integer oid,Integer statusId,String username);

    /**
     * 修改收货信息
     * @param oid 订单id
     * @param aid 地址id
     */
    void changeRecvInfo(Integer oid,Integer aid,Integer uid,String username);
}
