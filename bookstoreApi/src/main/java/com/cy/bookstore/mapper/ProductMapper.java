package com.cy.bookstore.mapper;

import com.cy.bookstore.entity.Product;

import java.util.Date;
import java.util.List;

public interface ProductMapper {
    /**
     * 获得热销商品列表（这两个还是没有删除的）
     * @return 返回热销商品
     */
    List<Product> getHotProduct();

    /**
     * 获得最新商品列表
     * @return 返回最新商品
     */
    List<Product> getNewProduct();

    /**
     * 通过书本的id寻找相应的书本
     * @param id 书本id
     * @return 找到的书本
     */
    Product findProductById(Integer id);

    /**
     * 插入新的商品数据
     * @param product 商品对象
     * @return 受影响的行数
     */
    Integer insertNewProduct(Product product);

    /**
     * 更新商品信息
     * @param product 商品对象
     * @return 受影响的行数
     */
    Integer updateProduct(Product product);

    /**
     * 通过商品id删除商品
     * @param id 商品id
     * @return 返回受影响的行数
     */
    Integer deleteByPid(Integer id, String username, Date modifiedTime);

    /**
     * 通过pid重新上架商品
     * @param id 商品id
     * @param username 修改人用户名
     * @param modifiedTime 修改时间
     * @return 受影响的条数
     */
    Integer reListingByPid(Integer id,String username,Date modifiedTime);

    /**
     * 通过商品id更改图片路径
     * @param pid 商品id
     * @param username 用户名
     * @param modifiedTime 修改时间
     * @param imgPath 图片地址
     * @return 受影响的行数
     */
    Integer addProductImg(Integer pid,String username,Date modifiedTime,String imgPath);

    /**
     * 通过对象中存在的数据作为条件筛选
     * @param product 商品对象
     * @return 返回查询到的所有结果
     */
    List<Product> getAll(Product product);
}
