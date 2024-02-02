package com.extcraft.school.jw.module.course.view.student;

import com.extcraft.school.jw.dao.course.CourseDao;
import com.extcraft.school.jw.entity.auth.User;
import com.extcraft.school.jw.entity.course.Course;
import com.extcraft.school.jw.module.base.AbstractBaseView;
import org.thymeleaf.context.WebContext;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/course/student/courseAdd")
public class CourseAddView extends AbstractBaseView {


    @Override
    protected void handle(WebContext context, HttpServletRequest request) {
        Course course = new Course();
        course.setName(request.getParameter("courseName"));

        User user = (User) request.getSession().getAttribute("user");
        course.setTeacherId(user.getId());
        course.setDescription(request.getParameter("courseDescription"));

        List<Course> list = CourseDao.queryAllCourse(user.getId());

        context.setVariable("courses", list);
    }
}
