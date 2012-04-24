import java.applet.*;
import java.awt.*;
import java.awt.event.*;

public class Ex0128_04 extends Applet implements ActionListener{
    private Button bt = new Button("�Ҹ�����");
    private Button bt1 = new Button("����");
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
        if(e.getSource() == bt){//�Ҹ�����...
            //ac.play();
            ac.loop();
        } else if(e.getSource() == bt1) {//����
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
