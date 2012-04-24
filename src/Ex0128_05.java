import java.applet.*;
import java.awt.*;
import java.awt.event.*;

public class Ex0128_05 extends Applet implements ActionListener {
    private boolean bb = false;
    private Image im, im1;
    private AudioClip ac;
    private Button bt = new Button("소리내기");
    private Button bt1 = new Button("중지");
    private boolean bbb = false;
    public Ex0128_05() {}
    public Ex0128_05(boolean bb) {
        this.bb = bb;
        this.init();
        this.start();
    }
    public void init() {
        if(bb) {
            im = Toolkit.getDefaultToolkit().getImage("aaa.gif");
            im1 = Toolkit.getDefaultToolkit().getImage("bbb.gif");
            try {
                ac = this.getAudioClip(getCodeBase(), "aaa.au");
            } catch(Exception ee) {}
        }
        else {
            try {
                im = this.getImage(getDocumentBase(), "aaa.gif");
                im1 = this.getImage(getCodeBase(), "bbb.gif");
                ac = this.getAudioClip(getCodeBase(), "aaa.au");
            } catch(Exception ee) {}
        }
        this.setLayout(new BorderLayout());
        Panel p = new Panel(new FlowLayout(FlowLayout.RIGHT));
        p.add(bt);
        p.add(bt1);
        this.add("South", p);
    }
    public void start() {
        bt.addActionListener(this);
        bt1.addActionListener(this);
    }
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == bt) {
            if(!bb) {
                ac.play();
            }
            bbb = false;
            repaint();
        }
        else if(e.getSource() == bt1) {
            if(!bb) {
                ac.stop();
            }
            bbb = true;
            repaint();
        }
    }
    public void paint(Graphics g){
        if(bbb) {
            g.drawImage(im, 10, 10, 280, 160, this);
        } else {
            g.drawImage(im1, 10, 10, 280, 160, this);
        }
    }
    public static void main(String[] ar) {
        Ex0128_05 es = new Ex0128_05(true);
        Frame f = new Frame();
        f.setLayout(new BorderLayout());
        f.add("Center", es);
        f.setSize(300, 200);
        f.setVisible(true);
    }
}
