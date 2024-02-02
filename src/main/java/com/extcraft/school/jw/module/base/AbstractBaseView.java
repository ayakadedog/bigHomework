package com.extcraft.school.jw.module.base;

import com.extcraft.school.jw.ProjectInitializer;
import org.thymeleaf.context.WebContext;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 抽象基础视图
 * TO-EDIT-FULL-DESCRIPTION
 *
 * @author SmallL-U
 * @version 1.0 2023/12/18
 */
public abstract class AbstractBaseView extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        WebContext context = new WebContext(req, resp, req.getServletContext());
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html;charset=UTF-8");
        if (intercept(req, resp)) return;
        handle(context, req);
        if (intercept(req, resp, context)) return;
        ProjectInitializer.TEMPLATE_ENGINE.process(getLogicViewName(), context, resp.getWriter());
    }

    /**
     * 拦截器 默认不拦截
     * 用于无侵入修改请求逻辑
     *
     * @param request  请求
     * @param response 响应
     * @return 是否拦截
     */
    protected boolean intercept(HttpServletRequest request, HttpServletResponse response) {
        return false;
    }

    /**
     * 上下文拦截器 默认不拦截
     * 用于无侵入判断权限
     */
    protected boolean intercept(HttpServletRequest request, HttpServletResponse response, WebContext context) {
        return false;
    }

    /**
     * 处理请求 默认不处理
     * 用于无侵入处理模板上下文
     *
     * @param context 上下文
     * @param request 请求
     */
    protected void handle(WebContext context, HttpServletRequest request) {
    }

    /**
     * 获取逻辑视图名称
     * 逻辑视图名称是指相对于模板文件夹的路径
     *
     * @return 逻辑视图名称
     */
    private String getLogicViewName() {
        WebServlet annotation = this.getClass().getAnnotation(WebServlet.class);
        if (annotation == null) throw new RuntimeException("视图类必须有WebServlet注解");
        String[] value = annotation.value();
        if (value.length == 0) throw new RuntimeException("WebServlet注解必须有value属性");
        String path = value[0];
        if (path.endsWith("/")) path = path + "index";
        if (path.startsWith("/")) path = path.substring(1);
        return path;
    }

}
