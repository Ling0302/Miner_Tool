package service;

import java.io.File;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;

import org.eclipse.swt.widgets.TableItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import app.MinerCheckApp;
import utils.SftpUtils;

public class UpgradeCall implements Callable<String>{
	
	private static final Logger logger = LoggerFactory.getLogger(UpgradeCall.class);
	private CountDownLatch countDownLatch;
	private TableItem item;
	
	public UpgradeCall(TableItem item,CountDownLatch countDownLatch) {
		this.item = item;
		this.countDownLatch = countDownLatch;
	}
	
	@Override
	public String call() throws Exception {
		String result = null;
		try {
			result = upgrade(item);
		}catch(Exception e) {
			logger.error("upgrade error!{}",e);
			result = e.getMessage();
		}finally
        {
            countDownLatch.countDown();
        }
		return result;
	}
	
	public String upgrade(TableItem item) {
		String filename = MinerCheckApp.filePath;
		String user = MinerCheckApp.lblNewLabel_20.getText();
		String pass = MinerCheckApp.lblNewLabel_21.getText();
		String ip = item.getText(0);
		String minerType = item.getText(3);
		String version = item.getText(4);
		//校验
		SftpUtils sftpUtils = new SftpUtils(ip, 22, user, pass);
		int success = sftpUtils.upload("/tmp", new File(filename), null);
		if(success != 1) {
			return "upload file fail!";
		}
		sftpUtils.execute("chmod 755 /tmp/" + filename);
//		sftpUtils.execute("nohup system_update online /tmp/" + filename);
		return "upgrade success!";
	}

}
