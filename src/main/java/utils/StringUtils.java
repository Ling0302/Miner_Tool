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
    
    
    public static void main(String[] args)
    {
        System.out.println(34f/10);
        System.out.println(StringUtils.round(3.43734f, 2));
    }
}
