package game.entities;

import java.awt.Rectangle;

import map.TileMap;
import utils.Animation;
import utils.CollisionBox;
import utils.MovementAttributes;
import utils.MovementState;
import utils.PVector;

/**
 * 
 * @author G_ANGELOV
 * </p><em>MapObject<em> is the base class for every object in the game
 * excluding the Tile Map and the other prerequisites and resources
 * we have. It is the link between the entities and their movements
 * and actions</p>
 *
 */
public abstract class MapObject {
	
	protected TileMap tileMap;
	protected int tileSize;
	
	protected PVector position;
	
	protected CollisionBox cBox;
	protected Animation animation;
	
	protected MovementState movementState;
	protected MovementAttributes objectMovementAttr;
	
	protected MapObject(TileMap map){
		tileMap = map;
		tileSize = map.getTileSize();
	}
	
	/**
	 * 
	 * This method return a boolean indicating
	 * weather this object is in collision with
	 * the argument.
	 * 
	 * @param MapObject o1
	 * @return boolean isInCollisionWith
	 */
	protected boolean intersectsWith(MapObject o){
		return this.getObjectRectangle().intersects(o.getObjectRectangle());
	}

	private Rectangle getObjectRectangle(){
		return new Rectangle(
				(int)position.getPositionX() - cBox.getCollisionWidth(),
				(int)position.getPositionY() - cBox.getCollisionHeight(),
				cBox.getCollisionWidth(),
				cBox.getCollisionHeight());
		
	}
}