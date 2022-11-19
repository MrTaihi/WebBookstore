package com.cy.bookstore.controller;

import com.cy.bookstore.entity.Product;
import com.cy.bookstore.service.IProductService;
import com.cy.bookstore.util.JsonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Api(tags = "商品")
@RequestMapping("api/product")
@RestController
public class ProductController extends BaseController{
    @Autowired
    IProductService productService;

    /**
     * 得到热门产品（书籍）
     * @return 返回热门书籍数据
     */
    @ApiOperation("获得热门产品")
    @GetMapping("getHotList")
    public JsonResult<List<Product>> getHotList(){
        List<Product> data = productService.getHotList();
        return new JsonResult<>(OK,data);
    }

    /**
     * 得到最新商品列表（书籍）
     * @return 返回最新商品列表
     */
    @ApiOperation("得到最新商品")
    @GetMapping("getNewList")
    public JsonResult<List<Product>> getNewList(){
        List<Product> data = productService.getNewList();
        return new JsonResult<>(OK,data);
    }

    /**
     * 通过商品id获得商品信息
     * @param productId 商品id
     * @return 商品信息
     */
    @ApiOperation("获得单个商品信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "productId",dataTypeClass = Integer.class,value = "商品id",required = true)
    })
    @GetMapping("getProductInfo/{productId}")
    public JsonResult<Product> getProductInfo(@PathVariable Integer productId){
        Product data = productService.getProductInfo(productId);
        return new JsonResult<>(OK,data);
    }

    /**
     * 添加新的商品
     * @param product 商品对象
     * @return 返回商品id
     */
    @ApiOperation("添加新商品")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id",dataTypeClass = Integer.class,value = "商品id",required = true)
    })
    @PostMapping("addNewProduct")
    public JsonResult<Integer> addNewProduct(Product product){
        Integer pid = productService.addNewProduct(product);
        return new JsonResult<>(OK,pid);
    }

    /**
     * 更新商品信息
     * @param product 商品对象
     * @return 结果状态码
     */
    @PostMapping("updateProduct")
    public JsonResult<Void> updateProduct(Product product){
        productService.updateProduct(product);
        return new JsonResult<>(OK);
    }

    /**
     * 删除商品
     * @param id 商品id
     * @param modifiedUser 修改者用户名
     * @return 结果状态码
     */
    @ApiOperation("删除商品")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id",dataTypeClass = Integer.class,value = "商品id",required = true),
            @ApiImplicitParam(name = "modifiedUser",dataTypeClass = String.class,value = "修改者用户名",required = true)
    })
    @PostMapping("deleteProduct")
    public JsonResult<Void> deleteProduct(@RequestParam Integer id,@RequestParam String modifiedUser){
        productService.deleteProduct(id,modifiedUser);
        return new JsonResult<>(OK);
    }

    /**
     * 重新上架商品
     * @param id 商品id
     * @param modifiedUser 修改者用户名
     * @return 结果状态码
     */
    @ApiOperation("重新上架商品")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id",dataTypeClass = Integer.class,value = "商品id",required = true),
            @ApiImplicitParam(name = "modifiedUser",dataTypeClass = String.class,value = "修改者用户名",required = true)
    })
    @PostMapping("reListingProduct")
    public JsonResult<Void> reListing(@RequestParam Integer id,@RequestParam String modifiedUser){
        productService.reListingProduct(id,modifiedUser);
        return new JsonResult<>(OK);
    }



    /**
     * 添加商品图片
     * @param pid 商品id
     * @param username 用户名
     * @param file 图片文件
     * @return 返回图片路径
     * @throws IOException 错误则抛出异常
     */
    @ApiOperation("添加商品图片")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pid",dataTypeClass = Integer.class,value = "商品id",required = true),
            @ApiImplicitParam(name = "username",dataTypeClass = String.class,value = "用户名",required = true),
            @ApiImplicitParam(name = "file",dataTypeClass = MultipartFile.class,value = "图片文件",required = true)
    })
    @PostMapping("addProductImg")
    public JsonResult<String> addProductImg(@RequestParam Integer pid,@RequestParam String username,@RequestPart  MultipartFile file) throws IOException {
        String imgPath = productService.addProductImg(pid,username,file);
        return new JsonResult<>(OK,imgPath);
    }

    @ApiOperation("按条件获得数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "categoryId",dataTypeClass = Integer.class,value = "分类id"),  //不是必须的
            @ApiImplicitParam(name = "title",dataTypeClass = String.class,value = "商品名"),
    })
    @GetMapping("getAll")
    public JsonResult<List<Product>> getAll(Product product){
        List<Product> data = productService.getAll(product);
        return new JsonResult<>(OK,data);
    }
}
