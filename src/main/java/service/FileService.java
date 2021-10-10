package service;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

import app.MinerCheckApp;

public class FileService {
	
	//选择文件夹
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
	
	//选择文件
	public static String selectFile(Shell shell,Label label) {
		FileDialog fileDialog = new FileDialog(shell, SWT.OPEN);
	    fileDialog.setFilterNames(new String[] { "Office Word Files ",
	     "All Files (*.*)" });
	    fileDialog.setFilterExtensions(new String[] { "*.doc;*.docx", "*.*" });
	    fileDialog.setFilterPath("SystemDrive");
	    fileDialog.setText("请选择固件file");
	    String dir = fileDialog.open();
	    if(dir==null){
			dir = "";
		}
	    System.out.println("您选中的文件绝对路径为："+dir);		    
	    MinerCheckApp.filePath = dir;
	    String fileNameNow = dir.substring(dir.lastIndexOf("\\")+1);
	    System.out.println("您选中的文件为："+fileNameNow);	
	    label.setText(fileNameNow);
	    return dir;
	}
	
	/**
     * 判断文件大小
     *
     * @param len
     *            文件长度
     * @param size
     *            限制大小
     * @param unit
     *            限制单位（B,K,M,G）
     * @return
     */
    public static boolean checkFileSize(Long len, int size, String unit) {
//        long len = file.length();
        double fileSize = 0;
        if ("B".equals(unit.toUpperCase())) {
            fileSize = (double) len;
        } else if ("K".equals(unit.toUpperCase())) {
            fileSize = (double) len / 1024;
        } else if ("M".equals(unit.toUpperCase())) {
            fileSize = (double) len / 1048576;
        } else if ("G".equals(unit.toUpperCase())) {
            fileSize = (double) len / 1073741824;
        }
        if (fileSize > size) {
            return false;
        }
        return true;
    }
}
