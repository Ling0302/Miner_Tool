package service;

import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;

import org.eclipse.swt.widgets.TableItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import utils.HttpRequestUtils;

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
			result = rebootMiner(item);
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
		String result = HttpRequestUtils.post(item.getText(0), "/index.php/app/api?command=reboot", null);
		return result;
	}

}
