package service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.wb.swt.SWTResourceManager;

import app.IpSettingDialog;
import app.MinerCheckApp;
import dao.IpSettingDao;
import utils.Constants;
import utils.IPRangeUtils;
import utils.PropertiesUtils;
import vo.IpsVO;

public class IpSettingServcie {
	
	public static boolean checkIp(Shell shell,String text,String label,String ipNote) {
		boolean result = false;
		if(text.indexOf("-")==-1) {
			infoDialog(shell,"必须为IP范围!\n如192.168.1.1-255");
			return false;
		}
		if(IPRangeUtils.isContainManyStr(text)) {
			infoDialog(shell,"可变位只能最后1位能包含字符'-'！");
			return false;
		}
		if(text.indexOf("=") != -1 || text.indexOf(",") != -1 || text.indexOf(":") !=-1) {
			infoDialog(shell,"IP段不合法，请检查！");
			return false;
		}
		String str[] = text.split("\\.");
		if(str.length > 4) {
			infoDialog(shell,"IP段不合法，请检查！");
			return false;
		}
		if(label==null || "".equals(label.trim())) {
			infoDialog(shell,"备注不能为空！");
			return false;
		}
		/*if(!checkIpRepeat(text)) {
			infoDialog(shell,"IP段存在重复地址，请检查！");
			return false;
		}*/
		
		IpsVO ipsVO = new IpsVO();
		ipsVO.setIpsName(label);
		ipsVO.setIpsStr(text);

		int flag = 0;
		for(int i=0;i<str.length;i++) {
			if(str[i].indexOf("-") != -1) {
				flag = i;
				break;
			}
		}
		if(str[flag].indexOf("-")!=-1) {
			String min = str[flag].split("-")[0];
			String max = str[flag].split("-")[1];
			String minIp = text.replace(str[flag], min);
			String maxIp = text.replace(str[flag], max);			
			if(IPRangeUtils.ipCheck(minIp) && IPRangeUtils.ipCheck(maxIp)) {
				if(ipNote != null) {
					//编辑Ip					
					ipsVO.setIndex(ipNote.split(":")[2]);
					if(IpSettingDao.updateIpStr(ipsVO)) {
						result = true;
					}					
				}else {
					//新增Ip
					ipsVO.setIndex(IpSettingDao.getNextIpIndex()+"");					
					if(IpSettingDao.insertIpStr(ipsVO)) {
						MinerCheckApp.ips = ipsVO;
						result = true;
					}					
				}
			}else {
				infoDialog(shell,"IP段不合法，请检查！");
			}
		}else {
			if(IPRangeUtils.ipCheck(text)) {
				if(ipNote != null) {
					//编辑Ip					
					ipsVO.setIndex(ipNote.split(":")[2]);
					if(IpSettingDao.updateIpStr(ipsVO)) {
						result = true;
					}					
				}else {
					//新增Ip
					ipsVO.setIndex(IpSettingDao.getNextIpIndex()+"");
					if(IpSettingDao.insertIpStr(ipsVO)) {	
						MinerCheckApp.ips = ipsVO;
						result = true;
					}					
				}
			}else {
				infoDialog(shell,"IP段不合法，请检查！");
			}
		}
		return result;
	}
	
	public static void readConfigIp(Composite ipListCompsite) {
		List<IpsVO> ipsList = IpSettingDao.getIpsList();
		if(ipsList != null) {
			for(int i=0;i<ipsList.size();i++) {
				IpsVO ipsVO = ipsList.get(i);
				Button tempButton = new Button(ipListCompsite, SWT.CHECK);
				tempButton.setData(ipsVO);
	            tempButton.setText(ipsVO.getIpsName()+":"+ipsVO.getIpsStr());
	            tempButton.setSelection(true);
	            tempButton.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
	            tempButton.setBounds(5, (ipListCompsite.getChildren().length - 1) * 25 + 5, 200, 17);
			}
			//ipListCompsite.setSize(ipListCompsite.computeSize(SWT.DEFAULT, SWT.DEFAULT));
		}
	}
	
	public static void loadConfigIp() {
		List<IpsVO> ipsList = IpSettingDao.getIpsList();
		if(ipsList != null) {
			String items[] = new String [ipsList.size()];
			for(int i=0;i<ipsList.size();i++) {
				IpsVO ipsVO = ipsList.get(i);				
	            items[i] = ipsVO.getIpsName()+":"+ipsVO.getIpsStr();
			}
			MinerCheckApp.combo.setItems(items);
		}
	}
	
	public static void removeConfigIp(List<Integer> indexLists) {
		for(Integer str_index:indexLists) {
			IpSettingDao.deleteIpStr(str_index);
		}
	}
	
	public static boolean checkIpRepeat(String text) {
		List<String> list = getIps();
		if(text.indexOf("-")==-1) {
			//不存在多IP
			list.add(text);
		}else {
			//存在多IP
			String str[] = text.split("\\.");
			int flag = 0;
			for(int j=0;j<str.length;j++) {
				if(str[j].indexOf("-") != -1) {
					flag = j;
					break;
				}
			}
			String min = str[flag].split("-")[0];
			String max = str[flag].split("-")[1];
			for(int k=Integer.parseInt(min);k<=Integer.parseInt(max);k++) {
				String ip = text.replace(str[flag], k+"");
				list.add(ip);
			}
		}
		return hasSame(list);
	}
	
	//判定是否有重复IP
	public static boolean hasSame(List<? extends Object> list)  
    {  
        if(null == list)  
            return false;  
        return list.size() == new HashSet<Object>(list).size();  
    }
	
	//获取配置文件里IP集合
	public static List<String> getIps(){
		List<String> list = new ArrayList<String>();
//		List<IpsVO> ipsLists = PropertiesUtils.getIpsListByConfig();//配置文件
		List<IpsVO> ipsLists = IpSettingDao.getIpsList();//数据库文件
		if(ipsLists!=null && !ipsLists.isEmpty()) {
			for(int i=0;i<ipsLists.size();i++) {
				String ips= ipsLists.get(i).getIpsStr();				
				if(ips.indexOf("-")==-1) {
					//不存在多IP
					list.add(ips);
				}else {
					//存在多IP
					String str[] = ips.split("\\.");
					int flag = 0;
					for(int j=0;j<str.length;j++) {
						if(str[j].indexOf("-") != -1) {
							flag = j;
							break;
						}
					}
					String min = str[flag].split("-")[0];
					String max = str[flag].split("-")[1];
					for(int k=Integer.parseInt(min);k<=Integer.parseInt(max);k++) {
						String ip = ips.replace(str[flag], k+"");
						list.add(ip);
					}					

				}
			}
		}
		return list;
	}
	
	//组选
	public static void groupSelect(String text,Table table,boolean select){
		List<String> list = new ArrayList<String>();
		//存在多IP
		String str[] = text.split("\\.");
		int flag = 0;
		for(int j=0;j<str.length;j++) {
			if(str[j].indexOf("-") != -1) {
				flag = j;
				break;
			}
		}
		String min = str[flag].split("-")[0];
		String max = str[flag].split("-")[1];
		for(int k=Integer.parseInt(min);k<=Integer.parseInt(max);k++) {
			String ip = text.replace(str[flag], k+"");
			list.add(ip);
		}
		TableItem[] t = table.getItems();
		if(select) {
			//选中
			for(int i=0;i<t.length;i++) {
				list.add(t[i].getText(Constants.ipIndex));
				if(!hasSame(list)) {
					t[i].setChecked(true);
				}
				list.remove(list.size()-1);
			}
		}else {
			//不选中
			for(int i=0;i<t.length;i++) {
				list.add(t[i].getText(Constants.ipIndex));
				if(!hasSame(list)) {
					t[i].setChecked(false);
				}
				list.remove(list.size()-1);
			}
		}
	}
	
	public static void infoDialog(Shell shell,String msg) {
    	MessageBox dialog=new MessageBox(shell,SWT.OK|SWT.ICON_INFORMATION);
        dialog.setText("提示");
        dialog.setMessage(msg);
        dialog.open();
    }
}
