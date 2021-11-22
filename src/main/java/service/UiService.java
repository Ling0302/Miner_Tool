package service;

import app.MinerCheckApp;
import common.LangConfig;

public class UiService {

	public static void UiLang() {
		MinerCheckApp.shell.setText("BYY矿机老化工具-V1.0");
		
		MinerCheckApp.tabItem_1.setText(LangConfig.getKey("app.tab.scan"));
		MinerCheckApp.tabItem_3.setText(LangConfig.getKey("app.tab.staticIp"));
		MinerCheckApp.tabItem_4.setText(LangConfig.getKey("app.tab.upgrade"));
		
		//UI-scan
		MinerCheckApp.btnCheck_select_all.setText(LangConfig.getKey("app.check.ipRange"));
		MinerCheckApp.button_display_success.setText(LangConfig.getKey("app.check.show"));
		MinerCheckApp.button_scan.setText(LangConfig.getKey("app.check.scanMiner"));
		/*if(MinerCheckApp.isMonitor) {			
			MinerCheckApp.button_monitor.setText(LangConfig.getKey("app.check.stopMonitor"));
			MinerCheckApp.lblNewLabel_10.setText(LangConfig.getKey("app.check.isMonitor"));			
		}else {
			MinerCheckApp.button_monitor.setText(LangConfig.getKey("app.check.monitorMiner"));
			MinerCheckApp.lblNewLabel_10.setText(LangConfig.getKey("app.check.noMonitor"));
		}*/
		//MinerCheckApp.button_config_all.setText(LangConfig.getKey("app.check.configAll"));
		//MinerCheckApp.button_config_select.setText(LangConfig.getKey("app.check.configSelect"));
		//MinerCheckApp.button_reset.setText(LangConfig.getKey("app.check.restartMiner"));
		MinerCheckApp.button_export.setText(LangConfig.getKey("app.check.export"));
		MinerCheckApp.button_lighton.setText("点红灯");
		//MinerCheckApp.button_setting.setText(LangConfig.getKey("app.check.setting"));
		//MinerCheckApp.lblNewLabel_11.setText(LangConfig.getKey("app.check.ipNum"));
		MinerCheckApp.lblNewLabel_24.setText(LangConfig.getKey("app.check.lang"));
		
		MinerCheckApp.btnCheckButton_1.setText(LangConfig.getKey("app.check.pool1"));
		MinerCheckApp.btnCheckButton_2.setText(LangConfig.getKey("app.check.pool2"));
		MinerCheckApp.btnCheckButton_3.setText(LangConfig.getKey("app.check.pool3"));
		MinerCheckApp.lblNewLabel.setText(LangConfig.getKey("app.check.user1"));
		MinerCheckApp.lblNewLabel_3.setText(LangConfig.getKey("app.check.pwd1"));
		MinerCheckApp.lblNewLabel_6.setText(LangConfig.getKey("app.check.suffix"));
		MinerCheckApp.lblNewLabel_1.setText(LangConfig.getKey("app.check.user2"));
		MinerCheckApp.lblNewLabel_4.setText(LangConfig.getKey("app.check.pwd2"));
		MinerCheckApp.lblNewLabel_7.setText(LangConfig.getKey("app.check.suffix"));
		MinerCheckApp.lblNewLabel_2.setText(LangConfig.getKey("app.check.user3"));
		MinerCheckApp.lblNewLabel_5.setText(LangConfig.getKey("app.check.pwd3"));
		MinerCheckApp.lblNewLabel_8.setText(LangConfig.getKey("app.check.suffix"));
		MinerCheckApp.button_14.setText(LangConfig.getKey("app.check.nochange"));
		MinerCheckApp.button_15.setText(LangConfig.getKey("app.check.clear"));
		MinerCheckApp.button_17.setText(LangConfig.getKey("app.check.nochange"));
		MinerCheckApp.button_18.setText(LangConfig.getKey("app.check.clear"));
		MinerCheckApp.button_12.setText(LangConfig.getKey("app.check.nochange"));
		MinerCheckApp.button_13.setText(LangConfig.getKey("app.check.clear"));
		
		MinerCheckApp.lblNewLabel_12.setText(LangConfig.getKey("app.check.worker"));
		MinerCheckApp.lblavg.setText(LangConfig.getKey("app.check.hashrate"));
		MinerCheckApp.label.setText(LangConfig.getKey("app.check.to"));
		MinerCheckApp.button_2.setText(LangConfig.getKey("app.check.find"));
		MinerCheckApp.button_3.setText(LangConfig.getKey("app.check.all"));
		
		MinerCheckApp.tblclmn_datetime.setText("日期");
		//MinerCheckApp.tblclmn_index.setText("序号");
		MinerCheckApp.tblclmn_ip.setText(LangConfig.getKey("app.check.table.ip"));
		MinerCheckApp.tblclmn_status.setText(LangConfig.getKey("app.check.table.status"));
		MinerCheckApp.tblclmn_minerType.setText(LangConfig.getKey("app.check.table.minerType"));
		MinerCheckApp.tblclmn_binType.setText("BIN");
		MinerCheckApp.tblclmn_pool.setText(LangConfig.getKey("app.check.table.pool"));
		MinerCheckApp.tblclmn_worker.setText(LangConfig.getKey("app.check.table.worker"));
		MinerCheckApp.tblclmn_realHash.setText(LangConfig.getKey("app.check.table.realHash"));
		MinerCheckApp.tblclmn_avgHash.setText(LangConfig.getKey("app.check.table.avgHash"));
		MinerCheckApp.tblclmn_runningTime.setText(LangConfig.getKey("app.check.table.runningTime"));
		MinerCheckApp.tblclmn_firmwareVersion.setText(LangConfig.getKey("app.check.table.firmwareVersion"));
		//MinerCheckApp.tblclmn_softVersion.setText(LangConfig.getKey("app.check.table.softVersion"));
		MinerCheckApp.tblclmn_temperature.setText(LangConfig.getKey("app.check.table.temperature"));
		MinerCheckApp.tblclmn_fanSpeed.setText(LangConfig.getKey("app.check.table.fanSpeed"));
		MinerCheckApp.tblclmn_fanDuty.setText(LangConfig.getKey("app.check.table.fanDuty"));
		MinerCheckApp.tblclmn_devFreq.setText(LangConfig.getKey("app.check.table.devFreq"));
		MinerCheckApp.tblclmn_chipVolt.setText(LangConfig.getKey("app.check.table.chipVolt"));
		MinerCheckApp.tblclmn_volt.setText(LangConfig.getKey("app.check.table.volt"));
		MinerCheckApp.tblclmn_networkType.setText(LangConfig.getKey("app.check.table.networkType"));   
		MinerCheckApp.tblclmn_macAddress.setText(LangConfig.getKey("app.check.table.macAddress"));
		
		//UI-static ip
		MinerCheckApp.lblNewLabel_14.setText(LangConfig.getKey("app.static.network"));
	    MinerCheckApp.lbl_14.setText(LangConfig.getKey("app.static.network.nextIp"));
	    MinerCheckApp.lbl_15.setText(LangConfig.getKey("app.static.network.netmask"));
	    MinerCheckApp.label_1.setText(LangConfig.getKey("app.static.network.gateway"));
	        
	    MinerCheckApp.lblNewLabel_15.setText(LangConfig.getKey("app.static.poolConfig"));
	    MinerCheckApp.label_kc1.setText(LangConfig.getKey("app.static.poolConfig.pool"));
	    MinerCheckApp.label_kc2.setText(LangConfig.getKey("app.static.poolConfig.user"));
	    MinerCheckApp.label_kc3.setText(LangConfig.getKey("app.static.poolConfig.pwd"));
	    MinerCheckApp.label_kc4.setText(LangConfig.getKey("app.static.poolConfig.suffix"));   
	    MinerCheckApp.button_kc_1.setText(LangConfig.getKey("app.static.poolConfig.nochange"));
	    MinerCheckApp.button_kc_2.setText(LangConfig.getKey("app.static.poolConfig.none"));
	    MinerCheckApp.button_kc_4.setText(LangConfig.getKey("app.static.poolConfig.nochange"));
	    MinerCheckApp.button_kc_5.setText(LangConfig.getKey("app.static.poolConfig.none"));  
	    MinerCheckApp.button_kc_7.setText(LangConfig.getKey("app.static.poolConfig.nochange"));
	    MinerCheckApp.button_kc_8.setText(LangConfig.getKey("app.static.poolConfig.none"));
	    
	    MinerCheckApp.lblNewLabel_18.setText(LangConfig.getKey("app.static.pointConfig"));
	    MinerCheckApp.lbl_hjh.setText(LangConfig.getKey("app.static.pointConfig.shelfNum"));
	    MinerCheckApp.lbl_cs.setText(LangConfig.getKey("app.static.pointConfig.layerNum"));
	    MinerCheckApp.lbl_wzs.setText(LangConfig.getKey("app.static.pointConfig.pointNum"));
	    MinerCheckApp.label_nextPoint.setText(LangConfig.getKey("app.static.pointConfig.nextPoint"));
	    MinerCheckApp.lbl_nextLayer.setText(LangConfig.getKey("app.static.pointConfig.nextLayer"));
	    MinerCheckApp.lbl_step.setText(LangConfig.getKey("app.static.pointConfig.step"));
	    
	    MinerCheckApp.button_4.setText(LangConfig.getKey("app.static.tool.all"));
	    MinerCheckApp.lblNewLabel_16.setText(LangConfig.getKey("app.static.tool.status"));
	    MinerCheckApp.btnCheckButton.setText(LangConfig.getKey("app.static.tool.autoConfig"));
	    MinerCheckApp.btnCheckButton_4.setText(LangConfig.getKey("app.static.tool.configStaticIp"));
	    MinerCheckApp.btnCheckButton_5.setText(LangConfig.getKey("app.static.tool.configPool"));
	    MinerCheckApp.btnCheckButton_16.setText(LangConfig.getKey("app.static.tool.configPoint"));
	    MinerCheckApp.btnNewButton_1.setText(LangConfig.getKey("app.static.tool.applyConfig"));
	    
	    MinerCheckApp.tb_miner_ip.setText(LangConfig.getKey("app.static.table.currIp"));
	    MinerCheckApp.tb_target_ip.setText(LangConfig.getKey("app.static.table.targetIp"));
	    MinerCheckApp.tb_miner_mac.setText(LangConfig.getKey("app.static.table.macAddress"));
	    MinerCheckApp.tb_congfig_ip_status.setText(LangConfig.getKey("app.static.table.ipConfigResult"));
	    MinerCheckApp.tb_congfig_pool_status.setText(LangConfig.getKey("app.static.table.poolConfigResult"));
	    MinerCheckApp.tb_congfig_point_status.setText(LangConfig.getKey("app.static.table.pointConfigResult"));
	    MinerCheckApp.tb_zwym.setText(LangConfig.getKey("app.static.table.netmask"));
	    MinerCheckApp.tb_mrwg.setText(LangConfig.getKey("app.static.table.gateway"));
	    MinerCheckApp.tb_dns.setText(LangConfig.getKey("app.static.table.dns"));
	    MinerCheckApp.tb_point.setText(LangConfig.getKey("app.static.table.point"));
	    MinerCheckApp.tb_pool1.setText(LangConfig.getKey("app.static.table.pool1"));
	    MinerCheckApp.tb_worker1.setText(LangConfig.getKey("app.static.table.worker1"));
	    MinerCheckApp.tb_pwd1.setText(LangConfig.getKey("app.static.table.pwd1"));
	    MinerCheckApp.tb_pool2.setText(LangConfig.getKey("app.static.table.pool2"));
	    MinerCheckApp.tb_worker2.setText(LangConfig.getKey("app.static.table.worker2"));
	    MinerCheckApp.tb_pwd2.setText(LangConfig.getKey("app.static.table.pwd2"));
	    MinerCheckApp.tb_pool3.setText(LangConfig.getKey("app.static.table.pool3"));
	    MinerCheckApp.tb_worker3.setText(LangConfig.getKey("app.static.table.worker3"));
	    MinerCheckApp.tb_pwd3.setText(LangConfig.getKey("app.static.table.pwd3"));
	    
	    //upgrade ui
	    MinerCheckApp.lblNewLabel_19.setText(LangConfig.getKey("app.upgrade.ipRange"));
	    MinerCheckApp.btnNewButton_2.setText(LangConfig.getKey("app.upgrade.minerFind"));
	    //MinerCheckApp.lblNewLabel_20.setText(LangConfig.getKey("app.upgrade.username"));
	    //MinerCheckApp.lblNewLabel_21.setText(LangConfig.getKey("app.upgrade.password"));
	    MinerCheckApp.btnNewButton_3.setText(LangConfig.getKey("app.upgrade.fileSelect"));
	    MinerCheckApp.button_6.setText(LangConfig.getKey("app.upgrade.upgradeStart"));
	    MinerCheckApp.btnCheckButton_6.setText(LangConfig.getKey("app.upgrade.allSelect"));
	    
	    MinerCheckApp.table_ip.setText(LangConfig.getKey("app.upgrade.table.ipAddress"));
	    MinerCheckApp.table_status.setText(LangConfig.getKey("app.upgrade.table.status"));
	    MinerCheckApp.table_mac.setText(LangConfig.getKey("app.upgrade.table.macAddress"));
	    MinerCheckApp.table_type.setText(LangConfig.getKey("app.upgrade.table.minerType"));
	    MinerCheckApp.table_version.setText(LangConfig.getKey("app.upgrade.table.firmwareVersion"));
	    MinerCheckApp.table_result.setText(LangConfig.getKey("app.upgrade.table.actionResult"));
	    MinerCheckApp.table_note.setText(LangConfig.getKey("app.upgrade.table.note"));
		
		
		
	}
}
