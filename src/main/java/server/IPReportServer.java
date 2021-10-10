package server;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.wb.swt.SWTResourceManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import app.MinerCheckApp;
import service.ButtonStatusService;
import service.IPReportThreadService;

public class IPReportServer {
	
	private static final Logger logger = LoggerFactory.getLogger(IPReportServer.class);

	// 默认的端口
	public static int DEFAULT_PORT = 10000;
	// 用于连接的UDP Socket
	private DatagramSocket serverSocket;

	public IPReportServer() throws SocketException {
		// 将UDP连接绑定到默认的主机以及端口
		serverSocket = new DatagramSocket(DEFAULT_PORT);
		logger.info("UDP-IpReport-server is running！");
	}
	
	/**
	 * 开启ipreport
	 * @throws SocketException
	 */
	public static void startServer() throws SocketException {
		// 创建服务端的对象
		IPReportServer server = new IPReportServer();
		while (true) {
			try {
				// 初始化接受数据包，此处缓冲区使用最大的64KB
				DatagramPacket dp = new DatagramPacket(new byte[1024], 1024);
				// 阻塞接受数据包
				server.getServerSocket().receive(dp);
				// 打印数据包内容
				String mac = new String(dp.getData(), 0, dp.getLength()).toString();
				String ip = dp.getAddress().toString().replaceAll("/", "");
				
				Display.getDefault().asyncExec(new Runnable() {
					@Override
					public void run() {		
						String text = "";
						int index = 0;
						TableItem[] items = MinerCheckApp.macTable.getItems();
						if(items.length == 0) {
							MinerCheckApp.lblNewLabel_16.setText(ip);
							MinerCheckApp.lblNewLabel_16.setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));
							return;
						}
						for(TableItem item :items) {
							index++;
							String currIp = item.getText(0).trim();
							if(currIp.equals(ip)) {
								text = "IP("+ip+") already exists";
								MinerCheckApp.lblNewLabel_16.setText(text);
								MinerCheckApp.lblNewLabel_16.setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));
								break;
							}else {
								text = ip;
								MinerCheckApp.lblNewLabel_16.setText(text);
								MinerCheckApp.lblNewLabel_16.setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));
							}
							
							if(index == items.length) {
								item.setText(0, ip);								
								item.setText(2, mac);								
							}
						}
					}
				});
				
				logger.info("from hostname : " + dp.getAddress() + ", port : " + dp.getPort() + " : "
						+ new String(dp.getData(), 0, dp.getLength()));
				// 将数据包传递给处理线程处理，提供服务器的处理效率
				Thread echoHandlerThread = new EchoHandlerThread(dp, server.getServerSocket());
				echoHandlerThread.start();
				// 放弃，让处理线程优先执行一下
				Thread.yield();

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args){		
		IPReportThreadService iPReportThreadService = new IPReportThreadService();
		iPReportThreadService.start();	
	}

	public DatagramSocket getServerSocket() {
		return serverSocket;
	}

	public void setServerSocket(DatagramSocket serverSocket) {
		this.serverSocket = serverSocket;
	}

}

class EchoHandlerThread extends Thread {

	private DatagramPacket dp;
	private DatagramSocket serverSocket;

	public EchoHandlerThread(DatagramPacket dp, DatagramSocket serverSocket) {
		this.dp = dp;
		this.serverSocket = serverSocket; 
	}

	@Override
	public void run() {
		// 获取服务端接受的数据并且处理用于回显操作
		String replyString = new String(dp.getData(), 0, dp.getLength()).toUpperCase();
		byte[] replyData = replyString.getBytes();
		// 初始化数据包，包括填充数据，地址和端口
		DatagramPacket ndp = new DatagramPacket(replyData, 0, replyData.length, dp.getAddress(), dp.getPort());
		System.out.println("sent to hostname : " + ndp.getAddress() + ", port : " + ndp.getPort() + " : "
				+ new String(ndp.getData(), 0, dp.getLength()));
		try {
			// 通过UDP Socket发送数据包
			serverSocket.send(ndp);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
