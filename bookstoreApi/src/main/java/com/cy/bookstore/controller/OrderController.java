package com.cy.bookstore.controller;

import com.cy.bookstore.VO.OrderVO;
import com.cy.bookstore.entity.Order;
import com.cy.bookstore.service.IOrderService;
import com.cy.bookstore.util.JsonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@Api(tags = "订单")
@RestController
@RequestMapping("api/order")
public class OrderController extends BaseController{
    @Autowired
    private IOrderService orderService;

    /**
     * 在购物车多选创建订单
     * @param aid 地址id
     * @param cids 多个购物车id
     * @param uid 用户id
     * @param username 用户名
     * @return 返回订单数据
     */
    @ApiOperation("购物车多选创建订单")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "aid",dataTypeClass = Integer.class,value = "地址id",required = true),
            @ApiImplicitParam(name = "cids",dataTypeClass = Arrays.class,value = "多个购物车id",required = true),
            @ApiImplicitParam(name = "uid",dataTypeClass = Integer.class,value = "用户id",required = true),
            @ApiImplicitParam(name = "username",dataTypeClass = String.class,value = "用户名",required = true),
    })
    @PostMapping("createOrder")
    public JsonResult<Order> createOrder(@RequestParam Integer aid,@RequestParam Integer[] cids,@RequestParam Integer uid,@RequestParam String username){
        Order data = orderService.createOrder(aid,uid,username,cids);
        return new JsonResult<>(OK,data);
    }

    @ApiOperation("获得用户订单")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "uid",dataTypeClass = Integer.class,value = "用户id",required = true)
    })
    @GetMapping("getOrders/{uid}")
    public JsonResult<List<OrderVO>> getOrders(@PathVariable Integer uid){
        List<OrderVO> data = orderService.getOrderInfo(uid);
        return new JsonResult<>(OK,data);
    }

    @ApiOperation("立即购买创建订单")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "aid",dataTypeClass = Integer.class,value = "地址id",required = true),
            @ApiImplicitParam(name = "pid",dataTypeClass = Arrays.class,value = "商品id",required = true),
            @ApiImplicitParam(name = "uid",dataTypeClass = Integer.class,value = "用户id",required = true),
            @ApiImplicitParam(name = "username",dataTypeClass = String.class,value = "用户名",required = true),
            @ApiImplicitParam(name = "amount",dataTypeClass = Integer.class,value = "购买数量",required = true),
    })
    @PostMapping("createOrderByPid")
    public JsonResult<Order> createOrderByPid(@RequestParam Integer aid,@RequestParam Integer uid,@RequestParam String username,@RequestParam Integer pid,@RequestParam Integer amount){
        Order data = orderService.createOrderByPid(aid,uid,username,pid,amount);
        return new JsonResult<>(OK,data);
    }

    @ApiOperation("改变订单状态")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "oid",dataTypeClass = Integer.class,value = "订单id",required = true),
            @ApiImplicitParam(name = "statusId",dataTypeClass = Integer.class,value = "状态id",required = true),
            @ApiImplicitParam(name = "username",dataTypeClass = String.class,value = "用户名",required = true)
    })
    @PostMapping("updateStatus")
    public JsonResult<Void> updateOrderStatus(@RequestParam Integer oid,@RequestParam Integer statusId,@RequestParam String username){
        orderService.changeStatus(oid,statusId,username);
        return new JsonResult<>(OK);
    }

    @ApiOperation("改变订单收货信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "oid",dataTypeClass = Integer.class,value = "订单id",required = true),
            @ApiImplicitParam(name = "aid",dataTypeClass = Integer.class,value = "地址id",required = true),
            @ApiImplicitParam(name = "uid",dataTypeClass = Integer.class,value = "用户id",required = true),
            @ApiImplicitParam(name = "username",dataTypeClass = String.class,value = "用户名",required = true)
    })
    @PostMapping("changeRecvInfo")
    public JsonResult<Void> changeRecvInfo(@RequestParam Integer oid,@RequestParam Integer aid,@RequestParam Integer uid,@RequestParam String username){
        orderService.changeRecvInfo(oid,aid,uid,username);
        return new JsonResult<>(OK);
    }
}
