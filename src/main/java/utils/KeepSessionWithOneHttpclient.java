package utils;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
 * Httpclient爬虫保持session
 * */
public class KeepSessionWithOneHttpclient {
	
	private static final String userAgent = "Mozilla/5.0 (Windows NT 6.2; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.87 Safari/537.36";

	/** 
     * 如果用的是同一个HttpClient且没去手动连接放掉client.getConnectionManager().shutdown(); 
     * 都不用去设置cookie的ClientPNames.COOKIE_POLICY。httpclient都是会保留cookie的 
     * @param loginUrl 
     * @param loginNameValuePair 
     * @param urlAndNamePairList 
     * @param isSSL（网站是否需要证书 true-需要，false-不需要）
     * @return 
     */  
    public static Map<String,String> doPostWithOneHttpclient(String loginUrl,List<NameValuePair> loginNameValuePair,  
            Map<String,List<NameValuePair>> urlAndNamePairList,boolean isSSL,String minertype) {  

        //返回每个URL对应的响应信息  
        Map<String,String> map = new HashMap<String,String>();  
        String retStr = "";//每次响应信息  
        int statusCode = 0 ;//每次响应代码  
                  
        // HttpClient  
        CloseableHttpClient closeableHttpClient = null;
        if(isSSL) {
        	//有证书要求，需要绕过
        	closeableHttpClient = createSSLClientDefault();
        }else {
        	// 创建HttpClientBuilder  
            HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
            httpClientBuilder.setUserAgent("Mozilla/5.0(Windows;U;Windows NT 5.1;en-US;rv:0.9.4)");  
            closeableHttpClient = httpClientBuilder.build(); 
        }
        try {          
        HttpPost httpPost = new HttpPost(loginUrl);  
        httpPost.setHeader("User-Agent", userAgent);
        // 设置请求和传输超时时间  
        RequestConfig requestConfig = RequestConfig.custom()  
                .setSocketTimeout(1000)  
                .setConnectTimeout(1000).build();  
        httpPost.setConfig(requestConfig);  
        UrlEncodedFormEntity entity = null;  
        
            if(null!=loginNameValuePair){  
                entity = new UrlEncodedFormEntity(loginNameValuePair, "UTF-8");  
                httpPost.setEntity(entity);  
            }  
            //登录  
            CloseableHttpResponse loginResponse = closeableHttpClient.execute(httpPost);  
            statusCode = loginResponse.getStatusLine().getStatusCode();  
            retStr = EntityUtils.toString(loginResponse.getEntity(), "UTF-8");
            if("EBT".equals(minertype)) {
            	System.out.println("EBT---login："+loginUrl+"\n"+retStr);
                //翼比特网址验证，该方法不通用
                if(retStr.indexOf("Authorization") != -1 
                		|| retStr.indexOf("404") != -1
                		|| retStr.indexOf("index.esp") == -1
                		|| retStr.indexOf("Temporarily") == -1) {
                	return null;
                }
            }else if("WhatsMiner".equals(minertype)) {
            	//神马矿机
            	CloseableHttpResponse operationResponse = closeableHttpClient.execute(httpPost);  
                statusCode = operationResponse.getStatusLine().getStatusCode();  
                retStr = EntityUtils.toString(operationResponse.getEntity(), "UTF-8");  
                //map.put(loginUrl, retStr);  
                  
                if(statusCode == 302){  
                    String redirectUrl = operationResponse.getLastHeader("Location").getValue();  
                    httpPost = new HttpPost(redirectUrl);  
                    CloseableHttpResponse redirectResponse = closeableHttpClient.execute(httpPost);  
                    statusCode = redirectResponse.getStatusLine().getStatusCode();  
                    retStr = EntityUtils.toString(redirectResponse.getEntity(), "UTF-8");  
                    map.put(loginUrl, retStr);  
                } 
                /*
            	if(statusCode == 302){  
            		String locationUrl=loginResponse.getLastHeader("Location").getValue();  
            		httpPost = new HttpPost(locationUrl);  
            		System.out.println("5555");
                }*/
                if(retStr.indexOf("Authorization") != -1 
                		|| retStr.indexOf("404") != -1
                		|| retStr.indexOf("WhatsMiner") == -1) {
                	return null;
                }
                
                String token = "";
                Document domLogin = Jsoup.parse(retStr);
                token = domLogin.getElementsByAttributeValue("name", "token").val();
                System.out.println("WhatsMiner---login--token："+token);
                for(Map.Entry<String,List<NameValuePair>> entry : urlAndNamePairList.entrySet()){ 
                	String url = entry.getKey();
                	List<NameValuePair> params = urlAndNamePairList.get(url); 
                	params.add(new BasicNameValuePair("token", token));//token
                    urlAndNamePairList.put(url, params);                            	
                }                	                             
                System.out.println(urlAndNamePairList);
            	System.out.println("statusCodeLoginPage:"+statusCode);           	
            	System.out.println("WhatsMiner---login："+loginUrl+"\n:loginResultStr"+retStr);
            }
            
            map.put(loginUrl, retStr);  
              
            //登录后其他操作  
            for(Map.Entry<String,List<NameValuePair>> entry : urlAndNamePairList.entrySet()){  
                String url = entry.getKey();  
                //System.out.println("url:"+url);
                List<NameValuePair> params = urlAndNamePairList.get(url);  
                httpPost = new HttpPost(url);  
                httpPost.setHeader("User-Agent", userAgent);
                if(null!=params){  
                    entity = new UrlEncodedFormEntity(params, "UTF-8");  
                    httpPost.setEntity(entity);  
                }  
                CloseableHttpResponse operationResponse = closeableHttpClient.execute(httpPost);  
                statusCode = operationResponse.getStatusLine().getStatusCode();  
                retStr = EntityUtils.toString(operationResponse.getEntity(), "UTF-8");  
                
                map.put(url, retStr);  
                  
                if(statusCode == 302){  
                    String redirectUrl = operationResponse.getLastHeader("Location").getValue();  
                    httpPost = new HttpPost(redirectUrl);  
                    CloseableHttpResponse redirectResponse = closeableHttpClient.execute(httpPost);  
                    statusCode = redirectResponse.getStatusLine().getStatusCode();  
                    retStr = EntityUtils.toString(redirectResponse.getEntity(), "UTF-8");  
                    map.put(redirectUrl, retStr);  
                }  
            }  
            
            // 释放资源  
            closeableHttpClient.close();  
        } catch (Exception e) {  
            e.printStackTrace();  
            return null;
        }finally {  
        	
        }
        
        return map;  
    }  
    
    //绕过证书验证
    public static CloseableHttpClient createSSLClientDefault(){

        try {
            //SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
            // 在JSSE中，证书信任管理器类就是实现了接口X509TrustManager的类。我们可以自己实现该接口，让它信任我们指定的证书。
            // 创建SSLContext对象，并使用我们指定的信任管理器初始化
            //信任所有
                X509TrustManager x509mgr = new X509TrustManager() {

                    //　　该方法检查客户端的证书，若不信任该证书则抛出异常
                    public void checkClientTrusted(X509Certificate[] xcs, String string) {
                    }
                    // 　　该方法检查服务端的证书，若不信任该证书则抛出异常
                    public void checkServerTrusted(X509Certificate[] xcs, String string) {
                    }
                    // 　返回受信任的X509证书数组。
                    public X509Certificate[] getAcceptedIssuers() {
                        return null;
                    }
                };
                SSLContext sslContext = SSLContext.getInstance("TLS");
                sslContext.init(null, new TrustManager[] { x509mgr }, null);
                ////创建HttpsURLConnection对象，并设置其SSLSocketFactory对象
                SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext, SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);

            //  HttpsURLConnection对象就可以正常连接HTTPS了，无论其证书是否经权威机构的验证，只要实现了接口X509TrustManager的类MyX509TrustManager信任该证书。
            return HttpClients.custom().setSSLSocketFactory(sslsf).build();


        } catch (KeyManagementException e) {

            e.printStackTrace();

        } catch (NoSuchAlgorithmException e) {

            e.printStackTrace();

        } catch (Exception e) {

            e.printStackTrace();

        }
        // 创建默认的httpClient实例.
        return  HttpClients.createDefault();

    }
  
    public static void main(String[] args){  
    	//session测试
    	//KeepSessionWithOneHttpclient
		//登录的地址以及登录操作参数  
        String loginUrl = "https://172.16.8.248/user/login";  
        //登录的相关参数以及登录后操作参数  
        List<NameValuePair> loginParams = new ArrayList<NameValuePair>();   
        loginParams.add(new BasicNameValuePair("username", "admin"));  
        loginParams.add(new BasicNameValuePair("word", "admin"));  
        loginParams.add(new BasicNameValuePair("yuyan", "0"));  
        loginParams.add(new BasicNameValuePair("login", "Login"));  
        loginParams.add(new BasicNameValuePair("get_password", ""));  
          
        //登录后操作地址以及登录后操作参数  
//        String queryUrl = "https://10.21.2.1/alarm/GetBoardAlarm";  
//        String queryUrl = "https://10.21.2.1/alarm/GetAlarmLoop";  
        String queryUrl = "https://172.16.8.248/Cgminer/CgminerGetVal";  
//        String queryUrl = "https://10.21.2.1/alarm/GetBoardAlarm";
//        String queryUrl = "https://10.21.2.1/alarm/GetBoardAlarm";  
//        String queryUrl = "https://10.21.2.1/alarm/GetBoardAlarm";  
        
        List<NameValuePair> queryParams = new ArrayList<NameValuePair>();  
//        queryParams.add(new BasicNameValuePair("regionNo", "xxx"));  
//        queryParams.add(new BasicNameValuePair("pageNo", "xxx"));  
//        queryParams.add(new BasicNameValuePair("pageSize", "xxx"));  
  
        Map<String,List<NameValuePair>> map = new HashMap<String,List<NameValuePair>>();  
        map.put(queryUrl, queryParams);      	        
        Map<String,String> returnMap = KeepSessionWithOneHttpclient.doPostWithOneHttpclient(loginUrl, loginParams, map,true,"EBT");  
        System.out.println(returnMap.toString()); 		

        
    }  
}
