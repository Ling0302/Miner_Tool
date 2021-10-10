package vo;

public class PoolConfigVO {
	
	private String isSelsect;//是否选中
	private String poolName;//矿池名称
	private String poolUrl;//矿池地址
	private String poolUser;//矿池用户名
	private String poolPwd;//矿池密码
	private String option;// 1-IP,2-不改变,3-清空
	public String getIsSelsect() {
		return isSelsect;
	}
	public void setIsSelsect(String isSelsect) {
		this.isSelsect = isSelsect;
	}
	public String getPoolName() {
		return poolName;
	}
	public void setPoolName(String poolName) {
		this.poolName = poolName;
	}
	public String getPoolUrl() {
		return poolUrl;
	}
	public void setPoolUrl(String poolUrl) {
		this.poolUrl = poolUrl;
	}
	public String getPoolUser() {
		return poolUser;
	}
	public void setPoolUser(String poolUser) {
		this.poolUser = poolUser;
	}
	public String getPoolPwd() {
		return poolPwd;
	}
	public void setPoolPwd(String poolPwd) {
		this.poolPwd = poolPwd;
	}
	public String getOption() {
		return option;
	}
	public void setOption(String option) {
		this.option = option;
	}
	
	
}
