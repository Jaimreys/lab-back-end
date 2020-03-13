package com.lpc.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

import java.util.List;

/**
 * jwt处理的工具类
 */
public class JwtUtil {
    public static String getRole(String jwt) {
        jwt = jwt.replace("Bearer ", "");
        Claims claims = Jwts.parser()
                .setSigningKey("FatShallot")
                .parseClaimsJws(jwt)
                .getBody();
        //获取保存在token中的用户角色信息
        String role = (String) claims.get("authorities");
        return role;
    }
}
