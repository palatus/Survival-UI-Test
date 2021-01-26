package woodOfMist;

import javax.swing.ImageIcon;

public class Item{

	private int slot;
	private String Name;
	private ImageIcon icon;

	public void spawn(int x, int y, Window w){
		@SuppressWarnings("unused")
		WorldItem spawnItem = new WorldItem(x,y,w,this);
	}
	
	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public ImageIcon getIcon() {
		return icon;
	}

	public void setIcon(ImageIcon icon) {
		this.icon = icon;
	}
	
	public Item Clone(){
		Item cl = new Item();
		cl.setName(getName());
		cl.setIcon(icon);
		return cl;
	}

	public int getSlot() {
		return slot;
	}

	public void setSlot(int slot) {
		this.slot = slot;
	}
	
}
