package com.katana.tenement.framework.util;

import com.katana.tenement.framework.exception.IllegalParameterException;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.regex.Pattern;


/**
 * @author zuzu
 * @date 2019/5/10
 */

public class FileUploadUtils {
    /**
     * 图片上传
     *
     * @param file
     * @param filePath
     * @return
     */
    public static String upload(MultipartFile file, String filePath, Integer fileType) {
        if (file.isEmpty()) {
            throw new IllegalParameterException(IllegalParameterException.CodeOption.INVALID_FILE_SIZE);
        }
        String fileName = file.getOriginalFilename();
        // 获取文件的后缀名
        String suffixName = fileName.substring(fileName.lastIndexOf("."));
        if ((fileType == 0 && !ImageUtils.isImage(file.getContentType(), suffixName.replace(".", ""))) || (fileType == 1 && !isVideo(file.getOriginalFilename())) || (fileType == -1 && !isMp3(file.getOriginalFilename()))) {
            throw new IllegalParameterException(IllegalParameterException.CodeOption.INVALID_FILE_EXTENSION);
        }
        fileName = UNIDGenerateUtils.getUnid() + suffixName;
        File dest = new File(filePath + File.separator + fileName);
        // 检测是否存在目录
        if (!dest.getParentFile().exists()) {
            dest.getParentFile().mkdirs();
        }
        try {
            file.transferTo(dest);
            return fileName;
        } catch (IllegalStateException | IOException e) {
            throw new IllegalParameterException(IllegalParameterException.CodeOption.UPLOAD_FAIL);
        }
    }


    public static Boolean isVideo(String fileName) {
        Pattern p = null;
        try {
            String reg = "(mp4|flv|avi|rm|rmvb|wmv)";
            p = Pattern.compile(reg,Pattern.CASE_INSENSITIVE);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return p.matcher(fileName).find();
    }

    public static Boolean isMp3(String fileName) {
        Pattern p = null;
        try {
            String reg = "wav|midi|cda|mp3|wmv";
            p = Pattern.compile(reg,Pattern.CASE_INSENSITIVE);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return p.matcher(fileName).find();
    }
}
