package app;

import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.browser.TitleEvent;
import org.eclipse.swt.browser.TitleListener;
import org.eclipse.swt.events.ShellAdapter;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import utils.PropertiesUtils;
import vo.MinerParamVO;
import vo.SetupVO;

public class MinerWebDialog extends Dialog {

	protected Object result;
	protected Shell shell;
	private String ip;
	private String minerType;

	/**
	 * Create the dialog.
	 * @param parent
	 * @param style
	 */
	public MinerWebDialog(Shell parent, int style) {
		super(parent, style);
		setText("Miner Console");
	}
	
	public MinerWebDialog(Shell parent, int style,String ip,String minerType) {
		super(parent, style);
		setText("Miner Console");
		this.ip = ip;
		this.minerType = minerType;
	}
	
	/**
	 * Open the dialog.
	 * @return the result
	 */
	public Object open() {
		createContents();
		shell.open();
		shell.layout();
		Display display = getParent().getDisplay();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		return result;
	}

	/**
	 * Create contents of the dialog.
	 */
	private void createContents() {
		shell = new Shell(getParent(), SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL | SWT.MAX);
		shell.addShellListener(new ShellAdapter() {
            @Override
            public void shellClosed(ShellEvent e) {
            	shell.dispose();
            }
        });
        shell.setMinimumSize(new Point(800, 600));
        shell.setSize(1200, 800);
        //shell.setMaximized(true);
        shell.setText(getText());
        FillLayout layout = new FillLayout();  
        shell.setLayout(layout);  
        Browser browser = new Browser(shell, SWT.NONE);   
        //Browser browser = new Browser(shell, SWT.MOZILLA);   
        //Browser browser = new Browser(shell, SWT.FILL);   
        browser.addTitleListener(new TitleListener() {   
            public void changed(TitleEvent event) {  
                shell.setText(event.title+"("+ip+")");  
            }  
        }); 
    	String url = ip+"/index.php/app/dashboard";
		browser.setUrl(url);
        //根据minerType获取用户名密码（获取默认配置）
//        if("S9".equals(minerType)) {
//        	String url = ip+"/index.html";
//    		browser.setUrl(url); 
//        	return;
//        }else if(minerType.indexOf("DWM_BTC") != -1) {
//        	String url = "https://"+ip+"/user/login?username=admin&word=admin&yuyan=0&login=Login&get_password=";
//    		browser.setUrl(url); 
//        	return;
//        }else if(minerType.indexOf("Whats") != -1) {
//        	String url = "http://"+ip+"/cgi-bin/luci?luci_username=root&luci_password=root";
//    		browser.setUrl(url); 
//        	return;
//        }
//        SetupVO setupVO = PropertiesUtils.readConfig();
//        List<MinerParamVO> minerList = setupVO.getMinerList();
//        for(int i=0;i<minerList.size();i++) {
//        	MinerParamVO vo = minerList.get(i);
//        	String type = vo.getType();
//        	minerType = minerType.trim();
//        	if(minerType.equals(type)) {
//        		String username = vo.getUsername();
//        		String password = vo.getPassword();
//        		String url = ip+"/login?username="+username+"&password="+password;
//        		//System.out.println("url:"+url);//api/auth
//        		browser.setUrl(url); 
//        		break;
//        	}else {
//        		continue;
//        	}
//        }
        

	}

}
