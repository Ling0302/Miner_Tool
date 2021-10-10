package service;

import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;

import utils.HttpRequestUtils;

public class MinerDiscoveryCall implements Callable<String>{
	
	private String ip;
	private CountDownLatch countDownLatch;
	
	public MinerDiscoveryCall(String ip,CountDownLatch countDownLatch) {
		this.ip = ip;
		this.countDownLatch = countDownLatch;
	}

	@Override
	public String call() throws Exception {
		String result = null;
		try {
			result = HttpRequestUtils.get(ip, "/index.php/app/api?command=miner_info", null);	
		}finally
        {
            countDownLatch.countDown();
        }
		return result;
	}

}
