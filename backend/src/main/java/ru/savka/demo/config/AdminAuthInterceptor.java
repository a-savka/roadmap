package ru.savka.demo.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class AdminAuthInterceptor implements HandlerInterceptor {

    private static final String ADMIN_TOKEN = "admin-token-static";
    private static final String AUTH_HEADER = "X-Admin-Token";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String path = request.getRequestURI();

        if (path.startsWith("/api/admin/") && !path.contains("/auth/login")) {
            String token = request.getHeader(AUTH_HEADER);
            if (token == null || !token.equals(ADMIN_TOKEN)) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("{\"message\":\"Unauthorized\"}");
                return false;
            }
        }
        return true;
    }
}