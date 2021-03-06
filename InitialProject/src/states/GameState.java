package states;

import game.entities.EnemyMeleeUnit;
import game.entities.EnemyShootingUnit;
import game.entities.Player;
import game.entities.playerProperties.Stats;
import gfx.Assets;
import map.ObjectLayer;
import map.TileMap;
import map.parser.LevelLoader;
import utils.Level;

import java.awt.*;
import java.io.*;

import constants.Constants;

public class GameState extends State {
    private static final int GRAVITY = 2;
    private final static int ID = 2;
    
    private static TileMap map;
    private InGameMenu menu;
    private InGameHUD hud;
    private ObjectLayer objects;
    
    private boolean isRunning;
    private boolean isMenuOpen;
    
    private Level level;
    public static Player player;
    public static EnemyShootingUnit firstEnemyShootingUnit;
    public static EnemyMeleeUnit firstMeleeEnemy;

    public GameState(Level level) {
    	super(ID);
    	this.level = level;
    	isRunning = false;
    }

    private void init() throws IOException {
        Assets.init();
      
        LevelLoader loader = new LevelLoader(this.level);

        map = new TileMap(loader.getLevelData().getTileLayer().getData()
        		,loader.getLevelData().getTileLayer().getHeight()
        		,loader.getLevelData().getTileLayer().getWidth());
        
        map.loadTiles("/textures/Sheet.png");
        map.setPosition(0, 0);

        objects = new ObjectLayer(loader.getLevelData().getObjectsLayer());
        this.objects.setSecondaryTileLayer(loader.getLevelData().getLootLayer());
        this.objects.setOffset(loader.getLevelData().getTileLayer().getOffsetX()
        		,loader.getLevelData().getTileLayer().getOffsetY());
        
        menu = new InGameMenu();

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        loadOrStartNewGame(reader);

        //firstEnemyShootingUnit = new EnemyShootingUnit("NekvaPachaSLesenSpriteSheet", 60, 60, 650, 450, 150, 50, 250);
        //firstMeleeEnemy = new EnemyMeleeUnit("Melee", 100, 134, 450, 400, 5, 35, 700);
        
    }

    private void loadOrStartNewGame(BufferedReader reader) throws IOException {
        System.out.println("Start new game or load player?");
        System.out.print("new/load:");
        String userChoice = reader.readLine();
        switch (userChoice) {
            case "new":
                createNewPlayer(reader);
                break;
            case "load":
                loadPlayer(reader);
                break;
            default:
                System.out.println("Unrecognized command.");
                loadOrStartNewGame(reader);
                break;
        }
    }

    private void loadPlayer(BufferedReader reader) throws IOException {
        String playerName;
        System.out.println("Load character.");
        System.out.print("Player name: ");
        playerName = reader.readLine();
        String playerFilePath = System.getProperty("user.dir") + "/resources/players/" + playerName + ".ser";
        File playerFile = new File (playerFilePath);
        if (playerFile.exists()) {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(playerFile));
            try {
                Stats loadedStats = (Stats) ois.readObject();
                player = new Player(loadedStats.getPlayerName(), map, loadedStats);
                hud = new InGameHUD(player);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Player with such name does not exist.");
            loadPlayer(reader);
        }
    }

    private void createNewPlayer(BufferedReader reader) throws IOException {
        String playerName;
        System.out.println("Creating new character.");
        System.out.print("Player name: ");
        playerName = reader.readLine();
        String playerFilePath = "/players/" + playerName + ".ser";
        File playerFile = new File (playerFilePath);
        if (playerFile.exists()) {
            System.out.println("Player with such name already exists. Please choose new one.");
            createNewPlayer(reader);
        } else {
            player = new Player(playerName,map);
            hud = new InGameHUD(player);
        }
    }


    @Override
    public void tick() {
    	if(!isRunning){
            try {
                init();
            } catch (IOException e) {
                e.printStackTrace();
            }
            isRunning = true;
    	}
   
        player.tick();
    	map.setPosition(Constants.WIDTH / 2 - this.player.getX()
    			, Constants.HEIGHT / 2 - this.player.getY());
    	
    	objects.tick();
   
        hud.tick();
        //firstEnemyShootingUnit.tick();
        //firstMeleeEnemy.tick();
        if(this.isMenuOpen){
        	menu.tick();
        }
    }

    @Override
    public void render(Graphics g) {
    	map.draw(g);
    	objects.render(g);
        player.render(g);
        hud.render(g);
        //firstEnemyShootingUnit.render(g);
        //firstMeleeEnemy.render(g);
        if(this.isMenuOpen){
        	menu.render(g);;
        }
    }

    public static int getGRAVITY() {
        return GRAVITY;
    }

    public static Player getPlayer() {
        return player;
    }
    public static TileMap getMap(){
    	return map;
    }
    public boolean isInMenuState(){
    	return this.isMenuOpen;
    }
    public void toggleMenu() {
		if(this.isMenuOpen){
			this.isMenuOpen = false;
			return;
		}
		
		this.isMenuOpen = true;
	}
    
    public InGameMenu menu(){
    	return this.menu;
    }

    public static void setPlayer(Player player) {
        GameState.player = player;
    }

    public static EnemyShootingUnit getFirstEnemyShootingUnit() {
        return firstEnemyShootingUnit;
    }

    public static void setFirstEnemyShootingUnit(EnemyShootingUnit firstEnemyShootingUnit) {
        GameState.firstEnemyShootingUnit = firstEnemyShootingUnit;
    }

	
}