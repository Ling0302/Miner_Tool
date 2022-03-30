package service;

import java.io.File;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.SftpUtils;

public class UpgradeCallV2 implements Callable<String>{
	
	private static final Logger logger = LoggerFactory.getLogger(UpgradeCallV2.class);
	private CountDownLatch countDownLatch;
	private String ip;
	
	public UpgradeCallV2(String ip,CountDownLatch countDownLatch) {
		this.ip = ip;
		this.countDownLatch = countDownLatch;
	}
	
	@Override
	public String call() throws Exception {
		String result = null;
		try {
			result = upgrade(ip);
		}catch(Exception e) {
			logger.error("upgrade error!{}",e);
			result = e.getMessage();
		}finally
        {
            countDownLatch.countDown();
        }
		return result;
	}
	
	public String upgrade(String ip) {
		String filename = "C:\\swupdate_40_20220102.tar.gz";
		String user = "root";
		String pass = "F9Miner1234";
		
		SftpUtils sftpUtils = new SftpUtils(ip, 22, user, pass);
		int success = sftpUtils.upload("/tmp", new File(filename), null);
		if(success != 1) {
			return "upload file fail!";
		}
		sftpUtils.execute("chmod 755 /tmp/" + filename);
        sftpUtils.execute("nohup sudo system_update online /tmp/" + filename + "> /tmp/up.log 2>&1");

		return "upgrade success!";
	}

}
