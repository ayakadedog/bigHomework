package com.extcraft.school.jw.module.course.view.student;

import com.extcraft.school.jw.entity.course.CourseMaterial;
import com.extcraft.school.jw.entity.course.CourseUnitDto;
import com.extcraft.school.jw.entity.course.CourseUnitStudentDto;
import com.extcraft.school.jw.module.base.AbstractBaseView;
import org.thymeleaf.context.WebContext;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import java.util.List;


@WebServlet("/course/student/courseDetail")
public class CourseDetailView extends AbstractBaseView {

    @Override
    protected void handle(WebContext context, HttpServletRequest request) {

        CourseUnitStudentDto courseUnitStudentDto = (CourseUnitStudentDto) request.getAttribute("courseUnitStudentDto");
        List<CourseMaterial> courseMaterialList  = (List<CourseMaterial>)request.getAttribute("courseMaterialList");
        context.setVariable("courseUnitStudentDto", courseUnitStudentDto);
        context.setVariable("courseMaterialList", courseMaterialList);
    }

}
