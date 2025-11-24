package com.doubleshan.scenery.security;

import com.doubleshan.scenery.response.ApiResponse;
import com.doubleshan.scenery.response.ErrorCodes;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Arrays;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {
    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String path = request.getRequestURI();
        try {
            if (path.startsWith("/api/v1/auth/login") || path.startsWith("/api/v1/merchant/login")
                    || path.startsWith("/actuator")) {
                filterChain.doFilter(request, response);
                return;
            }
            String auth = request.getHeader("Authorization");
            if (auth == null || !auth.startsWith("Bearer ")) {
                unauthorized(response, "缺少令牌");
                return;
            }
            String token = auth.substring(7);
            JwtUtil.JwtUser user = JwtUtil.parse(token);
            SecurityContext.set(user);
            // 角色校验
            RoleRequired roleRequired = resolveRoleAnnotation(request);
            if (roleRequired != null) {
                boolean ok = Arrays.stream(roleRequired.value()).anyMatch(r -> r.equalsIgnoreCase(user.role));
                if (!ok) {
                    unauthorized(response, "角色不允许");
                    return;
                }
            }
            filterChain.doFilter(request, response);
        } catch (Exception e) {
            unauthorized(response, "令牌无效");
        } finally {
            SecurityContext.clear();
        }
    }

    private void unauthorized(HttpServletResponse resp, String msg) throws IOException {
        resp.setStatus(401);
        resp.setContentType("application/json;charset=UTF-8");
        mapper.writeValue(resp.getWriter(), ApiResponse.error(ErrorCodes.UNAUTHORIZED, msg));
    }

    private RoleRequired resolveRoleAnnotation(HttpServletRequest request) {
        Object handler = request.getAttribute("org.springframework.web.servlet.HandlerMapping.bestMatchingHandler");
        if (handler == null)
            return null;
        try {
            Method m = handler.getClass().getMethod("getMethod");
            Method real = (Method) m.invoke(handler);
            return AnnotatedElementUtils.findMergedAnnotation(real, RoleRequired.class);
        } catch (Exception ignored) {
        }
        return null;
    }
}
