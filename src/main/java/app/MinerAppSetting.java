package app;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.TableEditor;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.SWTResourceManager;

import com.alibaba.fastjson.JSON;

import common.AppSettingConfig;
import common.LangConfig;
import dao.MinerConfigDao;
import dao.UiConfigDao;
import utils.PropertiesUtils;
import vo.MinerParamVO;
import vo.SetupVO;

public class MinerAppSetting extends Dialog
{

    protected Object result;
    protected Shell shell;
    private Text text;
    private Text text_2;
    private Label label;
    private Table table;
    private TableColumn tblclmnDd;
    private TableColumn tblclmnNewColumn;
    private TableColumn tblclmnNewColumn_1;
    private TableColumn tblclmnNewColumn_2;
    private TableColumn tblclmnNewColumn_3;
    private TableColumn tblclmnNewColumn_4;
    private TableColumn tblclmnNewColumn_5;
    private TableColumn tblclmnNewColumn_6;
    private TableColumn tblclmnNewColumn_7;
    private TableColumn tblclmnNewColumn_8;
    private TableColumn tblclmnNewColumn_9;
    private TableColumn tblclmnNewColumn_10;
    private TableColumn tblclmnNewColumn_11;
    private TableColumn tblclmnNewColumn_12;
    private Button btnNewButton;
    private Button btnNewButton_1;
    private Button button_3;
    private Button button_4;
    private Button button_5;
    private Composite composite;
    private Composite composite_1;
//    private Label lblmin;
//    private Text text_1;
//    private Button button;
    
    /**
     * Create the dialog.
     * @param parent
     * @param style
     */
    public MinerAppSetting(Shell parent, int style)
    {
        super(parent, style);
        setText(LangConfig.getKey("app.setting.title"));
    }

    /**
     * Open the dialog.
     * @return the result
     */
    public Object open()
    {
        createContents();
        shell.open();
        shell.layout();
        Display display = getParent().getDisplay();
        while (!shell.isDisposed())
        {
            if (!display.readAndDispatch())
            {
                display.sleep();
            }
        }
        return result;
    }

    /**
     * Create contents of the dialog.
     */
    private void createContents()
    {
        shell = new Shell(getParent(), SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
        shell.setMinimumSize(new Point(400, 300));
        shell.setSize(622, 480);
        shell.setText(getText());
        shell.setLayout(new GridLayout(4, false));
        
        Label lblNewLabel = new Label(shell, SWT.CENTER);
        GridData gd_lblNewLabel = new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1);
        gd_lblNewLabel.widthHint = 106;
        lblNewLabel.setLayoutData(gd_lblNewLabel);
        lblNewLabel.setText(LangConfig.getKey("app.setting.scanTimeout"));
        
        text = new Text(shell, SWT.BORDER);
        GridData gd_text = new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1);
        gd_text.widthHint = 120;
        text.setLayoutData(gd_text);
        
        Label lblNewLabel_2 = new Label(shell, SWT.CENTER);
        lblNewLabel_2.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false, 1, 1));
        lblNewLabel_2.setText(LangConfig.getKey("app.setting.monitorCycle"));
        
        text_2 = new Text(shell, SWT.BORDER);
        GridData gd_text_2 = new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1);
        gd_text_2.widthHint = 120;
        text_2.setLayoutData(gd_text_2);
        
//        lblmin = new Label(shell, SWT.NONE);
//        lblmin.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
//        lblmin.setText("监控周期(min)：  ");
//        
//        text_1 = new Text(shell, SWT.BORDER);
//        GridData gd_text_1 = new GridData(SWT.LEFT, SWT.TOP, true, false, 1, 1);
//        gd_text_1.widthHint = 120;
//        text_1.setLayoutData(gd_text_1);
//        
//        button = new Button(shell, SWT.CHECK);
//        button.setText("是否监控");
//        new Label(shell, SWT.NONE);
        
        
        label = new Label(shell, SWT.NONE);
        label.setText(LangConfig.getKey("app.setting.rule"));
        new Label(shell, SWT.NONE);
        new Label(shell, SWT.NONE);
        
        composite_1 = new Composite(shell, SWT.NONE);
        GridData gd_composite_1 = new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1);
        gd_composite_1.heightHint = 28;
        gd_composite_1.widthHint = 173;
        composite_1.setLayoutData(gd_composite_1);
        
        
        btnNewButton = new Button(composite_1, SWT.NONE);
        btnNewButton.setBounds(122, 0, 36, 27);
        btnNewButton.addSelectionListener(new SelectionAdapter() {
        	@Override
        	public void widgetSelected(SelectionEvent e) {
        		//添加矿机类型、用户名、密码
        		int len = table.getItems().length;
        		String typeName = "type"+(len+1);
        		TableItem item = new TableItem(table, SWT.NONE);  
                item.setText(new String[]{typeName, "root", "root", "3000","40", "62", "62","5600", "6700", "20","51", "75", "30","success"});
        	}
        });
        btnNewButton.setText("+");
        
        btnNewButton_1 = new Button(composite_1, SWT.NONE);
        btnNewButton_1.setBounds(163, 0, 36, 27);
        btnNewButton_1.addSelectionListener(new SelectionAdapter() {
        	@Override
        	public void widgetSelected(SelectionEvent e) {
        		//删除矿机类型、用户名、密码
        		TableItem[] t = table.getItems();
        		//这里的循环如果正向的话会报错，数组越界，因为删除第一行后，第二行就变成了第一行，
        		//如果再删除第二行的话，因为已经不存在了，所以会报越界
        		int checkNum = 0;
        		for(int i=t.length-1;i>-1;i--){
	        		if(!t[i].getChecked()){
	        			continue;
	        		}
	        		checkNum++;
	        		if(checkNum != t.length) {
	        			table.remove(i);//保留最后一行
	        		}
	        		//table.remove(i);
        		}
        	}
        });
        btnNewButton_1.setText("-");
        
        table = new Table(shell, SWT.BORDER | SWT.CHECK | SWT.FULL_SELECTION | SWT.MULTI);  
        //table = new Table(shell, SWT.BORDER | SWT.FULL_SELECTION | SWT.MULTI);
        GridData gd_table = new GridData(SWT.FILL, SWT.FILL, true, false, 4, 2);
        gd_table.widthHint = 477;
        gd_table.heightHint = 220;
        table.setLayoutData(gd_table);
        table.setHeaderVisible(true);
        table.setLinesVisible(true);
        
        tblclmnDd = new TableColumn(table, SWT.NONE);
        tblclmnDd.setWidth(100);
        tblclmnDd.setText(LangConfig.getKey("app.setting.tab.minerType"));
        
        tblclmnNewColumn = new TableColumn(table, SWT.NONE);
        tblclmnNewColumn.setWidth(80);
        tblclmnNewColumn.setText(LangConfig.getKey("app.setting.tab.user"));
        
        tblclmnNewColumn_1 = new TableColumn(table, SWT.NONE);
        tblclmnNewColumn_1.setWidth(80);
        tblclmnNewColumn_1.setText(LangConfig.getKey("app.setting.tab.pass"));
        
        tblclmnNewColumn_2 = new TableColumn(table, SWT.NONE);
        tblclmnNewColumn_2.setWidth(60);
        tblclmnNewColumn_2.setText(LangConfig.getKey("app.setting.tab.power"));
        
        tblclmnNewColumn_3 = new TableColumn(table, SWT.NONE);
        tblclmnNewColumn_3.setWidth(60);
        tblclmnNewColumn_3.setText(LangConfig.getKey("app.setting.tab.minCp"));
        
        tblclmnNewColumn_4 = new TableColumn(table, SWT.NONE);
        tblclmnNewColumn_4.setWidth(60);
        tblclmnNewColumn_4.setText(LangConfig.getKey("app.setting.tab.maxU"));
        
        tblclmnNewColumn_5 = new TableColumn(table, SWT.NONE);
        tblclmnNewColumn_5.setWidth(60);
        tblclmnNewColumn_5.setText(LangConfig.getKey("app.setting.tab.maxI"));
        
        tblclmnNewColumn_6 = new TableColumn(table, SWT.NONE);
        tblclmnNewColumn_6.setWidth(90);
        tblclmnNewColumn_6.setText(LangConfig.getKey("app.setting.tab.minSpeed"));
        
        tblclmnNewColumn_7 = new TableColumn(table, SWT.NONE);
        tblclmnNewColumn_7.setWidth(90);
        tblclmnNewColumn_7.setText(LangConfig.getKey("app.setting.tab.maxSpeed"));
        
        tblclmnNewColumn_8 = new TableColumn(table, SWT.NONE);
        tblclmnNewColumn_8.setWidth(80);
        tblclmnNewColumn_8.setText(LangConfig.getKey("app.setting.tab.maxRefuse"));
        
        tblclmnNewColumn_9 = new TableColumn(table, SWT.NONE);
        tblclmnNewColumn_9.setWidth(80);
        tblclmnNewColumn_9.setText(LangConfig.getKey("app.setting.tab.maxInOutTemp"));
        
        tblclmnNewColumn_10 = new TableColumn(table, SWT.NONE);
        tblclmnNewColumn_10.setWidth(60);
        tblclmnNewColumn_10.setText(LangConfig.getKey("app.setting.tab.highTemp"));
        
        tblclmnNewColumn_11 = new TableColumn(table, SWT.NONE);
        tblclmnNewColumn_11.setWidth(60);
        tblclmnNewColumn_11.setText(LangConfig.getKey("app.setting.tab.lowTemp"));
                
        tblclmnNewColumn_12 = new TableColumn(table, SWT.NONE);
        tblclmnNewColumn_12.setWidth(80);
        tblclmnNewColumn_12.setText(LangConfig.getKey("app.setting.tab.freqStatus"));
                
        addEditListener(table);
        new Label(shell, SWT.NONE);
        new Label(shell, SWT.NONE);
        new Label(shell, SWT.NONE);
        new Label(shell, SWT.NONE);
        
        composite = new Composite(shell, SWT.NONE);
        GridData gd_composite = new GridData(SWT.FILL, SWT.TOP, false, false, 4, 1);
        gd_composite.widthHint = 173;
        composite.setLayoutData(gd_composite);
        
        button_4 = new Button(composite, SWT.NONE);
        button_4.addSelectionListener(new SelectionAdapter() {
        	@Override
        	public void widgetSelected(SelectionEvent e) {
        		saveData();
        	}
        });
        button_4.setBounds(504, 10, 44, 27);
        button_4.setText(LangConfig.getKey("app.setting.confirm"));
        
        button_5 = new Button(composite, SWT.NONE);
        button_5.addSelectionListener(new SelectionAdapter() {
        	@Override
        	public void widgetSelected(SelectionEvent e) {
        		shell.close();
        	}
        });
        button_5.setBounds(454, 10, 44, 27);
        button_5.setText(LangConfig.getKey("app.setting.cancel"));
        
        button_3 = new Button(composite, SWT.NONE);
        button_3.addSelectionListener(new SelectionAdapter() {
        	@Override
        	public void widgetSelected(SelectionEvent e) {
        		table.removeAll();
        		readDefault();
        	}
        });
        button_3.setBounds(0, 10, 60, 27);
        button_3.setText(LangConfig.getKey("app.setting.default"));
        //addEditListener(table_1);
        
        readConfig();
    }
    
    //读取默认配置(打开设置窗口）
    public void readDefault() {
    	//读取默认配置类
    	text.setText(AppSettingConfig.SCAN_TIMEOUT + "");//扫描超时时间(s)
    	text_2.setText(AppSettingConfig.MONITOR_TIME + "");//监控周期（min）：
    	List<MinerParamVO> minerList = AppSettingConfig.initMinerVO();
    	readTableData(minerList);
    }
    
    //读取配置(打开设置窗口）
    public void readConfig() {
    	//读取数据库
    	Map<String,String> map = UiConfigDao.selectConfig();
    	text.setText(map.get("SCAN_TIMEOUT"));//扫描超时时间(s)
    	text_2.setText(map.get("MONITOR_TIME"));//监控周期（min）
    	List<MinerParamVO> minerList = MinerConfigDao.getMinerList();
    	readTableData(minerList);
    }
    
    //填充数据
    public void readTableData(List<MinerParamVO> minerList) {
    	for(int i=0;i<minerList.size();i++) {
    		TableItem item = new TableItem(table, SWT.NONE);  
            item.setText(new String[]{
            		minerList.get(i).getType(), 
            		minerList.get(i).getUsername(), 
            		minerList.get(i).getPassword(),
            		minerList.get(i).getPower(),
				    minerList.get(i).getCpWarn(),
				    minerList.get(i).getMaxVoltage(),
				    minerList.get(i).getMaxCurrent(),
				    minerList.get(i).getMinFanSpeed(),
				    minerList.get(i).getMaxFanSpeed(),
				    minerList.get(i).getMaxRefuseRate(),
				    minerList.get(i).getMaxPsuInOutTemprature(),
				    minerList.get(i).getMaxTemprature(),
				    minerList.get(i).getMinTemprature(),
				    minerList.get(i).getScanStatus()
            		});
            //TableItem item2 = new TableItem(table_1, SWT.NONE);  
            //item2.setText(new String[]{minerList.get(i).getMinerType(), minerList.get(i).getMinerCpWarn()});
    	}
    }
    
    //获取数据
    public SetupVO getConfigData() {
    	SetupVO setupVO = new SetupVO();   	
    	setupVO.setMonitorTime(text_2.getText().trim());
    	setupVO.setScanTimeout(text.getText().trim());
//    	setupVO.setMonitorCycle(text_1.getText().trim());
//    	if(button.getSelection()) {
//    		setupVO.setIsMonitor("1");
//    	}else {
//    		setupVO.setIsMonitor("0");
//    	}
    	List<MinerParamVO> list = new ArrayList<MinerParamVO>();
    	TableItem[] t = table.getItems();
    	for(int i=0;i<t.length;i++) {
    		MinerParamVO minerParamVO = new MinerParamVO();
    		minerParamVO.setType(t[i].getText(0).trim());
			minerParamVO.setUsername(t[i].getText(1).trim());
			minerParamVO.setPassword(t[i].getText(2).trim());
			minerParamVO.setPower(t[i].getText(3).trim());
			minerParamVO.setCpWarn(t[i].getText(4).trim());
			minerParamVO.setMaxVoltage(t[i].getText(5).trim());
			minerParamVO.setMaxCurrent(t[i].getText(6).trim());
			minerParamVO.setMinFanSpeed(t[i].getText(7).trim());
			minerParamVO.setMaxFanSpeed(t[i].getText(8).trim());
			minerParamVO.setMaxRefuseRate(t[i].getText(9).trim());
			minerParamVO.setMaxPsuInOutTemprature(t[i].getText(10).trim());
			minerParamVO.setMaxTemprature(t[i].getText(11).trim());
			minerParamVO.setMinTemprature(t[i].getText(12).trim());
			minerParamVO.setScanStatus(t[i].getText(13).trim());
			list.add(minerParamVO);
    	}
    	setupVO.setMinerList(list);
    	//setupVO.setTempHigh(null);
    	//setupVO.setTempLow(null);    	
    	//PropertiesUtils.saveConfig(setupVO);
    	return setupVO;
    }
    
    //校验数据
    public boolean checkData(SetupVO setupVO) {
    	if(!isInteger(setupVO.getScanTimeout().trim())||"".equals(setupVO.getScanTimeout().trim())) {
    		infoDialog(LangConfig.getKey("app.setting.mess.timeout")); 
    		return false;
    	}
//    	if(!isInteger(setupVO.getScanThreads().trim())||"".equals(setupVO.getScanThreads().trim())) {
//    		infoDialog("扫描并发数只能填写整数！"); 
//    		return false;
//    	}
    	if(!isInteger(setupVO.getMonitorTime().trim())||"".equals(setupVO.getMonitorTime().trim())) {
    		infoDialog(LangConfig.getKey("app.setting.mess.monitorCycle")); 
    		return false;
    	}
    	List<MinerParamVO> list = setupVO.getMinerList();    	
    	if(cheakIsRepeat(list)) {
    		infoDialog(LangConfig.getKey("app.setting.mess.repeat")); 
    		return false;
    	}
    	for(int i=0;i<list.size();i++) {
    		MinerParamVO vo = list.get(i);
    		String type = vo.getType().trim();
    		if("".equals(type)) {
    			infoDialog(LangConfig.getKey("app.setting.mess.devNotNull")); 
        		return false;
    		}
    		if("".equals(vo.getPower().trim())||!isInteger(vo.getPower().trim())) {
    			infoDialog("["+type+"]："+LangConfig.getKey("app.setting.mess.power")); 
        		return false;
    		}
    		if("".equals(vo.getCpWarn().trim())||!isInteger(vo.getCpWarn().trim())) {
    			infoDialog("["+type+"]："+LangConfig.getKey("app.setting.mess.minCp")); 
        		return false;
    		}
    		if("".equals(vo.getMaxVoltage().trim())||!isInteger(vo.getMaxVoltage().trim())) {
    			infoDialog("["+type+"]："+LangConfig.getKey("app.setting.mess.maxU")); 
        		return false;
    		}
    		if("".equals(vo.getMaxCurrent().trim())||!isInteger(vo.getMaxCurrent().trim())) {
    			infoDialog("["+type+"]："+LangConfig.getKey("app.setting.mess.maxI")); 
        		return false;
    		}
    		if("".equals(vo.getMinFanSpeed().trim())||!isInteger(vo.getMinFanSpeed().trim())) {
    			infoDialog("["+type+"]："+LangConfig.getKey("app.setting.mess.minSpeed")); 
        		return false;
    		}
    		if("".equals(vo.getMaxFanSpeed().trim())||!isInteger(vo.getMaxFanSpeed().trim())) {
    			infoDialog("["+type+"]："+LangConfig.getKey("app.setting.mess.maxSpeed")); 
        		return false;
    		}
    		if("".equals(vo.getMaxRefuseRate().trim())||!isInteger(vo.getMaxRefuseRate().trim())) {
    			infoDialog("["+type+"]："+LangConfig.getKey("app.setting.mess.maxRefuse")); 
        		return false;
    		}
    		if("".equals(vo.getMaxPsuInOutTemprature().trim())||!isInteger(vo.getMaxPsuInOutTemprature().trim())) {
    			infoDialog("["+type+"]："+LangConfig.getKey("app.setting.mess.maxInOutTemp")); 
        		return false;
    		}
    		if("".equals(vo.getMaxTemprature().trim())||!isInteger(vo.getMaxTemprature().trim())) {
    			infoDialog("["+type+"]："+LangConfig.getKey("app.setting.mess.highTemp")); 
        		return false;
    		}
    		if("".equals(vo.getMinTemprature().trim())||!isInteger(vo.getMinTemprature().trim())) {
    			infoDialog("["+type+"]："+LangConfig.getKey("app.setting.mess.lowTemp")); 
        		return false;
    		}

    	}
    	
    	return true;
    }
        
    /*
     * 判断数组中是否有重复的值
     */
    public boolean cheakIsRepeat(List<MinerParamVO> list) {
    	String[] array = new String[list.size()] ;
    	for(int i=0;i<list.size();i++) {
    		array[i]=list.get(i).getType().trim();
    	}
        HashSet<String> hashSet = new HashSet<String>();
        for (int i = 0; i < array.length; i++) {
            hashSet.add(array[i]);
        }
        if (hashSet.size() == array.length) {
            return false;
        } else {
            return true;
        }
    }
    
    //保存数据
    public void saveData() {
    	SetupVO setupVO = getConfigData();
    	if(checkData(setupVO)) {
    		UiConfigDao.updateByKey("SCAN_TIMEOUT", setupVO.getScanTimeout());
    		UiConfigDao.updateByKey("MONITOR_TIME", setupVO.getMonitorTime());
    		if(MinerConfigDao.delete()) {
    			List<MinerParamVO> list = setupVO.getMinerList();
        		for(MinerParamVO minerVO : list) {        			
        			MinerConfigDao.insert(minerVO);
        		}
    		}    		
    		//PropertiesUtils.saveConfig(setupVO);
    		shell.close();
    	}   	
    }
    
    public void infoDialog(String msg) {
    	MessageBox dialog=new MessageBox(shell,SWT.OK|SWT.ICON_INFORMATION);
        dialog.setText("Info");
        dialog.setMessage(msg);
        dialog.open();
    }
    
    public void addEditListener(Table table) {
    	final TableEditor editor = new TableEditor(table);  
    	editor.horizontalAlignment = SWT.LEFT;  
        editor.grabHorizontal = true;  
        editor.minimumWidth = 50;  
        // editing the second column  
        //final TableEditor editor = new TableEditor(table); 
        table.addMouseListener(new MouseAdapter() {
            public void mouseDown(MouseEvent event) {
              Control old = editor.getEditor();
              if (old != null)
                old.dispose();
              Point pt = new Point(event.x, event.y);
              final TableItem item = table.getItem(pt);
              if (item == null) {
                return;
              }
              int column = -1;
              for (int i = 0, n = table.getColumnCount(); i < n; i++) {
                Rectangle rect = item.getBounds(i);
                if (rect.contains(pt)) {
                  column = i;
                  break;
                }
              }
     
              if (column > 12) {
                return;
              }
              //System.out.println(column);
              final Text text = new Text(table, SWT.NONE);
              text.setForeground(item.getForeground());

              text.setText(item.getText(column));
              text.setForeground(item.getForeground());
              text.selectAll();
              text.setFocus();
              text.setFont(SWTResourceManager.getFont("宋体", 12, SWT.NORMAL));

              editor.minimumWidth = text.getBounds().width;

              editor.setEditor(text, item, column);

              final int col = column;
              text.addModifyListener(new ModifyListener() {
                public void modifyText(ModifyEvent event) {
                  item.setText(col, text.getText());
                  //System.out.println("Text modified to " + text.getText());
                }
              });
            }
          });
    }
        
    /*
     * 判断是否为整数 
     * @param str 传入的字符串 
     * @return 是整数返回true,否则返回false 
     */
    public boolean isInteger(String str) {  
        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");  
        return pattern.matcher(str).matches();  
    }
}
