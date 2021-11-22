package common;

import java.util.ArrayList;
import java.util.List;

import vo.MinerParamVO;

public class AppSettingConfig {

	public static Integer SCAN_TIMEOUT = 1;
	public static Integer SCAN_THREAD = 20;
	public static Integer MONITOR_TIME = 3;
	
	public static MinerParamVO initMinerF5() {
		MinerParamVO vo = new MinerParamVO();
		vo.setType("F5");
		vo.setUsername("admin");
		vo.setPassword("123456");
		vo.setPower("3000");
		vo.setCpWarn("60");
		vo.setMaxVoltage("62");
		vo.setMaxCurrent("63");
		vo.setMinFanSpeed("5400");
		vo.setMaxFanSpeed("6800");
		vo.setMaxRefuseRate("20");
		vo.setMaxPsuInOutTemprature("60");
		vo.setMaxTemprature("80");
		vo.setMinTemprature("30");
		vo.setScanStatus("success");
		return vo;
	}
	
	public static MinerParamVO initMinerF5i(){
		MinerParamVO vo = new MinerParamVO();
		vo.setType("F5+");
		vo.setUsername("admin");
		vo.setPassword("123456");
		vo.setPower("3000");
		vo.setCpWarn("60");
		vo.setMaxVoltage("62");
		vo.setMaxCurrent("63");
		vo.setMinFanSpeed("5600");
		vo.setMaxFanSpeed("6500");
		vo.setMaxRefuseRate("20");
		vo.setMaxPsuInOutTemprature("51");
		vo.setMaxTemprature("75");
		vo.setMinTemprature("30");
		vo.setScanStatus("success");
		return vo;
	}
	
	public static MinerParamVO initMinerF9(){
		MinerParamVO vo = new MinerParamVO();
		vo.setType("F9");
		vo.setUsername("admin");
		vo.setPassword("admin@miner");
		vo.setPower("3600");
		vo.setCpWarn("60");
		vo.setMaxVoltage("62");
		vo.setMaxCurrent("63");
		vo.setMinFanSpeed("5600");
		vo.setMaxFanSpeed("6500");
		vo.setMaxRefuseRate("20");
		vo.setMaxPsuInOutTemprature("51");
		vo.setMaxTemprature("100");
		vo.setMinTemprature("30");
		vo.setScanStatus("success");
		return vo;
	}
	
	public static List<MinerParamVO>  initMinerVO() {
		List<MinerParamVO> list = new ArrayList<MinerParamVO>();
		list.add(initMinerF5());
		list.add(initMinerF5i());
		list.add(initMinerF9());

		return list;
	}
}
