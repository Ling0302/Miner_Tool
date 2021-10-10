package vo;

import java.util.ArrayList;
import java.util.List;

public class Config
{
	public static String scanTimeout="1";
	public static String scanThreads="20";
	public static String monitorCycle="1";//监控周期
	public static String isMonitor="1";//是否监控 0-不监控
    
	public static String kinds="A1";
//    public static String type="A1";//矿机类别
//	public static String username="root";//矿机用户名
//	public static String password="root";//矿机密码
//	public static String power="3000";//功耗
//	public static String cpWarn="40";//算力低报警值
//	public static String maxVoltage="62";//最大电压
//	public static String maxCurrent="62";//最大电流
//	public static String minFanSpeed="5600";//风扇最低转速
//	public static String maxFanSpeed="6700";//风扇最高转速
//	public static String maxRefuseRate="20";//最大拒绝率
//	public static String maxPsuInOutTemprature="51";//进出最高温
//	public static String maxTemprature="75";//温度上限
//	public static String minTemprature="30";//温度下限
//	public static String scanStatus="success";//	扫频状态

	public static SetupVO getDefaultConfig() {
		SetupVO setupVO = new SetupVO();
		setupVO.setScanThreads(scanThreads);
		setupVO.setScanTimeout(scanTimeout);
		setupVO.setMonitorCycle(monitorCycle);
		setupVO.setIsMonitor(isMonitor);
		
		List<MinerParamVO> list = new ArrayList<MinerParamVO>();
		MinerParamVO vo = new MinerParamVO();
		vo.setType("A1");
		vo.setUsername("admin");
		vo.setPassword("123456");
		vo.setPower("3000");
		vo.setCpWarn("45");
		vo.setMaxVoltage("62");
		vo.setMaxCurrent("63");
		vo.setMinFanSpeed("5400");
		vo.setMaxFanSpeed("6800");
		vo.setMaxRefuseRate("20");
		vo.setMaxPsuInOutTemprature("60");
		vo.setMaxTemprature("80");
		vo.setMinTemprature("30");
		vo.setScanStatus("success");
		list.add(vo);
//		MinerParamVO vo2 = new MinerParamVO();
//		vo2.setType("A300");
//		vo2.setUsername("admin");
//		vo2.setPassword("123456");
//		vo2.setPower("3000");
//		vo2.setCpWarn("46");
//		vo2.setMaxVoltage("62");
//		vo2.setMaxCurrent("63");
//		vo2.setMinFanSpeed("5600");
//		vo2.setMaxFanSpeed("6500");
//		vo2.setMaxRefuseRate("20");
//		vo2.setMaxPsuInOutTemprature("51");
//		vo2.setMaxTemprature("75");
//		vo2.setMinTemprature("30");
//		vo2.setScanStatus("success");
//		list.add(vo2);
		MinerParamVO vo3 = new MinerParamVO();
		vo3.setType("B1");
		vo3.setUsername("admin");
		vo3.setPassword("123456");
		vo3.setPower("1800");
		vo3.setCpWarn("12");
		vo3.setMaxVoltage("62");
		vo3.setMaxCurrent("63");
		vo3.setMinFanSpeed("5400");
		vo3.setMaxFanSpeed("6800");
		vo3.setMaxRefuseRate("20");
		vo3.setMaxPsuInOutTemprature("60");
		vo3.setMaxTemprature("80");
		vo3.setMinTemprature("30");
		vo3.setScanStatus("success");
		list.add(vo3);
		setupVO.setMinerList(list);		
		return setupVO;
	}
	
}
