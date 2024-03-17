package com.banma_school.http;

import java.net.Socket;

import org.apache.log4j.Logger;


public class RequestHandler implements Runnable {

	private static Logger log = Logger.getLogger(RequestHandler.class);

	private Socket socket;

	public RequestHandler(Socket socket) {
		this.socket = socket;
	}

	public void run() {
		
		try {
			
			HttpRequest req = new HttpRequest(socket.getInputStream());
			HttpResponse res = new HttpResponse(req);
			res.write(socket.getOutputStream());

		} catch (Exception e) {
			log.error("Runtime Error", e);
		}finally {
			try {
				socket.close();
			} catch (Exception e2) {
				log.error("Runtime Error", e2);
			}
		}
	}
}
