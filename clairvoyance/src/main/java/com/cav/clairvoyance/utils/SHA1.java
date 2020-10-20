package com.cav.clairvoyance.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Utility class for calculating SHA1 sums of files as strings.
 *
 * @author evan
 */
public class SHA1 {

    private static final String SHA1_STR = "SHA-1";

    private static char[] hexChars = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    private static MessageDigest md = null;

    private static final int BYTES_TO_READ = 1024;

    /**
     * Given a byte, return its representation as a 2-char hex string.
     *
     * @param b
     * @return
     */
    public static String byteToHexString(byte b) {
        /*
         * 8 bits.  To get the low 4 bits, AND with 1111 (15)
         */
        int low = ((b & 15) + hexChars.length) % hexChars.length;

        /*
         * To get the high 4 bits, shift right 4 bits.
         */
        int high = ((b >> 4) + hexChars.length) % hexChars.length;

        return new String(new char[]{hexChars[high], hexChars[low]});
    }

    public static String byteArrayToHexString(byte[] byteArray) {

        StringBuilder sb = new StringBuilder();

        for (byte b : byteArray) {
            sb.append(byteToHexString(b));
        }

        return sb.toString();

    }

    public static synchronized byte[] getSHA1(InputStream is) {
        try {
            md = MessageDigest.getInstance(SHA1_STR);
        } catch (NoSuchAlgorithmException ne) {
            throw new RuntimeException(ne);
        }
        byte readArray[] = new byte[BYTES_TO_READ];
        byte digest[];

        int totalBytesRead = 0;
        int bytesRead = 0;

        try {
            while ((bytesRead = is.read(readArray, 0, BYTES_TO_READ)) > 0) {
                totalBytesRead += bytesRead;
                md.update(readArray, 0, bytesRead);
            }

            digest = md.digest();
        } catch (IOException ie) {
            throw new RuntimeException("IO Exception: " + ie.getLocalizedMessage(), ie);
        }

        return digest;
    }

    public static synchronized byte[] getSHA1(String s) {
        try {
            md = MessageDigest.getInstance(SHA1_STR);
        } catch (NoSuchAlgorithmException ne) {
            throw new RuntimeException(ne);
        }

        byte byteArray[] = s.getBytes();
        byte digest[];

        md.update(byteArray);

        digest = md.digest();

        return digest;
    }

    public static synchronized String getSHA1String(String s) {
        byte digest[] = getSHA1(s);
        return byteArrayToHexString(digest);
    }

    public static synchronized String getSHA1String(InputStream is) {
        byte digest[] = getSHA1(is);

        return byteArrayToHexString(digest);
    }

    public static byte[] getSHA1(File f) throws FileNotFoundException {
        FileInputStream is = new FileInputStream(f);
        return getSHA1(is);
    }

    public static String getSHA1String(File f) throws FileNotFoundException {
        FileInputStream is = new FileInputStream(f);
        return getSHA1String(is);
    }

    public static void main(String args[]) {
        if (args.length < 1) {
            System.err.println("Error: first argument must be the filename.");
            return;
        }
        File f = new File(args[0]);

        try {
            String digest = SHA1.getSHA1String(f);
            System.out.println(digest + "\t" + f.getAbsolutePath());
        } catch (FileNotFoundException fn) {
            throw new RuntimeException("Error opening file " + f.getAbsolutePath(), fn);
        }
    }

}
