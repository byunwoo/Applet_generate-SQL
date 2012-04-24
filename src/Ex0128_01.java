import java.applet.*;
import java.awt.*;
import java.awt.event.*;

public class Ex0128_01 extends Applet {
    private static String str = "";
    public Ex0128_01() {
        this.init();
        this.start();
        this.stop();
    }
    public void init() { str += "init "; }
    public void start() { str += "start "; }
    public void paint(Graphics g) { 
        g.setFont(new Font("Sans-Serif", Font.BOLD, 20));
        g.drawString("String : " + str, 50, 100);
    }
    public void stop() { str = "stop "; }
}
