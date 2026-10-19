package com.da.rp2627.activities;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class App443 {
    public static final String DEFAULT_FILE = "/home/raman_padstsenny/IdeaProjects/DAM2/DATA_ACCESS_RP_2627/Unit2/Act_4/3/src/com/da/rp2627/activities/bmp";
    private static final int HEADER_LENGTH = 30;

    public static void main(String[] args) {
        try (FileInputStream fIn = new FileInputStream(DEFAULT_FILE)) {
            System.out.println(readHeaderInfo(fIn));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    static String readHeaderInfo(InputStream in) throws IOException {
        byte[] header = new byte[HEADER_LENGTH];
        int n = in.read(header);
        if (n < HEADER_LENGTH || header[0] != 'B' || header[1] != 'M') {
            return "Not a valid BMP file";
        }
        return "Size: " + readInt(header, 2) + " bytes\n"
                + "Width: " + readInt(header, 18) + " px\n"
                + "Height: " + readInt(header, 22) + " px\n"
                + "Bits per pixel: " + readShort(header, 28);
    }

    private static int readInt(byte[] d, int off) {
        return (d[off] & 0xFF)
                | (d[off + 1] & 0xFF) << 8
                | (d[off + 2] & 0xFF) << 16
                | (d[off + 3] & 0xFF) << 24;
    }

    private static int readShort(byte[] d, int off) {
        return (d[off] & 0xFF)
                | (d[off + 1] & 0xFF) << 8;
    }
}
