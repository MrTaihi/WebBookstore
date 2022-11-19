package com.cy.bookstore.service;

import com.cy.bookstore.VO.CartVO;

import java.util.List;

public interface ICartService {
    // 添加购物车 修改一下 把购物车id传递出去
    Integer addToCart(Integer pid,Integer uid,Integer amount,String username);

    // 显示购物车数据 使用VO获得多表连接的购物车数据
    List<CartVO> getCartByUid(Integer uid);

    // 选择在购物车上点击的商品 生成订单数据
    List<CartVO> getCartByCid(Integer uid,Integer[] cids);

    // 通过uid和pid删除购物车
    void delCart(Integer uid,Integer pid);

    //通过cid删除购物车
    void delCartByCid(Integer cid);

    void delCartByCids(Integer[] cids);
}
