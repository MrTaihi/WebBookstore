package com.cy.bookstore.controller;

import com.cy.bookstore.VO.CartVO;
import com.cy.bookstore.service.ICartService;
import com.cy.bookstore.util.JsonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@Api(tags = "购物车")
@RestController
@RequestMapping("api/cart")
public class CartController extends BaseController{
    @Autowired
    private ICartService cartService;

    /**
     * 将商品添加到购物车
     * @param pid 商品id
     * @param amount 购买数量
     * @param uid 用户id
     * @param username 用户名
     * @return 返回地址id
     */
    @ApiOperation("添加购物车")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pid",dataTypeClass = Integer.class,value = "商品id",required = true),
            @ApiImplicitParam(name = "amount",dataTypeClass = String.class,value = "购买数量",required = true),
            @ApiImplicitParam(name = "uid",dataTypeClass = Integer.class,value = "用户id",required = true),
            @ApiImplicitParam(name = "username",dataTypeClass = String.class,value = "用户名",required = true)
    })
    @PostMapping("addToCart")
    public JsonResult<Integer> addToCart(@RequestParam Integer pid,@RequestParam Integer amount,@RequestParam Integer uid,@RequestParam String username){
        Integer data = cartService.addToCart(pid,uid,amount,username);
        System.out.println("aid:"+data);
        return new JsonResult<>(OK,data);
    }

    /**
     * 通过uid获得购物车
     * @param uid 用户id
     * @return 所有购物车数据
     */
    @ApiOperation("获得购物车数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "uid",dataTypeClass = Integer.class,value = "用户id",required = true)
    })
    @GetMapping({"","/{uid}"})
    // 把参数写在路径上
    public JsonResult<List<CartVO>> getCartByUid(@PathVariable Integer uid){
        List<CartVO> data = cartService.getCartByUid(uid);
        return new JsonResult<>(OK,data);
    }

    /**
     * 通过用户id和商品id删除购物车
     * @param uid 用户id
     * @param pid 商品id
     * @return 返回成功状态码
     */
    @ApiOperation("通过pid和uid删除购物车")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pid",dataTypeClass = Integer.class,value = "商品id",required = true),
            @ApiImplicitParam(name = "uid",dataTypeClass = Integer.class,value = "用户id",required = true)
    })
    @PostMapping("/deleteByUidAndPid")
    public JsonResult<Void> deleteCart(@RequestParam Integer uid,@RequestParam Integer pid){
        cartService.delCart(uid,pid);
        return new JsonResult<>(OK);
    }

    /**
     * 通过cid删除购物车
     * @param cid 购物车id
     * @return 成功状态码
     */
    @ApiOperation("通过cid删除购物车")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "cid",dataTypeClass = Integer.class,value = "购物车id",required = true),
    })
    @PostMapping("/deleteByCid")
    public JsonResult<Void> deleteCartByCid(@RequestParam Integer cid){
        cartService.delCartByCid(cid);
        return new JsonResult<>(OK);
    }

    /**
     * 批量删除购物车
     * @param cids 购物车id列表
     * @return 成功状态码
     */
    @ApiOperation("批量删除购物车")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "cids",dataTypeClass = Integer[].class,value = "cid列表",required = true),
    })
    @PostMapping("/deleteByCids")
    public JsonResult<Void> deleteCartByCids(@RequestParam Integer[] cids){
        cartService.delCartByCids(cids);
        return new JsonResult<>(OK);
    }

    /**
     * 通过多个购物车id获得购物车数据
     * @param uid 用户id
     * @param cids 多个购物车id
     * @return 符合条件的多条购物车数据
     */
    @ApiOperation("获得对应id的购物车数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "uid",dataTypeClass = Integer.class,value = "用户id",required = true),
            @ApiImplicitParam(name = "cids",dataTypeClass = Arrays.class,value = "购买数量",required = true),
    })
    @GetMapping("getCartByCids")
    public JsonResult<List<CartVO>> getCartByCid(Integer uid,Integer[] cids){
        List<CartVO> data = cartService.getCartByCid(uid,cids);
        return new JsonResult<>(OK,data);
    }
}
