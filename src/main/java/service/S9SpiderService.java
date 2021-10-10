package service;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import cn.dreampie.log.Logger;
import utils.HttpClientUtils;
import vo.PoolApiVO;
import vo.S9AntMinerVO;
import vo.S9ChainVO;
import vo.S9ConfVO;
import vo.S9MinerVO;
import vo.S9PoolVO;
import vo.S9SummaryVO;
import vo.S9SystemInfoVO;

public class S9SpiderService {
	protected final Logger logger = Logger.getLogger(getClass());

	public static S9MinerVO spiderS9(String ip) {
		S9MinerVO minerS9 = new S9MinerVO();
		try {
			//1.SystemInfo
			String info = HttpClientUtils.createStream("http://"+ip+"/cgi-bin/get_system_info.cgi","root");//首页 json模型数据
			System.out.println(ip+"---info:\n"+info);
			if(info == null || "".equals(info)) {
				return null;
			}
			if(info.indexOf("404") != -1 || info.indexOf("login") != -1 || info.indexOf("minertype") == -1) {
				return null;
			}
			System.out.println("出错ip:" + ip);
			
			
			S9SystemInfoVO systemInfo = JSON.parseObject(info, S9SystemInfoVO.class);
			minerS9.setSystemInfo(systemInfo);
			//URL:/cgi-bin/minerStatus.cgi
			String html = HttpClientUtils.createStream("http://"+ip+"/cgi-bin/minerStatus.cgi","root");
			if(html == null || "".equals(html)) {
				return null;
			}
			if(html.indexOf("404") != -1 || html.indexOf("login") != -1) {
				return null;
			}
			Document dom = Jsoup.parse(html);
			//2.summary		
			S9SummaryVO summary = new S9SummaryVO();
			summary.setElapsed(dom.getElementById("ant_elapsed").text());
			summary.setCpRt(dom.getElementById("ant_ghs5s").text());
			summary.setCpAvg(dom.getElementById("ant_ghsav").text());
			summary.setFoundBlocks(dom.getElementById("ant_foundblocks").text());
			summary.setLocalWork(dom.getElementById("ant_localwork").text());
			summary.setUtility(dom.getElementById("ant_utility").text());
			summary.setWu(dom.getElementById("ant_wu").text());
			summary.setBestShare(dom.getElementById("ant_bestshare").text());
			minerS9.setSummary(summary);
			//3.Pools
			List<S9PoolVO> pools = new ArrayList<S9PoolVO>();
			Elements pool = dom.select("div#cbi-table-1-pool"); 
			Elements url = dom.select("div#cbi-table-1-url"); 
			Elements user = dom.select("div#cbi-table-1-user"); 
			Elements status = dom.select("div#cbi-table-1-status"); 
			Elements diff = dom.select("div#cbi-table-1-diff"); 
			Elements getWorks = dom.select("div#cbi-table-1-getworks"); 
			Elements priority = dom.select("div#cbi-table-1-priority"); 
			Elements accepted = dom.select("div#cbi-table-1-accepted"); 
			Elements diff1 = dom.select("div#cbi-table-1-diff1shares"); 
			Elements diffA = dom.select("div#cbi-table-1-diffaccepted"); 
			Elements diffR = dom.select("div#cbi-table-1-diffrejected"); 
			Elements diffS = dom.select("div#cbi-table-1-diffstale"); 
			Elements rejected = dom.select("div#cbi-table-1-rejected"); 
			Elements discarded = dom.select("div#cbi-table-1-discarded"); 
			Elements stale = dom.select("div#cbi-table-1-stale"); 
			Elements lsDiff = dom.select("div#cbi-table-1-lastsharedifficulty"); 
			Elements lsTime = dom.select("div#cbi-table-1-lastsharetime"); 
			if(pool!=null && !pool.isEmpty()) {
				for(int i=0;i<pool.size();i++) {
					S9PoolVO poolVO = new S9PoolVO();
					poolVO.setPool(pool.get(i).text());
					poolVO.setUrl(url.get(i).text());
					poolVO.setUser(user.get(i).text());
					poolVO.setStatus(status.get(i).text());
					poolVO.setDiff(diff.get(i).text());
					poolVO.setGetWorks(getWorks.get(i).text());
					poolVO.setPriority(priority.get(i).text());
					poolVO.setAccepted(accepted.get(i).text());
					poolVO.setDiff1(diff1.get(i).text());
					poolVO.setDiffA(diffA.get(i).text());
					poolVO.setDiffR(diffR.get(i).text());
					poolVO.setDiffS(diffS.get(i).text());
					poolVO.setRejected(rejected.get(i).text());
					poolVO.setDiscarded(discarded.get(i).text());
					poolVO.setStale(stale.get(i).text());
					poolVO.setLsdiff(lsDiff.get(i).text());
					poolVO.setLsTime(lsTime.get(i).text());
					pools.add(poolVO);
				}
			}
			minerS9.setPools(pools);
			//4.AntMiner
			S9AntMinerVO antMiner = new S9AntMinerVO();
			List<S9ChainVO> chainList = new ArrayList<S9ChainVO>();
			Elements chain = dom.select("div#cbi-table-1-chain");
			Elements asic = dom.select("div#cbi-table-1-asic");
			Elements freqAvg = dom.select("div#cbi-table-1-frequency");
			Elements ideal = dom.select("div#cbi-table-1-rate2");
			Elements rt = dom.select("div#cbi-table-1-rate");
			Elements hw = dom.select("div#cbi-table-1-hw");
			Elements tempChip1 = dom.select("div#cbi-table-1-temp");
			Elements tempChip2 = dom.select("div#cbi-table-1-temp2");
			Elements asicStatus = dom.select("div#cbi-table-1-status");
			if(chain!=null&&!chain.isEmpty()) {
				for(int i=0;i<chain.size();i++) {
					S9ChainVO chainVO = new S9ChainVO();
					chainVO.setChain(chain.get(i).text());
					chainVO.setAsic(asic.get(i).text());
					chainVO.setFreqAvg(freqAvg.get(i).text());
					chainVO.setIdeal(ideal.get(i).text());
					chainVO.setRt(rt.get(i).text());
					chainVO.setHw(hw.get(i).text());
					chainVO.setTempChip1(tempChip1.get(i).text());
					chainVO.setTempChip2(tempChip2.get(i).text());
					chainVO.setAsicStatus(asicStatus.get(i).text());
					chainList.add(chainVO);
				}
			}
			antMiner.setChainList(chainList);
			String fan[] = new String[8];
			fan[0] = dom.getElementById("ant_fan1").text();
			fan[1] = dom.getElementById("ant_fan2").text();
			fan[2] = dom.getElementById("ant_fan3").text();
			fan[3] = dom.getElementById("ant_fan4").text();
			fan[4] = dom.getElementById("ant_fan5").text();
			fan[5] = dom.getElementById("ant_fan6").text();
			fan[6] = dom.getElementById("ant_fan7").text();
			fan[7] = dom.getElementById("ant_fan8").text();
			antMiner.setFan(fan);
//			antMiner.setFan1(dom.getElementById("ant_fan1").text());
//			antMiner.setFan2(dom.getElementById("ant_fan2").text());
//			antMiner.setFan3(dom.getElementById("ant_fan3").text());
//			antMiner.setFan4(dom.getElementById("ant_fan4").text());
//			antMiner.setFan5(dom.getElementById("ant_fan5").text());
//			antMiner.setFan6(dom.getElementById("ant_fan6").text());
//			antMiner.setFan7(dom.getElementById("ant_fan7").text());
//			antMiner.setFan8(dom.getElementById("ant_fan8").text());
			minerS9.setAntMiner(antMiner);
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}
		
		System.out.println("Result:\n"+JSON.toJSONString(minerS9));
		return minerS9;
	}
	
	public static JSONObject getS9Data(String ip) {
		S9MinerVO minerS9;
		try {
			minerS9 = spiderS9(ip);
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}
		if(minerS9 == null) {
			return null;
		}		
		JSONObject status = new JSONObject();
		//矿池、矿工、拒绝率、难度
		if(minerS9.getPools()!=null && !minerS9.getPools().isEmpty()) {
			for(S9PoolVO s9Pool : minerS9.getPools()) {
				if("Alive".equals(s9Pool.getStatus())) {
					status.put("url", s9Pool.getUrl());//矿池url
					status.put("user", s9Pool.getUser());//矿工
					String diff = s9Pool.getLsdiff();
					diff = diff.replaceAll(",", "");
					status.put("diff", diff);//难度
				}
				if("total".equals(s9Pool.getPool())) {
					status.put("rejectRate", s9Pool.getRejected()+"%");//拒绝率
				}
			}
		}
		//运行时长、实时算力、平均算力
		String runTime = minerS9.getSummary().getElapsed();
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
		String cpRt = minerS9.getSummary().getCpRt();
		cpRt = cpRt.replaceAll(",", "");
		float rt = Float.parseFloat(cpRt)/1024*1.00f;
		String cpAvg = minerS9.getSummary().getCpAvg();
		cpAvg = cpAvg.replaceAll(",", "");
		float avg = Float.parseFloat(cpAvg)/1024*1.00f;
		status.put("runTime", parse2Point(min));//运行时长分钟
		status.put("cpRt", parse2Point(rt));//实时算力
		status.put("cpAvg", parse2Point(avg));//平均算力
		//上、下温
		S9AntMinerVO antMiner = minerS9.getAntMiner();
		String temp1[] = new String[antMiner.getChainList().size()];
		String temp2[] = new String[antMiner.getChainList().size()];
		for(int i=0;i<antMiner.getChainList().size();i++) {
			temp1[i] = antMiner.getChainList().get(i).getTempChip1();
			temp2[i] = antMiner.getChainList().get(i).getTempChip2();
		}
		Integer maxTemp = 0;
		Integer minTemp = 0;
		if(!"-".equals(temp2[0])) {
			Integer x = Integer.parseInt(temp2[0]);
			Integer y = Integer.parseInt(temp2[1]);
			Integer z = Integer.parseInt(temp2[2]);
			maxTemp = max(max(x,y),z);
			minTemp = min(min(x,y),z);
		}else if(!"-".equals(temp1[0])) {
			Integer x = Integer.parseInt(temp1[0]);
			Integer y = Integer.parseInt(temp1[1]);
			Integer z = Integer.parseInt(temp1[2]);
			maxTemp = max(max(x,y),z);
			minTemp = min(min(x,y),z);
		}
		status.put("temp", maxTemp+"/"+minTemp);
		//最低-高转速
		List<String> speedList = new ArrayList<String>();
		for(int i=0;i<8;i++) {
			if(!"0".equals(antMiner.getFan()[i])) {
				speedList.add(antMiner.getFan()[i]);
			}
		}
		String speed0 = speedList.get(0);
		speed0 = speed0.replaceAll(",", "");
		String speed1 = speedList.get(1);
		speed1 = speed1.replaceAll(",", "");
		status.put("speed", min(Integer.parseInt(speed0),Integer.parseInt(speed1))+"/"+max(Integer.parseInt(speed0),Integer.parseInt(speed1)));
		//任务数、发送share
		String jobs = minerS9.getSummary().getLocalWork();
		jobs = jobs.replaceAll(",", "");
		String sharesSent = minerS9.getSummary().getBestShare();
		sharesSent = sharesSent.replaceAll(",", "");
		status.put("jobs", jobs);
		status.put("sharesSent", sharesSent);
		
		status.put("ipSeq", ip);
        status.put("status", 1); //正常
        status.put("minertype","S9");
        status.put("version", minerS9.getSystemInfo().getSystem_logic_version());
		return status;
	}
	
	//S9矿机重启
	public static void reboot(String ip) {
		HttpClientUtils.createStream("http://"+ip+"/cgi-bin/reboot.cgi","root");
	}
	
	//配置矿池
	public static String setPool(String ip,PoolApiVO pools) {
		System.out.println(JSON.toJSONString(pools));
//		S9ConfVO conf = new S9ConfVO();
//		conf.set_ant_pool1url(pools.getUrl()+":"+pools.getPort());
//		conf.set_ant_pool1user(pools.getWorker());
//		conf.set_ant_pool1pw(pools.getPassword());
//		conf.set_ant_pool2url(pools.getBak1_url()+":"+pools.getBak1_port());
//		conf.set_ant_pool2user(pools.getBak1_worker());
//		conf.set_ant_pool2pw(pools.getBak1_password());
//		conf.set_ant_pool3url(pools.getBak2_url()+":"+pools.getBak2_port());
//		conf.set_ant_pool3user(pools.getBak2_worker());
//		conf.set_ant_pool3pw(pools.getBak2_password());
//		String s9ConfJson = JSON.toJSONString(conf);
//		HttpClientUtils.postUrl("http://"+ip+"/cgi-bin/set_miner_conf.cgi", s9ConfJson);
		

//		_ant_pool1url:sz.ss.btc.com:1800
//		_ant_pool1user:Bitfily.001
//		_ant_pool1pw:123
//		_ant_pool2url:sz.ss.btc.com:443
//		_ant_pool2user:Bitfily.001
//		_ant_pool2pw:123
//		_ant_pool3url:sz.ss.btc.com:25
//		_ant_pool3user:Bitfily.001
//		_ant_pool3pw:123
//		ant_nobeeper = false;// _ant_nobeeper,
//		ant_notempoverctrl = false;// _ant_notempoverctrl,
//		ant_fan_customize_switch = false;// _ant_fan_customize_switch,
//		ant_fan_customize_value = "";// _ant_fan_customize_value,
//		ant_freq = "550";// _ant_freq,
//		ant_voltage = "0706";// _ant_voltage		
		try {
			List<NameValuePair> parameters = new ArrayList<NameValuePair>();
			if(pools.getWorker()!=null && pools.getBak1_worker()!=null && pools.getBak2_worker()!=null) {
				parameters.add(new BasicNameValuePair("_ant_pool1url", pools.getUrl()+":"+pools.getPort()));
				parameters.add(new BasicNameValuePair("_ant_pool1user", pools.getWorker()));
				parameters.add(new BasicNameValuePair("_ant_pool1pw", pools.getPassword()));
				parameters.add(new BasicNameValuePair("_ant_pool2url", pools.getBak1_url()+":"+pools.getBak1_port()));
				parameters.add(new BasicNameValuePair("_ant_pool2user", pools.getBak1_worker()));
				parameters.add(new BasicNameValuePair("_ant_pool2pw", pools.getBak1_password()));
				parameters.add(new BasicNameValuePair("_ant_pool3url", pools.getBak2_url()+":"+pools.getBak2_port()));
				parameters.add(new BasicNameValuePair("_ant_pool3user", pools.getBak2_worker()));
				parameters.add(new BasicNameValuePair("_ant_pool3pw", pools.getBak2_password()));
				parameters.add(new BasicNameValuePair("_ant_nobeeper", "false"));
				parameters.add(new BasicNameValuePair("_ant_notempoverctrl", "false"));
				parameters.add(new BasicNameValuePair("_ant_fan_customize_switch", "false"));
				parameters.add(new BasicNameValuePair("_ant_fan_customize_value", ""));
				parameters.add(new BasicNameValuePair("_ant_freq", "550"));
				parameters.add(new BasicNameValuePair("_ant_voltage", "0706"));
				HttpClientUtils.postUrlParam("http://"+ip+"/cgi-bin/set_miner_conf.cgi", parameters);
				return "success";
			}
		}catch(Exception e) {
			e.printStackTrace();
			return "fail";
		}
		
		return "fail";	
		
	    
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
		S9SpiderService.getS9Data("10.42.7.1");
	}
}
