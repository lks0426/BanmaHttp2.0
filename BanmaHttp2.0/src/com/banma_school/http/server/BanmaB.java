package com.banma_school.http.server;

public class BanmaB implements IServer{
	public String execute(String[][] pStrings) {
		String string = "欢迎来到东京斑马编程教室";
		
		if(pStrings == null) {
			return string;
		}
		
		for (int i = 0; i < pStrings.length; i++) {
			string += pStrings[i][0] + "-"+pStrings[i][1];
		}
		
		return string;
	}
}
