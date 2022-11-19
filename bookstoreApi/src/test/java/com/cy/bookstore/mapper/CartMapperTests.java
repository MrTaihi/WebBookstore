package com.cy.bookstore.mapper;

import com.cy.bookstore.VO.CartVO;
import com.cy.bookstore.entity.Cart;
import com.cy.bookstore.entity.Product;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class CartMapperTests {
    @Autowired
    private CartMapper cartMapper;

    @Test
    public void insert(){
        Cart cart = new Cart();
        cart.setUid(7);
        cart.setPid(2);
        cartMapper.insertIntoCart(cart);
    }

    @Test
    public void update(){
        cartMapper.updateCart(1,2,"管理员",new Date());
    }

    @Test
    public void select(){
        Cart cart = cartMapper.findByUidAndPid(7,2);
        System.out.println(cart);
    }

    @Test
    public void find(){
        System.out.println(cartMapper.findCartByUid(7));
    }

    @Test
    public void findCartListByCid(){
        Integer[] cids = {1,2,4,5,6};
        List<CartVO> list = cartMapper.findCartListByCid(cids);
        System.out.println(list);
    }

    @Test
    public void deleteCart(){
        cartMapper.deleteByUidAndPid(7,2);
    }
}
