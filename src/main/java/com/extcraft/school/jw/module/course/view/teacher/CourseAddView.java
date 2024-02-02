package com.extcraft.school.jw.module.course.view.teacher;

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

@WebServlet("/course/teacher/courseAdd")
public class CourseAddView extends AbstractBaseView {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        Course course = new Course();
        course.setName(request.getParameter("courseName"));
//        course.setTeacherId((Long) request.getSession().getAttribute("id"));
        //        course.setTeacherId(101L);

        User user = (User) request.getSession().getAttribute("user");
//        course.setTeacherId(101L);
        course.setTeacherId(user.getId());
        course.setDescription(request.getParameter("courseDescription"));

        int i = CourseDao.addCourse(course);

        response.sendRedirect(request.getContextPath() + "/course/teacher/courseList"); // 这里设置了跳转的路径
    }

}
