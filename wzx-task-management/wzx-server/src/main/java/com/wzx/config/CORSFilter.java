package com.wzx.config;

import com.wzx.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.Enumeration;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;

@Component
public class CORSFilter implements Filter {

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // 初始化方法，可以留空
    }

    @Override
    public void destroy() {
        // 销毁方法，可以留空
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        // CORS headers
        String origin = request.getHeader("Origin");
        if (origin != null) {
            response.setHeader("Access-Control-Allow-Origin", origin);
            response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE, HEAD, PUT");
            response.setHeader("Access-Control-Max-Age", "3600");
            response.setHeader("Access-Control-Allow-Headers", "Authorization, authority, content-type, version-info, X-Requested-With");
            response.setHeader("Access-Control-Allow-Credentials", "true");
        }

        // 处理预检请求
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            response.setStatus(HttpServletResponse.SC_OK);
            return; // 结束处理预检请求
        }

        // Token validation and userId extraction
        AtomicReference<Long> userId = new AtomicReference<>(null); // 使用 AtomicReference

        boolean isLoginRequest = request.getRequestURI().equals("/api/admin/login");
        boolean isRegisterRequest = request.getRequestURI().equals("/api/admin/register");
        // 只有在非登录请求时进行用户ID的校验
        if (!isLoginRequest|| isRegisterRequest) {
            try {
                userId.set(jwtUtil.getUserId(request)); // 获取用户ID
            } catch (IllegalArgumentException e) {
                // 处理无效的Token，记录日志（可选）
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 返回401状态
                return; // 终止请求处理
            }
        }

        // Wrap the request to add loginUserId as a parameter
        HttpServletRequestWrapper requestWrapper = new HttpServletRequestWrapper(request) {
            @Override
            public String[] getParameterValues(String name) {
                if ("loginUserId".equals(name) && userId.get() != null) {
                    return new String[]{userId.get().toString()};
                }
                return super.getParameterValues(name);
            }

            @Override
            public Enumeration<String> getParameterNames() {
                Set<String> paramNames = new LinkedHashSet<>();
                if (userId.get() != null) {
                    paramNames.add("loginUserId");
                }
                Enumeration<String> names = super.getParameterNames();
                while (names.hasMoreElements()) {
                    paramNames.add(names.nextElement());
                }
                return Collections.enumeration(paramNames);
            }
        };

        // 继续处理请求
        filterChain.doFilter(requestWrapper, response);
    }
}