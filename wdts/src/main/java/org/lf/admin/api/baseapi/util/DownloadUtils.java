package org.lf.admin.api.baseapi.util;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;

public abstract class DownloadUtils {

    public static void download(HttpServletRequest request, HttpServletResponse response, File file) throws IOException {
        download(request, response, file, "");
    }

    public static void download(HttpServletRequest request, HttpServletResponse response, String filePath) throws IOException {
        download(request, response, filePath, "");
    }

    public static void download(HttpServletRequest request, HttpServletResponse response, byte[] bytes) throws IOException {
        download(request, response, bytes, "");
    }

    public static void download(HttpServletRequest request, HttpServletResponse response, String filePath, String displayName) throws IOException {
        if (StringUtils.isBlank(filePath)) {
            response.setContentType("text/html;charset=utf-8");
            response.getWriter().write("您下载的文件不存在！");
            return;
        }
        File file = new File(filePath);
        if (!file.exists() || !file.canRead()) {
            response.setContentType("text/html;charset=utf-8");
            response.getWriter().write("您下载的文件不存在！");
            return;
        }
        if (StringUtils.isBlank(displayName))
            displayName = file.getName();
        displayName = displayName.replace(" ", "_");

        response.setContentLength((int) file.length());
        put(request, response, displayName, new FileInputStream(file));
    }

    public static void download(HttpServletRequest request, HttpServletResponse response, File file, String displayName) throws IOException {
        if (!file.exists() || !file.canRead()) {
            response.setContentType("text/html;charset=utf-8");
            response.getWriter().write("The file you downloaded does not exist!");
            return;
        }

        if (StringUtils.isBlank(displayName))
            displayName = file.getName();
        displayName = displayName.replace(" ", "_");
        response.setContentLength((int) file.length());
        put(request, response, displayName, new FileInputStream(file));
    }

    public static void download(HttpServletRequest request, HttpServletResponse response, byte[] bytes, String displayName) throws IOException {
        if (ArrayUtils.isEmpty(bytes)) {
            response.setContentType("text/html;charset=utf-8");
            response.setCharacterEncoding("utf-8");
            response.getWriter().write("The file you downloaded does not exist!");
            return;
        }
        if (StringUtils.isNotBlank(displayName))
            displayName = displayName.replace(" ", "_");

        response.setContentLength(bytes.length);

        put(request, response, displayName, new ByteArrayInputStream(bytes));
    }

    private static void put(HttpServletRequest request, HttpServletResponse response, String displayName, InputStream inputStream) throws IOException {
        response.reset();
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "must-revalidate, no-transform");
        response.setDateHeader("Expires", 0L);
        response.setContentType("application/x-download");

        String userAgent = request.getHeader("User-Agent");
        boolean isIE = (userAgent != null) && (userAgent.toLowerCase().contains("msie"));
        if (isIE) {
            displayName = URLEncoder.encode(displayName, "UTF-8");
            response.setHeader("Content-Disposition", "attachment;filename=\"" + displayName + "\"");
        } else {
            displayName = new String(displayName.getBytes("UTF-8"), "ISO8859-1");
            response.setHeader("Content-Disposition", "attachment;filename=" + displayName);
        }

        try (BufferedInputStream is = new BufferedInputStream(inputStream);
             OutputStream os = response.getOutputStream()) {
            IOUtils.copy(is, os);
        }
    }
}
