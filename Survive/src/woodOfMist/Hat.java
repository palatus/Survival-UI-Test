package woodOfMist;

import javax.swing.ImageIcon;

public class Hat extends Apparel{

	public Hat(ImageIcon u,ImageIcon d,ImageIcon l,ImageIcon r){
		this.setUp(u);
		this.setDown(d);
		this.setLeft(l);
		this.setRight(r);
		this.setIcon(d);
	}
	
	public Hat Clone(){
		Hat cl = new Hat(getUp(),getDown(),getLeft(),getRight());
		cl.setName(getName());
		cl.setIcon(getIcon());
		return cl;
	}
	
}
