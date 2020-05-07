package com.lpc.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lpc.entity.pojo.SystemUser;
import com.lpc.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class SystemUserController {
    private final UserServiceImpl userService;

    @Autowired
    public SystemUserController(UserServiceImpl userService) {
        this.userService = userService;
    }

    /**
     * 获取发起该请求的用户真实姓名
     *
     * @return 发起该请求的用户真实姓名
     */
    @GetMapping("/user/real_name")
    public String getRealName() {
        return userService.getRealName();
    }

    /**
     * 修改密码
     *
     * @param oldPassword 旧密码
     * @param newPassword 要修改的新密码
     */
    @PutMapping("/user/password")
    public void updatePassword(@RequestParam("oldPassword") String oldPassword,
                               @RequestParam("newPassword") String newPassword) {
        userService.updatePassword(oldPassword, newPassword);
    }

    /**
     * 初始化密码
     *
     * @param username 用户名
     */
    @PutMapping("/user/password/init")
    public void initializePassword(@RequestParam("username") Long username) {
        userService.initializePassword(username);
    }

    /**
     * 分页下获取所有账户符合条件的账户
     *
     * @param pageNum  当前页码
     * @param pageSize 每页数据条数
     * @param realName 系统用户真实姓名，支持模糊查询
     */
    @GetMapping("/users")
    public Page<SystemUser> getSystemUsers(@RequestParam("pageNum") int pageNum,
                                           @RequestParam("pageSize") int pageSize,
                                           @RequestParam("realName") String realName) {
        //如果前面什么都没传，接收到的realName==""
        return userService.getSystemUsers(pageNum, pageSize, realName);
    }

    /**
     * 删除用户
     *
     * @param username 用户名
     */
    @DeleteMapping("/user")
    public void deleteSystemUser(@RequestParam("username") Long username) {
        userService.deleteSystemUser(username);
    }

    /**
     * 更改用户角色
     *
     * @param systemUser 系统用户对象。
     *                   username、role两个字段不能为null
     */
    @PutMapping("/user/role")
    public void updateUserRole(@RequestBody SystemUser systemUser) {
        userService.updateUserRole(systemUser);
    }

    /**
     * 添加用户
     *
     * @param systemUser 要新增的系统用户对象。
     *                   role、username、realName三个字段不能为null
     */
    @PostMapping("/user")
    public void addSystemUser(@RequestBody SystemUser systemUser) {
        userService.addSystemUser(systemUser);
    }
}
