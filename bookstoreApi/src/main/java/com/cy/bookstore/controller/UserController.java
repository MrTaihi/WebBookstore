package com.cy.bookstore.controller;

import com.cy.bookstore.controller.ex.*;
import com.cy.bookstore.entity.User;
import com.cy.bookstore.service.IUserService;
import com.cy.bookstore.util.JsonResult;
import com.sun.istack.internal.NotNull;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 用户控制层
 */
@Api(tags = "用户")
@RestController   // 相当于Controller+ResponseBody
@RequestMapping("api/user")
@Validated
//Validated表示开启校验功能 但需要在bean中各个变量的上面添加注释 才可以开启校验
public class UserController extends BaseController{
    @Autowired
    private IUserService userService;

    /**
     * 接收数据的方式1：使用实体类的方式进行接收
     * @param user
     * @return
     */
    @ApiOperation("用户注册")
    @PostMapping("reg")
    public JsonResult<String> reg(User user){
        JsonResult<String> result = new JsonResult<>();
        userService.reg(user);
        result.setState(OK);
        result.setData("success");
        result.setMessage("用户创建成功！");
        return result;
    }

    /**
     * 接收数据的方式二：使用具体的字段进行接收 不适用实体类的方式
     * @param username 用户名
     * @param password 密码
     * @return JsonResult<User>
     */
    @ApiOperation("用户登录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username",dataTypeClass = String.class,value = "用户名",required = true),
            @ApiImplicitParam(name = "password",dataTypeClass = String.class,value = "密码",required = true)
    })
    @PostMapping ("login")
    public JsonResult<User> login(@RequestParam @NotNull String username, @RequestParam @NotNull String password){
        User data = userService.login(username,password);
        return new JsonResult<>(OK,data);
    }

    /**
     * 更换密码
     * @param oldPassword 旧密码
     * @param newPassword 新密码
     * @param uid uid（找到相应的数据）
     * @param username 得到修改者信息
     * @return 操作成功状态 并不会携带信息
     */
    @RequestMapping("updataPassword")
    public JsonResult<Void> updataPassword(@RequestParam String oldPassword,@RequestParam String newPassword,@RequestParam Integer uid,@RequestParam String username){
        userService.updatePassword(uid,username,oldPassword,newPassword);
        return new JsonResult<>(OK);
    }

    /**
     * 根据uid获得用户信息
     * @param uid 用户id
     * @return 返回用户信息
     */
    @ApiOperation("获得用户信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "uid",dataTypeClass = Integer.class,value = "用户id",required = true),
    })
    @GetMapping("getInfoByUid/{uid}")
    public JsonResult<User> getInfoByUid(@PathVariable Integer uid){
        User data = userService.getInfoByUid(uid);
        return new JsonResult<>(OK,data);
    }

    /**
     * 更新用户信息
     * @param user 用户对象
     * @return 结果状态码
     */
    @ApiOperation("更新用户信息")
    @PostMapping("updateUserInfo")
    public JsonResult<Void> updateUserInfo(User user){
        userService.updateInfo(user);
        return new JsonResult<>(OK);
    }

    /**
     * 更新用户头像
     * @param uid 用户id
     * @param username 用户名
     * @param file 头像文件
     * @return 头像本地存储的地址
     * @throws IOException 错误则抛出异常
     */
    @ApiOperation("更新用户头像")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "uid",dataTypeClass = Integer.class,value = "用户id",required = true),
            @ApiImplicitParam(name = "username",dataTypeClass = String.class,value = "用户名",required = true),
            @ApiImplicitParam(name = "file",dataTypeClass = MultipartFile.class,value = "用户头像文件",required = true)
    })
    @PostMapping("updateAvatar")
    public JsonResult<String> updateAvatar(@RequestParam Integer uid,@RequestParam String username,@RequestPart MultipartFile file) throws IOException {
//        if (file.isEmpty()){
//            throw new FileEmptyException("文件为空");
//        }
//        if (file.getSize()>MAX_AVATAR_SIZE){
//            throw new FileSizeException("文件大小不能超过10M");
//        }
//        String contentType = file.getContentType();
//        if (!AVATAR_TYPE.contains(contentType)){
//            throw new FileTypeException("上传的文件类型错误！");
//        }
//        String parent = session.getServletContext().getRealPath("upload");
//        String parent = "D:/bookstore/bookstore-api/upload/";
//        File dir = new File(parent);
//        if (!dir.exists()){
//            dir.mkdirs();
//        }
//        String originalFilename = file.getOriginalFilename();
//        int index = originalFilename.lastIndexOf('.');
//        String sufflx = originalFilename.substring(index);
//        String fileName = UUID.randomUUID().toString().toUpperCase().substring(0,10)+sufflx;
//        File dest = new File(dir,fileName);
//        try {
//            file.transferTo(dest);
//        } catch(FileStateException e) {
//            throw new FileStateException("文件状态异常");
//        } catch (IOException e) {
//            throw new FileUploadIOException("文件读写异常");
//        }
//        String avatarPath = "D:/bookstore/bookstore-api/upload/" + fileName; // 这里存在了本地路径 以后想一想还有没有其他的方法
//        userService.updateAvatar(uid,avatarPath,username);
        String avatarPath = userService.updateAvatar(uid,file,username);
        return new JsonResult<>(OK,avatarPath);
    }
}
