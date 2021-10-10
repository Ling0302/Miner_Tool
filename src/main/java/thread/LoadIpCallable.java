package thread;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

import org.eclipse.swt.widgets.TableItem;

import utils.HttpRequestUtils;

public class LoadIpCallable implements Callable<Map<String,Object>>{
	
	private String ip;
	private TableItem item;
	
	public LoadIpCallable(String ip,TableItem item) {
		this.ip = ip;
		this.item = item;
	}

	@Override
	public Map<String,Object> call() throws Exception {	
		Map<String,Object> map = new HashMap<String,Object>();
		String result = HttpRequestUtils.get(ip, "/status", null);
		map.put("result", result);
		map.put("item", item);
		return map;
	}

}
