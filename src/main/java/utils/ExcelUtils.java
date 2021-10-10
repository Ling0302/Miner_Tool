package utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
//Excel 导出 HSSF -->支持97-2013版本 默认版本
//需要的jar包 
 /**
        * commons-io-2.2.jar
        * dom4j-1.6.1.jar
        * jxl.jar
        * poi-3.11-20141221.jar
        * poi-examples-3.11-20141221.jar
        * poi-excelant-3.11-20141221.jar
        * poi-ooxml-3.11-20141221.jar
        * poi-ooxml-schemas-3.11-20141221.jar
        * poi-scratchpad-3.11-20141221.jar
        * xmlbeans-2.6.0.jar
        */

public class ExcelUtils {
	
	//获取工作表
	public static HSSFSheet getSheet(HSSFWorkbook workbook,String sheetName) {
		HSSFSheet sheet=workbook.createSheet(sheetName);
		sheet.setDefaultRowHeight((short)(3 * 256));   
	    sheet.setDisplayGridlines(false);//去网格线
	    sheet.setDefaultColumnWidth(15);// 设置表格默认列宽度为20个字节     
		return sheet;
	}
	
	//获取标题栏字体样式
	public static HSSFFont getTitleFont(HSSFWorkbook workbook) {
		HSSFFont font = workbook.createFont();
		font.setFontName("宋体");
		font.setFontHeightInPoints((short)10);
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		return font;
	}
	
	//获取普通栏字体样式
	public static HSSFFont getCommFont(HSSFWorkbook workbook) {
		HSSFFont font = workbook.createFont();
		font.setFontName("宋体");
		font.setFontHeightInPoints((short)10);
		return font;
	}
	
	//获取标题栏背景样式
	public static HSSFCellStyle getTitleStyle(HSSFWorkbook workbook) {
		HSSFCellStyle cellStyle = workbook.createCellStyle();
		cellStyle.setFont(getTitleFont(workbook));
	    cellStyle.setFillBackgroundColor(HSSFColor.LIGHT_CORNFLOWER_BLUE.index);
	    cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框
	    cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框
	    cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框
	    cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框
		return cellStyle;
	}
	
	//获取普通单元格样式
	public static HSSFCellStyle getCommStyle(HSSFWorkbook workbook) {
		HSSFCellStyle style = workbook.createCellStyle();
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框
		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框
		style.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框
		style.setFont(getCommFont(workbook));
		return style;
	}	
	
	//填充标题数据
	public static void setTitleData(String[] title,HSSFSheet sheet,HSSFCellStyle style) {
		HSSFRow row = sheet.createRow(0);  
	    row.setHeight((short)(15.625*30));
		//创建单元格
	    HSSFCell cell=null;
	    //循环为第一行三个单元格插入三个标题
	    for(int i=0;i<title.length;i++){
	        cell=row.createCell(i);
	        cell.setCellStyle(style);
	        cell.setCellValue(title[i]);
	    }
	}
	
	//填充内容数据
	public static void setContentData(List<String[]> list,HSSFSheet sheet,HSSFCellStyle style) {
		int len = list.size();
		for(int i=0;i<len;i++) {
			HSSFRow nextrow=sheet.createRow(i+1);
			String content[]=list.get(i);
			for(int j=0;j<content.length;j++) {
				HSSFCell cell=nextrow.createCell(j);
				cell.setCellValue(content[j]);
				cell.setCellStyle(style);
			}
			
		}
	}
	
	//导出
	public static void export(String filePath,HSSFWorkbook workbook) {
		File file=new File(filePath);
	    try {
	        file.createNewFile();
	        //将Excel存盘
	        FileOutputStream stream=new FileOutputStream(file);
	        workbook.write(stream);
	        stream.close();

	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}
	
	public static boolean saveExcel(String[] title,List<String[]> list,String path) {
		try {
			//创建Excel工作簿
	        HSSFWorkbook workbook=new HSSFWorkbook();
	        //创建一个工作表sheet  默认是表名是sheet0
	        HSSFSheet sheet = ExcelUtils.getSheet(workbook, "矿机状态信息");
	        HSSFCellStyle titleStyle = ExcelUtils.getTitleStyle(workbook);
	        HSSFCellStyle commStyle = ExcelUtils.getCommStyle(workbook);
	        ExcelUtils.setTitleData(title, sheet, titleStyle);
	        ExcelUtils.setContentData(list, sheet, commStyle);        
	        String filePath =path +"//"+ getDateRandom()+".xls";
	        ExcelUtils.export(filePath, workbook);
	        System.out.println(filePath);
		}catch(Exception e) {
			return false;
		}        
        return true;
	}
	
	/**
	 * 生成时间随机数 
	 * @return
	 */
	public static String getDateRandom(){
		String s=new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
		return s;
	}
	
    public static void main(String[] args) {
    	String[] title={"id","name","sex"};
    	List<String[]> list = new ArrayList<String[]>();
        String[] content1={"1","zhangshan","男"};
        String[] content2={"2","lisi","男"};
        String[] content3={"3","zhaoliu","女12"};
        list.add(content1);
        list.add(content2);
        list.add(content3);
        String path = PropertiesUtils.getJarPath();
        saveExcel(title,list,path);
    	/**
    	//表头标题
        String[] title={"id","name","sex"};
        //创建Excel工作簿
        HSSFWorkbook workbook=new HSSFWorkbook();
        //创建一个工作表sheet  默认是表名是sheet0
        HSSFSheet sheet = ExcelUtils.getSheet(workbook, "矿机信息");
        HSSFCellStyle titleStyle = ExcelUtils.getTitleStyle(workbook);
        HSSFCellStyle commStyle = ExcelUtils.getCommStyle(workbook);
        ExcelUtils.setTitleData(title, sheet, titleStyle);
        List<String[]> list = new ArrayList<String[]>();
        String[] content1={"1","zhangshan","男"};
        String[] content2={"2","lisi","男"};
        String[] content3={"3","zhaoliu","女1"};
        list.add(content1);
        list.add(content2);
        list.add(content3);
        ExcelUtils.setContentData(list, sheet, commStyle);
        String filePath =PropertiesUtils.getJarPath()+"/test.xls";
        ExcelUtils.export(filePath, workbook);
        System.out.println(filePath);
        **/
    /**	
    //表头标题
    String[] title={"id","name","sex"};
    //创建Excel工作簿
    HSSFWorkbook workbook=new HSSFWorkbook();
    //创建一个工作表sheet  默认是表名是sheet0
    HSSFSheet sheet=workbook.createSheet("ygc开发表");
    //设置默认行高
    sheet.setDefaultRowHeight((short)(3 * 256));   
    sheet.setDisplayGridlines(false);//去网格线
    sheet.setDefaultColumnWidth(15);// 设置表格默认列宽度为20个字节     
    HSSFRow row = sheet.createRow(0);  
    row.setHeight((short)(15.625*30));
    //创建字体设置字体为宋体
    HSSFFont font = workbook.createFont();
    font.setFontName("宋体");
    //font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
    //设置字体高度
    font.setFontHeightInPoints((short) 20);
    
    //设置字体
    HSSFFont font2 = workbook.createFont();  
    font2.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
    HSSFCellStyle style = workbook.createCellStyle();
    style.setFillBackgroundColor(HSSFColor.LIGHT_CORNFLOWER_BLUE.index);
    style.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框
    style.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框
    style.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框
    style.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框
    style.setFont(font2);
    //设置自动换行
    style.setWrapText(true);

    //设置对齐方式为居中对齐
    //style.setAlignment(XSSFCellStyle.ALIGN_CENTER);
    //设置单元格内容垂直对其方式为居中
    //style.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
    //创建表的第一行
    //HSSFRow row=sheet.createRow(0);
    //创建单元格
    HSSFCell cell=null;
    //循环为第一行三个单元格插入三个标题
    for(int i=0;i<title.length;i++){
        cell=row.createCell(i);
        cell.setCellStyle(style);
        cell.setCellValue(title[i]);
    }
    //追加数据 1是第二行
    for(int i=1;i<=10;i++){
        HSSFRow nextrow=sheet.createRow(i);

        HSSFCell cell2=nextrow.createCell(0);
        cell2.setCellStyle(style);
        cell2.setCellValue("a"+i);

        cell2=nextrow.createCell(1);
        cell2.setCellValue("user"+i);
        cell2.setCellStyle(style);

        cell2=nextrow.createCell(2);
        cell2.setCellValue("男");
        cell2.setCellStyle(style);
    }
    //创建一个文件
    File file=new File("C:\\Users\\wwq\\Desktop\\test.xls");
    try {
        file.createNewFile();
        //将Excel存盘
        FileOutputStream stream=new FileOutputStream(file);
        workbook.write(stream);
        stream.close();

    } catch (IOException e) {
        e.printStackTrace();
    }
		*/
    }
}
