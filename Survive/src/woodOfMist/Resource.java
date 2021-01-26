package woodOfMist;

import javax.swing.ImageIcon;

public class Resource extends Stackable{
	
	public Resource(String name,String url){
		this.setName(name);
		this.setIcon(new ImageIcon(url));
		this.setCount(1);
	}
	
	public Resource(String name,String url, int c){
		this(name,url);
		this.setCount(c);
	}
	
	public Resource Clone(){
		Resource cl = new Resource(getName(),"");
		cl.setIcon(getIcon());
		return cl;
	}
	
}
