package com.lpc.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lpc.entity.dto.SystemUserDTO;
import com.lpc.entity.pojo.SystemUser;
import com.lpc.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class SystemUserController {
    private UserServiceImpl userService;


    @Autowired
    public SystemUserController(UserServiceImpl userService) {
        this.userService = userService;
    }

    /**
     * 获取用户的真实姓名
     */
    @GetMapping("/user/real_name")
    public String getRealName() {
        return userService.getRealName();
    }

    /**
     * 修改密码
     */
    @PutMapping("/user/password")
    public void updatePassword(@RequestParam("oldPassword") String oldPassword,
                               @RequestParam("newPassword") String newPassword) {
        userService.updatePassword(oldPassword, newPassword);
    }

    /**
     * 初始化密码
     */
    @PutMapping("/user/password/init")
    public void initializePassword(@RequestParam("username") String username) {
        userService.initializePassword(username);
    }

    /**
     * 获取所有账户
     */
    @GetMapping("/users")
    public PageInfo<SystemUserDTO> getSystemUsers(@RequestParam("pageNum") int pageNum,
                                                  @RequestParam("pageSize") int pageSize,
                                                  @RequestParam("realName") String realName) {
        //如果前面什么都没传，realName==""
        PageHelper.startPage(pageNum, pageSize);
        List<SystemUserDTO> users = userService.getSystemUsers(realName);
        return new PageInfo<>(users);
    }

    /**
     * 删除用户
     */
    @DeleteMapping("/user")
    public void deleteSystemUser(@RequestParam("username") String username) {
        userService.deleteSystemUser(username);
    }

    /**
     * 更改用户角色
     */
    @PutMapping("/user/role")
    public void updateUserRole(@RequestBody SystemUser systemUser) {
        userService.updateUserRole(systemUser);
    }

    /**
     * 添加用户
     */
    @PostMapping("/user")
    public void addSystemUser(@RequestBody SystemUser systemUser) {
        userService.addSystemUser(systemUser);
    }
}
