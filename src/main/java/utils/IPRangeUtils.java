package utils;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class IPRangeUtils
{
    public static List<String> getIPRangeList(String range)
    {
        List<String> result = new ArrayList<String>();
        int index = range.indexOf(":");
        range = range.substring(index + 1);
        String[] temp = range.split("\\.");
        int changeIndex = 0;
        for (int i = 0; i < temp.length; i++)
        {
            if (temp[i].indexOf("-") > -1)
            {
                changeIndex = i;
            }
        }
        String[] changeIpRange = temp[changeIndex].split("-");
        
        for (int i = Integer.parseInt(changeIpRange[0]); i <= Integer.parseInt(changeIpRange[1]); i++)
        {
            String ip = "";
            for (int j = 0; j < changeIndex; j ++)
            {
                ip += temp[j] + ".";
            }
            ip += i + ".";
            for (int j = changeIndex + 1; j <4; j++)
            {
                ip += temp[j] + ".";
            }
            
            result.add(ip.replaceAll("\\.$", "")); 
        }
        
        return result;
    }
    
    
    /**
     * 获取主机IP
     * @return
     */
     public static String getLocalIPAdress()
        {
            Enumeration<NetworkInterface> netInterfaces = null;  
            try {  
                netInterfaces = NetworkInterface.getNetworkInterfaces();  
                while (netInterfaces.hasMoreElements()) {  
                    NetworkInterface ni = netInterfaces.nextElement();  
                    if (!ni.isLoopback())
                    {
                        Enumeration<InetAddress> ips = ni.getInetAddresses();  
                        while (ips.hasMoreElements()) {  
                            InetAddress addres = ips.nextElement();
                            if (addres.getHostAddress().length() < 16)
                            {
                                return addres.getHostAddress();
                            }
                        }  
                    }
                }  
            } catch (Exception e) {  
                return "0.0.0.0";
            }
            return "0.0.0.0";
        }
    
     //IP正则判断
     public static boolean ipCheck(String text) {
    	 if (text != null && !text.isEmpty()) {
   	      // 定义正则表达式
   	      String regex = "^(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|[1-9])\\."
   	          + "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."
   	          + "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."
   	          + "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)$";
   	      // 判断ip地址是否与正则表达式匹配
   	      if (text.matches(regex)) {
   	        // 返回判断信息
   	        return true;
   	      } else {
   	        // 返回判断信息
   	        return false;
   	      }
   	    }
   	    return false;    
     }
     
    //IP可变位，只能包含一个“-”
    public static boolean isContainManyStr(String text) {
    	String str[] = text.split("-");
    	int len =str.length;
    	if(len > 2) {
    		return true;
    	}
    	String str2[] = text.split("\\.");
    	if(str2[3].indexOf("-") == -1) {
    		return true;
    	}
    	return false;
    }
    
    //去掉重复IP
    public static List<String> removeDuplicate(List<String> list) {   
        HashSet<String> h = new HashSet<String>(list);   
        list.clear();   
        list.addAll(h);   
        return list;   
    } 
    
    // 去掉重复IP，删除ArrayList中重复元素，保持顺序     
    public static List<String> removeDuplicateWithOrder(List<String> list) {    
       Set set = new HashSet();    
        List newList = new ArrayList();    
      for (Iterator iter = list.iterator(); iter.hasNext();) {    
            Object element = iter.next();    
            if (set.add(element))    
               newList.add(element);    
         }     
        list.clear();    
        list.addAll(newList); 
     // System.out.println( " remove duplicate " + list);  
        return list;
        
    }  
     
    public static void main(String[] args)
    {
    	System.out.println(ipCheck("10.1.255.0"));
    	/**
        List<String> ss = IPRangeUtils.getIPRangeList("sfsd:172.16-20.1.99");
        for (String s : ss)
        {
            System.out.println(s);
        }**/
        
       // System.out.println(IPRangeUtils.getLocalIPAdress());
    }
}
