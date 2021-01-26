package woodOfMist;

import javax.swing.ImageIcon;

public class Tool extends Equipable{
	
	private double strength;
	
	public Tool(double str , double dur){
		this.setStrength(str);
		this.setDurability(dur);
		this.setMaxDurability(dur);
	}
	
	public Tool(double str , double dur, String icon){
		this(str,dur);
		this.setIcon(new ImageIcon(icon));
	}

	public double getStrength() {
		return strength;
	}

	public void setStrength(double strength) {
		this.strength = strength;
	}
	
	public Item Clone(){
		Tool cl = new Tool(getStrength(),getDurability());
		cl.setName(getName());
		cl.setIcon(getIcon());
		cl.setFlipped45(getFlipped45());
		cl.setRot45(getRot45());
		return cl;
	}
	
}
