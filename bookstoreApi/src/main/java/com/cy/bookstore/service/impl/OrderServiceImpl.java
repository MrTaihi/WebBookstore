package com.cy.bookstore.service.impl;

import com.cy.bookstore.VO.CartVO;
import com.cy.bookstore.VO.OrderVO;
import com.cy.bookstore.entity.Address;
import com.cy.bookstore.entity.Order;
import com.cy.bookstore.entity.OrderItem;
import com.cy.bookstore.entity.Product;
import com.cy.bookstore.mapper.OrderMapper;
import com.cy.bookstore.mapper.ProductMapper;
import com.cy.bookstore.service.IAddressService;
import com.cy.bookstore.service.ICartService;
import com.cy.bookstore.service.IOrderService;
import com.cy.bookstore.service.ex.InsertException;
import com.cy.bookstore.service.ex.UpdateException;
import org.eclipse.core.runtime.IProduct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class OrderServiceImpl implements IOrderService {

    @Autowired
    private OrderMapper orderMapper;  //自动注入
    @Autowired
    private IAddressService addressService;  // 要更多地依赖于不同实体的服务层 因为在那边做了异常的处理
    @Autowired
    private ICartService cartService;
    @Autowired
    private ProductMapper productMapper;

    @Override
    public Order createOrder(Integer aid, Integer uid, String username, Integer[] cids) {
        List<CartVO> list = cartService.getCartByCid(uid,cids);
        cartService.delCartByCids(cids);  // 创建订单的同时把购物车中的数据删除
        Long totalPrice = 0L;
        for (CartVO c:list){
            totalPrice += c.getRealPrice() * c.getNum();
        }
        Address address = addressService.getAddressByAid(aid,uid);
        Order order = new Order();
        order.setUid(uid);
        //不全order 收获地址数据
        order.setRecvPhone(address.getPhone());
        order.setRecvName(address.getName());
        order.setRecvAddress(address.getAddress());
        order.setRecvProvince(address.getProvinceName());
        order.setRecvCity(address.getCityName());
        order.setRecvArea(address.getAreaName());

        // 支付状态 总价 时间
        order.setStatus(0);
        order.setTotalPrice(totalPrice);
        order.setOrderTime(new Date());

        //补全四项日志
        order.setCreatedUser(username);
        order.setCreatedTime(new Date());
        order.setModifiedUser(username);
        order.setModifiedTime(new Date());

        Integer rows = orderMapper.insertOrder(order);
        if (rows != 1){
            throw new InsertException("插入数据时产生了未知的错误");
        }
        for (CartVO c:list){
            OrderItem orderItem = new OrderItem();
            // 补全具体信息
            orderItem.setOid(order.getOid());
            orderItem.setTitle(c.getTitle());
            orderItem.setPid(c.getPid());
            orderItem.setPrice(c.getRealPrice());
            orderItem.setNum(c.getNum());
            orderItem.setImage(c.getImage());
            //补全日志文件
            orderItem.setCreatedUser(username);
            orderItem.setCreatedTime(new Date());
            orderItem.setModifiedUser(username);
            orderItem.setModifiedTime(new Date());
            rows = orderMapper.insertOrderItem(orderItem);
            if (rows != 1){
                throw new InsertException("插入数据时产生了未知的异常");
            }
        }
        return order;
    }

    @Override
    public List<OrderVO> getOrderInfo(Integer uid) {
        List<OrderVO> orderVOList = new ArrayList<OrderVO>();
        List<Order> orderList = orderMapper.getOrderListByUid(uid);   // 为什么不使用数据库连接的方式：因为这是一对多的关系
        for (Order order:orderList){
            OrderVO orderVO = new OrderVO();

            //添加数据
            List<OrderItem> orderItemList = orderMapper.getOrderItemListByOid(order.getOid());
            orderVO.setOrderItems(orderItemList); // 这个是最重要的
            orderVO.setOrderTime(order.getOrderTime());
            orderVO.setRecvName(order.getRecvName());
            orderVO.setUid(order.getUid());
            orderVO.setRecvPhone(order.getRecvPhone());
            orderVO.setOid(order.getOid());
            orderVO.setRecvName(order.getRecvName());
            orderVO.setRecvProvince(order.getRecvProvince());
            orderVO.setRecvCity(order.getRecvCity());
            orderVO.setRecvArea(order.getRecvArea());
            orderVO.setRecvAddress(order.getRecvAddress());
            orderVO.setTotalPrice(order.getTotalPrice());
            orderVO.setStatus(order.getStatus());
            orderVO.setOrderTime(order.getOrderTime());

            orderVOList.add(orderVO);
        }
        return orderVOList;
    }

    @Override
    public Order createOrderByPid(Integer aid, Integer uid, String username, Integer pid,Integer amount) {
        Product product = productMapper.findProductById(pid);
        Long totalPrice = product.getPrice()*amount;

        Address address = addressService.getAddressByAid(aid,uid);  // 这里与上边出现重复 想方法进行优化
        Order order = new Order();
        order.setUid(uid);
        //补全order 收获地址数据
        order.setRecvPhone(address.getPhone());
        order.setRecvName(address.getName());
        order.setRecvAddress(address.getAddress());
        order.setRecvProvince(address.getProvinceName());
        order.setRecvCity(address.getCityName());
        order.setRecvArea(address.getAreaName());

        // 支付状态 总价 时间
        order.setStatus(0);
        order.setTotalPrice(totalPrice);
        order.setOrderTime(new Date());

        //补全四项日志
        order.setCreatedUser(username);
        order.setCreatedTime(new Date());
        order.setModifiedUser(username);
        order.setModifiedTime(new Date());

        Integer rows = orderMapper.insertOrder(order);
        if (rows != 1){
            throw new InsertException("插入数据时产生了未知的错误");
        }

        OrderItem orderItem = new OrderItem();
        // 补全订单项信息 这里只有一个 故不用循环
        orderItem.setOid(order.getOid());
        orderItem.setTitle(product.getTitle());
        orderItem.setPid(product.getId());
        orderItem.setPrice(product.getPrice());
        orderItem.setNum(product.getNum());
        orderItem.setImage(product.getImage());
        //补全日志文件
        orderItem.setCreatedUser(username);
        orderItem.setCreatedTime(new Date());
        orderItem.setModifiedUser(username);
        orderItem.setModifiedTime(new Date());
        rows = orderMapper.insertOrderItem(orderItem);
        if (rows != 1){
            throw new InsertException("插入数据时产生了未知的异常");
        }

        return order;
    }

    @Override
    public void changeStatus(Integer oid, Integer statusId, String username) {
        Integer rows = orderMapper.changeStatus(statusId,oid,username,new Date());
        if (rows!=1){
            throw new UpdateException("更新订单状态时出现了未知的异常");
        }
    }

    @Override
    public void changeRecvInfo(Integer oid, Integer aid,Integer uid,String username) {
        Address address = addressService.getAddressByAid(aid,uid);
        Order order = new Order();
        //设置地址信息
        order.setRecvName(address.getName());
        order.setRecvPhone(address.getPhone());
        order.setRecvProvince(address.getProvinceName());
        order.setRecvCity(address.getCityName());
        order.setRecvArea(address.getAreaName());
        order.setRecvAddress(address.getAddress());

        //设置日志记录
        order.setModifiedUser(username);
        order.setModifiedTime(new Date());

        //设置订单信息
        order.setOid(oid);

        Integer rows = orderMapper.updateRecvAddress(order);
        if (rows!=1){
            throw new UpdateException("更新时出现了未知的异常");
        }
    }
}
