package com.extcraft.school.jw.module.auth;

import com.extcraft.school.jw.dao.auth.UserDao;
import com.extcraft.school.jw.entity.auth.User;
import com.extcraft.school.jw.util.PathFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * TO-EDIT-FULL-DESCRIPTION
 *
 * @author SmallL-U
 * @version 1.0 2023/12/19
 */
@WebServlet("/api/register")
public class RegisterAPI extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String name = req.getParameter("name");
        String email = req.getParameter("email");
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String className = req.getParameter("className");
        String subject = req.getParameter("subject");
        String role = req.getParameter("role");
        // bad request
        if (name == null || email == null || username == null || password == null || className == null || subject == null || role == null) return;
        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setUserName(username);
        user.setUserPassword(password);
        user.setClassName(className);
        user.setSubject(subject);
        user.setRole(role);
        user = UserDao.addUser(user);
        if (user == null) {
            resp.sendRedirect(PathFactory.buildPath("/auth/register/?msg={0}", "注册失败"));
            return;
        }
        req.getSession().setAttribute("user", user);
        resp.sendRedirect(PathFactory.buildPath("/"));
    }
}
