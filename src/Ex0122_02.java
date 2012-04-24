import java.applet.*;
import java.awt.*;
import java.awt.event.*;
public class Ex0122_02 extends Applet implements ActionListener {
    /*
    private MenuBar mb = new MenuBar();
    private Menu edit = new Menu("편집");
    private Menu show = new Menu("보기");
    private Menu help = new Menu("도움말");
    */
    private Color cc = new Color(192,192,192);
    private Label empty = new Label();
    private Label view = new Label("0.", Label.RIGHT);
    private Label mv = new Label();
    private Button mc = new Button("MC");
    private Button mr = new Button("MR");
    private Button ms = new Button("MS");
    private Button mp = new Button("M+");
    private Button bs = new Button("Backspace");
    private Button ce = new Button("CE");
    private Button c = new Button("C");
    private Button su1 = new Button("1");
    private Button su2 = new Button("2");
    private Button su3 = new Button("3");
    private Button su4 = new Button("4");
    private Button su5 = new Button("5");
    private Button su6 = new Button("6");
    private Button su7 = new Button("7");
    private Button su8 = new Button("8");
    private Button su9 = new Button("9");
    private Button su0 = new Button("0");
    private Button sign = new Button("+ / -");
    private Button jum = new Button(".");
    private Button add1 = new Button("+");
    private Button min1 = new Button("-");
    private Button mul1 = new Button("*");
    private Button div1 = new Button("/");
    private Button res = new Button("=");
    private Button per = new Button("%");
    private Button na = new Button("1 / x");
    private Button sqrt = new Button("sqrt");
    private Font f = new Font("돋움체", Font.PLAIN, 13);
    
    private boolean first = true;//true :최초..
                                                            //false :최초아님.
    private double tot = 0.0;
    private char yonsan = '+';
    private boolean press = false;//false :누르지 않음..
                                                                //true :누름..
    private double memory = 0.0;
    /*
    public Ex0122_02_Sub(String str){
        super(str);
        this.setBounds(10,10,10,10);
        this.setBackground(cc);
        this.setForeground(Color.blue);
        this.setFont(f);
        this.setLayout(new BorderLayout(10,10));
        this.init();
        this.start();
        this.setResizable(false);
        this.setSize(300,235);
        Toolkit tk = Toolkit.getDefaultToolkit();
        Dimension di = tk.getScreenSize();
        Dimension di1 = this.getSize();
        this.setLocation((int)(di.getWidth()/2-di1.getWidth()/2),(int)(di.getHeight()/2-di1.getHeight()/2));
        this.setVisible(true);
    }
    */
    public void init() {
        this.setBackground(cc);
        this.setForeground(Color.blue);
        this.setFont(f);
        this.setLayout(new BorderLayout(10,10));
        mv.setForeground(Color.red);
        mc.setForeground(Color.red);
        mr.setForeground(Color.red);
        ms.setForeground(Color.red);
        mp.setForeground(Color.red);
        bs.setForeground(Color.red);
        ce.setForeground(Color.red);
        c.setForeground(Color.red);
        add1.setForeground(Color.red);
        min1.setForeground(Color.red);
        mul1.setForeground(Color.red);
        div1.setForeground(Color.red);
        res.setForeground(Color.red);
        
        /*
        this.setMenuBar(mb);
        mb.add(edit);
        mb.add(show);
        mb.add(help);
        */
        
        view.setFont(new Font("휴먼옛체", Font.BOLD, 20));
        view.setBackground(Color.WHITE);
        //mv.setBackground(Color.WHITE);
        
        //Bor-North
        this.add("North", view);
        //Bor-West	
        Panel pW = new Panel();
        pW.setLayout(new GridLayout(5,1,5,5));
        pW.add(mv);
        pW.add(mc);
        pW.add(mr);
        pW.add(ms);
        pW.add(mp);
        this.add("West",pW);
        //Bor-Center
        Panel pCN = new Panel();
        pCN.setLayout(new GridLayout(1,3,5,5));
        pCN.add(bs);
        pCN.add(ce);
        pCN.add(c);
        Panel pCC = new Panel();
        pCC.setLayout(new GridLayout(4,5,5,5));
        pCC.add(su7);
        pCC.add(su8);
        pCC.add(su9);
        pCC.add(div1);
        pCC.add(sqrt);
        pCC.add(su4);
        pCC.add(su5);
        pCC.add(su6);
        pCC.add(mul1);
        pCC.add(per);
        pCC.add(su1);
        pCC.add(su2);
        pCC.add(su3);
        pCC.add(min1);
        pCC.add(na);
        pCC.add(su0);
        pCC.add(sign);
        pCC.add(jum);
        pCC.add(add1);
        pCC.add(res);
        
        
        Panel pC = new Panel();
        pC.setLayout(new BorderLayout(5,5));
        pC.add("North",pCN);
        pC.add("Center",pCC);
        
        this.add("Center",pC);
        
    
    }
    public void start() {
        /*
        this.addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent e){
                System.exit(0);
            }
        });	
        */
        su0.addActionListener(this);
        su1.addActionListener(this);
        su2.addActionListener(this);
        su3.addActionListener(this);
        su4.addActionListener(this);
        su5.addActionListener(this);
        su6.addActionListener(this);
        su7.addActionListener(this);
        su8.addActionListener(this);
        su9.addActionListener(this);
        su0.setActionCommand("su");
        su1.setActionCommand("su");
        su2.setActionCommand("su");
        su3.setActionCommand("su");
        su4.setActionCommand("su");
        su5.setActionCommand("su");
        su6.setActionCommand("su");
        su7.setActionCommand("su");
        su8.setActionCommand("su");
        su9.setActionCommand("su");
        add1.addActionListener(this);
        min1.addActionListener(this);
        mul1.addActionListener(this);
        div1.addActionListener(this);
        add1.setActionCommand("yon");
        min1.setActionCommand("yon");
        mul1.setActionCommand("yon");
        div1.setActionCommand("yon");
        res.addActionListener(this);
        res.setActionCommand("yon");
        sign.addActionListener(this);
        jum.addActionListener(this);
        ce.addActionListener(this);
        c.addActionListener(this);
        bs.addActionListener(this);
        mc.addActionListener(this);
        mr.addActionListener(this);
        ms.addActionListener(this);
        mp.addActionListener(this);
    }
    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand().equals("su")){
            String imsi = ((Button)e.getSource()).getLabel().trim();
            if(first == true) {
                view.setText(imsi + ".");
                if(imsi.equals("0")){
                    return;
                }
                first = false;
            } else {
                if(press == true) {
                    String str = view.getText().trim();
                    str += imsi;
                    view.setText(str);
                } else {
                    String str = view.getText().trim();
                    str = str.substring(0, str.indexOf("."));
                    str += imsi;//str = str + imsi;
                    view.setText(str + ".");
                }
            }

        } else if(e.getActionCommand().equals("yon")) {
            double x = Double.parseDouble(view.getText().trim());
            switch(yonsan) {
            case '+': tot += x; break;
            case '-': tot -= x; break;
            case '*': tot *= x; break;
            case '/': tot /= x; break;
            }
            yonsan = ((Button)e.getSource()).getLabel().charAt(0);
            first = true;
            press = false;
            double imsi = tot - (int)tot;
            if(imsi == 0.0) {
                view.setText((int)tot + ".");
            } else {
                view.setText(String.valueOf(tot));
            }
            if(yonsan == '=') {
                tot = 0.0;
                yonsan = '+';
            }
        } else if(e.getSource() == sign) {
            String str = view.getText().trim();
            if(str.equals("0.")) {
                return;
            }
            char imsi = str.charAt(0);
            if(imsi == '-') {
                str = str.substring(1);
            } else {
                str = "-" + str;
            }
            view.setText(str);
        } else if(e.getSource() == jum) {
            if(first == true) {
                view.setText("0.");
            }
            press = true;
            first = false;
        } else if(e.getSource() == ce) {
            first = true;
            press = false;
            view.setText("0.");
        } else if(e.getSource() == c) {
            first = true;
            press = false;
            view.setText("0.");
            tot = 0.0;
            yonsan = '+';
        } else if(e.getSource() == bs) {
            String str = view.getText().trim();
            
            if(str.length() == 3 && str.charAt(0) == '-') {
                view.setText("0.");
                first = true;
                press = false;
                return;
            }
            if(str.length() == 2) {
                view.setText("0.");
                first = true;
                press = false;
                return;
            }
            if(press) {
                if(str.charAt(str.length() - 1) == '.') {
                    press = false;
                    return;
                }
                str = str.substring(0, str.length() - 1);
            } else {
                str = str.substring(0, str.length() - 2);
                str += ".";
            }
            view.setText(str);
        } else if(e.getSource() == mc) {
            memory = 0.0;
            mv.setText("");
        } else if(e.getSource() == mr) {
            double imsi = memory - (int)memory;
            if(imsi == 0.0) {
                view.setText((int)memory + ".");
            } else {
                view.setText(String.valueOf(memory));
            }
        } else if(e.getSource() == ms) {
            String str = view.getText().trim();
            memory = Double.parseDouble(str);
            mv.setText("M");
        } else if(e.getSource() == mp) {
            String str = view.getText().trim();
            memory += Double.parseDouble(str);
            mv.setText("M");
        }
    }
}
/*
public class Ex0122_02{
    public static void main(String[] ar){
        Ex0122_02_Sub es = new Ex0122_02_Sub("계산기");
    }
}
*/

