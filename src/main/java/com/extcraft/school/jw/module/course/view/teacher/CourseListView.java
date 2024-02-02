package com.extcraft.school.jw.module.course.view.teacher;

import com.extcraft.school.jw.dao.course.CourseDao;
import com.extcraft.school.jw.entity.auth.User;
import com.extcraft.school.jw.entity.course.Course;
import com.extcraft.school.jw.module.base.AbstractBaseView;
import org.thymeleaf.context.WebContext;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@WebServlet("/course/teacher/courseList")
public class CourseListView extends AbstractBaseView {

    @Override
    protected void handle(WebContext context, HttpServletRequest request) {

        User user = (User) request.getSession().getAttribute("user");

        Long id = user.getId();

        List<Course> courses = CourseDao.queryTeacherCourse(id);

        context.setVariable("courses", courses);

    }

}
