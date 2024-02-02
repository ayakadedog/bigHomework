package com.extcraft.school.jw.module.course.view.teacher;

import com.extcraft.school.jw.entity.course.Course;
import com.extcraft.school.jw.entity.course.CourseUnitDto;
import com.extcraft.school.jw.module.base.AbstractBaseView;
import org.thymeleaf.context.WebContext;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;

@WebServlet("/course/teacher/courseEdit")
public class CourseEditView extends AbstractBaseView {

    @Override
    protected void handle(WebContext context, HttpServletRequest request) {

        Course course = (Course) request.getAttribute("courseInfo");

        context.setVariable("id",course.getId());

        context.setVariable("name", course.getName());

        context.setVariable("description",course.getDescription());

    }
}
