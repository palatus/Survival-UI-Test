package woodOfMist;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class EnterTheWoods {

	public static JFrame frame = new JFrame("Wood of The Mists");
	public static final Dimension ScreenSize = Toolkit.getDefaultToolkit().getScreenSize();
	public static Window screen= new Window();
	
	@SuppressWarnings("unused")
	public static void main(String[] args) {
		
		frame.setUndecorated(true);
		frame.setSize(ScreenSize);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(screen);
		screen.setOwner(frame);
		Player player = new Player(50,50,"Images\\HeartFilled.png",screen);
		player.setTag("Main player");
		Player s2 = new Player(550,50,"Images\\HeartFilledGold.png",screen);
		
		Food f = new Food(10.00,"Drumstick");
		f.setIcon(new ImageIcon("Images\\drumstick.png"));
		f.setCount(50);
		Food f2 = new Food(40.00,"Power Food");
		f2.setIcon(new ImageIcon("Images\\Icons\\Gunn.png"));
		WorldItem w = new WorldItem(75,75,screen,f);
		w.getCenter();
		
		WorldItem w2 = new WorldItem(175,175,screen,f2);
		
		Food f3 = new Food(55.00,"TEST3");
		WorldItem w3 = new WorldItem(175,175,screen,f3);
		
		Tool swordOne = new Tool(1,150,"Images\\Icons\\Axe.png");
		swordOne.setName("Standard Axe");
		swordOne.setRot45(new ImageIcon("Images\\Icons\\Axe45.png"));
		swordOne.setFlipped45(new ImageIcon("Images\\Icons\\Axe45r.png"));
		WorldItem swo = new WorldItem(200,50,screen,swordOne);
		
		Resource ruby = new Resource("Ruby","Images\\Icons\\ruby.png");
		ruby.setCount(25);
		ruby.spawn(-25, -50, screen);
		
		ImageIcon toph = new ImageIcon("Images\\Player\\Hats\\Test1.png");
		Hat h = new Hat(toph,toph,toph,toph);
		h.setName("Top Hat");
		h.spawn(55, 100, screen);
		
		screen.addObject(player);
		screen.addObject(s2);
		player.control();
		
		frame.revalidate();
		
		Cursor c = new Cursor(new File("Images\\Cursor01.gif"),frame);
		screen.setCursor(c);
		
		screen.setPlayer(player);
		
	}

}
