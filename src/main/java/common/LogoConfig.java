package common;


import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;

public class LogoConfig {
	
	public static Image getLogoImage() {
		Image image = new Image(Display.getDefault(),Log4jConfig.class.getResourceAsStream("/common/logo-cheetah.png")); 
		return image;
	}
	
	public static Image getBgImage() {
		Image image = new Image(Display.getDefault(),Log4jConfig.class.getResourceAsStream("/common/bj.png")); 
		return image;
	}
}
