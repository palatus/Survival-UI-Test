package woodOfMist;

import java.text.DecimalFormat;

import javax.swing.ImageIcon;

public abstract class Equipable extends Item{

	private ImageIcon rot45, flipped45;
	
	private double durability, maxDurability;

	public double getDurabilityPercent(){
		return (durability/maxDurability)*100;
	}
	
	public String getDurableString(){
		return new DecimalFormat("#").format(getDurabilityPercent())+" %";
	}
	
	public double getDurability() {
		return durability;
	}

	public void setDurability(double durability) {
		this.durability = durability;
	}

	public double getMaxDurability() {
		return maxDurability;
	}

	public void setMaxDurability(double maxDurability) {
		this.maxDurability = maxDurability;
	}

	public ImageIcon getRot45() {
		return rot45;
	}

	public void setRot45(ImageIcon rot45) {
		this.rot45 = rot45;
	}

	public ImageIcon getFlipped45() {
		return flipped45;
	}

	public void setFlipped45(ImageIcon flipped45) {
		this.flipped45 = flipped45;
	}
	
	
	
}
