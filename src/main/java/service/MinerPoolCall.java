package service;

import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.TableItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;

import utils.HttpRequestUtils;
import vo.PoolSettingVO;

public class MinerPoolCall implements Callable<String>{
	
	private static final Logger logger = LoggerFactory.getLogger(MinerPoolCall.class);
	private CountDownLatch countDownLatch;
	private String ip;
	private PoolSettingVO poolVO;
	
	public MinerPoolCall(String ip,PoolSettingVO poolVO,CountDownLatch countDownLatch) {
		this.ip = ip;
		this.countDownLatch = countDownLatch;
		this.poolVO = poolVO;
	}

	@Override
	public String call() throws Exception {
		String result = null;
		try {
			result = configPoolV2(ip,poolVO);
		}catch(Exception e){
			logger.error("connect error!{}",e);
			//e.printStackTrace();
		}finally
        {
            countDownLatch.countDown();
        }
		return result;
	}
	
	private String configPool(String ip,PoolSettingVO poolVO) {
		String result = HttpRequestUtils.post(ip, "/index.php/app/api?command=save_pools", null);
		return result;
	}
	
	private String configPoolV2(String ip,PoolSettingVO poolVO) {
		
		
		return "ok";
	}

}
