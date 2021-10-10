package service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import app.MinerCheckApp;
import utils.IPRangeUtils;

public class UpgradeUiService {
	
	public static void upgradeFile(Table table_gj) {
		
		Runnable runnable = new Runnable() {
			@Override
			public void run() {
				ExecutorService service = Executors.newFixedThreadPool(30);					
				TableItem[] t = table_gj.getSelection();
				final int size = t.length;
				CountDownLatch countDownLatch = new CountDownLatch(size);	
				List<Future<String>> statusList =  new ArrayList<>();
				for(TableItem item : t) {					
					UpgradeCall uc = new UpgradeCall(item,countDownLatch);
					statusList.add(service.submit(uc));										
				}
				int index = 0;				
				for(TableItem item : t) {
					Future<String> fs = statusList.get(index);
					index++;
					try {
						String result = fs.get();
						Display.getDefault().asyncExec(new Runnable() {
							@Override
							public void run() {								
								item.setText(6, "complete!");
								item.setText(5, result);
								if(countDownLatch.getCount() == 0) {
									//完成
									ButtonStatusService.enableButton();
					        		table_gj.setEnabled(true);
								}
							}
						});
					}catch(Exception e) {
						e.printStackTrace();
					}
				}
				
			}
			
		};
		new Thread(runnable).start();	
				
	}

	public static void loadIp(Table table_gj,String ips) {
		Runnable t = new Runnable() {
			public void run() {
				List<String> ipList = IPRangeUtils.getIPRangeList(ips);			
				ExecutorService service = Executors.newFixedThreadPool(30);	
				CountDownLatch countDownLatch = new CountDownLatch(ipList.size());
				List<Future<String>> statusList =  new ArrayList<>();
				for(String ip : ipList) {
					MinerDiscoveryCall md = new MinerDiscoveryCall(ip,countDownLatch);
					statusList.add(service.submit(md));
				}
				int index = 0;
				if(ipList.size() > 0) {
					final int size = ipList.size();
					final List<String> ips2 = new ArrayList<String>(ipList);
					ipList = null;
					for(Future<String> fs : statusList) {	
						index++;
		            	final int num = index;				
						try {
							String result = fs.get();
							Display.getDefault().asyncExec(new Runnable() {
								@Override
								public void run() {
									if(num == 1) {
										MinerCheckApp.lblNewLabel_23.setText("                                                                          ");
										table_gj.removeAll();
									}
									if(!"".equals(result) && result != null){
										try {
											JSONObject jol = JSON.parseObject(result);
											TableItem item = new TableItem(table_gj, SWT.NONE);  
											item.setText(new String[]{ips2.get(num-1) , "running" , jol.getString("mac") , jol.getString("model") , jol.getString("firmware") , "-", "-"});
											item.setChecked(true);
										}catch(Exception e) {
											
										}
								
									}
									//System.out.println(ips2.get(num-1));
									int percent = size == 0 ? 0 : (size - (int)countDownLatch.getCount()) * 100/size;
									String content = percent + "% | "+ips2.get(num-1)+"";
									//System.out.println(content);
									MinerCheckApp.lblNewLabel_23.setText(content);
									//System.out.println("正确数："+table_gj.getItemCount());
									if(num == ips2.size()) {
										MinerCheckApp.isLoadIp = false;
										int okNum = table_gj.getItemCount();										
										MinerCheckApp.lblNewLabel_23.setText("("+okNum+"/"+num+")");
										ButtonStatusService.enableButton();
										//释放内存
					            		service.shutdownNow();
					            		statusList.clear();
					            		ips2.clear();
									}
									
								}						
							});
						}catch(Exception e) {
							e.printStackTrace();
						}
					}
				}
			}
		};
		new Thread(t).start();		
	}
	
	/**
	 * 判断文件是否合法
	 * @param filename
	 * @return
	 */
	public static Boolean isAllowUpgrade(String filename) {
		if(filename == null || "".equals(filename)) {
			return false;
		}
		if(filename.indexOf("tar") == -1 
				|| filename.indexOf("gz") == -1
						|| filename.indexOf("swupdate_") == -1
				) {
			return false;
		}		
		return true;
	}
}
