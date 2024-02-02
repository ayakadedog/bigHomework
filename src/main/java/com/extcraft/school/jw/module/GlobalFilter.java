package com.extcraft.school.jw.module;

import com.extcraft.school.jw.entity.auth.User;
import com.extcraft.school.jw.util.PathFactory;
import com.extcraft.school.jw.util.SessionKeys;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 全局Filter
 * TO-EDIT-FULL-DESCRIPTION
 *
 * @author SmallL-U
 * @version 1.0 2023/12/18
 */
@WebFilter(urlPatterns = "/*")
public class GlobalFilter implements Filter {

    static final List<String> WHITELIST_PATH = new ArrayList<>();

    static {
        WHITELIST_PATH.add("/auth/login/");
        WHITELIST_PATH.add("/auth/register/");
        WHITELIST_PATH.add("/api/login");
        WHITELIST_PATH.add("/api/register");
    }


    /*
    必须重写，来自tomcat的奇怪bug
     */
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        String path = ((HttpServletRequest) servletRequest).getServletPath();
        if (((HttpServletRequest) servletRequest).getPathInfo() != null) path += ((HttpServletRequest) servletRequest).getPathInfo();
        String queryString = ((HttpServletRequest) servletRequest).getQueryString();
        HttpSession session = ((HttpServletRequest) servletRequest).getSession();
        User user = (User) session.getAttribute(SessionKeys.USER);
        if (WHITELIST_PATH.contains(path.toLowerCase()) || user != null) {
            session.setMaxInactiveInterval(session.getMaxInactiveInterval() + 60 * 60);
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }
        if (path.startsWith("/api/")) path = "/";

        String redirectUrl = PathFactory.buildPath("/auth/login/?redirect=" + path);
        if (!path.equalsIgnoreCase("/") && queryString != null && !queryString.isEmpty()) {
            redirectUrl += '?' + queryString;
        }

        ((HttpServletResponse) servletResponse).sendRedirect(redirectUrl);
    }

}
