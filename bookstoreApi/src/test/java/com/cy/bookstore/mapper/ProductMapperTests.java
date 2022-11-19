package com.cy.bookstore.mapper;

import com.cy.bookstore.entity.Product;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ProductMapperTests {
    @Autowired
    private ProductMapper productMapper;

    @Test
    public void getHotList(){
        List<Product> list = productMapper.getHotProduct();
        for (Product product:list){
            System.out.println(product);
        }
    }

    @Test
    public void insertProduct(){
        Product product = new Product();
        product.setCategoryId(2);
        product.setAuthor("郑利城");
        productMapper.insertNewProduct(product);
    }

    @Test
    public void update(){
        Product product = new Product();
        product.setId(9);
        product.setCategoryId(3);
        productMapper.updateProduct(product);
    }

    @Test
    public void getAll(){
        Product product = new Product();
        product.setStatus(0);
        List<Product> list = productMapper.getAll(product);
        System.out.println(list);
    }
}
