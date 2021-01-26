package woodOfMist;

import javax.swing.ImageIcon;

public class Food extends Stackable{

	private double nutrition;
	private boolean poisoned;
	
	public Food(double nut, String name){
		this.nutrition=nut;
		this.setCount(1);
		this.setName(name);
		this.setIcon(new ImageIcon("Images\\drumstick.png"));
	}
	
	public Food(double nut, String name, boolean p){
		this(nut,name);
		this.poisoned=p;
	}

	public double getNutrition() {
		return nutrition;
	}

	public void setNutrition(double nutrition) {
		this.nutrition = nutrition;
	}

	public boolean isPoisoned() {
		return poisoned;
	}

	public void setPoisoned(boolean poisoned) {
		this.poisoned = poisoned;
	}
	
	public Food Clone(){
		Food cl = new Food(this.nutrition,getName());
		cl.setCount(getCount());
		cl.setName(getName());
		cl.setIcon(getIcon());
		cl.setPoisoned(poisoned);
		return cl;
	}
	
}
