import java.applet.*;
import java.awt.*;
import java.awt.event.*;

public class Ex0128_04 extends Applet implements ActionListener{
    private Button bt = new Button("소리내기");
    private Button bt1 = new Button("중지");
    private AudioClip ac;
    public Ex0128_04() {
        this.init();
        this.start();
    }
    public void init() {
        try {
            ac = this.getAudioClip(getCodeBase(), "aaa.au");
        } catch(Exception ee) {}
        this.setLayout(new FlowLayout());
        this.add(bt);
        this.add(bt1);
    }
    public void start() {
        bt.addActionListener(this);
        bt1.addActionListener(this);
    }
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == bt){//소리내기...
            //ac.play();
            ac.loop();
        } else if(e.getSource() == bt1) {//중지
            ac.stop();
        }
    }
    public static void main(String[] ar) {
        Ex0128_04 es = new Ex0128_04();
        Frame f = new Frame();
        f.setLayout(new BorderLayout());
        f.add("Center", es);
        f.setSize(300, 200);
        f.setVisible(true);
    }
}
