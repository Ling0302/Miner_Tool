package service;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class EchoHandlerThread extends Thread 
{
    
    private IThreadCallback callback;
    
    DatagramSocket serverSocket = null;
    
    public EchoHandlerThread(IThreadCallback callback)
    {
        this.callback = callback;
    }

    @Override
    public void run() {
        try
        {
            serverSocket = new DatagramSocket(10000);
            DatagramPacket dp = new DatagramPacket(new byte[1024], 1024);
            
            while (!serverSocket.isClosed()) {  
                try
                {
                    serverSocket.receive(dp);
                    callback.execute(dp.getAddress().getHostAddress());
                    Thread.sleep(2000); 
                }
                catch (SocketException e1)
                {
                    e1.printStackTrace();
                }
                catch (IOException e1)
                {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
                catch (InterruptedException e)
                {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
        catch (SocketException e2)
        {
            e2.printStackTrace();
        }
        finally
        {
            serverSocket.close();
        }
        
    }
    
    public void closeServer()
    {
        if (serverSocket != null)
            serverSocket.close();
    }

}
