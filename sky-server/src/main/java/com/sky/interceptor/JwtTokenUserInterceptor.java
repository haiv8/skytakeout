package com.sky.interceptor;

import com.sky.constant.JwtClaimsConstant;
import com.sky.context.BaseContext;
import com.sky.properties.JwtProperties;
import com.sky.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.HandlerMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
     * 用户端 jwt 令牌校验的拦截器
     */
    @Component
    @Slf4j
    public class JwtTokenUserInterceptor implements HandlerInterceptor {

        @Autowired
        private JwtProperties jwtProperties;

        @Override
        public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
            // 判断是否拦截的是Controller方法
            if (!(handler instanceof HandlerMethod)) {
                return true; // 不是Controller方法（比如静态资源），直接放行
            }

            // 1. 从请求头中获取 Token
            String token = request.getHeader(jwtProperties.getUserTokenName());

            try {
                log.info("【User端】jwt校验: {}", token);
                // 2. 解析 token
                Claims claims = JwtUtil.parseJWT(jwtProperties.getUserSecretKey(), token);

                // 3. 获取用户ID
                Long userId = Long.valueOf(claims.get(JwtClaimsConstant.USER_ID).toString());

                // 4. 存入 ThreadLocal
                BaseContext.setCurrentId(userId);
                log.info("【User端】当前用户id：{}", userId);

                return true; // 放行
            } catch (Exception e) {
                log.error("【User端】jwt校验失败: {}", e.getMessage());
                response.setStatus(401); // 未认证
                return false;
            }
        }
    }

