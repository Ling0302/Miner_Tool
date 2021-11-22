package service;

import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import org.eclipse.swt.widgets.TableItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.SftpUtils;

public class MinerLedCall implements Callable<String>{
	
	private static final Logger logger = LoggerFactory.getLogger(MinerRebootCall.class);
	private CountDownLatch countDownLatch;
	private TableItem item;
	
	public MinerLedCall(TableItem item,CountDownLatch countDownLatch) {
		this.countDownLatch = countDownLatch;
		this.item = item;
	}

	@Override
	public String call() throws Exception {
		String result = null;
		try {
			result = lightMiner(item);
		}catch(Exception e) {
			logger.error("reboot error!{}",e);
			result = e.getMessage();
		}finally
        {
            countDownLatch.countDown();
        }
		return result;
	}
	
	public String lightMiner(TableItem item) {
		String ip = item.getText(1).trim();		
    	try {
    		SftpUtils sftpUtils = new SftpUtils(ip, 22, "root", "F9Miner1234");
        	sftpUtils.execute("sudo led_ctl red off");
			Thread.sleep(1000);
			sftpUtils.execute("sudo led_ctl red on");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
    	return "点亮红灯ok";
	}

}
