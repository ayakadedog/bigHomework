package com.extcraft.school.jw.module.course.view.student;

import com.extcraft.school.jw.dao.course.CourseDao;
import com.extcraft.school.jw.dao.course.CourseUnitDao;
import com.extcraft.school.jw.dao.course.EnrollmentDao;
import com.extcraft.school.jw.dao.course.StudentCourseUnitDao;
import com.extcraft.school.jw.entity.auth.User;
import com.extcraft.school.jw.entity.course.Course;
import com.extcraft.school.jw.entity.course.Enrollment;
import com.extcraft.school.jw.entity.course.EnrollmentDto;
import com.extcraft.school.jw.module.base.AbstractBaseView;
import org.thymeleaf.context.WebContext;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@WebServlet("/course/student/courseList")
public class CourseListView extends AbstractBaseView {
    @Override
    protected void handle(WebContext context, HttpServletRequest request) {

        User user = (User) request.getSession().getAttribute("user");

        Long id = user.getId();

        List<EnrollmentDto> enrollmentDtos = EnrollmentDao.queryStudentCourse(id);

        for (int i=0;i<enrollmentDtos.size();i++){
            Double process = StudentCourseUnitDao.countLearn(enrollmentDtos.get(i).getCourseId().toString(), id.toString());
            Double count = CourseUnitDao.countUnit(enrollmentDtos.get(i).getCourseId().toString());
            if (count != 0)
                enrollmentDtos.get(i).setProcess(Integer.valueOf((int) ((process/count)*100)));
        }


        context.setVariable("enrollmentDtos", enrollmentDtos);

    }
}
