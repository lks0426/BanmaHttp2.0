package com.banma_school.http;
import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

public class HttpRequest {
	
	private HttpHeader header;
	private String bodyText;

	public HttpRequest(InputStream is) throws IOException {

		try {
			
			this.header = new HttpHeader(is);
			this.bodyText = this.readBody(is);

		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
	}
	
	private String readBody(InputStream in) throws IOException {
		return this.readBodyByContentLength(in);
	}

	private String readBodyByContentLength(InputStream in) throws IOException {
		final int contentLength = this.header.getContentLength();

		if (contentLength <= 0) {
			return null;
		}

		byte[] buffer = new byte[contentLength];
		in.read(buffer);

		return  IOUtil.toString(buffer);
	}
	
	// 在HttpRequest类中添加一个方法来解析请求体中的键值对
	public Map<String, String> parseFormBody() throws UnsupportedEncodingException {
	    Map<String, String> formData = new HashMap<>();
	    
	    if (bodyText != null && !bodyText.isEmpty()) {
	        // 分割键值对
	        String[] pairs = bodyText.split("&");
	        for (String pair : pairs) {
	            // 分割键和值
	            String[] kv = pair.split("=");
	            String key = URLDecoder.decode(kv[0], "UTF-8");
	            String value = (kv.length > 1) ? URLDecoder.decode(kv[1], "UTF-8") : ""; // 处理没有值的情况
	            formData.put(key, value);
	        }
	    }
	    
	    return formData;
	}

	public HttpHeader getHeader() {
		return header;
	}

	public void setHeader(HttpHeader header) {
		this.header = header;
	}

	public String getBodyText() {
		return bodyText;
	}

	public void setBodyText(String bodyText) {
		this.bodyText = bodyText;
	}

	
}
