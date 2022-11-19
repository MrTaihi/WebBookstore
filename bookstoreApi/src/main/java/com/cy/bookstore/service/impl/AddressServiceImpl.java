package com.cy.bookstore.service.impl;

import com.cy.bookstore.entity.Address;
import com.cy.bookstore.mapper.AddressMapper;
import com.cy.bookstore.service.IAddressService;
import com.cy.bookstore.service.ex.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class AddressServiceImpl implements IAddressService {
    @Autowired
    AddressMapper addressMapper;

    @Override
    public void addNewAddress(Address address) {
        Integer addressNum = addressMapper.getAddressNum(address.getUid());
        if (addressNum >= 20){
            throw new AddressCountLimitException("用户收货地址数量超出限制");
        }
        if (addressNum == 0){
            address.setIsDefault(1);
        }else{
            address.setIsDefault(0);
        }

        //还需要日志数据
        address.setModifiedUser(address.getCreatedUser());
        address.setCreatedTime(new Date());
        address.setModifiedTime(new Date());

        //插入address
        Integer rows = addressMapper.addAddress(address);
        if (rows != 1){
            throw new InsertException("插入地址数据时出现了未知的异常");
        }
    }

    @Override
    public List<Address> getAddressByUid(Integer uid) {
        //这里应该对传输的数据量进行优化的 但这里就暂时不优化了
        return addressMapper.getAddressByUid(uid);
    }

    @Override
    public void setDefault(Integer aid, Integer uid, String username) {
        Address result = addressMapper.getAddressByAid(aid);
        if (result == null){
            throw new AddressNotFoundException("地址已被删除");
        }
        if (!result.getUid().equals(uid)){
            throw new AccessDeniedException("非法数据访问");
        }
        Integer rows = addressMapper.setAllNotDefault(uid);  // 这里已经将所有设置成了非默认
        if (rows < 1){
            throw new UpdateException("数据更新时异常");
        }
        rows = addressMapper.updateDefault(aid,username,new Date()); // 将地址修改铖默认地址 还需要将已经是默认地址的取消默认
        if (rows != 1){
            throw new UpdateException("数据更新时异常");
        }
    }

    @Override
    public void deleteAddress(Integer aid, Integer uid, String username) {
        Address result = addressMapper.getAddressByAid(aid);
        if (result == null){
            throw new AddressNotFoundException("地址已被删除");
        }
        if (!result.getUid().equals(uid)){
            throw new AccessDeniedException("非法数据访问");
        }
        Integer rows = addressMapper.deleteAddress(aid);
        if (rows != 1){
            throw new DeleteException("删除数据时产生了未知的异常");
        }
        Integer count = addressMapper.getAddressNum(uid);
        if (count == 0 || result.getIsDefault() == 0){
            return;
        }
        Address address = addressMapper.findLastModifiedAddress(uid);
        rows = addressMapper.updateDefault(address.getAid(),username,new Date());
        if (rows != 1){
            throw new UpdateException("更新数据时产生未知的异常");
        }
    }

    @Override
    public Address getAddressByAid(Integer aid, Integer uid) {
        Address address = addressMapper.getAddressByAid(aid);
        if (address == null){
            throw new AddressNotFoundException("收获地址不存在");
        }
        if (!address.getUid().equals(uid)){
            throw new AccessDeniedException("数据非法访问");
        }
        // 四项地址文件
        address.setCreatedTime(null);
        address.setCreatedUser(null);
        address.setModifiedTime(null);
        address.setModifiedUser(null);
        return address;
    }

    @Override
    public void editAddress(Address address) {
        address.setModifiedUser(address.getCreatedUser());  // 这里在逻辑上是错误的 因为管理员也可以修改地址
        address.setModifiedTime(new Date());
        address.setIsDefault(0);
        Integer rows = addressMapper.updateAddress(address);
        if (rows!=1){
            throw new UpdateException("更新地址数据时出现了未知的异常");
        }
    }
}
