package com.cav.clairvoyance.utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;


public class FileUtil {
    public static void makeDir(String pathName) {
        File directories = new File(pathName);
        if(directories.exists()) {
            System.out.println("文件上传根目录已存在");
        } else {
            if(directories.mkdirs()) {
                System.out.println("创建多级目录成功");
            } else {
                System.out.println("创建多级目录失败");
            }
        }
    }

    public static void writeStringToFile(String content, String filePath) {
        FileWriter fwriter = null;
        try {
            // true表示不覆盖原来的内容，而是加到文件的后面。若要覆盖原来的内容，直接省略这个参数就好
            fwriter = new FileWriter(filePath);
            fwriter.write(content);
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            try {
                fwriter.flush();
                fwriter.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    /**
     * 递归删除文件或子文件夹
     * @param path 路径
     */
    public static void deleteFile(String path) {
        File file = new File(path);
        if(!file.exists()) {
            return;
        }

        if(file.isFile()) {
            file.delete();
            return;
        }

        if(file.isDirectory()) {
            for(File f : file.listFiles()) {
                deleteFile(f.getAbsolutePath());
            }
            file.delete();
        } else{
            file.delete();
        }
    }

    public static void getFilesBySuffix(File dir, String suffix, Collection<File> collection) {
        File[] files = dir.listFiles();
        for (File subFile:files) {
            if (subFile.isDirectory())
                getFilesBySuffix(subFile,suffix,collection);
            else if (subFile.getName().toLowerCase().endsWith(suffix)) {
                collection.add(subFile);
            }
        }
    }
}
