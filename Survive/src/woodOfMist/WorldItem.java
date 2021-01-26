package woodOfMist;

public class WorldItem extends Sprite{

	private Item item;
	
	public WorldItem(int x, int y, Window w, Item it){
		super(x,y,it.getIcon()); // icon images must be 32x32
		item = it;
		this.setOwner(w);
		this.setImage(item.getIcon());
		w.addObject(this);
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}
	
}
