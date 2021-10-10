package utils;

import java.text.Collator;
import java.util.Locale;
import org.eclipse.swt.*;
import org.eclipse.swt.widgets.*;

import service.MinerRuleService;
 
public class TableColumnSorter {
 
 
 public static void addNumberSorter(final Table table, final TableColumn column,boolean columnSort) {
    column.addListener(SWT.Selection, new Listener() {
     //boolean isAscend = true; // 按照升序排序
     boolean isAscend = columnSort;
     public void handleEvent(Event e) {
      int columnIndex = getColumnIndex(table, column);
      TableItem[] items = table.getItems();
      //使用冒泡法进行排序
      for (int i = 1; i < items.length; i++) {
       String strvalue2 = items[i].getText(columnIndex);
       if(strvalue2.equalsIgnoreCase("")){
        //当遇到表格中的空项目时，就停止往下检索排序项目
        break;
       }
       for (int j = 0; j < i; j++) {
        String strvalue1 = items[j].getText(columnIndex);
        //将字符串类型数据转化为float类型
        float numbervalue1=Float.valueOf(strvalue1);
        float numbervalue2=Float.valueOf(strvalue2);
        boolean isLessThan =false;
        if(numbervalue2<numbervalue1){
         isLessThan =true;
        }    
        if ((isAscend && isLessThan)
          || (!isAscend && !isLessThan)) {
         String[] values = getTableItemText(table, items[i]);
         Object obj = items[i].getData();
         items[i].dispose();
         TableItem item = new TableItem(table, SWT.NONE, j);
         item.setText(values);
         item.setData(obj);
         MinerRuleService.addRuleCss(item);//增加样式
         items = table.getItems();
         break;
        }
       }
      }
      table.setSortColumn(column);
      table.setSortDirection((isAscend ? SWT.UP : SWT.DOWN));
      isAscend = !isAscend;
     }
    });
 }
 
 public static void addNumberSorterAuto(final Table table, final TableColumn column,boolean columnSort) {
	    
	     //boolean isAscend = true; // 按照升序排序
	     boolean isAscend = columnSort;
	     
	      int columnIndex = getColumnIndex(table, column);
	      TableItem[] items = table.getItems();
	      //使用冒泡法进行排序
	      for (int i = 1; i < items.length; i++) {
	       String strvalue2 = items[i].getText(columnIndex);
	       if(strvalue2.equalsIgnoreCase("")){
	        //当遇到表格中的空项目时，就停止往下检索排序项目
	        break;
	       }
	       for (int j = 0; j < i; j++) {
	        String strvalue1 = items[j].getText(columnIndex);
	        //将字符串类型数据转化为float类型
	        float numbervalue1=Float.valueOf(strvalue1);
	        float numbervalue2=Float.valueOf(strvalue2);
	        boolean isLessThan =false;
	        if(numbervalue2<numbervalue1){
	         isLessThan =true;
	        }    
	        if ((isAscend && isLessThan)
	          || (!isAscend && !isLessThan)) {
	         String[] values = getTableItemText(table, items[i]);
	         Object obj = items[i].getData();
	         items[i].dispose();
	         TableItem item = new TableItem(table, SWT.NONE, j);
	         item.setText(values);
	         item.setData(obj);
	         MinerRuleService.addRuleCss(item);//增加样式
	         items = table.getItems();
	         break;
	        }
	       }
	      }
	      table.setSortColumn(column);
	      table.setSortDirection((isAscend ? SWT.UP : SWT.DOWN));
	      isAscend = !isAscend;
	     	    
	 }
 
 
 public static void addStringSorter(final Table table, final TableColumn column,boolean columnSort) {
     column.addListener(SWT.Selection, new Listener() {
      //boolean isAscend = true; // 按照升序排序
      boolean isAscend = columnSort;
      Collator comparator = Collator.getInstance(Locale.getDefault());
      public void handleEvent(Event e) {
       int columnIndex = getColumnIndex(table, column);
       TableItem[] items = table.getItems();
      //使用冒泡法进行排序
       for (int i = 1; i < items.length; i++) {
        String str2value = items[i].getText(columnIndex);
        if(str2value.equalsIgnoreCase("")){
         //当遇到表格中的空项目时，就停止往下检索排序项目
         break;
        }
        for (int j = 0; j < i; j++) {
         String str1value = items[j].getText(columnIndex);
         boolean isLessThan = comparator.compare(str2value, str1value) < 0;     
         if ((isAscend && isLessThan)
           || (!isAscend && !isLessThan)) {
          String[] values = getTableItemText(table, items[i]);
          Object obj = items[i].getData();
          items[i].dispose();
          TableItem item = new TableItem(table, SWT.NONE, j);
          item.setText(values);
          item.setData(obj);
          MinerRuleService.addRuleCss(item);//增加样式
          items = table.getItems();
          break;
         }
        }
       }
       table.setSortColumn(column);
       table.setSortDirection((isAscend ? SWT.UP : SWT.DOWN));
       isAscend = !isAscend;
      }
     });
  }
 
 public static void addStringSorterAuto(final Table table, final TableColumn column,boolean columnSort) {
     
      //boolean isAscend = true; // 按照升序排序
      boolean isAscend = columnSort;
      Collator comparator = Collator.getInstance(Locale.getDefault());
      
       int columnIndex = getColumnIndex(table, column);
       TableItem[] items = table.getItems();
      //使用冒泡法进行排序
       for (int i = 1; i < items.length; i++) {
        String str2value = items[i].getText(columnIndex);
        if(str2value.equalsIgnoreCase("")){
         //当遇到表格中的空项目时，就停止往下检索排序项目
         break;
        }
        for (int j = 0; j < i; j++) {
         String str1value = items[j].getText(columnIndex);
         boolean isLessThan = comparator.compare(str2value, str1value) < 0;     
         if ((isAscend && isLessThan)
           || (!isAscend && !isLessThan)) {
          String[] values = getTableItemText(table, items[i]);
          Object obj = items[i].getData();
          items[i].dispose();
          TableItem item = new TableItem(table, SWT.NONE, j);
          item.setText(values);
          item.setData(obj);
          MinerRuleService.addRuleCss(item);//增加样式
          items = table.getItems();
          break;
         }
        }
       }
       table.setSortColumn(column);
       table.setSortDirection((isAscend ? SWT.UP : SWT.DOWN));
       isAscend = !isAscend;
      
     
  }
 
 public static int getColumnIndex(Table table, TableColumn column) {
    TableColumn[] columns = table.getColumns();
    for (int i = 0; i < columns.length; i++) {
     if (columns[i].equals(column))
      return i;
    }
    return -1;
 }
 
 public static String[] getTableItemText(Table table, TableItem item) {
    int count = table.getColumnCount();
    String[] strs = new String[count];
    for (int i = 0; i < count; i++) {
     strs[i] = item.getText(i);
    }
    return strs;
 }
 

}
