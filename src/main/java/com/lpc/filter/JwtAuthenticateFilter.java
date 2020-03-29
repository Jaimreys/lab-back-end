package com.lpc.filter;

import com.lpc.entity.enumeration.HttpStatusEnum;
import com.lpc.util.ResponseDataUtil;
import io.jsonwebtoken.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * 用于token校验的过滤器
 * 我写的全局异常捕获只能处理控制层的异常，所以这里抛出异常没用
 */
public class JwtAuthenticateFilter extends BasicAuthenticationFilter {
    public JwtAuthenticateFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    public JwtAuthenticateFilter(AuthenticationManager authenticationManager,
                                 AuthenticationEntryPoint authenticationEntryPoint) {
        super(authenticationManager, authenticationEntryPoint);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain)
            throws IOException, ServletException {
        String jwt = request.getHeader("Authorization");
        if (jwt == null || !jwt.startsWith("Bearer ")) {
            ResponseDataUtil.setDataInResponse400(response,
                    null,
                    HttpStatusEnum.UN_LOGIN);
            chain.doFilter(request, response);
        } else {
            //存在jwt时
            //去掉token前加的前缀
            jwt = jwt.replace("Bearer ", "");
            try {
                Claims claims = Jwts.parser()
                        .setSigningKey("FatShallot")
                        .parseClaimsJws(jwt)
                        .getBody();

                String username = claims.getSubject();
                List<GrantedAuthority> roles
                        = AuthorityUtils.commaSeparatedStringToAuthorityList((String) claims.get("authorities"));
                UsernamePasswordAuthenticationToken token
                        = new UsernamePasswordAuthenticationToken(username, null, roles);
                SecurityContextHolder.getContext().setAuthentication(token);

                chain.doFilter(request, response);
            } catch (ExpiredJwtException e) {
                // 解析token时发现过期，会抛出ExpiredJwtException
                e.printStackTrace();
                ResponseDataUtil.setDataInResponse400(response,
                        null,
                        HttpStatusEnum.TOKEN_EXPIRED);
            } catch (UnsupportedJwtException |
                    MalformedJwtException |
                    SignatureException |
                    IllegalArgumentException e) {
                e.printStackTrace();
                ResponseDataUtil.setDataInResponse400(response,
                        null,
                        HttpStatusEnum.UN_LOGIN);
            }
        }
    }
}
