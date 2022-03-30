package common;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import dao.UiConfigDao;

public class LangConfig {
	
	private static Properties props;
	public static String lang;
	
	private static final Logger logger = LoggerFactory.getLogger(LangConfig.class);

	public static void uiLang() {
		Map<String,String> map = UiConfigDao.selectConfig();
		lang = map.get("LANG");
		InputStream is = null;
		if("zh_CN".equals(lang)) {
			//中文
			is = Log4jConfig.class.getResourceAsStream("/common/mess_zh_CN.properties");
		}else {
			//英文
			is = Log4jConfig.class.getResourceAsStream("/common/mess_en_US.properties");
		}
		props = new Properties();
		try {
			props.load(is);
		} catch (IOException e) {
			logger.error("lang config load error！{}", e);			
		}
		//System.out.println(props.get("app"));
		logger.info("UI-lang is：[{}]",lang);
		//logger.info("key:"+LangConfig.getKey("app.check.lighton"));
	}
	
	public static String getKey(String key) {
		return props.getProperty(key);
	}
	
	public static void updateLang(String lang) {
		//更新数据库
		if(UiConfigDao.updateByKey("LANG", lang)) {
			uiLang();//更新缓存值
		}		
	}
}
