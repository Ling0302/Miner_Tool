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
		//createStream("http://10.42.5.1/index.html","root");//??????html		
		System.out.println("???????????????"+createStream("http://172.16.8.44/cgi-bin/get_system_info.cgi","root"));//?????? json????????????
		//createStream("http://172.16.8.183/cgi-bin/minerConfiguration.cgi","root");//????????????html
		String html = createStream("http://172.16.8.44/cgi-bin/minerStatus.cgi","root");//????????????html
		System.out.println(html);
		//createStream("http://172.16.8.183/cgi-bin/get_network_info.cgi","root");//??????????????????
		Document document = Jsoup.parse(html);
		Elements temps = document.select("div#cbi-table-1-temp2"); 
		for(Element e : temps) {
			System.out.println("Test:"+e.text());
		}
		
		System.out.println("Result:"+document.getElementById("cbi-table-1-rate2").text());
		/*
		try {
			Document doc = Jsoup.connect("http://www.oschina.net/") 
					  .data("query", "Java")   // ????????????
					  .userAgent("I ??? m jsoup") // ?????? User-Agent 
					  .cookie("auth", "token") // ?????? cookie 
					  .timeout(3000)           // ????????????????????????
					  .post();                 // ?????? POST ???????????? URL 
		}catch(Exception e) {
			e.printStackTrace();
		}*/
		
	}
}
