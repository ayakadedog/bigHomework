package com.extcraft.school.jw.module.course.API;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

@WebServlet("/api/download/*")
public class DownloadAPI extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String filePath = request.getParameter("filePath");

        String fileExtension = "";
        int lastDotIndex = filePath.lastIndexOf(".");
        if (lastDotIndex > 0) {
            fileExtension = filePath.substring(lastDotIndex + 1);
        }

        response.setHeader("Content-Disposition", "attachment; filename=file." + fileExtension);
        try (InputStream inputStream = new FileInputStream(filePath);
             // 获取输出流
             OutputStream outputStream = response.getOutputStream()) {

            // 将文件内容写入输出流
            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
