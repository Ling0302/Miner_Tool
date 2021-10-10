package service;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;

import com.alibaba.fastjson.JSON;

import utils.Constants;
import utils.HttpRequestUtils;
import vo.BatchMinerResultVO;
import vo.PoolApiVO;

public class BatchMinerService {
	
	//默认10个
	public static List<BatchMinerResultVO> doAction(List<Work> workList,int num) {
		List<BatchMinerResultVO> resultList = new ArrayList<BatchMinerResultVO>();
	    // 执行任务的线程：10个
	    for (int i = 0; i < 10; i++) {
	        WorkThread thread = new WorkThread(workList, "worker" + i,resultList);
	        thread.start();
	    }
	    while(true) {	
	    	if(resultList.size() != 0 && resultList.size() == num) {
	    		return resultList;
	    	}
	    }
	}	
	
	//执行线程数
	public static List<BatchMinerResultVO> doAction(List<Work> workList,int num,int threads) {
		List<BatchMinerResultVO> resultList = new ArrayList<BatchMinerResultVO>();
	    // 执行任务的线程：10个
	    for (int i = 0; i < threads; i++) {
	        WorkThread thread = new WorkThread(workList, "worker" + i,resultList);
	        thread.start();
	    }
	    while(true) {
	    	try {
	    		Thread.sleep(1000);
	    	}catch(Exception e) {
	    		e.printStackTrace();
	    	}
	    	//System.out.println(resultList.size());
	    	if(resultList.size() != 0 && resultList.size() == num) {
	    		return resultList;
	    	}
	    }
	}
	
	public static List<BatchMinerResultVO> BatchMinerAll(Table table,PoolApiVO poolApiVO,int threads) {
		List<Work> workList = new ArrayList<Work>();
		TableItem[] t = table.getItems();
		int num = t.length;
		for(int i=0;i<t.length;i++) {
			String ip = t[i].getText(Constants.ipIndex).trim();
			String minertype = t[i].getText(Constants.minertypeIndex).trim();
			workList.add(new Work(ip,minertype,JSON.toJSONString(poolApiVO)));
		}
		return doAction(workList,num,threads);
	}
	
	public static List<BatchMinerResultVO> BatchMinerSelect(Table table,PoolApiVO poolApiVO,int threads) {
		List<Work> workList = new ArrayList<Work>();
		TableItem[] t = table.getItems();
		int num = 0;
		for(int i=0;i<t.length;i++) {
			if(t[i].getChecked()) {
				String ip = t[i].getText(Constants.ipIndex).trim();
				String minertype = t[i].getText(Constants.minertypeIndex).trim();
				workList.add(new Work(ip,minertype,JSON.toJSONString(poolApiVO)));
				num++;
			}
		}
		return doAction(workList,num,threads);
	}
	
	public static void main(String args[]) {
		
	}
	
}

 //线程类
class WorkThread extends Thread {
	 // 线程名
	 public String name = null;
	 // 任务List
	 private List<Work> workList = null;
	 // 结果List
	 private List<BatchMinerResultVO> resultList = null;
	
	 public WorkThread(List<Work> workList, String name,List<BatchMinerResultVO> resultList) {
	     super();
	     this.name = name;
	     this.workList = workList;
	     this.resultList = resultList;
	 }

	 public void run() {
	     //System.out.println(name + " start working...");
	     while (true) {
	         // 同步锁，放在循环里是不让该线程一直占用workList
	         synchronized (workList) {
	             // 任务List中有任务时进行循环
	             if (workList != null && !workList.isEmpty()) {
	                 // 取得一个任务，并从任务List中删除该任务
	                 Work work = workList.remove(0);
	                 // 执行任务，例：
	                 //work.work(name);
	                 resultList.add(work.workReturnJson(name));
	             } else {
	                 // 所有任务都完成
	             	 //System.out.println("所有任务完成！");
	                 break;
	             }
	         }
	     }
	 }
}

	//任务类
class Work {
	// 任务名
	private String ip;
	private String minertype;
	private String param;
		
	public Work(String ip,String minertype,String param) {
		this.ip = ip;
		this.minertype = minertype;
		this.param = param;
	}
		
    // 任务内容
	public void work(String worker) {
	     System.out.println(worker + ":" + ip + "\n param:"+param);
	     //System.out.println("Result:"+HttpRequestUtils.httpReq("post", "172.16.8.128", "/api/poolConfig", param));
	     System.out.println("Result:"+HttpRequestUtils.httpReq("post", ip, "/api/poolConfig", param));
	}
	
	//返回值
	public BatchMinerResultVO workReturnJson(String worker) {
		BatchMinerResultVO resultVO = new BatchMinerResultVO();
		if("S9".equals(minertype)) {
			PoolApiVO pools = JSON.parseObject(param, PoolApiVO.class);
			String resultS9 = S9SpiderService.setPool(ip, pools);
			if("success".equals(resultS9)) {
				resultVO.setIp(ip);
				resultVO.setSuccess(true);
				resultVO.setMessage("推送命令成功！");
			}else {
				resultVO.setIp(ip);
				resultVO.setSuccess(false);
				resultVO.setMessage("S9矿机需要配置3个矿池，请检查！");
			}
			
		}else if(minertype.indexOf("DWM_BTC") != -1) {
			//EBT Miner
			PoolApiVO pools = JSON.parseObject(param, PoolApiVO.class);
			String resultEBT = EbtSpiderService.setPool(ip, pools);
			if("success".equals(resultEBT)) {
				resultVO.setIp(ip);
				resultVO.setSuccess(true);
				resultVO.setMessage("推送命令成功！");
			}else {
				resultVO.setIp(ip);
				resultVO.setSuccess(false);
				resultVO.setMessage("翼比特miner需要配置3个矿池，请检查！");
			}
		}else if(minertype.indexOf("Whats") != -1) {
			//Whats Miner
			PoolApiVO pools = JSON.parseObject(param, PoolApiVO.class);
			String resultWhats = WhatsMinerService.setPool(ip, pools);
			if("success".equals(resultWhats)) {
				resultVO.setIp(ip);
				resultVO.setSuccess(true);
				resultVO.setMessage("推送命令成功！");
			}else {
				resultVO.setIp(ip);
				resultVO.setSuccess(false);
				resultVO.setMessage("WhatsMiner需要配置3个矿池，请检查！");
			}
		}else {
			try {
				String rs = HttpRequestUtils.httpReq("post", ip, "/api/poolConfig", param);
				if(rs==null || "".equals(rs.trim())) {
					resultVO.setIp(ip);
					resultVO.setSuccess(false);
					resultVO.setMessage("未知原因!请联系技术人员检查矿机运行状况");
				}else {
					resultVO = JSON.parseObject(rs, BatchMinerResultVO.class);
					resultVO.setIp(ip);
				}			
			}catch(Exception e) {
				resultVO.setIp(ip);
				resultVO.setSuccess(false);
				resultVO.setMessage("未知原因!请联系技术人员检查矿机运行状况");
			}
		}
		
		return resultVO;
	}
}
