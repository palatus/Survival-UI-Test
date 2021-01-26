package woodOfMist;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.MouseInfo;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

@SuppressWarnings("serial")
public class Window extends JLabel implements KeyListener, MouseListener, MouseMotionListener, MouseWheelListener{
	
	private ArrayList<Sprite> sprites = new ArrayList<Sprite>();
	private ArrayList<Sprite> UIsprites = new ArrayList<Sprite>();
	
	private boolean[] wasd = new boolean[4];
	private boolean escape,shift,control;
	private Timer task = new Timer();
	private JFrame owner;
	private Cursor cursor;
	private Player player;
	private int shiftx,shifty,toolBarSelect;
	
	public Window(){
		this.addKeyListener(this);
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
		this.addMouseWheelListener(this);
		this.setVisible(true);
		this.setSize(EnterTheWoods.ScreenSize);
		setFocusable(true);	
	}
	
	public void addObject(Sprite s){
		if (s instanceof Sprite){
			getSprites().add(s);
			System.out.println(getSprites().size());
		}
	}
	
	public void addUIObject(Sprite s){
		if (s instanceof Sprite){
			getUIsprites().add(s);
			System.out.println(getUIsprites().size());
		}
	}
	
	public void paint(Graphics g){
		
		quickSort((Sprite[]) sprites.toArray(new Sprite[sprites.size()]));
		
		int shiftx = 0;
		int shifty = 0;
		if (player!=null && getOwner()!=null){ // IMPORTANT! Use to get visual x and y coodinates of all drawn sprites 
			shiftx = (int)-player.getX()+getOwner().getWidth()/2;
			shifty = (int) -player.getY()+getOwner().getHeight()/2;
			this.shiftx = shiftx;
			this.shifty = shifty;
		}
		int gx = 0;
		int gy = 0;

		int w = 0;
		int h = 0;
		
		if (getCustomCursor()!=null && getCustomCursor().getClickPoint()!=null){
			gx = getCustomCursor().getClickPoint().x;
			gy = getCustomCursor().getClickPoint().y;

			w = getCustomCursor().getLocation().x-gx;
			h = getCustomCursor().getLocation().y-gy;
		}
		
		if (getCustomCursor()!=null)
		for (int i = 0; i<getCustomCursor().getSelected().size(); i++){
			g.fillOval((int)getCustomCursor().getSelected().get(i).getX()+shiftx-6-(int)(getCustomCursor().getSelected().get(i)).getWidth()/2,
					(int)getCustomCursor().getSelected().get(i).getY()+shifty-6-(int)(getCustomCursor().getSelected().get(i)).getHeight()/2,
					(int)getCustomCursor().getSelected().get(i).getWidth()+8, (int)getCustomCursor().getSelected().get(i).getHeight()+8);
		}
		
		Graphics2D g2 = (Graphics2D) g;
		
		if (getCustomCursor()!=null){
			String ll = "";
			for (int i = 0; i<getCustomCursor().getSelected().size(); i++){
				if (getCustomCursor().getSelected().get(i) instanceof WorldItem)
					ll+="|"+((WorldItem) getCustomCursor().getSelected().get(i)).getItem().getName()+"| + ";
				else 
					ll+="|"+getCustomCursor().getSelected().get(i).getTag()+"| + ";
				
				g.drawString(ll.substring(0, ll.length()-3), 16, 48);
			}
			g.drawString(getPlayer().toString()+" -- "+getPlayer().getX()+" - "+getPlayer().getY(), 16, 64);
			if (getCustomCursor().isMouseDown() && getCustomCursor().getClickPoint()!=null){
				g.setColor(Color.black);
				g.drawString(gx+" - "+gy, 16, 16);
				g.drawString((w)+" - "+(h), 16, 32);
				g.setColor(new Color(0,255,0,50));
				g.fillRect(gx, gy, w ,h);
			}
		}
		
		g.setColor(Color.black);
		g.drawString("BP number: "+toolBarSelect, 16, 80);
		
		// AFTER THIS, ALL GRAPHICS WILL SHIFT
		
		if (player!=null && getOwner()!=null){
			g.translate((int)-player.getX()+getOwner().getWidth()/2,(int) -player.getY()+getOwner().getHeight()/2);
		}
		
		g.setColor(Color.black);
		for (int i = 0; i<sprites.size(); i++){
			if (sprites.get(i).getImage()!=null){
				g.drawImage(sprites.get(i).getImage().getImage(), (int)sprites.get(i).getX()-(int)sprites.get(i).getWidth()/2, (int)sprites.get(i).getY()-(int)sprites.get(i).getHeight()/2,null);
			} else {
				g.drawRect((int)sprites.get(i).getX(), (int)sprites.get(i).getY(), (int)sprites.get(i).getWidth(), (int)sprites.get(i).getHeight());
			}
		}
		
		// Draw Player Equipped Items
		if (player!=null && player.getEquiped() != null && player.getEquiped().getRot45() != null){
			if (player.isFaceDown()){
				g.drawImage(player.getEquiped().getRot45().getImage(), (int) (player.getX()-(int)player.getWidth()/2+24), (int)player.getY()-(int)player.getHeight()/2+12,null);
			}
			if (player.isFaceUp()){
				g.drawImage(player.getEquiped().getFlipped45().getImage(), (int) (player.getX()-(int)player.getWidth()/2-24), (int)player.getY()-(int)player.getHeight()/2+12,null);
			}
			if (player.isFaceRight()){
				g.drawImage(player.getEquiped().getRot45().getImage(), (int) (player.getX()-(int)player.getWidth()/2+16), (int)player.getY()-(int)player.getHeight()/2+12,null);
			}
			if (player.isFaceLeft()){
				g.drawImage(player.getEquiped().getFlipped45().getImage(), (int) (player.getX()-(int)player.getWidth()/2-16), (int)player.getY()-(int)player.getHeight()/2+12,null);
			}
		}if (player!=null && player.getHat()!=null){
			g.drawImage(player.getHat().getIcon().getImage(), (int) (player.getX()-(int)player.getWidth()/2), (int)player.getY()-(int)player.getHeight()+16,null);
		}
		
		
		// BACK TO NORMAL TRANSLATION
		
		if (player!=null && getOwner()!=null){
			g.translate((int)player.getX()-getOwner().getWidth()/2,(int)player.getY()-getOwner().getHeight()/2);
		}
		
		drawUIBP(g);
		drawHP(g);
		drawHunger(g);
		
		for (int i = 0; i<UIsprites.size(); i++){
			if (UIsprites.get(i).getImage()!=null){
				g.drawImage(UIsprites.get(i).getImage().getImage(), (int)UIsprites.get(i).getX(), (int)UIsprites.get(i).getY(),null);
			} else {
				if (UIsprites.get(i).getDisplayString()==null){
					g.drawRect((int)UIsprites.get(i).getX(), (int)UIsprites.get(i).getY(), (int)UIsprites.get(i).getWidth(), (int)UIsprites.get(i).getHeight());
				} else {
					g.drawString(UIsprites.get(i).getDisplayString(),(int)UIsprites.get(i).getX(), (int)UIsprites.get(i).getY());
				}
			}
		}

		g = g2;
		
		super.paint(g);
		if (getOwner()!=null){
			getOwner().repaint();
		}
	}
	
	public void drawUIBP(Graphics g){
		
		g.setColor(new Color(0,0,0,0.5f));
		g.fillRect(EnterTheWoods.ScreenSize.width/2-304, EnterTheWoods.ScreenSize.height-64, 601, 39);
		g.setColor(new Color(0,0,0,1f));
		g.drawRect(EnterTheWoods.ScreenSize.width/2-304, EnterTheWoods.ScreenSize.height-64, 601, 39);
		g.setColor(new Color(0,0,0,0.5f));
		
		for (int i = 0; i<15; i++){
			
			Rectangle r1 = new Rectangle(EnterTheWoods.ScreenSize.width/2-304 +((32+8)*i)+4, EnterTheWoods.ScreenSize.height-61, 32, 32);
			Rectangle r2 = new Rectangle(MouseInfo.getPointerInfo().getLocation().x,MouseInfo.getPointerInfo().getLocation().y,2,2);
			
			if (r1.intersects(r2)){
				g.setColor(new Color(150,150,150));
				g.fillRect(EnterTheWoods.ScreenSize.width/2-304-115 +((32+8)*i)+4, EnterTheWoods.ScreenSize.height-256, 256,128);
				g.setColor(new Color(0,0,0));
				g.drawRect(EnterTheWoods.ScreenSize.width/2-304-115 +((32+8)*i)+4, EnterTheWoods.ScreenSize.height-256, 256,128);
				
				if (player != null && player.getToolBar()!=null && player.getToolBar().get(i)!= null && player.getToolBar().get(i).getName()!=null){
					drawItemD(g,i);
				} else if (player.getToolBar().get(i) == null){
					g.drawString("Empty Toolbar Slot", EnterTheWoods.ScreenSize.width/2-304-115 +((32+8)*i)+4, EnterTheWoods.ScreenSize.height-260);
				}
			}
			
			g.setColor(new Color(0,0,0,0.5f));
			g.drawRoundRect(EnterTheWoods.ScreenSize.width/2-304 +((32+8)*i)+4, EnterTheWoods.ScreenSize.height-61, 32, 32,2,2);
			if (i == toolBarSelect)
				g.fillRect(EnterTheWoods.ScreenSize.width/2-304 +((32+8)*i)+4, EnterTheWoods.ScreenSize.height-61, 33, 33);
			
			if (player != null && player.getToolBar() != null && player.getToolBar().get(i) != null){
				drawItemDisplay(g,i);
			}
			
		}
		
	}
	
	public void drawItemD(Graphics g, int i){
		Font f = g.getFont();
		g.setFont(new Font("TimesRoman",Font.BOLD,22));
		g.drawString(player.getToolBar().get(i).getName(), EnterTheWoods.ScreenSize.width/2-304-115 +((32+8)*i)+4, EnterTheWoods.ScreenSize.height-260);
		if (player.getToolBar().get(i) instanceof Food){
			g.drawString("Nutritional Value: "+((Food) player.getToolBar().get(i)).getNutrition(), EnterTheWoods.ScreenSize.width/2-304-104 +((32+8)*i)+4, EnterTheWoods.ScreenSize.height-256+32);
			g.drawString("Count: "+((Food) player.getToolBar().get(i)).getCount(), EnterTheWoods.ScreenSize.width/2-304-104 +((32+8)*i)+4, EnterTheWoods.ScreenSize.height-256+64);
		} else if (player.getToolBar().get(i) instanceof Tool){
			g.drawString("Tool Strength: "+((Tool)player.getToolBar().get(i)).getStrength(), EnterTheWoods.ScreenSize.width/2-304-104 +((32+8)*i)+4, EnterTheWoods.ScreenSize.height-256+32);
			g.drawString("Durability: "+((Tool) player.getToolBar().get(i)).getDurableString(), EnterTheWoods.ScreenSize.width/2-304-104 +((32+8)*i)+4, EnterTheWoods.ScreenSize.height-256+64);
		} else if (player.getToolBar().get(i) instanceof Resource){
			g.drawString("Crafting Material", EnterTheWoods.ScreenSize.width/2-304-104 +((32+8)*i)+4, EnterTheWoods.ScreenSize.height-256+32);		
		} else if (player.getToolBar().get(i) instanceof Hat){
			g.drawString("Cosmetic Hat Item", EnterTheWoods.ScreenSize.width/2-304-104 +((32+8)*i)+4, EnterTheWoods.ScreenSize.height-256+32);		
		}
		g.setFont(f);
	}
	
	public void drawItemDisplay(Graphics g, int i){
		g.drawImage(player.getToolBar().get(i).getIcon().getImage(), EnterTheWoods.ScreenSize.width/2-304 +((32+8)*i)+4, EnterTheWoods.ScreenSize.height-61, null);
		g.setColor(Color.black);
		if (player.getToolBar().get(i) instanceof Stackable)
			g.drawString(""+((Stackable) player.getToolBar().get(i)).getCount(), EnterTheWoods.ScreenSize.width/2-304 +((32+8)*i)+4, EnterTheWoods.ScreenSize.height-61-8);
		if (player.getToolBar().get(i) instanceof Equipable){
			g.drawString(""+((Equipable) player.getToolBar().get(i)).getDurableString(), EnterTheWoods.ScreenSize.width/2-304 +((32+8)*i)+4, EnterTheWoods.ScreenSize.height-61-8);
		}
	}
	
	public void drawHP(Graphics g){
		// Health Bar
		if (player != null){
			g.setColor(new Color(0,0,0));
			g.drawRoundRect(EnterTheWoods.ScreenSize.width-256, 32, 224, 32, 4, 4);
		
			double p1 = player.getHealth();
			int mod = 0;
			if (p1<1){
				p1 = 1;
				mod = 2;
			}
			double p2 = player.getMaxHealth();
			if (p1>p2){
				p1=p2;
			}
			double pc = p1/p2;
			
			g.setColor(new Color((int)(255-255*pc),(int)(255*pc),0));
			
			g.fillRect(EnterTheWoods.ScreenSize.width-255, 33, (int) (223*pc)-mod, 31);
			g.drawImage(new ImageIcon("Images\\HeartIcon.png").getImage(),EnterTheWoods.ScreenSize.width-304, 32,null);
			Rectangle r1 = new Rectangle(EnterTheWoods.ScreenSize.width-256, 32, 224, 32);
			Rectangle r2 = new Rectangle(MouseInfo.getPointerInfo().getLocation().x,MouseInfo.getPointerInfo().getLocation().y,2,2);
			
			if (r1.intersects(r2)){
				g.setColor(new Color(0,0,0));
				Font f = g.getFont();
				g.setFont(new Font("TimesRoman",Font.BOLD,22));
				g.drawString(""+new DecimalFormat("#").format(player.getHealth()), EnterTheWoods.ScreenSize.width-150, 56);
				g.setFont(f);
			}

		}
		
		g.setColor(new Color(0,0,0));
	}
	
	public void drawHunger(Graphics g){
		
		if (player != null){
			int mod = 0;
			double p1 = player.getHunger();
			if (p1<1){
				p1 = 1;
				mod=2;
			}
			double p2 = player.getMaxHunger();
			if (p1>p2){
				p1=p2;
			}
			double pc = p1/p2;
			
			g.drawRoundRect(EnterTheWoods.ScreenSize.width-256, 80, 224, 32, 4, 4);
			
			g.setColor(new Color((int)(255-255*pc),(int)(255*pc),0));
			
			g.fillRect(EnterTheWoods.ScreenSize.width-255, 81, (int) (223*pc)-mod+1, 31);
			g.drawImage(new ImageIcon("Images\\drumstick.png").getImage(),EnterTheWoods.ScreenSize.width-304, 80,null);
			Rectangle r1 = new Rectangle(EnterTheWoods.ScreenSize.width-256, 80, 224, 32);
			Rectangle r2 = new Rectangle(MouseInfo.getPointerInfo().getLocation().x,MouseInfo.getPointerInfo().getLocation().y,2,2);
			
			if (r1.intersects(r2)){
				g.setColor(new Color(0,0,0));
				Font f = g.getFont();
				g.setFont(new Font("TimesRoman",Font.BOLD,22));
				g.drawString(""+new DecimalFormat("#").format(player.getHunger()), EnterTheWoods.ScreenSize.width-150, 104);
				g.setFont(f);
			}

		}
		g.setColor(new Color(0,0,0));
	}
	
	public void removeObjectByString(String s){
		for (int i = 0; i<sprites.size(); i++){
			if (sprites.get(i).getTag() != null && sprites.get(i).getTag().equals(s)){
				sprites.remove(i);
				i--;
			}
		}
		for (int i = 0; i<UIsprites.size(); i++){
			if (UIsprites.get(i).getTag() != null && UIsprites.get(i).getTag().equals(s)){
				UIsprites.remove(i);
				i--;
			}
		}
	}
	
	public void dropItem(Item item){
		if(item!=null){
			WorldItem w = new WorldItem((int)player.getX(), (int)player.getY()+32, this, item.Clone());
			w.setWidth(item.getIcon().getIconWidth());
			w.setHeight(item.getIcon().getIconHeight());
			w.generateTag();
			System.out.println(w.getTag());
		}
	}
	
	public void dropItemButton(Item item){
		if(item!=null){
			WorldItem w = new WorldItem((int)player.getX(), (int)player.getY()+32, this, item.Clone());
			if (item instanceof Stackable){
				((Stackable) w.getItem()).setCount(1);
			}
			w.setWidth(item.getIcon().getIconWidth());
			w.setHeight(item.getIcon().getIconHeight());
			w.generateTag();
			System.out.println(w.getTag());
			player.removeItemCount(player.getToolBar().get(toolBarSelect), 1, toolBarSelect);
		}
	}
	
	public void checkClick(){
		
		for (int i = 0; i<sprites.size(); i++){
			if (sprites.get(i) instanceof WorldItem){
				Rectangle r = new Rectangle((int)sprites.get(i).getX()-8,(int)sprites.get(i).getY()-8,(int)sprites.get(i).getWidth(),(int)sprites.get(i).getHeight());
				Rectangle r2 = new Rectangle(MouseInfo.getPointerInfo().getLocation().x-shiftx,MouseInfo.getPointerInfo().getLocation().y-shifty,8,8);
				if (r.intersects(r2)){		
						if (player.getToolBar().addItem(((WorldItem) sprites.get(i)).getItem())){
							getCustomCursor().getSelected().remove(((WorldItem) sprites.get(i)));
							this.removeObjectByString(((WorldItem) sprites.get(i)).getTag());
							System.out.println("PICKUP");
						}
					break;
				}
			}
		}
		
		for (int i = 0; i<15; i++){
			
			Rectangle r1 = new Rectangle(EnterTheWoods.ScreenSize.width/2-304 +((32+8)*i)+4, EnterTheWoods.ScreenSize.height-61, 32, 32);
			Rectangle r2 = new Rectangle(MouseInfo.getPointerInfo().getLocation().x,MouseInfo.getPointerInfo().getLocation().y,8,8);
			
			if (r1.intersects(r2)){
				if (!shift){ // Do item click code
					if (player.getToolBar().get(i) instanceof Food){
						player.eat((Food) player.getToolBar().get(i));
					}
					if (player.getToolBar().get(i) instanceof Hat){
						player.setHat((Hat) player.getToolBar().get(i));
					}

				} else { // Do item Shift+click code
					if (player.getToolBar().get(i) instanceof Food){
						dropItem(player.getToolBar().get(i));
						player.removeItemCount((Stackable) player.getToolBar().get(i), ((Stackable) player.getToolBar().get(i)).getCount(), i);
					}
				}
			}
			
		}
			
	}
	
	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		
		if (e.getWheelRotation() < 0){
			moveToolBar(-1);
		}
		
		if (e.getWheelRotation() > 0){
			moveToolBar(1);
		}
		
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if (e.getButton() == MouseEvent.BUTTON1){
		if (getCustomCursor() != null){
			if (getCustomCursor().getSelected().size()>0)
				getCustomCursor().setSelected(new ArrayList<Sprite>());
			getCustomCursor().setMouseDown(true);
			getCustomCursor().setClickPoint(getCustomCursor().getLocation());
		}
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if (e.getButton() == MouseEvent.BUTTON1){
		if (getCustomCursor() != null && getCustomCursor().getClickPoint() != null){
			
			int shiftx = 0;
			int shifty = 0;
			if (player!=null && getOwner()!=null){
				shiftx = (int)-player.getX()+getOwner().getWidth()/2;
				shifty = (int) -player.getY()+getOwner().getHeight()/2;
			}
			
			int gx = getCustomCursor().getClickPoint().x;
			int gy = getCustomCursor().getClickPoint().y;
			int w = getCustomCursor().getLocation().x;
			int h = getCustomCursor().getLocation().y;
			
			for (int i = 0; i<sprites.size(); i++){
				int sx = (int) sprites.get(i).getX()+shiftx-(int)(sprites.get(i)).getWidth()/2;
				int sy = (int) sprites.get(i).getY()+shifty-(int)(sprites.get(i)).getHeight()/2;
				int sw = (int) sprites.get(i).getTopRight().getX()+shiftx;
				int sh = (int) sprites.get(i).getBottomRight().getY()+shifty;
				int sh2 = (int) sprites.get(i).getTopRight().getY()+shifty;
				int sw2 = (int) sprites.get(i).getBottomRight().getX()+shiftx;
				// Check box collison 
				if (gx<w && gy<h){
					if (sx >= gx && sy >= gy && sw < w && sh < h){
						getCustomCursor().getSelected().add(sprites.get(i));
						System.out.println("Selected1");
					}
				}else if (sx <= gx && sy <= gy && sw > w && sh > h){
						getCustomCursor().getSelected().add(sprites.get(i));
						System.out.println("Selected2");
				}else if (sx <= gx && sy >= gy && sw2 > w && sh2 < h){
						getCustomCursor().getSelected().add(sprites.get(i));
						System.out.println("Selected3");
				}else if (sx >= gx && sy <= gy && sw2 < w && sh2 > h){
						getCustomCursor().getSelected().add(sprites.get(i));
						System.out.println("Selected4");
				}
			}
			
			getCustomCursor().setMouseDown(false);
			getCustomCursor().setClickPoint(null);
			
			for (int i = 0; i<getCustomCursor().getSelected().size(); i++){
				if (getCustomCursor().getSelected().get(i) instanceof Player){
					getPlayer().stopTasks();
					setPlayer((Player) getCustomCursor().getSelected().get(i));
					getPlayer().control();
					System.out.println("Set Player");
					break;
				}
			}
			
		}
		} else if (e.getButton() == MouseEvent.BUTTON3){
			checkClick();
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_ESCAPE && !escape){
			ImageIcon lk = new ImageIcon("Images\\runed.gif");
			Sprite m = new Sprite((int)(EnterTheWoods.ScreenSize.getWidth()/2-lk.getIconWidth()/2),(int)EnterTheWoods.ScreenSize.getHeight()/2-lk.getIconHeight()/2-64,lk);
			m.setTag("EscapeDraw");
			
			Sprite m2 = new Sprite((int)(EnterTheWoods.ScreenSize.getWidth()/2-lk.getIconWidth()/2),(int)(EnterTheWoods.ScreenSize.getHeight()/2-lk.getIconHeight()/2)-128,32,32);
			m2.setDisplayString("CLOSING GAME");
			m2.setTag("EscapeDraw");
			
			addUIObject(m);
			addUIObject(m2);
			
			escape = true;
			task.schedule(new TimerTask(){
				@Override
				public void run() {
					System.err.println("SYSTEM EXIT");
					System.exit(0);
				}
			}, 750);
		}
		if(e.getKeyCode() == KeyEvent.VK_S){
			wasd[2] = true;
			player.setImage(new ImageIcon("Images\\Player\\HoodDAnim.gif"));
			player.setFaceUp(false);
			player.setFaceDown(true);
			player.setFaceLeft(false);
			player.setFaceRight(false);
		} else if(e.getKeyCode() == KeyEvent.VK_W){
			wasd[0] = true;
			player.setImage(new ImageIcon("Images\\Player\\HoodDIdle.gif"));
			player.setFaceUp(true);
			player.setFaceDown(false);
			player.setFaceLeft(false);
			player.setFaceRight(false);
		}else if(e.getKeyCode() == KeyEvent.VK_A){
			wasd[1] = true;
			player.setImage(new ImageIcon("Images\\Player\\HoodLAnim.gif"));
			player.setFaceUp(false);
			player.setFaceDown(false);
			player.setFaceLeft(true);
			player.setFaceRight(false);
		}else if(e.getKeyCode() == KeyEvent.VK_D){
			wasd[3] = true;
			player.setImage(new ImageIcon("Images\\Player\\HoodRAnim.gif"));
			player.setFaceUp(false);
			player.setFaceDown(false);
			player.setFaceLeft(false);
			player.setFaceRight(true);
		}
		
		if(e.getKeyCode() == KeyEvent.VK_L){
			for (int i = 0; i<getCustomCursor().getSelected().size(); i++){
				if (getCustomCursor().getSelected().get(i) instanceof Player){
					if (!((Player) getCustomCursor().getSelected().get(i)).isActive()){
						((Player) getCustomCursor().getSelected().get(i)).control();
					} else {
						((Player) getCustomCursor().getSelected().get(i)).stopTasks();
					}
				}
			}
		}
		
		// Swap backpack slots with eachother
		if (e.getKeyCode() == KeyEvent.VK_1){
			swapSlots(1);
		}
		if (e.getKeyCode() == KeyEvent.VK_2){
			swapSlots(2);
		}
		if (e.getKeyCode() == KeyEvent.VK_3){
			swapSlots(3);
		}
		if (e.getKeyCode() == KeyEvent.VK_4){
			swapSlots(4);
		}
		if (e.getKeyCode() == KeyEvent.VK_5){
			swapSlots(5);
		}
		if (e.getKeyCode() == KeyEvent.VK_6){
			swapSlots(6);
		}
		if (e.getKeyCode() == KeyEvent.VK_7){
			swapSlots(7);
		}
		if (e.getKeyCode() == KeyEvent.VK_8){
			swapSlots(8);
		}
		if (e.getKeyCode() == KeyEvent.VK_9){
			swapSlots(9);
		}
		if (e.getKeyCode() == KeyEvent.VK_0){
			swapSlots(10);
		}
		if (e.getKeyCode() == KeyEvent.VK_MINUS){
			swapSlots(11);
		}
		if (e.getKeyCode() == KeyEvent.VK_EQUALS){
			swapSlots(12);
		}
		if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE){
			swapSlots(13);
		}
		if (e.getKeyCode() == KeyEvent.VK_SHIFT){
			shift = true;
		}
		if (e.getKeyCode() == KeyEvent.VK_CONTROL){
			control = true;
		}
		if (e.getKeyCode() == KeyEvent.VK_R){
			dropItemButton(player.getToolBar().get(toolBarSelect));
		}
		if (e.getKeyCode() == KeyEvent.VK_E){
			if (player!=null && player.getToolBar().get(toolBarSelect)!=null && player.getToolBar().get(toolBarSelect) instanceof Food){
				player.eat((Food) player.getToolBar().get(toolBarSelect));
			}
		}
	}
	
	public void swapSlots(int slot){
		
		slot--;
		// Swap Slot Integers
		int slotO = -1;
		if (player.getToolBar().getContents()[slot]!=null){
			slotO=player.getToolBar().getContents()[slot].getSlot();
		}
		int slotN = 0;
		if(player.getToolBar().get(toolBarSelect)!=null){
			slotN=player.getToolBar().get(toolBarSelect).getSlot();
		}
		Item hold = player.getToolBar().getContents()[slot];
		player.getToolBar().getContents()[slot] = player.getToolBar().get(toolBarSelect);
		player.getToolBar().set(toolBarSelect, hold);
		
		if (player.getToolBar().get(toolBarSelect)!=null)
			player.getToolBar().get(toolBarSelect).setSlot(slotN);
		if (player.getToolBar().getContents()[slot] != null)
			player.getToolBar().getContents()[slot].setSlot(slotO);
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_ESCAPE){
			removeObjectByString("EscapeDraw");
			escape = false;
			task.cancel();
			task = new Timer();
		}
		if(e.getKeyCode() == KeyEvent.VK_W){
			wasd[0] = false;
			if (!wasd[1] && !wasd[2] && !wasd[3])
				player.setImage(new ImageIcon("Images\\Player\\HoodDIdle.gif"));
			player.setFaceUp(true);
			player.setFaceDown(false);
			player.setFaceLeft(false);
			player.setFaceRight(false);
		}
		if(e.getKeyCode() == KeyEvent.VK_A){
			wasd[1] = false;
			if (!wasd[3] && !wasd[2] && !wasd[0])
				player.setImage(new ImageIcon("Images\\Player\\HoodLIdle.png"));
			player.setFaceUp(false);
			player.setFaceDown(false);
			player.setFaceLeft(true);
			player.setFaceRight(false);
		}
		if(e.getKeyCode() == KeyEvent.VK_S){
			wasd[2] = false;
			if (!wasd[1] && !wasd[3] && !wasd[0])
				player.setImage(new ImageIcon("Images\\Player\\HoodDIdle.gif"));
			player.setFaceUp(false);
			player.setFaceDown(true);
			player.setFaceLeft(false);
			player.setFaceRight(false);
		}
		if(e.getKeyCode() == KeyEvent.VK_D){
			wasd[3] = false;
			if (!wasd[1] && !wasd[2] && !wasd[0])
				player.setImage(new ImageIcon("Images\\Player\\HoodRIdle.png"));
			player.setFaceUp(false);
			player.setFaceDown(false);
			player.setFaceLeft(false);
			player.setFaceRight(true);
		}
		if (e.getKeyCode() == KeyEvent.VK_SHIFT){
			shift = false;
		}
		if (e.getKeyCode() == KeyEvent.VK_CONTROL){
			control = false;
		}

	}
		
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	public ArrayList<Sprite> getSprites() {
		return sprites;
	}

	public void setSprites(ArrayList<Sprite> sprites) {
		this.sprites = sprites;
	}

	public boolean[] getWasd() {
		return wasd;
	}

	public void setWasd(boolean[] wasd) {
		this.wasd = wasd;
	}

	public boolean isEscape() {
		return escape;
	}

	public void setEscape(boolean escape) {
		this.escape = escape;
	}

	public JFrame getOwner() {
		return owner;
	}

	public void setOwner(JFrame owner) {
		this.owner = owner;
	}

	public Cursor getCustomCursor() {
		return cursor;
	}

	public void setCursor(Cursor cursor) {
		this.cursor = cursor;
	}

	public Timer getTask() {
		return task;
	}

	public void setTask(Timer task) {
		this.task = task;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public int getShiftx() {
		return shiftx;
	}

	public void setShiftx(int shiftx) {
		this.shiftx = shiftx;
	}

	public int getShifty() {
		return shifty;
	}

	public void setShifty(int shifty) {
		this.shifty = shifty;
	}

	public ArrayList<Sprite> getUIsprites() {
		return UIsprites;
	}

	public void setUIsprites(ArrayList<Sprite> uIsprites) {
		UIsprites = uIsprites;
	}

	public int getToolBarSelect() {
		return toolBarSelect;
	}
	
	public void moveToolBar(int dir){
		setToolBarSelect(dir);
	}

	public void setToolBarSelect(int tbs) {
		this.toolBarSelect+=tbs;
		System.out.println("YP: "+toolBarSelect);
		if (this.toolBarSelect <= player.getToolBar().getContents().length-1 && this.toolBarSelect>0){
		} else if (this.toolBarSelect < 0){
			this.toolBarSelect = player.getToolBar().getContents().length-1;
		} else if (this.toolBarSelect > player.getToolBar().getContents().length-1){
			this.toolBarSelect = 0;
		}
		System.out.println("YP: "+toolBarSelect);
		
		if (player.getToolBar().getContents()[toolBarSelect] instanceof Tool){
			player.setEquiped((Tool) player.getToolBar().getContents()[toolBarSelect]);
		} else {
			player.setEquiped(null);
		}
		
	}

	public boolean isShift() {
		return shift;
	}

	public void setShift(boolean shift) {
		this.shift = shift;
	}

	public boolean isControl() {
		return control;
	}

	public void setControl(boolean control) {
		this.control = control;
	}
	
	public void quickSort(Sprite array[]) {
		quickSort(array, 0, array.length - 1);              // quicksort all the elements in the array
	}


	public void quickSort(Sprite array[], int start, int end){
	        int i = start;                          // index of left-to-right scan
	        int k = end;                            // index of right-to-left scan

	        if (end - start >= 1){
	        	Sprite pivot = array[start];       // set the pivot as the first element in the partition

	                while (k > i) {
	                        while (array[i].getY() <= pivot.getY() && i <= end && k > i)  // from the left, look for the first
	                                i++;                                    // element greater than the pivot
	                        while (array[k].getY() > pivot.getY() && k >= start && k >= i) // from the right, look for the first
	                            k--;                                        // element not greater than the pivot
	                        if (k > i)                                       // if the left seekindex is still smaller than
	                                swap(array, i, k);                      // the right index, swap the corresponding elements
	                }
	                swap(array, start, k);          // after the indices have crossed, swap the last element in
	                                                // the left partition with the pivot 
	                quickSort(array, start, k - 1); // quicksort the left partition
	                quickSort(array, k + 1, end);   // quicksort the right partition
	        }
	        else {
	        		sprites = new ArrayList<Sprite>(Arrays.asList(array));
	                return;                     // the array is sorted, so exit
	        }
	}

	public void swap(Sprite[] array, int index1, int index2) 
	// pre: array is full and index1, index2 < array.length
	// post: the values at indices 1 and 2 have been swapped
	{
		Sprite temp = array[index1];           // store the first value in a temp
		array[index1] = array[index2];      // copy the value of the second into the first
		array[index2] = temp;               // copy the value of the temp into the second
	}
	
	public void drawRotatedImage(Graphics g2d, ImageIcon image, int x, int y){
		
		// The required drawing location
		int drawLocationX = x;
		int drawLocationY = y;

		// Rotation information
		
		BufferedImage bi = new BufferedImage(
				image.getIconWidth(),
				image.getIconHeight(),
			    BufferedImage.TYPE_INT_RGB);
			Graphics g = bi.createGraphics();
			// paint the Icon to the BufferedImage.
			image.paintIcon(null, g, 0,0);

		double rotationRequired = Math.toRadians (45);
		double locationX = image.getIconWidth() / 2;
		double locationY = image.getIconHeight() / 2;
		AffineTransform tx = AffineTransform.getRotateInstance(rotationRequired, locationX, locationY);
		AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
		tx.scale(2, 2);
		// Drawing the rotated image at the required drawing locations
		g2d.drawImage(op.filter(bi, null), drawLocationX, drawLocationY, null);
		
	}
	
	  	public ImageIcon rotatedImage(ImageIcon image, double degs) {
		      int width = image.getIconWidth();
		      int height = image.getIconHeight();
		      BufferedImage temp = new BufferedImage(height, width, BufferedImage.TYPE_INT_RGB);
		      Graphics2D g2 = temp.createGraphics();
		      g2.rotate(Math.toRadians(degs), height / 2, height / 2);
		      g2.drawImage(image.getImage(), 0, 0, Color.WHITE, null);
		      g2.dispose();
		      return new ImageIcon(temp);
	  }
	
	  	public static BufferedImage getBufferedImage(Image img){
	  	    if (img instanceof BufferedImage)
	  	    {
	  	       return (BufferedImage) img;
	  	    }

	  	    BufferedImage bimage = new BufferedImage(img.getWidth(null), 
	  	                    img.getHeight(null), BufferedImage.TYPE_INT_ARGB);

	  	    Graphics2D bGr = bimage.createGraphics();
	  	    bGr.drawImage(img, 0, 0, null);
	  	    bGr.dispose();

	  	    // Return the buffered image
	  	    return bimage;
	  	}
	
}
