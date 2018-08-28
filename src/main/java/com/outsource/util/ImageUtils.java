package com.outsource.util;

import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.UUID;

/**
 * @author chuanchen
 */
public class ImageUtils {
    private ImageUtils() {
    }

    public static String getFilePath(String dir, MultipartFile file) {
        if (StringUtils.isEmpty(dir)) {
            return null;
        }
        String originFileName = file.getOriginalFilename();
        String suffix = originFileName.substring(originFileName.lastIndexOf("."), originFileName.length());
        String fileName = System.currentTimeMillis() + suffix;
        return dir + fileName;
    }

    public static String generateFileName(String fileSuffix){
        return UUIDUtils.generateUUID() + fileSuffix;
    }

    public static String saveImg(MultipartFile multipartFile,String dirPath, String filePath) {
        File file = new File(dirPath);
        if (!file.exists()) {
            file.mkdirs();
        }
        String fileName = dirPath + filePath;
        FileInputStream fileInputStream;
        BufferedOutputStream bos = null;
        try {
            fileInputStream = (FileInputStream) multipartFile.getInputStream();
            bos = new BufferedOutputStream(new FileOutputStream(fileName));
            byte[] bs = new byte[1024];
            int len;
            while ((len = fileInputStream.read(bs)) != -1) {
                bos.write(bs, 0, len);
            }
            bos.flush();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }finally {
            try {
                bos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return fileName;
    }

    public static byte[] getImage(String filePath){
        filePath += ".png";
        File file = new File(filePath);
        if(!file.exists()){
            return null;
        }
        byte[] fileByte = new byte[(int) file.length()];
        InputStream is = null;
        try {
            is = new FileInputStream(file);
            is.read(fileByte);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        }finally {
            if(is != null){
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return fileByte;
    }
}
