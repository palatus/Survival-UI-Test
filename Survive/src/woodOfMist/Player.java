package woodOfMist;

import java.util.TimerTask;

import javax.swing.ImageIcon;

public class Player extends Sprite{

	private double speed, hunger, maxHunger, health, maxHealth;
	private boolean active, faceUp, faceDown, faceLeft, faceRight;
	private Tool equiped;
	private Backpack toolBar = new Backpack(15);
	private Hat hat;
	
	public Player(int x, int y, String i, Window w){
		super(x,y,i,w);
		this.health=50;
		this.maxHealth=100;
		this.hunger=50;
		this.maxHunger=100;
		speed = 5.5;
	}

	public void control(){
		active = true;
		getTask().schedule(new TimerTask(){
			@Override
			public void run() {
				setHunger(getHunger()-0.002);
				
				if (getOwner().getWasd()[0] && !getOwner().getWasd()[1] && !getOwner().getWasd()[3] && !getOwner().getWasd()[2]){
					setImage(new ImageIcon("Images\\Player\\HoodDIdle.png"));
				} else if (getOwner().getWasd()[1] && !getOwner().getWasd()[0] && !getOwner().getWasd()[3] && !getOwner().getWasd()[2]){
					setImage(new ImageIcon("Images\\Player\\HoodLAnim.gif"));
				} else if (getOwner().getWasd()[2] && !getOwner().getWasd()[0] && !getOwner().getWasd()[3] && !getOwner().getWasd()[1]){
					setImage(new ImageIcon("Images\\Player\\HoodDAnim.gif"));
				}else if (getOwner().getWasd()[3] && !getOwner().getWasd()[0] && !getOwner().getWasd()[2] && !getOwner().getWasd()[1]){
					setImage(new ImageIcon("Images\\Player\\HoodRAnim.gif"));
				}
				if (getOwner().getWasd()[0] && !getOwner().getWasd()[2]){
					setY(getY()-speed);
					setHealth(health-0.5);
				}
				if (getOwner().getWasd()[1] && !getOwner().getWasd()[3]){
					setX(getX()-speed);
				}
				if (getOwner().getWasd()[2] && !getOwner().getWasd()[0]){
					setY(getY()+speed);
					setHealth(health+0.5);
				}
				if (getOwner().getWasd()[3] && !getOwner().getWasd()[1]){
					setX(getX()+speed);
				}
			}
		}, 15,15);
	}
	
	public void stopTasks(){
		setActive(false);
		super.stopTasks();
	}

	public void removeItemCount(Item item, int c, int s){

		if (this.getToolBar().getContents()[s] instanceof Stackable){
			int count = ((Stackable) this.getToolBar().getContents()[s]).getCount();
			System.err.println(count);
			((Stackable) this.getToolBar().getContents()[s]).setCount(count-c);
		
			if (((Stackable) this.getToolBar().getContents()[s]).getCount()<1){
				this.getToolBar().getContents()[s] = null;	
			}
		} else {
			this.getToolBar().getContents()[s] = null;
			System.err.println("This aint no stackable");
		}
		
	}
	
	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public Backpack getToolBar() {
		return toolBar;
	}

	public void setToolBar(Backpack toolBar) {
		this.toolBar = toolBar;
	}

	public double getSpeed() {
		return speed;
	}

	public void setSpeed(double speed) {
		this.speed = speed;
	}

	public double getHunger() {
		return hunger;
	}

	public void setHunger(double hunger) {
		this.hunger = hunger;
		
		if (this.hunger>maxHunger){
			this.hunger=maxHunger;
		}
		if (this.hunger<0){
			this.hunger=0;
		}
		
	}

	public double getMaxHunger() {
		return maxHunger;
	}

	public void setMaxHunger(double maxHunger) {
		this.maxHunger = maxHunger;
	}

	public double getHealth() {
		return health;
	}
	
	public void eat(Food f){
		this.setHunger(this.hunger+f.getNutrition());
		this.heal(f.getNutrition()/5);
		removeItemCount(f, 1, f.getSlot());
		System.out.println("YUM");
	}

	public void heal(double h){
		this.setHealth(health+h);
	}
	
	public void setHealth(double health) {
		
		this.health = health;
		
		if (this.health>maxHealth){
			this.health=maxHealth;
		}
		if (this.health<0){
			this.health=0;
		}
		
	}

	public double getMaxHealth() {
		return maxHealth;
	}

	public void setMaxHealth(double maxHealth) {
		this.maxHealth = maxHealth;
	}

	public Tool getEquiped() {
		return equiped;
	}

	public void setEquiped(Tool equiped) {
		this.equiped = equiped;
	}

	public boolean isFaceUp() {
		return faceUp;
	}

	public void setFaceUp(boolean faceUp) {
		this.faceUp = faceUp;
	}

	public boolean isFaceDown() {
		return faceDown;
	}

	public void setFaceDown(boolean faceDown) {
		this.faceDown = faceDown;
	}

	public boolean isFaceLeft() {
		return faceLeft;
	}

	public void setFaceLeft(boolean faceLeft) {
		this.faceLeft = faceLeft;
	}

	public boolean isFaceRight() {
		return faceRight;
	}

	public void setFaceRight(boolean faceRight) {
		this.faceRight = faceRight;
	}

	public Hat getHat() {
		return hat;
	}

	public void setHat(Hat hat) {
		this.hat = hat;
	}
	
	
	
}
