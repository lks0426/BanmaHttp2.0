package com.banma_school.http;
import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;

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
