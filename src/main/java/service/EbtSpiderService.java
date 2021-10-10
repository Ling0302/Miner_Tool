package service;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import utils.KeepSessionWithOneHttpclient;
import vo.EbtCgminerGetValVO;
import vo.EbtCgminerStatusVO;
import vo.EbtMinerVO;
import vo.EbtSystemstatusVO;
import vo.PoolApiVO;

public class EbtSpiderService {

	public static EbtMinerVO spiderEbt(String ip) {
		//登录的地址以及登录操作参数  
        String loginUrl = "https://"+ip+"/user/login";  
        //登录的相关参数以及登录后操作参数  
        List<NameValuePair> loginParams = new ArrayList<NameValuePair>();   
        loginParams.add(new BasicNameValuePair("username", "admin"));  
        loginParams.add(new BasicNameValuePair("word", "admin"));  
        loginParams.add(new BasicNameValuePair("yuyan", "0"));  
        loginParams.add(new BasicNameValuePair("login", "Login"));  
        loginParams.add(new BasicNameValuePair("get_password", "")); 
        //登录后操作地址以及登录后操作参数  
        Map<String,List<NameValuePair>> map = new HashMap<String,List<NameValuePair>>(); 
        List<NameValuePair> queryParams = new ArrayList<NameValuePair>(); 
        //1.服务器状态
        String cgminerStatusUrl = "https://"+ip+"/Cgminer/CgminerStatus";
        map.put(cgminerStatusUrl, queryParams);   
        //2.设备信息
        String systemStatusUrl = "https://"+ip+"/Status/getsystemstatus";
        map.put(systemStatusUrl, queryParams);   
        //3.矿池信息
        String cgminerPoolUrl = "https://"+ip+"/Cgminer/CgminerGetVal";
        map.put(cgminerPoolUrl, queryParams);   
        Map<String,String> returnMap = KeepSessionWithOneHttpclient.doPostWithOneHttpclient(loginUrl, loginParams, map,true,"EBT");          
        EbtMinerVO miner = mapFormatMiner(map,returnMap);
        return miner;
	}
	
	public static EbtMinerVO mapFormatMiner(Map<String,List<NameValuePair>> map,Map<String,String> returnMap) {
		if(returnMap == null || returnMap.isEmpty()) {
			return null;
		}
		EbtCgminerStatusVO vo1 = null;
		EbtSystemstatusVO vo2 = null;
		EbtCgminerGetValVO vo3 = null;
		try {
			for (Map.Entry<String,List<NameValuePair>> entry : map.entrySet()) { 
				String key = entry.getKey();
				if(key.indexOf("/Cgminer/CgminerStatus") != -1) {
					String rs1 = returnMap.get(key);
					JSONObject job1 = JSON.parseObject(rs1);
					if("1".equals(job1.getString("status"))) {
						vo1 = JSON.parseObject(job1.getString("feedback"), EbtCgminerStatusVO.class);
					}				
				}else if(key.indexOf("/Status/getsystemstatus") != -1) {
					String rs2 = returnMap.get(key);
					JSONObject job2 = JSON.parseObject(rs2);
					if("1".equals(job2.getString("status"))) {
						vo2 = JSON.parseObject(job2.getString("feedback"), EbtSystemstatusVO.class);
					}				
				}else if(key.indexOf("/Cgminer/CgminerGetVal") != -1) {
					String rs3 = returnMap.get(key);
					JSONObject job3 = JSON.parseObject(rs3);
					if("1".equals(job3.getString("status"))) {
						vo3 = JSON.parseObject(job3.getString("feedback"), EbtCgminerGetValVO.class);
					}				
				}
			}
		}catch(Exception e) {
			e.printStackTrace();			
		}
		EbtMinerVO miner = new EbtMinerVO();
		miner.setCgminerStatus(vo1);
		miner.setSystemStatus(vo2);
		miner.setCgminerGetVal(vo3);
		return miner;
	}
	
	public static JSONObject getMinerData(String ip) {
		EbtMinerVO miner;
		try {
			miner = spiderEbt(ip);
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}
		if(miner == null) {
			return null;
		}
		System.out.println("************************(翼比特矿机)能运行到这里*****************************");
		System.out.println(ip);
		EbtCgminerStatusVO vo1 = miner.getCgminerStatus();
		EbtSystemstatusVO vo2 = miner.getSystemStatus();
		EbtCgminerGetValVO vo3 = miner.getCgminerGetVal();
		JSONObject status = new JSONObject();
		status.put("ipSeq", ip);
        status.put("status", 1); //正常
        //矿机类型
        String minertype = vo2.getProductname();
        status.put("minertype",minertype);
        //工作矿池
        String pool = vo1.getCgminerstatus();
        status.put("url", pool);//矿池url
        //矿工
        String worker = "";
        if(pool.indexOf(vo3.getMip1()) != -1) {
        	worker = vo3.getMwork1();
        }else if(pool.indexOf(vo3.getMip2()) != -1) {
        	worker = vo3.getMwork2();
        }else if(pool.indexOf(vo3.getMip3()) != -1) {
        	worker = vo3.getMwork3();
        }
        status.put("user", worker);//矿工
        //平均算力
        String cpAvg = vo1.getFiveavg();
        cpAvg = cpAvg.replace("T", "");        
		status.put("cpAvg", cpAvg);//平均算力
        //实时算力
        String cpRt = vo1.getFivesecfigure();
        cpRt = cpRt.replace("T", "");
        status.put("cpRt", cpRt);//实时算力
        //难度
        String diff = vo1.getCurrentaccident();
        if(diff.indexOf(".") != -1) {
        	diff = diff.split("\\.")[0];
        }
        status.put("diff", diff);
        //任务数
        String jobs = vo1.getGetworks();
        status.put("jobs", jobs);
        //发送share
        String sharesSent = vo1.getAccepted();
        status.put("sharesSent", sharesSent);
        //温度上/下
        String temp = vo2.getDevice1temp();
        temp = temp.replace(",", "/");
        status.put("temp", temp);
        //最低、高转速
        Integer fan1=0,fan2 = 0;
        try {
        	fan1 = Integer.parseInt(vo2.getDevicefan());
        }catch(Exception e) {
        	fan1 = 0;
        }
        try {
        	fan2 = Integer.parseInt(vo2.getDevicefan2());
        }catch(Exception e) {
        	fan2 = 0;
        }                
        status.put("speed", min(fan1,fan2)+"/"+max(fan1,fan2));
        //软件版本
        String version = vo2.getSystemsoftwareversion();
        status.put("version", version);
        //拒绝率
        String reject  = vo1.getRejected();
        status.put("reject", reject+"%");
        //运行时长（分钟）
        String runTime = vo1.getWorktime();
        float min = 0.0f;
        String str[] = runTime.split(":");
        if(str.length == 1) {
        	min += Float.parseFloat(runTime)/60;
        }else if(str.length == 2) {
        	min += Float.parseFloat(str[0]) + Float.parseFloat(str[1])/60;
        }else if(str.length == 3) {
        	min += Float.parseFloat(str[0])*60 + Float.parseFloat(str[1]) + Float.parseFloat(str[2])/60;
        }
        status.put("runTime", parse2Point(min));
        System.out.println(status.toJSONString());
		return status;
	}
	
	//重启
	public static void reboot(String ip) {
		//登录的地址以及登录操作参数  
        String loginUrl = "https://"+ip+"/user/login";  
        //登录的相关参数以及登录后操作参数  
        List<NameValuePair> loginParams = new ArrayList<NameValuePair>();   
        loginParams.add(new BasicNameValuePair("username", "admin"));  
        loginParams.add(new BasicNameValuePair("word", "admin"));  
        loginParams.add(new BasicNameValuePair("yuyan", "0"));  
        loginParams.add(new BasicNameValuePair("login", "Login"));  
        loginParams.add(new BasicNameValuePair("get_password", "")); 
        //登录后操作地址以及登录后操作参数  
        Map<String,List<NameValuePair>> map = new HashMap<String,List<NameValuePair>>(); 
        List<NameValuePair> queryParams = new ArrayList<NameValuePair>(); 
        //reboot
        String rebootUrl = "http://"+ip+"/update/resetcgminer";
        map.put(rebootUrl, queryParams); 
        KeepSessionWithOneHttpclient.doPostWithOneHttpclient(loginUrl, loginParams, map,true,"EBT");
	}
	
	//配置矿池
	public static String setPool(String ip,PoolApiVO pools) {
		try {
			//登录的地址以及登录操作参数  
	        String loginUrl = "https://"+ip+"/user/login";  
	        //登录的相关参数以及登录后操作参数  
	        List<NameValuePair> loginParams = new ArrayList<NameValuePair>();   
	        loginParams.add(new BasicNameValuePair("username", "admin"));  
	        loginParams.add(new BasicNameValuePair("word", "admin"));  
	        loginParams.add(new BasicNameValuePair("yuyan", "0"));  
	        loginParams.add(new BasicNameValuePair("login", "Login"));  
	        loginParams.add(new BasicNameValuePair("get_password", "")); 
	        //登录后操作地址以及登录后操作参数  
	        Map<String,List<NameValuePair>> map = new HashMap<String,List<NameValuePair>>(); 
	        List<NameValuePair> queryParams = new ArrayList<NameValuePair>(); 
	        //setPool
	        if(pools.getWorker()!=null && pools.getBak1_worker()!=null && pools.getBak2_worker()!=null) {
	        	String setPoolUrl = "http://"+ip+"/Cgminer/CgminerConfig";
	        	queryParams.add(new BasicNameValuePair("mip1", pools.getUrl()+":"+pools.getPort()));
	        	queryParams.add(new BasicNameValuePair("mwork1", pools.getWorker()));
	        	queryParams.add(new BasicNameValuePair("mpassword1", pools.getPassword()));
	        	queryParams.add(new BasicNameValuePair("mip2", pools.getBak1_url()+":"+pools.getBak1_port()));
	        	queryParams.add(new BasicNameValuePair("mwork2", pools.getBak1_worker()));
	        	queryParams.add(new BasicNameValuePair("mpassword2", pools.getBak1_password()));
	        	queryParams.add(new BasicNameValuePair("mip3", pools.getBak2_url()+":"+pools.getBak2_port()));
	        	queryParams.add(new BasicNameValuePair("mwork3", pools.getBak2_worker()));
	        	queryParams.add(new BasicNameValuePair("mpassword3", pools.getBak2_password()));
	        	map.put(setPoolUrl, queryParams); 
	            KeepSessionWithOneHttpclient.doPostWithOneHttpclient(loginUrl, loginParams, map,true,"EBT");
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
}
