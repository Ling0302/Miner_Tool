package service;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import utils.HttpRequestUtils;
import utils.SftpUtils;

public class CgminerShellTest {
	
	public static int VOLT_MIN = 355;
	public static int VOLT_MAX = 370;
	public static int FREQ_MIN = 620;
	public static int FREQ_MAX = 645;
	public static int TEMP = 85;	
	public static String IP = "192.168.0.62";
	public static SftpUtils sftpUtils = new SftpUtils(IP, 22, "root", "root");

	public static void main(String args[]) throws InterruptedException {
//		f5YjTest();
		 
		 
		
//		for(int i = VOLT_MIN;i <= VOLT_MAX ; i = i + 5) {
//			for(int j = FREQ_MIN;j <= FREQ_MAX;j = j + 5) {
//				String param = i + " " + j + " " + TEMP;
//				//1.开启cgminer 10分钟
//				System.out.println("开启矿机IP:"+IP);
//				System.out.println("参数（电压|频率|温度）："+param);
//				startCgminer(param);
//				Thread.sleep(2000);
//				//Thread.sleep(1000*60*10);
//				
//				//2.API获取算力值
//				String result = HttpRequestUtils.get(IP, "/index.php/app/stats", null);
//				JSONObject jol1 = JSON.parseObject(result);
//				JSONObject jol2 = jol1.getJSONObject("pool");
//				Long hashrateLong = jol2.getLong("hashrate");
//				String hashrate = String.format("%.2f", hashrateLong.doubleValue()/1000/1000/1000/1000);
//				System.out.println("算力："+hashrate+" Th/s");
//				
//				//3.关闭cgminer
//				stopCgminer();
//				Thread.sleep(1000*5);
//				
//			}
//		}

	}
	
	public static void f5ScanFreq() throws InterruptedException {
		Scanner sc = new Scanner(System.in);
		for(int i = VOLT_MIN;i <= VOLT_MAX ; i = i + 5) {
			for(int j = FREQ_MIN;j <= FREQ_MAX;j = j + 5) {
				String param = i + " " + j + " " + TEMP;
				//1.开启cgminer 10分钟
				System.out.println("开启矿机IP:"+IP);
				System.out.println("参数（电压|频率|温度）："+param);
				startCgminer(param);
				Thread.sleep(2000);
				//Thread.sleep(1000*60*10);
				
				//2.API获取算力值
				String result = HttpRequestUtils.get(IP, "/index.php/app/stats", null);
				JSONObject jol1 = JSON.parseObject(result);
				JSONObject jol2 = jol1.getJSONObject("pool");
				Long hashrateLong = jol2.getLong("hashrate");
				String hashrate = String.format("%.2f", hashrateLong.doubleValue()/1000/1000/1000/1000);
				System.out.println("算力："+hashrate+" Th/s");
				
				System.out.println("请输入电源电压："); 
		        String volt = sc.nextLine(); 
		        System.out.println("电源实际电压："+volt); 
		        
		        System.out.println("请输入功耗："); 
		        String power = sc.nextLine(); 
		        System.out.println("功耗为："+power);
				
				//3.关闭cgminer
				stopCgminer();
				Thread.sleep(1000*5);
				
			}
		}
	}
	
	public static void startCgminer(String param) {		
		//System.out.println(param);
		System.out.println(sftpUtils.execute("sh /tmp/start_cgminer.sh " + param));
	}
	
	public static void stopCgminer() {		
		System.out.println("关闭矿机...");
		System.out.println(sftpUtils.execute("sh /tmp/stop_cgminer.sh "));
	}
	
	public static void f5YjTest() throws InterruptedException {
		String param1 = "325 580 85";
		String param2 = "345 580 85";
		String param3 = "355 620 85";
		String param4 = "360 630 85";
		String param5 = "370 645 85";
		List <String> list = new ArrayList<String>();
		//list.add(param1);
		list.add(param2);
		list.add(param3);
		list.add(param4);
		list.add(param5);
		for(String param : list) {
			//1.开启cgminer 10分钟
			System.out.println("开启矿机IP:"+IP);
			System.out.println("参数（电压|频率|温度）："+param);
			startCgminer(param);
			Thread.sleep(1000*60*10);
			
			//2.API获取算力值
			String result = HttpRequestUtils.get(IP, "/index.php/app/stats", null);
			JSONObject jol1 = JSON.parseObject(result);
			JSONObject jol2 = jol1.getJSONObject("pool");
			Long hashrateLong = jol2.getLong("hashrate");
			String hashrate = String.format("%.2f", hashrateLong.doubleValue()/1000/1000/1000/1000);
			System.out.println("算力："+hashrate+" Th/s");
			
			JSONObject jol3 = jol1.getJSONObject("totals");
			String hwErrors = jol3.getString("hw_errors");
			System.out.println("hw错误数："+hwErrors);
			
			//3.关闭cgminer
			stopCgminer();
			Thread.sleep(1000*5);
		}
	}
}
