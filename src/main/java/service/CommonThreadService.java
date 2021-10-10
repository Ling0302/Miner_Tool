package service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.ProgressBar;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.wb.swt.SWTResourceManager;

import com.alibaba.fastjson.JSONObject;

import app.MinerCheckApp;
import dao.UiConfigDao;
import utils.Constants;
import utils.IPRangeUtils;
import utils.PropertiesUtils;
import utils.StringUtils;
import vo.MinerParamVO;

public class CommonThreadService {	
	
	//扫描矿机
	public static void ScanThread(ProgressBar progressBar, Table resultTable, boolean display_success, List<String> ipRange,Shell shell) {
		Runnable t = new Runnable() {
			public void run() {
				//任务执行业务代码(不能包含ui界面控件)
				ExecutorService service = Executors.newFixedThreadPool(30);
		        List<String> ips = new ArrayList<>();
		        for (String range : ipRange)
		        {
		            ips.addAll(IPRangeUtils.getIPRangeList(range));
		        }
		        ips = IPRangeUtils.removeDuplicateWithOrder(ips);
		        CountDownLatch countDownLatch = new CountDownLatch(ips.size());
		        List<Future<JSONObject>> statusList = new ArrayList<>();
		        for (String ip : ips)
		        {	            		            	
		            MinerStatusCheck mc = new MinerStatusCheck(ip, countDownLatch);
		            statusList.add(service.submit(mc));	                
		        }				
	            //扫描功能
		        
		        
				int index = 0;
				if(ips.size()>0) {
					final int size = ips.size();
					final List<String> ips2 = new ArrayList<String>(ips);
					ips.clear();	
					ipRange.clear();
					for (Future<JSONObject> fs : statusList)
	                {
		            	index++;
		            	final int num = index;
		            	try {
		            		JSONObject jol = fs.get();
		            		Display.getDefault().asyncExec(new Runnable() {
		    	            	public void run() {
		    	            		//界面更新业务代码(可操作ui界面控件)	
		    	            		int progress = size == 0 ? 0 : (size - (int)countDownLatch.getCount()) * 100/size;			    	            		
									if(num==1) {
					            		progressBar.setSelection(0);
					            		resultTable.removeAll();
					            	}else {
					            		progressBar.setSelection(progress);
					            	}
									progressBar.setData("type", "矿机扫描");
									progressBar.setData("content", ips2.get(num-1)+"("+num+"/"+ips2.size()+")");
									//System.out.println(progress);
									if (jol.getInteger("status") == 1 )
				                    {
										String minertype = jol.getString("minertype");
										if("S9".equals(minertype)) {
											setS9TableData(jol,resultTable);
										}else if(minertype!=null && minertype.indexOf("DWM_BTC") != -1){
											System.out.println("翼比特矿机设备发现成功！");
											setEbtTableData(jol,resultTable);
										}else if(minertype!=null && minertype.indexOf("Whats") != -1){
											System.out.println("Whats矿机设备发现成功！");
											setEbtTableData(jol,resultTable);//与翼比特矿机通用结果集
										}else {
											String strArray[] = getArrayJol(jol);
					    	            	setTableData(strArray,resultTable,jol);	
										}
											    	            	
				                    }
									if(num == statusList.size()) {
										int okNum = resultTable.getItemCount();
					            		progressBar.setSelection(100);
					            		progressBar.setData("type", "扫描完成");
										progressBar.setData("content", "(扫描IP数："+ips2.size()+",矿机："+okNum+"台)");
					            		MinerCmdService.infoDialog(shell, "扫描完成！共"+okNum+"台");
					            		MinerCheckApp.isScan = false;
					            		MinerSearchService.recordTable(resultTable);	
					            		MinerCheckApp.lblNewLabel_13.setText("扫描完成！共"+okNum+"条记录");
					            		//释放内存
					            		service.shutdownNow();
					            		statusList.clear();
					            		ips2.clear();
					            	}
		    	            	}
		    	            });
		            		
		            	}catch(Exception e) {
		            		System.out.println("发生异常!");
		            		e.printStackTrace();
		            	}	          	
	                }
				}  
	            
	            
	            
			}
		};
		new Thread(t).start();
	}
	
	//监控矿机
	public static void monitorThread(ProgressBar progressBar, Table resultTable, boolean display_success, Composite ipListCompsite) {
		Runnable t = new Runnable() {
			public void run() {
				while(MinerCheckApp.isMonitor) {
					try {
						List<String> ipRange = new ArrayList<String>();
						Display.getDefault().syncExec(new Runnable() {
							@Override
							public void run() {
								Control[] cList = ipListCompsite.getChildren();						        
						        for (Control control : cList)
						        {
						            Button bt = (Button) control;
						            if (bt.getSelection())
						            {
						                ipRange.add(bt.getText());
						            }
						        }
								
							}
							
						});
						
						//任务执行业务代码(不能包含ui界面控件)						
						List<Future<JSONObject>> statusList = new ArrayList<>();
						ExecutorService service = Executors.newFixedThreadPool(30);
				        List<String> ips = new ArrayList<>();
				        for (String range : ipRange)
				        {
				            ips.addAll(IPRangeUtils.getIPRangeList(range));
				        }
				        ips = IPRangeUtils.removeDuplicateWithOrder(ips);
				        CountDownLatch countDownLatch = new CountDownLatch(ips.size());
				        for (String ip : ips)
				        {					        	
				        	MinerStatusCheck mc = new MinerStatusCheck(ip, countDownLatch);
				        	Future<JSONObject> fs = service.submit(mc);
				        					        	
				            if(MinerCheckApp.isMonitor) {
				            	statusList.add(fs);
				            }else {
				            	fs.cancel(true);
				            	service.shutdownNow();
				            	//非监控状态(中间按了停止监控，直接返回)				            	
								Display.getDefault().asyncExec(new Runnable() {
									@Override
									public void run() {
										progressBar.setData("type", "停止监控");
										progressBar.setSelection(100);											
									}										
								});		            			
		            			return;
				            }
				                            
				        }
	
			            //监控功能
				        int index = 0;
						if(ips.size()>0) {
							final int size = ips.size();
							final List<String> ips2 = new ArrayList<String>(ips);
							ips.clear();
							ipRange.clear();
							for (Future<JSONObject> fs : statusList)
			                {
				            	index++;
				            	final int num = index;
				            	try {
				            		JSONObject jol;
				            		if(MinerCheckApp.isMonitor) {
				            			jol = fs.get();
				            		}else {
				            			fs.cancel(true);
				            			//非监控状态(中间按了停止监控，直接返回)
										Display.getDefault().asyncExec(new Runnable() {
											@Override
											public void run() {
												progressBar.setData("type", "停止监控");
												progressBar.setSelection(100);											
											}										
										});										
				            			return;
				            		}
				            		
				            		Display.getDefault().asyncExec(new Runnable() {
				    	            	public void run() {
				    	            		//界面更新业务代码(可操作ui界面控件)	
				    	            		int progress = size == 0 ? 0 : (size - (int)countDownLatch.getCount()) * 100/size;	
//				    	            		System.out.println("**********************进度*****************："+progress);
											if(num==1) {
							            		progressBar.setSelection(0);
							            		resultTable.removeAll();
							            	}else {
							            		progressBar.setSelection(progress);
							            	}
											if(!MinerCheckApp.isMonitor) {
						            			//非监控状态(中间按了停止监控，直接返回)
												progressBar.setData("type", "停止监控");
												progressBar.setSelection(100);
												Thread.interrupted();
						            			return;
						            		}
											progressBar.setData("type", "矿机监控");
											progressBar.setData("content", ips2.get(num-1)+"("+num+"/"+ips2.size()+")");
											//System.out.println(progress);
											if (jol.getInteger("status") == 1 )
						                    {
												String minertype = jol.getString("minertype");
												if("S9".equals(minertype)) {
													setS9TableData(jol,resultTable);
												}else if(minertype!=null && minertype.indexOf("DWM_BTC") != -1){
													System.out.println("翼比特矿机设备发现成功！");
													setEbtTableData(jol,resultTable);
												}else if(minertype!=null && minertype.indexOf("Whats") != -1){
													System.out.println("Whats矿机设备发现成功！");
													setEbtTableData(jol,resultTable);//与翼比特矿机通用结果集
												}else {
													String strArray[] = getArrayJol(jol);
							    	            	setTableData(strArray,resultTable,jol);	
												}
													    	            	
						                    }
											if(num == statusList.size()) {
												int okNum = resultTable.getItemCount();
							            		progressBar.setSelection(100);
							            		progressBar.setData("type", "监控完成");
												progressBar.setData("content", "(监控IP数："+ips2.size()+",矿机："+okNum+"台)");
												//释放内存
							            		service.shutdownNow();
							            		statusList.clear();
							            		ips2.clear();
							            	}
				    	            	}
				    	            });
				            		
				            	}catch(Exception e) {
				            		System.out.println("发生异常!");
				            		e.printStackTrace();
				            	}	          	
			                }
						}  
						Map<String,String> map = UiConfigDao.selectConfig();
						Integer monitorTime = Integer.parseInt((String)map.get("MONITOR_TIME"));
						Thread.sleep(1000);
						//Thread.sleep(1000*60*monitorTime);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				
	            
			}
		};
		new Thread(t).start();
		
	}
	
	//扫描业务线程
	public static List<Future<JSONObject>> getScanThreadList(List<String> ipRange){
		//任务执行业务代码(不能包含ui界面控件)
		ExecutorService service = Executors.newFixedThreadPool(20);
        List<String> ips = new ArrayList<>();
        for (String range : ipRange)
        {
            ips.addAll(IPRangeUtils.getIPRangeList(range));
        }
        ips = IPRangeUtils.removeDuplicateWithOrder(ips);
        CountDownLatch countDownLatch = new CountDownLatch(ips.size());
        List<Future<JSONObject>> statusList = new ArrayList<>();
        for (String ip : ips)
        {	            		            	
            MinerStatusCheck mc = new MinerStatusCheck(ip, countDownLatch);
            statusList.add(service.submit(mc));	                
        }
        return statusList;
	}
	
	
	//拼装结果集
	public static String[] getArrayJol(JSONObject jol) {
		Map<String,String> minersVersionMap = MinerCheckApp.minersVersionMap;
		String versionStr = "";
		String currVersion = jol.getString("version");//当前版本
		versionStr += currVersion;
		String newVersion = MinerCheckApp.minersVersionMap.get(jol.getString("minerType").trim());
		if(newVersion!=null 
				&& !"".equals(newVersion)
				&& !currVersion.equals(newVersion)) {
			versionStr += " | "+newVersion+"(new)";
		}
		
		String str[] = new String[] {jol.getString("ipSeq"),                               
                jol.getString("minerType"), 
                jol.getString("url"), 
                jol.getString("worker"), 
                jol.getString("sn"), 
                jol.get("avgHashRate") == null || jol.get("avgHashRate").equals("")? "0" : StringUtils.round(jol.getFloat("avgHashRate")/1024,2), 
                jol.get("hashRate") == null || jol.get("hashRate").equals("")? "0" : StringUtils.round(jol.getFloat("hashRate")/1024,2), 
                jol.getString("diff") == null|| jol.get("diff").equals("") ? "" : jol.getString("diff"),
                jol.getString("jobs") == null|| jol.get("jobs").equals("") ? "" : jol.getString("jobs"),
                jol.getString("sharesSent") == null|| jol.get("sharesSent").equals("") ? "" : jol.getString("sharesSent"),
                jol.getString("temperature1")==null||jol.getString("temperature2")==null?"":StringUtils.formatNull(jol.getString("temperature1") + "/" + jol.getString("temperature2")) ,
                jol.getString("minSpeed")==null||jol.getString("maxSpeed")==null?"":StringUtils.formatNull(jol.getString("minSpeed") + "/" + jol.getString("maxSpeed")), 
                jol.getString("psuMaxV")==null||jol.getString("psuMaxI")==null?"":StringUtils.formatNull(jol.getString("psuMaxV") + "/" + jol.getString("psuMaxI")),
                jol.getString("psuPower1")==null||jol.getString("psuPower2")==null?"":StringUtils.formatNull(jol.getString("psuPower1") + "/" + jol.getString("psuPower2")),
                jol.getString("psuMaxInTemp")==null||jol.getString("psuMaxOutTemp")==null?"":StringUtils.formatNull(jol.getString("psuMaxInTemp") + "/" + jol.getString("psuMaxOutTemp")),
                versionStr,
                jol.getString("rejectedRate")==null?"":(jol.getString("rejectedRate")+"%"), 
                jol.get("upTime")==null || jol.get("upTime").equals("") ? "": StringUtils.round(jol.getFloat("upTime")/60,2), 
                "ON".equals(jol.getString("hashboardStatus")) ? "休眠" : Constants.STATUS[jol.getInteger("status") - 1]
                };
		return str;
	}
	
	//S9结果集
	public static void setS9TableData(JSONObject jol,Table resultTable) {
		String strArray[] = new String[] {
				jol.getString("ipSeq"),                               
                jol.getString("minertype"), 
                jol.getString("url"),
                jol.getString("user"),
                "-",
                jol.getString("cpAvg"),
                jol.getString("cpRt"),
                jol.getString("diff") == null ? "" : jol.getString("diff"),
                jol.getString("jobs") == null ? "" : jol.getString("jobs"),
                jol.getString("sharesSent") == null ? "" : jol.getString("sharesSent"),
                jol.getString("temp"),
                jol.getString("speed"),
                "-",
                "-",
                "-",
                jol.getString("version"),
                jol.getString("rejectRate"),
                jol.getString("runTime"),
                Constants.STATUS[jol.getInteger("status") - 1]
		};
		TableItem tableItem = new TableItem(resultTable, SWT.NONE);
        tableItem.setChecked(true);
        tableItem.setText(strArray);
        tableItem.setBackground(Constants.statusIndex,SWTResourceManager.getColor(Constants.STATUS_COLOR[jol.getInteger("status") - 1]));
	}
	
	//Ebt翼比特结果集
	public static void setEbtTableData(JSONObject jol,Table resultTable) {
		String strArray[] = new String[] {
				jol.getString("ipSeq"),                               
                jol.getString("minertype"), 
                jol.getString("url"),
                jol.getString("user"),
                "-",
                jol.getString("cpAvg"),
                jol.getString("cpRt"),
                jol.getString("diff"),
                jol.getString("jobs"),
                jol.getString("sharesSent"),
                jol.getString("temp"),
                jol.getString("speed"),
                "-",
                "-",
                "-",
                jol.getString("version"),
                jol.getString("reject"),
                jol.getString("runTime"),
                Constants.STATUS[jol.getInteger("status") - 1]
		};
		TableItem tableItem = new TableItem(resultTable, SWT.NONE);
        tableItem.setChecked(true);
        tableItem.setText(strArray);
        tableItem.setBackground(Constants.statusIndex,SWTResourceManager.getColor(Constants.STATUS_COLOR[jol.getInteger("status") - 1]));
	}
	
	//填充表格数据（单条）
	public static void setTableData(String[] strArray,Table resultTable,JSONObject jol) {
		TableItem tableItem = new TableItem(resultTable, SWT.NONE);
        tableItem.setChecked(true);
        tableItem.setText(strArray);
        if(!"ON".equals(jol.getString("hashboardStatus"))) {
        	tableItem.setBackground(Constants.statusIndex,SWTResourceManager.getColor(Constants.STATUS_COLOR[jol.getInteger("status") - 1]));
        }                
        tableItem.setText(jol.getString("ipSeq"));
        
        //规则配置
        MinerParamVO rule = PropertiesUtils.getRule(jol.getString("minerType").trim());
        if(rule != null) {
        	MinerRuleService.ruleCheck(jol,rule,tableItem);                        	
        }
	}
}
