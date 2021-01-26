package woodOfMist;

import java.awt.Image;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class Cursor{

	private String imageURL;
	private Timer task = new Timer();
	private ArrayList<Sprite> selected = new ArrayList<Sprite>();
	private boolean mouseDown, canDragBox;
	private Point clickPoint;
	
	// Animated Variables
	private ArrayList<ImageIcon> frames = new ArrayList<ImageIcon>();
	private ScheduledExecutorService thread = Executors.newSingleThreadScheduledExecutor();
	private int currentFrame = -1;
	private boolean loopBackwards = false, goback = false;
	
	public Cursor(String url, JFrame f){
		this.setImageURL(url);
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Image image = toolkit.getImage(imageURL);
		java.awt.Cursor c = toolkit.createCustomCursor(image , new Point(f.getX(), f.getY()), "png");
		f.setCursor (c);
	}
	
	public Cursor(File gif, JFrame f){
		
		ImageReader reader = ImageIO.getImageReadersBySuffix("GIF").next();
		ImageInputStream in;
		try {
			in = ImageIO.createImageInputStream(gif);
			reader.setInput(in);
			for (int i = 0, count = reader.getNumImages(true); i < count; i++){
			    BufferedImage image = reader.read(i);
			    ImageIO.write(image, "PNG", new File("output" + i + ".png"));
			    frames.add(new ImageIcon("output" + i + ".png"));
			    File del = new File("output" + i + ".png");
			    del.delete(); // remove temporary file
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		enableCursor(f);
		
	}

	public void enableCursor(JFrame frame){
		thread = Executors.newSingleThreadScheduledExecutor(); // re-initialize thread
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		java.awt.Cursor[] curall = new java.awt.Cursor[7];
		for (int i = 1; i<7; i++){
			java.awt.Cursor curs = toolkit.createCustomCursor(new ImageIcon("Images\\cursor\\"+i+".png").getImage() , new Point(frame.getX(),frame.getY()), "img");
			curall[i-1] = curs;
		}
		thread.scheduleWithFixedDelay(new Runnable(){
			@Override
			public void run() {
				
				if (!loopBackwards){
					currentFrame++;
					if (currentFrame==frames.size()-1){
						currentFrame=0;
					}
				} else {
					if ( !goback && currentFrame < frames.size()-1){
						currentFrame++;
					} else if (!goback && currentFrame==frames.size()-1){
						goback = true;
						currentFrame--;
					} else if (goback && currentFrame>0){
						currentFrame--;
						if (currentFrame == 0)
						goback = false;

					} 
				}
				frame.setCursor(curall[currentFrame]);
			}	
		}, 5, 150, TimeUnit.MILLISECONDS);
	}
	
	
	public Point getLocation(){
		return MouseInfo.getPointerInfo().getLocation();
	}

	public String getImageURL() {
		return imageURL;
	}

	public void setImageURL(String imageURL) {
		this.imageURL = imageURL;
	}

	public Timer getTask() {
		return task;
	}

	public void setTask(Timer task) {
		this.task = task;
	}

	public boolean isMouseDown() {
		return mouseDown;
	}

	public void setMouseDown(boolean mouseDown) {
		this.mouseDown = mouseDown;
	}

	public Point getClickPoint() {
		return clickPoint;
	}

	public void setClickPoint(Point clickPoint) {
		this.clickPoint = clickPoint;
	}

	public ArrayList<ImageIcon> getFrames() {
		return frames;
	}

	public void setFrames(ArrayList<ImageIcon> frames) {
		this.frames = frames;
	}

	public ScheduledExecutorService getThread() {
		return thread;
	}

	public void setThread(ScheduledExecutorService thread) {
		this.thread = thread;
	}

	public int getCurrentFrame() {
		return currentFrame;
	}

	public void setCurrentFrame(int currentFrame) {
		this.currentFrame = currentFrame;
	}

	public boolean isLoopBackwards() {
		return loopBackwards;
	}

	public void setLoopBackwards(boolean loopBackwards) {
		this.loopBackwards = loopBackwards;
	}

	public boolean isGoback() {
		return goback;
	}

	public void setGoback(boolean goback) {
		this.goback = goback;
	}

	public boolean isCanDragBox() {
		return canDragBox;
	}

	public void setCanDragBox(boolean canDragBox) {
		this.canDragBox = canDragBox;
	}

	public ArrayList<Sprite> getSelected() {
		return selected;
	}

	public void setSelected(ArrayList<Sprite> selected) {
		this.selected = selected;
	}
	
}
