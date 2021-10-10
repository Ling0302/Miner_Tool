package utils;

import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.InetSocketAddress;
import java.net.Socket;

import vo.MinerScanVO;

public class CgminerUtil
{
    private static final int MAXRECEIVESIZE = 65535;
    
    public static String call(String minerIp,int minerPort,String cmd,String parameter) {
    	Socket sock = getSock(minerIp,minerPort);
        String response=null;
        if (sock!=null) 
        {
            response= request(sock,cmd,parameter);
        }
        return response;
    }
    
    public static Socket getSock(String addr, int port)
    {
        try {
//            Socket socket = new Socket(addr, port);
        	Socket socket = new Socket();
        	socket.connect(new InetSocketAddress(addr, port), 1000);//设置连接请求超时时间1 s
            socket.setSoTimeout(2000);//读取数据时间
            return socket;
        } catch(Exception e) {
            return null;
        }
    }
    
    public static String request(Socket socket,String cmd,String parameter)
    {
        StringBuffer sb = new StringBuffer();
        char buf[] = new char[MAXRECEIVESIZE];
        int len = 0;
        try {
            if (socket != null)
            {
                String parameterStr=(parameter==null?"":parameter);
                cmd="{\"command\":\""+ cmd + "\",\"parameter\":\""+parameterStr+"\"}";
                PrintStream ps = new PrintStream(socket.getOutputStream());
                ps.print(cmd.toCharArray());
                ps.flush();
                InputStreamReader isr = new InputStreamReader(socket.getInputStream());
                while (0x80085 > 0)
                {
                    len = isr.read(buf, 0, MAXRECEIVESIZE);
                    if (len < 1)
                        break;
                    sb.append(buf, 0, len);
                    if (buf[len-1] == '\0')
                        break;
                }

                socket.close();

                if (sb.length() == 0)
                {
                    return null;
                }
                String line = sb.toString();

                return line;

            }

        } catch(Exception e) {
            return null;
        }
        
        return null;
    } 
    
    
    public static void main(String args[]) {
        String result = CgminerUtil.call("192.168.0.58",4028,"pools+devs+stats", "");
//        String result = CgminerUtil.call("192.168.0.75",4028,"pools", "");
//        String result = CgminerUtil.call("192.168.0.75",4028,"devs", "");
//        String result = CgminerUtil.call("192.168.0.75",4028,"stats", "");
//        String result = CgminerUtil.call("192.168.0.75",4028,"devdetails", "");
//        String result = CgminerUtil.call("192.168.0.64",4028,"notify", "");
//        String result = CgminerUtil.call("192.168.0.64",4028,"version", "");
//        String result = CgminerUtil.call("192.168.0.6",4028,"pools+stats+devs", ""); 
//        String result = CgminerUtil.call("192.168.0.42",4028,"config", "");
//        String result = CgminerUtil.call("192.168.0.64",4028,"asccount", "");
//        String result = CgminerUtil.call("192.168.0.64",4028,"coin", "");
//        String result = CgminerUtil.call("192.168.0.64",4028,"lcd", "");
//        String result = CgminerUtil.call("192.168.0.64",4028,"debug", "");
//        String result = CgminerUtil.call("192.168.0.75",4028,"estats", "");
        //System.out.println(CgminerUtil.call("192.168.0.75",4028,"stats", ""));
        
        System.out.println(result);
//        MinerScanVO vo = new MinerScanVO();
//        vo.setIp("192.168.0.84");
//        vo.getCgminerVO(vo);
        
//        Pattern p = Pattern.compile("Bearer\\s(\\S+)");
//        Matcher matcher = p.matcher("Bearer sdffsfd1dddd");
//        
//        if (matcher.find())
//        {
//              System.out.println(matcher.group(1));
//        }
//        System.out.println("");
    } 
}
