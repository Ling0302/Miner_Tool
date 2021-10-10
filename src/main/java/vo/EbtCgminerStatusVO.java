package vo;
/**
 * 服务器状态
 * */
public class EbtCgminerStatusVO {

	private String currentpooltime;// 5;//14;//20,
	private String rejected;// 5,
	private String findblock;// 0,
	private String accepted;// 1640,
	private String averfigure;// 17.53T,
	private String currentaccident;// 65536.00000000,
	private String getworks;// 734,
	private String fiveavg;// 18.64T,
	private String harderrornumber;// 0.0002,
	private String worktime;// 5;//45;//43,
	private String fivesecfigure;// 18.53T,
	private String cgminerstatus;// stratum+tcpstratum.btc.top:8888
	public String getCurrentpooltime() {
		return currentpooltime;
	}
	public void setCurrentpooltime(String currentpooltime) {
		this.currentpooltime = currentpooltime;
	}
	public String getRejected() {
		return rejected;
	}
	public void setRejected(String rejected) {
		this.rejected = rejected;
	}
	public String getFindblock() {
		return findblock;
	}
	public void setFindblock(String findblock) {
		this.findblock = findblock;
	}
	public String getAccepted() {
		return accepted;
	}
	public void setAccepted(String accepted) {
		this.accepted = accepted;
	}
	public String getAverfigure() {
		return averfigure;
	}
	public void setAverfigure(String averfigure) {
		this.averfigure = averfigure;
	}
	public String getCurrentaccident() {
		return currentaccident;
	}
	public void setCurrentaccident(String currentaccident) {
		this.currentaccident = currentaccident;
	}
	public String getGetworks() {
		return getworks;
	}
	public void setGetworks(String getworks) {
		this.getworks = getworks;
	}
	public String getFiveavg() {
		return fiveavg;
	}
	public void setFiveavg(String fiveavg) {
		this.fiveavg = fiveavg;
	}
	public String getHarderrornumber() {
		return harderrornumber;
	}
	public void setHarderrornumber(String harderrornumber) {
		this.harderrornumber = harderrornumber;
	}
	public String getWorktime() {
		return worktime;
	}
	public void setWorktime(String worktime) {
		this.worktime = worktime;
	}
	public String getFivesecfigure() {
		return fivesecfigure;
	}
	public void setFivesecfigure(String fivesecfigure) {
		this.fivesecfigure = fivesecfigure;
	}
	public String getCgminerstatus() {
		return cgminerstatus;
	}
	public void setCgminerstatus(String cgminerstatus) {
		this.cgminerstatus = cgminerstatus;
	}
	
}
