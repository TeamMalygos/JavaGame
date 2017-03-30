package states;

import java.awt.Graphics;

import components.DiamondsCounter;
import components.HealthBar;
import constants.Constants;
import game.entities.Player;
import game.entities.Drawable;

public class InGameHUD implements Drawable{

	private HealthBar bar;
	private DiamondsCounter counter;
	private Player p;

	public InGameHUD(Player p){
		this.p = p;
		init();
	}
	
	private void init(){
		
		this.bar = new HealthBar(Constants.HEALTH_BAR_X
				,Constants.HEALTH_BAR_Y);
		this.bar.linkToPlayer(p);
		
		this.counter = new DiamondsCounter("Diamonds Counter");
		this.counter.linkToPlayer(p);
		
	}
	
	
	@Override
	public void tick() {
		this.bar.tick();
		this.counter.tick();
	}

	@Override
	public void render(Graphics g) {
		this.bar.render(g);
		this.counter.render(g);
		
	}

}
