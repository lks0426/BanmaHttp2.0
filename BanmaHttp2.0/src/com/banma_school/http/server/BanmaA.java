package com.banma_school.http.server;
/**
 * 1.����һ����
 * 2.���������urlӳ��
 * @author banma
 *
 */
public class BanmaA implements IServer{
	public String execute(String[][] pStrings) {
		String string = "11";
		
		if(pStrings == null) {
			return string;
		}
		
		for (int i = 0; i < pStrings.length; i++) {
			string += pStrings[i][0] + "-"+pStrings[i][1];
		}
		
		return string;
	}
}
