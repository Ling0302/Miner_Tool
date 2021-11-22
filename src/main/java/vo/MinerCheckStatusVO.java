package vo;

public class MinerCheckStatusVO {

	private String chipCount;// 576,
	private String chainCount;// 6,
	private String ipSeq;// 10.43.13.1,
	private boolean psuMaxIResult;//
	private boolean psuMaxPResult;//
	private String fanV;// 11.96,
	private String psuMaxP;// 2892.38,
	private String temperature2;// 51,
	private String temperature1;// 53,
	private String rejectedRate;// 8.448,
	private boolean maxSpeedResult;//
	private String ruleName;// A1矿机通用规则,
	private String hashRate;// 54360.955,
	private String psuMaxI;// 59.60,
	private String worker;// Bitfily.BTC_050,
	private String minSpeed;// 6068,
	private boolean avgHashRateResult;//
	private boolean temperature1Result;//
	private String ip;// 10.43.13.1,
	private String psuMaxV;// 48.60,
	private String maxSpeed;// 6261,
	private String minerType;// A1,
	private String url;// sz.ss.btc.com;//1800,
	private String uptime;// 55981,
	private boolean hashRateResult;//
	private boolean temperature2Result;//
	private String name;// 货架C-1,
	private String avgHashRate;// 52317.311,
	private String fanI;// 13.34,
	private boolean rejectedRateResult;//
	private String status;// 0
	private boolean psuMaxInTempResult;
	private boolean psuMaxOutTempResult;
	private String psuMaxInTemp;
	private String psuMaxOutTemp;
	private String scanStatus;//扫描状态
	private String version;//软件版本
	private String psuPower1;//电源功率1
	private String psuPower2;//电源功率2
	private String normalChipCount;//有效芯片数
	private String sn;//整机SN
	private String hashboardStatus;//休眠状态
	
	public String getHashboardStatus()
    {
        return hashboardStatus;
    }
    public void setHashboardStatus(String hashboardStatus)
    {
        this.hashboardStatus = hashboardStatus;
    }
    public String getSn()
    {
        return sn;
    }
    public void setSn(String sn)
    {
        this.sn = sn;
    }
    public String getNormalChipCount()
    {
        return normalChipCount;
    }
    public void setNormalChipCount(String normalChipCount)
    {
        this.normalChipCount = normalChipCount;
    }
    public String getPsuPower1()
    {
        return psuPower1;
    }
    public void setPsuPower1(String psuPower1)
    {
        this.psuPower1 = psuPower1;
    }
    public String getPsuPower2()
    {
        return psuPower2;
    }
    public void setPsuPower2(String psuPower2)
    {
        this.psuPower2 = psuPower2;
    }
    public String getScanStatus()
    {
        return scanStatus;
    }
    public void setScanStatus(String scanStatus)
    {
        this.scanStatus = scanStatus;
    }
    public String getVersion()
    {
        return version;
    }
    public void setVersion(String version)
    {
        this.version = version;
    }
    public boolean isPsuMaxInTempResult() {
		return psuMaxInTempResult;
	}
	public void setPsuMaxInTempResult(boolean psuMaxInTempResult) {
		this.psuMaxInTempResult = psuMaxInTempResult;
	}
	public boolean isPsuMaxOutTempResult() {
		return psuMaxOutTempResult;
	}
	public void setPsuMaxOutTempResult(boolean psuMaxOutTempResult) {
		this.psuMaxOutTempResult = psuMaxOutTempResult;
	}
	public String getPsuMaxInTemp() {
		return psuMaxInTemp;
	}
	public void setPsuMaxInTemp(String psuMaxInTemp) {
		this.psuMaxInTemp = psuMaxInTemp;
	}
	public String getPsuMaxOutTemp() {
		return psuMaxOutTemp;
	}
	public void setPsuMaxOutTemp(String psuMaxOutTemp) {
		this.psuMaxOutTemp = psuMaxOutTemp;
	}
	public String getChipCount() {
		return chipCount;
	}
	public void setChipCount(String chipCount) {
		this.chipCount = chipCount;
	}
	public String getChainCount() {
		return chainCount;
	}
	public void setChainCount(String chainCount) {
		this.chainCount = chainCount;
	}
	public String getIpSeq() {
		return ipSeq;
	}
	public void setIpSeq(String ipSeq) {
		this.ipSeq = ipSeq;
	}
	public boolean isPsuMaxIResult() {
		return psuMaxIResult;
	}
	public void setPsuMaxIResult(boolean psuMaxIResult) {
		this.psuMaxIResult = psuMaxIResult;
	}
	public boolean isPsuMaxPResult() {
		return psuMaxPResult;
	}
	public void setPsuMaxPResult(boolean psuMaxPResult) {
		this.psuMaxPResult = psuMaxPResult;
	}
	public String getFanV() {
		return fanV;
	}
	public void setFanV(String fanV) {
		this.fanV = fanV;
	}
	public String getPsuMaxP() {
		return psuMaxP;
	}
	public void setPsuMaxP(String psuMaxP) {
		this.psuMaxP = psuMaxP;
	}
	public String getTemperature2() {
		return temperature2;
	}
	public void setTemperature2(String temperature2) {
		this.temperature2 = temperature2;
	}
	public String getTemperature1() {
		return temperature1;
	}
	public void setTemperature1(String temperature1) {
		this.temperature1 = temperature1;
	}
	public String getRejectedRate() {
		return rejectedRate;
	}
	public void setRejectedRate(String rejectedRate) {
		this.rejectedRate = rejectedRate;
	}
	public boolean isMaxSpeedResult() {
		return maxSpeedResult;
	}
	public void setMaxSpeedResult(boolean maxSpeedResult) {
		this.maxSpeedResult = maxSpeedResult;
	}
	public String getRuleName() {
		return ruleName;
	}
	public void setRuleName(String ruleName) {
		this.ruleName = ruleName;
	}
	public String getHashRate() {
		return hashRate;
	}
	public void setHashRate(String hashRate) {
		this.hashRate = hashRate;
	}
	public String getPsuMaxI() {
		return psuMaxI;
	}
	public void setPsuMaxI(String psuMaxI) {
		this.psuMaxI = psuMaxI;
	}
	public String getWorker() {
		return worker;
	}
	public void setWorker(String worker) {
		this.worker = worker;
	}
	public String getMinSpeed() {
		return minSpeed;
	}
	public void setMinSpeed(String minSpeed) {
		this.minSpeed = minSpeed;
	}
	public boolean isAvgHashRateResult() {
		return avgHashRateResult;
	}
	public void setAvgHashRateResult(boolean avgHashRateResult) {
		this.avgHashRateResult = avgHashRateResult;
	}
	public boolean isTemperature1Result() {
		return temperature1Result;
	}
	public void setTemperature1Result(boolean temperature1Result) {
		this.temperature1Result = temperature1Result;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getPsuMaxV() {
		return psuMaxV;
	}
	public void setPsuMaxV(String psuMaxV) {
		this.psuMaxV = psuMaxV;
	}
	public String getMaxSpeed() {
		return maxSpeed;
	}
	public void setMaxSpeed(String maxSpeed) {
		this.maxSpeed = maxSpeed;
	}
	public String getMinerType() {
		return minerType;
	}
	public void setMinerType(String minerType) {
		this.minerType = minerType;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getUptime() {
		return uptime;
	}
	public void setUptime(String uptime) {
		this.uptime = uptime;
	}
	public boolean isHashRateResult() {
		return hashRateResult;
	}
	public void setHashRateResult(boolean hashRateResult) {
		this.hashRateResult = hashRateResult;
	}
	public boolean isTemperature2Result() {
		return temperature2Result;
	}
	public void setTemperature2Result(boolean temperature2Result) {
		this.temperature2Result = temperature2Result;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAvgHashRate() {
		return avgHashRate;
	}
	public void setAvgHashRate(String avgHashRate) {
		this.avgHashRate = avgHashRate;
	}
	public String getFanI() {
		return fanI;
	}
	public void setFanI(String fanI) {
		this.fanI = fanI;
	}
	public boolean isRejectedRateResult() {
		return rejectedRateResult;
	}
	public void setRejectedRateResult(boolean rejectedRateResult) {
		this.rejectedRateResult = rejectedRateResult;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
}
