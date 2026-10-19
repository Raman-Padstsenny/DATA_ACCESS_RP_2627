package com.da.rp2627.activities;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class DataStreamExample {
    public static void main(String[] args) throws IOException {
        FileInputStream fIn = null;
        FileOutputStream fOut = null;
        try {
            fIn = new FileInputStream("/home/raman_padstsenny/IdeaProjects/DAM2/DATA_ACCESS_RP_2627/Unit2/Act_4/1/src/com/da/rp2627/activities/testin.txt");
            fOut = new FileOutputStream("/home/raman_padstsenny/IdeaProjects/DAM2/DATA_ACCESS_RP_2627/Unit2/Act_4/1/src/com/da/rp2627/activities/testout.txt");
            byte[] data = new byte[128];
            int i = 0;
            while (fIn.read(data) != -1) {
                fOut.write(data);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } finally {
            if (fIn != null) {
                fIn.close();
            }
            if (fOut != null) {
                fOut.close();
            }
        }
    }
}
