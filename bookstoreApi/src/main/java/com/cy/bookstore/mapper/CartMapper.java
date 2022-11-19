package com.cy.bookstore.mapper;

import com.cy.bookstore.VO.CartVO;
import com.cy.bookstore.entity.Cart;

import java.util.Date;
import java.util.List;

public interface CartMapper {
    /**
     * 往购物车里插入数据
     * @param cart 购物车数据
     * @return 受影响的行数
     */
    Integer insertIntoCart(Cart cart);

    /**
     * 更新购物车数据
     * @param cid 唯一表示一个购物车数据
     * @param amount 数量
     * @param modifiedUser 修改人
     * @param modifiedTime 修改时间
     * @return 受影响的行数
     */
    Integer updateCart(Integer cid, Integer amount, String modifiedUser, Date modifiedTime);

    /**
     * 查找购物车数据是否存在，若不存在，则插入，若存在，则更新数据
     * @param uid 用户id
     * @param pid 商品id
     * @return 查找到的购物车数据
     */
    Cart findByUidAndPid(Integer uid,Integer pid);

    /**
     * 根据用户id查找购物车数据
     * @param uid 用户id
     * @return 查询到的所有购物车数据
     */
    List<CartVO> findCartByUid(Integer uid);

    /**
     * 根据购物车id列表寻找对应的cartVO 用于在点击勾选的时候展示下单数据
     * @param cids 购物车id列表
     * @return 返回cartVO对象列表
     */
    List<CartVO> findCartListByCid(Integer[] cids);

    /**
     * 通过用户id和商品id删除购物车数据
     * @param uid 用户id
     * @param pid 商品id
     * @return 受影响的行数
     */
    Integer deleteByUidAndPid(Integer uid,Integer pid);

    /**
     * 通过购物车id删除购物车
     * @param cid 购物车id
     * @return 受影响的行数
     */
    Integer deleteByCid(Integer cid);
}
