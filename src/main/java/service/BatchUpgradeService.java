package service;

import java.io.File;
import java.util.List;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import app.MinerCheckApp;
import utils.SftpUtils;

public class BatchUpgradeService {


	public static void upgrade(Shell shell, List<String> ips) {
		
		if(MinerCheckApp.isScan) {
			infoDialog(shell,"正在扫描！请稍后再试");
			return;
		}
		
		String path = "C:\\swupdate_40_20220102.tar.gz";
		String filename = "swupdate_40_20220102.tar.gz";
		String user = "root";
		String pass = "F9Miner1234";
		
		for(int i =0; i<ips.size(); i++) {
			String ip = ips.get(i);
			System.out.println("ip:" + ip);

			//校验
			SftpUtils sftpUtils = new SftpUtils(ip, 22, user, pass);
			int success = sftpUtils.upload("/tmp", new File(path), null);
			
			sftpUtils.execute("chmod 755 /tmp/" + filename);
	        sftpUtils.execute("nohup sudo system_update online /tmp/" + filename + "> /tmp/up.log 2>&1");

	        infoDialog(shell, ip+"固件升级成功！");
			
		}
	}
	
	public static void setnologo(Shell shell, List<String> ips) {
			
			if(MinerCheckApp.isScan) {
				infoDialog(shell,"正在扫描！请稍后再试");
				return;
			}
			
			String user = "root";
			String pass = "F9Miner1234";
			String ipStr = "";
			// String filename = "./script/util_model.php";
			
			for(int i =0; i<ips.size(); i++) {
				String ip = ips.get(i);
				System.out.println("ip:" + ip);
				ipStr = ipStr + ip;
				SftpUtils sftpUtils = new SftpUtils(ip, 22, user, pass);
		        sftpUtils.execute("miner_cfg MINER_WEB_LOGO set MINER_WEB_NOLOGO");
		        // sftpUtils.upload("/www/application/models", new File(filename), null);
			}
			infoDialog(shell, ipStr+"设置无LOGO成功！");
	}
		
	public static void infoDialog(Shell shell,String msg) {
    	MessageBox dialog=new MessageBox(shell,SWT.OK|SWT.ICON_INFORMATION);
        dialog.setText("Info");
        dialog.setMessage(msg);
        dialog.open();
    }
	
}
