package vo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import common.LangConfig;
import utils.CgminerUtil;

public class MinerScanVO {

	private int sequence;//序号
	private String ip;//ip地址
	private String status;//状态
	private String minerType;//矿机类型
	private String binType;//矿机BIN
	private String poolUrl;//矿池
	private String worker;//矿工
	private String nowCp;//实时算力(1min算力)
	private String avgCp;//平均算力(1h算力)
	private String point;//位置
	private String firmwareVersion;//固件版本
	private String psuVersion;//电源版本
	private String hardwareVersion; //硬件版本
	private String softVersion;//软件版本(cgminer)
	private String networkType;//网络类型（static、dhcp)
	private String macAddress;//矿机mac地址
	private String temperature;//温度(界面示例65,66,67,68)
	private String fanSpeed;//风扇转速(界面示例3000,3500,3200)
	private String sn;//矿机sn号（置空）
	private boolean isMiner;//是否为矿机
	private int code;//0-异常，1-正常
	private String uptime;//运行时长
	private String fanDuty;//风扇占空比
	private String devFreq;//频率
	private String chipVolt;//芯片电压
	private String chipCount;//芯片数量
	private String volt;//电压
	
		
	public String getFanDuty() {
		return fanDuty;
	}
	public void setFanDuty(String fanDuty) {
		this.fanDuty = fanDuty;
	}
	public String getDevFreq() {
		return devFreq;
	}
	public void setDevFreq(String devFreq) {
		this.devFreq = devFreq;
	}
	public String getChipVolt() {
		return chipVolt;
	}
	public void setChipVolt(String chipVolt) {
		this.chipVolt = chipVolt;
	}
	public String getVolt() {
		return volt;
	}
	public void setVolt(String volt) {
		this.volt = volt;
	}
	public String getUptime() {
		return uptime;
	}
	public void setUptime(String uptime) {
		this.uptime = uptime;
	}
	public void setMiner(boolean isMiner) {
		this.isMiner = isMiner;
	}
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public boolean getIsMiner() {
		return isMiner;
	}
	public void setIsMiner(boolean isMiner) {
		this.isMiner = isMiner;
	}
	public int getSequence() {
		return sequence;
	}
	public void setSequence(int sequence) {
		this.sequence = sequence;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getMinerType() {
		return minerType;
	}
	public void setMinerType(String minerType) {
		this.minerType = minerType;
	}
	public String getBinType() {
		return binType;
	}
	public void setBinType(String binType) {
		this.binType = binType;
	}
	public String getPoolUrl() {
		return poolUrl;
	}
	public void setPoolUrl(String poolUrl) {
		this.poolUrl = poolUrl;
	}
	public String getWorker() {
		return worker;
	}
	public void setWorker(String worker) {
		this.worker = worker;
	}
	public String getChipCount() {
		return chipCount;
	}
	public void setChipCount(String chipCount) {
		this.chipCount = chipCount;
	}
	public String getNowCp() {
		return nowCp;
	}
	public void setNowCp(String nowCp) {
		this.nowCp = nowCp;
	}
	public String getAvgCp() {
		return avgCp;
	}
	public void setAvgCp(String avgCp) {
		this.avgCp = avgCp;
	}
	public String getPoint() {
		return point;
	}
	public void setPoint(String point) {
		this.point = point;
	}
	public String getFirmwareVersion() {
		return firmwareVersion;
	}
	public void setFirmwareVersion(String firmwareVersion) {
		this.firmwareVersion = firmwareVersion;
	}
	public String getPsuVersion() {
		return psuVersion;
	}
	public void setPsuVersion(String psuVersion) {
		this.psuVersion = psuVersion;
	}
	public String getHardwareVersion() {
		return hardwareVersion;
	}
	public void setHardwareVersion(String hardwareVersion) {
		this.hardwareVersion = hardwareVersion;
	}
	public String getSoftVersion() {
		return softVersion;
	}
	public void setSoftVersion(String softVersion) {
		this.softVersion = softVersion;
	}
	public String getNetworkType() {
		return networkType;
	}
	public void setNetworkType(String networkType) {
		this.networkType = networkType;
	}
	public String getMacAddress() {
		return macAddress;
	}
	public void setMacAddress(String macAddress) {
		this.macAddress = macAddress;
	}
	public String getTemperature() {
		return temperature;
	}
	public void setTemperature(String temperature) {
		this.temperature = temperature;
	}
	public String getFanSpeed() {
		return fanSpeed;
	}
	public void setFanSpeed(String fanSpeed) {
		this.fanSpeed = fanSpeed;
	}
	public String getSn() {
		return sn;
	}
	public void setSn(String sn) {
		this.sn = sn;
	}
	
	public void getCgminerVO(MinerScanVO minerScanVO) {
		String cgminerResult = CgminerUtil.call(minerScanVO.getIp(),4028,"pools+stats", "");
		//System.out.println(cgminerResult);
		if(cgminerResult != null) {
			minerScanVO.setIsMiner(true);
			minerScanVO.setCode(2);
			if(minerScanVO.getMinerType() == null || "".equals(minerScanVO.getMinerType())) {
				minerScanVO.setMinerType("unknown");
			}			
			minerScanVO.setStatus(minerScanVO.getStatus());
			try {
				//解析cgminer-socketAPI-json
				JSONObject jol = JSON.parseObject(cgminerResult);
				JSONArray poolsArray = jol.getJSONArray("pools");
				JSONObject jol1 = poolsArray.getJSONObject(0);
				JSONArray array1 = jol1.getJSONArray("STATUS");
				JSONObject jol2 = (JSONObject)array1.get(0);
				String softVersion = jol2.getString("Description");
				softVersion = softVersion.replace("cgminer", "").trim();
				minerScanVO.setSoftVersion(softVersion);
				JSONArray array2 = jol1.getJSONArray("POOLS");
				for(int i=0;i<array2.size();i++) {
					JSONObject jol3 = array2.getJSONObject(i);
					boolean stratumActive = jol3.getBooleanValue("Stratum Active");
					if(stratumActive) {
						minerScanVO.setPoolUrl(jol3.getString("Stratum URL"));
						minerScanVO.setWorker(jol3.getString("User"));
						break;
					}
				}
				JSONArray statsArr = jol.getJSONArray("stats");
				JSONObject statusJol = statsArr.getJSONObject(0);
				JSONArray statsArray = statusJol.getJSONArray("STATS");
				int index = 0;
				String temp = "";//hash板温度
				String fanSpeed = "";//风扇转速
				String duty="";//风扇占空比转速
				String freq="";//频率
				String cVolt="";//芯片电压
				String powerVolt="";//电压输出
				Double cp = 0.0;
				Double cpAv = 0.0;
				for(int i=0;i<statsArray.size();i++) {
					JSONObject chipJol = statsArray.getJSONObject(i);
					String chainId = chipJol.getString("Chain ID");
					Float fanDuty = chipJol.getFloat("Fan Duty");
					Float devFreq = chipJol.getFloat("Device Freq");
					Float chipVolt = chipJol.getFloat("Voltage Avg");
					Float volt = chipJol.getFloat("Voltage");					
					if(chainId != null && !"".equals(chainId)) {							
						String t = chipJol.getString("Temp Avg");
						if(index == 0) {
							duty += fanDuty.toString();
							if(devFreq > 0) {
								freq = devFreq.toString();
							}							
							cVolt += String.format("%.2f", chipVolt);
							if(volt > 0) {
								powerVolt = volt.toString();
							}							
							temp += t;
							for(int j=0;j<10;j++) {
								String fanSpeedKey = "Fan"+j+" Speed";
								String fanSpeedValue = chipJol.getString(fanSpeedKey);
								if(fanSpeedValue == null) {
									break;
								}else {
									if(j == 0) {
										fanSpeed += fanSpeedValue;
									}else {
										fanSpeed += "|" + fanSpeedValue;
									}
									
								}
							}
						}else {
							temp += "|" + t;
							if(devFreq > 0) {
								freq = devFreq.toString();
							}
							duty += "|" +fanDuty.toString();
							cVolt += "|" + String.format("%.2f", chipVolt);
							if(volt > 0) {
								powerVolt =  volt.toString();
							}
						}
						Float chipCp = chipJol.getFloat("MHS 5s") == null ? 0.0f : chipJol.getFloat("MHS 5s");
						Float chipCpAv = chipJol.getFloat("MHS av") == null ? 0.0f : chipJol.getFloat("MHS av");
						cp += chipCp;
						cpAv += chipCpAv;
						index ++;
					}
				}
				minerScanVO.setTemperature(temp);
				minerScanVO.setFanSpeed(fanSpeed);
				minerScanVO.setFanDuty(duty);
				minerScanVO.setDevFreq(freq);
				minerScanVO.setChipVolt(cVolt);
				minerScanVO.setVolt(powerVolt);
				minerScanVO.setNowCp(String.format("%.2f", cp/1000/1000));
				minerScanVO.setAvgCp(String.format("%.2f", cpAv/1000/1000));
				//logger.info("ip-[{}]cgminer scan success！",ip);
			}catch(Exception e2) {
				//minerScanVO.setIsMiner(false);
				//logger.error("Exeptions:[{}]",e2);
				e2.printStackTrace();
			}			
		}else {				
			//minerScanVO.setStatus(LangConfig.getKey("app.message.connectRefuse"));
			minerScanVO.setCode(0);
			//minerScanVO.setIsMiner(false);
			//logger.info("ip-[{}] is not a miner！",ip);
		}		
		//System.out.println(JSON.toJSONString(minerScanVO));
	}
}
