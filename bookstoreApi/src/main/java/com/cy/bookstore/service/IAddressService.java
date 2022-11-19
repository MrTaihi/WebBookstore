package com.cy.bookstore.service;

import com.cy.bookstore.entity.Address;
import com.cy.bookstore.service.ex.AddressNotFoundException;

import java.util.List;

public interface IAddressService {
    /**
     * 添加新地址
     * @param address 地址对象
     */
    void addNewAddress(Address address);

    /**
     * 获得用户地址列表
     * @param uid 用户id
     * @return 地址列表
     */
    List<Address> getAddressByUid(Integer uid);

    /**
     * 设置默认地址
     * @param aid 地址id
     * @param uid 用户id
     * @param username 修改者姓名 去标识修改者
     */
    void setDefault(Integer aid,Integer uid,String username);

    /**
     * 删除地址
     * @param aid 地址id
     * @param uid 用户id
     * @param username 修改者姓名
     */
    void deleteAddress(Integer aid, Integer uid, String username);

    /**
     * 通过aid获得单个address数据
     * @param aid 地址id
     * @param uid 用户id
     * @return 返回地址对象
     */
    Address getAddressByAid(Integer aid,Integer uid);

    /**
     * 编辑地址id
     * @param address 地址对象
     */
    void editAddress(Address address);
}
