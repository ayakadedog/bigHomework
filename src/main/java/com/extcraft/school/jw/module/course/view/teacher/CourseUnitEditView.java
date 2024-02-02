package com.extcraft.school.jw.module.course.view.teacher;

import com.extcraft.school.jw.entity.course.Course;
import com.extcraft.school.jw.entity.course.CourseUnit;
import com.extcraft.school.jw.module.base.AbstractBaseView;
import org.thymeleaf.context.WebContext;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;

@WebServlet("/course/teacher/courseUnitEdit")
public class CourseUnitEditView extends AbstractBaseView {

    @Override
    protected void handle(WebContext context, HttpServletRequest request) {

        CourseUnit courseUnit = (CourseUnit) request.getAttribute("courseUnit");

        context.setVariable("id",courseUnit.getId());

        context.setVariable("name", courseUnit.getName());

        context.setVariable("description",courseUnit.getMaterialUrl());

    }
}
