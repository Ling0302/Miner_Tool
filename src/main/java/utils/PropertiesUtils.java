package utils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import vo.Config;
import vo.IpsVO;
import vo.MinerParamVO;
import vo.SetupVO;

public class PropertiesUtils {
	
	private final static String fileName = "config.properties";
	public final static String defaultPath = PropertiesUtils.class.getResource("/").getPath()+"defaultConfig.properties";//默认配置路径
	public final static String defaultPath2 = PropertiesUtils.class.getResource("/").getPath()+"resources/defaultConfig.properties";//默认配置路径
	//读取文件（返回json）
	public static SetupVO readConfig() {
		String profilepath = getJarPath()+"/"+fileName;
		Properties props = new Properties();
		SetupVO setupVO = new SetupVO();
		try {			
			try {
				props.load(new FileInputStream(profilepath));
			}catch(java.io.FileNotFoundException e1) {
				return Config.getDefaultConfig();
			}
			
			List<MinerParamVO> minerList = new ArrayList<MinerParamVO> ();
			String minerKinds[] = props.getProperty("miner.kinds").split(",");
			for(int i=0;i<minerKinds.length;i++) {
				MinerParamVO minerParamVO = new MinerParamVO();
				minerParamVO.setType(props.getProperty("miner."+minerKinds[i]+".type"));
				minerParamVO.setUsername(props.getProperty("miner."+minerKinds[i]+".username"));
				minerParamVO.setPassword(props.getProperty("miner."+minerKinds[i]+".password"));
				minerParamVO.setPower(props.getProperty("miner."+minerKinds[i]+".power"));
				minerParamVO.setCpWarn(props.getProperty("miner."+minerKinds[i]+".cpWarn"));
				minerParamVO.setMaxVoltage(props.getProperty("miner."+minerKinds[i]+".maxVoltage"));
				minerParamVO.setMaxCurrent(props.getProperty("miner."+minerKinds[i]+".maxCurrent"));
				minerParamVO.setMinFanSpeed(props.getProperty("miner."+minerKinds[i]+".minFanSpeed"));
				minerParamVO.setMaxFanSpeed(props.getProperty("miner."+minerKinds[i]+".maxFanSpeed"));
				minerParamVO.setMaxRefuseRate(props.getProperty("miner."+minerKinds[i]+".maxRefuseRate"));
				minerParamVO.setMaxPsuInOutTemprature(props.getProperty("miner."+minerKinds[i]+".maxPsuInOutTemprature"));
				minerParamVO.setMaxTemprature(props.getProperty("miner."+minerKinds[i]+".maxTemprature"));
				minerParamVO.setMinTemprature(props.getProperty("miner."+minerKinds[i]+".minTemprature"));
				minerParamVO.setScanStatus(props.getProperty("miner."+minerKinds[i]+".scanStatus"));
				minerList.add(minerParamVO);
			}
			setupVO.setMinerList(minerList);
			setupVO.setScanThreads(props.getProperty("scanThreads"));
			setupVO.setScanTimeout(props.getProperty("scanTimeout"));
//			setupVO.setMonitorCycle(props.getProperty("monitorCycle"));
//			setupVO.setIsMonitor(props.getProperty("isMonitor"));
			
			
		}catch(Exception e2) {
			e2.printStackTrace();
		}
		return setupVO;
	}
	
	//保存文件
	public static void saveConfig(SetupVO setupVO) {
		String profilepath = getJarPath()+"/"+fileName;//保存文件到jar目录
	    try {
		   Properties props=writeData(setupVO);
		   OutputStream fos = new FileOutputStream(profilepath);          		   
		   props.store(fos, "Update config");
		   fos.close();
		} catch (IOException e) {
		   e.printStackTrace();
		   System.err.println("属性文件更新错误");
		}
	}
	
	//恢复默认配置
	public static SetupVO setDefault() {
		String profilepath = getJarPath()+"/"+fileName;//保存文件到jar目录
	    try {
		   Properties props=writeData(Config.getDefaultConfig());
		   OutputStream fos = new FileOutputStream(profilepath);          
		   props.store(fos, "default config");
		   fos.close();
		} catch (IOException e) {
		   e.printStackTrace();
		   System.err.println("恢复默认配置出错");
		}	
	    return readConfig();
	}	
	
	public static Properties writeData(SetupVO setupVO) {
		Properties props=new Properties();
		 props.setProperty("scanTimeout", setupVO.getScanTimeout());
		   props.setProperty("scanThreads", setupVO.getScanThreads());
//		   props.setProperty("monitorCycle", setupVO.getMonitorCycle());
//		   props.setProperty("isMonitor", setupVO.getIsMonitor());
		   List<MinerParamVO> minerList = setupVO.getMinerList();
		   String minerKinds = "";
		   for(int i = 0; i < minerList.size();i++) {
			   if(i==0) {
				   minerKinds += minerList.get(i).getType();				   
			   }else {
				   minerKinds += ","+minerList.get(i).getType();
			   }
			   props.setProperty("miner."+minerList.get(i).getType()+".type", minerList.get(i).getType());
			   props.setProperty("miner."+minerList.get(i).getType()+".username", minerList.get(i).getUsername());
			   props.setProperty("miner."+minerList.get(i).getType()+".password", minerList.get(i).getPassword());
			   props.setProperty("miner."+minerList.get(i).getType()+".power", minerList.get(i).getPower());
			   props.setProperty("miner."+minerList.get(i).getType()+".cpWarn", minerList.get(i).getCpWarn());
			   props.setProperty("miner."+minerList.get(i).getType()+".maxVoltage", minerList.get(i).getMaxVoltage());
			   props.setProperty("miner."+minerList.get(i).getType()+".maxCurrent", minerList.get(i).getMaxCurrent());
			   props.setProperty("miner."+minerList.get(i).getType()+".minFanSpeed", minerList.get(i).getMinFanSpeed());
			   props.setProperty("miner."+minerList.get(i).getType()+".maxFanSpeed", minerList.get(i).getMaxFanSpeed());
			   props.setProperty("miner."+minerList.get(i).getType()+".maxRefuseRate", minerList.get(i).getMaxRefuseRate());
			   props.setProperty("miner."+minerList.get(i).getType()+".maxPsuInOutTemprature", minerList.get(i).getMaxPsuInOutTemprature());
			   props.setProperty("miner."+minerList.get(i).getType()+".maxTemprature", minerList.get(i).getMaxTemprature());
			   props.setProperty("miner."+minerList.get(i).getType()+".minTemprature", minerList.get(i).getMinTemprature());
			   props.setProperty("miner."+minerList.get(i).getType()+".scanStatus", minerList.get(i).getScanStatus());

		   }
		   props.setProperty("miner.kinds", minerKinds);
		   List<IpsVO> ipsList = getIpsListByConfig();
		   if(ipsList != null) {
			   String ipsListStr = "";
			   for(int i = 0 ;i < ipsList.size(); i++) {
				   if(i==0) {
					   ipsListStr += ipsList.get(i).getIndex();
				   }else {
					   ipsListStr += ","+ipsList.get(i).getIndex();
				   }
				   props.setProperty("ipsName"+ipsList.get(i).getIndex(), ipsList.get(i).getIpsName());
				   props.setProperty("ipsStr"+ipsList.get(i).getIndex(),ipsList.get(i).getIpsStr());
			   }
			   props.setProperty("ipsList", ipsListStr);
		   }
		return props;
	}
	
	//获取jar包运行路径
	public static String getRunPath() {
		/**
		 * 方法二：获取当前可执行jar包所在目录
		 */
		String filePath = "";
		URL url = PropertiesUtils.class.getProtectionDomain().getCodeSource().getLocation();
		try {
		    filePath = URLDecoder.decode(url.getPath(), "utf-8");// 转化为utf-8编码，支持中文
		} catch (Exception e) {
		    e.printStackTrace();
		}
		if (filePath.endsWith(".jar")) {// 可执行jar包运行的结果里包含".jar"
		    // 获取jar包所在目录
		    filePath = filePath.substring(0, filePath.lastIndexOf("/") + 1);
		}

		File file = new File(filePath);
		filePath = file.getAbsolutePath();//得到windows下的正确路径
		//System.out.println("jar包所在目录："+filePath);
		return filePath;
	}
	
	public static MinerParamVO getRule(String minerType) {		
		String profilepath = getJarPath()+"/"+fileName;
		Properties props = new Properties();
		MinerParamVO minerParamVO = new MinerParamVO();
		try {
			try {
				props.load(new FileInputStream(profilepath));
				if(props.getProperty("miner.kinds")==null) {
					return null;
				}
				String minerKinds[] = props.getProperty("miner.kinds").split(",");
				for(int i=0;i<minerKinds.length;i++) {
					if(minerType.equals(minerKinds[i])) {
						minerParamVO.setType(props.getProperty("miner."+minerKinds[i]+".type"));
						minerParamVO.setUsername(props.getProperty("miner."+minerKinds[i]+".username"));
						minerParamVO.setPassword(props.getProperty("miner."+minerKinds[i]+".password"));
						minerParamVO.setPower(props.getProperty("miner."+minerKinds[i]+".power"));
						minerParamVO.setCpWarn(props.getProperty("miner."+minerKinds[i]+".cpWarn"));
						minerParamVO.setMaxVoltage(props.getProperty("miner."+minerKinds[i]+".maxVoltage"));
						minerParamVO.setMaxCurrent(props.getProperty("miner."+minerKinds[i]+".maxCurrent"));
						minerParamVO.setMinFanSpeed(props.getProperty("miner."+minerKinds[i]+".minFanSpeed"));
						minerParamVO.setMaxFanSpeed(props.getProperty("miner."+minerKinds[i]+".maxFanSpeed"));
						minerParamVO.setMaxRefuseRate(props.getProperty("miner."+minerKinds[i]+".maxRefuseRate"));
						minerParamVO.setMaxPsuInOutTemprature(props.getProperty("miner."+minerKinds[i]+".maxPsuInOutTemprature"));
						minerParamVO.setMaxTemprature(props.getProperty("miner."+minerKinds[i]+".maxTemprature"));
						minerParamVO.setMinTemprature(props.getProperty("miner."+minerKinds[i]+".minTemprature"));
						minerParamVO.setScanStatus(props.getProperty("miner."+minerKinds[i]+".scanStatus"));
						return minerParamVO;
					}else {
						continue;
					}				
				}
			}catch(java.io.FileNotFoundException e1) {
				List<MinerParamVO> list = Config.getDefaultConfig().getMinerList();
				for(int i=0;i<list.size();i++) {
					if(minerType.equals(list.get(i).getType())) {
						minerParamVO = list.get(i);
						return minerParamVO;
					}
				}		
			}
		}catch(Exception e) {
			return null;
		}
		
		return null;
	}
	
	//获取IP配置VO对象（单独）
	public static List<IpsVO> getIpsListByConfig(){
		String profilepath = getJarPath()+"/"+fileName;
		Properties prop = new Properties();  
		try {
			InputStream in = new BufferedInputStream (new FileInputStream(profilepath));
            prop.load(in);     ///加载属性列表
            if(prop.getProperty("ipsList")==null || "".equals(prop.getProperty("ipsList"))) {
            	return new ArrayList<IpsVO>();
            }
            List<IpsVO> list = new ArrayList<IpsVO>();
            String ipsList[] = prop.getProperty("ipsList").split(",");
            for(int i=0;i<ipsList.length;i++) {
            	IpsVO ipsVO = new IpsVO();
            	ipsVO.setIndex(ipsList[i]);
            	ipsVO.setIpsName(prop.getProperty("ipsName"+ipsList[i]));
            	ipsVO.setIpsStr(prop.getProperty("ipsStr"+ipsList[i]));
            	list.add(ipsVO);
            }
            in.close();
            return list;
		}catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	//将IP配置写入配置文件
	public static void writeIp2Config(IpsVO ipsVO) {
		List<IpsVO> ipsList = getIpsListByConfig();
		if(ipsList == null || ipsList.isEmpty()) {
			//不存在ip配置
			ipsList = new ArrayList<IpsVO>();	
			ipsVO.setIndex("0");
			ipsList.add(ipsVO);
		}else {
			//存在IP配置,索引从小排到大
			Integer index = Integer.parseInt(ipsList.get(ipsList.size()-1).getIndex())+1;
			ipsVO.setIndex(index+"");
			ipsList.add(ipsVO);
		}		
		updateIp2File(ipsList);
	}
	
	public static void updateIp2File(List<IpsVO> ipsList) {
		Properties props = new Properties();     
		String profilepath = getJarPath()+"/"+fileName;
		
        try{
        	try {
        		InputStream in = new BufferedInputStream (new FileInputStream(profilepath));
                props.load(in);     ///加载属性列表
                in.close();  
    		}catch(java.io.FileNotFoundException e1) {
    			props=writeData(Config.getDefaultConfig());
    		}          
            ///保存属性到b.properties文件
            FileOutputStream oFile = new FileOutputStream(profilepath);//true表示追加打开
            //FileOutputStream oFile = new FileOutputStream("src/a.properties", true);//true表示追加打开
            String ipsListStr = "";
            for(int i = 0 ;i < ipsList.size(); i++) {
            	if(i==0) {
            		ipsListStr += ipsList.get(i).getIndex();
				}else {
					ipsListStr += ","+ipsList.get(i).getIndex();
				}
            	props.setProperty("ipsName"+ipsList.get(i).getIndex(), ipsList.get(i).getIpsName());
				props.setProperty("ipsStr"+ipsList.get(i).getIndex(),ipsList.get(i).getIpsStr());	  
			}
			props.setProperty("ipsList", ipsListStr);   
            props.store(oFile, "updateIpConfig");
            oFile.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }
	}
	
	public static void editIps(String ipNote) {
		Properties props = new Properties();     
		String profilepath = getJarPath()+"/"+fileName;
        try{
        	List<IpsVO> ipsList = getIpsListByConfig();
            //读取属性文件a.properties
            InputStream in = new BufferedInputStream (new FileInputStream(profilepath));
            props.load(in);     ///加载属性列表
            in.close();            
            ///保存属性到b.properties文件
            FileOutputStream oFile = new FileOutputStream(profilepath);//true表示追加打开            
            String ipsName = ipNote.split(":")[0];
            String ipsStr = ipNote.split(":")[1];
            Integer index = Integer.parseInt(ipNote.split(":")[2]);
            String ip_index = ipsList.get(index).getIndex();//index从0开始
            
            props.setProperty("ipsName"+ip_index, ipsName);
            props.setProperty("ipsStr"+ip_index, ipsStr);
            props.store(oFile, "editIpConfig");
            oFile.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }
	}
	
	public static void removeIps(List<Integer> indexLists) {
		Properties props = new Properties();     
		String profilepath = getJarPath()+"/"+fileName;
        try{
            //读取属性文件a.properties
            InputStream in = new BufferedInputStream (new FileInputStream(profilepath));
            props.load(in);     ///加载属性列表
            in.close();            
            ///保存属性到b.properties文件
            FileOutputStream oFile = new FileOutputStream(profilepath);//true表示追加打开
            for(Integer index : indexLists) {
            	props.remove("ipsStr"+index);
            	props.remove("ipsName"+index);
            }
            props.store(oFile, "deleteIpConfig");
            oFile.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }
	}
	
	public static String getJarPath() {
		//String path = PropertiesUtils.class.getProtectionDomain().getCodeSource().getLocation().getPath();
		//String path = PropertiesUtils.class.getProtectionDomain().getCodeSource().getLocation().getFile();
		String path = "";
		path = System.getProperty("user.dir");
		/**
		try {
			path = System.getProperty("java.class.path");
			int firstIndex = path.lastIndexOf(System.getProperty("path.separator")) + 1;
			int lastIndex = path.lastIndexOf(File.separator) + 1;
			path = path.substring(firstIndex, lastIndex);
		}catch(Exception e) {
			path = System.getProperty("user.dir");
		}**/
		
		//System.out.println("jar包所在目录："+path);
		return path;
	}
	
	//存在配置文件时读取配置值-
	public static String getValue(String key) {
		String rs = "";
		try {
			String profilepath = getJarPath()+"/"+fileName;
			Properties props = new Properties();
			InputStream in = new BufferedInputStream (new FileInputStream(profilepath));
            props.load(in);     //加载属性列表
            rs = props.getProperty(key);
            in.close(); 
		}catch(Exception e) {
			e.printStackTrace();
		}		
		return rs;
	}
	
    public static void main(String[] args) {
    	//System.out.println(readConfig());
    	//setDefault();
    	//System.out.println(new Gson().toJson(readConfig()));
    }
}
