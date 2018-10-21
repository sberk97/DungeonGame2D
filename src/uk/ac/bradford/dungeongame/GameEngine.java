/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.bradford.dungeongame;

import java.awt.Point;
import static java.lang.Math.abs;
import java.util.ArrayList;
import java.util.Random;

/**
 * The GameEngine class is responsible for managing information about the game,
 * creating levels, the player and monsters, as well as updating information
 * when a key is pressed while the game is running.
 * @author prtrundl
 * @author sberk
 */
public class GameEngine {

    /**
     * An enumeration type to represent different types of tiles that make up
     * a dungeon level. Each type has a corresponding image file that is used
     * to draw the right tile to the screen for each tile in a level. Floors are
     * open for monsters and the player to move into, walls should be impassable,
     * stairs allow the player to progress to the next level of the dungeon, and
     * chests can yield a reward when moved over.
     */
    public enum TileType {
        WALL, FLOOR, CHEST_CLOSED, CHEST_OPENED, STAIRS, COIN, FOUNTAIN_FULL, FOUNTAIN_EMPTY, SHOPHP, SHOPAR
    }

    /**
     * The width of the dungeon level, measured in tiles. Changing this may
     * cause the display to draw incorrectly, and as a minimum the size of the
     * GUI would need to be adjusted.
     */
    public static final int DUNGEON_WIDTH = 25;
    
    /**
     * The height of the dungeon level, measured in tiles. Changing this may
     * cause the display to draw incorrectly, and as a minimum the size of the
     * GUI would need to be adjusted.
     */
    public static final int DUNGEON_HEIGHT = 18;
    
    /**
     * The maximum number of monsters that can be generated on a single level
     * of the dungeon. This attribute can be used to fix the size of an array
     * (or similar) that will store monsters.
     */
    public static final int MAX_MONSTERS = 40;
    
    /**
     * The chance of a wall being generated instead of a floor when generating
     * the level. 1.0 is 100% chance, 0.0 is 0% chance.
     */
    public static double WALL_CHANCE = 0.05;
    
     /**
     * The chance of a coin being generated instead of a floor when generating
     * the level. 1.0 is 100% chance, 0.0 is 0% chance.
     */
    public static double COIN_CHANCE = 0.01;
    
     /**
     * The chance of a fountain being generated instead of a floor when generating
     * the level. 1.0 is 100% chance, 0.0 is 0% chance.
     */
    public static double FOUNTAIN_CHANCE = 0.001;

    /**
     * A random number generator that can be used to include randomised choices
     * in the creation of levels, in choosing places to spawn the player and
     * monsters, and to randomise movement and damage. This currently uses a seed
     * value of 123 to generate random numbers - this helps you find bugs by
     * giving you the same numbers each time you run the program. Remove
     * the seed value if you want different results each game.
     */
    private Random rng = new Random();

    /**
     * The current level number for the dungeon. As the player moves down stairs
     * the level number should be increased and can be used to increase the
     * difficulty e.g. by creating additional monsters with more health.
     */
    protected static int depth = 1;  //current dunegeon level

    /**
     * The GUI associated with a GameEngine object. THis link allows the engine
     * to pass level (tiles) and entity information to the GUI to be drawn.
     */
    private GameGUI gui;

    /**
     * The 2 dimensional array of tiles the represent the current dungeon level.
     * The size of this array should use the DUNGEON_HEIGHT and DUNGEON_WIDTH
     * attributes when it is created.
     */
    private TileType[][] tiles;
    
    /**
     * An ArrayList of Point objects used to create and track possible locations
     * to spawn the player and monsters.
     */
    private ArrayList<Point> spawns;

    /**
     * An Entity object that is the current player. This object stores the state
     * information for the player, including health and the current position (which
     * is a pair of co-ordinates that corresponds to a tile in the current level)
     */
    protected PlayerUtilities player;
    
    /**
     * playerClass stores which class player have chosen
     * 1 - Knight
     * 2 - Warrior
     * 3 - Thief
     */
    protected static int playerClass = 0;
    
    /**
     * playerClassName stores a name of the class
     */
    protected static String playerClassName = "";
    
    /**
     * score stores score value which player gained
     */
    protected static int score;
    
    /**
     * monstersDmg stores damage value of monster
     */
    protected static double monstersDmg;
    
    /**
     * monstersMaxHealth stores maximum health of monster
     */
    protected static double monstersMaxHealth;
    
    /**
     * lastCoinNumber stores last number of coins which player had
     */
    protected static int lastCoinNumber = 0;
    
    /**
     * fountainUsed gives an information if player used fountain
     */
    protected static boolean fountainUsed = false;
    
    /**
     * lastDamageToMonster stores number of damage dealt to monster
     */
    protected static double lastDamageToMonster;
    
    /**
     * monsterDodged stores an information if monster dodged player's attack
     */
    protected static boolean monsterDodged = false;
    
    /**
     * criticalHit stores an information if player dealt critical hit to monster
     */
    protected static boolean criticalHit = false;
    
    /**
     * lastDamageToPlayer stores number of damage dealt to player
     */
    protected static double lastDamageToPlayer;
    
    /**
     * playerDodged stores an information if player dodged attack
     */
    protected static boolean playerDodged = false;
    
    /**
     * monstersKilled stores amount of monsters killed
     */
    protected static int monstersKilled;
    
    /**
     * monsterDropCoin stores an information if killed monster dropped a coin
     */
    protected static boolean monsterDropCoin = false;
    
    /**
     * monsterDropHP stores an information if killed monster dropped health potion
     */
    protected static boolean monsterDropHP = false;
    
    /**
     * monsterHPChance stores percentage chance of dropping a health potion from monster
     */
    protected static double monsterHPChance = 0.15;
    
    /**
     * monsterCoinChance stores percentage chance of dropping a coin from monster
     */
    protected static double monsterCoinChance = 0.4;
    
    /**
     * nextDepth stores an information if player is on a next depth level
     */
    protected static boolean nextDepth = false;
    
    /**
     * increasedDmg stores an information if player received damage increase from chest
     */
    protected static boolean increasedDmg = false;
    
    /**
     * increasedCritDmg stores an information if player received critical damage increase from chest
     */
    protected static boolean increasedCritDmg = false;
    
    /**
     * increasedCritDmgChance stores an information if player received critical damage chance increase from chest
     */
    protected static boolean increasedCritDmgChance = false;
    
    /**
     * increasedHealth stores an information if player received health increase from chest
     */
    protected static boolean increasedHealth = false;
    
    /**
     * increasedHealthPotion stores an information if player received health potion from chest
     */
    protected static boolean receivedHealthPotion = false;
    
    /**
     * increasedMaxHealth stores an information if player received max health increase from chest
     */
    protected static boolean increasedMaxHealth = false;
    
    /**
     * increasedArmour stores an information if player received armour increase from chest
     */
    protected static boolean increasedArmour = false;
    
    /**
     * increasedDodge stores an information if player received dodge chance increase from chest
     */
    protected static boolean increasedDodge = false;

    /**
     * immortality stores an information if player is immortal
     */
    protected static boolean immortality = false;
    
    /**
     * lastHP stores number of health which player had before becoming immortal
     */
    protected static double lastHP;
  
    /**
     * monstersKill stores an information if player received item which kills all monsters on map
     */
    protected static boolean monsterKill = false;
    
    /**
     * oneHitKill stores an information if player received item with which player kill every monster with one hit
     */
    protected static boolean oneHitKill = false;
    
    /**
     * immortalityTurns stores number of how many immortal turns left
     */
    protected static int immortalityTurns;
    
    /**
     * oneHitKillTurns stores number of how many one hit kill turns left
     */
    protected static int oneHitKillTurns;
    
    /**
     * SHOPHP_CHANCE stores a percentage chance of spawning a shop selling hp
     */
    protected static double SHOPHP_CHANCE = 0.003;
    
    /**
     * SHOPAR_CHANCE stores a percentage chance of spawning a shop selling ar
     */
        protected static double SHOPAR_CHANCE = 0.002;
    
    /**
     * shopHPBuy stores an information if player bought potion from shop
     */
    protected static boolean shopHPBuy = false;
    
    /**
     * shopARBuy stores an information if player bought armour from shop
     */
    protected static boolean shopARBuy = false;
    
    /**
     * shopNotEnough stores an information that player has not enough money to buy item from shop
     */
    protected static boolean shopNotEnough = false;


    
    /**
     * An array of Entity objects that represents the monsters in the current
     * level of the dungeon. Elements in this array should be of the type Entity,
     * meaning that a monster is alive and needs to be drawn or moved, or should
     * be null which means nothing is drawn or processed for movement.
     * Null values in this array are skipped during drawing and movement processing.
     * Monsters (Entity objects) that die due to player attacks can be replaced
     * with the value null in this array which removes them from the game.
     */
    private Entity[] monsters;

    /**
     * Constructor that creates a GameEngine object and connects it with a GameGUI
     * object.
     * @param gui The GameGUI object that this engine will pass information to in
     * order to draw levels and entities to the screen.
     */
    public GameEngine(GameGUI gui) {
        this.gui = gui;
        startGame();
    }

    /**
     * Generates a new dungeon level. The method builds a 2D array of TileType
     * values that will be used to draw tiles to the screen and to add a variety
     * of elements into each level. Tiles can be floors, walls, stairs (to
     * progress to the next level of the dungeon), chests, fountains, coins, shops. The
     * method should contain the implementation of an algorithm to create an
     * interesting and varied level each time it is called. It also removes
     * walls around stairs and chests. Walls around edges of map are generated, with
     * few holes on each sides to let player get on the other side of map.
     *
     * @return A 2D array of TileTypes representing the tiles in the current
     * level of the dungeon. The size of this array should use the width and
     * height of the dungeon.
     */
    private TileType[][] generateLevel() {
        TileType[][] level = new TileType[DUNGEON_WIDTH][DUNGEON_HEIGHT];
        boolean stairsSet = false; // used in do-while loop when it looks for a place for stairs
        boolean chestSet = false; // used in do-while loop when it looks for a place for chest
        boolean fountainSet = false; // used to remember if there is a fountain on map, to generate only one per levelt
        boolean shopSet = false; // used to remember if there is a shop on map, to generate only one per level
        // This loop randomly generates fountain, coins, walls and floor
        for (int i = 0; i < DUNGEON_WIDTH; i++) {
            for (int j = 0; j < DUNGEON_HEIGHT; j++) {
                double f = rng.nextDouble();
                if (f < FOUNTAIN_CHANCE && !fountainSet) { // spawing of fountain
                    level[i][j] = TileType.FOUNTAIN_FULL;
                    fountainSet = true;
                } else if (f < SHOPAR_CHANCE && !shopSet) { // spawning of shop with armour
                    level[i][j] = TileType.SHOPAR;
                    shopSet = true;
                } else if (f < SHOPHP_CHANCE && !shopSet) { // spawning of shop with potions
                    level[i][j] = TileType.SHOPHP;
                    shopSet = true;
                } else if (f < COIN_CHANCE) { // spawning of coin
                    level[i][j] = TileType.COIN;
                } else if (f < WALL_CHANCE) { // spawning of wall
                    level[i][j] = TileType.WALL;
                } else {
                    level[i][j] = TileType.FLOOR;
                }
            }
        }
        
        // This loop generates walls on the sides
        for(int i = 0; i < DUNGEON_HEIGHT; i++){
            level[0][i] = TileType.WALL;
            level[DUNGEON_WIDTH-1][i] = TileType.WALL;
        }
        
        // This loop generates walls on the top and bottom
        for(int i = 0; i < DUNGEON_WIDTH; i++){
            level[i][0] = TileType.WALL;
            level[i][DUNGEON_HEIGHT-1] = TileType.WALL;
        }
        
        // This loop makes a holes on sides, to let player get on the other side of map
        for(int i = 0; i < 3; i++) {
            Point xyz = new Point(rng.nextInt(DUNGEON_WIDTH-1), rng.nextInt(DUNGEON_HEIGHT-1));
            //Holes on the left and right
            level[0][(int) xyz.getY()] = TileType.FLOOR;
            level[DUNGEON_WIDTH-1][(int) xyz.getY()] = TileType.FLOOR;
            
            //Holes on the top and bottom
            level[(int) xyz.getX()][0] = TileType.FLOOR;
            level[(int) xyz.getX()][DUNGEON_HEIGHT-1] = TileType.FLOOR;
            
            //Fills corners of map with wall
            level[0][0] = TileType.WALL;
            level[DUNGEON_WIDTH-1][0] = TileType.WALL;
            level[0][DUNGEON_HEIGHT-1] = TileType.WALL;
            level[DUNGEON_WIDTH-1][DUNGEON_HEIGHT-1] = TileType.WALL;
        }
        
        //Generates stairs
        do{
        Point xy = new Point(rng.nextInt(DUNGEON_WIDTH), rng.nextInt(DUNGEON_HEIGHT));
            if(level[(int) xy.getX()][(int) xy.getY()] == TileType.FLOOR){
                level[(int) xy.getX()][(int) xy.getY()] = TileType.STAIRS;
                deleteWalls(xy.getX(), xy.getY(), level);
                stairsSet = true;
            }
        }while(!stairsSet);
        
        //Generates chest
        do{
        Point xy = new Point(rng.nextInt(DUNGEON_WIDTH), rng.nextInt(DUNGEON_HEIGHT));
            if(level[(int) xy.getX()][(int) xy.getY()] == TileType.FLOOR){
                level[(int) xy.getX()][(int) xy.getY()] = TileType.CHEST_CLOSED;
                deleteWalls(xy.getX(), xy.getY(), level);
                chestSet = true;
            }
        }while(!chestSet);

        return level;
    }
    /**
     * This method delete walls around an object of x, y coordinates. It prevents stairs and chest from being blocked
     *
     * @param x X variable of object
     * @param y Y variable of object
     * @param level ArrayList of tiles
     */
    private void deleteWalls(double x, double y, TileType[][] level) {
        if ((int) x + 1 < DUNGEON_WIDTH) {
            if (level[(int) x + 1][(int) y] == TileType.WALL) {
                level[(int) x + 1][(int) y] = TileType.FLOOR;
            }
        }
        if ((int) x - 1 > 0) {
            if (level[(int) x - 1][(int) y] == TileType.WALL) {
                level[(int) x - 1][(int) y] = TileType.FLOOR;
            }
        }
        if ((int) y + 1 < DUNGEON_HEIGHT) {
            if (level[(int) x][(int) y + 1] == TileType.WALL) {
                level[(int) x][(int) y + 1] = TileType.FLOOR;
            }
        }
        if ((int) y - 1 > 0) {
            if (level[(int) x][(int) y - 1] == TileType.WALL) {
                level[(int) x][(int) y - 1] = TileType.FLOOR;
            }
        }
    }
    
    /**
     * Generates spawn points for the player and monsters. The method processes
     * the tiles array and finds tiles that are suitable for spawning, i.e.
     * tiles that are floor. Suitable tiles should be added
     * to the ArrayList that will contain Point objects - Points are a
     * simple kind of object that contain an X and a Y co-ordinate stored using
     * the int primitive type and are part of the Java language (search for the
     * Point API documentation and examples of their use)
     * @return An ArrayList containing Point objects representing suitable X and
     * Y co-ordinates in the current level that the player or monsters can be
     * spawned in
     */
    private ArrayList<Point> getSpawns() {
        ArrayList<Point> availableSpawns = new ArrayList<Point>();
        // This loop runs through every index of Array 'tiles' and checks if TileType equals FLOOR
        // Then it adds coordinates x = i and y = j to an ArrayList 'availableSpawns'
        for(int i = 0; i < DUNGEON_WIDTH; i++){
            for(int j = 0; j < DUNGEON_HEIGHT; j++) {
                if(tiles[i][j] == TileType.FLOOR) {
                    availableSpawns.add(new Point(i, j)); // Add floor tile coordinates to availableSpawns
                }     
            }
        }
        return availableSpawns;   
    }
    /**
     * Spawns monsters in suitable locations in the current level. The method
     * uses the spawns ArrayList to pick suitable positions to add monsters,
     * removing these positions from the spawns ArrayList as they are used
     * (using the remove() method) to avoid multiple monsters spawning in the
     * same location. The method creates monsters by instantiating the Entity
     * class, setting health (depending on depth level), damage (depending on
     * depth level), chance of dodging attack and setting the X and Y position for the monster using the
     * X and Y values in the Point object removed from the spawns ArrayList.
     *
     * @return An array of Entity objects representing the monsters for the
     * current level of the dungeon
     */
    private Entity[] spawnMonsters() {
        Point xy; // Initialization of spawn coordinates
        Entity[] arrayMonsters = new Entity[depth+2]; // Initalization of array of monsters
        // This for loop randomly generates 'xy' point and checks if this coordinate is a floor then create new Monster Entity in array with these coordinates
        for(int i = 0; i < arrayMonsters.length; i++) { 
            do {
            xy = new Point(rng.nextInt(DUNGEON_WIDTH)-1, rng.nextInt(DUNGEON_HEIGHT)-1); // Generating a point by randomizing number from range of 0 to DUNGEON_WIDTH-1 and HEIGHT-1
            }while(!spawns.contains(xy)); // Do - While loop checks if ArrayList from method 'getSpawns()' contains point 'xy'
            spawns.remove(xy); // removes used spawn point
            arrayMonsters[i] = new Entity(9+depth, depth, 0.1, (int) xy.getX(), (int) xy.getY(), Entity.EntityType.MONSTER); // initalization of object Entity(health, damage, dodgechance, x, y, type)
        }
        monstersMaxHealth = 9+depth; // saves monster max health to use it for one hit kill option
        monstersDmg = depth; // saves monster damage to use it in hitPlayer method
        return arrayMonsters;
    }

    /**
     * Spawns a player entity in the game. The method uses the spawns ArrayList
     * to select a suitable location to spawn the player and removes the Point
     * from the spawns ArrayList. The method instantiates the PlayerUtilities
     * class and assigns values for the health, armour, damage, dodge chance,
     * critical damage chance, critical damage value, position and type of
     * Entity.
     *
     * @return An PlayerUtilities object representing the player in the game
     */
    protected PlayerUtilities spawnPlayer() {
        Point xy; // Initialization of spawn coordinates
        do {
            xy = new Point(rng.nextInt(DUNGEON_WIDTH) - 1, rng.nextInt(DUNGEON_HEIGHT) - 1); // Generating a point by randomizing number from range of 0 to DUNGEON_WIDTH-1 and HEIGHT-1
        } while (!spawns.contains(xy)); // Do - While loop checks if ArrayList from method 'getSpawns()' contains point 'xy'
        spawns.remove(xy); // removes used spawn position
        if (playerClass == 1) { // Knight class
            playerClassName = "Knight";
            player = new PlayerUtilities(100, 100, 5, 0.1, 0.1, 3, (int) xy.getX(), (int) xy.getY(), Entity.EntityType.PLAYER); // initalization of object PlayerUtilities(maxHealth, armor, damage, dodgeChance, crit damage chance, crit damage value, x, y, type)
        } else if (playerClass == 2) { // Warrior class
            playerClassName = "Warrior";
            player = new PlayerUtilities(60, 50, 15, 0.05, 0.3, 5, (int) xy.getX(), (int) xy.getY(), Entity.EntityType.PLAYER); // initalization of object PlayerUtilities(maxHealth, armor, damage, dodgeChance, crit damage chance, crit damage value, x, y, type)
        } else if (playerClass == 3) { // Thief class
            playerClassName = "Thief";
            player = new PlayerUtilities(150, 100, 2, 0.3, 0.05, 2, (int) xy.getX(), (int) xy.getY(), Entity.EntityType.PLAYER); // initalization of object PlayerUtilities(maxHealth, armor, damage, dodgeChance, crit damage chance, crit damage value, x, y, type)
        }
        gui.updateDisplay(tiles, player, monsters);
        return player;
    }
    
    /**
     * Handles the movement of the player when attempting to move left or right
     * in the game. This method is called by the DungeonInputHandler class when
     * the user has pressed the left or right arrow key on the keyboard. The
     * method checks whether the tile to the left or right of the player is
     * empty for movement and if it is updates the player object's X and Y
     * locations with the new position. If player reach edge of map, it will
     * appear on the other side of map. If there is a chest, fountain, shop,
     * wall on the on the other side of map player won't appear there. If the
     * tile to the left or right of the player is not empty the method will not
     * update the player position, but may make other changes to the game, by
     * using movePlayerChecksSides method, such as damaging a monster in the
     * tile to the left or right, open chest, use fountain, pickup coin, buy
     * item from shop.
     *
     * @param x Variable used to set which way player has to move, negative
     * numbers moves left, positive right.
     */
    public void movePlayerSides(int x) {
        if (player != null && player.getX() + x > DUNGEON_WIDTH - 1) { // checks if player got out from map from right side
            if (tiles[0][player.getY()] != TileType.WALL && tiles[0][player.getY()] != TileType.CHEST_OPENED && tiles[0][player.getY()] != TileType.FOUNTAIN_EMPTY && tiles[0][player.getY()] != TileType.FOUNTAIN_FULL && tiles[0][player.getY()] != TileType.CHEST_CLOSED && tiles[0][player.getY()] != TileType.SHOPAR && tiles[0][player.getY()] != TileType.SHOPHP) {
                player.setPosition(0, player.getY()); // take player back on the other side of map
                movePlayerCheckSides(x); // calls method to check if there is a monster, coin, fountain or chest on this coordinates
            }
        } else if (player != null && player.getX() + x < 0) { // checks if player got out from map from left side
            if (tiles[player.getX() + DUNGEON_WIDTH - 1][player.getY()] != TileType.WALL && tiles[player.getX() + DUNGEON_WIDTH - 1][player.getY()] != TileType.CHEST_OPENED && tiles[player.getX() + DUNGEON_WIDTH - 1][player.getY()] != TileType.FOUNTAIN_EMPTY && tiles[player.getX() + DUNGEON_WIDTH - 1][player.getY()] != TileType.FOUNTAIN_FULL && tiles[player.getX() + DUNGEON_WIDTH - 1][player.getY()] != TileType.CHEST_OPENED && tiles[player.getX() + DUNGEON_WIDTH - 1][player.getY()] != TileType.SHOPHP && tiles[player.getX() + DUNGEON_WIDTH - 1][player.getY()] != TileType.SHOPAR) {
                player.setPosition(DUNGEON_WIDTH - 1, player.getY()); // take player back on the other side of map
                movePlayerCheckSides(x); // calls method to check if there is a monster, coin, fountain or chest on this coordinates
            }
        } else {
            if (tiles[player.getX() + x][player.getY()] != TileType.WALL && tiles[player.getX() + x][player.getY()] != TileType.CHEST_OPENED && tiles[player.getX() + x][player.getY()] != TileType.FOUNTAIN_EMPTY) {
                player.setPosition(player.getX() + x, player.getY()); // moves player right or left depending on variable x
                movePlayerCheckSides(x); // calls method to check if there is a monster, coin, fountain or chest on this coordinates
            }
        }
    }
    
    /**
     * Handles the movement of the player when attempting to move up or down in
     * the game. This method is called by the DungeonInputHandler class when the
     * user has pressed the up or down arrow key on the keyboard. The method
     * checks whether the tile above or under the player is empty for movement
     * and if it is updates the player object's X and Y locations with the new
     * position. If player reach edge of map, it will appear on the other side
     * of map. If there is a chest, fountain, shop, wall on the on the other
     * side of map player won't appear there. If the tile above or under the
     * player is not empty the method will not update the player position, but
     * may make other changes to the game, by using movePlayerChecksUD method,
     * such as damaging a monster in the tile to the left or right, open chest,
     * use fountain, pickup coin, buy item from shop.
     *
     * @param y Variable used to set which way player has to move, negative
     * numbers moves up, positive down.
     */
    public void movePlayerUD(int y) {
        if (player != null && player.getY() + y > DUNGEON_HEIGHT - 1) {
            if (tiles[player.getX()][0] != TileType.WALL && tiles[player.getX()][0] != TileType.CHEST_OPENED && tiles[player.getX()][0] != TileType.FOUNTAIN_EMPTY && tiles[player.getX()][0] != TileType.FOUNTAIN_FULL && tiles[player.getX()][0] != TileType.CHEST_CLOSED && tiles[player.getX()][0] != TileType.SHOPAR && tiles[player.getX()][0] != TileType.SHOPHP) {
                player.setPosition(player.getX(), 0); // take player back on the other side of map
                movePlayerCheckUD(y); // calls method to check if there is a monster, coin, fountain or chest on this coordinates
            }
        } else if (player != null && player.getY() + y < 0) {
            if (tiles[player.getX()][DUNGEON_HEIGHT - 1] != TileType.WALL && tiles[player.getX()][DUNGEON_HEIGHT - 1] != TileType.CHEST_OPENED && tiles[player.getX()][DUNGEON_HEIGHT - 1] != TileType.FOUNTAIN_EMPTY && tiles[player.getX()][DUNGEON_HEIGHT - 1] != TileType.FOUNTAIN_FULL && tiles[player.getX()][DUNGEON_HEIGHT - 1] != TileType.CHEST_CLOSED && tiles[player.getX()][DUNGEON_HEIGHT - 1] != TileType.SHOPAR && tiles[player.getX()][DUNGEON_HEIGHT - 1] != TileType.SHOPHP) {
                player.setPosition(player.getX(), DUNGEON_HEIGHT - 1); // take player back on the other side of map
                movePlayerCheckUD(y); // calls method to check if there is a monster, coin, fountain or chest on this coordinates
            }
        } else {
            if (tiles[player.getX()][player.getY() + y] != TileType.WALL && tiles[player.getX()][player.getY() + y] != TileType.CHEST_OPENED && tiles[player.getX()][player.getY() + y] != TileType.FOUNTAIN_EMPTY) {
                player.setPosition(player.getX(), player.getY() + y); // moves player up or down depending on variable y
                movePlayerCheckUD(y); // calls method to check if there is a monster, coin, fountain or chest on this coordinates
            }
        }
    }
    
    /**
     * Handles operations like attacking monsters, opening chests, picking up
     * coins, using fountain and buying items from shop. This method is called
     * by the movePlayerSides.
     *
     * @param x Variable used to set which way player has to move, negative
     * numbers moves left, positive right.
     */
    public void movePlayerCheckSides(int x) {
        // attacking monster
        for (int i = 0; i < monsters.length; i++) {
            if (monsters[i] != null && (player.getX() == monsters[i].getX()) && (player.getY() == monsters[i].getY())) {
                hitMonster(monsters[i]);
                player.setPosition(player.getX() + (x * -1), player.getY());
            }
        }
        if (tiles[player.getX()][player.getY()] == TileType.CHEST_CLOSED) {
            // opening chest
            tiles[player.getX()][player.getY()] = TileType.CHEST_OPENED;
            openChest();
            player.setPosition(player.getX() + (x * -1), player.getY());
        } else if (tiles[player.getX()][player.getY()] == TileType.COIN) { 
            // picking up coin
            tiles[player.getX()][player.getY()] = TileType.FLOOR;
            player.addCoin();
        } else if (tiles[player.getX()][player.getY()] == TileType.FOUNTAIN_FULL) {
            // using fountain
            tiles[player.getX()][player.getY()] = TileType.FOUNTAIN_EMPTY;
            player.changeHealth(player.getMaxHealth());
            fountainUsed = true;
            player.setPosition(player.getX() + (x * -1), player.getY());
        } else if (tiles[player.getX()][player.getY()] == TileType.SHOPHP || tiles[player.getX()][player.getY()] == TileType.SHOPAR) { 
            // using shop
            if (tiles[player.getX()][player.getY()] == TileType.SHOPHP) { // item which will be bought depends on TileType
                buyThing(0);
            } else {
                buyThing(1);
            }
            player.setPosition(player.getX() + (x * -1), player.getY());
        }
    }

    /**
     * Handles operations like attacking monsters, opening chests, picking up
     * coins, using fountain and buying items from shop. This method is called
     * by the movePlayerUD.
     *
     * @param y Variable used to set which way player has to move, negative
     * numbers moves up, positive down.
     */
    public void movePlayerCheckUD(int y) {
        // attacking monster
        for (int i = 0; i < monsters.length; i++) {
            if (monsters[i] != null && (player.getX() == monsters[i].getX()) && (player.getY() == monsters[i].getY())) {
                hitMonster(monsters[i]);
                player.setPosition(player.getX(), player.getY() + (y * -1));
            }
        }
        if (tiles[player.getX()][player.getY()] == TileType.CHEST_CLOSED) {
            // opening chest
            tiles[player.getX()][player.getY()] = TileType.CHEST_OPENED;
            openChest();
            player.setPosition(player.getX(), player.getY() + (y * -1));
        } else if (tiles[player.getX()][player.getY()] == TileType.COIN) { 
            // picking up coin
            tiles[player.getX()][player.getY()] = TileType.FLOOR;
            player.addCoin();
        } else if (tiles[player.getX()][player.getY()] == TileType.FOUNTAIN_FULL) {
            // using fountain
            tiles[player.getX()][player.getY()] = TileType.FOUNTAIN_EMPTY;
            player.changeHealth(player.getMaxHealth());
            fountainUsed = true;
            player.setPosition(player.getX(), player.getY() + (y * -1));
        } else if (tiles[player.getX()][player.getY()] == TileType.SHOPHP || tiles[player.getX()][player.getY()] == TileType.SHOPAR) { 
            // using shop
            if (tiles[player.getX()][player.getY()] == TileType.SHOPHP) { // item which will be bought depends on TileType
                buyThing(0);
            } else {
                buyThing(1);
            }
            player.setPosition(player.getX(), player.getY() + (y * -1));
        }
    }
    
    /**
     * Reduces a monster's health in response to the player attempting to move
     * into the same square as the monster (attacking the monster).
     * Variable f randomize number to use it in critical damage chance percentage 
     * probability. Method checks if player can kill with one hit or if player
     * dealt critical damage(it adds fixed amount of additional damage to main player damage). 
     * Variable lastDamageToMonster saves dealt damage to use it in GameGUI to print on screen.
     * 
     * @param m The Entity which is the monster that the player is attacking
     */
    private void hitMonster(Entity m) {
        double f = rng.nextDouble(); // random number used in critical damage chance and dodge chance randomization
        if(oneHitKill) { // checks if player can kill with one hit
            m.changeHealth(-(monstersMaxHealth));
            lastDamageToMonster = monstersMaxHealth;
        }
        else if (f < m.getDodgeChance()) {
            monsterDodged = true;
        }
        else if (f < player.getPlayerCritDmgChance()+ m.getDodgeChance()) { // checks if player dealt critical damage
            m.changeHealth(-(player.getDmg() + player.getPlayerCritDmg()));
            criticalHit = true; // Saves information about critical hit to print it on screen in GameGUI
            lastDamageToMonster = -(player.getDmg() + player.getPlayerCritDmg());
        } else {
            m.changeHealth(-(player.getDmg()));
            lastDamageToMonster = -(player.getDmg());
        }
    }

    /**
     * Moves all monsters on the current level. The method processes all non-null
     * elements in the monsters array and calls the moveMonster method for each one.
     */
    private void moveMonsters() {
        for(int i = 0; i < monsters.length; i++) {
            if(monsters[i] != null){
                moveMonster(monsters[i]);
            }
        }
    }

    /**
     * Moves a specific monster towards the player in the game. The method updates the X and Y
     * attributes of the monster Entity to reflect its new position.
     * At first it checks if player is further on X or Y coordinates. 
     * Then it checks if player is left/right or up/down to the monster and makes a move towards player
     * by calling method monsterMoveX or monsterMoveY.
     * If monster can't move towards player for example to the right, it call method
     * monsterMoveUD to move up or down. If monster can't move up or down it calls method
     * monsterMoveSides to move left or right.
     * @param m The Entity (monster) that needs to be moved
     */
    private void moveMonster(Entity m) {
        if (abs(player.getX() - m.getX()) >= abs(player.getY() - m.getY())) { // checks if player is further on x or y coordinate
            if (player.getX() >= m.getX()) { // checks is player is to the right of monster
                if (monsterMoveX(m, 1)) { // monster moves right
                } else {
                    monsterMoveUD(m, 1); // monster moves up or down
                }
            } else if (player.getX() <= m.getX()) { // checks if player is to the left of monster
                if (monsterMoveX(m, -1)) { // monster moves left
                } else {
                    monsterMoveUD(m, -1); // monster moves up or down
                }
            }
        } else {
            if (player.getY() >= m.getY()) { // checks if player is under monster
                if (monsterMoveY(m, 1)) { // monster moves down
                } else {
                    monsterMoveSides(m, 1); // monster moves left or right
                }
            } else if (player.getY() <= m.getY()) { // checks if player is above monster
                if (monsterMoveY(m, -1)) { // monster moves up
                } else {
                    monsterMoveSides(m, -1); // monster moves left or right
                }
            }
        }
    }
    
    /**
     * Moves monster to the right or left. If monster is on player position, it
     * deals damage and go back. If monster is on another monster it moves back.
     *
     * @param m The Entity (monster) that needs to be moved
     * @param move Variable used to set which way monster has to move, negative
     * numbers moves left, positive right.
     */
    private boolean monsterMoveX(Entity m, int move) {
        if (m.getX() + move < DUNGEON_WIDTH && m.getX() + move >= 0) {
            if (tiles[m.getX() + move][m.getY()] == TileType.FLOOR) { // check if next tile is a floor                       
                m.setPosition(m.getX() + move, m.getY());
                for (int i = 0; i < monsters.length; i++) {
                    if (monsters[i] != null && monsters[i] != m && m.getX() == monsters[i].getX() && m.getY() == monsters[i].getY()) {
                        m.setPosition(m.getX() + (move * -1), m.getY());
                        return false;
                    }
                }
                if (player.getX() == m.getX() && player.getY() == m.getY()) {
                    hitPlayer();
                    m.setPosition(m.getX() + (move * -1), m.getY());
                }
                return true;
            }

        }
        return false;
    }
    
    /**
     * Moves monster up or down. If monster is on player position, it
     * deals damage and go back. If monster is on another monster it moves back.
     *
     * @param m The Entity (monster) that needs to be moved
     * @param move Variable used to set which way monster moved in moveMonster method.
     */
    private boolean monsterMoveY(Entity m, int move) {
        if (m.getY() + move < DUNGEON_HEIGHT && m.getY() + move >= 0) {
            if (tiles[m.getX()][m.getY() + move] == TileType.FLOOR) {
                m.setPosition(m.getX(), m.getY() + move);
                for (int i = 0; i < monsters.length; i++) {
                    if (monsters[i] != null && monsters[i] != m && m.getX() == monsters[i].getX() && m.getY() == monsters[i].getY()) {
                        m.setPosition(m.getX(), m.getY() + (move * -1));
                        return false;
                    }
                }
                if (player.getX() == m.getX() && player.getY() == m.getY()) {
                    hitPlayer();
                    m.setPosition(m.getX(), m.getY() + (move * -1));
                }
                return true;
            }
        }
        return false;
    }
    
    /**
     * Moves monster up or down. This method is used in monsterMove if monster
     * can't move left or right. If player is under monster, monster moves down,
     * if he can't move down it moves up. If monster can't make any move, it
     * destroy wall. If player is above monster, monster makes same moves in
     * reverse order.
     *
     * @param m The Entity (monster) that needs to be moved
     * @param move Variable used to set which way monster moved in moveMonster method.
     */
    private void monsterMoveUD(Entity m, int move) {
        if (player.getY() >= m.getY()) { 
            if (!monsterMoveY(m, 1)) {
                if (!monsterMoveY(m, -1) && tiles[(int) m.getX() + move][(int) m.getY()] != TileType.STAIRS) {
                    tiles[(int) m.getX() + move][(int) m.getY()] = TileType.FLOOR;
                }
            }
        } else if (player.getY() <= m.getY()) {
            if (!monsterMoveY(m, -1)) {
                if (!monsterMoveY(m, 1) && tiles[(int) m.getX() + (move * -1)][(int) m.getY()] != TileType.STAIRS) {
                    tiles[(int) m.getX() + (move * -1)][(int) m.getY()] = TileType.FLOOR;
                }
            }
        }
    }
    
     /**
     * Moves monster to the left or right. This method is used in monsterMove if
     * monster can't move up or down. If player is to the right of monster,
     * monster moves right, if he can't move right it moves left. If monster
     * can't make any move, it destroy wall. If player is to the left monster,
     * monster makes same moves in reverse order.
     *
     * @param m The Entity (monster) that needs to be moved
     * @param move Variable used to set which way monster has to move, negative
     * numbers moves left, positive right.
     */
    private void monsterMoveSides(Entity m, int move) {
        if (player.getX() >= m.getX()) {
            if (!monsterMoveX(m, 1)) {
                if (!monsterMoveX(m, -1) && tiles[(int) m.getX()][(int) m.getY() + move] != TileType.STAIRS) {
                    tiles[(int) m.getX()][(int) m.getY() + move] = TileType.FLOOR;
                }
            }
        } else if (player.getX() <= m.getX()) {
            if (!monsterMoveX(m, -1)) {
                if (!monsterMoveX(m, 1) && tiles[(int) m.getX()][(int) m.getY() + (move * -1)] != TileType.STAIRS) {
                    tiles[(int) m.getX()][(int) m.getY() + (move * -1)] = TileType.FLOOR;
                }
            }
        }
    }
    
    /**
     * Reduces the health and armour of the player when hit by a monster - a monster next
     * to the player can attack it instead of moving and should call this method
     * to reduce the player's health. If player is immortal monsters can't deal damage.
     * If player has armour, monsters damage is divided by 2 and player armour is decreased.
     * All received damage is saved to lastDamageToPlayer to use it in GameGUI to show
     * information on screen
     */   
    private void hitPlayer() {
        double f = rng.nextDouble(); // random number used in dodge chance randomization
        if (!immortality) { // checks if player is immortal
            if(f < player.getDodgeChance()) {
                playerDodged = true;
            }
            else if (player.getArmour() > 0) { // checks if player has armour
                player.changeArmour(-1); // decreases player's armour
                player.changeHealth(-(monstersDmg / 2)); // deal damage to player, its half of monster's damage
                lastDamageToPlayer += -(monstersDmg / 2); // saves amount of damage which player received
            } else {
                player.changeHealth(-monstersDmg);
                lastDamageToPlayer += -monstersDmg;

            }
        }
    }

    /**
     * Processes the monsters array to find any Entity in the array with 0 or
     * less health. Any Entity in the array with 0 or less health should be set
     * to null; when drawing or moving monsters the null elements in the
     * monsters array are skipped. It also increase monstersKilled counter for
     * every dead monster and increases score for every dead monster. If it
     * finds dead monster method calls another method monsterDrop.
     */
    private void cleanDeadMonsters() {
        for(int i = 0; i < monsters.length; i++) {
            if(monsters[i] != null && (monsters[i].getHealth() <= 0)) {
                monsters[i] = null; // monsters with health <= 0 are set to null
                monsterDrop();
                monstersKilled += 1; // increase monstersKilled counter
                score += 50; // increases score
            }
        }
    }
    
    /**
     * This method handles item drop from monsters. Drop is chosen randomly.
     * This method is called by cleanDeadMonsters if null entry in monsters
     * array is found.
     */
    private void monsterDrop() {
        double f = rng.nextDouble();
        if(f < monsterHPChance) {
            monsterDropHP = true;
            player.givePotion();
        }
        else if(f < monsterCoinChance) {
            monsterDropCoin = true;
            player.addCoin();
        }
    }

    /**
     * Called in response to the player moving into a Stair tile in the game.
     * The method increases the dungeon depth, increases score,
     * sets nextDepth true to print level number on screen, increases wall chance, 
     * generates a new level by calling the generateLevel method,
     * fills the spawns ArrayList with suitable spawn
     * locations and spawns monsters. Finally it places the player in the new
     * level by calling the placePlayer() method. Note that a new player object
     * should not be created here unless the health of the player should be reset.
     */    
    private void descendLevel() {
        depth += 1; // increases depth level
        score += 100; // increases score
        nextDepth = true; // gives information for GameGUI to show text 'Level depth'
        if(WALL_CHANCE < 0.25) {
            WALL_CHANCE += 0.005; // increases wall chance
        }
        tiles = generateLevel(); // generates new level
        spawns = getSpawns(); // generates new available spawns
        monsters = spawnMonsters(); // spawns monsters
        placePlayer(); // place player somewhere on a map
    }

    /**
     * Places the player in a dungeon level by choosing a spawn location from the
     * spawns ArrayList, removing the spawn position as it is used. The method sets
     * the players position in the level by calling its setPosition method with the
     * x and y values of the Point taken from the spawns ArrayList.
     */
    private void placePlayer() {
        Point xy; // Initialization of spawn coordinates
        do {
            xy = new Point(rng.nextInt(DUNGEON_WIDTH), rng.nextInt(DUNGEON_HEIGHT)); // Generating a point by randomizing number from range of 0 to DUNGEON_WIDTH and HEIGHT
            }while(!spawns.contains(xy)); // Do - While loop checks if ArrayList from method 'getSpawns()' contains point 'xy'
        spawns.remove(xy); // remove player spawn from available spawnpoints
        player.setPosition((int) xy.getX(), (int) xy.getY()); // place player on a map
    }

    /**
     * Performs a single turn of the game when the user presses a key on the
     * keyboard. The method cleans dead monsters, moves any monsters still alive
     * and then checks if the player is immortal, if the player
     * kills with one hit, if the player is dead, exiting the game after an
     * appropriate output to the user is given. It checks if the player
     * moved into a stair tile and calls the descendLevel method if it does.
     * Finally it requests the GUI to redraw the game level by passing it the
     * tiles, player and monsters for the current level.
     */     
    public void doTurn() {
        cleanDeadMonsters();
        moveMonsters();
        if (player != null) {       //checks a player object exists
            if (immortality) { // checks if player is immortal
                if (immortalityTurns == 0) {
                    immortality = false; // turns off immortality after 5 rounds
                    player.changeHealth(-100);
                    player.changeHealth(lastHP);
                }
                player.changeHealth(player.getMaxHealth()); // gives immortality by setting max player's health
                immortalityTurns--; // decrease rounds count
            }
            if (oneHitKill) { // checks if player kills with one hit
                if (oneHitKillTurns == 0) {
                    oneHitKill = false; // turns off one hit killing after 5 rounds
                }
                oneHitKillTurns--; // decrase rounds count
            }
            if (player.getHealth() < 1 && playerClass != 0) { // checks if player is dead
                player = null; // sets player to null, to be able to show scores
                gui.updateDisplay(tiles, null, monsters);
            } else {
                if (tiles[player.getX()][player.getY()] == TileType.STAIRS) {
                    descendLevel();     //moves to next level if the player is on Stairs
                }
                gui.updateDisplay(tiles, player, monsters);     //updates GUI
            }
        }
    }
    
    /**
     * Method used to open chests and drop item from them. This method is called
     * by movePlayerCheckSides and movePlayerCheckUD. It generates random number
     * from 0 to 1 and then compare this number with percentage possibilities of
     * dropping certain items. The items from the chests are: potion, kill of
     * all monsters, player killing with one hit, player's damage increasement,
     * making player immortal, player's dodge chance increasement, giving player
     * 30 armour, player's critical damage increasement, player's max health
     * increasement, player's critical damage chance increasement.
     */
    public void openChest() {
        double f = rng.nextDouble(); // generate random number
        score += 20; // adds score for opening chest
        if (f < 0.3) { // gives health potion
            player.givePotion();
            receivedHealthPotion = true;
        } else if (f < 0.35) { // kills all monsters
            for (int i = 0; i < monsters.length; i++) {
                monsters[i] = null;
            }
            monsterKill = true;
        } else if (f < 0.4) { // player kills with one hit for 5 turns
            oneHitKill = true;
            oneHitKillTurns = 5;
        } else if (f < 0.6) { // increases player's damage
            player.increaseDmg(1);
            increasedDmg = true;
        } else if (f < 0.65) { // makes player immortal for 5 turns
            immortalityTurns = 5;
            lastHP = player.getHealth();
            immortality = true;
        } else if (f < 0.70) { // increases player's dodge chance
            player.increaseDodgeChance(0.05);
            increasedDodge = true;
        } else if (f < 0.80) { // gives 30 armour
            player.changeArmour(30);
            increasedArmour = true;
        } else if (f < 0.90) { // increases critical damage
            player.increasePlayerCritDmg(1);
            increasedCritDmg = true;
        } else if (f < 0.95) { // increases max health
            player.increaseMaxHealth(10);
            increasedMaxHealth = true;
        } else if (f < 1) { // increases critical damage chance
            player.increasePlayerCritDmgChance(0.02);
            increasedCritDmgChance = true;
        }
    }
    
    /**
     * This method is used by shops in playerCheckSides and playerCheckUD while
     * player walk into a shop.
     *
     * @param item stores an information which item player bought 0 - health
     * potion, 1 - armour.
     */
    public void buyThing(int item) {
        if(item==0 && player.getCoin() >= 5) {
            player.removeCoin(5);
            shopHPBuy = true;
            player.givePotion();
        } else if(item==1 && player.getCoin() >= 10) {
            player.removeCoin(10);
            shopARBuy = true;
            player.changeArmour(30);
        } else {
            shopNotEnough = true;
        }
    }
    
    /**
     * Starts a game. This method generates a level, finds spawn positions in
     * the level, spawns monsters and the player and then requests the GUI to
     * update the level on screen using the information on tiles, player and
     * monsters.
     */
    public void startGame() {
        tiles = generateLevel();
        spawns = getSpawns();
        monsters = spawnMonsters();
        //player = spawnPlayer();
        gui.updateDisplay(tiles, player, monsters);
    }
     /**
     * Ends a game. This method checks if player is null and then invokes
     * System.exit to close game window.
     */
    public void endGame() {
        if(player == null) {
            System.exit(0);
        }
    }
}
