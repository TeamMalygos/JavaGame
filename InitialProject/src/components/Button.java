package components;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import components.interfaces.Clickable;
import events.MenuButtonClickEvent;


import states.State;

public class Button extends MenuComponent implements Clickable{
	
	private BufferedImage pressed;
	private BufferedImage normal;
	private BufferedImage hover;
	private State stateInit;
	
	//private BufferedImage normalReflection;
	private float reflectionOpacity;
	private float fadeHeight;
	private int gap;
	
	//private GradientPaint paint;
	//private AlphaComposite composite;
	
	public Button(int x,int y){
		super(x,y);
		
	}
	
	public float getReflectionOpacity() {
		return reflectionOpacity;
	}

	public void setReflectionOpacity(float reflectionOpacity) {
		this.reflectionOpacity = reflectionOpacity;
	}

	public float getFadeHeight() {
		return fadeHeight;
	}

	public void setFadeHeight(float fadeHeight) {
		this.fadeHeight = fadeHeight;
	}

	public int getGap() {
		return gap;
	}

	public void setGap(int gap) {
		this.gap = gap;
	}

	public void setReflectionProperties(float opacity,float fadeHeight,int gap){
		
		this.setReflectionOpacity(opacity);
		this.setFadeHeight(fadeHeight);
		this.setGap(gap);
		
	}
	
	public void setFrames(BufferedImage frames){
		super.setFrames(frames);
		loadFrameHolders();
		//createReflection();
	}
	
	/*
	private void createReflection(){
		
		this.normalReflection = new BufferedImage(this.normal.getWidth(),this.normal.getHeight(),BufferedImage.TYPE_INT_ARGB);
		this.composite = AlphaComposite.getInstance(AlphaComposite.DST_IN);
		this.paint = new GradientPaint(0, this.normal.getHeight() * fadeHeight, new Color(0.0f, 0.0f, 0.0f, 0.0f),
		          0, this.normal.getHeight(), new Color(0.0f, 0.0f, 0.0f, this.reflectionOpacity));
		
	}
	*/
	private void loadFrameHolders(){
		
		normal = super.getFrames().getSubimage(0,0,super.getWidth(),super.getHeight());
		pressed = super.getFrames().getSubimage(super.getWidth(),0,super.getWidth(),super.getHeight());
		hover = super.getFrames().getSubimage(super.getWidth() * 2, 0, super.getWidth(), super.getHeight());
		super.setCurrentFrame(normal);
	}
	
	
	public void linkToState(State state){
		stateInit = state;
	}
	
	public int getStateId(){
		
		if(this.stateInit == null){
			return -1;
		}
		
		return this.stateInit.getID();
	}
	
	@Override
	public void tick(){

		if(super.isHover()){
			super.setCurrentFrame(this.hover);
		}else if(super.isPressed()){
			super.setCurrentFrame(this.pressed);
		}else {
			super.setCurrentFrame(this.normal);
		}
	}
	
	@Override
	public void render(Graphics g){
		//Graphics2D g2d = (Graphics2D)g;
		g.drawImage(super.getCurrentFrame(), super.getxAxisPosition()
				, super.getyAxisPosition(),null);
		//Drawing reflection
		/*
			g2d.setPaint( new GradientPaint( 0, 0, Color.black, 0, Constants.HEIGHT, Color.darkGray ) );
			g2d.fillRect( 0, 0, Constants.WIDTH, Constants.HEIGHT);
	
			g2d.translate(this.getxAxisPosition(),this.getyAxisPosition());
			
			g2d.drawRenderedImage(this.currentFrame,null);
					
			g2d.translate(0 , this.gap + this.normal.getHeight() * 2);
			g2d.scale(1, -1);
			Graphics2D rg = this.normalReflection.createGraphics();
			rg.drawRenderedImage(this.normal,null);
			rg.setComposite(this.composite);
			rg.setPaint(this.paint);
			rg.fillRect(0, 0, this.getWidth(), this.getHeight());
			rg.dispose();
			
			g2d.drawImage(this.normalReflection,0,0, null);	
		*/
	}

	@Override
	public void onMenuButtonClick(int mouseX, int mouseY) {
		if(this.isInside(mouseX, mouseY)){
			super.setPressed(true);
			super.setHover(false);
		}else{
			super.setHover(false);
			super.setPressed(false);
		}
	}

	@Override
	public void onMenuButtonRelease(int mouseX, int mouseY) {
		
		if(!this.isInside(mouseX, mouseY)){
			this.setPressed(false);
			this.setHover(false);
			return;
		}
		
		if(this.stateInit != null){
			new MenuButtonClickEvent(this,stateInit);
		}
	}

	@Override
	public void onMenuButtonHover(int mouseX, int mouseY) {
		
		if(!this.isInside(mouseX, mouseY)){
			this.setHover(false);
			return;
		}
		
		super.setPressed(false);
		super.setHover(true);
	}

}
