package service;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.wb.swt.SWTResourceManager;

import com.alibaba.fastjson.JSONObject;

import utils.Constants;
import utils.PropertiesUtils;
import utils.StringUtils;
import vo.MinerParamVO;

public class MinerRuleService {

	public static void ruleCheck(JSONObject jol,MinerParamVO rule,TableItem tableItem) {
		//门限值
		Float power = Float.parseFloat(rule.getPower());//功耗
    	Float cpWarn = Float.parseFloat(rule.getCpWarn());//算力低报警值
    	Float maxVoltage = Float.parseFloat(rule.getMaxVoltage());//最大电压
    	Float maxCurrent = Float.parseFloat(rule.getMaxCurrent());//最大电流
    	Float minFanSpeed = Float.parseFloat(rule.getMinFanSpeed());//风扇最低转速
    	Float maxFanSpeed = Float.parseFloat(rule.getMaxFanSpeed());//风扇最高转速
    	Float maxRefuseRate = Float.parseFloat(rule.getMaxCurrent());//最大拒绝率
    	Float maxPsuInOutTemprature = Float.parseFloat(rule.getMaxPsuInOutTemprature());//进出最高温
    	Float maxTemprature = Float.parseFloat(rule.getMaxTemprature());//温度上限
    	Float minTemprature = Float.parseFloat(rule.getMinTemprature());//温度下限
//    	String scanStatus = rule.getScanStatus();//	扫频状态
    	Boolean isFanFullSpeedMode = jol.getBoolean("isFanFullSpeedMode") == null? false : jol.getBoolean("isFanFullSpeedMode");
    	//当前值
    	//Float curr_hashCP = jol.getFloat("hashRate") == null ? 0.0f : jol.getFloat("hashRate")/1024;//实测算力
    	//Float curr_hashAvg = jol.getFloat("avgHashRate") == null ? 0.0f : jol.getFloat("avgHashRate")/1024;//平均算力
    	Float curr_hashCP = jol.get("hashRate") == null || jol.get("hashRate").equals("")? 0.0f : jol.getFloat("hashRate")/1024;//实测算力
    	Float curr_hashAvg = jol.get("avgHashRate") == null || jol.get("avgHashRate").equals("")?  0.0f : jol.getFloat("avgHashRate")/1024;//平均算力
    	String curr_temperature1 = jol.getString("temperature1") == null ? "" : jol.getString("temperature1");
    	String curr_temperature2 = jol.getString("temperature2") == null ? "" : jol.getString("temperature2");
    	String curr_minFanSpeed = jol.getString("minSpeed") == null ? "" : jol.getString("minSpeed");
    	String curr_maxFanSpeed = jol.getString("maxSpeed") == null ? "" : jol.getString("maxSpeed") ;
    	String curr_psuMaxV = jol.getString("psuMaxV") == null ? "" : jol.getString("psuMaxV");
    	String curr_psuMaxI = jol.getString("psuMaxI") == null ? "" : jol.getString("psuMaxI");
    	String curr_psuPower1 = jol.getString("psuPower1") == null ? "" : jol.getString("psuPower1");
    	String curr_psuPower2 = jol.getString("psuPower2") == null ? "" : jol.getString("psuPower2");
    	String curr_psuMaxInTemp = jol.getString("psuMaxInTemp") == null ? "" : jol.getString("psuMaxInTemp");
    	String curr_psuMaxOutTemp = jol.getString("psuMaxOutTemp") == null ? "" : jol.getString("psuMaxOutTemp");
    	String curr_rejectedRate = jol.getString("rejectedRate") == null ? "" : jol.getString("rejectedRate");
    	String runtime = jol.get("upTime")==null || jol.get("upTime").equals("") ? "0": StringUtils.round(jol.getFloat("upTime")/60,2);
    	float runtimeNum = Float.parseFloat(runtime);
    	boolean flag = true;//异常标志位，false为有异常
    	/*
    	if(curr_hashCP < cpWarn) {
    		//算力低-异常
    		flag = false;
    		tableItem.setForeground(Constants.cpIndex,SWTResourceManager.getColor(SWT.COLOR_RED));
    	}*/
    	System.out.println("curr_hashAvg:"+curr_hashAvg);
    	System.out.println("cpWarn:"+cpWarn);
    	if(curr_hashAvg < cpWarn) {
    		//平均算力低-异常
    		if(runtimeNum >= 60) {
    			flag = false;
        		tableItem.setForeground(Constants.avgcpIndex,SWTResourceManager.getColor(SWT.COLOR_RED));
    		}    		
    	}
    	if(!"".equals(curr_temperature1)) {
    		if(Float.parseFloat(curr_temperature1) > maxTemprature || Float.parseFloat(curr_temperature1) < minTemprature) {
    			//温度1-异常
    			flag = false;
    			tableItem.setForeground(Constants.tempUpdownIndex,SWTResourceManager.getColor(SWT.COLOR_RED));
    		}
    	}else {
    		flag = false;
    	}
    	if(!"".equals(curr_temperature2)) {
    		if(Float.parseFloat(curr_temperature2) > maxTemprature || Float.parseFloat(curr_temperature2) < minTemprature) {
    			//温度2-异常
    			flag = false;
    			tableItem.setForeground(Constants.tempUpdownIndex,SWTResourceManager.getColor(SWT.COLOR_RED));
    		}
    	}else {
    		flag = false;
    	}
    	if(isFanFullSpeedMode) {
    		//全速模式才开启校验    		
        	if(!"".equals(curr_minFanSpeed)) {
        		if(Float.parseFloat(curr_minFanSpeed) < minFanSpeed) {
        			//转速低-异常
        			flag = false;
        			tableItem.setForeground(Constants.fanIndex,SWTResourceManager.getColor(SWT.COLOR_RED));
        		}
        	}else {
        		flag = false;
        	}
        	if(!"".equals(curr_maxFanSpeed)) {
        		if(Float.parseFloat(curr_maxFanSpeed) > maxFanSpeed) {
        			//转速高-异常
        			flag = false;
        			tableItem.setForeground(Constants.fanIndex,SWTResourceManager.getColor(SWT.COLOR_RED));
        		}
        	}else {
        		flag = false;
        	}
    	}
    	
    	if(!"".equals(curr_psuMaxV)) {
    		//最大电压-异常
    		if(Float.parseFloat(curr_psuMaxV) > maxVoltage) {
    			flag = false;
    			tableItem.setForeground(Constants.uiIndex,SWTResourceManager.getColor(SWT.COLOR_RED));
    		}
    	}else {
    		flag = false;
    	}
    	if(!"".equals(curr_psuMaxI)) {
    		//最大电流-异常
    		if(Float.parseFloat(curr_psuMaxI) > maxCurrent) {
    			flag = false;
    			tableItem.setForeground(Constants.uiIndex,SWTResourceManager.getColor(SWT.COLOR_RED));
    		}
    	}else {
    		flag = false;
    	}
    	if(!"".equals(curr_psuPower1)) {
    		//电源功率1-异常
    		if(Float.parseFloat(curr_psuPower1) > power) {
    			flag = false;
    			tableItem.setForeground(Constants.powerIndex,SWTResourceManager.getColor(SWT.COLOR_RED));
    		}
    	}else {
    		flag = false;
    	}
    	if(!"".equals(curr_psuPower2)) {
    		//电源功率2-异常
    		if(Float.parseFloat(curr_psuPower2) > power) {
    			flag = false;
    			tableItem.setForeground(Constants.powerIndex,SWTResourceManager.getColor(SWT.COLOR_RED));
    		}
    	}else {
    		flag = false;
    	}
    	if(!"".equals(curr_psuMaxInTemp)) {
    		//进温-异常
    		if(Float.parseFloat(curr_psuMaxInTemp) > maxPsuInOutTemprature) {
    			flag = false;
    			tableItem.setForeground(Constants.InOutTempIndex,SWTResourceManager.getColor(SWT.COLOR_RED));
    		}
    		
    	}else {
    		flag = false;
    	}
    	if(!"".equals(curr_psuMaxOutTemp)) {
    		//出温-异常
    		if(Float.parseFloat(curr_psuMaxOutTemp) > maxPsuInOutTemprature) {
    			flag = false;
    			tableItem.setForeground(Constants.InOutTempIndex,SWTResourceManager.getColor(SWT.COLOR_RED));
    		}
    	}else {
    		flag = false;
    	}
    	if(!"".equals(curr_rejectedRate)) {
    		//拒绝率-异常
    		if(Float.parseFloat(curr_rejectedRate) > maxRefuseRate) {
    			flag = false;
    			tableItem.setForeground(Constants.rejectIndex,SWTResourceManager.getColor(SWT.COLOR_RED));
    		}
    	}else {
    		flag = false;
    	}
    	
    	if(!flag) {
    		//验证不通过
    		tableItem.setBackground(Constants.statusIndex,SWTResourceManager.getColor(Constants.STATUS_COLOR[2]));
    		tableItem.setText(Constants.statusIndex, Constants.STATUS[2]);
    		tableItem.setForeground(Constants.statusIndex,SWTResourceManager.getColor(SWT.COLOR_WHITE));
    		tableItem.setForeground(Constants.ipIndex,SWTResourceManager.getColor(SWT.COLOR_RED));
    	}else {
    		tableItem.setBackground(Constants.statusIndex,SWTResourceManager.getColor(Constants.STATUS_COLOR[jol.getInteger("status") - 1]));
    	}
    	if("ON".equals(jol.getString("hashboardStatus"))) {
    		//睡眠状态
    		tableItem.setBackground(Constants.statusIndex,SWTResourceManager.getColor(Constants.STATUS_COLOR[2]));
    		tableItem.setText(Constants.statusIndex, "休眠");
    		tableItem.setForeground(Constants.statusIndex,SWTResourceManager.getColor(SWT.COLOR_WHITE));
    	}
    	
	}
	
	//Table已有数据增加规则css显示
	public static void addRuleCss(TableItem tableItem) {
		String minertype = tableItem.getText(Constants.minertypeIndex).trim();//矿机类型
		JSONObject jol = new JSONObject(); 
    	
    	String cp = tableItem.getText(Constants.cpIndex).trim();//实时算力
    	String avgCp = tableItem.getText(Constants.avgcpIndex).trim();//平均算力
    	String temperature = tableItem.getText(Constants.tempUpdownIndex).trim();//上/下温度
    	String fan = tableItem.getText(Constants.fanIndex).trim();//风扇转速
    	String ui = tableItem.getText(Constants.uiIndex).trim();//电源电压/电流
    	String power = tableItem.getText(Constants.powerIndex).trim();//电源功率
    	String inOutTemp = tableItem.getText(Constants.InOutTempIndex).trim();//电源进/出温
    	String reject = tableItem.getText(Constants.rejectIndex).trim();//拒绝率
    	String statusText = tableItem.getText(Constants.statusIndex).trim();//状态
    	
    	Float hashRate;//实时算力
    	try {
    		if("".equals(cp)) {
    			hashRate = 0.0f;
    		}else {
    			hashRate = Float.parseFloat(cp) * 1024;
    		}    		
    	}catch(Exception e) {
    		hashRate = 0.0f;
    	}
    	jol.put("hashRate", hashRate);
    	
    	Float avgHashRate;//平均算力
    	try {
    		if("".equals(avgCp)) {
    			avgHashRate = 0.0f;
    		}else {
    			avgHashRate = Float.parseFloat(avgCp) * 1024;
    		}    		
    	}catch(Exception e) {
    		avgHashRate = 0.0f;
    	}
    	jol.put("avgHashRate", avgHashRate);
    	
    	String temperature1,temperature2;//温度-上/下
    	if(temperature.indexOf("/") != -1) {
    		temperature1 = temperature.split("/")[0];
    		temperature2 = temperature.split("/")[1];
    	}else {
    		temperature1 = null;
    		temperature2 = null;
    	}
    	jol.put("temperature1", temperature1);
    	jol.put("temperature2", temperature2);
    	
    	String minSpeed,maxSpeed;//最低/高转速
    	if(fan.indexOf("/") != -1) {
    		minSpeed = fan.split("/")[0];
    		maxSpeed = fan.split("/")[1];
    	}else {
    		minSpeed = null;
    		maxSpeed = null;
    	}
    	jol.put("minSpeed", minSpeed);
    	jol.put("maxSpeed", maxSpeed);
    	
    	String psuMaxV,psuMaxI;//电源电压/电流
    	if(ui.indexOf("/") != -1) {
    		psuMaxV = ui.split("/")[0];
    		psuMaxI = ui.split("/")[1];
    	}else {
    		psuMaxV = null;
    		psuMaxI = null;
    	}
    	jol.put("psuMaxV", psuMaxV);
    	jol.put("psuMaxI", psuMaxI);
    	
    	String psuPower1,psuPower2;//电源功率
    	if(power.indexOf("/") != -1) {
    		psuPower1 = power.split("/")[0];
    		psuPower2 = power.split("/")[1];
    	}else {
    		psuPower1 = null;
    		psuPower2 = null;
    	}
    	jol.put("psuPower1", psuPower1);
    	jol.put("psuPower2", psuPower2);
    	
    	String psuMaxInTemp,psuMaxOutTemp;//电源进/出温
    	if(inOutTemp.indexOf("/") != -1) {
    		psuMaxInTemp = inOutTemp.split("/")[0];
    		psuMaxOutTemp = inOutTemp.split("/")[1];
    	}else {
    		psuMaxInTemp = null;
    		psuMaxOutTemp = null;
    	}
    	jol.put("psuMaxInTemp", psuMaxInTemp);
    	jol.put("psuMaxOutTemp", psuMaxOutTemp);
    	
    	String rejectedRate;//拒绝率
    	if(!"".equals(reject)) {
    		rejectedRate = reject.replace("%", "");
    	}else {
    		rejectedRate = null;
    	}
    	jol.put("rejectedRate", rejectedRate);
    	
    	Integer status;//状态
    	String hashboardStatus;//睡眠状态
    	if("正常".equals(statusText)) {
    		status = 1;
    	}else if("超时".equals(statusText)) {
    		status = 2;
    	}else if("异常".equals(statusText)) {
    		status = 3;
    	}else if("休眠".equals(statusText)) {
    		status = 1;
    		hashboardStatus = "ON";
    		jol.put("hashboardStatus", hashboardStatus);
    	}else {
    		//其它定义为异常
    		status = 3;
    	}
    	jol.put("status", status);
    	
		//规则配置
        MinerParamVO rule = PropertiesUtils.getRule(minertype);
        if(rule != null) {
        	ruleCheck(jol,rule,tableItem);                  	
        }
	}
}
