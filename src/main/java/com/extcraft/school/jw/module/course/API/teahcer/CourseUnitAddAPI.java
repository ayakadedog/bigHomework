package com.extcraft.school.jw.module.course.API.teahcer;

import com.extcraft.school.jw.dao.course.CourseUnitDao;
import com.extcraft.school.jw.entity.course.CourseUnit;
import com.extcraft.school.jw.entity.course.CourseUnitDto;
import com.extcraft.school.jw.util.DBWrapper;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.UUID;


@MultipartConfig
@WebServlet("/course/teacher/courseUnitAdd/*")
public class CourseUnitAddAPI extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // 从请求URL中提取课程ID
        String pathInfo = request.getPathInfo();
        String[] pathParts = pathInfo.split("/");

        if (pathParts.length > 1) {
            String courseId = pathParts[1];

            if (courseId != null) {
                // 将课程信息设置为请求属性
                request.setAttribute("courseId", courseId);
                request.getSession().setAttribute("courseId", courseId);
                RequestDispatcher dispatcher = request.getRequestDispatcher("/course/teacher/courseUnitAdd");
                dispatcher.forward(request, response);
            } else {
                // 处理未找到课程的情况
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Course not found");
            }
        } else {
            // 处理未提供课程ID的情况
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Course ID not specified");
        }
    }

    // 从数据库中获取课程详细信息的方法
    private CourseUnitDto getCourseUnitDto(String courseId) {

        return CourseUnitDao.queryCourseUnits(courseId);
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
//        String courseId = request.getAttribute("courseId").toString();
        String courseId = "";
        String pathInfo = request.getPathInfo();
        String[] pathParts = pathInfo.split("/");

        if (pathParts.length > 1) {
            courseId = pathParts[1];
        }

        String unitName = request.getParameter("unitName");
        String description =  request.getParameter("description");
//        String url = getUrl(request,response,courseId);

        int i = CourseUnitDao.addCourseUnit(new CourseUnit(Long.parseLong(courseId), unitName,description));

        response.sendRedirect("/course/teacher/courseDetail/" + courseId);

    }

    private String getUrl(HttpServletRequest request, HttpServletResponse response,String courseId) throws ServletException, IOException {
        // 获取上传的文件部分
        Part filePart = request.getPart("file");

        System.out.println("filePart: " + filePart);

        // 获取文件名
        String originalFilename = getSubmittedFileName(filePart);
        // 原始文件名
        String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));

        // 使用UUID重新生成文件名，防止文件名称重复造成文件覆盖
        String fileName = UUID.randomUUID().toString() + suffix;

        // 指定保存文件的目录，这里保存在web应用的根目录下的uploads文件夹
        String uploadDir = "E:\\javaWeb课程设计\\picture\\";
        // 确保目录存在，如果不存在则创建
        File dir = new File(uploadDir);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        String filePath = uploadDir + fileName;
        // 将文件保存到本地
        try (InputStream fileContent = filePart.getInputStream()) {
            Files.copy(fileContent, new File(filePath).toPath(), StandardCopyOption.REPLACE_EXISTING);
            // 重定向到成功页面或返回表单页面
            response.sendRedirect(request.getContextPath() + "/course/teacher/courseDetail/" + courseId);
        } catch (Exception e) {
            // 处理异常
            response.getWriter().println("文件上传失败: " + e.getMessage());
        }

        // 构建保存文件的完整路径
        return uploadDir + fileName;
    }

    private String getSubmittedFileName(Part part) {
        for (String content : part.getHeader("content-disposition").split(";")) {
            if (content.trim().startsWith("filename")) {
                return content.substring(content.indexOf('=') + 1).trim().replace("\"", "");
            }
        }
        return null;
    }

}

