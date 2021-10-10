package app;

import java.io.IOException;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.wb.swt.SWTResourceManager;

public class Test {

	protected Shell shell;

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			Test window = new Test();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the window.
	 */
	public void open() {
		Display display = Display.getDefault();
		createContents();
		shell.open();
		shell.layout();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shell = new Shell();
		shell.setSize(450, 300);
		shell.setText("SWT Application");
		
		//始终最前            
		//OS.SetWindowPos(shell.handle , OS.HWND_TOPMOST, 0 , 700 , 1024 , 68 , SWT.NULL);
		//透明窗体
		//OS.SetWindowLong(shell.handle , OS.GWL_EXSTYLE , OS.GetWindowLong(shell.handle , OS.GWL_EXSTYLE)^0x80000);	
		//shell.setBackground(SWTResourceManager.getColor(SWT.COLOR_BLACK));
		Composite composite = new Composite(shell, SWT.NONE);
		composite.setBackground(SWTResourceManager.getColor(SWT.COLOR_GRAY));
		composite.setBounds(10, 10, 128, 128);
		
		Button button = new Button(composite, SWT.NONE);
		button.setEnabled(true);
		button.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 9, SWT.NORMAL));
		button.setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));
		button.setBounds(0, 10, 80, 27);
		button.setText("历史");
		button.setBackground(SWTResourceManager.getColor(SWT.COLOR_BLUE));
		button.addSelectionListener(new SelectionAdapter() {
			@Override
        	public void widgetSelected(SelectionEvent e) {
				button.setEnabled(false);
				//ProcessBuilder pb=new ProcessBuilder("java","-jar","F:\\silva-work\\temp\\a24.jar");
//				ProcessBuilder pb=new ProcessBuilder("F:\\silva-work\\temp\\minertool-test-v0.2.exe");
//				try {
//					Process p=pb.start();
//	                System.exit(0);
//	                
//	            } catch (IOException e2) {
//	            	e2.printStackTrace();
//	            }
        	}
		});
		
		//fixSetBackground(button);

	}
	
	private void fixSetBackground(Button button) {

		Color foreground = button.getForeground();

		Color background = button.getBackground();

		int x = 0;

		int y = 0;

		Rectangle rect = button.getBounds();

		int width = rect.width;

		int height = rect.height;

		String text = button.getText();

		if (width == 0)

		width = 1;

		if (height == 0)

		height = 1;

		button.setImage(new Image(button.getParent().getDisplay(), width,height));

		Image original = button.getImage();

		GC gc = new GC(original);

		gc.setForeground(foreground);

		gc.setBackground(background);

		gc.drawRectangle(x, y, width, height);

		gc.fillRectangle(x, y, width, height);

		Font font = button.getFont();

		FontData fontData = font.getFontData()[0];

		int fontSize = fontData.getHeight();

		gc.setFont(button.getFont());

		int ximg = (x + width) / 2 - fontSize * text.length() / 3;

		int yimg = (y + height) / 2 - fontSize * 3 / 4;

		gc.drawText(text, ximg < 4 ? ximg : 4, yimg < 4 ? yimg : 4,

		SWT.DRAW_TRANSPARENT | SWT.DRAW_MNEMONIC);

		gc.dispose();

		}
	
}
