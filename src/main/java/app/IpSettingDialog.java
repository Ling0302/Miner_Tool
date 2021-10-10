package app;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import common.LangConfig;
import service.IpSettingServcie;
import utils.IPRangeUtils;

public class IpSettingDialog extends Dialog
{
    private Text text;
    private Text text_1;
    private Text text_2;
    private Text text_3;
    private Text text_4;
    
    private String ipValue;
    private String ipNote;

    /**
     * Create the dialog.
     * @param parentShell
     */
    public IpSettingDialog(Shell parentShell)
    {
        super(parentShell);
        setShellStyle(SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
    }
    
    public IpSettingDialog(Shell parentShell,String ipNote)
    {
        super(parentShell);
        setShellStyle(SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
        this.ipNote = ipNote;
    }

    /**
     * Create contents of the dialog.
     * @param parent
     */
    @Override
    protected Control createDialogArea(Composite parent)
    {
        Composite container = (Composite) super.createDialogArea(parent);
        container.setLayout(new FormLayout());
        
        Label lblIp = new Label(container, SWT.NONE);
        FormData fd_lblIp = new FormData();
        fd_lblIp.top = new FormAttachment(0, 23);
        fd_lblIp.left = new FormAttachment(0, 10);
        lblIp.setLayoutData(fd_lblIp);
        lblIp.setText(LangConfig.getKey("app.setting.ipSegment"));
        
        text = new Text(container, SWT.BORDER);
        FormData fd_text = new FormData();
        fd_text.top = new FormAttachment(lblIp, 0, SWT.TOP);
        fd_text.left = new FormAttachment(lblIp, 27);
        fd_text.width = 60;
        text.setLayoutData(fd_text);
        
        Label label = new Label(container, SWT.NONE);
        FormData fd_label = new FormData();
        fd_label.top = new FormAttachment(lblIp, 0, SWT.TOP);
        fd_label.left = new FormAttachment(text, 6);
        label.setLayoutData(fd_label);
        label.setText(".");
        
        text_1 = new Text(container, SWT.BORDER);
        FormData fd_text_1 = new FormData();
        fd_text_1.top = new FormAttachment(lblIp, 0, SWT.TOP);
        fd_text_1.left = new FormAttachment(label, 6);
        fd_text_1.width = 60;
        text_1.setLayoutData(fd_text_1);
        
        Label label_1 = new Label(container, SWT.NONE);
        label_1.setText(".");
        FormData fd_label_1 = new FormData();
        fd_label_1.top = new FormAttachment(lblIp, 0, SWT.TOP);
        fd_label_1.left = new FormAttachment(text_1, 6);
        label_1.setLayoutData(fd_label_1);
        
        text_2 = new Text(container, SWT.BORDER);
        FormData fd_text_2 = new FormData();
        fd_text_2.top = new FormAttachment(lblIp, 0, SWT.TOP);
        fd_text_2.left = new FormAttachment(label_1, 3);
        fd_text_2.width = 60;
        text_2.setLayoutData(fd_text_2);
        
        Label label_2 = new Label(container, SWT.NONE);
        label_2.setText(".");
        FormData fd_label_2 = new FormData();
        fd_label_2.top = new FormAttachment(lblIp, 0, SWT.TOP);
        fd_label_2.left = new FormAttachment(text_2, 6);
        label_2.setLayoutData(fd_label_2);
        
        text_3 = new Text(container, SWT.BORDER);
        FormData fd_text_3 = new FormData();
        fd_text_3.top = new FormAttachment(lblIp, 0, SWT.TOP);
        fd_text_3.left = new FormAttachment(label_2, 6);
        fd_text_3.width = 60;
        text_3.setLayoutData(fd_text_3);
        
        Label label_3 = new Label(container, SWT.NONE);
        FormData fd_label_3 = new FormData();
        fd_label_3.top = new FormAttachment(lblIp, 33);
        fd_label_3.left = new FormAttachment(lblIp, 0, SWT.LEFT);
        label_3.setLayoutData(fd_label_3);
        label_3.setText(LangConfig.getKey("app.setting.note"));
        
        text_4 = new Text(container, SWT.BORDER);
        FormData fd_text_4 = new FormData();
        fd_text_4.left = new FormAttachment(text, 0, SWT.LEFT);
        fd_text_4.right = new FormAttachment(text_3, 0, SWT.RIGHT);
        fd_text_4.top = new FormAttachment(text, 27);
        text_4.setLayoutData(fd_text_4);
        
        setIpValue(IPRangeUtils.getLocalIPAdress());
        addFocusInListener();
        return container;
    }
    
    public String getIpNote() {
		return ipNote;
	}

	public String getIpValue()
    {
        return ipValue;
    }
    
    private void setIpValue(String ip)
    {
        String[] ips = ip.split("\\.");
        text.setText(ips[0]);
        text_1.setText(ips[1]);
        text_2.setText(ips[2]);
        text_3.setText("1-255");
        text_4.setText("LAN"+ips[2]);
        if(ipNote != null) {
        	readIpValue(ipNote);
        }
    }
    
    //ipNote格式： 局域网：10.0.0.1:2
    public void readIpValue(String ipNote) {
    	//System.out.println("读取ip："+ipNote);
    	String str[] = ipNote.split(":");
    	text_4.setText(str[0]);
    	String ips[] = str[1].split("\\.");
    	text.setText(ips[0]);
        text_1.setText(ips[1]);
        text_2.setText(ips[2]);
        text_3.setText(ips[3]);
    }

    /**
     * Create contents of the button bar.
     * @param parent
     */
    @Override
    protected void createButtonsForButtonBar(Composite parent)
    {
        Button button = createButton(parent, IDialogConstants.OK_ID, IDialogConstants.OK_LABEL, true);
        button.setText(LangConfig.getKey("app.setting.confirm"));
        Button button_1 = createButton(parent, IDialogConstants.CANCEL_ID, IDialogConstants.CANCEL_LABEL, false);
        button_1.setText(LangConfig.getKey("app.setting.cancel"));
    }
    
    protected void buttonPressed(int buttonId) {
        if (buttonId == IDialogConstants.OK_ID)// 如果单击确定按钮，则将值保存到变量
        {
            String ipsStr = text.getText() + "." + text_1.getText() + "." + text_2.getText() + "." + text_3.getText();
            String ipsName = text_4.getText();            
            if(IpSettingServcie.checkIp(this.getShell(),ipsStr, ipsName,ipNote)) {           	
            	ipValue =  (text_4.getText()== "" ? "" : (text_4.getText() + ":")) +  text.getText() + "." + text_1.getText() + "." + text_2.getText() + "." + text_3.getText();
            	IpSettingServcie.loadConfigIp();
            }else {
            	return;
            }

        }
        super.buttonPressed(buttonId);
    }

    /**
     * Return the initial size of the dialog.
     */
    @Override
    protected Point getInitialSize()
    {
        return new Point(450, 200);
    }

    private void addFocusInListener() {
    	text.addListener(SWT.FocusIn, new Listener() {
    		public void handleEvent (Event event) {
    			text.selectAll();
    		}
    	});
    	text_1.addListener(SWT.FocusIn, new Listener() {
    		public void handleEvent (Event event) {
    			text_1.selectAll();
    		}
    	});
    	text_2.addListener(SWT.FocusIn, new Listener() {
    		public void handleEvent (Event event) {
    			text_2.selectAll();
    		}
    	});
    	text_3.addListener(SWT.FocusIn, new Listener() {
    		public void handleEvent (Event event) {
    			text_3.selectAll();
    		}
    	});
    	text_4.addListener(SWT.FocusIn, new Listener() {
    		public void handleEvent (Event event) {
    			text_4.selectAll();
    		}
    	});
    }
}
