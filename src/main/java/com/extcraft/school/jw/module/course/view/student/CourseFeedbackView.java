package com.extcraft.school.jw.module.course.view.student;

import com.extcraft.school.jw.module.base.AbstractBaseView;
import org.thymeleaf.context.WebContext;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;

@WebServlet("/course/student/courseFeedback")
public class CourseFeedbackView extends AbstractBaseView {


    @Override
    protected void handle(WebContext context, HttpServletRequest request) {

        String feedback = (String) request.getAttribute("feedback");

        context.setVariable("feedback",feedback);

    }
}
