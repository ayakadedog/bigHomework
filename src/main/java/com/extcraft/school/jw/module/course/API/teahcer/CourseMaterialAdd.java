package com.extcraft.school.jw.module.course.API.teahcer;

import com.extcraft.school.jw.dao.course.CourseMaterialDao;
import com.extcraft.school.jw.dao.course.CourseUnitDao;
import com.extcraft.school.jw.entity.course.CourseMaterial;
import com.extcraft.school.jw.entity.course.CourseUnit;

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
@WebServlet("/course/teacher/courseMaterialAdd/*")
public class CourseMaterialAdd extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        // 从请求URL中提取课程ID
        String pathInfo = request.getPathInfo();
        String[] pathParts = pathInfo.split("/");

        if (pathParts.length > 1) {
            String courseId = pathParts[1];

            String unitName = request.getParameter("unitName");

            String url =  getUrl(request,response,courseId);
            String type = getType(request,response,courseId);
            int i = CourseMaterialDao.addCourseMaterial(courseId,type,url);

//            response.sendRedirect(request.getContextPath() + "/course/teacher/courseDetail/" + courseId);

        }
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
        return filePath;
    }

    private String getType(HttpServletRequest request, HttpServletResponse response,String courseId) throws ServletException, IOException {
// 获取上传的文件部分
        Part filePart = request.getPart("file");

        System.out.println("filePart: " + filePart);

// 获取文件名
        String originalFilename = getSubmittedFileName(filePart);

// 直接获取文件后缀
        String fileExtension = "";
        int lastDotIndex = originalFilename.lastIndexOf(".");
        if (lastDotIndex > 0) {
            fileExtension = originalFilename.substring(lastDotIndex + 1);
        }

        return fileExtension;

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
