package com.lpc.filter;

import com.lpc.enumeration.HttpStatusEnum;
import com.lpc.util.ResponseDataUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
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
import java.util.Date;
import java.util.List;

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
            chain.doFilter(request, response);
            ResponseDataUtil.setDataInResponse400(response,
                    null,
                    HttpStatusEnum.UN_LOGIN);
        } else {
            //存在jwt时
            //去掉token前加的前缀
            jwt = jwt.replace("Bearer ", "");
            Claims claims = Jwts.parser()
                    .setSigningKey("FatShallot")
                    .parseClaimsJws(jwt)
                    .getBody();

            Date expiration = claims.getExpiration();
            if (new Date(System.currentTimeMillis()).before(expiration)) {
                //如果token没有过期
                String username = claims.getSubject();
                List<GrantedAuthority> roles
                        = AuthorityUtils.commaSeparatedStringToAuthorityList((String) claims.get("authorities"));
                UsernamePasswordAuthenticationToken token
                        = new UsernamePasswordAuthenticationToken(username, null, roles);
                SecurityContextHolder.getContext().setAuthentication(token);
                chain.doFilter(request, response);
            } else {
                //如果token过期
                ResponseDataUtil.setDataInResponse400(response,
                        null,
                        HttpStatusEnum.TOKEN_EXPIRED);
                chain.doFilter(request, response);
            }
        }
    }
}
