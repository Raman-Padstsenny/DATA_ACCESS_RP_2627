package com.da.rp2627.activities;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;

import static com.da.rp2627.activities.App443.DEFAULT_FILE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class App443Test {

    @Test
    void readsHeaderOfBmpResource() throws IOException {
        try (FileInputStream fIn = new FileInputStream(DEFAULT_FILE)) {
            assertNotNull(fIn, "bmp test resource not found");
            String expected = "Size: 12342 bytes\n"
                    + "Width: 64 px\n"
                    + "Height: 64 px\n"
                    + "Bits per pixel: 24";
            assertEquals(expected, App443.readHeaderInfo(fIn));
        }
    }

    @Test
    void rejectsFileWithoutBmpSignature() throws IOException {
        byte[] data = new byte[40];
        data[0] = (byte) 0x89;
        assertEquals("Not a valid BMP file", App443.readHeaderInfo(new ByteArrayInputStream(data)));
    }

    @Test
    void rejectsTooShortFile() throws IOException {
        assertEquals("Not a valid BMP file", App443.readHeaderInfo(new ByteArrayInputStream(new byte[] {'B', 'M', 0})));
    }
}
