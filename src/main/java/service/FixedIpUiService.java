package service;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;

import utils.IPRangeUtils;
import vo.FixedIpVO;
import vo.PoolVO;

public class FixedIpUiService {
	
	public static void addIpItem(Button btnCheckButton,
			Button btnCheckButton_4,
			Button btnCheckButton_5,
			Button btnCheckButton_16,
			Text text_13,
			List<PoolVO> poolList,
			FixedIpVO fixedIpVO,
			Table macTable
			){		
		if(!IPRangeUtils.ipCheck(fixedIpVO.getIp())
				||!IPRangeUtils.ipCheck(fixedIpVO.getNetMast())
				||!IPRangeUtils.ipCheck(fixedIpVO.getGateway())
				||!IPRangeUtils.ipCheck(fixedIpVO.getDns())
				) {
			MinerCmdService.infoDialog(macTable.getShell(), "不符合IP规范！请检查网络配置，再试...");
			return;
		}
		String targetIp = fixedIpVO.getIp();
		String ipStr[] = targetIp.split("\\.");
		int ipNum = Integer.parseInt(ipStr[3]);
		if(ipNum >= 255) {
			MinerCmdService.infoDialog(macTable.getShell(), "当前IP段的IP已填充完毕，请输入下一个填充IP");
			return;
		}
		String nextIp = ipStr[0] + "." + ipStr[1] + "." + ipStr[2] + "." + (ipNum+1);
		text_13.setText(nextIp);
		
		Boolean isAuto = btnCheckButton.getSelection();
		Boolean isConfIp = btnCheckButton_4.getSelection();
		Boolean isConfPool = btnCheckButton_5.getSelection();
		Boolean isConfWz = btnCheckButton_16.getSelection();
		TableItem item = new TableItem(macTable, SWT.NONE); 
		item.setChecked(true);
		item.setText(0, fixedIpVO.getIp());
		item.setText(1, fixedIpVO.getIp());
		item.setText(6, fixedIpVO.getNetMast());
		item.setText(7, fixedIpVO.getGateway());
		item.setText(8, fixedIpVO.getDns());
		item.setText(10, poolList.get(0).getPoolUrl());
		item.setText(11, poolList.get(0).getPoolUser());
		item.setText(12, poolList.get(0).getPoolPwd());
		item.setText(13, poolList.get(1).getPoolUrl());
		item.setText(14, poolList.get(1).getPoolUser());
		item.setText(15, poolList.get(1).getPoolPwd());
		item.setText(16, poolList.get(2).getPoolUrl());
		item.setText(17, poolList.get(2).getPoolUser());
		item.setText(18, poolList.get(2).getPoolPwd());
		ExecutorService ex = Executors.newFixedThreadPool(20);	
		if(isAuto) {
			if(isConfIp&&isConfPool) {
				item.setText(3, "配置中...");
				item.setText(4, "配置中...");
				ex.execute(new Runnable() {
					@Override
					public void run() {
						macTable.getDisplay().syncExec(new Runnable() {
							@Override
							public void run() {
								
														
							}							
						});
					}				
				});
			}else if(isConfIp&&!isConfPool) {
				item.setText(3, "配置中...");
				ex.execute(new Runnable() {
					@Override
					public void run() {
						macTable.getDisplay().syncExec(new Runnable() {
							@Override
							public void run() {
								item.setText(3, "配置成功!");						
							}							
						});
					}				
				});
			}else if(!isConfIp&&isConfPool) {
				item.setText(4, "配置中...");
				ex.execute(new Runnable() {
					@Override
					public void run() {
						macTable.getDisplay().syncExec(new Runnable() {
							@Override
							public void run() {
								item.setText(4, "配置成功!");						
							}							
						});
					}				
				});
			}			
			if(isConfWz) {
				item.setText(5, "配置中...");
				ex.execute(new Runnable() {
					@Override
					public void run() {
						macTable.getDisplay().syncExec(new Runnable() {
							@Override
							public void run() {
														
							}							
						});
					}				
				});
			}
			
		}
		
	}
	
	public static void appConfig(Button btnCheckButton,
			Button btnCheckButton_4,
			Button btnCheckButton_5,
			Button btnCheckButton_16,
			Table macTable) {
		Boolean isAuto = btnCheckButton.getSelection();
		Boolean isConfIp = btnCheckButton_4.getSelection();
		Boolean isConfPool = btnCheckButton_5.getSelection();
		Boolean isConfWz = btnCheckButton_16.getSelection();
		TableItem[] items = macTable.getItems();
		ExecutorService ex = Executors.newFixedThreadPool(20);
		for(TableItem item : items) {
			if("".equals(item.getText(3))
					||"".equals(item.getText(4))
					||"".equals(item.getText(5))) {
				
			}else {
				continue;
			}
			
			if(isConfIp&&isConfPool) {
				item.setText(3, "配置中...");
				item.setText(4, "配置中...");
				ex.execute(new Runnable() {
					@Override
					public void run() {
						macTable.getDisplay().syncExec(new Runnable() {
							@Override
							public void run() {
								
														
							}							
						});
					}				
				});
			}else if(isConfIp&&!isConfPool) {
				item.setText(3, "配置中...");
				ex.execute(new Runnable() {
					@Override
					public void run() {
						macTable.getDisplay().syncExec(new Runnable() {
							@Override
							public void run() {
								item.setText(3, "配置成功!");						
							}							
						});
					}				
				});
			}else if(!isConfIp&&isConfPool) {
				item.setText(4, "配置中...");
				ex.execute(new Runnable() {
					@Override
					public void run() {
						macTable.getDisplay().syncExec(new Runnable() {
							@Override
							public void run() {
								item.setText(4, "配置成功!");						
							}							
						});
					}				
				});
			}			
			if(isConfWz) {
				item.setText(5, "配置中...");
				ex.execute(new Runnable() {
					@Override
					public void run() {
						macTable.getDisplay().syncExec(new Runnable() {
							@Override
							public void run() {
														
							}							
						});
					}				
				});
			}
		}
	}
}
