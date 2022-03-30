package utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.util.Properties;
import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import cn.dreampie.log.Logger;

public class SftpUtils {

    protected final Logger logger = Logger.getLogger(getClass());

    protected String host;
    protected int port;
    protected String username;
    protected String password;
    

    /**
     * @param host
     *            ip
     * @param port
     *            端口
     * @param username
     *            账号
     * @param password
     *            密码
     */
    public SftpUtils(String host, int port, String username, String password) {
        set(host, port, username, password);
    }

    public void set(String host, int port, String username, String password) {
        this.host = host;
        this.port = port;
        this.username = username;
        this.password = password;
    }

    Session sshSession = null;

    /**
     * 链接linux
     */
    public ChannelSftp connect() {
        ChannelSftp sftp = null;
        try {
            JSch jsch = new JSch();
            jsch.getSession(username, host, port);
            sshSession = jsch.getSession(username, host, port);
            sshSession.setPassword(password);
            Properties sshConfig = new Properties();
            sshConfig.put("StrictHostKeyChecking", "no");
            sshSession.setConfig(sshConfig);
            sshSession.connect();
            logger.info(String.format("%s connect success", host));
            Channel channel = sshSession.openChannel("sftp");
            channel.connect();
            sftp = (ChannelSftp) channel;
        } catch (Exception e) {
            // LogManager.err("connect:" + host , e );
            close(null);
            return null;
        }
        return sftp;
    }

    /**
     * linux上传文件
     */
    public int upload(String directory, File file, String destFileName) {
        ChannelSftp sftp = connect();
        try {
            if (null != sftp) {
                sftp.cd(directory);
                logger.info(String.format("cd %s", directory));
                if (destFileName != null)
                {
                    sftp.put(new FileInputStream(file), destFileName);
                }
                else
                {
                    sftp.put(new FileInputStream(file), file.getName());
                }
                return 1;
            }
        } catch (Exception e) {
            logger.error("upload:" + host, e);
            return -1; 
        } finally {
            sftp.disconnect();
            sftp.exit();
            sshSession.disconnect();
        }
        return 0; 
    }

    /**
     * linux下载文件
     */
    public void get(String directory, String downloadFile) {
        ChannelSftp sftp = connect();
        try {
            if (null != sftp) {
                File file = new File(directory);
                String parent = getParent(file);
                sftp.cd(parent);
                File desc = new File(downloadFile);
                FileOutputStream outputStream = new FileOutputStream(desc);
                sftp.get(file.getName(), outputStream);
                logger.info(String.format("down %s suc", directory));
                outputStream.close();
            }
        } catch (Exception e) {
            logger.error("down error :" + directory, e);
        } finally {
            close(sftp);
        }
    }

    protected void close(ChannelSftp sftp) {
        if (sftp != null) {
            sftp.disconnect();
            sftp.exit();
        }
        if (sshSession != null) {
            sshSession.disconnect();
        }
    }

    protected String getParent(File desc) {
        return desc.getParent();
    }
    
    
    
    public String execute(final String command) {  
        String result = "";
   
        try {  
            // Create and connect session.  
            JSch jsch = new JSch();
            jsch.getSession(username, host, port);
            sshSession = jsch.getSession(username, host, port);
            sshSession.setPassword(password);
            Properties sshConfig = new Properties();
            sshConfig.put("StrictHostKeyChecking", "no");
            sshSession.setConfig(sshConfig);
            sshSession.connect();
   
            // Create and connect channel.  
            Channel channel = sshSession.openChannel("exec");  
            ((ChannelExec) channel).setCommand(command);  
   
            channel.setInputStream(null);  
            BufferedReader input = new BufferedReader(new InputStreamReader(channel  
                    .getInputStream()));  
   
            channel.connect();  
            System.out.println("The remote command is: " + command);  
   
            // Get the output of remote command.  
            String line;  
            while ((line = input.readLine()) != null) {  
                result += line + "\n";
            }  
            input.close();  
   
            // Disconnect the channel and session.  
            channel.disconnect();  
            sshSession.disconnect();  
        } catch (JSchException e) {  
            return "failed" ;
        } catch (Exception e) {  
            return "failed" ;
        }  
        return result;  
    }  

    public static void main(String[] args) {

        /**
         * @param host
         *            ip
         * @param port
         *            端口
         * @param username
         *            账号
         * @param password
         *            密码
         */
    	
    	/*
        SftpUtils sftpUtils = new SftpUtils("10.43.38.1", 22, "bitfily", "bitfily20180230");
        //sftpUtils.upload("/home/a1", new File("F:\\360Downloads\\Software\\Everything64_1.4.1.877.exe"), null);
        String result  = sftpUtils.execute("cat /home/bitfily/script/fb_test.py");
        System.out.println(result);*/
//    	SftpUtils sftpUtils = new SftpUtils("otaftp.bitfily.com", 22, "www", "admin@akiWWW2017");
//        //sftpUtils.upload("/home/a1", new File("F:\\360Downloads\\Software\\Everything64_1.4.1.877.exe"), null);
//        String result  = sftpUtils.execute("cat /data/www/ota/A1/upgradeInfo.json");
//        System.out.println(result);
//        VersionOtaVO vo = JSON.parseObject(result, VersionOtaVO.class);
//        System.out.println(vo.getVersion());
//        System.out.println(MinerVersionService.getNewVersion("A1+"));
//        System.out.println(MinerVersionService.getMinersVersion());
    	
//    	SftpUtils sftpUtils = new SftpUtils("192.168.0.62", 22, "root", "root");
        //sftpUtils.upload("/var/log", new File("F:\\常用软件\\IP-Reporter.rar"), null);
//        String result  = sftpUtils.execute("ls /tmp");
//        System.out.println(result);
//        System.out.println(sftpUtils.execute("cat /var/log/resolv.conf"));
//        System.out.println(sftpUtils.execute("sh /tmp/start_cgminer.sh 325 580 85"));
//        System.out.println(sftpUtils.execute("sh /tmp/stop_cgminer.sh"));
    	SftpUtils sftpUtils = new SftpUtils("192.168.0.69", 22, "root", "root");
    	System.out.println(sftpUtils.execute("ls /tmp"));
    	int success = sftpUtils.upload("/tmp", new File("C:\\Users\\wangjian\\Desktop\\temp\\swupdate_20191201.tar.gz"), null);
    	if(success != 1) {
    		System.out.println("上传失败");
    	}
    	System.out.println(sftpUtils.execute("chmod 755 /tmp/swupdate_20191201.tar.gz"));
    	System.out.println(sftpUtils.execute("ls /tmp"));
    	System.out.println(sftpUtils.execute("nohup system_update online /tmp/swupdate_20191201.tar.gz"));
    }
}
