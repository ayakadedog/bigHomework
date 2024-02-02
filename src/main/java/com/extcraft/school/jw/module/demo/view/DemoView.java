package com.extcraft.school.jw.module.demo.view;

import com.extcraft.school.jw.module.base.AbstractBaseView;
import org.thymeleaf.context.WebContext;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;

/**
 * ThymeleafDemo
 * TO-EDIT-FULL-DESCRIPTION
 *
 * @author SmallL-U
 * @version 1.0 2023/12/18
 */
@WebServlet("/demo/")
public class DemoView extends AbstractBaseView {

    @Override
    protected void handle(WebContext context, HttpServletRequest request) {
        context.setVariable("message", "Hello Thymeleaf!");
    }
}
