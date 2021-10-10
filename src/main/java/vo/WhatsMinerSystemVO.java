package vo;

public class WhatsMinerSystemVO {

	private String model;
	private String hostName;
	private String firmwareVersion;
	private String kernelVersion;
	private String cgminerVersion;
	private String localTime;
	private String upTime;
	private String loadAverage;
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public String getHostName() {
		return hostName;
	}
	public void setHostName(String hostName) {
		this.hostName = hostName;
	}
	public String getFirmwareVersion() {
		return firmwareVersion;
	}
	public void setFirmwareVersion(String firmwareVersion) {
		this.firmwareVersion = firmwareVersion;
	}
	public String getKernelVersion() {
		return kernelVersion;
	}
	public void setKernelVersion(String kernelVersion) {
		this.kernelVersion = kernelVersion;
	}
	public String getCgminerVersion() {
		return cgminerVersion;
	}
	public void setCgminerVersion(String cgminerVersion) {
		this.cgminerVersion = cgminerVersion;
	}
	public String getLocalTime() {
		return localTime;
	}
	public void setLocalTime(String localTime) {
		this.localTime = localTime;
	}
	public String getUpTime() {
		return upTime;
	}
	public void setUpTime(String upTime) {
		this.upTime = upTime;
	}
	public String getLoadAverage() {
		return loadAverage;
	}
	public void setLoadAverage(String loadAverage) {
		this.loadAverage = loadAverage;
	}
	
}
