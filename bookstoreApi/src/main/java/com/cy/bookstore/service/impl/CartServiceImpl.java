package com.cy.bookstore.service.impl;

import com.cy.bookstore.VO.CartVO;
import com.cy.bookstore.entity.Cart;
import com.cy.bookstore.mapper.CartMapper;
import com.cy.bookstore.mapper.ProductMapper;
import com.cy.bookstore.service.ICartService;
import com.cy.bookstore.service.ex.AccessDeniedException;
import com.cy.bookstore.service.ex.DeleteException;
import com.cy.bookstore.service.ex.InsertException;
import com.cy.bookstore.service.ex.UpdateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class CartServiceImpl implements ICartService {
    @Autowired
    private CartMapper cartMapper;

    @Autowired
    private ProductMapper productMapper;

    @Override
    public Integer addToCart(Integer pid, Integer uid, Integer amount, String username) {
        Cart result = cartMapper.findByUidAndPid(uid,pid);
        Date date = new Date();
        if (result == null){  // 插入
            Cart cart = new Cart();
            cart.setPid(pid);
            cart.setUid(uid);
            cart.setNum(amount);
            cart.setPrice(productMapper.findProductById(pid).getPrice());

            //下面是四项日志记录
            cart.setCreatedUser(username);
            cart.setCreatedTime(date);
            cart.setModifiedUser(username);
            cart.setModifiedTime(date);

            Integer rows = cartMapper.insertIntoCart(cart);
            if (rows!=1){
                throw new InsertException("插入数据时出现了未知的异常");
            }
            return cart.getCid(); //插入之后会将原来的数据进行补全 但更新不会
        }else{  //更新
            Integer num = amount + result.getNum();  // 只有数量进行了更新
            Integer rows = cartMapper.updateCart(result.getCid(),num,username,date);
            if (rows!=1){
                throw new UpdateException("更新数据时出现了未知的异常");
            }
            return result.getCid();
        }
    }

    @Override
    public List<CartVO> getCartByUid(Integer uid) {
        List<CartVO> list = cartMapper.findCartByUid(uid);
        return list;
    }

    @Override
    public List<CartVO> getCartByCid(Integer uid, Integer[] cids) {
        List<CartVO> list = cartMapper.findCartListByCid(cids);
        for (CartVO c:list){
            if (!c.getUid().equals(uid)){
                throw new AccessDeniedException("数据非法访问");
            }
        }
        return list;
    }

    @Override
    public void delCart(Integer uid, Integer pid) {
        Integer rows = cartMapper.deleteByUidAndPid(uid,pid);
        if (rows==0){
            throw new DeleteException("删除购物车数据时出现了未知的异常");
        }
    }

    @Override
    public void delCartByCid(Integer cid) {
        Integer rows = cartMapper.deleteByCid(cid);
        if (rows!=0){
            throw new DeleteException("删除购物车数据时出现了未知的异常");
        }
    }

    @Override
    public void delCartByCids(Integer[] cids) {
        for (Integer cid:cids){
            Integer rows = cartMapper.deleteByCid(cid);
            if (rows!=1){
                throw new DeleteException("删除购物车时出现了未知的异常");
            }
        }
    }
}
