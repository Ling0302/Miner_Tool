package service;

import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import common.LangConfig;
import dao.PointDao;
import utils.CgminerUtil;
import utils.HttpRequestUtils;
import vo.MinerScanVO;

public class MinerScanCall implements Callable<MinerScanVO>{
	
	private static final Logger logger = LoggerFactory.getLogger(MinerScanCall.class);
	
	private String ip;
	private CountDownLatch countDownLatch;
	
	public MinerScanCall(String ip,CountDownLatch countDownLatch) {
		this.ip = ip;
		this.countDownLatch = countDownLatch;
	}

	@Override
	public MinerScanVO call() throws Exception {
		MinerScanVO minerScanVO = null;
		try {
			minerScanVO = requestRemoteApi(ip);
		}catch(Exception e){
			logger.error("connect error!{}",e);
			//e.printStackTrace();
		}finally
        {
            countDownLatch.countDown();
        }
		return minerScanVO;
	}
	
	private MinerScanVO requestRemoteApi(String ip) {
		MinerScanVO minerScanVO = new MinerScanVO();
		minerScanVO.setIp(ip);
		String result = HttpRequestUtils.get(ip, "/index.php/app/api?command=miner_info", null);
		try {
			JSONObject jol = JSON.parseObject(result);
			if("".equals(jol.getString("mac"))) {
			//if("".equals(result) || result == null) {
				minerScanVO.setStatus(LangConfig.getKey("app.message.timeout"));
				minerScanVO.setCode(0);
				minerScanVO.setIsMiner(false);
				return minerScanVO;
			}
			minerScanVO.setIsMiner(true);
			//JSONObject jol = JSON.parseObject(result);
			//解析json结构			
			logger.info("ip [{}] connect message:[{}]",ip,JSON.toJSONString(jol));
			minerScanVO.setMinerType(jol.getString("model"));
			minerScanVO.setBinType(jol.getString("bin"));
			minerScanVO.setNetworkType(jol.getString("network_type"));
			minerScanVO.setMacAddress(jol.getString("mac"));
			minerScanVO.setFirmwareVersion(jol.getString("firmware_version"));
			minerScanVO.setUptime(Integer.parseInt(jol.getString("uptime"))/60 + "");
			minerScanVO.setStatus(jol.getString("status"));
			minerScanVO.setPoint(PointDao.findPoint(jol.getString("mac")));			
			//minerScanVO.setIsMiner(true);
			minerScanVO.setCode(1);
			//minerScanVO.setSequence(Integer.parseInt(ip.substring(0, ip.lastIndexOf("."))));
			minerScanVO.getCgminerVO(minerScanVO);
		}catch(Exception e) {	
			minerScanVO.getCgminerVO(minerScanVO);
			minerScanVO.setIsMiner(false);
//			String cgminerResult = CgminerUtil.call(ip,4028,"pools+stats", "");
//			logger.info("cgminer-pools+stats:{}",cgminerResult);
//			if(cgminerResult != null) {
//				minerScanVO.setIsMiner(true);
//				minerScanVO.setCode(2);
//				minerScanVO.setMinerType("unknown");
//				minerScanVO.setStatus("running");
//				try {
//					//解析cgminer-socketAPI-json
//					JSONObject jol = JSON.parseObject(cgminerResult);
//					JSONObject jol1 = jol.getJSONObject("pools");
//					JSONArray array1 = jol1.getJSONArray("STATUS");
//					JSONObject jol2 = (JSONObject)array1.get(0);
//					String softVersion = jol2.getString("Description");
//					softVersion = softVersion.replace("cgminer", "").trim();
//					minerScanVO.setSoftVersion(softVersion);
//					JSONArray array2 = jol1.getJSONArray("POOLS");
//					for(int i=0;i<array2.size();i++) {
//						JSONObject jol3 = array2.getJSONObject(i);
//						boolean stratumActive = jol3.getBooleanValue("Stratum Active");
//						if(stratumActive) {
//							minerScanVO.setPoolUrl(jol3.getString("Stratum URL"));
//							minerScanVO.setWorker(jol3.getString("User"));
//							break;
//						}
//					}
//					JSONObject statusJol = jol.getJSONObject("stats");
//					JSONArray statsArray = statusJol.getJSONArray("STATS");
//					int index = 0;
//					String temp = "";//hash板温度
//					String fanSpeed = "";//风扇转速
//					Double cp = 0.0;
//					Double cpAv = 0.0;
//					for(int i=0;i<statsArray.size();i++) {
//						JSONObject chipJol = statsArray.getJSONObject(i);
//						String chainId = chipJol.getString("Chain ID");
//						if(chainId != null && !"".equals(chainId)) {							
//							String t = chipJol.getString("Temp Avg");
//							if(index == 0) {
//								temp += t;
//								for(int j=0;j<10;j++) {
//									String fanSpeedKey = "Fan"+j+" Speed";
//									String fanSpeedValue = chipJol.getString(fanSpeedKey);
//									if(fanSpeedValue == null) {
//										break;
//									}else {
//										if(j == 0) {
//											fanSpeed += fanSpeedValue;
//										}else {
//											fanSpeed += "|" + fanSpeedValue;
//										}
//										
//									}
//								}
//							}else {
//								temp += "|" + t;
//							}
//							Float chipCp = chipJol.getFloat("MHS 5s") == null ? 0.0f : chipJol.getFloat("MHS 5s");
//							Float chipCpAv = chipJol.getFloat("MHS av") == null ? 0.0f : chipJol.getFloat("MHS av");
//							cp += chipCp;
//							cpAv += chipCpAv;
//							index ++;
//						}
//					}
//					minerScanVO.setTemperature(temp);
//					minerScanVO.setFanSpeed(fanSpeed);
//					minerScanVO.setNowCp(String.format("%.2f", cp/1000/1000));
//					minerScanVO.setAvgCp(String.format("%.2f", cpAv/1000/1000));
//					
//					logger.info("ip-[{}]cgminer scan success！",ip);
//				}catch(Exception e2) {
//					minerScanVO.setIsMiner(false);
//					logger.error("Exeptions:[{}]",e2);
//					//e2.printStackTrace();
//				}
//			}else {				
//				minerScanVO.setStatus(LangConfig.getKey("app.message.connectRefuse"));
//				minerScanVO.setCode(0);
//				minerScanVO.setIsMiner(false);
//				logger.info("ip-[{}] is not a miner！",ip);
//			}			
		}
		return minerScanVO;
	}
	
	

}
