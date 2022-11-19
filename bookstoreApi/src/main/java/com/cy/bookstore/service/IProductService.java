package com.cy.bookstore.service;

import com.cy.bookstore.entity.Product;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface IProductService {
    /**
     * 获得热销商品列表
     * @return 热销商品列表
     */
    List<Product> getHotList();

    /**
     * 获得最新商品列表
     * @return 最新商品列表
     */
    List<Product> getNewList();

    /**
     * 获得商品信息
     * @param id 商品id
     * @return 返回商品信息
     */
    Product getProductInfo(Integer id);

    /**
     * 添加新的商品
     * @param product 商品对象
     */
    Integer addNewProduct(Product product);

    /**
     * 修改商品信息
     * @param product 商品对象
     */
    void updateProduct(Product product);

    /**
     * 删除商品
     * @param id 商品id
     */
    void deleteProduct(Integer id,String username);

    /**
     * 重新上架商品
     * @param id 商品id
     * @param username 修改者用户名
     */
    void reListingProduct(Integer id,String username);

    /**
     * 添加商品图片
     * @param id 商品id
     * @param username 用户名
     * @param file 文件数据
     */
    String addProductImg(Integer id, String username, MultipartFile file) throws IOException;

    /**
     * 按照对象中的数据 筛选需要的数据
     * @param product 商品对象
     * @return 查询到的数据
     */
    List<Product> getAll(Product product);
}
