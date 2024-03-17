package com.banma_school.http;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

public class HttpHeader {
	private static Logger log = Logger.getLogger(HttpRequest.class);
	
    private Method method;
    private String path;
	String version;
	
    private Map<String, String> messageHeaders = new HashMap<>();
    
    public HttpHeader(InputStream in) throws IOException {
		String str = IOUtil.readLine(in);
		
		log.info(str);

		String[] split = str.split("\\s+");
		
		try {
			method = Method.valueOf(split[0]);
		} catch (Exception e) {
			method = Method.UNRECOGNIZED;
		}
		
		path = split[1];
		version = split[2];
		
		String messageLine = IOUtil.readLine(in);
        
        while (messageLine != null && !messageLine.isEmpty()) {
            this.putMessageLine(messageLine);
            messageLine = IOUtil.readLine(in);
        }
		
    }
    
    private void putMessageLine(String messageLine) {
        String[] tmp = messageLine.split(":");
        String key = tmp[0].trim();
        String value = tmp[1].trim();
        this.messageHeaders.put(key, value);
    }
    
    public int getContentLength() {
        return Integer.parseInt(this.messageHeaders.getOrDefault("Content-Length", "0"));
    }
    
    public String getPath() {
        return this.path;
    }

    public Method getMethod() {
        return this.method;
    }
}
