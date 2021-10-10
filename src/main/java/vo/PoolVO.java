package vo;

public class PoolVO {

	private String ischeckd;
	private String poolUrl;
	private String poolPort;
	private String poolUser;
	private String poolPwd;
	private String name;
	private Integer minerLast;//矿机名后缀0-IP，1-不改变，2-清空
	
	public Integer getMinerLast() {
		return minerLast;
	}
	public void setMinerLast(Integer minerLast) {
		this.minerLast = minerLast;
	}
	public String getPoolPort() {
		return poolPort;
	}
	public void setPoolPort(String poolPort) {
		this.poolPort = poolPort;
	}
	public String getIscheckd() {
		return ischeckd;
	}
	public void setIscheckd(String ischeckd) {
		this.ischeckd = ischeckd;
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
}
