package com.extcraft.school.jw.module.course.view.teacher;

import com.extcraft.school.jw.dao.course.CourseDao;
import com.extcraft.school.jw.entity.course.Course;
import com.extcraft.school.jw.entity.course.CourseUnitDto;
import com.extcraft.school.jw.module.base.AbstractBaseView;
import org.thymeleaf.context.WebContext;

import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;

@WebServlet("/course/teacher/courseUnitAdd")
public class CourseUnitAddView extends AbstractBaseView {

    @Override
    protected void handle(WebContext context, HttpServletRequest request) {

        String courseId = request.getAttribute("courseId").toString();

        context.setVariable("courseId", courseId);
    }



}