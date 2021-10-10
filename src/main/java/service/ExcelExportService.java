package service;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;

import app.MinerCheckApp;
import utils.ExcelUtils;

public class ExcelExportService {

	public static void export(Shell shell,Table table,String path){
		if(!checkData(shell,table,path)) {			
			return;
		}
		String[] title = new String[table.getColumnCount()];
		TableItem[] t = table.getItems();
		List<String[]> list = new ArrayList<String[]>();
		for(int i=0;i<table.getColumnCount();i++) {
			title[i]=table.getColumn(i).getText().toString().trim();			
		}	
		for(int i=0;i<t.length;i++) {
			String[] rows = new String[table.getColumnCount()];
			for(int j=0;j<table.getColumnCount();j++) {
				rows[j] = t[i].getText(j).trim();
			}
			list.add(rows);
		}

		if(ExcelUtils.saveExcel(title, list, path)) {
			infoDialog(shell,"导出成功！");
		}else {
			infoDialog(shell,"导出数据失败！");
		}
	}
	
	public static boolean checkData(Shell shell,Table table,String path) {		
		int num = table.getItems().length;
		if("".equals(path)) {
			//infoDialog(shell,"导出失败！路径为空");
			return false;
		}
		if(num < 1) {
			infoDialog(shell,"导出失败！数据为空");
			return false;
		}
		
		return true;
	}
	
	public static void infoDialog(Shell shell,String msg) {
    	MessageBox dialog=new MessageBox(shell,SWT.OK|SWT.ICON_INFORMATION);
        dialog.setText("Info");
        dialog.setMessage(msg);
        dialog.open();
    }
	
	public static void fileDig(Shell shell){
		//新建文件对话框，并设置为打开的方式
		FileDialog filedlg=new FileDialog(shell,SWT.OPEN);
		//设置文件对话框的标题
		filedlg.setText("文件选择");
		//设置初始路径
		filedlg.setFilterPath("SystemRoot");
		//打开文件对话框，返回选中文件的绝对路径
		String selected=filedlg.open();
		System.out.println("您选中的文件路径为："+selected);
	}

	public static void saveExcel(Shell shell,Table table) {
		if(MinerCheckApp.isScan) {
			infoDialog(shell,"正在扫描！请稍后再试");
			return;
		}
		String path = folderDig(shell);
		export(shell,table,path);
	}

	public static String folderDig(Shell shell){
		//新建文件夹（目录）对话框
		DirectoryDialog folderdlg=new DirectoryDialog(shell);
		//设置文件对话框的标题
		folderdlg.setText("文件选择");
		//设置初始路径
		folderdlg.setFilterPath("SystemDrive");
		//设置对话框提示文本信息
		folderdlg.setMessage("请选择相应的文件夹");
		//打开文件对话框，返回选中文件夹目录
		String selecteddir=folderdlg.open();
		if(selecteddir==null){
			return "";
		}
		else{
			System.out.println("您选中的文件夹目录为："+selecteddir);
			return selecteddir;
		}
	}	
}
