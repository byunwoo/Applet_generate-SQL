import java.applet.*;
import java.awt.*;
import java.awt.event.*;

public class Ex0128_03 extends Applet {
    private Image im;
    private boolean bb = false;
    public Ex0128_03() {}
    public Ex0128_03(boolean bb) {
        this.bb = bb;
        this.init();
        this.start();
    }
    public void init() {
        if(!bb) {
            try {
                im = this.getImage(getDocumentBase(), "aaa.gif");
                //this.getDocumentBase() : Html 경로..
                //this.getCodeBase() : class파일의 경로..
            } catch(Exception ee) {}
        } else {
            im = Toolkit.getDefaultToolkit().getImage("aaa.gif");
        }
    }
    public void start() {}
    public void paint(Graphics g) {
        try {
            g.drawImage(im, 10, 10, 280, 180, this);
        } catch(Exception ee) {}
    }
    public static void main(String[] ar) {
        Ex0128_03 es = new Ex0128_03(true);
        Frame f = new Frame();
        f.setLayout(new BorderLayout());
        f.add("Center", es);
        f.setSize(300, 200);
        f.setVisible(true);
    }
}
