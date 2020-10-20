package com.cav.clairvoyance.utils;

import java.io.*;
import java.util.Collection;


public class FileUtil {
    public static void makeDir(String pathName) {
        File directories = new File(pathName);
        if (!directories.exists()) directories.mkdirs();
    }

    public static void writeStringToFile(String content, String filePath) throws IOException {

        File file = new File(filePath);
        makeDir(file.getParent());
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "utf-8"));
        out.write(content);
        out.flush();
        out.close();
//        FileWriter fwriter = null;
//        try {
//            // true表示不覆盖原来的内容，而是加到文件的后面。若要覆盖原来的内容，直接省略这个参数就好
//            fwriter = new FileWriter(filePath);
//            fwriter.write(content);
//        } catch (IOException ex) {
//            ex.printStackTrace();
//        } finally {
//            try {
//                fwriter.flush();
//                fwriter.close();
//            } catch (IOException ex) {
//                ex.printStackTrace();
//            }
//        }
    }

    public static String readFile(String filePath) throws IOException {
        File file = new File(filePath);
        return readFile(file);
    }

    public static String readFile(File file) throws IOException {
        String str = "";
        if (!file.exists()) return str;
        FileInputStream in = new FileInputStream(file);
        // size  为字串的长度 ，这里一次性读完
        int size = in.available();
        byte[] buffer = new byte[size];
        in.read(buffer);
        in.close();
        str = new String(buffer, "utf-8");
        return str;
    }


    public static String getTById(String uuid, String dirPath, String extension) throws IOException {
        String str = "";
        File dir = getDir(uuid, dirPath);
        if (!dir.exists()) return str;
        String filePath = getFilePath(dir, uuid, extension);
        str = readFile(filePath);
        return str;
    }

    public static String saveT(String data, String uuid, String dirPath, String extension) throws IOException {
        File dir = getDir(uuid, dirPath);
        if (!dir.exists()) dir.mkdirs();
        String filePath = getFilePath(dir, uuid, extension);
        writeStringToFile(data, filePath);
        return filePath;
    }

    public static void deleteT(String uuid, String dirPath, String extension) {
        File dir = getDir(uuid, dirPath);
        if (!dir.exists()) return;
        String filePath = getFilePath(dir, uuid, extension);
        deleteFile(filePath);
    }

    public static File getDir(String uuid, String dirPath) {
        return new File(dirPath + File.separator + UUIDUtil.getTimeFromUUID(uuid));
    }

    public static String getFilePath(File dirFile, String uuid, String extension) {
        return dirFile.getAbsolutePath() + File.separator + uuid + "." + extension;
    }


    /**
     * 递归删除文件或子文件夹
     *
     * @param path 路径
     */
    public static void deleteFile(String path) {
        File file = new File(path);
        if (!file.exists()) {
            return;
        }
        if (file.isFile()) {
            file.delete();
            return;
        }
        if (file.isDirectory()) {
            for (File f : file.listFiles()) {
                deleteFile(f.getAbsolutePath());
            }
        }
        file.delete();
    }

    public static void getFilesBySuffix(File dir, String suffix, Collection<File> collection) {
        File[] files = dir.listFiles();
        for (File subFile : files) {
            if (subFile.isDirectory())
                getFilesBySuffix(subFile, suffix, collection);
            else if (subFile.getName().toLowerCase().endsWith(suffix)) {
                collection.add(subFile);
            }
        }
    }
}
