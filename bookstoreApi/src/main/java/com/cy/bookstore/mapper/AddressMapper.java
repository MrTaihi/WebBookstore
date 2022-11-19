package com.cy.bookstore.mapper;

import com.cy.bookstore.entity.Address;

import java.util.Date;
import java.util.List;

public interface AddressMapper {
    /**
     * 新增地址数据
     * @param address 地址数据
     * @return 返回受影响的行数
     */
    Integer addAddress(Address address);

    /**
     * 得到一个用户存储的地址的数量
     * @param uid 用户id
     * @return 返回当前用户的收货地址总数
     */
    Integer getAddressNum(Integer uid);

    /**
     * 根据用户id获得用户所有的收获地址
     * @param uid 用户id
     * @return 返回用户所有的收获地址数据
     */
    List<Address> getAddressByUid(Integer uid);

    /**
     * 根据地址id获得相应的地址数据
     * @param aid 地址id
     * @return 地址数据
     */
    Address getAddressByAid(Integer aid);

    /**
     * 根据用户id 把用户所有的收获地址都设置为非默认地址
     * @param uid 用户id
     * @return 受影响的行数
     */
    Integer setAllNotDefault(Integer uid);

    /**
     * 根据aid 将对应的地址设置为默认地址
     * @param aid 地址id
     * @return 返回受影响的行数
     */
    Integer updateDefault(Integer aid, String modifiedUser, Date modifiedTime);

    /**
     * 根据uid找到最后做修改的地址
     * @param uid 用户id
     * @return 返回相应的地址
     */
    Address findLastModifiedAddress(Integer uid);

    /**
     * 根据aid删除地址
     * @param aid 地址id
     * @return 返回受影响的行数
     */
    Integer deleteAddress(Integer aid);

    Integer updateAddress(Address address);

}
