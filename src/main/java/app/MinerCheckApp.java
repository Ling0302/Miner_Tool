package app;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.ProgressEvent;
import org.eclipse.swt.browser.ProgressListener;
import org.eclipse.swt.browser.StatusTextEvent;
import org.eclipse.swt.browser.StatusTextListener;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.ShellAdapter;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontMetrics;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.ProgressBar;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.SWTResourceManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import common.DbFile;
import common.LangConfig;
import common.Log4jConfig;
import common.LogoConfig;
import service.BatchUpgradeService;
import service.ButtonStatusService;
import service.ExcelExportService;
import service.FileService;
import service.FixedIpUiService;
import service.IPReportThreadService;
import service.IpSettingServcie;
import service.LedCtrlService;
import service.MinerBatchService;
import service.MinerCmdService;
import service.MinerScanService;
import service.MinerSearchService;
import service.UiService;
import service.UpgradeUiService;
import utils.ClipBoardUtil;
import utils.Constants;
import utils.TableColumnSorter;
import vo.FixedIpVO;
import vo.IpsVO;
import vo.MinerSearchVO;
import vo.PoolConfig;
import vo.PoolVO;

public class MinerCheckApp
{
    public static Shell shell;
    private Table resultTable;
    private ProgressBar progressBar;
    
    private Text text;
    private Text text_3;
    private Text text_6;
    
    private Text text_1;
    private Text text_4;
    private Text text_7;
    
    private Text text_2;
    private Text text_5;
    private Text text_8;
    
    private ScrolledComposite ipListScrolledCompsite;
    
    public static Composite ipListCompsite;
     
    public static boolean isScan;
    public static boolean isMonitor;
    public static boolean isLoadIp;
    public static boolean isUpgrade;
    public static boolean isConfigFixedIp;
    public static boolean isRestartMiner;
        
    public static Map<String,String> minersVersionMap;
    public static boolean ipSort;//ip列排序，true为升序
    public static boolean minertypeSort;
    public static boolean workerSort;
    public static boolean avgCpSort;
    public static boolean cpSort;
    public static boolean versionSort;
    public static boolean uptimeSort;
    public static boolean statusSort;
    public static int ipLoadNum;
    public static IpsVO ips;
    
    private Spinner spinner;
    private Text text_9;
    private Text text_10;
    private Text text_11;
    public static Label lblNewLabel_13;   
    
    /**
     * 固定IP-tab页
     * */
    private Text text_12;
    private Text text_13;
    private Text text_14;
    private Text text_15;
    private Text text_16;
    private Text text_17;
    private Text text_18;
    private Text text_19;
    private Text text_20;
    private Text text_21;
    private Text text_22;
    private Text text_23;
    private Text text_24;
    private Text text_25;
    private Text text_26;
    private Text text_27;
    private Text text_28;
    private Text text_29;
    private Text text_30;
    
    public static Table macTable;
    private Text text_31;
    private Text text_32;    
    private Label lblNewLabel_22;
    
    
    public static Combo combo;
    public static String filePath;
    public static Table table_gj;
    public static Label lblNewLabel_23;
    
    //中英文全局变量
    public static TabItem tabItem_1;
    public static TabItem tabItem_3;
    public static TabItem tabItem_4;
    
    //miner scan ui
    public static Button button_5;
    public static Button btnNewButton;
    
    public static Button btnCheck_select_all;
    public static Button button_display_success;
    public static Button button_scan;
    //public static Button button_monitor;
    //public static Button button_config_all;
    public static Button button_config_select;
    public static Button button_reset;
    public static Button button_export;
    public static Button button_lighton;
    public static Button button_upgrade;
    public static Button button_setnologo;
    public static Button button_setting;
    public static Label lblNewLabel_11;
    public static Label lblNewLabel_24;
    //public static Label lblNewLabel_10;
    
    public static Button btnCheckButton_1;
    public static Label lblNewLabel;
    public static Label lblNewLabel_3;
    public static Label lblNewLabel_6;
    public static Button button_10;
    public static Button button_14;
    public static Button button_15;
    
    public static Button btnCheckButton_2;
    public static Label lblNewLabel_1;
    public static Label lblNewLabel_4;
    public static Label lblNewLabel_7;
    public static Button button_16;
    public static Button button_17;
    public static Button button_18;
      
    public static Button btnCheckButton_3;
    public static Label lblNewLabel_2;
    public static Label lblNewLabel_5;
    public static Label lblNewLabel_8;
    public static Button button_11;
    public static Button button_12;
    public static Button button_13;
    
    public static Label lblNewLabel_12;
    public static Label lblavg;
    public static Label label;
    public static Button button_2;
    public static Button button_3;
    
    public static TableColumn tblclmn_datetime;
    public static TableColumn tblclmn_index;
    public static TableColumn tblclmn_operation;
    public static TableColumn tblclmn_ip;
    public static TableColumn tblclmn_status;
    public static TableColumn tblclmn_minerType;
    public static TableColumn tblclmn_binType;
    public static TableColumn tblclmn_pool;
    public static TableColumn tblclmn_worker;
    public static TableColumn tblclmn_chipCount;
    public static TableColumn tblclmn_realHash;
    public static TableColumn tblclmn_avgHash;
    public static TableColumn tblclmn_firmwareVersion;
    public static TableColumn tblclmn_psuVersion;
    public static TableColumn tblclmn_hardwareVersion;
    public static TableColumn tblclmn_softVersion;
    public static TableColumn tblclmn_temperature;
    public static TableColumn tblclmn_fanSpeed;
    public static TableColumn tblclmn_fanDuty;
    public static TableColumn tblclmn_devFreq;
    public static TableColumn tblclmn_chipVolt;
    public static TableColumn tblclmn_volt;
    public static TableColumn tblclmn_networkType;
    public static TableColumn tblclmn_macAddress;
    public static TableColumn tblclmn_runningTime;
    
    //static ip ui
    public static Button button_plus;
    public static Button button_minus;
    
    public static Label lblNewLabel_14;
    public static Label lbl_14;
    public static Label lbl_15;
    public static Label label_1;
    public static Label lblDns;
        
    public static Label lblNewLabel_15;
    public static Label label_kc1;
    public static Label label_kc2;
    public static Label label_kc3;
    public static Label label_kc4;   
    public static Button button_kc_1;
    public static Button button_kc_2;
    public static Button button_kc_3;
    public static Button button_kc_4;
    public static Button button_kc_5;
    public static Button button_kc_6;    
    public static Button button_kc_7;
    public static Button button_kc_8;
    public static Button button_kc_9;
    
    public static Label lblNewLabel_18;
    public static Label lbl_hjh;
    public static Label lbl_cs;
    public static Label lbl_wzs;
    public static Label label_nextPoint;
    public static Label lbl_nextLayer;
    public static Label lbl_step;
    
    public static Button button_4;
    public static Label lblNewLabel_16;
    public static Button btnCheckButton;
    public static Button btnCheckButton_4;
    public static Button btnCheckButton_5;
    public static Button btnCheckButton_16;
    public static Button btnNewButton_1;
    
    public static TableColumn tb_miner_ip;
    public static TableColumn tb_target_ip;
    public static TableColumn tb_miner_mac;
    public static TableColumn tb_congfig_ip_status;
    public static TableColumn tb_congfig_pool_status;
    public static TableColumn tb_congfig_point_status;
    public static TableColumn tb_zwym;
    public static TableColumn tb_mrwg;
    public static TableColumn tb_dns;
    public static TableColumn tb_point;
    public static TableColumn tb_pool1;
    public static TableColumn tb_worker1;
    public static TableColumn tb_pwd1;
    public static TableColumn tb_pool2;
    public static TableColumn tb_worker2;
    public static TableColumn tb_pwd2;
    public static TableColumn tb_pool3;
    public static TableColumn tb_worker3;
    public static TableColumn tb_pwd3;
    
    //upgrade ui
    public static Label lblNewLabel_19;
    public static Button btnNewButton_2;
    //public static Label lblNewLabel_20;
    //public static Label lblNewLabel_21;
    public static Button btnNewButton_3;
    public static Button button_6;
    public static Button btnCheckButton_6;
    
    public static TableColumn table_ip;
    public static TableColumn table_status;
    public static TableColumn table_mac;
    public static TableColumn table_type;
    public static TableColumn table_version;
    public static TableColumn table_result;
    public static TableColumn table_note;
    
    
    private static final Logger logger = LoggerFactory.getLogger(MinerCheckApp.class);
    
    static {
    	//log4j
    	Log4jConfig.initLog4j();
    	
    	//ip-report
    	IPReportThreadService iPReportThreadService = new IPReportThreadService();
		iPReportThreadService.start();
		
		//初始化数据库连接
		DbFile.initDbFile();
		
		//初始化UI界面语言
		LangConfig.uiLang();
    }
    

	/**
     * Launch the application.
     * @param args
     */
    public static void main(String[] args)
    {
        try
        {
            MinerCheckApp window = new MinerCheckApp();
            window.open();
            
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Open the window.
     */
    public void open()
    {
        Display display = Display.getDefault();
        createContents();
        shell.open();
        shell.layout();
        while (!shell.isDisposed())
        {
            if (!display.readAndDispatch())
            {
                display.sleep();
            }
            
        }
       
    }

    /**
     * @wbp.parser.entryPoint
     */
    protected void createContents()
    {    		
		//主界面
        shell = new Shell();
        shell.addShellListener(new ShellAdapter() {
            @Override
            public void shellClosed(ShellEvent e) {
                System.exit(0);
            }
        });
        //shell.setMaximized(true);
        shell.setSize(1380, 800);
        shell.setText(LangConfig.getKey("app"));
        shell.setLayout(new FillLayout(SWT.HORIZONTAL));
        //shell.setBackground(SWTResourceManager.getColor(SWT.COLOR_BLUE));
        //Image image = new Image(shell.getDisplay(), "F:\\silva-work\\temp\\4.png");
        shell.setImage(LogoConfig.getLogoImage());

        logger.info("Miner-tool start!");
        //shell.addListener(, listener);
                        
        TabFolder tabFolder = new TabFolder(shell, SWT.NONE);
        //tabFolder.setBackground(SWTResourceManager.getColor(SWT.COLOR_BLUE));
        //tabFolder.setBackgroundImage(LogoConfig.getBgImage());
        //#232428
        //tabFolder.setBackground(SWTResourceManager.getColor(SWT.COLOR_BLUE));
        //tabFolder.setBackground(SWTResourceManager.getColor(SWT.COLOR_TITLE_INACTIVE_FOREGROUND));        
        
        tabItem_1 = new TabItem(tabFolder, SWT.NONE);
        tabItem_1.setText("矿机状态监控");
        
        Composite composite = new Composite(tabFolder, SWT.NONE);
        tabItem_1.setControl(composite);
        composite.setLayout(new GridLayout(11, false));
        
        btnCheck_select_all = new Button(composite, SWT.CHECK);
        GridData gd_btnCheck_select_all = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
        gd_btnCheck_select_all.widthHint = 64;
        btnCheck_select_all.setLayoutData(gd_btnCheck_select_all);
        btnCheck_select_all.addSelectionListener(new SelectionAdapter() {
        	@Override
        	public void widgetSelected(SelectionEvent e) {
        		Control[] cList = ipListCompsite.getChildren();
        		TableItem[] t = resultTable.getItems();
        		if(btnCheck_select_all.getSelection()) {
        			//选中
        			for (Control control : cList)
                    {
        				Button bt = (Button)control;
        				bt.setSelection(true);
                    }
        			for(int i=0;i<t.length;i++){
        				t[i].setChecked(true);
        			}
        		}else {
        			//不选中
        			for (Control control : cList)
                    {
        				Button bt = (Button)control;
        				bt.setSelection(false);
                    }
        			for(int i=0;i<t.length;i++){
        				t[i].setChecked(false);
        			}
        		}
        	}
        });
        btnCheck_select_all.setText("IP范围");
        btnCheck_select_all.setSelection(true);
        
        Label lblNewLabel_9 = new Label(composite, SWT.NONE);
        lblNewLabel_9.setText("                    ");
        new Label(composite, SWT.NONE);
        
        button_plus = new Button(composite, SWT.NONE);
        button_plus.setText("＋");
        //添加IP段
        button_plus.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                IpSettingDialog dialog = new IpSettingDialog(e.display.getActiveShell());
                dialog.open();
                if (dialog.getReturnCode() == Window.OK)
                {
                    Button tempButton = new Button(ipListCompsite, SWT.CHECK);
                    tempButton.setData(ips);
                    tempButton.setText(dialog.getIpValue());
                    tempButton.setSelection(true);
                    tempButton.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
                    tempButton.setBounds(5, (ipListCompsite.getChildren().length - 1) * 25 + 5, 200, 17);
                    tempButton.addSelectionListener(new SelectionAdapter() {
                    	@Override
                    	public void widgetSelected(SelectionEvent e) {
                    		String ips = tempButton.getText().trim().split(":")[1];
                    		if(tempButton.getSelection()) {
                    			//选中
                    			IpSettingServcie.groupSelect(ips,resultTable,true);
                    		}else {
                    			//不选中
                    			IpSettingServcie.groupSelect(ips,resultTable,false);
                    		}
                    	}
                    });
                    //双击进行编辑
                    tempButton.addListener(SWT.MouseDown, new Listener() {  
                    	@Override  
                	    public void handleEvent(Event event) {  
                	        if(event.button != 3) { //按键不是右键跳出. 1左键,2中键,3右键  
                	            return;  
                	        } 
                	        Control[] cList = ipListCompsite.getChildren();
                	        String index = "";
                	        for (Control control : cList) {
                	        	Button bt = (Button)control;
                	        	if(tempButton.equals(bt)) {
                	        		IpsVO ipsVO = (IpsVO)tempButton.getData();
                	        		index = ipsVO.getIndex();
                	        		break;
                	        	}
                	        	
                	        }
                	        String ipNote = tempButton.getText() + ":" + index;
                	        //System.out.println(ipNote);
                	        IpSettingDialog dialog2 = new IpSettingDialog(event.display.getActiveShell(),ipNote);                   	        
                            dialog2.open();
                            if (dialog2.getReturnCode() == Window.OK) {
                            	tempButton.setText(dialog2.getIpValue());
                                tempButton.setSelection(true);
                            }
                    	}
                    });
                    ipListCompsite.setSize(ipListCompsite.computeSize(SWT.DEFAULT, SWT.DEFAULT));//关键点3  
                }
                
            }
            
        });
        
        button_minus = new Button(composite, SWT.NONE);
        button_minus.setText("－");
        //删除选中的ip段
        button_minus.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                Control[] cList = ipListCompsite.getChildren();
                Control[] unchecked = new Control[cList.length];
                int unCheckLength = 0;
   
                List <Integer> indexLists = new ArrayList<Integer>();
                for (Control control : cList)
                {
                    Button bt = (Button)control;
                    IpsVO ipsVO = (IpsVO)bt.getData();
                    if (bt.getSelection())
                    {                   	
                        bt.dispose();
                        indexLists.add(Integer.parseInt(ipsVO.getIndex()));
                    }
                    else
                    {
                        unchecked[unCheckLength] = bt;
                        unCheckLength ++;
                    }
                    
                }
                for (int i = 0; i < unCheckLength; i++)
                {
                    Button bt = (Button)unchecked[i];
                    bt.setBounds(5, i * 25 + 5, 200, 17);
                }
                if(indexLists != null && !indexLists.isEmpty()) {
                	IpSettingServcie.removeConfigIp(indexLists);
                	IpSettingServcie.loadConfigIp();
                }
                ipListCompsite.setSize(ipListCompsite.computeSize(SWT.DEFAULT, SWT.DEFAULT));//关键点3  
            }
            
        });
        
        button_display_success = new Button(composite, SWT.CHECK);
        button_display_success.setSelection(true);
        button_display_success.setText("只显示成功矿机");
        
        Composite composite_toolbar = new Composite(composite, SWT.NONE);
        GridData gd_composite_toolbar = new GridData(SWT.LEFT, SWT.CENTER, false, false, 5, 1);
        gd_composite_toolbar.widthHint = 905;
        gd_composite_toolbar.heightHint = 30;
        composite_toolbar.setLayoutData(gd_composite_toolbar);
        
        button_scan = new Button(composite_toolbar, SWT.NONE);
        button_scan.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
            	ButtonStatusService.disableButton();
            	lblNewLabel_13.setText("");
            	if(isScan) {
            		//再按，停止扫描
            		isScan=false;
            		((Button)e.widget).setText(LangConfig.getKey("app.check.scanMiner"));
            		ButtonStatusService.enableButton();
            	}else {
            		//再按，开始扫描
            		button_scan.setEnabled(true);
            		if(isAction()) {
    					MinerCmdService.infoDialog(shell, LangConfig.getKey("app.message.actionWarn"));
    					return;
    				}
            		((Button)e.widget).setText(LangConfig.getKey("app.check.stopScan"));                	
                    Control[] cList = ipListCompsite.getChildren();
                    List<String> ipRange = new ArrayList<String>();
                    for (Control control : cList)
                    {
                        Button bt = (Button)control;
                        if (bt.getSelection())
                        {
                            ipRange.add(bt.getText());
                        }
                    }
                    if(ipRange.isEmpty()) {
                    	MinerCmdService.infoDialog(shell, "没有选择IP段！");
                    	return;//若IP没有选择
                    }
                    isScan = true;
                    MinerScanService.ScanThread(progressBar, resultTable, button_display_success.getSelection(), ipRange,shell);
            	}
            }
            
        });
        button_scan.setText("扫描矿机");
        button_scan.setBounds(10, 0, 60, 27);
        
        //button_monitor = new Button(composite_toolbar, SWT.NONE);
        /*
        button_monitor.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
            	ButtonStatusService.disableButton();
            	lblNewLabel_13.setText("");
                if(isMonitor) {
                	//已是监控状态，再按下则为停止监控                	
                	((Button)e.widget).setText(LangConfig.getKey("app.message.minerMonitor"));
                    isMonitor = false;
                    lblNewLabel_10.setText(LangConfig.getKey("app.message.noMonitor"));
                    ButtonStatusService.enableButton();
                }else {
                	//未监控状态，再按下则为监控状态
                	button_monitor.setEnabled(true);
                	if(isAction()) {
    					MinerCmdService.infoDialog(shell, LangConfig.getKey("app.message.actionWarn"));
    					return;
    				}               	
                	IpSettingServcie.loadConfigIp();
                    ((Button)e.widget).setText(LangConfig.getKey("app.message.stopMonitor"));
                    isMonitor = true;
                    lblNewLabel_10.setText(LangConfig.getKey("app.message.monitoring"));
                    MinerScanService.monitorThread(progressBar, resultTable, button_display_success.getSelection(), ipListCompsite);
                }
                
            }
        });
        button_monitor.setText("监控矿机");
        button_monitor.setBounds(75, 0, 60, 27);*/
        
        //button_config_all = new Button(composite_toolbar, SWT.NONE);
        //button_config_all.addSelectionListener(new SelectionAdapter() {
        	//@Override
        	//public void widgetSelected(SelectionEvent e) {
        		//MinerCmdService.infoDialog(shell, LangConfig.getKey("app.message.notOpen"));    	
//        		Integer spinnerIp = spinner.getSelection();
//        		List<PoolVO> poolList = getPoolList();
//        		MinerBatchService.configAllPool(resultTable, poolList, spinnerIp);
        		//MinerCmdService.configAllMiner(shell, resultTable,getPoolList(),spinner);
        	//}
        //});
        //button_config_all.setText("配置所有矿机");
        //button_config_all.setBounds(140, 0, 84, 27);
        
        button_config_select = new Button(composite_toolbar, SWT.NONE);
        button_config_select.addSelectionListener(new SelectionAdapter() {
        	@Override
        	public void widgetSelected(SelectionEvent e) {
        		  //MinerCmdService.infoDialog(shell, LangConfig.getKey("app.message.notOpen"));
        		  Integer spinnerIp = spinner.getSelection();
        		  List<PoolVO> poolList = getPoolList();
        		if(!MinerBatchService.isHaveRecord(resultTable)) {
        			MinerCmdService.infoDialog(shell, "没有选中任何矿机!");
        			return;
        		}
        		MinerBatchService.configSelectPool(resultTable, poolList, spinnerIp);
        		
        		//MinerCmdService.configSelectMiner(shell, resultTable, getPoolList(),spinner);
        	}
        });
        button_config_select.setText("配置选中矿机");
        button_config_select.setBounds(205, 0, 100, 27);
        
        button_reset = new Button(composite_toolbar, SWT.NONE);
        button_reset.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
            	if(!MinerBatchService.isHaveRecord(resultTable)) {
            		MinerCmdService.infoDialog(shell, "没有选中任何矿机!");
        			return;
        		}
            	
            	MinerBatchService.rebootMiner(resultTable);
            	//MinerCmdService.rebootAction(shell,resultTable);
           }
        });
        button_reset.setText("重启矿机");
        button_reset.setBounds(310, 0, 84, 27);
        
        button_export = new Button(composite_toolbar, SWT.NONE);
        button_export.addSelectionListener(new SelectionAdapter() {
        	@Override
        	public void widgetSelected(SelectionEvent e) {
        		ExcelExportService.saveExcel(shell,resultTable);
        	}
        });
        button_export.setText("导出");
        //button_export.setBounds(408, 0, 60, 27);
        button_export.setBounds(75, 0, 60, 27);
        
        button_lighton = new Button(composite_toolbar, SWT.NONE);
    	button_lighton.addSelectionListener(new SelectionAdapter() {
    	@Override
    	public void widgetSelected(SelectionEvent e) {
    		// Integer spinnerIp = spinner.getSelection();
    		if(!MinerBatchService.isHaveRecord(resultTable)) {
    			MinerCmdService.infoDialog(shell, "没有选中任何矿机!");
    			return;
    		}
    		
    		TableItem[] items = resultTable.getItems();         		
    		List<String> selectedIps = new ArrayList<>();
    		for(int i=0;i<items.length;i++) {
    			TableItem item = items[i];
    			if(item.getChecked()) {
    				selectedIps.add(items[i].getText(1).trim());
    			}
    		}
    		
    		//TableItem[] items = resultTable.getSelection();
    		LedCtrlService.redon(shell, selectedIps);
    	  }
    	});
        
        //button_lighton.addSelectionListener(new SelectionAdapter() {
        	//@Override
        	//public void widgetSelected(SelectionEvent e) {
        		//LedCtrlService.redon(shell,resultTable);
        	//}
        //});
        button_lighton.setText("点红灯");
        button_lighton.setBounds(140, 0, 60, 27);
        
        //button_upgrade = new Button(composite_toolbar, SWT.NONE);
        //button_upgrade.addSelectionListener(new SelectionAdapter() {
    	//@Override
    	//public void widgetSelected(SelectionEvent e) {
    		// Integer spinnerIp = spinner.getSelection();
    		//if(!MinerBatchService.isHaveRecord(resultTable)) {
    			//MinerCmdService.infoDialog(shell, "没有选中任何矿机!");
    			//return;
    		//}
    		
    		//TableItem[] items = resultTable.getItems();         		
    		//List<String> selectedIps = new ArrayList<>();
    		//for(int i=0;i<items.length;i++) {
    			//TableItem item = items[i];
    			//if(item.getChecked()) {
    				//selectedIps.add(items[i].getText(1).trim());
    			//}
    		//}

    		//BatchUpgradeService.upgrade(shell, selectedIps);
    	  //}
    	//});
        
        //button_lighton.addSelectionListener(new SelectionAdapter() {
        	//@Override
        	//public void widgetSelected(SelectionEvent e) {
        		//LedCtrlService.redon(shell,resulreable);
        	//}
        //});
        //button_upgrade.setText("升级到最新固件");
        //button_upgrade.setBounds(205, 0, 110, 27);
        
        // button_setnologo = new Button(composite_toolbar, SWT.NONE);
        // button_setnologo.addSelectionListener(new SelectionAdapter() {
    	//@Override
    	//public void widgetSelected(SelectionEvent e) {
    		// Integer spinnerIp = spinner.getSelection();
    		//if(!MinerBatchService.isHaveRecord(resultTable)) {
    			//MinerCmdService.infoDialog(shell, "没有选中任何矿机!");
    			//return;
    		//}
    		
    		//TableItem[] items = resultTable.getItems();         		
    		//List<String> selectedIps = new ArrayList<>();
    		//for(int i=0;i<items.length;i++) {
    			//TableItem item = items[i];
    			//if(item.getChecked()) {
    				//selectedIps.add(items[i].getText(1).trim());
    			//}
    		//}

    		//BatchUpgradeService.setnologo(shell, selectedIps);
    	  //}
    	//});
        
        //button_lighton.addSelectionListener(new SelectionAdapter() {
        	//@Override
        	//public void widgetSelected(SelectionEvent e) {
        		//LedCtrlService.redon(shell,resultTable);
        	//}
        //});
        //button_setnologo.setText("设置无LOGO");
        //button_setnologo.setBounds(320, 0, 90, 27);
        
        
//        Button button_close_hash = new Button(composite_toolbar, SWT.NONE);
//        button_close_hash.addSelectionListener(new SelectionAdapter() {
//        	@Override
//        	public void widgetSelected(SelectionEvent e) {
//        		MinerCmdService.shutdownAction(shell, resultTable);
//        	}
//        });
//        button_close_hash.setBounds(409, 0, 80, 27);
//        button_close_hash.setText("关闭矿机");
        
        button_setting = new Button(composite_toolbar, SWT.NONE);
        button_setting.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                MinerAppSetting dialog = new MinerAppSetting(e.display.getActiveShell(), SWT.NONE);
                dialog.open();
            }
        });
        button_setting.setBounds(400, 0, 60, 27);
        button_setting.setText("设置");
        
//        Button button = new Button(composite_toolbar, SWT.NONE);
//        button.addSelectionListener(new SelectionAdapter() {
//        	@Override
//        	public void widgetSelected(SelectionEvent e) {
//        		MinerCmdService.openAction(shell, resultTable);
//        	}
//        });
//        button.setText("开启矿机");
//        button.setBounds(495, 0, 80, 27);
        
        //lblNewLabel_10 = new Label(composite_toolbar, SWT.NONE);
        //lblNewLabel_10.setBounds(823, 5, 72, 17);
        //lblNewLabel_10.setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));
        //lblNewLabel_10.setText("未监控");
        
//        Button button_1 = new Button(composite_toolbar, SWT.NONE);
//        button_1.addSelectionListener(new SelectionAdapter() {
//        	@Override
//        	public void widgetSelected(SelectionEvent e) {
//        		MinerVersionService.upGrade(shell,resultTable);
//        	}
//        });
//        button_1.setBounds(581, 0, 57, 27);
//        button_1.setText("版本升级");
        
        lblNewLabel_11 = new Label(composite_toolbar, SWT.NONE);
        lblNewLabel_11.setBounds(480, 5, 70, 17);
        lblNewLabel_11.setText("矿工(IP位数):");
        
        spinner = new Spinner(composite_toolbar, SWT.BORDER);
        spinner.setBounds(560, 2, 36, 23);
        spinner.setMaximum(4);
		spinner.setMinimum(1);
		spinner.setSelection(3);
		
		Combo combo_1 = new Combo(composite_toolbar, SWT.NONE | SWT.READ_ONLY);
		combo_1.setBounds(685, 2, 70, 25);
		String items[] = new String []{"中文","English"};
		combo_1.setItems(items);
		if("zh_CN".equals(LangConfig.lang)) {
			combo_1.select(0);
		}else {
			combo_1.select(1);
		}		
		combo_1.addSelectionListener(new SelectionAdapter() {
			@Override
            public void widgetSelected(SelectionEvent e) {
				if(combo_1.getSelectionIndex() == 0) {
					//中文
					LangConfig.updateLang("zh_CN");
				}else if(combo_1.getSelectionIndex() == 1) {
					//英文
					LangConfig.updateLang("en_US");
				}   
				UiService.UiLang();
            }
		});
		
		lblNewLabel_24 = new Label(composite_toolbar, SWT.NONE);
		lblNewLabel_24.setBounds(620, 5, 60, 17);
		lblNewLabel_24.setText("语言:");
        
        //带滚动条模块        
        Composite composite1 = new Composite(composite, SWT.NONE);
        GridData gd_composite = new GridData(SWT.RIGHT, SWT.FILL, false, false, 5, 5);
		gd_composite.heightHint = 150;
		gd_composite.widthHint = 227;
		composite1.setLayoutData(gd_composite);
		composite1.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		composite1.setLayout(new FillLayout());//关键点1.外层的容器layout为FillLayout 
			
		ipListScrolledCompsite = new ScrolledComposite(composite1, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);	
		ipListScrolledCompsite.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		ipListCompsite = new Composite(ipListScrolledCompsite, SWT.NONE);
		ipListScrolledCompsite.setContent(ipListCompsite);//关键点2.设置Scrolled容器的Content为内层的容器  
	    IpSettingServcie.readConfigIp(ipListCompsite);    
	    //ipListScrolledCompsite.setSize(ipListCompsite.computeSize(SWT.DEFAULT, SWT.DEFAULT));
	    ipListCompsite.setSize(ipListCompsite.computeSize(SWT.DEFAULT, SWT.DEFAULT));//关键点3  
	    ipListCompsite.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
        Control[] cList = ipListCompsite.getChildren();
        for (Control control : cList)
        {
            Button tempButton = (Button)control;
            tempButton.addSelectionListener(new SelectionAdapter() {
            	@Override
            	public void widgetSelected(SelectionEvent e) {
            		String ips = tempButton.getText().trim().split(":")[1];
            		if(tempButton.getSelection()) {
            			//选中
            			IpSettingServcie.groupSelect(ips,resultTable,true);
            		}else {
            			//不选中
            			IpSettingServcie.groupSelect(ips,resultTable,false);
            		}
            	}
            }); 
          //双击进行编辑
            tempButton.addListener(SWT.MouseDown, new Listener() {  
            	@Override  
        	    public void handleEvent(Event event) {  
        	        if(event.button != 3) { //按键不是右键跳出. 1左键,2中键,3右键  
        	            return;  
        	        } 
        	        Control[] cList = ipListCompsite.getChildren();
        	        String index = "";
        	        for (Control control : cList) {
        	        	Button bt = (Button)control;
        	        	if(tempButton.equals(bt)) {
        	        		IpsVO ipsVO = (IpsVO)tempButton.getData();
        	        		index = ipsVO.getIndex();
        	        		break;
        	        	}        	        	
        	        }        	        
        	        String ipNote = tempButton.getText() + ":" + index;
        	        //System.out.println(ipNote);
        	        IpSettingDialog dialog2 = new IpSettingDialog(event.display.getActiveShell(),ipNote);       	        
                    dialog2.open();
                    if (dialog2.getReturnCode() == Window.OK) {
                    	tempButton.setText(dialog2.getIpValue());
                        tempButton.setSelection(true);
                    }
            	}
            });
        }	    
        
        progressBar = new ProgressBar(composite, SWT.BORDER);
        progressBar.setBackground(SWTResourceManager.getColor(SWT.COLOR_DARK_GREEN));
        progressBar.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 6, 1));
        progressBar.addPaintListener(new PaintListener() {

			@Override
			public void paintControl(PaintEvent e) {
				String progressCent = (int)(progressBar.getSelection() *1.0 /(progressBar.getMaximum()-progressBar.getMinimum()) * 100) + "%"; 
				String type = (String)progressBar.getData("type");
				String content = (String)progressBar.getData("content");
				String progressText = "";
				if(type == null) {
					progressText = progressCent;
					//int num = resultTable.getItemCount();
				}else {
					progressText = progressCent + "-" + type + " " + content;
				}
//				System.out.println(progressText);
				Point point = progressBar.getSize(); 
				Font font = new Font(shell.getDisplay(),"Courier",10,SWT.BOLD); 
				e.gc.setFont(font);
				if(progressBar.getSelection()<70) {
					e.gc.setForeground(shell.getDisplay().getSystemColor(SWT.COLOR_BLACK));
				}else {
					e.gc.setForeground(shell.getDisplay().getSystemColor(SWT.COLOR_WHITE));
				}
				 
				FontMetrics fontMetrics = e.gc.getFontMetrics(); 
				int stringWidth = fontMetrics.getAverageCharWidth() * progressText.length(); 
				int stringHeight = fontMetrics.getHeight(); 
				e.gc.drawString(progressText, (point.x-stringWidth)/2 , (point.y-stringHeight)/2, true); 
				font.dispose();
				
			} 
        	
        });
        
        Composite composite_1 = new Composite(composite, SWT.NONE);
        composite_1.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 2, 1));
        composite_1.setLayout(new GridLayout(2, false));
        
        btnCheckButton_1 = new Button(composite_1, SWT.CHECK);
        btnCheckButton_1.setSelection(true);
        btnCheckButton_1.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
               
            }
        });
        btnCheckButton_1.setText("矿池1");
        
        text = new Text(composite_1, SWT.BORDER);
        GridData gd_text = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
        gd_text.minimumWidth = 100;
        text.setLayoutData(gd_text);
        
        Composite composite_4 = new Composite(composite, SWT.NONE);
        composite_4.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
        composite_4.setLayout(new GridLayout(2, false));
        
        lblNewLabel = new Label(composite_4, SWT.NONE);
        lblNewLabel.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
        lblNewLabel.setText("子账户名");
        
        text_3 = new Text(composite_4, SWT.BORDER);
        text_3.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
        
        Composite composite_5 = new Composite(composite, SWT.NONE);
        composite_5.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
        composite_5.setLayout(new GridLayout(2, false));
        
        lblNewLabel_3 = new Label(composite_5, SWT.NONE);
        lblNewLabel_3.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
        lblNewLabel_3.setText("密码");
        
        text_6 = new Text(composite_5, SWT.BORDER);
        text_6.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
        
        lblNewLabel_6 = new Label(composite, SWT.NONE);
        lblNewLabel_6.setText("矿机名后缀");
        
        Composite composite_11 = new Composite(composite, SWT.NONE);
        GridData gd_composite_11 = new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1);
        gd_composite_11.heightHint = 38;
        composite_11.setLayoutData(gd_composite_11);
        
        button_10 = new Button(composite_11, SWT.RADIO);
        button_10.setText("IP");
        button_10.setSelection(true);
        button_10.setBounds(10, 10, 32, 17);
        
        button_14 = new Button(composite_11, SWT.RADIO);
        button_14.setText("不改变");
        button_14.setBounds(48, 10, 60, 17);
        
        button_15 = new Button(composite_11, SWT.RADIO);
        button_15.setText("清空");
        button_15.setBounds(109, 10, 45, 17);
        
        Composite composite_2 = new Composite(composite, SWT.NONE);
        composite_2.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 2, 1));
        composite_2.setLayout(new GridLayout(2, false));
        
        btnCheckButton_2 = new Button(composite_2, SWT.CHECK);
        btnCheckButton_2.setSelection(true);
        btnCheckButton_2.setText("矿池2");
        
        text_1 = new Text(composite_2, SWT.BORDER);
        text_1.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
        
        Composite composite_6 = new Composite(composite, SWT.NONE);
        composite_6.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1));
        composite_6.setLayout(new GridLayout(2, false));
        
        lblNewLabel_1 = new Label(composite_6, SWT.NONE);
        lblNewLabel_1.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
        lblNewLabel_1.setText("  子账户名");
        
        text_4 = new Text(composite_6, SWT.BORDER);
        text_4.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
        
        Composite composite_7 = new Composite(composite, SWT.NONE);
        composite_7.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1));
        composite_7.setLayout(new GridLayout(2, false));
        
        lblNewLabel_4 = new Label(composite_7, SWT.NONE);
        lblNewLabel_4.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
        lblNewLabel_4.setText("   密码");
        
        text_7 = new Text(composite_7, SWT.BORDER);
        text_7.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
        
        lblNewLabel_7 = new Label(composite, SWT.NONE);
        lblNewLabel_7.setText("  矿机名后缀");
        
        Composite composite_12 = new Composite(composite, SWT.NONE);
        GridData gd_composite_12 = new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1);
        gd_composite_12.heightHint = 40;
        composite_12.setLayoutData(gd_composite_12);
        
        button_16 = new Button(composite_12, SWT.RADIO);
        button_16.setText("IP");
        button_16.setSelection(true);
        button_16.setBounds(10, 10, 32, 17);
        
        button_17 = new Button(composite_12, SWT.RADIO);
        button_17.setText("不改变");
        button_17.setBounds(48, 10, 60, 17);
        
        button_18 = new Button(composite_12, SWT.RADIO);
        button_18.setText("清空");
        button_18.setBounds(109, 10, 45, 17);
        
        Composite composite_3 = new Composite(composite, SWT.NONE);
        composite_3.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 2, 1));
        composite_3.setLayout(new GridLayout(2, false));
        
        btnCheckButton_3 = new Button(composite_3, SWT.CHECK);
        btnCheckButton_3.setSelection(true);
        btnCheckButton_3.setText("矿池3");
        
        text_2 = new Text(composite_3, SWT.BORDER);
        text_2.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
        
        Composite composite_8 = new Composite(composite, SWT.NONE);
        composite_8.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1));
        composite_8.setLayout(new GridLayout(2, false));
        
        lblNewLabel_2 = new Label(composite_8, SWT.NONE);
        lblNewLabel_2.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
        lblNewLabel_2.setText("子账户名");
        
        text_5 = new Text(composite_8, SWT.BORDER);
        text_5.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
        
        Composite composite_9 = new Composite(composite, SWT.NONE);
        composite_9.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1));
        composite_9.setLayout(new GridLayout(2, false));
        
        lblNewLabel_5 = new Label(composite_9, SWT.NONE);
        lblNewLabel_5.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
        lblNewLabel_5.setText("密码");
        
        text_8 = new Text(composite_9, SWT.BORDER);
        text_8.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
        
        lblNewLabel_8 = new Label(composite, SWT.NONE);
        lblNewLabel_8.setText("矿机名后缀");
        
        Composite composite_13 = new Composite(composite, SWT.NONE);
        GridData gd_composite_13 = new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1);
        gd_composite_13.heightHint = 36;
        composite_13.setLayoutData(gd_composite_13);
        
        button_11 = new Button(composite_13, SWT.RADIO);
        button_11.setText("IP");
        button_11.setSelection(true);
        button_11.setBounds(10, 10, 32, 17);
        
        button_12 = new Button(composite_13, SWT.RADIO);
        button_12.setText("不改变");
        button_12.setBounds(48, 10, 60, 17);
        
        button_13 = new Button(composite_13, SWT.RADIO);
        button_13.setText("清空");
        button_13.setBounds(109, 10, 45, 17);
        //Composite panel = new Composite(tabItem, SWT.DOUBLE_BUFFERED);
        configPool();
        
        Composite composite_15 = new Composite(composite, SWT.NONE);
        GridData gd_composite_15 = new GridData(SWT.FILL, SWT.TOP, false, false, 6, 1);
        gd_composite_15.heightHint = 31;
        composite_15.setLayoutData(gd_composite_15);
        
        text_9 = new Text(composite_15, SWT.BORDER);
        text_9.setBounds(66, 3, 103, 23);
        
        lblNewLabel_12 = new Label(composite_15, SWT.NONE);
        lblNewLabel_12.setAlignment(SWT.RIGHT);
        lblNewLabel_12.setBounds(10, 6, 50, 17);
        lblNewLabel_12.setText("矿工：");
        
        lblavg = new Label(composite_15, SWT.NONE);
        lblavg.setAlignment(SWT.RIGHT);
        lblavg.setBounds(169, 6, 65, 17);
        lblavg.setText("算力：");
        
        text_10 = new Text(composite_15, SWT.BORDER);
        text_10.setBounds(236, 3, 73, 23);
        
        label = new Label(composite_15, SWT.NONE);
        label.setBounds(315, 6, 12, 17);
        label.setText("至");
        
        text_11 = new Text(composite_15, SWT.BORDER);
        text_11.setBounds(333, 3, 73, 23);
        
        Label lblTh = new Label(composite_15, SWT.NONE);
        lblTh.setBounds(412, 6, 25, 17);
        lblTh.setText("Th/s");
        
        button_2 = new Button(composite_15, SWT.NONE);
        button_2.addSelectionListener(new SelectionAdapter() {
        	@Override
        	public void widgetSelected(SelectionEvent e) {
        		//检索
        		MinerSearchVO searchVO = new MinerSearchVO();
        		searchVO.setWorker(text_9.getText().trim());
        		searchVO.setMinAvgCp(text_10.getText().trim());
        		searchVO.setMaxAvgCp(text_11.getText().trim());
        		//校验条件
        		if(!MinerSearchService.checkSelectParam(shell, searchVO,resultTable)) {
        			return;
        		}
        		lblNewLabel_13.setText("检索中...");
        		MinerSearchService.doSearch(searchVO, resultTable,lblNewLabel_13);
        		/*
        		Display.getDefault().asyncExec(new Runnable() {
        			public void run() {
        				
        			}
        		});*/
        	}
        });
        button_2.setBounds(441, 1, 53, 27);
        button_2.setText("检索");
        
        button_3 = new Button(composite_15, SWT.NONE);
        button_3.addSelectionListener(new SelectionAdapter() {
        	@Override
        	public void widgetSelected(SelectionEvent e) {
        		MinerSearchService.getScanTable(resultTable,lblNewLabel_13);
        	}
        });
        button_3.setBounds(497, 1, 61, 27);
        button_3.setText("全部");
        
        lblNewLabel_13 = new Label(composite_15, SWT.NONE);
        lblNewLabel_13.setForeground(SWTResourceManager.getColor(SWT.COLOR_DARK_GREEN));
        lblNewLabel_13.setBounds(576, 6, 328, 17);
        
        resultTable = new Table(composite, SWT.BORDER | SWT.CHECK | SWT.FULL_SELECTION | SWT.MULTI);
        resultTable.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 11, 2));
        resultTable.setHeaderVisible(true);
        resultTable.setLinesVisible(true);
        
        tblclmn_datetime = new TableColumn(resultTable, SWT.NONE);
        tblclmn_datetime.setWidth(150);
        tblclmn_datetime.setText("日期");
        
        /*tblclmn_index = new TableColumn(resultTable, SWT.NONE);
        tblclmn_index.setWidth(50);
        tblclmn_index.setText("序号");*/
        
        tblclmn_ip = new TableColumn(resultTable, SWT.NONE);
        tblclmn_ip.setWidth(120);
        tblclmn_ip.setText("IP");
        
        tblclmn_macAddress = new TableColumn(resultTable, SWT.NONE);
        tblclmn_macAddress.setWidth(120);
        tblclmn_macAddress.setText("mac地址");
                
        tblclmn_status = new TableColumn(resultTable, SWT.NONE );
        tblclmn_status.setWidth(120);
        tblclmn_status.setText("状态");
        
        tblclmn_minerType = new TableColumn(resultTable, SWT.NONE);
        tblclmn_minerType.setWidth(100);
        tblclmn_minerType.setText("矿机类型");
        
        tblclmn_binType = new TableColumn(resultTable, SWT.NONE);
        tblclmn_binType.setWidth(100);
        tblclmn_binType.setText("BIN");
        
        tblclmn_pool = new TableColumn(resultTable, SWT.NONE);
        tblclmn_pool.setWidth(150);
        tblclmn_pool.setText("矿池");
        
        tblclmn_worker = new TableColumn(resultTable, SWT.NONE);
        tblclmn_worker.setWidth(130);
        tblclmn_worker.setText("矿工");
        
        tblclmn_chipCount = new TableColumn(resultTable, SWT.NONE);
        tblclmn_chipCount.setWidth(80);
        tblclmn_chipCount.setText("芯片数量");
        
        tblclmn_realHash = new TableColumn(resultTable, SWT.NONE);
        tblclmn_realHash.setWidth(100);
        tblclmn_realHash.setText("实时算力");
        
        tblclmn_avgHash = new TableColumn(resultTable, SWT.NONE);
        tblclmn_avgHash.setWidth(100);
        tblclmn_avgHash.setText("平均算力");
        
        tblclmn_runningTime = new TableColumn(resultTable, SWT.NONE);
        tblclmn_runningTime.setWidth(100);
        tblclmn_runningTime.setText("运行时长(分钟)");
        
        tblclmn_firmwareVersion = new TableColumn(resultTable, SWT.NONE);
        tblclmn_firmwareVersion.setWidth(120);
        tblclmn_firmwareVersion.setText("固件版本");
        
        tblclmn_psuVersion = new TableColumn(resultTable, SWT.NONE);
        tblclmn_psuVersion.setWidth(120);
        tblclmn_psuVersion.setText("电源版本");
        
        tblclmn_hardwareVersion = new TableColumn(resultTable, SWT.NONE);
        tblclmn_hardwareVersion.setWidth(120);
        tblclmn_hardwareVersion.setText("硬件版本");
        
        
        /*tblclmn_softVersion = new TableColumn(resultTable, SWT.NONE);
        tblclmn_softVersion.setWidth(80);
        tblclmn_softVersion.setText("软件版本");*/
        
        tblclmn_temperature = new TableColumn(resultTable, SWT.NONE);
        tblclmn_temperature.setWidth(120);
        tblclmn_temperature.setText("温度");
        
        tblclmn_fanSpeed = new TableColumn(resultTable, SWT.NONE);
        tblclmn_fanSpeed.setWidth(140);
        tblclmn_fanSpeed.setText("风扇转速");
        
        tblclmn_fanDuty = new TableColumn(resultTable, SWT.NONE);
        tblclmn_fanDuty.setWidth(130);
        tblclmn_fanDuty.setText("风扇占空比");
        
        tblclmn_devFreq = new TableColumn(resultTable, SWT.NONE);
        tblclmn_devFreq.setWidth(100);
        tblclmn_devFreq.setText("频率");
        
        tblclmn_chipVolt = new TableColumn(resultTable, SWT.NONE);
        tblclmn_chipVolt.setWidth(140);
        tblclmn_chipVolt.setText("芯片电压");
        
        tblclmn_volt = new TableColumn(resultTable, SWT.NONE);
        tblclmn_volt.setWidth(100);
        tblclmn_volt.setText("电压");
        
        tblclmn_networkType = new TableColumn(resultTable, SWT.NONE);
        tblclmn_networkType.setWidth(100);
        tblclmn_networkType.setText("网络类型");
        
        /*tblclmn_macAddress = new TableColumn(resultTable, SWT.NONE);
        tblclmn_macAddress.setWidth(120);
        tblclmn_macAddress.setText("mac地址");*/
        
        
        addTableListener(resultTable);//超链
        addSortTableListener(resultTable);
        
        //固定IP---tab页---start
        tabItem_3 = new TabItem(tabFolder, SWT.NONE);
        tabItem_3.setText("配置固定IP");
        
        Composite composite_16 = new Composite(tabFolder, SWT.NONE);
        tabItem_3.setControl(composite_16);
        composite_16.setLayout(new GridLayout(1, false));
        
        Composite composite_17 = new Composite(composite_16, SWT.NONE);
        composite_17.setLayout(new GridLayout(3,false));
        composite_17.setLayoutData(new GridData(SWT.LEFT, SWT.FILL, true, false, 3, 2));
        
        lblNewLabel_14 = new Label(composite_17, SWT.NONE);
        lblNewLabel_14.setText("网络配置");
        
        lblNewLabel_15 = new Label(composite_17, SWT.NONE);
        lblNewLabel_15.setText("矿池配置");
        
        lblNewLabel_18 = new Label(composite_17, SWT.NONE);
        lblNewLabel_18.setText("位置配置");
        
        Composite composite_17_a = new Composite(composite_17, SWT.NONE);
        GridData gd_composite_1 = new GridData();
        gd_composite_1.heightHint = 120;
        gd_composite_1.widthHint = 270;
        composite_17_a.setLayoutData(gd_composite_1);
        composite_17_a.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
        composite_17_a.setLayout(new FillLayout());//关键点1.外层的容器layout为FillLayout        
	    
        Composite composite_17_b = new Composite(composite_17_a, SWT.BORDER);
        composite_17_b.setLayout(new GridLayout(3,false));
        composite_17_b.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
        
        lbl_14 = new Label(composite_17_b, SWT.NONE);
        lbl_14.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
        lbl_14.setText("下一个填充IP");
        new Label(composite_17_b, SWT.NONE);        
        lbl_15 = new Label(composite_17_b, SWT.NONE);
        lbl_15.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
        lbl_15.setText("子网掩码");
        
        text_13 = new Text(composite_17_b, SWT.BORDER);
        text_13.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
        new Label(composite_17_b, SWT.NONE);        
        text_12 = new Text(composite_17_b, SWT.BORDER);
        text_12.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
        
        label_1 = new Label(composite_17_b, SWT.NONE);
        label_1.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
        label_1.setText("网关");
        new Label(composite_17_b, SWT.NONE);        
        lblDns = new Label(composite_17_b, SWT.NONE);
        lblDns.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
        lblDns.setText("DNS");
        
        text_14 = new Text(composite_17_b, SWT.BORDER);
        text_14.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
        new Label(composite_17_b, SWT.NONE);        
        text_15 = new Text(composite_17_b, SWT.BORDER);
        text_15.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
      
      Composite composite_17_c_1 = new Composite(composite_17, SWT.NONE);
      GridData gd_composite_2 = new GridData();
      gd_composite_2.heightHint = 120;
      gd_composite_2.widthHint = 750;
      composite_17_c_1.setLayoutData(gd_composite_2);
      composite_17_c_1.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
      composite_17_c_1.setLayout(new FillLayout());//关键点1.外层的容器layout为FillLayout    
      Composite composite_17_c = new Composite(composite_17_c_1, SWT.BORDER);
      composite_17_c.setLayout(new GridLayout(4,false));
      composite_17_c.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
      
      label_kc1 = new Label(composite_17_c, SWT.NONE);
      label_kc1.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
      label_kc1.setText("矿池");
      label_kc2 = new Label(composite_17_c, SWT.NONE);
      label_kc2.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
      label_kc2.setText("矿工名");
      label_kc3 = new Label(composite_17_c, SWT.NONE);
      label_kc3.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
      label_kc3.setText("密码");
      label_kc4 = new Label(composite_17_c, SWT.NONE);
      label_kc4.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
      label_kc4.setText("后缀");
      
      text_16 = new Text(composite_17_c, SWT.BORDER);
      text_16.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));        
      text_17 = new Text(composite_17_c, SWT.BORDER);
      text_17.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
      text_18 = new Text(composite_17_c, SWT.BORDER);
      text_18.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

      Composite group1 = new Composite(composite_17_c, SWT.NONE);
      group1.setLayout(new FillLayout());
      group1.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
      button_kc_1 = new Button(group1, SWT.RADIO| SWT.LEFT);
      button_kc_1.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
      button_kc_1.setText("不变  ");             
      button_kc_2 = new Button(group1, SWT.RADIO| SWT.LEFT);
      button_kc_2.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
      button_kc_2.setText("清空 ");       
      button_kc_3 = new Button(group1, SWT.RADIO| SWT.LEFT);
      button_kc_3.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
      button_kc_3.setText("IP");
      button_kc_3.setSelection(true); 
      
      text_19 = new Text(composite_17_c, SWT.BORDER);
      text_19.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));        
      text_20 = new Text(composite_17_c, SWT.BORDER);
      text_20.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
      text_21 = new Text(composite_17_c, SWT.BORDER);
      text_21.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
      
      Composite group2 = new Composite(composite_17_c, SWT.NONE);
      group2.setLayout(new FillLayout());
      group2.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
      button_kc_4 = new Button(group2, SWT.RADIO);
      button_kc_4.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
      button_kc_4.setText("不变  ");              
      button_kc_5 = new Button(group2, SWT.RADIO);
      button_kc_5.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
      button_kc_5.setText("清空  ");       
      button_kc_6 = new Button(group2, SWT.RADIO);
      button_kc_6.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
      button_kc_6.setText("IP");
      button_kc_6.setSelection(true);
      
      text_22 = new Text(composite_17_c, SWT.BORDER);
      text_22.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));        
      text_23 = new Text(composite_17_c, SWT.BORDER);
      text_23.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
      text_24 = new Text(composite_17_c, SWT.BORDER);
      text_24.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
      
      Composite group3 = new Composite(composite_17_c, SWT.NONE);
      group3.setLayout(new FillLayout());
      group3.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
      button_kc_7 = new Button(group3, SWT.RADIO);
      button_kc_7.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
      button_kc_7.setText("不变  ");              
      button_kc_8 = new Button(group3, SWT.RADIO);
      button_kc_8.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
      button_kc_8.setText("清空  ");       
      button_kc_9 = new Button(group3, SWT.RADIO);
      button_kc_9.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
      button_kc_9.setText("IP");
      button_kc_9.setSelection(true); 
      
      Composite composite_17_d = new Composite(composite_17, SWT.NONE);
      GridData gd_composite_3 = new GridData();
      gd_composite_3.heightHint = 120;
      gd_composite_3.widthHint = 240;
      composite_17_d.setLayoutData(gd_composite_3);
      composite_17_d.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
      composite_17_d.setLayout(new FillLayout());//关键点1.外层的容器layout为FillLayout        
	    
      Composite composite_17_d_1 = new Composite(composite_17_d, SWT.BORDER);
      composite_17_d_1.setLayout(new GridLayout(3,false));
      composite_17_d_1.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
      
      lbl_hjh = new Label(composite_17_d_1, SWT.NONE);
      lbl_hjh.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
      lbl_hjh.setText("货架号");
      lbl_cs = new Label(composite_17_d_1, SWT.NONE);
      lbl_cs.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
      lbl_cs.setText("层数");      
      lbl_wzs = new Label(composite_17_d_1, SWT.NONE);
      lbl_wzs.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
      lbl_wzs.setText("位置数");
      
      text_25 = new Text(composite_17_d_1, SWT.BORDER);
      text_25.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));       
      text_26 = new Text(composite_17_d_1, SWT.BORDER);
      text_26.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
      text_27 = new Text(composite_17_d_1, SWT.BORDER);
      text_27.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
      
      label_nextPoint = new Label(composite_17_d_1, SWT.NONE);
      label_nextPoint.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
      label_nextPoint.setText("下个位置");
      lbl_nextLayer = new Label(composite_17_d_1, SWT.NONE);
      lbl_nextLayer.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
      lbl_nextLayer.setText("下个层数");        
      lbl_step = new Label(composite_17_d_1, SWT.NONE);
      lbl_step.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
      lbl_step.setText("步长");
      
      text_28 = new Text(composite_17_d_1, SWT.BORDER);
      text_28.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));     
      text_29 = new Text(composite_17_d_1, SWT.BORDER);
      text_29.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
      text_30 = new Text(composite_17_d_1, SWT.BORDER);
      text_30.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
        
      Composite composite_19 = new Composite(composite_16, SWT.NONE);
      composite_19.setLayout(new GridLayout(12,false));
      composite_19.setLayoutData(new GridData(SWT.LEFT, SWT.FILL, true, false, 1, 1));
        
      button_4 = new Button(composite_19, SWT.CHECK);
      button_4.setText("全选");
      button_4.setSelection(true);
      button_4.setBounds(10, 10, 51, 17);
      button_4.addSelectionListener(new SelectionAdapter() {
      	@Override
      	public void widgetSelected(SelectionEvent e) {
      		TableItem[] items = macTable.getItems();      		
      		if(button_4.getSelection()) {
      			for(TableItem item : items) {
          			item.setChecked(true);
          		}
      		}else {
      			for(TableItem item : items) {
      				item.setChecked(false);
          		}
      		}
       	}
      });
        
        button_5 = new Button(composite_19, SWT.NONE);
        button_5.setText("＋");
        button_5.addSelectionListener(new SelectionAdapter() {
        	@Override
        	public void widgetSelected(SelectionEvent e) {       		
        		if(isAction()) {
					MinerCmdService.infoDialog(shell, "请终止正在执行的操作，再试！（停止扫描，监控，IP加载等）");
					return;
				} 
        		FixedIpUiService.addIpItem(btnCheckButton, btnCheckButton_4, btnCheckButton_5, btnCheckButton_16, text_13,getPoolListByFixedIpUi(),getFixedIpInfo(), macTable);
        	}
        });
        
        btnNewButton = new Button(composite_19, SWT.NONE);
        btnNewButton.setText("－");
        btnNewButton.addSelectionListener(new SelectionAdapter() {
        	@Override
        	public void widgetSelected(SelectionEvent e) {       		
        		if(isAction()) {
					MinerCmdService.infoDialog(shell, "请终止正在执行的操作，再试！（停止扫描，监控，IP加载等）");
					return;
				}       
        		TableItem[] items = macTable.getItems();         		
        		List<Integer> removeList = new ArrayList<Integer>();
        		for(int i=0;i<items.length;i++) {
        			TableItem item = items[i];
        			if(item.getChecked()) {
        				removeList.add(i);
        			}
        		}
        		int[] removeIndex = new int[removeList.size()];
        		for(int i=0;i<removeList.size();i++) {
        			removeIndex[i] = removeList.get(i);
        		}        		        		
        		macTable.remove(removeIndex);
        	}
        });
        
        lblNewLabel_16 = new Label(composite_19, SWT.NONE);
        lblNewLabel_16.setText("请点击矿机IP Reporter键3秒以上，获取矿机IP...");
        lblNewLabel_16.setForeground(SWTResourceManager.getColor(SWT.COLOR_BLUE));
        
        Label lblNewLabel_17 = new Label(composite_19, SWT.NONE);
        lblNewLabel_17.setText("                                     ");
        new Label(composite_19, SWT.NONE);
        
        btnCheckButton = new Button(composite_19, SWT.CHECK);
        btnCheckButton.setText("自动配置");
        
        btnCheckButton_4 = new Button(composite_19, SWT.CHECK);
        btnCheckButton_4.setText("配置固定IP");
        btnCheckButton_4.setSelection(true);
        
        btnCheckButton_5 = new Button(composite_19, SWT.CHECK);
        btnCheckButton_5.setText("配置矿池");
        
        btnCheckButton_16 = new Button(composite_19, SWT.CHECK);
        btnCheckButton_16.setText("配置矿机位置");
        
        btnNewButton_1 = new Button(composite_19, SWT.NONE);
        btnNewButton_1.setText("应用配置");
        new Label(composite_19, SWT.NONE);
        btnNewButton_1.addSelectionListener(new SelectionAdapter() {
        	@Override
        	public void widgetSelected(SelectionEvent e) {     
        		MinerCmdService.infoDialog(shell, LangConfig.getKey("app.message.notOpen"));
//        		FixedIpUiService.appConfig(btnCheckButton, btnCheckButton_4, btnCheckButton_5, btnCheckButton_16, macTable);
        	}
        });
        
//        Button btnNewButton_12 = new Button(composite_19, SWT.NONE);
//        btnNewButton_12.setText("重启矿机");
//        btnNewButton_12.addSelectionListener(new SelectionAdapter() {
//        	@Override
//        	public void widgetSelected(SelectionEvent e) {       		
//        		       		
//        	}
//        });
        
        macTable = new Table(composite_16, SWT.BORDER | SWT.CHECK | SWT.FULL_SELECTION | SWT.MULTI);
        macTable.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 13, 2));
        macTable.setHeaderVisible(true);
        macTable.setLinesVisible(true);
        
        tb_miner_ip = new TableColumn(macTable, SWT.NONE);
        tb_miner_ip.setWidth(150);
        tb_miner_ip.setText("当前IP");   
        
        tb_target_ip = new TableColumn(macTable, SWT.NONE );
        tb_target_ip.setWidth(120);
        tb_target_ip.setText("目标IP");
        
        tb_miner_mac = new TableColumn(macTable, SWT.NONE);
        tb_miner_mac.setWidth(120);
        tb_miner_mac.setText("mac地址");
        
        tb_congfig_ip_status = new TableColumn(macTable, SWT.NONE);
        tb_congfig_ip_status.setWidth(100);
        tb_congfig_ip_status.setText("IP配置结果");
        
        tb_congfig_pool_status = new TableColumn(macTable, SWT.NONE);
        tb_congfig_pool_status.setWidth(100);
        tb_congfig_pool_status.setText("矿池配置结果");
        
        tb_congfig_point_status = new TableColumn(macTable, SWT.NONE);
        tb_congfig_point_status.setWidth(100);
        tb_congfig_point_status.setText("位置配置结果");
        
        tb_zwym = new TableColumn(macTable, SWT.NONE);
        tb_zwym.setWidth(120);
        tb_zwym.setText("子网掩码");
        
        tb_mrwg = new TableColumn(macTable, SWT.NONE);
        tb_mrwg.setWidth(120);
        tb_mrwg.setText("默认网关");
        
        tb_dns = new TableColumn(macTable, SWT.NONE);
        tb_dns.setWidth(120);
        tb_dns.setText("DNS服务器");
        
        tb_point = new TableColumn(macTable, SWT.NONE);
        tb_point.setWidth(100);
        tb_point.setText("矿机位置信息");
        
        tb_pool1 = new TableColumn(macTable, SWT.NONE);
        tb_pool1.setWidth(150);
        tb_pool1.setText("矿池1");
        
        tb_worker1 = new TableColumn(macTable, SWT.NONE);
        tb_worker1.setWidth(80);
        tb_worker1.setText("矿工1");
        
        tb_pwd1 = new TableColumn(macTable, SWT.NONE);
        tb_pwd1.setWidth(80);
        tb_pwd1.setText("密码1");
        
        tb_pool2 = new TableColumn(macTable, SWT.NONE);
        tb_pool2.setWidth(150);
        tb_pool2.setText("矿池2");
        
        tb_worker2 = new TableColumn(macTable, SWT.NONE);
        tb_worker2.setWidth(80);
        tb_worker2.setText("矿工2");
        
        tb_pwd2 = new TableColumn(macTable, SWT.NONE);
        tb_pwd2.setWidth(80);
        tb_pwd2.setText("密码2");
        
        tb_pool3 = new TableColumn(macTable, SWT.NONE);
        tb_pool3.setWidth(150);
        tb_pool3.setText("矿池3");
        
        tb_worker3 = new TableColumn(macTable, SWT.NONE);
        tb_worker3.setWidth(80);
        tb_worker3.setText("矿工3");
        
        tb_pwd3 = new TableColumn(macTable, SWT.NONE);
        tb_pwd3.setWidth(80);
        tb_pwd3.setText("密码3");
      //固定IP---tab页---end
        
        //固件升级---tab页---start
        tabItem_4 = new TabItem(tabFolder, SWT.NONE);
        tabItem_4.setText("固件升级"); 
        
        Composite composite_18 = new Composite(tabFolder, SWT.NONE);
        tabItem_4.setControl(composite_18);
        composite_18.setLayout(new GridLayout(1, false));
        
        Composite composite_20 = new Composite(composite_18, SWT.NONE);
        GridData gd_composite_4 = new GridData(SWT.LEFT, SWT.FILL, true, false, 11, 1);
        gd_composite_4.heightHint = 31;
//      gd_composite_4.widthHint = 900;
        composite_20.setLayout(new GridLayout(12,false));
        composite_20.setLayoutData(gd_composite_4);
        
        lblNewLabel_19 = new Label(composite_20, SWT.NONE);
        lblNewLabel_19.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
        lblNewLabel_19.setText("IP范围:");
        
        combo = new Combo(composite_20, SWT.NONE | SWT.READ_ONLY);
        combo.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
        
        btnNewButton_2 = new Button(composite_20, SWT.NONE);
        btnNewButton_2.setText("矿机发现");
        btnNewButton_2.addSelectionListener(new SelectionAdapter() {
        	@Override
        	public void widgetSelected(SelectionEvent e) {       		
        		if(isAction()) {
					MinerCmdService.infoDialog(shell, "请终止正在执行的操作，再试！（停止扫描，监控，IP加载等）");
					return;
				}        		
        		if("".equals(combo.getText().trim())) {
        			MinerCmdService.infoDialog(shell, "请选择IP范围!");
        			return;
        		}
        		isLoadIp = true;
        		ButtonStatusService.disableButton();       		
        		UpgradeUiService.loadIp(table_gj,combo.getText());
        		//isLoadIp = false;
        		
        	}
        });
        
        /*lblNewLabel_20 = new Label(composite_20, SWT.NONE);
        lblNewLabel_20.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
        lblNewLabel_20.setText("用户名:");
        
        text_31 = new Text(composite_20, SWT.BORDER);
        text_31.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
        
        lblNewLabel_21 = new Label(composite_20, SWT.NONE);
        lblNewLabel_21.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
        lblNewLabel_21.setText("密码:");
        
        text_32 = new Text(composite_20, SWT.BORDER);
        text_32.setText("");
        text_32.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));*/
        
        btnNewButton_3 = new Button(composite_20, SWT.NONE);
        btnNewButton_3.setText("选择固件");
        btnNewButton_3.addSelectionListener(new SelectionAdapter() {
        	@Override
        	public void widgetSelected(SelectionEvent e) {
        		FileService.selectFile(shell,lblNewLabel_22);
        	}
        });
        
        button_6 = new Button(composite_20, SWT.NONE);
        button_6.setText("开始升级");
        button_6.addSelectionListener(new SelectionAdapter() {
        	@Override
        	public void widgetSelected(SelectionEvent e) {
        		//MinerCmdService.infoDialog(shell, LangConfig.getKey("app.message.notOpen"));
        		if(isAction()) {
					MinerCmdService.infoDialog(shell, "请终止正在执行的操作，再试！（停止扫描，监控，IP加载等）");
					return;
				}       		        		
        		if(filePath == null || "".equals(filePath)) {
        			MinerCmdService.infoDialog(shell, "请选择固件文件后，再试！");
					return;
        		}
        		File file = new File(filePath);
        		Boolean fileSize = FileService.checkFileSize(file.length(),100,"M");
        		if(!fileSize) {
        			MinerCmdService.infoDialog(shell, "固件文件大小不能超过100M！");
					return;
        		}
        		if(!UpgradeUiService.isAllowUpgrade(filePath)) {
        			MinerCmdService.infoDialog(shell, "固件文件不合法，请重新选择固件！");
					return;
        		}
//        		String user = lblNewLabel_20.getText().trim();
//        		String pass = lblNewLabel_21.getText().trim();
//        		if("".equals(user)) {
//        			MinerCmdService.infoDialog(shell, "用户名不能为空！");
//					return;
//        		}
//        		if("".equals(pass)) {
//        			MinerCmdService.infoDialog(shell, "密码不能为空！");
//					return;
//        		}       		
        	    //TableItem[] t = table_gj.getSelection();
        		TableItem[]  t = table_gj.getItems();
        		System.out.println("正确数："+table_gj.getItemCount());
        		if(t.length == 0) {
        			MinerCmdService.infoDialog(shell, "请选中需要升级的矿机！");
					return;
        		}
         		//ButtonStatusService.disableButton();
        		//table_gj.setEnabled(false);
        		UpgradeUiService.upgradeFile(table_gj);
        		
        	}
        });
        
        lblNewLabel_22 = new Label(composite_20, SWT.NONE);
        lblNewLabel_22.setText("                                                               ");
        new Label(composite_20, SWT.NONE);
        new Label(composite_20, SWT.NONE);
        
        Composite composite_21 = new Composite(composite_18, SWT.NONE);
        GridData gd_composite_5 = new GridData(SWT.LEFT, SWT.FILL, true, false, 12, 1);
        gd_composite_5.heightHint = 31;
        //gd_composite_5.widthHint = 800;
        composite_21.setLayout(new GridLayout(2,false));
        composite_21.setLayoutData(gd_composite_5);
        
        btnCheckButton_6 = new Button(composite_21, SWT.CHECK);
        btnCheckButton_6.setText("全选");
        btnCheckButton_6.setSelection(true);
        
        lblNewLabel_23 = new Label(composite_21, SWT.NONE);
        lblNewLabel_23.setText("                                                                          ");
        
        btnCheckButton_6.addSelectionListener(new SelectionAdapter() {
        	
        	@Override
        	public void widgetSelected(SelectionEvent e) {
        		TableItem[] t = table_gj.getItems();
        		for(int i=0;i<t.length;i++) {
        			if(btnCheckButton_6.getSelection()) {
        				//全选
        				t[i].setChecked(true);
        			}else {
        				//取消全选
        				t[i].setChecked(false);
        			}
        			
        		}
        	}
        });
        
        table_gj = new Table(composite_18, SWT.BORDER | SWT.CHECK | SWT.FULL_SELECTION | SWT.MULTI);
        table_gj.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
        table_gj.setHeaderVisible(true);
        table_gj.setLinesVisible(true);
        
        table_ip = new TableColumn(table_gj, SWT.NONE);
        table_ip.setWidth(150);
        table_ip.setText("IP地址");   
        
        table_status = new TableColumn(table_gj, SWT.NONE);
        table_status.setWidth(150);
        table_status.setText("状态");
        
        table_mac = new TableColumn(table_gj, SWT.NONE );
        table_mac.setWidth(100);
        table_mac.setText("MAC地址");
        
        table_type = new TableColumn(table_gj, SWT.NONE);
        table_type.setWidth(120);
        table_type.setText("矿机型号");
        
        table_version = new TableColumn(table_gj, SWT.NONE);
        table_version.setWidth(100);
        table_version.setText("固件版本");
        
        table_result = new TableColumn(table_gj, SWT.NONE);
        table_result.setWidth(100);
        table_result.setText("操作结果");
        
        table_note = new TableColumn(table_gj, SWT.NONE);
        table_note.setWidth(500);
        table_note.setText("设备描述");
        
       
        //固件升级---tab页---end
        
        
        //行情---tab页---- start
//        TabItem tabItem_2 = new TabItem(tabFolder, SWT.NONE);
//        tabItem_2.setText("行情");
//        
//        Composite composite_10 = new Composite(tabFolder, SWT.NONE);
//        tabItem_2.setControl(composite_10);
//        composite_10.setLayout(new FormLayout());
//     // Create the composite to hold the buttons and text field  
//		  
//        Composite controls = new Composite(composite_10, SWT.NONE);  
//        controls.setBackground(SWTResourceManager.getColor(SWT.COLOR_INFO_BACKGROUND));
//        controls.setLayout(new FormLayout());
//        FormData data = new FormData();  
//        data.top = new FormAttachment(0, 0);  
//        data.left = new FormAttachment(0, 0);  
//        data.right = new FormAttachment(100, 0);  
//        controls.setLayoutData(data);  
//  
//        // Create the status bar  
//  
//        Label status = new Label(composite_10, SWT.NONE);  
//        data = new FormData();  
//        data.left = new FormAttachment(0, 0);  
//        data.right = new FormAttachment(100, 0);  
//        data.bottom = new FormAttachment(100, 0);  
//        status.setLayoutData(data);  
//  
//        // Create the web browser  
//  
//        Browser browser = new Browser(composite_10, SWT.BORDER); 
//        data_1 = new FormData();  
//        data_1.top = new FormAttachment(0, 32);
//        data_1.bottom = new FormAttachment(status);
//        data_1.left = new FormAttachment(0, 0);  
//        data_1.right = new FormAttachment(100, 0);  
//        browser.setLayoutData(data_1);
//        
//        Link link = new Link(controls, SWT.NONE);
//        link.setBackground(SWTResourceManager.getColor(SWT.COLOR_INFO_BACKGROUND));
//        FormData fd_link = new FormData();
//        fd_link.top = new FormAttachment(0, 8);
//        fd_link.left = new FormAttachment(0, 5);
//        link.setLayoutData(fd_link);
//        link.setText("<a>火币行情</a>");
//        link.addSelectionListener(new SelectionAdapter() {
//            @Override
//            public void widgetSelected(SelectionEvent e) {
//            	String url = "https://www.huobi.vn/zh-cn/markets/";
//            	browser.setUrl(url);
//				browser.update();
//            }
//		});       
//        
//        Composite composite_14 = new Composite(controls, SWT.NONE);
//        composite_14.setBackground(SWTResourceManager.getColor(SWT.COLOR_INFO_BACKGROUND));
//        FormData fd_composite_14 = new FormData();
//        fd_composite_14.bottom = new FormAttachment(0, 32);
//        fd_composite_14.right = new FormAttachment(100, -6);
//        fd_composite_14.top = new FormAttachment(0);
//        composite_14.setLayoutData(fd_composite_14);
//        
//              // Create the refresh button  
//        
//              Button button_refresh = new Button(composite_14, SWT.PUSH);  
//              button_refresh.setBounds(220, 10, 36, 17);
//              button_refresh.setText("刷新");  
//              
//                    // Create the forward button  
//              
//                    Button button_forward = new Button(composite_14, SWT.PUSH);  
//                    button_forward.setBounds(178, 10, 36, 17);
//                    button_forward.setText("前进");  
//                    
//// Create the back button  
//                    
//                    Button button_back = new Button(composite_14, SWT.PUSH);  
//                    button_back.setBounds(136, 10, 36, 17);
//                    button_back.setText("后退");  
//                    button_back.addSelectionListener(new SelectionAdapter() {  
//                        public void widgetSelected(SelectionEvent event) {  
//                            browser.back();  
//                        }  
//                    });
//                    button_forward.addSelectionListener(new SelectionAdapter() {  
//                        public void widgetSelected(SelectionEvent event) {  
//                            browser.forward();  
//                        }  
//                    });
//              button_refresh.addSelectionListener(new SelectionAdapter() {  
//                  public void widgetSelected(SelectionEvent event) {  
//                      browser.refresh();  
//                  }  
//              });  
//          
//        browser.addOpenWindowListener(new OpenWindowListener() {  
//			public void open(WindowEvent e) {  
//				final Shell shell2 = new Shell(shell);
//				final Browser browser2=new Browser(shell2,SWT.EMBEDDED);
//				e.browser = browser2; 
//				e.display.asyncExec(new Runnable() {  
//					public void run() {
//						String url = browser2.getUrl();  
//		                System.out.println(url); 
//						browser.setUrl(url);
//						browser.update();
//						shell2.close();
//					}
//				});
//            }  
//        });  
//        //browser.addProgressListener(new AdvancedProgressListener(lblNewLabel_11)); 
//        browser.addStatusTextListener(new AdvancedStatusTextListener(status)); 
//        browser.setUrl("https://www.huobi.vn/zh-cn/markets/");
//        browser.update();
        UiService.UiLang();
        IpSettingServcie.loadConfigIp();
        configIpPool();
       
    }
    
    //以下为本类私有方法
    private Boolean isAction() {
    	if(isScan || isMonitor || isLoadIp || isUpgrade) {
    		return true;
    	}
    	return false;
    }
    
    private List<PoolVO> getPoolList(){
    	List<PoolVO> poolList = new ArrayList<PoolVO>();
    	if(btnCheckButton_1.getSelection()) {
    		PoolVO poolVO = new PoolVO();
    		poolVO.setIscheckd("1");
    		poolVO.setName("矿池1");
    		String url =  text.getText().trim();
    		url = url.replaceAll(" ", "");
    		poolVO.setPoolUrl(url);
    		poolVO.setPoolUser(text_3.getText().trim());
    		poolVO.setPoolPwd(text_6.getText().trim());
    		poolVO.setMinerLast(getRadioValue(button_10,button_14,button_15));
    		poolList.add(poolVO);
    	}
    	if(btnCheckButton_2.getSelection()) {
    		PoolVO poolVO = new PoolVO();
    		poolVO.setIscheckd("1");
    		poolVO.setName("矿池2");
    		String url_1 =  text_1.getText().trim();
    		url_1 = url_1.replaceAll(" ", "");
    		poolVO.setPoolUrl(url_1);
    		poolVO.setPoolUser(text_4.getText().trim());
    		poolVO.setPoolPwd(text_7.getText().trim());
    		poolVO.setMinerLast(getRadioValue(button_16,button_17,button_18));
    		poolList.add(poolVO);
    	}
    	if(btnCheckButton_3.getSelection()) {
    		PoolVO poolVO = new PoolVO();
    		poolVO.setIscheckd("1");
    		poolVO.setName("矿池3");
    		String url_2 =  text_2.getText().trim();
    		url_2 = url_2.replaceAll(" ", "");
    		poolVO.setPoolUrl(url_2);
    		poolVO.setPoolUser(text_5.getText().trim());
    		poolVO.setPoolPwd(text_8.getText().trim());
    		poolVO.setMinerLast(getRadioValue(button_11,button_12,button_13));
    		poolList.add(poolVO);
    	}
    	
    	return poolList;
    }
    
    //矿机后缀名
    private Integer getRadioValue(Button b1,Button b2,Button b3) {
    	if(b1.getSelection()) {
    		return 0;
    	}else if(b2.getSelection()) {
    		return 1;
    	}else {
    		return 2;
    	}
    }
    
    private FixedIpVO getFixedIpInfo() {
    	FixedIpVO vo = new FixedIpVO();
    	vo.setIp(text_13.getText().trim());
    	vo.setNetMast(text_12.getText().trim());
    	vo.setGateway(text_14.getText().trim());
    	vo.setDns(text_15.getText().trim());
    	return vo;
    }
    
    private List<PoolVO> getPoolListByFixedIpUi(){
    	List<PoolVO> poolList = new ArrayList<PoolVO>();
    	PoolVO vo1 = new PoolVO();
    	vo1.setPoolUrl(text_16.getText().trim());
    	vo1.setPoolUser(text_17.getText().trim());
    	vo1.setPoolPwd(text_18.getText().trim());
    	vo1.setMinerLast(getRadioValue(button_kc_3,button_kc_1,button_kc_2));
    	PoolVO vo2 = new PoolVO();
    	vo2.setPoolUrl(text_19.getText().trim());
    	vo2.setPoolUser(text_20.getText().trim());
    	vo2.setPoolPwd(text_21.getText().trim());
    	vo2.setMinerLast(getRadioValue(button_kc_6,button_kc_4,button_kc_5));
    	PoolVO vo3 = new PoolVO();
    	vo3.setPoolUrl(text_22.getText().trim());
    	vo3.setPoolUser(text_23.getText().trim());
    	vo3.setPoolPwd(text_24.getText().trim());
    	vo3.setMinerLast(getRadioValue(button_kc_9,button_kc_7,button_kc_8));
    	poolList.add(vo1);
    	poolList.add(vo2);
    	poolList.add(vo3);
    	return poolList;
    }
    
    private void configPool() {
    	//矿池1
        text.setText(PoolConfig.poolUrl1);
        text_3.setText(PoolConfig.poolUser1);
        text_6.setText(PoolConfig.poolPwd1);
        //矿池2
        text_1.setText(PoolConfig.poolUrl2);
        text_4.setText(PoolConfig.poolUser2);
        text_7.setText(PoolConfig.poolPwd2);
        //矿池3
        text_2.setText(PoolConfig.poolUrl3);
        text_5.setText(PoolConfig.poolUser3);
        text_8.setText(PoolConfig.poolPwd3);
    }
    
    //固定IP页面
    private void configIpPool() {
    	//固定IP配置
    	text_12.setText("255.255.255.0");
    	text_13.setText("192.168.1.2");
    	text_14.setText("192.168.1.1");
    	text_15.setText("114.114.114.114");
    	//矿池1
        text_16.setText(PoolConfig.poolUrl1);
        text_17.setText(PoolConfig.poolUser1);
        text_18.setText(PoolConfig.poolPwd1);
        //矿池2
        text_19.setText(PoolConfig.poolUrl2);
        text_20.setText(PoolConfig.poolUser2);
        text_21.setText(PoolConfig.poolPwd2);
        //矿池3
        text_22.setText(PoolConfig.poolUrl3);
        text_23.setText(PoolConfig.poolUser3);
        text_24.setText(PoolConfig.poolPwd3);
    }
    
    private void addTableListener(Table table) {
    	//为这个table加一个事件,如果点击某一个element,则在控制台显示该element的坐标
    	
    	table.addListener(SWT.MouseDown, new Listener() {	
			public void handleEvent(Event event) {
				//右键
				if (event.button == 3) {
		            System.out.println("右键复制内容为：");
		            Point pt = new Point(event.x, event.y);
					int index = table.getTopIndex();
					while (index < table.getItemCount()) {
						final TableItem item = table.getItem(index);
						for (int i = 0; i < table.getColumnCount(); i++) {
							Rectangle rect = item.getBounds(i);
							if (rect.contains(pt)) {
								String copyContent = item.getText(i);
								System.out.println(copyContent);
								ClipBoardUtil.setSysClipboardText(copyContent);
								//System.out.println("location : " + index + " : " + i);//坐标位置
								break;
							}
						}
						 
						index++;
					}
		            /*
		            JPopupMenu jpm = new JPopupMenu();  
		            jpm.add(event.x+"fff");  
                    jpm.show(table, event.x, event.y);  */
                    /*
		            PopupMenu popupMenu = new PopupMenu();
		            MenuItem copyItem = new MenuItem("Copy");
		            MenuItem pasteItem = new MenuItem("Paste");
		            popupMenu.add(copyItem);
		            popupMenu.add(pasteItem);
		            final Panel panel = new Panel();
		            panel.setPreferredSize(new Dimension(300, 160));
		            panel.add(popupMenu);		            
		            popupMenu.show(panel, event.x, event.y);*/
		            
		        }
				
			}
		});		
    	
    	table.addListener(SWT.MouseDoubleClick, new Listener() {  
    	    @Override  
    	    public void handleEvent(Event event) {  
    	        if(event.button != 1) { //按键不是左键跳出. 1左键,2中键,3右键  
    	            return;  
    	        }  
    	        //鼠标双击事件处理  
    	        Point pt = new Point(event.x, event.y);
				int index = table.getTopIndex();
				while (index < table.getItemCount()) {
					final TableItem item = table.getItem(index);
					for (int i = 0; i < table.getColumnCount(); i++) {
						Rectangle rect = item.getBounds(i);
						if (rect.contains(pt)) {
							//System.out.println(item.getText(0));
							String ip = item.getText(Constants.ipIndex);
							String minerType = item.getText(Constants.minertypeIndex);
							MinerWebDialog web = new MinerWebDialog(event.display.getActiveShell(),SWT.NONE,ip,minerType);
							web.open();
							break;
							//System.out.println("location : " + index + " : " + i);
						}
					}
					
					index++;
				}
    	    }  
    	});  
    }
    
    //table升序
    private void addSortTableListener(Table table) {
    	for(int i=0;i<table.getColumnCount();i++) {
    		if(i == Constants.ipIndex
    				|| i == Constants.minertypeIndex
    				|| i == Constants.workerIndex
    				|| i == Constants.avgcpIndex
    				|| i == Constants.cpIndex
    				|| i == Constants.versionIndex
    				|| i == Constants.statusIndex
    				|| i == Constants.uptimeIndex) {
    			final int index = i;
        		table.getColumn(index).addSelectionListener(
            			new SelectionAdapter()
            		    {
        	    		     public void widgetSelected(SelectionEvent e)
        	    		     {
    	    	    		      //调用排序文件，处理排序
    	    	    		      //new TableColumnSorter().addStringSorter(table, table.getColumn(index));
        	    		    	  if(index == 1) {
        	    		    		  ipSort = !ipSort;
        	    		    		  TableColumnSorter.addStringSorter(table, table.getColumn(index),ipSort);
        	    		    	  }else if(index == 3) {
        	    		    		  statusSort = !statusSort;
        	    		    		  TableColumnSorter.addStringSorter(table, table.getColumn(index),statusSort);
        	    		    	  }else if(index == 10) {
        	    		    		  uptimeSort = !uptimeSort;
        	    		    		  TableColumnSorter.addNumberSorter(table, table.getColumn(index),uptimeSort);
        	    		    	  }else if(index == 9) {
        	    		    		  avgCpSort = !avgCpSort;
        	    		    		  TableColumnSorter.addNumberSorter(table, table.getColumn(index),avgCpSort);
        	    		    	  }else if(index == 8) {
        	    		    		  cpSort = !cpSort;
        	    		    		  TableColumnSorter.addNumberSorter(table, table.getColumn(index),cpSort);
        	    		    	  }else if(index == 11) {
        	    		    		  versionSort = !versionSort;
        	    		    		  TableColumnSorter.addStringSorter(table, table.getColumn(index),versionSort);
        	    		    	  }
        	    		     }
            		    }
            	);
    		}
    		
    	}
    	
    }
}

/** 
 * This class implements a ProgressListener for TestBrowser 
 */  
 class AdvancedProgressListener implements ProgressListener {  
     // The label on which to report progress  

     private Label progress;  

     /** 
     * Constructs an AdvancedProgressListener 
     * 
     * @param label 
     *            the label on which to report progress 
     */  
     public AdvancedProgressListener(Label label) {  
         // Store the label on which to report updates  

         progress = label;  
     }  

     /** 
     * Called when progress is made 
     * 
     * @param event 
     *            the event 
     */  
     public void changed(ProgressEvent event) {  
         // Avoid divide-by-zero  

         if (event.total != 0) {  
             // Calculate a percentage and display it  

             //int percent = (int) (event.current / event.total);
             progress.setText("Loading..."); 
             //progress.setText("页面加载进度："+percent + "%");  
         } else {  
             // Since we can't calculate a percent, show confusion :-)  

             progress.setText("Complete");  
         }  
     }  

     /** 
     * Called when load is complete 
     * 
     * @param event 
     *            the event 
     */  
     public void completed(ProgressEvent event) {  
         // Reset to the "at rest" message  

         progress.setText("Ready");  
     }  
 }
 
 /** 
  * This class implements a StatusTextListener for TestBrowser 
  */  
  class AdvancedStatusTextListener implements StatusTextListener {  
      // The label on which to report status  

      private Label status;  

      /** 
      * Constructs an AdvancedStatusTextListener 
      * 
      * @param label 
      *            the label on which to report status 
      */  
      public AdvancedStatusTextListener(Label label) {  
          // Store the label on which to report status  

          status = label;  
      }  

      /** 
      * Called when the status changes 
      * 
      * @param event 
      *            the event 
      */  
      public void changed(StatusTextEvent event) {  
          // Report the status  

          status.setText(event.text);  
      }  
  }  
