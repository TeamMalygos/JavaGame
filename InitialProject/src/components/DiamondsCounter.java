package components;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import constants.Constants;
import gfx.Assets;

public class DiamondsCounter extends HUDComponent{
	
	private String count;
	
	public DiamondsCounter(String name){
		super(Constants.DIAMONDS_COUNTER_X, Constants.DIAMONDS_COUNTER_Y, name);
		count = "0";
		
		super.setSize(Constants.DIAMONDS_COUTNER_WIDTH, Constants.DIAMONDS_COUNTER_HEIGHT);
		super.loadSprite(Assets.diamondsCounter, new int[] { 1 });
		
	}
	
	@Override
	public void render(Graphics g) {
		
		g.drawImage(super.frames[0], super.xAxisPosition, super.yAxisPosition, null);
		g.setColor(Color.WHITE);
		g.drawString(count, super.width/2, super.yAxisPosition + 21);
		
	}

	@Override
	public void tick() {
		this.count = super.player.getBag().getDiamondsCount() + "";
	}
	
}