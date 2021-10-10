package service;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;

import app.MinerCheckApp;
import utils.Constants;
import utils.TableColumnSorter;
import vo.MinerSearchVO;

public class MinerSearchService {

	public static List<String[]> resultList;//扫描或监控的结果集
	
	public static boolean checkSelectParam(Shell shell,MinerSearchVO searchVO,Table table) {
		if(MinerCheckApp.isScan) {
			MinerCmdService.infoDialog(shell, "正在扫描，请稍后再试！");
			return false;
		}
		if(MinerCheckApp.isMonitor) {
			MinerCmdService.infoDialog(shell, "请关闭监控状态，再进行检索！");
			return false;
		}
		if(table.getItemCount() < 1) {
			MinerCmdService.infoDialog(shell, "没有可检索的目标集，请扫描后再试！");
			return false;
		}		
		String minAvgCp = searchVO.getMinAvgCp();
		String maxAvgCp = searchVO.getMaxAvgCp();
		if(!isFloat(minAvgCp)) {
			MinerCmdService.infoDialog(shell, "算力最低值不为数字，请检查！");
			return false;
		}
		if(!isFloat(maxAvgCp)) {
			MinerCmdService.infoDialog(shell, "算力最高值不为数字，请检查！");
			return false;
		}
		if(!"".equals(minAvgCp) && !"".equals(maxAvgCp)) {
			if(Float.parseFloat(minAvgCp) > Float.parseFloat(maxAvgCp)) {
				MinerCmdService.infoDialog(shell, "算力检索条件，最低值不能大于最高值！");
				return false;
			}
		}
		return true;
	}
	
	//按条件检索
	public static void doSearch(MinerSearchVO searchVO,Table table,Label label) {
		String worker = searchVO.getWorker();
		String minAvgCp = searchVO.getMinAvgCp();
		String maxAvgCp = searchVO.getMaxAvgCp();
		TableItem[] t = table.getItems();
		//这里的循环如果正向的话会报错，数组越界，因为删除第一行后，第二行就变成了第一行，
		//如果再删除第二行的话，因为已经不存在了，所以会报越界
		for(int i=t.length-1;i>-1;i--){
			//矿工名
			String user = t[i].getText(Constants.workerIndex).trim();
			//换成小写字符，再模糊匹配
			user = user.toLowerCase();
			worker = worker.toLowerCase();
			//算力
			String avgCpStr = t[i].getText(6).trim();	
			if("".equals(user) || "".equals(avgCpStr)) {
				table.remove(i);
				continue;
			}
			Float avgCp;
			try {
				avgCp = Float.parseFloat(avgCpStr);
			}catch(Exception e) {
				avgCp = 0.0f;
			}
			if(user.indexOf(worker) == -1) {
				table.remove(i);
				continue;
			}
			if(!"".equals(minAvgCp) && !"".equals(maxAvgCp)) {
				Float min = Float.parseFloat(minAvgCp);
				Float max = Float.parseFloat(maxAvgCp);
				if(avgCp < min || avgCp > max) {
					table.remove(i);
					continue;
				}
			}else if("".equals(minAvgCp) && !"".equals(maxAvgCp)) {
				Float max = Float.parseFloat(maxAvgCp);
				if(avgCp > max) {
					table.remove(i);
					continue;
				}
			}else if(!"".equals(minAvgCp) && "".equals(maxAvgCp)) {
				Float min = Float.parseFloat(minAvgCp);
				if(avgCp < min) {
					table.remove(i);
					continue;
				}
			}
		}
		//排序
		TableColumnSorter.addStringSorterAuto(table, table.getColumn(Constants.workerIndex), true);
//		TableColumnSorter.addNumberSorterAuto(table, table.getColumn(Constants.avgcpIndex),true);
		String textStatus = "检索完成（"+ table.getItemCount() +"条记录）";
		label.setText(textStatus);
	}
	
	
	//判断是否为数字
	public static boolean isFloat(String str){
		if("".equals(str)) {
			return true;
		}
        try{
            Float.parseFloat(str);
            return true;
        }
        catch(NumberFormatException e) {
            return false;
        }
    }
	
	//记录扫描后的结果集
	public static void recordTable(Table table) {
		resultList = new ArrayList<String []>();
		TableItem t[] = table.getItems();
		Integer column = table.getColumnCount();
		for(int i=0;i<t.length;i++) {
			String str[] = new String[column];
			for(int j=0;j<column;j++) {
				str[j] = t[i].getText(j);
			}
			resultList.add(str);
		}
	}
	
	//恢复扫描的结果集
	public static void getScanTable(Table table,Label label) {
		if(resultList == null || resultList.isEmpty()) {
			return;
		}
		table.removeAll();//清除所有
		for(int i=0 ;i < resultList.size();i++) {
			TableItem tableItem = new TableItem(table, SWT.NONE);			
			tableItem.setText(resultList.get(i));
			MinerRuleService.addRuleCss(tableItem);//增加样式
		}
		String textStatus = "已恢复成扫描结果集（"+table.getItemCount()+"条记录）";
		label.setText(textStatus);
	}
}
