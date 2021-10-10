package utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class HttpClientUtils {

	public static CloseableHttpClient getHttpClient(){
	    CredentialsProvider provider = new BasicCredentialsProvider();
	    UsernamePasswordCredentials credentials = new UsernamePasswordCredentials("root", "root");
	    provider.setCredentials(AuthScope.ANY, credentials);
	    return  HttpClients.custom().setDefaultCredentialsProvider(provider).build();
	}
	
	public static String postUrl(String url,String json) {
		CloseableHttpClient httpClient = getHttpClient();
	    HttpPost httpPost = new HttpPost(url);
	    String s = "";
	    try {
	    	RequestConfig requestConfig = RequestConfig.custom()    
	    	        .setConnectTimeout(1000).setConnectionRequestTimeout(1000)    
	    	        .setSocketTimeout(1000).build();    
	    	httpPost.setConfig(requestConfig);
	    	HttpEntity formEntity = new StringEntity(json, "UTF-8");  
	        httpPost.setEntity(formEntity);
	        CloseableHttpResponse response = httpClient.execute(httpPost);
	        HttpEntity entity = response.getEntity();
	        s = EntityUtils.toString(entity);
	        //System.out.println(s);
	        httpClient.close();
	    } catch (ClientProtocolException e) {
	        e.printStackTrace();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	    return s;
	}
	
	public static String postUrlParam(String url,List<NameValuePair> parameters) {
		CloseableHttpClient httpClient = getHttpClient();
	    HttpPost httpPost = new HttpPost(url);
	    String s = "";
//	    List<NameValuePair> parameters = new ArrayList<NameValuePair>();
//	    parameters.add(new BasicNameValuePair("name", name));
	    try {
	    	RequestConfig requestConfig = RequestConfig.custom()    
	    	        .setConnectTimeout(1000).setConnectionRequestTimeout(1000)    
	    	        .setSocketTimeout(1000).build();    
	    	httpPost.setConfig(requestConfig);
	        UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(parameters,"UTF-8");
	        httpPost.setEntity(formEntity);
	        CloseableHttpResponse response = httpClient.execute(httpPost);
	        HttpEntity entity = response.getEntity();
	        s = EntityUtils.toString(entity);
	        //System.out.println(s);
	        httpClient.close();
	    } catch (ClientProtocolException e) {
	        e.printStackTrace();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	    return s;
	}
	
	public static String createStream(String url, String name) {
	    CloseableHttpClient httpClient = getHttpClient();
	    HttpPost httpPost = new HttpPost(url);
	    String s = "";
	    List<NameValuePair> parameters = new ArrayList<NameValuePair>();
	    parameters.add(new BasicNameValuePair("name", name));
	    try {
	    	RequestConfig requestConfig = RequestConfig.custom()    
	    	        .setConnectTimeout(1000).setConnectionRequestTimeout(1000)    
	    	        .setSocketTimeout(1000).build();    
	    	httpPost.setConfig(requestConfig);
	        UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(parameters,"UTF-8");
	        httpPost.setEntity(formEntity);
	        CloseableHttpResponse response = httpClient.execute(httpPost);
	        HttpEntity entity = response.getEntity();
	        s = EntityUtils.toString(entity);
	        //System.out.println(s);
	        httpClient.close();
	    } catch (ClientProtocolException e) {
	        e.printStackTrace();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	    return s;
	}
	
	public static void parseHtml(String html) {
		
	}
	
	public static void main(String args[]) {
		//createStream("http://10.42.5.1/index.html","root");//首页html		
		System.out.println("访问结果："+createStream("http://172.16.8.44/cgi-bin/get_system_info.cgi","root"));//首页 json模型数据
		//createStream("http://172.16.8.183/cgi-bin/minerConfiguration.cgi","root");//矿池配置html
		String html = createStream("http://172.16.8.44/cgi-bin/minerStatus.cgi","root");//状态检测html
		System.out.println(html);
		//createStream("http://172.16.8.183/cgi-bin/get_network_info.cgi","root");//网络配置参数
		Document document = Jsoup.parse(html);
		Elements temps = document.select("div#cbi-table-1-temp2"); 
		for(Element e : temps) {
			System.out.println("Test:"+e.text());
		}
		
		System.out.println("Result:"+document.getElementById("cbi-table-1-rate2").text());
		/*
		try {
			Document doc = Jsoup.connect("http://www.oschina.net/") 
					  .data("query", "Java")   // 请求参数
					  .userAgent("I ’ m jsoup") // 设置 User-Agent 
					  .cookie("auth", "token") // 设置 cookie 
					  .timeout(3000)           // 设置连接超时时间
					  .post();                 // 使用 POST 方法访问 URL 
		}catch(Exception e) {
			e.printStackTrace();
		}*/
		
	}
}
