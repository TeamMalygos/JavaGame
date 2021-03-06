package game.entities;


import gfx.Assets;
import gfx.SpriteSheet;
import map.TileMap;
import states.GameState;
import utils.PVector;

import java.awt.*;
import java.util.HashSet;
import java.util.Set;

public class EnemyShootingUnit extends MapObject implements UnitDrawable {

    private String name;
    private int width, height, velocityX, velocityY, health, initialX, movementRange, shootingRange, damage;

    private String direction;

    private SpriteSheet enemyImage;
    private Rectangle boundingBox;

    private int col;

    private int shootingTimer;
    private Set<Projectile> projectiles;

    private int experienceAwardedOnKill;
    private boolean isDead;


    public EnemyShootingUnit(String name, int width, int height, int x, int y, int shootingRange, int damage, int health,TileMap map) {
    	super(map);
    	
    	super.position = new PVector(x,y);
    	
        this.name = name;
        
        super.width = width;
        super.height = height;

        this.shootingRange = shootingRange;
        this.damage = damage;

        this.velocityX = this.velocityY = 4;
        this.health = health;

        this.boundingBox = new Rectangle((int)super.position.getPositionX()
        		, (int)super.position.getPositionY()
        		, super.width, super.height);
        //this.enemyImage = new SpriteSheet(Assets.enemy, width, height);
        this.col = 0;

        this.direction = "left";
        this.initialX = x;
        this.movementRange = this.velocityX * 20;

        this.shootingTimer = 0;
        this.projectiles = new HashSet<>();

        this.experienceAwardedOnKill = 40;
        this.isDead = false;

    }

    @Override
    public void tick() {
        // Check if enemy is alive
        if (isDead) {
            return;
        }

        Player currentPlayer = GameState.getPlayer();

        if (this.health <= 0) {
            currentPlayer.gainExperience(this.experienceAwardedOnKill);
            GameState.setPlayer(currentPlayer);
            this.isDead = true;

            System.out.println("Enemy " + this.name + " killed! " + experienceAwardedOnKill + " experience awarded!");

            return;
        }

        // See if player is in range. If yes - stop and fire at him.
        if (isPlayerInRange(GameState.getPlayer())) {
            if (shootingTimer <= 0) {
               // Projectile projectile = new Projectile(13, 30, (int)super.position.getPositionX(), (int)super.position.getPositionY(), this);
                //projectiles.add(projectile);
                shootingTimer = 30;
            } else {
                shootingTimer--;
            }

            // If no player is in range the Unit moves left and right around it's spawn point.
        } else {
            col++;
            if (col >= 8) {
                col = 0;
            }

            if (direction.equals("left")) {
                super.position.setPositionX(super.position.getPositionX() - this.velocityX);

                if (super.position.getPositionX() <= initialX - movementRange) {
                    direction = "right";
                }

            } else if (direction.equals("right")) {
                super.position.setPositionX(super.position.getPositionX() + this.velocityX);

                if (super.position.getPositionX() >= initialX + movementRange) {
                    direction = "left";
                }
            }

            this.boundingBox.setBounds((int)super.position.getPositionX()
            		,(int) super.position.getPositionY(), this.width, this.height);
        }

        // If there are any projectiles fired call their tick().
        for (Projectile projectile : projectiles) {
            projectile.tick();
        }
    }

    @Override
    public void render(Graphics graphics) {

        // Check if enemy is alive
        if (isDead) {
            return;
        }

        // See if player is in range. If yes - render static graphics.
        //if (isPlayerInRange(GameState.getPlayer())) {
            //graphics.drawImage(this.enemyImage.crop(1, 3), this.x, this.y, null);
        //} else {
           // if (direction.equals("left")) {
            //    graphics.drawImage(this.enemyImage.crop(1, col), this.x, this.y, null);
            //} else {
       //         graphics.drawImage(this.enemyImage.crop(2, col), this.x, this.y, null);
            //}
     //   }

        // If there are any projectiles fired call their render().
        for (Projectile projectile : projectiles) {
            projectile.render(graphics);
        }
    }

    public boolean isPlayerInRange(Player player) {
        return super.position.getPositionX() - this.shootingRange <= player.getBoundingBox().getMaxX();
    }

    public int getX() {
        return (int)super.position.getPositionX();
    }

    public void setX(int x) {
        super.position.setPositionX(x);
    }

    public int getY() {
        return (int)super.position.getPositionY();
    }

    public void setY(int y) {
        super.position.setPositionY(y);
    }

    public int getShootingRange() {
        return shootingRange;
    }

    public void setShootingRange(int shootingRange) {
        this.shootingRange = shootingRange;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public Set<Projectile> getProjectiles() {
        return projectiles;
    }

    public void setProjectiles(Set<Projectile> projectiles) {
        this.projectiles = projectiles;
    }
}
