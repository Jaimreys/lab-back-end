package com.lpc.controller;

import com.lpc.entity.CustomizedException;
import com.lpc.entity.SystemUser;
import com.lpc.service.impl.UserServiceImpl;
import com.lpc.util.SystemUserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
public class SystemUserController {
    private UserServiceImpl userService;
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public SystemUserController(UserServiceImpl userService,
                                BCryptPasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * 改为PUT方法更合理
     */
    @PutMapping("/password")
    public void updateUserPassword(@RequestParam("oldPassword") String oldPassword,
                                   @RequestParam("newPassword") String newPassword) {
        String username = SystemUserUtil.getUsername();
        SystemUser systemUser = userService.getSystemUserByUsername(username);
        if (passwordEncoder.matches(oldPassword, systemUser.getPassword())) {
            //原密码正确，更新到新密码
            userService.updatePasswordByUsername(systemUser.getUsername(),
                    passwordEncoder.encode(newPassword));
        } else {
            throw new CustomizedException(4000, "原密码错误");
        }
    }
}
