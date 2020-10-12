package com.cav.clairvoyance.utils;

import java.io.*;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class ZipUtil {

    private static final int BUFF_SIZE = 4 * 1024;

    /**
     * 自定义添加文件生成zip包，zip包中没有目录结构
     * <p>
     * 用法：ZipUtil.zipFiles("/Users/hh/git/job/circle/tmp.zip",
     * "/Users/hh/git/job/circle/tmp/aa.txt",
     * "/Users/hh/Downloads/test_2.jpg",
     * "/Users/hh/git/job/circle/tmp/bb.txt");
     *
     * @param zipFileName 全路径的zip文件包的名字
     * @param files       要添加到zip包中的文件的全路径
     */
    public static void zipFiles(String zipFileName, String... files) {
        try {
            byte[] buffer = new byte[1024];
            File zipFile = new File(zipFileName);
            FileOutputStream fos = new FileOutputStream(zipFile);
            ZipOutputStream zos = new ZipOutputStream(new BufferedOutputStream(fos));
            for (String file : files) {
                File file1 = new File(file);
                String fileName = file1.getName();
                ZipEntry zipEntry = new ZipEntry(fileName);
                zos.putNextEntry(zipEntry);
                FileInputStream fis = new FileInputStream(file1);
                int len;
                while ((len = fis.read(buffer)) > 0) {
                    zos.write(buffer, 0, len);
                }
                fis.close();
            }
            zos.closeEntry();
            zos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 压缩文件夹
     *
     * @param zipFileName 全路径的zip文件包的名字
     * @param dir         要压缩的文件夹的路径
     */
    public static void zipDir(String zipFileName, String dir) {
        try {
            File zipFile = new File(zipFileName);
            FileOutputStream fos = new FileOutputStream(zipFile);
            ZipOutputStream zos = new ZipOutputStream(new BufferedOutputStream(fos));
            File dirFile = new File(dir);
            addEntry(dirFile, zos, "");
            zos.closeEntry();
            zos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void addEntry(File file, ZipOutputStream zos, String root) throws IOException {
        byte[] buffer = new byte[1024];
        for (File file1 : file.listFiles()) {
            System.out.println(file1.getName());
            if (file1.isDirectory()) {
                addEntry(file1, zos, file1.getName());
            } else {
                String fileName = file1.getName();
                ZipEntry zipEntry = new ZipEntry(root + "/" + fileName);
                zos.putNextEntry(zipEntry);
                FileInputStream fis = new FileInputStream(file1);
                int len;
                while ((len = fis.read(buffer)) > 0) {
                    zos.write(buffer, 0, len);
                }
                fis.close();
            }
        }
    }

    public static boolean unzip(String zipFilePath, String unzipPath) {

        try {
            ZipFile zipFile = new ZipFile(zipFilePath);
            Enumeration emu = zipFile.entries();
            int i = 0;
            while (emu.hasMoreElements()) {
                ZipEntry entry = (ZipEntry) emu.nextElement();
                if (entry.isDirectory()) {
                    new File(unzipPath + "/" + entry.getName()).mkdirs();
                    continue;
                }

                BufferedInputStream bis = new BufferedInputStream(zipFile.getInputStream(entry));
                File file = new File(unzipPath + "/" + entry.getName());
                File parent = file.getParentFile();
                if (parent != null && (!parent.exists())) {
                    parent.mkdirs();
                }

                FileOutputStream fos = new FileOutputStream(file);
                BufferedOutputStream bos = new BufferedOutputStream(fos);

                int count;
                byte data[] = new byte[BUFF_SIZE];
                while ((count = bis.read(data, 0, BUFF_SIZE)) != -1) {
                    bos.write(data, 0, count);
                }

                bos.flush();
                bos.close();
                bis.close();
            }

            zipFile.close();

            return true;
        } catch (Exception e) {
            // Log.e("ZipUtil", "unzip error! zip file:" + zipFilePath + " unzip to path:" + unzipPath);
            e.printStackTrace();
            return false;
        }
    }

}
