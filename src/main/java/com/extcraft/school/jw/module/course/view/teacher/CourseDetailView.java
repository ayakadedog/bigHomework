package com.extcraft.school.jw.module.course.view.teacher;

import com.extcraft.school.jw.entity.course.CourseUnitDto;
import com.extcraft.school.jw.module.base.AbstractBaseView;
import org.thymeleaf.context.WebContext;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;


@WebServlet("/course/teacher/courseDetail")
public class CourseDetailView extends AbstractBaseView {

    @Override
    protected void handle(WebContext context, HttpServletRequest request) {

        CourseUnitDto courseUnitDto = (CourseUnitDto) request.getAttribute("courseUnitDto");

        context.setVariable("courseUnitDto", courseUnitDto);

    }

}
