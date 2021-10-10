package service;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import cn.wanghaomiao.xpath.model.JXDocument;
import utils.KeepSessionWithOneHttpclient;
import vo.PoolApiVO;
import vo.WhatsMinerDevicesVO;
import vo.WhatsMinerPoolVO;
import vo.WhatsMinerSummaryVO;
import vo.WhatsMinerSystemVO;
import vo.WhatsMinerVO;

public class WhatsMinerService {

	public static WhatsMinerVO spiderWhatsMiner(String ip) {
		//登录的地址以及登录操作参数  
        String loginUrl = "http://"+ip+"/cgi-bin/luci";  
        //登录的相关参数以及登录后操作参数  
        List<NameValuePair> loginParams = new ArrayList<NameValuePair>();   
        loginParams.add(new BasicNameValuePair("luci_username", "root"));  
        loginParams.add(new BasicNameValuePair("luci_password", "root")); 
        loginParams.add(new BasicNameValuePair("content", "Authorization Required"));
        //登录后操作地址以及登录后操作参数  
        Map<String,List<NameValuePair>> map = new HashMap<String,List<NameValuePair>>(); 
        List<NameValuePair> queryParams = new ArrayList<NameValuePair>(); 
        //1.cgminerstatus
        String cgminerStatusUrl = "http://"+ip+"/cgi-bin/luci/admin/status/cgminerstatus";
        map.put(cgminerStatusUrl, queryParams);   
        //2.overview
        String overviewStatusUrl = "http://"+ip+"/cgi-bin/luci/admin/status/overview";
        map.put(overviewStatusUrl, queryParams);   
        Map<String,String> returnMap = KeepSessionWithOneHttpclient.doPostWithOneHttpclient(loginUrl, loginParams, map,false,"WhatsMiner");
		WhatsMinerVO miner = mapFormatMiner(map,returnMap);
		return miner;
	}
	
	public static WhatsMinerVO mapFormatMiner(Map<String,List<NameValuePair>> map,Map<String,String> returnMap) {
		if(returnMap == null || returnMap.isEmpty()) {
			return null;
		}
		List<WhatsMinerPoolVO> poolList = null;
		WhatsMinerSummaryVO summaryVO = null;
		WhatsMinerSystemVO systemVO = null;
		WhatsMinerDevicesVO devicesVO = null;
		try {
			for (Map.Entry<String,List<NameValuePair>> entry : map.entrySet()) { 
				String key = entry.getKey();
				
				if(key.indexOf("/admin/status/cgminerstatus") != -1) {
					System.out.println("key:"+key);
					String rs1 = returnMap.get(key);
					Document dom1 = Jsoup.parse(rs1);
					summaryVO = new WhatsMinerSummaryVO();
					summaryVO.setmElapsed(dom1.getElementById("cbid.table.1.elapsed").attr("value"));
					summaryVO.setmGHSav(dom1.getElementById("cbid.table.1.mhsav").attr("value"));
					summaryVO.setmAccepted(dom1.getElementById("cbid.table.1.accepted").attr("value"));
					summaryVO.setmRejected(dom1.getElementById("cbid.table.1.rejected").attr("value"));
					summaryVO.setmNetworkBlocks(dom1.getElementById("cbid.table.1.networkblocks").attr("value"));
					summaryVO.setmBestShare(dom1.getElementById("cbid.table.1.bestshare").attr("value"));
					summaryVO.setmFanSpeedIn(dom1.getElementById("cbid.table.1.fanspeedin").attr("value"));
					summaryVO.setmFanSpeedOut(dom1.getElementById("cbid.table.1.fanspeedout").attr("value"));
					System.out.println("summaryVO JSON:"+JSON.toJSONString(summaryVO));
					devicesVO = new WhatsMinerDevicesVO();
					devicesVO.setmDeviceName(dom1.getElementById("cbid.table.1.name").attr("value"));
					devicesVO.setmEnabled(dom1.getElementById("cbid.table.1.enable").attr("value"));
					devicesVO.setmStatus(dom1.getElementById("cbid.table.1.status").attr("value"));
					devicesVO.setmGHSav(dom1.getElementById("cbid.table.1.mhsav").attr("value"));
					devicesVO.setmGHS5s(dom1.getElementById("cbid.table.1.mhs5s").attr("value"));
					devicesVO.setmGHS1m(dom1.getElementById("cbid.table.1.mhs1m").attr("value"));
					devicesVO.setmGHS5m(dom1.getElementById("cbid.table.1.mhs5m").attr("value"));
					devicesVO.setmGHS15m(dom1.getElementById("cbid.table.1.mhs15m").attr("value"));
					devicesVO.setmLastValidWork(dom1.getElementById("cbid.table.1.lvw").attr("value"));
					devicesVO.setmDeviceId(dom1.getElementById("cbid.table.1.id").attr("value"));
					devicesVO.setmFreqAvg(dom1.getElementById("cbid.table.1.freqs_avg").attr("value"));
					devicesVO.setmUpfreqCompleted(dom1.getElementById("cbid.table.1.upfreq_complete").attr("value"));
					devicesVO.setmEffectiveChips(dom1.getElementById("cbid.table.1.effective_chips").attr("value"));
					devicesVO.setmTemperature1(dom1.getElementById("cbid.table.1.temp_1").attr("value"));
					devicesVO.setmTemperature2(dom1.getElementById("cbid.table.1.temp_2").attr("value"));
					
					poolList = new ArrayList<WhatsMinerPoolVO>();
					//3个矿池
					for(int i=1;i<=3;i++) {
						try {
							WhatsMinerPoolVO poolVO = new WhatsMinerPoolVO();
							poolVO.setmPool(dom1.getElementById("cbid.table."+i+".pool").attr("value"));
							poolVO.setmUrl(dom1.getElementById("cbid.table."+i+".url").attr("value"));
							poolVO.setmActive(dom1.getElementById("cbid.table."+i+".stratumactive").attr("value"));
							poolVO.setmUser(dom1.getElementById("cbid.table."+i+".user").attr("value"));
							poolVO.setmStatus(dom1.getElementById("cbid.table."+i+".status").attr("value"));
							poolVO.setmDifficulty(dom1.getElementById("cbid.table."+i+".stratumdifficulty").attr("value"));
							poolVO.setmGetWorks(dom1.getElementById("cbid.table."+i+".getworks").attr("value"));
							poolVO.setmAccepted(dom1.getElementById("cbid.table."+i+".accepted").attr("value"));
							poolVO.setmRejected(dom1.getElementById("cbid.table."+i+".rejected").attr("value"));
							poolVO.setmStale(dom1.getElementById("cbid.table."+i+".stale").attr("value"));
							poolVO.setmLST(dom1.getElementById("cbid.table."+i+".lastsharetime").attr("value"));
							poolVO.setmLSD(dom1.getElementById("cbid.table."+i+".lastsharedifficulty").attr("value"));
							poolList.add(poolVO);
						}catch(Exception e) {
							e.printStackTrace();
						}
						
					}
								
				}else if(key.indexOf("/admin/status/overview") != -1) {
					System.out.println("key:"+key);
					String rs2 = returnMap.get(key);
					//System.out.println("Sessions2:"+rs2);
					//Document dom2 = Jsoup.parse(rs2);
					JXDocument jxDocument = new JXDocument(rs2);				

					String model = jxDocument.selN("//fieldset[@class='cbi-section']/table/tbody/tr/td").get(1).getElement().text();
					String hostName = jxDocument.selN("//fieldset[@class='cbi-section']/table/tbody/tr/td").get(3).getElement().text();
					String firmwareVersion = jxDocument.selN("//fieldset[@class='cbi-section']/table/tbody/tr/td").get(5).getElement().text();
					String kenrnelVersion = jxDocument.selN("//fieldset[@class='cbi-section']/table/tbody/tr/td").get(7).getElement().text();
					String cgminerVersion =jxDocument.selN("//fieldset[@class='cbi-section']/table/tbody/tr/td").get(9).getElement().text();
					String localTime = jxDocument.selN("//fieldset[@class='cbi-section']/table/tbody/tr/td").get(11).getElement().text();
					String uptime = jxDocument.selN("//fieldset[@class='cbi-section']/table/tbody/tr/td").get(13).getElement().text();
					String loadAverage = jxDocument.selN("//fieldset[@class='cbi-section']/table/tbody/tr/td").get(15).getElement().text();
					systemVO = new WhatsMinerSystemVO();
					systemVO.setModel(model);
					systemVO.setHostName(hostName);
					systemVO.setFirmwareVersion(firmwareVersion);
					systemVO.setKernelVersion(kenrnelVersion);
					systemVO.setCgminerVersion(cgminerVersion);
					systemVO.setLocalTime(localTime);
					systemVO.setUpTime(uptime);
					systemVO.setLoadAverage(loadAverage);
					System.out.println(JSON.toJSONString(systemVO));
				}
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		WhatsMinerVO miner = new WhatsMinerVO();
		miner.setPoolList(poolList);
		miner.setSummaryVO(summaryVO);
		miner.setDevicesVO(devicesVO);
		miner.setSystemVO(systemVO);
		System.out.println("WhatsMiner JSON:\n"+JSON.toJSONString(miner));
		return miner;
	}
	
	public static JSONObject getMinerData(String ip) {
//		System.out.println("************************(Whats矿机)能运行到这里1*****************************");
//		System.out.println(ip);
		WhatsMinerVO miner;
		try {
			miner = spiderWhatsMiner(ip);
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}
		if(miner == null) {
			return null;
		}
		System.out.println("************************(Whats矿机)能运行到这里2*****************************");
		System.out.println(ip);
		List<WhatsMinerPoolVO> poolList = miner.getPoolList();
		WhatsMinerSummaryVO summaryVO = miner.getSummaryVO();
		WhatsMinerSystemVO systemVO = miner.getSystemVO();
		WhatsMinerDevicesVO devicesVO = miner.getDevicesVO();
		JSONObject status = new JSONObject();
		status.put("ipSeq", ip);
        status.put("status", 1); //正常
        //矿机类型
        String minertype = systemVO.getModel();
        status.put("minertype",minertype);
        //工作矿池、矿工、难度、任务数
        if(poolList != null && !poolList.isEmpty()) {
        	for(WhatsMinerPoolVO pool : poolList) {
        		if("true".equals(pool.getmActive()) && "Alive".equals(pool.getmStatus())) {
        			status.put("url", pool.getmUrl());//矿池url
        			status.put("user", pool.getmUser());//矿工        			
        	        status.put("diff", pool.getmDifficulty());//难度        	        
        	        status.put("jobs", pool.getmGetWorks());//任务数
        			break;
        		}
        	}
        }
        //平均算力     
        String cpAvgStr = summaryVO.getmGHSav();
        cpAvgStr = cpAvgStr.replaceAll(",", "");
        float cpAvg = Float.parseFloat(cpAvgStr)/1024*1.00f;
		status.put("cpAvg", parse2Point(cpAvg));//平均算力
        //实时算力
		String cpRtStr = devicesVO.getmGHS5s();
		cpRtStr = cpRtStr.replaceAll(",", "");
		float cpRt = Float.parseFloat(cpRtStr)/1024*1.00f;
        status.put("cpRt", parse2Point(cpRt));//实时算力
        
        //发送share
        String sharesSent = summaryVO.getmBestShare();
        sharesSent = sharesSent.replaceAll(",", "");
        status.put("sharesSent", sharesSent);
        //温度上/下
        String temp1Str = devicesVO.getmTemperature1();
        int temp1 = (int)Float.parseFloat(temp1Str);
        String temp2Str = devicesVO.getmTemperature2();
        int temp2 = (int)Float.parseFloat(temp2Str);
        status.put("temp", temp1+"/"+temp2);
        //最低、高转速
        String fan1Str = summaryVO.getmFanSpeedIn();
        fan1Str = fan1Str.replaceAll(",", "");
        Integer fan1 = Integer.parseInt(fan1Str);
        String fan2Str = summaryVO.getmFanSpeedOut();
        fan2Str = fan2Str.replaceAll(",", "");
        Integer fan2 = Integer.parseInt(fan2Str);
        status.put("speed", min(fan1,fan2)+"/"+max(fan1,fan2));
        //软件版本
        String version = systemVO.getCgminerVersion();
        status.put("version", version);
        //拒绝率
        String reject = summaryVO.getmRejected();
        status.put("reject", reject+"%");
        //运行时长（分钟）
        String runTime = summaryVO.getmElapsed();
		float min = 0.0f;
		if(runTime.indexOf("h")!=-1) {
			String str[] = runTime.split("h");
			String str2[] = str[1].split("m");
			str2[1] = str2[1].replace("s", "");
			min += Float.parseFloat(str[0])*60 + Float.parseFloat(str2[0]) + Float.parseFloat(str2[1])/60;
		}else if(runTime.indexOf("m")!=-1) {
			String str[] = runTime.split("m");
			str[1] = str[1].replace("s", "");
			min += Float.parseFloat(str[0]) + Float.parseFloat(str[1])/60;
		}else if(runTime.indexOf("s")!=-1) {
			runTime = runTime.replace("s", "");
			min += Float.parseFloat(runTime)/60;
		}
		status.put("runTime", parse2Point(min));//运行时长分钟
		return status;
	}
	
	//重启
	public static void reboot(String ip) {
		//登录的地址以及登录操作参数  
        String loginUrl = "http://"+ip+"/cgi-bin/luci";  
        //登录的相关参数以及登录后操作参数  
        List<NameValuePair> loginParams = new ArrayList<NameValuePair>();   
        loginParams.add(new BasicNameValuePair("luci_username", "root"));  
        loginParams.add(new BasicNameValuePair("luci_password", "root")); 
        loginParams.add(new BasicNameValuePair("content", "Authorization Required"));
        //登录后操作地址以及登录后操作参数  
        Map<String,List<NameValuePair>> map = new HashMap<String,List<NameValuePair>>(); 
        List<NameValuePair> queryParams = new ArrayList<NameValuePair>(); 
        //reboot
        //http://10.21.5.1/cgi-bin/luci//admin/system/reboot/call
        String rebootUrl = "http://"+ip+"/cgi-bin/luci//admin/system/reboot/call";
        map.put(rebootUrl, queryParams); 
        KeepSessionWithOneHttpclient.doPostWithOneHttpclient(loginUrl, loginParams, map,false,"WhatsMiner");
	}
	
	//配置矿池
	public static String setPool(String ip,PoolApiVO pools) {
		try {
			//登录的地址以及登录操作参数  
	        String loginUrl = "http://"+ip+"/cgi-bin/luci";  
	        //登录的相关参数以及登录后操作参数  
	        List<NameValuePair> loginParams = new ArrayList<NameValuePair>();   
	        loginParams.add(new BasicNameValuePair("luci_username", "root"));  
	        loginParams.add(new BasicNameValuePair("luci_password", "root")); 
	        loginParams.add(new BasicNameValuePair("content", "Authorization Required"));
	        //登录后操作地址以及登录后操作参数  
	        Map<String,List<NameValuePair>> map = new HashMap<String,List<NameValuePair>>(); 
	        List<NameValuePair> queryParams = new ArrayList<NameValuePair>(); 
//	        queryParams.add(new BasicNameValuePair("token", "8838efa33c8ef3458953e355c139e988"));//token
	        queryParams.add(new BasicNameValuePair("cbi.submit", "1"));//默认
	        queryParams.add(new BasicNameValuePair("cbi.apply", "Save & Apply"));//默认
	        queryParams.add(new BasicNameValuePair("content", "Configuration"));
	        queryParams.add(new BasicNameValuePair("cbid.pools.default.ntp_enable", "global"));//默认
	        queryParams.add(new BasicNameValuePair("cbid.pools.default.ntp_pools", "-p 0.pool.ntp.org -p 0.asia.pool.ntp.org -p 0.openwrt.pool.ntp.org -p 0.cn.pool.ntp.org"));//默认
	        //setPool
	        if(pools.getWorker()!=null && pools.getBak1_worker()!=null && pools.getBak2_worker()!=null) {
	        	String setPoolUrl = "http://"+ip+"/cgi-bin/luci/admin/network/cgminer";
	        	queryParams.add(new BasicNameValuePair("cbid.pools.default.pool1url", pools.getUrl()+":"+pools.getPort()));
	        	queryParams.add(new BasicNameValuePair("cbid.pools.default.pool1user", pools.getWorker()));
	        	queryParams.add(new BasicNameValuePair("cbid.pools.default.pool1pw", pools.getPassword()));
	        	queryParams.add(new BasicNameValuePair("cbid.pools.default.pool2url", pools.getBak1_url()+":"+pools.getBak1_port()));
	        	queryParams.add(new BasicNameValuePair("cbid.pools.default.pool2user", pools.getBak1_worker()));
	        	queryParams.add(new BasicNameValuePair("cbid.pools.default.pool2pw", pools.getBak1_password()));
	        	queryParams.add(new BasicNameValuePair("cbid.pools.default.pool3url", pools.getBak2_url()+":"+pools.getBak2_port()));
	        	queryParams.add(new BasicNameValuePair("cbid.pools.default.pool3user", pools.getBak2_worker()));
	        	queryParams.add(new BasicNameValuePair("cbid.pools.default.pool3pw", pools.getBak2_password()));
	        	map.put(setPoolUrl, queryParams); 
	            KeepSessionWithOneHttpclient.doPostWithOneHttpclient(loginUrl, loginParams, map,false,"WhatsMiner");
	        	return "success";
	        }
		}catch(Exception e) {
			e.printStackTrace();
			return "fail";
		}
		
        return "fail";
	}
	
	public static float max(float a , float b)//两个数的最大值
	{
	   return a>b?a:b;
	}
	
	public static float min(float a , float b)//两个数的最小值
	{
	    return a<b?a:b;
	}
	
	public static int max(int a , int b)//两个数的最大值
	{
	   return a>b?a:b;
	}
	
	public static int min(int a , int b)//两个数的最小值
	{
	    return a<b?a:b;
	}
	
	//保留2位有效数字
	public static String parse2Point(float scale) {
		DecimalFormat  fnum  =  new  DecimalFormat("##0.00");  
		String  dd=fnum.format(scale);    
		return dd;
	}
	
	public static void main(String args[]) {
		WhatsMinerService.spiderWhatsMiner("10.21.5.1");
	}
}
