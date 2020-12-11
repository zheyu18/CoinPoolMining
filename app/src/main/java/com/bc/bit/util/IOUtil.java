package com.bc.bit.util;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

/**
 *  数据流处理
 */
public class IOUtil {
    public static String readInputStreamToString(InputStream input) {
        try {
            if (input != null) {
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                byte[] buf = new byte[4096];
                int read;
                while ((read = input.read(buf)) > 0) {
                    out.write(buf, 0, read);
                    out.flush();
                }

                String str = new String(out.toByteArray());
                closeIn(input);
                closeOut(out);
                return str;
            }
        } catch (Exception var5) {
            var5.printStackTrace();
        }
        return null;
    }

    public static void closeIn(InputStream input) {
        try {
            if (input != null) {
                input.close();
            }
        } catch (Exception var2) {
        }

    }

    public static void closeOut(OutputStream out) {
        try {
            if (out != null) {
                out.close();
            }
        } catch (Exception var2) {
        }

    }
}
