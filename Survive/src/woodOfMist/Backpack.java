package woodOfMist;

public class Backpack {

	private Item[] contents;
	
	public Backpack(int size){
		contents = new Item[size];
	}
	
	public boolean addItem(Item item){
		for (int i = 0; i<contents.length; i++){
			if (contents[i]!=null && contents[i].getName()!=null)
			if (contents[i] instanceof Stackable && contents[i].getName().equals(item.getName())){
				((Stackable) contents[i]).setCount(((Stackable) contents[i]).getCount()+((Stackable) item).getCount());
				return true;
			}
		}
		if (getItemNumber()<=contents.length){
				int s =  getOpenSpace();
				this.contents[s] = item;
				this.contents[s].setSlot(s);
				return true;
		}
		return false;
	}
	
	public int getOpenSpace(){
		int index = -1;
		for (int i = 0; i<contents.length; i++){
			if (contents[i]==null){
				index = i;
				break;
			}
		}
		return index;
	}
	
	public int getItemNumber(){
		int c = 0;
		for (int i = 0; i<contents.length; i++){
			if (contents[i]!=null)
				c++;
		}
		return c;
	}
	
	public Item get(int i){
		if (i > -1 && i<contents.length){
			return contents[i];
		}
		return null;
	}
	
	public void set(int i, Item it){
		if (i > -1 && i<contents.length){
			contents[i] = it;
		}
	}

	public Item[] getContents() {
		return contents;
	}

	public void setContents(Item[] contents) {
		this.contents = contents;
	}

}
