package utils;

public class StringUtils
{
    public static String formatNull(String input)
    {
        if (input.contains("null"))
        {
            return "";
        }
        else
        {
            return input;
        }
      
    }
    
    public static String round(float input, int size)
    {
        return String.valueOf((float)(Math.round(input * (int)Math.pow(10, size)))/(int)Math.pow(10, size));
    }
    
    public static String formatDateTime(long mss) {
    	 String DateTimes = null;
    	 long days = mss / ( 60 * 60 * 24);
    	 long hours = (mss % ( 60 * 60 * 24)) / (60 * 60);
    	 long minutes = (mss % ( 60 * 60)) /60;
    	 long seconds = mss % 60;
    	 if(days>0){
    	  DateTimes= days + "d" + hours + "h" + minutes + "m" + seconds + "S"; 
    	 }else if(hours>0){
    	  DateTimes=hours + "h" + minutes + "m" + seconds + "s"; 
    	 }else if(minutes>0){
    	  DateTimes=minutes + "m" + seconds + "s"; 
    	 }else{
    	  DateTimes=seconds + "s";
    	 }
    	 
    	 return DateTimes;
    }
    
    public static void main(String[] args)
    {
        //System.out.println(34f/10);
        //System.out.println(StringUtils.round(3.43734f, 2));
    	long mss=58847;
    	String ss= StringUtils.formatDateTime(mss);
    	System.out.println(ss);
    }
}
