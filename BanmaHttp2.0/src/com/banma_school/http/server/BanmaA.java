package com.banma_school.http.server;
/**
 * 1.定义一个类
 * 2.给这个类做url映射
 * @author banma
 *
 */
public class BanmaA implements IServer{
	public String execute(String[][] pStrings) {
		String string = "一对一教学";
		
		if(pStrings == null) {
			return string;
		}
		
		for (int i = 0; i < pStrings.length; i++) {
			string += pStrings[i][0] + "-"+pStrings[i][1];
		}
		
		return string;
	}
}
