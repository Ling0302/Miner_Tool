package service;

import java.net.SocketException;

import server.IPReportServer;

public class IPReportThreadService extends Thread{
	@Override
	public void run() {
		try {
			IPReportServer.startServer();
		} catch (SocketException e) { 
			e.printStackTrace();
		}
	}
}
