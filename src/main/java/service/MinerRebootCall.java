package service;

import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;

import org.eclipse.swt.widgets.TableItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import utils.HttpRequestUtils;
import utils.SftpUtils;

public class MinerRebootCall implements Callable<String>{
	
	private static final Logger logger = LoggerFactory.getLogger(MinerRebootCall.class);
	private CountDownLatch countDownLatch;
	private TableItem item;
	
	public MinerRebootCall(TableItem item,CountDownLatch countDownLatch) {
		this.countDownLatch = countDownLatch;
		this.item = item;
	}

	@Override
	public String call() throws Exception {
		String result = null;
		try {
			result = rebootMinerV2(item);
		}catch(Exception e) {
			logger.error("reboot error!{}",e);
			result = e.getMessage();
		}finally
        {
            countDownLatch.countDown();
        }
		return result;
	}
	
	public String rebootMiner(TableItem item) {
		String result = HttpRequestUtils.post(item.getText(1), "/index.php/app/api?command=reboot", null);
		return result;
	}
	
	public String rebootMinerV2(TableItem item) {
		
		String ip = item.getText(1).trim();		
    	SftpUtils sftpUtils = new SftpUtils(ip, 22, "root", "F9Miner1234");
		sftpUtils.execute("sudo reboot");
		
		return "ok";
	}

}
