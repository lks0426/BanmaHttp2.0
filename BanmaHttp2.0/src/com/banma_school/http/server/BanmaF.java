package com.banma_school.http.server;

import java.util.Map;

public class BanmaF implements IServer2 {

	@Override
	public String execute(Map<String,String> pStringsMap) {
		
		if(pStringsMap.get("size") == null) {
			return "缺少必要参数";
		}
		
		String htmlString = "<!DOCTYPE html>\r\n" + 
				"<html>\r\n" + 
				"<head>\r\n" + 
				"	<title>斑马编程教室</title>\r\n" + 
				"	<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />\r\n" + 
				"</head>\r\n" + 
				"<body>\r\n";
		
			//sanjiao?size=3
			//[[size,3]]
		
			int size = Integer.valueOf(pStringsMap.get("size"));
			for (int i = 0; i < size; i++) {
				for (int j = 0; j < size; j++) {
					htmlString += "*";
				}
				htmlString += "<br/>";
			}
		
				
			htmlString +=	"</body>\r\n</html>";
		
		
		try {
			byte[] buf = htmlString.getBytes("utf-8");
			htmlString = new String(buf);
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return htmlString;
	}

}
