package com.extcraft.school.jw.module.auth;

import com.extcraft.school.jw.dao.auth.UserDao;
import com.extcraft.school.jw.entity.auth.User;
import com.extcraft.school.jw.util.PathFactory;
import com.google.common.base.Strings;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * TO-EDIT-FULL-DESCRIPTION
 *
 * @author SmallL-U
 * @version 1.0 2023/12/18
 */
@WebServlet("/api/login")
public class LoginAPI extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String redirect = req.getParameter("redirect");
        // bad request
        if (username == null || password == null) return;
        User user = UserDao.login(username, password);
        if (user == null) {
            resp.sendRedirect(PathFactory.buildPath("/auth/login/?status=error&msg={0}", "用户名或密码错误"));
            return;
        }
        req.getSession().setAttribute("user", user);
        req.getSession().setMaxInactiveInterval(60 * 60);
        if (!Strings.isNullOrEmpty(redirect)) resp.sendRedirect(PathFactory.buildPath(redirect));
        else resp.sendRedirect(PathFactory.buildPath("/"));
    }
}
