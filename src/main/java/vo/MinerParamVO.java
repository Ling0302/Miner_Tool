package vo;

public class MinerParamVO {
	
	private String type;//矿机类别
	private String username;//矿机用户名
	private String password;//矿机密码
	private String power;//功耗
	private String cpWarn;//算力低报警值
	private String maxVoltage;//最大电压
	private String maxCurrent;//最大电流
	private String minFanSpeed;//风扇最低转速
	private String maxFanSpeed;//风扇最高转速
	private String maxRefuseRate;//最大拒绝率
	private String maxPsuInOutTemprature;//进出最高温
	private String maxTemprature;//温度上限
	private String minTemprature;//温度下限
	private String scanStatus;//	扫频状态
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getPower() {
		return power;
	}
	public void setPower(String power) {
		this.power = power;
	}
	public String getCpWarn() {
		return cpWarn;
	}
	public void setCpWarn(String cpWarn) {
		this.cpWarn = cpWarn;
	}
	public String getMaxVoltage() {
		return maxVoltage;
	}
	public void setMaxVoltage(String maxVoltage) {
		this.maxVoltage = maxVoltage;
	}
	public String getMaxCurrent() {
		return maxCurrent;
	}
	public void setMaxCurrent(String maxCurrent) {
		this.maxCurrent = maxCurrent;
	}
	public String getMinFanSpeed() {
		return minFanSpeed;
	}
	public void setMinFanSpeed(String minFanSpeed) {
		this.minFanSpeed = minFanSpeed;
	}
	public String getMaxFanSpeed() {
		return maxFanSpeed;
	}
	public void setMaxFanSpeed(String maxFanSpeed) {
		this.maxFanSpeed = maxFanSpeed;
	}
	public String getMaxRefuseRate() {
		return maxRefuseRate;
	}
	public void setMaxRefuseRate(String maxRefuseRate) {
		this.maxRefuseRate = maxRefuseRate;
	}
	public String getMaxPsuInOutTemprature() {
		return maxPsuInOutTemprature;
	}
	public void setMaxPsuInOutTemprature(String maxPsuInOutTemprature) {
		this.maxPsuInOutTemprature = maxPsuInOutTemprature;
	}
	public String getMaxTemprature() {
		return maxTemprature;
	}
	public void setMaxTemprature(String maxTemprature) {
		this.maxTemprature = maxTemprature;
	}
	public String getMinTemprature() {
		return minTemprature;
	}
	public void setMinTemprature(String minTemprature) {
		this.minTemprature = minTemprature;
	}
	public String getScanStatus() {
		return scanStatus;
	}
	public void setScanStatus(String scanStatus) {
		this.scanStatus = scanStatus;
	}
    
    
}
