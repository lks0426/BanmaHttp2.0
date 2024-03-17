package com.banma_school.http;
import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.apache.log4j.Logger;

public class WebServer {

	private static Logger log = Logger.getLogger(WebServer.class);

	private static final int DEFAULT_PORT = 80;

	public static void main(String args[]) {
		try {
			new WebServer().start(getValidPortParam(args));
		} catch (Exception e) {
			log.error("启动错误", e);
		}
	}

	public void start(int port) throws IOException {
		ServerSocket s = new ServerSocket(port);
		log.info("Banma服务器已经启动，监听:" + port);
		
		ExecutorService executor = Executors.newCachedThreadPool();
		while (true) {
			executor.submit(new RequestHandler(s.accept()));
		}
	}

	static int getValidPortParam(String args[]) throws NumberFormatException {
		if (args.length > 0) {
			int port = Integer.parseInt(args[0]);
			if (port > 0 && port < 65535) {
				return port;
			} else {
				throw new NumberFormatException("请检查您的端口号");
			}
		}
		return DEFAULT_PORT;
	}
}
