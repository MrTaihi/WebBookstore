package com.cy.bookstore.util;

import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 文件处理工具类.
 *
 * @author Taihi
 * @since 2022-10-15 15:08:26
 */
public class FileHandlerUtil {

    private static final int MAX_PATH_LEN = 250;
    private static final int MAX_HEX_LEN = 32;
    private static final String FILE_TYPE_ALLOW_SUFFIX = ".*\\.(?i)jpg$|" +
            ".*\\.(?i)jpeg$|" +
            ".*\\.(?i)png$|" +
            ".*\\.(?i)pdf$|" +
            ".*\\.(?i)doc$|" +
            ".*\\.(?i)xls$|" +
            ".*\\.(?i)ppt$|" +
            ".*\\.(?i)docx$|" +
            ".*\\.(?i)xlsx$|" +
            ".*\\.(?i)pptx$|" +
            ".*\\.(?i)zip$|" +
            ".*\\.(?i)rar$";
    private static final Map<String, String> FILE_TYPE_ALLOW_MAP = new HashMap<>(16);

    static { // 这些有什么用？
        FILE_TYPE_ALLOW_MAP.put("ffd8ff", "jpg");
        FILE_TYPE_ALLOW_MAP.put("89504e47", "png");
        FILE_TYPE_ALLOW_MAP.put("255044462d312e", "pdf");
        FILE_TYPE_ALLOW_MAP.put("d0cf11e0", "office");
        FILE_TYPE_ALLOW_MAP.put("504b0304", "office_x");
        FILE_TYPE_ALLOW_MAP.put("526172211a07", "rar");
    }

    /**
     * 判断是否允许的文件类型.
     *
     * @param file 文件
     * @return the boolean
     * @throws IOException the io exception
     */
    public static boolean isAllowFile(MultipartFile file) throws IOException {
        String fileName = file.getOriginalFilename();
        if (!StringUtils.hasText(fileName) || !fileName.matches(FILE_TYPE_ALLOW_SUFFIX)) {
            return false;
        }
        String fileHeader = bytesToHexString(file.getBytes());
        for (String header : FILE_TYPE_ALLOW_MAP.keySet()) {
            if (fileHeader.startsWith(header)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断是否图片.
     *
     * @param file 文件
     * @return the boolean
     * @throws IOException the io exception
     */
    public static boolean isImg(MultipartFile file) throws IOException {
        return isAllowFile(file) && StringUtils.startsWithIgnoreCase(file.getContentType(), "image");
    }

    /**
     * 获取文件扩展名后缀.
     *
     * @param file 文件
     * @return 扩展名
     */
    public static String getSuffix(MultipartFile file) {
        String fileName = file.getOriginalFilename();
        if (!StringUtils.hasText(fileName)) {
            return "";
        }
        int index = fileName.lastIndexOf('.');
        if (index <= 0) {
            return "";
        }
        return fileName.substring(index + 1);
    }

    /**
     * 递归删除文件.
     *
     * @param file 文件夹
     * @return 删除结果
     */
    public static boolean deleteRecur(File file) {
        if (file != null && file.exists()) {
            if (file.isDirectory()) {
                File[] files = file.listFiles();
                if (files != null && files.length > 0) {
                    for (File f : files) {
                        deleteRecur(f);
                    }
                }
            }
            return file.delete();
        }
        return false;
    }

    /**
     * 保存文件.
     *
     * @param multipartFile   待保存文件
     * @param publicDirectory 共享文件夹
     * @param fileDirectory   文件夹相对路径
     * @param fileName        文件名
     * @param isClean         是否清空文件夹
     * @return 文件路径
     * @throws IOException the io exception
     */
    public static String transfer(MultipartFile multipartFile, String publicDirectory, String fileDirectory, String fileName, boolean isClean) throws IOException {
        File file = new File(publicDirectory + fileDirectory);
        // 绝对路径加相对路径
        if (isClean) {
            deleteRecur(file);
        }
        if (!file.exists() || !file.isDirectory()) { //不存在或者不是文件夹
            file.mkdirs();
        }
        if (fileDirectory.length() + fileName.length() > MAX_PATH_LEN) { // 按照规则改名
            fileName = fileName.substring(fileDirectory.length() + fileName.length() - MAX_PATH_LEN);
        }
        file = new File(publicDirectory + fileDirectory, fileName);
        multipartFile.transferTo(file);  // 生成该文件
        return  fileDirectory + "/" + fileName;
    }

    /**
     * 使用指定文件名保存文件.
     *
     * @param multipartFile   待保存文件
     * @param publicDirectory 共享文件夹
     * @param fileDirectory   文件夹相对路径
     * @param fileName        文件名
     * @return 文件路径
     * @throws IOException the io exception
     */
    public static String transfer(MultipartFile multipartFile, String publicDirectory, String fileDirectory, String fileName) throws IOException {
        return transfer(multipartFile, publicDirectory, fileDirectory, fileName + "." + getSuffix(multipartFile), true);
    }

    /**
     * 使用原文件名保存文件.
     *
     * @param multipartFile   待保存文件
     * @param publicDirectory 共享文件夹
     * @param fileDirectory   文件夹相对路径
     * @return 文件路径
     * @throws IOException the io exception
     */
    public static String transfer(MultipartFile multipartFile, String publicDirectory, String fileDirectory) throws IOException {
        return transfer(multipartFile, publicDirectory, fileDirectory, multipartFile.getOriginalFilename(), true);
    }

    /**
     * 字节数组转换十六进制字符串.
     *
     * @param bytes 字节数组
     * @return 十六进制字符串
     */
    private static String bytesToHexString(byte[] bytes) {
        StringBuilder stringBuilder = new StringBuilder(MAX_HEX_LEN * 2);
        String hv;
        for (int i = 0; i < MAX_HEX_LEN; i++) {
            hv = Integer.toHexString(0xFF & bytes[i]);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv.toLowerCase());
        }
        return stringBuilder.toString();
    }

}
