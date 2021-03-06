package states;

import java.awt.*;

import gfx.Assets;

public class MenuState extends State{
 
	private Menu startMenu;

	private final static int STATE_ID = 1;
	
	public MenuState() {
		super(STATE_ID);
		
		Assets.init();
		
    	startMenu = new Menu();
    	startMenu.init();
	}

	public Menu getMenu(){
		return startMenu;
	}
	
    @Override
    public void tick() {
    	
    	startMenu.tick();
    }

    @Override
    public void render(Graphics g) {
    	g.drawImage(Assets.background, 0, 0, null);
    	startMenu.render(g);
    }
}
