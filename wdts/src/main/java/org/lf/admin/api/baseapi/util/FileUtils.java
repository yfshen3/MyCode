package org.lf.admin.api.baseapi.util;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.lf.admin.api.baseapi.config.Constants;
import org.lf.admin.api.baseapi.core.ServiceException;
import org.lf.admin.api.baseapi.enums.DirectoryEnum;
import org.lf.admin.api.baseapi.enums.ExcelEnum;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FileUtils extends org.apache.commons.io.FileUtils {

    private static final String[] IMAGES_SUFFIXES = {"bmp", "jpg", "jpeg", "gif", "png"};

    private static final String[] DEFAULT_ALLOWED_EXTENSION = {
            // 图片
            "bmp", "gif", "jpg", "jpeg", "png",
            // word excel powerpoint
            "doc", "docx", "xls", "xlsx", "ppt", "pptx", "html", "htm", "txt",
            // 压缩文件
            "rar", "zip", "gz", "bz2",
            // pdf
            "pdf"};

    public static boolean assertAllowed(String filename) {
        if (!(filename != null && filename.trim().length() != 0)) {
            return false;
        }
        return ArrayUtils.contains(DEFAULT_ALLOWED_EXTENSION, FilenameUtils.getExtension(filename).toLowerCase());
    }

    /**
     * 是否是图片附件
     *
     * @param filename
     * @return
     */
    public static boolean isImage(String filename) {
        assertAllowed(filename);
        return ArrayUtils.contains(IMAGES_SUFFIXES, FilenameUtils.getExtension(filename).toLowerCase());
    }

    public static File getDataHome() {
        return getDataHome(null);
    }

    public static File getDataHome(DirectoryEnum directoryEnum) {
        String dataHome = Constants.dataHome;
        if (StringUtils.isBlank(dataHome))
            dataHome = System.getProperty("user.home") + File.separator + Constants.appName;
        File homeDir = new File(dataHome);
        if (!homeDir.exists() || !homeDir.isDirectory())
            homeDir.mkdirs();
        if (directoryEnum == null)
            return homeDir;
        File dir = new File(homeDir, directoryEnum.name());
        if (!dir.exists() || !dir.isDirectory())
            dir.mkdirs();
        return dir;
    }

    /**
     * 保存报告文件
     *
     * @param file          pdf 或者 json 文件
     * @param directoryEnum
     * @return
     */
    public static String saveFile(MultipartFile file, DirectoryEnum directoryEnum) {
        if (file == null)
            return null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String ymd = sdf.format(new Date());
        String filename = file.getOriginalFilename();
        String perfix = filename.substring(filename.lastIndexOf("."));
        File dataHome = FileUtils.getDataHome(directoryEnum);
        File descFile = new File(dataHome, ymd + File.separator + System.currentTimeMillis() + perfix);
        if (!descFile.getParentFile().exists())
            descFile.getParentFile().mkdirs();
        try {
            file.transferTo(descFile);
        } catch (IllegalStateException | IOException e) {
            throw new ServiceException("文件上传失败!");
        }
        return directoryEnum.name() + File.separator + ymd + File.separator + descFile.getName();
    }
    public static File getTempExcel(ExcelEnum fileEnum) throws IOException {
        String uri = DirectoryEnum.tmp.name() + "/" + System.currentTimeMillis() + ".xlsx";
        File destFile = new File(getDataHome(), uri);
        String name="/static/assets/excel/" + fileEnum.getFileName()+".xlsx";
        try (InputStream resourceAsStream = FileUtils.class.getResourceAsStream("/static/assets/excel/" + fileEnum.getFileName()+".xlsx")) {
            FileUtils.copyToFile(resourceAsStream, destFile);
        }
        return destFile;
    }
}
