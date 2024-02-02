package com.extcraft.school.jw;

import com.extcraft.school.jw.annotation.ConfigFile;
import com.extcraft.school.jw.annotation.Dao;
import com.extcraft.school.jw.annotation.DepInit;
import com.extcraft.school.jw.util.DBWrapper;
import com.extcraft.school.jw.util.PathFactory;
import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.reflections.Reflections;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.Set;

/**
 * 项目初始化器
 * 用于初始化项目
 *
 * @author SmallL-U
 * @version 1.0 2023/12/9
 */
@WebListener
public class ProjectInitializer implements ServletContextListener {

    public static TemplateEngine TEMPLATE_ENGINE;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        // base operation
        processServlet(sce);
        processConfig();
        processView(sce.getServletContext());

        // dep init operation
        processDep();

        // func init operation
        processDao();
    }

    private void processDao() {
        Class<Dao> annotation = Dao.class;
        Reflections reflections = new Reflections("com.extcraft.school.jw.dao");
        Set<Class<?>> daoClazzList = reflections.getTypesAnnotatedWith(annotation);
        for (Class<?> aClass : daoClazzList) {
            Method initMethod;
            try {
                initMethod = aClass.getMethod("init");
                initMethod.invoke(null);
            } catch (NoSuchMethodException e) {
                throw new RuntimeException("Dao类必须有一个名为init的静态方法");
            } catch (InvocationTargetException | IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void processDep() {
        Class<DepInit> annotation = DepInit.class;
        Reflections reflections = new Reflections("com.extcraft.school.jw");
        Set<Class<?>> depClazzList = reflections.getTypesAnnotatedWith(annotation);
        for (Class<?> aClass : depClazzList) {
            Method initMethod;
            try {
                initMethod = aClass.getMethod("init");
                initMethod.invoke(null);
            } catch (NoSuchMethodException e) {
                throw new RuntimeException("DepInit类必须有一个名为init的静态方法");
            } catch (InvocationTargetException | IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void processServlet(ServletContextEvent sce) {
        PathFactory.CONTEXT_PATH = sce.getServletContext().getContextPath();
    }

    private void processConfig() {
        // json transformer
        ObjectMapper objectMapper = new ObjectMapper();

        // scan config file class
        Class<ConfigFile> annotation = ConfigFile.class;
        Reflections reflections = new Reflections("com.extcraft.school.jw.config");
        Set<Class<?>> configClazzList = reflections.getTypesAnnotatedWith(annotation);
        for (Class<?> aClass : configClazzList) {
            // get file name
            String jsonFile = aClass.getAnnotation(annotation).value() + ".json";
            // get and check resource exist
            URL resource = aClass.getResource("/" + jsonFile);
            if (resource == null) throw new RuntimeException("配置文件不存在: " + jsonFile);
            // check instance field
            Field instanceField;
            try {
                instanceField = aClass.getField("INSTANCE");
                // process field
                Object o = objectMapper.readValue(resource, aClass);
                instanceField.set(null, o);
            } catch (NoSuchFieldException e) {
                // if not exist, throw exception
                throw new RuntimeException("配置文件类必须有一个名为INSTANCE的静态字段");
            } catch (IOException | IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void processView(ServletContext context) {
        TEMPLATE_ENGINE = new TemplateEngine();
        ServletContextTemplateResolver resolver = new ServletContextTemplateResolver(context);
        resolver.setPrefix("/WEB-INF/templates/");
        resolver.setSuffix(".html");
        resolver.setCharacterEncoding("utf-8");
        resolver.setTemplateMode(TemplateMode.HTML);
        resolver.setCacheable(true);
        TEMPLATE_ENGINE.setTemplateResolver(resolver);
    }

}