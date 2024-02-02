package com.extcraft.school.jw.module.demo;

import com.extcraft.school.jw.util.PathFactory;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * API Demo
 * TO-EDIT-FULL-DESCRIPTION
 *
 * @author SmallL-U
 * @version 1.0 2023/12/18
 */
@WebServlet("/api/demo")
public class DemoAPI extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        // redirect to /demo/
        resp.sendRedirect(PathFactory.buildPath("/demo/"));
    }
}
