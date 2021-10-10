package utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.dreampie.client.Client;
import cn.dreampie.client.ClientRequest;
import cn.dreampie.client.ClientResult;


public class HttpRequestUtils {
	
	private static final Logger logger = LoggerFactory.getLogger(HttpRequestUtils.class);
	
	public static String httpReq(String method,String ip,String api,String jsonParam) {
		String result = "";
		Client  client = new Client("http://" + ip);
		ClientResult cr = null;
		try
        {
            ClientRequest request = new ClientRequest(api);
            request.setReadTimeOut(1000);
            if(jsonParam!=null && !"".equals(jsonParam)) {
            	request.setJsonParam(jsonParam);
            }           
            request.setConnectTimeOut(1000);
            if("post".equals(method)) {
            	cr = client.build(request).post();
            }else if("get".equals(method)) {
            	cr = client.build(request).get();
            }else {
            	return result;
            }
            result = cr.getResult();
        }
        catch (Exception e)
        {
            if (!e.getMessage().contains("timed out"))
            {
               e.printStackTrace();
            }
        }
		return result;
	}

	public static String post(String ip,String api,String param) {
		String result = "";
		Client  client = new Client("http://" + ip);
		ClientResult cr = null;
		try
        {
            ClientRequest request = new ClientRequest(api);
            //request.setReadTimeOut(1000);
            if(param!=null && !"".equals(param)) {
            	request.setJsonParam(param);
            }   
            request.setConnectTimeOut(1000);
            request.setReadTimeOut(2000);
            cr = client.build(request).post();
            result = cr.getResult();
        }
        catch (Exception e)
        {
        	logger.debug("ip [{}] connect message:[{}]",ip,e.getMessage());
//            if (!e.getMessage().contains("timed out"))
//            {
//               e.printStackTrace();
//            }
        }
		return result;
	}
	
	public static String get(String ip,String api,String param) {
		String result = "";
		Client  client = new Client("http://" + ip);
		ClientResult cr = null;
		try
        {
            ClientRequest request = new ClientRequest(api);
            //request.setReadTimeOut(1000);
            if(param!=null && !"".equals(param)) {
            	request.setJsonParam(param);
            }   
            request.setConnectTimeOut(1000);
            request.setReadTimeOut(2000);
            cr = client.build(request).get();
            result = cr.getResult();
        }
        catch (Exception e)
        {
        	logger.debug("ip [{}] connect message:[{}]",ip,e.getMessage());
//        	System.out.println("11111111:"+e.getMessage());
//            if (!e.getMessage().contains("timed out"))
//            {
//               e.printStackTrace();
//            }
        }
		return result;
	}
	
	public static void main(String args[]) {
		System.out.println(httpReq("get","10.41.91.1","/api/firmwareCheck",""));
		System.out.println(get("10.41.91.1","/api/firmwareCheck",""));
	}
}
