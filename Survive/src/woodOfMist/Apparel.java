package woodOfMist;

import javax.swing.ImageIcon;

public abstract class Apparel extends Item{

	private ImageIcon up,down,left,right;

	public ImageIcon getUp() {
		return up;
	}

	public void setUp(ImageIcon up) {
		this.up = up;
	}

	public ImageIcon getDown() {
		return down;
	}

	public void setDown(ImageIcon down) {
		this.down = down;
	}

	public ImageIcon getLeft() {
		return left;
	}

	public void setLeft(ImageIcon left) {
		this.left = left;
	}

	public ImageIcon getRight() {
		return right;
	}

	public void setRight(ImageIcon right) {
		this.right = right;
	}
	
	
	
}
