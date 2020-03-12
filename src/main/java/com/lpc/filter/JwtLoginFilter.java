package com.lpc.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lpc.entity.SystemUser;
import com.lpc.enumeration.HttpStatusEnum;
import com.lpc.util.ResponseDataUtil;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.Date;

/**
 * 登录验证
 * 登录成功就生成token并放入响应头
 */
public class JwtLoginFilter extends UsernamePasswordAuthenticationFilter {

    private AuthenticationManager authenticationManager;

    public JwtLoginFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    /**
     * 验证用户信息
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response)
            throws AuthenticationException {
        try {
            SystemUser systemUser = new ObjectMapper().readValue(request.getInputStream(), SystemUser.class);
            //用户名去掉空格
            systemUser.setUsername(systemUser.getUsername().trim());
            UsernamePasswordAuthenticationToken token
                    = new UsernamePasswordAuthenticationToken(systemUser.getUsername(), systemUser.getPassword());
            return authenticationManager.authenticate(token);
        } catch (IOException e) {
            ResponseDataUtil.setDataInResponse400(response,
                    null,
                    HttpStatusEnum.LOGIN_UNSUCCESSFUL);
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult)
            throws IOException, ServletException {
        Collection<? extends GrantedAuthority> authorities = authResult.getAuthorities();
        StringBuffer role = new StringBuffer();
        for (GrantedAuthority authority : authorities) {
            role.append(authority.getAuthority())
                    .append(",");
        }

        String jwt = Jwts.builder()
                //配置用户角色
                .claim("authorities", role)
                .setSubject(authResult.getName())
                //过期时间，一小时
                .setExpiration(new Date(System.currentTimeMillis() + 60 * 60 * 1000))
                .signWith(SignatureAlgorithm.HS512, "FatShallot")
                .compact();

        //将token写到响应里
        response.addHeader("Authorization", "Bearer " + jwt);
        ResponseDataUtil.setDataInResponse(response, null, HttpStatusEnum.SUCCESSFUL);

        chain.doFilter(request, response);
    }

    @Override
    public void unsuccessfulAuthentication(HttpServletRequest req,
                                           HttpServletResponse resp,
                                           AuthenticationException failed)
            throws IOException, ServletException {
        ResponseDataUtil.setDataInResponse400(resp,
                null,
                HttpStatusEnum.LOGIN_UNSUCCESSFUL);
    }
}