package service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;

import vo.PoolSettingVO;
import vo.PoolVO;

public class MinerBatchService {

	//配置选中矿池
	public static void configSelectPool(Table table,List<PoolVO> poolList,int ipType) {
		TableItem[] items = table.getItems();
		List <String> ips = new ArrayList<String> ();
		List <TableItem> selectItems = new ArrayList<TableItem> ();
		for(TableItem item : items) {
			if(item.getChecked()) {
				ips.add(item.getText(0));
				selectItems.add(item);
				item.setText(1, "矿池配置中...");
			}
		}
		Runnable runnable = new Runnable() {
			@Override
			public void run() {		
				ExecutorService service = Executors.newFixedThreadPool(30);								
				final int size = ips.size();
				CountDownLatch countDownLatch = new CountDownLatch(size);
				PoolSettingVO vo = new PoolSettingVO();
				List<Future<String>> statusList =  new ArrayList<>();
				for(String ip : ips) {
					MinerPoolCall ms = new MinerPoolCall(ip,vo,countDownLatch);
					statusList.add(service.submit(ms));
				}
				int index = 0;				
				for(Future<String> fs : statusList) {					
					try {
						String result = fs.get();
						index++;
						final int num = index;
						Display.getDefault().asyncExec(new Runnable() {
							@Override
							public void run() {
								TableItem item = selectItems.get(num - 1);
								if(!"".equals(result) && result != null) {
									item.setText(1, "矿池配置成功!");
								}else {
									item.setText(1, "配置失败!");
								}
								if(num == size) {
									//完成
									ButtonStatusService.enableButton();
					        		table.setEnabled(true);
					        		//释放
					        		service.shutdownNow();
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
	
	//配置所有矿池
	public static void configAllPool(Table table,List<PoolVO> poolList,int ipType) {
		TableItem[] items = table.getSelection();
		List <String> ips = new ArrayList<String> ();
		for(TableItem item : items) {
			ips.add(item.getText(0));
			item.setText(1, "矿池配置中...");
		}
		Runnable runnable = new Runnable() {
			@Override
			public void run() {		
				ExecutorService service = Executors.newFixedThreadPool(30);				
				final int size = items.length;
				CountDownLatch countDownLatch = new CountDownLatch(size);
				PoolSettingVO vo = new PoolSettingVO();
				List<Future<String>> statusList =  new ArrayList<>();
				for(String ip : ips) {					
					MinerPoolCall ms = new MinerPoolCall(ip,vo,countDownLatch);
					statusList.add(service.submit(ms));
				}
				int index = 0;				
				for(TableItem item : items) {
					Future<String> fs = statusList.get(index);
					index++;
					try {
						String result = fs.get();
						Display.getDefault().asyncExec(new Runnable() {
							@Override
							public void run() {
								if(result != null) {
									item.setText(1, "配置矿池成功!");
								}	
								if(countDownLatch.getCount() == 0) {
									//完成
									ButtonStatusService.enableButton();
					        		table.setEnabled(true);
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
	
	//重启矿机
	public static void rebootMiner(Table table) {
		Runnable runnable = new Runnable() {
			@Override
			public void run() {				
				ExecutorService service = Executors.newFixedThreadPool(30);
				TableItem[] items = table.getSelection();	
				final int size = items.length;
				CountDownLatch countDownLatch = new CountDownLatch(size);
				List<Future<String>> statusList =  new ArrayList<>();
				for(TableItem item : items) {
					MinerRebootCall mr = new MinerRebootCall(item,countDownLatch);
					statusList.add(service.submit(mr));
				}
				int index = 0;				
				for(TableItem item : items) {
					Future<String> fs = statusList.get(index);
					index++;
					try {
						String result = fs.get();						
						Display.getDefault().asyncExec(new Runnable() {
							@Override
							public void run() {
								if(result != null) {
									item.setText(1, "重启成功!");
								}																
								if(countDownLatch.getCount() == 0) {
									//完成
									ButtonStatusService.enableButton();
					        		table.setEnabled(true);
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
	
	//固定IP界面的矿机重启
	public static void fixedIpReboot(Table macTable) {
		Runnable runnable = new Runnable() {
			@Override
			public void run() {				
				
			}			
		};
		new Thread(runnable).start();
	}
	
	//固定IP、矿池、位置配置
	public static void fixedIpConfig(Table macTable) {
		Runnable runnable = new Runnable() {
			@Override
			public void run() {				
				
			}			
		};
		new Thread(runnable).start();
	}
	
	/**
	 * table中是否有选中的结果集
	 * @param table
	 * @return
	 */
	public static boolean isHaveRecord(Table table) {
		TableItem[] items = table.getItems();
		List <TableItem> selectItems = new ArrayList<TableItem> ();
		for(TableItem item : items) {
			if(item.getChecked()) {
				selectItems.add(item);
			}
		}
		if(selectItems.isEmpty()) {
			return false;
		}
		return true;
	}
}
