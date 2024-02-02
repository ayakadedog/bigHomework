package com.extcraft.school.jw.util;

import lombok.SneakyThrows;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.MessageFormat;

/**
 * 路径工厂
 * TO-EDIT-FULL-DESCRIPTION
 *
 * @author SmallL-U
 * @version 1.0 2023/12/18
 */
public class PathFactory {
    // application context path
    public static String CONTEXT_PATH;

    @SneakyThrows
    public static String buildPath(String pathTemplate, String... params) {
        // Encode each parameter using URL encoding
        for (int i = 0; i < params.length; i++) {
            params[i] = URLEncoder.encode(params[i], StandardCharsets.UTF_8.toString());
        }

        // Format the path with the encoded parameters
        String formattedPath = MessageFormat.format(pathTemplate, (Object[]) params);

        StringBuilder builder = new StringBuilder(CONTEXT_PATH);
        if (!formattedPath.isEmpty() && formattedPath.length() != 1) formattedPath = formattedPath.substring(1);
        String[] pathSegments = formattedPath.split("/");
        for (int i = 0; i < pathSegments.length; i++) {
            if (!pathSegments[i].isEmpty()) {
                builder.append("/").append(pathSegments[i]);
            }
            // Append the trailing slash for all segments except the last
            else if (i < pathSegments.length - 1) {
                builder.append("/");
            }
        }

        // Append trailing slash if the original pathTemplate ended with one
        if (pathTemplate.endsWith("/")) {
            builder.append("/");
        }

        return builder.toString();
    }


}
