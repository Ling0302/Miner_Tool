package vo;

import java.util.List;

public class SetupVO {

	private String scanTimeout;
	private String scanThreads;
	private String monitorCycle;
	private String isMonitor;
	private String monitorTime;
	private List<MinerParamVO> minerList;
	private List<IpsVO> ipsList;
	
	
	public String getMonitorTime() {
		return monitorTime;
	}
	public void setMonitorTime(String monitorTime) {
		this.monitorTime = monitorTime;
	}
	public String getIsMonitor() {
		return isMonitor;
	}
	public void setIsMonitor(String isMonitor) {
		this.isMonitor = isMonitor;
	}
	public String getMonitorCycle() {
		return monitorCycle;
	}
	public void setMonitorCycle(String monitorCycle) {
		this.monitorCycle = monitorCycle;
	}
	public List<IpsVO> getIpsList() {
		return ipsList;
	}
	public void setIpsList(List<IpsVO> ipsList) {
		this.ipsList = ipsList;
	}
	public String getScanTimeout() {
		return scanTimeout;
	}
	public void setScanTimeout(String scanTimeout) {
		this.scanTimeout = scanTimeout;
	}
	public String getScanThreads() {
		return scanThreads;
	}
	public void setScanThreads(String scanThreads) {
		this.scanThreads = scanThreads;
	}
	public List<MinerParamVO> getMinerList() {
		return minerList;
	}
	public void setMinerList(List<MinerParamVO> minerList) {
		this.minerList = minerList;
	}

	
}
