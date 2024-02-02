package com.extcraft.school.jw.module.progress.view;

import com.extcraft.school.jw.dao.course.CourseUnitDao;
import com.extcraft.school.jw.dao.course.StudentCourseUnitDao;
import com.extcraft.school.jw.entity.course.StudentDto;
import com.extcraft.school.jw.module.base.AbstractBaseView;
import org.thymeleaf.context.WebContext;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@WebServlet("/progress/studentList")
public class StudentProgressView extends AbstractBaseView {

    @Override
    protected void handle(WebContext context, HttpServletRequest request) {

        List<StudentDto> users = (List<StudentDto>) request.getAttribute("userDtos");

        for (int i =0;i< users.size();i++){
            Long id = users.get(i).getStudentId();
            Double process = StudentCourseUnitDao.countLearn(users.get(i).getCourseId(), id.toString());
            Double count = CourseUnitDao.countUnit(users.get(i).getCourseId());
            if (count != 0)
                users.get(i).setProcess(Integer.valueOf((int) ((process/count)*100)));
           else
                users.get(i).setProcess(0);

        }
        context.setVariable("users",users);
    }
}
