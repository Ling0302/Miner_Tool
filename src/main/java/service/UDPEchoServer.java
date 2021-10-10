package service;

import java.net.DatagramSocket;
import java.net.SocketException;


public class UDPEchoServer {

    // 默认的端口
    public static int DEFAULT_PORT = 10000;
    // 用于连接的UDP Socket
    private DatagramSocket serverSocket;

    public UDPEchoServer() throws SocketException {
        // 将UDP连接绑定到默认的主机以及端口
        serverSocket = new DatagramSocket(DEFAULT_PORT);
    }

    public DatagramSocket getServerSocket() {
        return serverSocket;
    }

    public void setServerSocket(DatagramSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

}


