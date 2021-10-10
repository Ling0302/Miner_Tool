package vo;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSON;

public class PoolSettingVO {

	private Boolean save_miner_pools;
	private List<String> pool_url;
	private List<String> pool_username;
	private List<String> pool_password;
	
	public Boolean getSave_miner_pools() {
		return save_miner_pools;
	}
	public void setSave_miner_pools(Boolean save_miner_pools) {
		this.save_miner_pools = save_miner_pools;
	}
	public List<String> getPool_url() {
		return pool_url;
	}
	public void setPool_url(List<String> pool_url) {
		this.pool_url = pool_url;
	}	
	public List<String> getPool_username() {
		return pool_username;
	}
	public void setPool_username(List<String> pool_username) {
		this.pool_username = pool_username;
	}
	public List<String> getPool_password() {
		return pool_password;
	}
	public void setPool_password(List<String> pool_password) {
		this.pool_password = pool_password;
	}
	
	public static void main(String args[]) {
		PoolSettingVO vo = new PoolSettingVO();
		List<String> pool_url = new ArrayList<String>();
		List<String> pool_username = new ArrayList<String>();
		List<String> pool_password = new ArrayList<String>();
		pool_url.add("stratum+tcp://btc.ss.poolin.com:443");
		pool_url.add("stratum+tls://btc.ss.poolin.com:5222");
		pool_url.add("stratum+tcp://btc.ss.poolin.com:1883");
		pool_username.add("silva555.x01");
		pool_username.add("silva555.x02");
		pool_username.add("silva555.x03");
		pool_password.add("123");
		pool_password.add("123");
		pool_password.add("123");
		vo.setSave_miner_pools(true);
		vo.setPool_url(pool_url);
		vo.setPool_username(pool_username);
		vo.setPool_password(pool_password);
		System.out.println(JSON.toJSONString(vo));
	}
	
}
