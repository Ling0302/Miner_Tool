package service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;

import com.alibaba.fastjson.JSON;

import app.MinerCheckApp;
import utils.Constants;
import utils.HttpRequestUtils;
import utils.PropertiesUtils;
import utils.SftpUtils;
import vo.BatchMinerResultVO;
import vo.MinerParamVO;
import vo.PoolApiVO;
import vo.SetupVO;
import vo.VersionOtaVO;

public class MinerVersionService {

	//获取服务器最新版本
	public static String getNewVersion(String type) {
		String version = "";
		try {
			SftpUtils sftpUtils = getSftpUtils();
			String result = "";
			String command = "cat /data/www/ota/"+type+"/upgradeInfo.json";
			result  = sftpUtils.execute(command);
			VersionOtaVO vo = JSON.parseObject(result, VersionOtaVO.class);
			version = vo.getVersion()==null ? "" : vo.getVersion();
		}catch(Exception e) {
			version = "";
			e.printStackTrace();
		}
		return version;
	}
	
	//服务器地址信息
	public static SftpUtils getSftpUtils() {
		SftpUtils sftpUtils = new SftpUtils("otaftp.bitfily.com", 22, "www", "admin@akiWWW2017");
		return sftpUtils;
	}
	
	//获取所有矿机的版本信息，存入map返回
	public static Map<String,String> getMinersVersion(){
		Map<String,String> map = new HashMap<String,String>();
		//读取配置文件
		SetupVO setupVO = PropertiesUtils.readConfig();
		List<MinerParamVO> minerList = setupVO.getMinerList();
		for(MinerParamVO vo : minerList) {
			String key = vo.getType();
			String value = getNewVersion(key);
			map.put(key, value);
		}
		return map;
	}
	
	//升级版本
	public static void upGrade(Shell shell,Table table) {
		//校验部分
		if(upGradeCheck(shell,table)) {
			//批量操作部分
			List<UpGradeWork> workList = new ArrayList<UpGradeWork>();
			TableItem[] t = table.getItems();
			int num = 0;
			Map<String,String> versionMap = getMinersVersion();
			for(int i=0;i<t.length;i++) {
				if(t[i].getChecked()) {
					String ip = t[i].getText(Constants.ipIndex).trim();				
					String type = t[i].getText(Constants.minertypeIndex).trim();
					String versions = t[i].getText(Constants.versionIndex).trim();
					workList.add(new UpGradeWork(ip,type,versions,versionMap,""));
					num++;
				}
			}
			final int size = num;
			Runnable r = new Runnable() {				
				public void run(){	
					List<BatchMinerResultVO> resultList = doAction(workList,size,20);
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
		MinerCmdService.infoDialog(shell,msg);
	}	
	
	//升级版本---校验
	public static boolean upGradeCheck(Shell shell,Table table) {
		if(MinerCheckApp.isScan) {
			MinerCmdService.infoDialog(shell,"正在扫描！请稍后再试");
			return false;
		}
		if(MinerCheckApp.isMonitor) {
			MinerCmdService.infoDialog(shell,"批量操作时，请关闭监控状态！");
			return false;
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
			MinerCmdService.infoDialog(shell,"没有选择矿机！");
			return false;
		}
		if(selectNum > 5) {
			MinerCmdService.infoDialog(shell,"批量升级不能超过5台！");
			return false;
		}
		return true;
	}
	
	//版本升级过滤 type---矿机类型；versions---版本信息
	public static boolean filterUpGrade(String type,String versions,Map<String,String> versionMap) {
		//不支持该类型矿机的升级
		if(versionMap.get(type)==null) {			
			return true;
		}
		//版本已是最新
		if(versions.indexOf("|") == -1) {
			return true;
		}
		/*
		//版本已超最新
		String newVersion = versionMap.get(type);//新版本
		newVersion = newVersion.replaceAll("\\.", "");
		newVersion = newVersion.replace("v", "");
		String currVersion = versions.split("\\|")[0].trim();//当前版本
		currVersion = currVersion.replaceAll("\\.", "");
		currVersion = currVersion.replace("v", "");
		
		try {
			if(Integer.parseInt(currVersion) > Integer.parseInt(newVersion)) {
				return true;
			}
		}catch(Exception e) {
			//整形转化出错
			e.printStackTrace();
			return true;
		}*/
		return false;
	}
	
	//执行线程数
	public static List<BatchMinerResultVO> doAction(List<UpGradeWork> workList,int num,int threads) {
		List<BatchMinerResultVO> resultList = new ArrayList<BatchMinerResultVO>();
	    // 执行任务的线程：10个
	    for (int i = 0; i < threads; i++) {
	    	UpGradeWorkThread thread = new UpGradeWorkThread(workList, "worker" + i,resultList);
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
	
	/*
	public static List<BatchMinerResultVO> BatchMinerSelect(Table table,int threads) {
		List<UpGradeWork> workList = new ArrayList<UpGradeWork>();
		TableItem[] t = table.getItems();
		int num = 0;
		Map<String,String> versionMap = getMinersVersion();
		for(int i=0;i<t.length;i++) {
			if(t[i].getChecked()) {
				String ip = t[i].getText(0).trim();				
				String type = t[i].getText(1).trim();
				String versions = t[i].getText(12).trim();
				workList.add(new UpGradeWork(ip,type,versions,versionMap,""));
				num++;
			}
		}
		return doAction(workList,num,threads);
	}*/
	
}

//线程类
class UpGradeWorkThread extends Thread {
	 // 线程名
	 public String name = null;
	 // 任务List
	 private List<UpGradeWork> workList = null;
	 // 结果List
	 private List<BatchMinerResultVO> resultList = null;
	
	 public UpGradeWorkThread(List<UpGradeWork> workList, String name,List<BatchMinerResultVO> resultList) {
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
	            	 UpGradeWork work = workList.remove(0);
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
class UpGradeWork {
	// 任务名
	private String ip;
	private String param;
	private String type;
	private String versions;
	private Map<String,String> versionMap;
		
	public UpGradeWork(String ip,String type,String versions,Map<String,String> versionMap,String param) {
		this.ip = ip;
		this.param = param;
		this.versions = versions;
		this.type = type;
		this.versionMap = versionMap;
	}
		
   // 任务内容
	public void work(String worker) {
	     System.out.println(worker + ":" + ip + "\n param:"+param);
	     //System.out.println("Result:"+HttpRequestUtils.httpReq("post", "172.16.8.128", "/api/poolConfig", param));
	     System.out.println("Result:"+HttpRequestUtils.httpReq("post", ip, "/api/otaUpgrade", param));
	}
	
	//返回值
	public BatchMinerResultVO workReturnJson(String worker) {
		BatchMinerResultVO resultVO = new BatchMinerResultVO();
		try {
			String rs = "";
			if(MinerVersionService.filterUpGrade(type,versions,versionMap)) {
				//是否过滤掉
				resultVO.setIp(ip);
				resultVO.setSuccess(false);
				resultVO.setMessage("矿机版本已是最新或不支持该类型的矿机！");
				return resultVO;
			}else {
				rs = HttpRequestUtils.httpReq("post", ip, "/api/otaUpgrade", param);
			}			
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
			e.printStackTrace();
		}
		return resultVO;
	}
}
