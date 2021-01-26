package woodOfMist;

import java.awt.MouseInfo;
import java.awt.Point;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.ImageIcon;

public class Sprite {

	private double x, y, width, height;
	private String tag, displayString;
	private ImageIcon image;
	private Timer task = new Timer();
	private Window Owner;
	
	public Sprite(){
		x=0;
		y=0;
		width=0;
		height=0;
		generateTag();
	}
	
	public void generateTag(){
		this.setTag("SpecialID: "+this.toString());
	}
	
	public Sprite(int x, int y, int w, int h){
		this.x = x;
		this.y = y;
		this.setWidth(w);
		this.setHeight(h);
		generateTag();
	}
	
	public Sprite(int x, int y, ImageIcon i){
		this(x,y,i.getIconWidth(),i.getIconHeight());
		this.setImage(i);
	}
	
	public Sprite(int x, int y, String i){
		this(x,y,new ImageIcon(i));
	}
	
	public Sprite(int x, int y, String i, Window w){
		this(x,y,new ImageIcon(i));
		this.setOwner(w);
	}
	
	public void followCursor(){
		task.schedule(new TimerTask(){
			@Override
			public void run() {
				moveTo(MouseInfo.getPointerInfo().getLocation().x-Owner.getShiftx(),MouseInfo.getPointerInfo().getLocation().y-Owner.getShifty());
			}
		}, 10,10);
	}
	
	public void stopTasks(){
		task.cancel();
		task = new Timer();
	}
	
	public void jumpToCursor(){
		moveTo(MouseInfo.getPointerInfo().getLocation().x,MouseInfo.getPointerInfo().getLocation().y);
	}
	
	public void moveTo(int x, int y){
		this.setX(x-getWidth()/2);
		this.setY(y-getHeight()/2);
	}
	
	public Point getCenter(){
		return (new Point((int)getX()-(int)getWidth()/2,(int)getY()-(int)getHeight()/2));
	}
	
	public Point getTopRight(){
		return (new Point((int)getX()+(int)getWidth(),(int)getY()));
	}
	
	public Point getBottomRight(){
		return (new Point((int)getX()+(int)getWidth(),(int)getY()+(int)getHeight()));
	}
	
	public Point getBottomLeft(){
		return (new Point((int)getX(),(int)getY()+(int)getHeight()));
	}
	
	public Point getTopLeft(){
		return (new Point((int)getX(),(int)getY()));
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public ImageIcon getImage() {
		return image;
	}

	public void setImage(ImageIcon image) {
		this.image = image;
	}

	public double getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public double getHeight() {
		return height;
	}

	public void setHeight(double height) {
		this.height = height;
	}

	public Timer getTask() {
		return task;
	}

	public void setTask(Timer task) {
		this.task = task;
	}

	public Window getOwner() {
		return Owner;
	}

	public void setOwner(Window owner) {
		Owner = owner;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public String getDisplayString() {
		return displayString;
	}

	public void setDisplayString(String displayString) {
		this.displayString = displayString;
	}
	
}
