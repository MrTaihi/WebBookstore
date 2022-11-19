package com.cy.bookstore.controller;

import com.cy.bookstore.entity.Address;
import com.cy.bookstore.service.IAddressService;
import com.cy.bookstore.util.JsonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "地址")
@RequestMapping("api/address")
@RestController
public class AddressController extends BaseController{
    @Autowired
    IAddressService addressService;

    /**
     * 通过用户id获取该用户所有的地址
     * @param uid 用户id
     * @return 返回json对象
     */
    @ApiOperation("获得用户所有地址")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "uid", dataTypeClass = Integer.class, value = "用户id", required = true)
    })
    @GetMapping("{uid}")  // 路径为address或者address/时 自动拦截
    public JsonResult<List<Address>> getAddressByUid(@PathVariable Integer uid){
        List<Address> data = addressService.getAddressByUid(uid);
        return new JsonResult<>(OK,data);
    }

    /**
     * 添加新的收获地址
     * @param address 地址对象
     * @return 返回json对象
     */
    @ApiOperation("新增收获地址")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "uid", dataTypeClass = Integer.class, value = "用户id", required = true),
            @ApiImplicitParam(name = "provinceCode",dataTypeClass = String.class, value = "省份id",required = true),
            @ApiImplicitParam(name = "provinceName",dataTypeClass = String.class, value = "省份名",required = true),
            @ApiImplicitParam(name = "cityCode",dataTypeClass = String.class, value = "市id",required = true),
            @ApiImplicitParam(name = "cityName",dataTypeClass = String.class, value = "市名",required = true),
            @ApiImplicitParam(name = "areaCode",dataTypeClass = String.class, value = "区id",required = true),
            @ApiImplicitParam(name = "areaName",dataTypeClass = String.class, value = "区名",required = true),
    })
    @PostMapping("addNewAddress")
    public JsonResult<Void> addNewAddress(Address address){
        addressService.addNewAddress(address);
        return new JsonResult<>(OK);
    }

    /**
     * 设置默认地址
     * @param aid 地址id
     * @param uid 用户id
     * @param username 姓名
     * @return 成功结果编码
     */
    @ApiOperation("设置默认地址")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "aid", dataTypeClass = Integer.class, value = "地址id", required = true),
            @ApiImplicitParam(name = "uid", dataTypeClass = Integer.class, value = "用户id", required = true),
            @ApiImplicitParam(name = "username", dataTypeClass = String.class, value = "地址id", required = true)
    })
    @PostMapping("setDefault")
    public JsonResult<Void> setDefaultAddress(@RequestParam Integer aid,@RequestParam Integer uid,@RequestParam String username){
        addressService.setDefault(aid,uid,username);
        return new JsonResult<>(OK);
    }

    /**
     * 删除收获地址
     * @param aid 地址id
     * @param uid 用户id
     * @param username 用户姓名
     * @return 返回json对象
     */
    @ApiOperation("删除地址")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "aid", dataTypeClass = Integer.class, value = "地址id", required = true),
            @ApiImplicitParam(name = "uid", dataTypeClass = Integer.class, value = "用户id", required = true),
            @ApiImplicitParam(name = "username", dataTypeClass = String.class, value = "地址id", required = true)
    })
    @PostMapping("deleteAddress")
    public JsonResult<Void> deleteAddress(@RequestParam Integer aid,@RequestParam Integer uid,@RequestParam String username){
        addressService.deleteAddress(aid,uid,username);
        return new JsonResult<>(OK);
    }

    /**
     * 更新收获地址
     * @param address 地址对象
     * @return 返回Json成功状态码
     */
    @ApiOperation("更新收货地址")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "uid", dataTypeClass = Integer.class, value = "用户id", required = true),
            @ApiImplicitParam(name = "provinceCode",dataTypeClass = String.class, value = "省份id",required = true),
            @ApiImplicitParam(name = "provinceName",dataTypeClass = String.class, value = "省份名",required = true),
            @ApiImplicitParam(name = "cityCode",dataTypeClass = String.class, value = "市id",required = true),
            @ApiImplicitParam(name = "cityName",dataTypeClass = String.class, value = "市名",required = true),
            @ApiImplicitParam(name = "areaCode",dataTypeClass = String.class, value = "区id",required = true),
            @ApiImplicitParam(name = "areaName",dataTypeClass = String.class, value = "区名",required = true),
    })
    @PostMapping("updateAddress")
    public JsonResult<Void> updateAddress(Address address){
        addressService.editAddress(address);
        return new JsonResult<>(OK);
    }

    /**
     * 获得单条地址数据
     * @param aid 地址id
     * @param uid 用户id
     * @return 返回地址对象
     */
    @ApiOperation("获得单条地址数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "aid", dataTypeClass = Integer.class, value = "地址id", required = true),
            @ApiImplicitParam(name = "uid", dataTypeClass = Integer.class, value = "用户id", required = true),
    })
    @GetMapping("getAddressInfo")
    public JsonResult<Address> getAddressByAid(@RequestParam Integer aid,@RequestParam Integer uid){
        Address data = addressService.getAddressByAid(aid,uid);
        return new JsonResult<>(OK,data);
    }
}
