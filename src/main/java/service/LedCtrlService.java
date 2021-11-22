package service;

import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import app.MinerCheckApp;
import utils.SftpUtils;

public class LedCtrlService {
	
	public static void redon(Shell shell,List<String> ips){
		if(MinerCheckApp.isScan) {
			infoDialog(shell,"正在扫描！请稍后再试");
			return;
		}

		//TableItem[] t = table.getItems();
		//TableItem[] t = table.getSelection();

		for(int i=0;i<ips.size();i++) {
			String ip = ips.get(i);
			System.out.println("操作点灯的ip:"+ip);
    		SftpUtils sftpUtils = new SftpUtils(ip, 22, "root", "F9Miner1234");
        	sftpUtils.execute("sudo led_ctl red off");
			sftpUtils.execute("sudo led_ctl red on");
		}

		infoDialog(shell,"点亮红灯成功！");
	}

	public static void infoDialog(Shell shell,String msg) {
    	MessageBox dialog=new MessageBox(shell,SWT.OK|SWT.ICON_INFORMATION);
        dialog.setText("Info");
        dialog.setMessage(msg);
        dialog.open();
    }
	
}
