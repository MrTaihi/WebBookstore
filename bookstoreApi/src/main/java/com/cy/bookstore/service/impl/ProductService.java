package com.cy.bookstore.service.impl;

import com.cy.bookstore.entity.Product;
import com.cy.bookstore.mapper.ProductMapper;
import com.cy.bookstore.service.IProductService;
import com.cy.bookstore.service.ex.*;
import com.cy.bookstore.util.FileHandlerUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.List;

@Service
public class ProductService implements IProductService {

    @Autowired
    private ProductMapper productMapper;
    @Value("D:/bookstore/upload/")
    private String publicDirectory;

    @Override
    public List<Product> getHotList() {
        List<Product> list = productMapper.getHotProduct();
        for (Product product:list){
            product.setPriority(null);
            product.setCreatedTime(null);
            product.setCreatedUser(null);
            product.setModifiedTime(null);
            product.setModifiedUser(null);
        }
        return list;
    }

    @Override
    public List<Product> getNewList() {
        List<Product> list = productMapper.getNewProduct();
        for (Product product:list){  // 删除无用数据
            product.setPriority(null);
            product.setCreatedTime(null);
            product.setCreatedUser(null);
            product.setModifiedTime(null);
            product.setModifiedUser(null);
        }
        return list;
    }

    @Override
    public Product getProductInfo(Integer id) {
        Product product = productMapper.findProductById(id);
        if (product == null){
            throw new ProductNotFoundException("未找到商品信息");
        }
        return product;
    }

    @Override
    public Integer addNewProduct(Product product) {
        //补充日志文件
        product.setModifiedUser(product.getCreatedUser());
        product.setCreatedTime(new Date());
        product.setModifiedTime(new Date());

        int rows = productMapper.insertNewProduct(product);
        if (rows!=1){
            throw new InsertException("插入商品数据时出现了未知的异常");
        }
        return product.getId();
    }

    @Override
    public void updateProduct(Product product) {
        // 设置日志文件
        product.setModifiedTime(new Date());

        //进行更新
        int rows = productMapper.updateProduct(product);
        if (rows!=1){
            throw new UpdateException("修改商品信息时出现了未知的异常");
        }
    }

    @Override
    public void deleteProduct(Integer id,String username) {
        Integer rows = productMapper.deleteByPid(id,username,new Date());
        if (rows!=1){
            throw new DeleteException("删除商品时出现了未知的异常");
        }
    }

    @Override
    public void reListingProduct(Integer id, String username) {
        Integer rows = productMapper.reListingByPid(id,username,new Date());
        if (rows!=1){
            throw new DeleteException("删除商品时出现了未知的异常");
        }
    }

    @Override
    public String addProductImg(Integer id, String username, MultipartFile file) throws IOException {
        if (!FileHandlerUtil.isImg(file)){
            throw new IsNotImage("该文件不是图片");
        }
        Integer rows = 0;
        //将图片数据插入文件中
        String imgPath = FileHandlerUtil.transfer(file,publicDirectory,"product/"+id,System.currentTimeMillis()+"-product"+ "." + FileHandlerUtil.getSuffix(file),false);
        //获得商品的图片信息
        Product product = productMapper.findProductById(id);
        String imagesPath = product.getImage();
        if (imagesPath==null){  //如果为空 则直接插入即可
            rows = productMapper.addProductImg(id,username,new Date(),imgPath);
        }else{
            rows = productMapper.addProductImg(id,username,new Date(),imagesPath+" "+imgPath);
        }
        if (rows!=1){
            throw new UpdateException("更新数据时出现了未知的异常");
        }
        return imgPath;
    }

    @Override
    public List<Product> getAll(Product product) {
        return productMapper.getAll(product);
    }
}
