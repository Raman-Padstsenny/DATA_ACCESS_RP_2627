package com.da.rp2627.activities;

import java.io.FileInputStream;
import java.io.IOException;

public class App442 {
    public static void main(String[] args) {
        //Despite we didn't have it in the studying materials, I think using "try-with-resources" Java feature is better than using finally block for Closable objects.
        try (FileInputStream fIn = new FileInputStream("/home/raman_padstsenny/IdeaProjects/DAM2/DATA_ACCESS_RP_2627/Unit2/Act_4/2/src/com/da/rp2627/activities/bmp")) {
            byte[] data = new byte[6];
            int n = fIn.read(data);
            System.out.println(detect(data, Math.max(n, 0)));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private static String detect(byte[] d, int n) {
        if (startsWith(d, n, 0x42, 0x4D)) return "BMP";
        if (startsWith(d, n, 0x47, 0x49, 0x46, 0x38, 0x39, 0x61)
                || startsWith(d, n, 0x47, 0x49, 0x46, 0x38, 0x37, 0x61)) return "GIF";
        if (startsWith(d, n, 0x00, 0x00, 0x01, 0x00)) return "ICO";
        if (startsWith(d, n, 0xFF, 0xD8, 0xFF)) return "JPEG";
        if (startsWith(d, n, 0x89, 0x50, 0x4E, 0x47)) return "PNG";
        return "Unknown format";
    }

    private static boolean startsWith(byte[] d, int n, int... sig) {
        if (n < sig.length) return false;
        for (int i = 0; i < sig.length; i++) {
            if ((d[i] & 0xFF) != sig[i]) return false;
        }
        return true;
    }
}
