import java.applet.Applet;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
 
public class ByunApplet extends Applet implements MouseMotionListener {
  
	int _x;
	int _y;
	Graphics _g;
	
	public void init() {
		addMouseMotionListener(this);
		setBackground(Color.black);
		
		_g = getGraphics();
		_g.setColor(Color.white);
	}
	
	public void paint(Graphics screen) {
	}

	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		_g.drawLine(_x, _y, e.getX(), e.getY());
		
		_x = e.getX();
		_y = e.getY();
	}

	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		_x = e.getX();
		_y = e.getY();
	}
}
