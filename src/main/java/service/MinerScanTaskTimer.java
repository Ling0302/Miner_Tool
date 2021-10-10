/**
 * 
 */
package service;

import java.util.ArrayList;
import java.util.List;
import java.util.TimerTask;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.ProgressBar;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.wb.swt.SWTResourceManager;

import com.alibaba.fastjson.JSONObject;

import utils.Constants;
import utils.IPRangeUtils;
import utils.PropertiesUtils;
import utils.StringUtils;
import vo.MinerParamVO;

/**
 * @author john
 *
 */
public class MinerScanTaskTimer extends TimerTask
{
    private ProgressBar progressBar;
    
    private Table resultTable;
    
    private boolean display_success;
    
    private List<String> ipRange;
    
    private boolean stop = false;
    
    public MinerScanTaskTimer(ProgressBar progressBar, Table resultTable, boolean display_success, List<String> ipRange)
    {
        this.resultTable = resultTable;
        this.progressBar = progressBar;
        this.display_success = display_success;
        this.ipRange = ipRange;
    }

    /* (non-Javadoc)
     * @see java.util.TimerTask#run()
     */
    @Override
    public void run()
    {
        scanMiners(progressBar,resultTable, display_success,ipRange);
    }

    /**
     * @param args
     */
    public static void main(String[] args)
    {
        // TODO Auto-generated method stub

    }
    
    
    private void scanMiners(ProgressBar progressBar,Table resultTable, boolean display_success, List<String> ipRange)
    {
        progressBar.setSelection(0);
        
        ExecutorService service = Executors.newFixedThreadPool(20);
        List<String> ips = new ArrayList<>();
        for (String range : ipRange)
        {
            ips.addAll(IPRangeUtils.getIPRangeList(range));
        }
        
        
        if (ips.size() > 0)
        {
            CountDownLatch countDownLatch = new CountDownLatch(ips.size());
            
            List<Future<JSONObject>> statusList = new ArrayList<>();
            
            for (String ip : ips)
            {
                MinerStatusCheck mc = new MinerStatusCheck(ip, countDownLatch);
                statusList.add(service.submit(mc));
            }
            
            while (progressBar.getSelection() < 100)
            {
                try
                {
                    int progress = ips.size() == 0 ? 0 : (ips.size() - (int)countDownLatch.getCount()) * 100/ips.size();
                    progressBar.setSelection(progress);
                    Thread.sleep(100);
                }
                catch (InterruptedException e1)
                {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
            }
            
            // 主线程一直被阻塞,直到count的计数器被置为0
            try 
            {
                countDownLatch.await();
                resultTable.removeAll();
                for (Future<JSONObject> fs : statusList)
                {
                    JSONObject jol = fs.get();
                    //System.out.println(jol.toJSONString());
                    if ((jol.getInteger("status") == 1 && display_success) 
                            || !display_success)
                    {
                        TableItem tableItem = new TableItem(resultTable, SWT.NONE);
                        tableItem.setChecked(true);
                        tableItem.setText(new String[] {jol.getString("ipSeq"),                               
                                jol.getString("minerType"), 
                                jol.getString("url"), 
                                jol.getString("worker"), 
                                jol.getString("sn"), 
                                jol.getFloat("avgHashRate") == null ? "" : StringUtils.round(jol.getFloat("avgHashRate")/1024,2), 
                                jol.getFloat("hashRate") == null ? "" : StringUtils.round(jol.getFloat("hashRate")/1024,2), 
                                StringUtils.formatNull(jol.getString("temperature1") + "/" + jol.getString("temperature2")) ,
                                StringUtils.formatNull(jol.getString("minSpeed") + "/" + jol.getString("maxSpeed")), 
                                StringUtils.formatNull(jol.getString("psuMaxV") + "/" + jol.getString("psuMaxI")),
                                StringUtils.formatNull(jol.getString("psuPower1") + "/" + jol.getString("psuPower2")),
                                StringUtils.formatNull(jol.getString("psuMaxInTemp") + "/" + jol.getString("psuMaxOutTemp")),
                                jol.getString("version"),
                                jol.getString("rejectedRate")==null?"":(jol.getString("rejectedRate")+"%"), 
                                StringUtils.round(jol.getFloat("upTime")/60,2), 
                                "ON".equals(jol.getString("hashboardStatus")) ? "休眠" : Constants.STATUS[jol.getInteger("status") - 1]
                                });
                        if(!"ON".equals(jol.getString("hashboardStatus"))) {
                        	tableItem.setBackground(15,SWTResourceManager.getColor(Constants.STATUS_COLOR[jol.getInteger("status") - 1]));
                        }                
                        tableItem.setText(jol.getString("ipSeq"));
                        
                        //规则配置
                        MinerParamVO rule = PropertiesUtils.getRule(jol.getString("minerType"));
                        if(rule != null) {
                        	MinerRuleService.ruleCheck(jol,rule,tableItem);                        	
                        }
                        //tableItem.setForeground(2,SWTResourceManager.getColor(SWT.COLOR_RED));


                    }
                    
                } 
                
            }
            catch(Exception e2)
            {   
                
            }
            finally{   
                //启动一次顺序关闭，执行以前提交的任务，但不接受新任务  
                service.shutdown();   
            } 
        }
    }

    public void stop()
    {
        this.stop = true;
    }

}
