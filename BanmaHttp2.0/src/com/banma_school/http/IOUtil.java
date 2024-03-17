package com.banma_school.http;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UncheckedIOException;
import java.util.ArrayList;
import java.util.List;

public class IOUtil {
    
    public static void println(OutputStream out, String line) {
        print(out, line + Constant.CRLF);
    }
    
    public static void print(OutputStream out, String line) {
        try {
            out.write(line.getBytes(Constant.UTF_8));
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    public static String readLine(InputStream in) throws IOException {
        List<Byte> list = new ArrayList<>();
        
        while (true) {
            byte b = (byte)in.read();
            
            if (b == -1) {
                throw new EmptyRequestException();
            }
            
            list.add(b);
            
            int size = list.size();
            if (2 <= size) {
                char cr = (char)list.get(size - 2).byteValue();
                char lf = (char)list.get(size - 1).byteValue();
                
                if (cr == '\r' && lf == '\n') {
                    break;
                }
            }
        }
        
        byte[] buffer = new byte[list.size() - 2]; 
        for (int i = 0; i < list.size() - 2; i++) {
            buffer[i] = list.get(i);
        }
        
        return new String(buffer, Constant.UTF_8);
    }
    
    public static InputStream toInputStream(String string) {
        return new ByteArrayInputStream(string.getBytes(Constant.UTF_8));
    }
    
    public static String toString(byte[] buffer) {
        return new String(buffer, Constant.UTF_8);
    }
    
    private IOUtil() {}
}
