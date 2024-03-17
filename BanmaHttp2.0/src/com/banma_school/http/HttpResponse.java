package com.banma_school.http;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.banma_school.http.server.BanmaA;
import com.banma_school.http.server.BanmaB;
import com.banma_school.http.server.BanmaC;
import com.banma_school.http.server.IServer;

public class HttpResponse {

	private static Logger log = Logger.getLogger(HttpResponse.class);
	
	List<String> headers = new ArrayList<String>();

	byte[] body;
	
	private static Map<String, IServer> urlMap;
	
	static {
		//ӳ�� url ������� java��
		urlMap = new HashMap<String, IServer>();
		urlMap.put("/banma.a", new BanmaA());
		urlMap.put("/banma.b", new BanmaB());
		urlMap.put("/sanjiao", new BanmaC());
		
	}


	public HttpResponse(HttpRequest req) throws IOException {

		try {
			
			HttpHeader header = req.getHeader();
	
			Method method = header.getMethod();
			
			switch (method) {
				case HEAD:
					fillHeaders(Status._200);
					break;
				case POST:
					fillHeaders(Status._200);
					fillResponse(req.getBodyText());
					break;
				case GET:
				
						String pathString = "index.html";
						if(!"/".equals(header.getPath())) {
							pathString = header.getPath();
						}
						///banma.b?sss=123&zzz=321
						String[] pathArrString = pathString.split("\\?");
						
						if(urlMap.containsKey(pathArrString[0])) {
							
							 fillHeaders(Status._200);
							 setContentType(pathString, headers);
							 String[][] psStrings = null;
							 IServer iServer = urlMap.get(pathArrString[0]);
							 if(pathArrString.length == 2) {
								 String[] parameter = pathArrString[1].split("&");
								 psStrings = new String[parameter.length][2];
										 
								 for (int i = 0; i < parameter.length; i++) {
									 String[] parString = parameter[i].split("=");//0-> key 1 -> value  sss=123
									 psStrings[i] = parString;
								 }
								
							 }
							 
							 String repsonseString =  iServer.execute(psStrings);
							 
							 fillResponse(repsonseString);
							 
							 break;
							 
						}
						
						File file = new File("htdoc", pathString);
						if (file.isDirectory()) {
						    fillHeaders(Status._200);
						    
							headers.add(ContentType.HTML.toString());
							StringBuilder result = new StringBuilder("<html><head><title>Index of ");
							result.append(header.getPath());
							result.append("</title></head><body><h1>Index of ");
							result.append(header.getPath());
							result.append("</h1><hr><pre>");
	
							File[] files = file.listFiles();
							for (File subfile : files) {
								result.append(" <a href=\"" + subfile.getPath() + "\">" + subfile.getPath() + "</a>\n");
							}
							result.append("<hr></pre></body></html>");
							fillResponse(result.toString());
						} else if (file.exists()) {
						    fillHeaders(Status._200);
							setContentType(pathString, headers);
							fillResponse(getBytes(file));
						} else {
							log.info("File not found:" + header.getPath());
							fillHeaders(Status._404);
							fillResponse(Status._404.toString());
						}
	
					break;
				case UNRECOGNIZED:
					fillHeaders(Status._400);
					fillResponse(Status._400.toString());
					break;
				default:
					fillHeaders(Status._501);
					fillResponse(Status._501.toString());
			}//switch end
				
		} catch (Exception e) {
			log.error("Response Error", e);
			fillHeaders(Status._400);
			fillResponse(Status._400.toString());
		}

	}

	private byte[] getBytes(File file) throws IOException {
		int length = (int) file.length();
		byte[] array = new byte[length];
		InputStream in = new FileInputStream(file);
		int offset = 0;
		while (offset < length) {
			int count = in.read(array, offset, (length - offset));
			offset += count;
		}
		in.close();
		return array;
	}

	private void fillHeaders(Status status) {
		headers.add(Constant.VERSION + " " + status.toString());
		headers.add("Server: BanmaHttp");
	}

	private void fillResponse(String response) {
		body = response.getBytes();
	}

	private void fillResponse(byte[] response) {
		body = response;
	}

	public void write(OutputStream os) throws IOException {
		DataOutputStream output = new DataOutputStream(os);
		for (String header : headers) {
			output.writeBytes(header + Constant.CRLF);
		}
		
		output.writeBytes(Constant.CRLF);
		if (body != null) {
			output.write(body);
		}
		
		output.writeBytes(Constant.CRLF);
		output.flush();
	}

	private void setContentType(String uri, List<String> list) {
		try {
			String ext = uri.substring(uri.indexOf(".") + 1);
			list.add(ContentType.valueOf(ext.toUpperCase()).toString());
		} catch (Exception e) {
			list.add("Content-Type: text/html charset=utf-8");
			//log.error("ContentType not found: " + e, e);
		}
	}
}
