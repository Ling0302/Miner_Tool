package service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import cn.dreampie.client.Client;
import cn.dreampie.client.ClientRequest;
import cn.dreampie.client.ClientResult;
import cn.dreampie.log.Logger;
import utils.IPRangeUtils;


public class MinerStatusCheck implements Callable<JSONObject>
{
    private String ip;
    
    private String name;
    
    private CountDownLatch countDownLatch;
    
    protected final Logger logger = Logger.getLogger(getClass());
    
    public MinerStatusCheck(String ip, CountDownLatch countDownLatch)
    {
        this.ip = ip;
        this.countDownLatch = countDownLatch;
    }

    @Override
    public JSONObject call() throws Exception {
        
        JSONObject result = null;
        result = requestRemoteApi(ip);    
        try
        {

//        	result = S9SpiderService.getS9Data(ip);
//        	if(result == null) {
//        		result = requestRemoteApi(ip);
//        	}       

//        	//S9扫描
//            Callable<JSONObject> callableS9 = new StatusCheck(ip,"S9");
//            FutureTask<JSONObject> taskS9 = new FutureTask<>(callableS9);
//            //EBT翼比特扫描
//            Callable<JSONObject> callableEbt = new StatusCheck(ip,"EBT");
//            FutureTask<JSONObject> taskEbt = new FutureTask<>(callableEbt);
//            //WhatsMiner M3扫描
//            Callable<JSONObject> callableWhats = new StatusCheck(ip,"Whats");
//            FutureTask<JSONObject> taskWhats = new FutureTask<>(callableWhats);
//            new Thread(taskS9).start();
//            new Thread(taskEbt).start();
//            new Thread(taskWhats).start();
//            result = requestRemoteApi(ip);
//            if(result.getInteger("status") != 1) {
//            	// 调用get()阻塞主线程，反之，线程不会阻塞
//                JSONObject resultS9 = taskS9.get();
//                if(resultS9 != null) {
//                	result = resultS9;
//                }
//            }
//            
//            if(result.getInteger("status") != 1) {
//            	// 调用get()阻塞主线程，反之，线程不会阻塞
//            	JSONObject resultEBT = taskEbt.get();
//            	if(resultEBT != null) {
//            		result = resultEBT;
//            	}   
//            }
//            
//            if(result.getInteger("status") != 1) {
//            	// 调用get()阻塞主线程，反之，线程不会阻塞
//            	JSONObject resultWhats = taskWhats.get();
//            	if(resultWhats != null) {
//            		result = resultWhats;
//            	}   
//            }
            
//            System.out.println("cast : " + (endTime - beginTime) / 1000 + " second!");
            
        }
        finally
        {
            countDownLatch.countDown();
        }
        return result;
    }

    private JSONObject requestRemoteApi( String ip) {
        Client  client = new Client("http://" + ip);
        ClientResult cr = null;
        int retryCount = 1;
        while (retryCount > 0 && cr == null)
        {
            
            try
            {
                ClientRequest request = new ClientRequest("/api/runningStatus");
                //request.setReadTimeOut(1000);
                request.setConnectTimeOut(1000);
                cr = client.build(request).get();
                //System.out.println(ip + "retry times: " + retryCount);
            }
            catch (Exception e)
            {
            	logger.error("异常："+ip);
                if (!e.getMessage().contains("timed out"))
                {
                    logger.error("error when request " + ip, e);
                }
            }
            retryCount--;
        }
        if (cr != null)
        {
            JSONObject status;
            try
            {
                status = JSON.parseObject(cr.getResult());
                
                String resultIp = status.getString("ip");
                if(resultIp == null) {
                	//过滤掉其它设备拦截url请求
                	status = new JSONObject();
                    status.put("name", name);
                    status.put("ipSeq", ip);
                    status.put("ip", ip);
                    status.put("errorInfo", cr.getResult());
                    status.put("status", 3);  //异常
                	return status;
                }
                
                //addStatusCheckInfo(status);
                status.put("ipSeq", ip);
                status.put("name", name);
                status.put("status", 1); //正常
                return status;
            }
            catch (Exception e)
            {
                status = new JSONObject();
                status.put("name", name);
                status.put("ipSeq", ip);
                status.put("ip", ip);
                status.put("errorInfo", cr.getResult());
                status.put("status", 3);  //异常
                System.out.println("错误信息："+status.toJSONString());
                return status;
            }
        }
        else
        {
            JSONObject status = new JSONObject();
            status.put("name", name);
            status.put("ipSeq", ip);
            status.put("ip", ip);
            status.put("status", 2);  //检测超时
            return status;
        }
    }
    
    
    public static void main(String[] args)
    {
        ExecutorService service = Executors.newFixedThreadPool(6);
        //List<String> ips = IPRangeUtils.getIPRangeList("10.44.129-132.1");
        List<String> ips = IPRangeUtils.getIPRangeList("172.16.8.247-250");
        CountDownLatch countDownLatch = new CountDownLatch(ips.size());
        
        List<Future<JSONObject>> statusList = new ArrayList<>();
        
        List<JSONObject> resultList = new ArrayList<>();
        
        for (String ip : ips)
        {
                MinerStatusCheck mc = new MinerStatusCheck(ip, countDownLatch);
                statusList.add(service.submit(mc));
                System.out.println(333);
        }
        
        // 主线程一直被阻塞,直到count的计数器被置为0
        try 
        {
        	System.out.println(111);
            countDownLatch.await();
            System.out.println(222);
            for (Future<JSONObject> fs : statusList)
            {
                JSONObject jol = fs.get();
                resultList.add(jol); 
                System.out.println(jol);
            } 
        }
        catch(Exception e)
        {   
           
        }
        finally{   
            //启动一次顺序关闭，执行以前提交的任务，但不接受新任务  
            service.shutdown();   
        }   
    }
    
   
}

class StatusCheck implements Callable<JSONObject>{
	private String ip;
	private String minertype;
	
	public StatusCheck(String ip,String minertype) {
		this.ip = ip;
		this.minertype = minertype;
	}
	
	@Override
    public JSONObject call() throws Exception {        
        JSONObject result = null;
        if("S9".equals(minertype)) {
        	result = S9SpiderService.getS9Data(ip);
        }else if("EBT".equals(minertype)) {
        	result = EbtSpiderService.getMinerData(ip);
        }else if("Whats".equals(minertype)) {
        	result = WhatsMinerService.getMinerData(ip);
        }
        
        return result;
	}
}

