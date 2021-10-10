package service;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

import app.MinerCheckApp;
import common.LangConfig;
import utils.Constants;
import utils.HttpRequestUtils;
import vo.BatchMinerResultVO;
import vo.CmdResultVO;
import vo.PoolApiVO;
import vo.PoolVO;

public class MinerCmdService {

	//重启（选中）
	public static void rebootAction(Shell shell,Table table) {
		//API：http://xx.xx.xx/api/reboot
		if(!checkSelectNum(shell,table)) {
			return;
		}
		doAction(shell,table,"/api/reboot");
		
	}
	
	//关闭（选中）
	public static void shutdownAction(Shell shell,Table table) {
		//API：http://xx.xx.xx/api/hashboardCtrl?command=OFF
		if(!checkSelectNum(shell,table)) {
			return;
		}
		doAction(shell,table,"/api/hashboardCtrl?command=OFF");
	}
	
	//开启（选中）
	public static void openAction(Shell shell,Table table) {
		//API：http://xx.xx.xx/api/hashboardCtrl?command=ON
		if(!checkSelectNum(shell,table)) {
			return;
		}
		doAction(shell,table,"/api/hashboardCtrl?command=ON");
	}	
	
	public static void doAction(Shell shell,Table table,String api) {
		TableItem[] t = table.getItems();
		//boolean flag = true;
		//List <CmdResultVO> list = new ArrayList<CmdResultVO>();
		String dialogInfo = "执行状态：\n";
		for(int i=0;i<t.length;i++){
    		if(!t[i].getChecked()){
    			continue;
    		}
    		String ip = t[i].getText(Constants.ipIndex);
    		String minertype = t[i].getText(Constants.minertypeIndex); 
    		if("S9".equals(minertype) && "/api/reboot".equals(api)) {
    			//如果是S9矿机的重启
    			S9SpiderService.reboot(ip);
    			dialogInfo += "ip("+ip+"):[执行成功]\n";
    		}else if("/api/reboot".equals(api) && minertype.indexOf("DWM_BTC") != -1) {
    			//EBT Miner
    			EbtSpiderService.reboot(ip);
    			dialogInfo += "ip("+ip+"):[执行成功]\n";
    		}else if("/api/reboot".equals(api) && minertype.indexOf("Whats") != -1) {
    			//Whats Miner
    			WhatsMinerService.reboot(ip);
    			dialogInfo += "ip("+ip+"):[执行成功]\n";
    		}else {
    			try {    			
        			String jsonResult = HttpRequestUtils.httpReq("get", ip, api, "");
        			CmdResultVO rs = JSON.parseObject(jsonResult, new TypeReference<CmdResultVO>() {});
            		if(!rs.getSuccess()) {
            			//flag = false;
            			dialogInfo += "ip("+ip+"):[执行失败],message：["+rs.getMessage()+"]\n";
            		}else {
            			dialogInfo += "ip("+ip+"):[执行成功]\n";
            		}
        		}catch(Exception e) {
        			dialogInfo += "ip("+ip+"):[执行失败],message：[未知原因，请联络技术员]\n";
        			e.printStackTrace();
        		}
    		}
    		    		
    		//list.add(rs);
		}
		infoDialog(shell,dialogInfo);
		
		
	}
	
	//配置选择的矿机
	public static void configSelectMiner(Shell shell,Table table,List<PoolVO> poolList,Spinner spinner) {
		Integer spinnerIp = spinner.getSelection();
		String poolUser[] = new String[poolList.size()];
		for(int j=0;j<poolList.size();j++) {
			poolUser[j] = poolList.get(j).getPoolUser();
		}
		if(checkSelectPool(shell,table,poolList)) {	
			List<Work> workList = new ArrayList<Work>();
			TableItem[] t = table.getItems();
			int num = 0;		
			
			for(int i=0;i<t.length;i++) {
				if(t[i].getChecked()) {
					String ip = t[i].getText(Constants.ipIndex).trim();
					String minertype = t[i].getText(Constants.minertypeIndex).trim();
					for(int j=0;j<poolList.size();j++) {
						if(poolList.get(j).getMinerLast() == 0) {
							//如果选择的是IP
							String ipLast[] = ip.split("\\.");
							String user = "";
							if(spinnerIp == 1) {
								user = poolUser[j] + "." + ipLast[3];
							}else if(spinnerIp == 2) {
								user = poolUser[j] + "." + ipLast[2] + "x" +ipLast[3];
							}else if(spinnerIp == 4) {
								user = poolUser[j] + "." + ipLast[0] + "x" + ipLast[1] + "x" + ipLast[2] + "x" + ipLast[3];
							}else {
								user = poolUser[j] + "." + ipLast[1] + "x" + ipLast[2] + "x" + ipLast[3];
							}							
							poolList.get(j).setPoolUser(user);
						}
					}
					workList.add(new Work(ip,minertype,JSON.toJSONString(getPoolParam(poolList))));
					num++;
				}
			}
			final int size = num;
			Runnable r = new Runnable() {				
				public void run(){	
					List<BatchMinerResultVO> resultList = BatchMinerService.doAction(workList,size,20);
					Display.getDefault().asyncExec(new Runnable() {
					    public void run() {		
					    	infoDialogBatch(shell,resultList);
					    }
					}); 
				}
			};
			new Thread(r).start();
		}		
	}
	
	//配置所有矿机
	public static void configAllMiner(Shell shell,Table table,List<PoolVO> poolList,Spinner spinner) {	
		Integer spinnerIp = spinner.getSelection();
		String poolUser[] = new String[poolList.size()];
		for(int j=0;j<poolList.size();j++) {
			poolUser[j] = poolList.get(j).getPoolUser();
		}
		if(checkSelectPool(shell,table,poolList)) {
			List<Work> workList = new ArrayList<Work>();
			TableItem[] t = table.getItems();
			int num = t.length;			
			for(int i=0;i<t.length;i++) {
				String ip = t[i].getText(Constants.ipIndex).trim();
				String minertype = t[i].getText(Constants.minertypeIndex).trim();
				for(int j=0;j<poolList.size();j++) {
					if(poolList.get(j).getMinerLast() == 0) {
						//如果选择的是IP
						String ipLast[] = ip.split("\\.");
						String user = "";
						if(spinnerIp == 1) {
							user = poolUser[j] + "." + ipLast[3];
						}else if(spinnerIp == 2) {
							user = poolUser[j] + "." + ipLast[2] + "x" +ipLast[3];
						}else if(spinnerIp == 4) {
							user = poolUser[j] + "." + ipLast[0] + "x" + ipLast[1] + "x" + ipLast[2] + "x" + ipLast[3];
						}else {
							user = poolUser[j] + "." + ipLast[1] + "x" + ipLast[2] + "x" + ipLast[3];
						}
						poolList.get(j).setPoolUser(user);
					}
				}
				workList.add(new Work(ip,minertype,JSON.toJSONString(getPoolParam(poolList))));
			}		
			final int size = num;
			Runnable r = new Runnable() {				
				public void run(){		
					List<BatchMinerResultVO> resultList = BatchMinerService.doAction(workList,size,20);
					Display.getDefault().asyncExec(new Runnable() {			
					    public void run() {		
					    	infoDialogBatch(shell,resultList);
					    }
					}); 
				}
			};
			new Thread(r).start();
		}
	}
	
	//矿机批量配置结果弹窗
	public static void infoDialogBatch(Shell shell,List<BatchMinerResultVO> resultList) {
		String msg = "执行完毕！共"+resultList.size()+"台";
		String errorInfo = "";//失败信息
		Integer okNum = 0;
		Integer errorNum = 0;
		for(int i=0;i<resultList.size();i++) {
			BatchMinerResultVO resultVO = resultList.get(i);
			if(!resultVO.getSuccess()) {
				errorInfo += "IP:["+resultVO.getIp()+"]\t"+resultVO.getMessage()+"\n";
				errorNum ++;
			}
		}
		okNum = resultList.size() - errorNum;
		msg += "\n"+"[成功台数："+okNum+",  失败台数："+errorNum+"]";
		if(errorNum > 0) {
			msg += "\n" +"失败列表：+\n"+ errorInfo;
		}
		infoDialog(shell,msg);
	}
	
	public static PoolApiVO getPoolParam(List<PoolVO> poolList) {
		PoolApiVO poolApiVO = new PoolApiVO();
		for(int i=0;i<poolList.size();i++) {
			String url = poolList.get(i).getPoolUrl();
			int lastIndex = url.lastIndexOf(":");
			String ip = url.substring(0, lastIndex);
			String port = url.substring(lastIndex+1, url.length());
			String poolName = poolList.get(i).getName();
			if("矿池1".equals(poolName)) {
				poolApiVO.setUrl(ip);
				poolApiVO.setPort(port);
				poolApiVO.setWorker(poolList.get(i).getPoolUser());
				poolApiVO.setPassword(poolList.get(i).getPoolPwd());
			}else if("矿池2".equals(poolName)) {
				poolApiVO.setBak1_url(ip);
				poolApiVO.setBak1_port(port);
				poolApiVO.setBak1_worker(poolList.get(i).getPoolUser());
				poolApiVO.setBak1_password(poolList.get(i).getPoolPwd());
			}else if("矿池3".equals(poolName)) {
				poolApiVO.setBak2_url(ip);
				poolApiVO.setBak2_port(port);
				poolApiVO.setBak2_worker(poolList.get(i).getPoolUser());
				poolApiVO.setBak2_password(poolList.get(i).getPoolPwd());
			}
		}
		return poolApiVO;
	}
	
	public static boolean checkSelectPool(Shell shell,Table table,List<PoolVO> poolList) {
		if(MinerCheckApp.isScan) {
			infoDialog(shell,"正在扫描！请稍后再试");
			return false;
		}
		if(MinerCheckApp.isMonitor) {
			infoDialog(shell,"批量操作时，请关闭监控状态！");
			return false;
		}
		if(poolList==null || poolList.isEmpty()) {
			infoDialog(shell,"没有选择矿池！请检查");
			return false;
		}
		for(int i=0 ;i<poolList.size();i++) {
			PoolVO poolVO = poolList.get(i);
			if(poolVO.getPoolUrl()==null||"".equals(poolVO.getPoolUrl())) {
				infoDialog(shell,poolVO.getName()+":\n地址不能为空");
				return false;
			}
			String url = poolVO.getPoolUrl();
			if(url.indexOf(":")==-1) {
				infoDialog(shell,poolVO.getName()+":\nurl端口写法不正确，请检查！");
				return false;
			}
			/*
			String str[] = url.split(":");
			if(!IPRangeUtils.ipCheck(str[0])) {
				infoDialog(shell,poolVO.getName()+":\nurl不符合IP规则，请检查！");
				return false;
			}
			try {
				Integer.parseInt(str[1]);
			}catch(Exception e) {
				infoDialog(shell,poolVO.getName()+":\nurl不符合IP端口规则，请检查！");
				return false;
			}*/
			if(poolVO.getPoolUser()==null||"".equals(poolVO.getPoolUser())) {
				infoDialog(shell,poolVO.getName()+":\n子账户名不能为空");
				return false;
			}
			if(poolVO.getPoolPwd()==null||"".equals(poolVO.getPoolPwd())) {
				infoDialog(shell,poolVO.getName()+":\n密码不能为空");
				return false;
			}
		}
		TableItem[] t = table.getItems();
		int selectNum = 0;
		for(int i=0;i<t.length;i++) {
			if(t[i].getChecked()){
				selectNum ++;
    			continue;
    		}
		}
		if(selectNum == 0) {
			infoDialog(shell,"没有选择矿机！");
			return false;
		}
		return true;
	}
	
	//检测选中的矿机是否满足要求
	public static boolean checkSelectNum(Shell shell,Table table) {
		if(MinerCheckApp.isScan) {
			infoDialog(shell,"正在扫描！请稍后再试");
			return false;
		}
		if(MinerCheckApp.isMonitor) {
			infoDialog(shell,"批量操作时，请关闭监控状态！");
			return false;
		}
		TableItem[] t = table.getItems();
		int checkNum = 0;
		for(int i=t.length-1;i>-1;i--){
			if(checkNum>5) {
				infoDialog(shell,"选中的矿机不能超过5台！");
				return false;
			}
    		if(!t[i].getChecked()){
    			continue;
    		}
    		
    		checkNum++;   		
		}
		if(checkNum==0) {
			infoDialog(shell,"没有选中矿机！");
			return false;
		}		
		return true;
	}
	
	public static void infoDialog(Shell shell,String msg) {
    	MessageBox dialog=new MessageBox(shell,SWT.OK|SWT.ICON_INFORMATION);
        dialog.setText(LangConfig.getKey("app.message.info"));
        dialog.setMessage(msg);
        dialog.open();
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
		return BatchMinerService.doAction(workList,num,threads);
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
		return BatchMinerService.doAction(workList,num,threads);
	}
}
