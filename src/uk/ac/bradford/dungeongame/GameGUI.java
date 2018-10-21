package uk.ac.bradford.dungeongame;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.*;
import java.io.File;
import java.io.IOException;
import static java.lang.Math.abs;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import uk.ac.bradford.dungeongame.GameEngine.TileType;

/**
 * The GameGUI class is responsible for rendering graphics to the screen to display
 * the game grid, players and monsters. The GameGUI class passes keyboard events to
 * a registered DungeonInputHandler to be handled.
 * @author prtrundl
 * @author sberk
 */
public class GameGUI extends JFrame {
    
    /**
     * The three final int attributes below set the size of some graphical elements,
     * specifically the display height and width of tiles in the dungeon and the height
     * of health bars for Entity objects in the game. Tile sizes should match the size
     * of the image files used in the game.
     */
    public static final int TILE_WIDTH = 32;
    public static final int TILE_HEIGHT = 32;
    public static final int HEALTH_BAR_HEIGHT = 3;
    
    /**
     * The canvas is the area that graphics are drawn to. It is an internal class
     * of the GameGUI class.
     */
    Canvas canvas;
    
    /**
     * Constructor for the GameGUI class. It calls the initGUI method to generate the
     * required objects for display.
     */
    public GameGUI() {
        initGUI();
    }
    
    /**
     * Registers an object to be passed keyboard events captured by the GUI.
     * @param i the DungeonInputHandler object that will process keyboard events to
     * make the game respond to input
     */
    public void registerKeyHandler(DungeonInputHandler i) {
        addKeyListener(i);
    }
    
    /**
     * Method to create and initialise components for displaying elements of the
     * game on the screen.
     */
    private void initGUI() {
        add(canvas = new Canvas());     //adds canvas to this frame
        setTitle("Dungeon - Sebastian Berk UB: ");
        setSize(1030, 615);
        setLocationRelativeTo(null);        //sets position of frame on screen
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    /**
     * Method to update the graphical elements on the screen, usually after player
     * and/or monsters have moved when a keyboard event was handled. The method
     * requires three arguments and displays corresponding information on the screen.
     * @param tiles A 2-dimensional array of TileTypes. This is the tiles of the
     * current dungeon level that should be drawn to the screen.
     * @param player An playerUtilities object with the type EntityType.PLAYER. This object
     * is used to draw the player in the right dungeon tile and display its health.
     * null can be passed for this argument, in which case no player will be drawn.
     * @param monsters An array of EntityType.MONSTER objects that is processed to draw
     * monsters in tiles with a health bar. null can be passed for this argument in which
     * case no monsters will be drawn. Elements in the monsters array can also be null,
     * in which case nothing will be drawn.
     */
    public void updateDisplay(TileType[][] tiles, PlayerUtilities player, Entity[] monsters) {
        canvas.update(tiles, player, monsters);
    }
}

/**
 * Internal class used to draw elements within a JPanel. The Canvas class loads
 * images from an asset folder inside the main project folder.
 * @author prtrundl
 * @author sberk
 */
class Canvas extends JPanel {

    private BufferedImage floor;
    private BufferedImage wall;
    private BufferedImage player;
    private BufferedImage player1;
    private BufferedImage player2;
    private BufferedImage player3;
    private BufferedImage monster;
    private BufferedImage stairs;
    private BufferedImage chest_closed;
    private BufferedImage chest_opened;
    private BufferedImage coin;
    private BufferedImage fountain_full;
    private BufferedImage fountain_empty;
    private BufferedImage shophp;
    private BufferedImage shopar;
    private BufferedImage background;
    
    TileType[][] currentTiles;  //the current 2D array of tiles to display
    PlayerUtilities currentPlayer;       //the current player object to be drawn
    Entity[] currentMonsters;   //the current array of monsters to draw
    
    /**
     * Constructor that loads tile images for use in this class
     */
    public Canvas() {
        loadTileImages();
    }
    
    /**
     * Loads tiles images from a fixed folder location within the project directory
     */
    private void loadTileImages() {
        try {
            floor = ImageIO.read(new File("assets/floor.png")); // Floor downloaded from the Internet https://opengameart.org/content/map-tile author: Ivan voirol
            assert floor.getHeight() == GameGUI.TILE_HEIGHT &&
                    floor.getWidth() == GameGUI.TILE_WIDTH;
            wall = ImageIO.read(new File("assets/wall.png"));
            assert wall.getHeight() == GameGUI.TILE_HEIGHT &&
                    wall.getWidth() == GameGUI.TILE_WIDTH;
            player1 = ImageIO.read(new File("assets/player1.png"));
            assert player1.getHeight() == GameGUI.TILE_HEIGHT
                    && player1.getWidth() == GameGUI.TILE_WIDTH;
            player2 = ImageIO.read(new File("assets/player2.png"));
            assert player2.getHeight() == GameGUI.TILE_HEIGHT
                    && player2.getWidth() == GameGUI.TILE_WIDTH;
            player3 = ImageIO.read(new File("assets/player3.png"));
            assert player1.getHeight() == GameGUI.TILE_HEIGHT
                    && player3.getWidth() == GameGUI.TILE_WIDTH;
            monster = ImageIO.read(new File("assets/monster.png"));
            assert monster.getHeight() == GameGUI.TILE_HEIGHT &&
                    monster.getWidth() == GameGUI.TILE_WIDTH;
            stairs = ImageIO.read(new File("assets/stairs.png"));
            assert stairs.getHeight() == GameGUI.TILE_HEIGHT &&
                    stairs.getWidth() == GameGUI.TILE_WIDTH;
            chest_closed = ImageIO.read(new File("assets/chest_closed.png"));
            assert chest_closed.getHeight() == GameGUI.TILE_HEIGHT &&
                    chest_closed.getWidth() == GameGUI.TILE_WIDTH;
            chest_opened = ImageIO.read(new File("assets/chest_opened.png"));
            assert chest_opened.getHeight() == GameGUI.TILE_HEIGHT &&
                    chest_opened.getWidth() == GameGUI.TILE_WIDTH;
            coin = ImageIO.read(new File("assets/coin.png"));
            assert coin.getHeight() == GameGUI.TILE_HEIGHT &&
                    coin.getWidth() == GameGUI.TILE_WIDTH;            
            fountain_full = ImageIO.read(new File("assets/fountain_full.png"));
            assert fountain_full.getHeight() == GameGUI.TILE_HEIGHT &&
                    fountain_full.getWidth() == GameGUI.TILE_WIDTH;
            fountain_empty = ImageIO.read(new File("assets/fountain_empty.png"));
            assert fountain_empty.getHeight() == GameGUI.TILE_HEIGHT &&
                    fountain_empty.getWidth() == GameGUI.TILE_WIDTH;
            shophp = ImageIO.read(new File("assets/shophp.png"));
            assert shophp.getHeight() == GameGUI.TILE_HEIGHT &&
                    shophp.getWidth() == GameGUI.TILE_WIDTH;
            shopar = ImageIO.read(new File("assets/shopar.png"));
            assert shopar.getHeight() == GameGUI.TILE_HEIGHT
                    && shopar.getWidth() == GameGUI.TILE_WIDTH;
            background = ImageIO.read(new File("assets/background.png")); // Background downloaded from the Internet and modified by me. author: anonymous
        } catch (IOException e) {
            System.out.println("Exception loading images: " + e.getMessage());
            e.printStackTrace(System.out);
        }
    }
    
    /**
     * Updates the current graphics on the screen to display the tiles, player and monsters
     * @param t The 2D array of TileTypes representing the current level of the dungeon
     * @param player The current player object, used to draw the player and its health
     * @param mon The array of monsters to display them and their health
     */
    public void update(TileType[][] t, PlayerUtilities player, Entity[] mon) {
        currentTiles = t;
        currentPlayer = player;
        currentMonsters = mon;
        repaint();
    }
    
    /**
     * Override of method in super class, it draws the custom elements for this
     * game such as the tiles, player and monsters.
     * @param g 
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawDungeon(g);
        drawStart(g);
        drawStats(g);
    }
     /**
     * Draws class choice text.
     * @param g 
     */
    private void drawStart(Graphics g) {
        if (GameEngine.playerClass == 0) {
            g.setColor(Color.black);
            g.setFont(new Font("TimesRoman", Font.BOLD, 24));
            g.drawString("Choose your class!", 250, 150);
            g.drawString("Press number on keyboard to choose", 100, 190);
            g.drawString("1. Knight - Normal HP, armour and damage", 175, 230);
            g.drawImage(player1, 135, 200, null);
            g.drawString("2. Warrior - Little health and armour, huge damage", 175, 270);
            g.drawImage(player2, 135, 240, null);
            g.drawString("3. Thief - More health, big dodge chance, little damage", 175, 310);
            g.drawImage(player3, 135, 280, null);
        }
    }
     /**
     * Draws player's statistics.
     * @param g 
     */
    private void drawStats(Graphics g) {
         if (currentPlayer != null) {
            g.setColor(Color.white);
            g.setFont(new Font("TimesRoman", Font.BOLD, 13));
            g.drawString("Player status", 806, 15);
            g.drawString("Class: " + GameEngine.playerClassName, 806, 30);
            g.drawString("Health: " + currentPlayer.getHealth() + "/" + currentPlayer.getMaxHealth(), 806, 45);
            g.drawString("Armour: " + currentPlayer.getArmour() + "/" + currentPlayer.getMaxArmour(), 806, 60);
            g.drawString("Attack damage: " + currentPlayer.getDmg(), 806, 75);
            g.drawString("Attributes", 806, 105);
            g.drawString("Dodge chance: " + (int) (currentPlayer.getDodgeChance() * 100) + "%", 806, 120);
            g.drawString("Critical dmg chance: " + (int) (currentPlayer.getPlayerCritDmgChance() * 100) + "%", 806, 135);
            g.drawString("Critical damage: " + currentPlayer.getPlayerCritDmg(), 806, 150);
            g.drawString("Inventory", 806, 180);
            g.drawString("Coins: " + currentPlayer.getCoin(), 806, 195);
            g.drawString("Health potions: " + currentPlayer.getPotionsNumber() + " Use H", 806, 210);
            g.drawString("Score: " + Integer.toString(GameEngine.score), 806, 240);
            g.drawString("Level: " + GameEngine.depth, 806, 255);
            g.drawString("Monsters killed: " + GameEngine.monstersKilled, 806, 270);
            g.drawString("Wall chance: " + (GameEngine.WALL_CHANCE * 100) + "%", 806, 285);
            g.drawString("Monster's maxhealth: " + GameEngine.monstersMaxHealth, 806, 300);
            g.drawString("Monster's damage: " + GameEngine.monstersDmg, 806, 315);
         }
    }
    
    /**
     * Draws graphical elements to the screen to display the current dungeon level
     * tiles, the player and the monsters. If the tiles, player or monster objects
     * are null they will not be drawn.
     * @param g 
     */
    private void drawDungeon(Graphics g) {
        g.drawImage(background, 800, 0, null);
        Graphics2D g2 = (Graphics2D) g;
        if (currentTiles != null) {
            for (int i = 0; i < currentTiles.length; i++) {
                for (int j = 0; j < currentTiles[i].length; j++) {
                    if (currentTiles[i][j] != null) {   //checks a tile exists
                        switch (currentTiles[i][j]) {
                             case FLOOR:
                                g2.drawImage(floor, i * GameGUI.TILE_WIDTH, j * GameGUI.TILE_HEIGHT, null);
                                break;
                            case WALL:
                                g2.drawImage(wall, i * GameGUI.TILE_WIDTH, j * GameGUI.TILE_HEIGHT, null);
                                break;
                            case STAIRS:
                                g2.drawImage(stairs, i * GameGUI.TILE_WIDTH, j * GameGUI.TILE_HEIGHT, null);
                                break;
                            case CHEST_OPENED:
                                g2.drawImage(chest_opened, i * GameGUI.TILE_WIDTH, j * GameGUI.TILE_HEIGHT, null);
                                break;
                            case CHEST_CLOSED:
                                g2.drawImage(chest_closed, i * GameGUI.TILE_WIDTH, j * GameGUI.TILE_HEIGHT, null);
                                break;
                            case COIN:
                                g2.drawImage(coin, i * GameGUI.TILE_WIDTH, j * GameGUI.TILE_HEIGHT, null);
                                break;
                            case FOUNTAIN_FULL:
                                g2.drawImage(fountain_full, i * GameGUI.TILE_WIDTH, j * GameGUI.TILE_HEIGHT, null);
                                break;
                            case FOUNTAIN_EMPTY:
                                g2.drawImage(fountain_empty, i * GameGUI.TILE_WIDTH, j * GameGUI.TILE_HEIGHT, null);
                                break;
                            case SHOPHP:
                                g2.drawImage(shophp, i * GameGUI.TILE_WIDTH, j * GameGUI.TILE_HEIGHT, null);
                                break;
                            case SHOPAR:
                                g2.drawImage(shopar, i * GameGUI.TILE_WIDTH, j * GameGUI.TILE_HEIGHT, null);
                        }
                    }
                }
            }
        }
        
         if (currentMonsters != null) {
            for (Entity mon : currentMonsters) {
                if (mon != null) {
                    g2.drawImage(monster, mon.getX() * GameGUI.TILE_WIDTH, mon.getY() * GameGUI.TILE_HEIGHT, null);
                    drawHealthBar(g2, mon);
                }
            }
        }
        // Chooses which image of player use, depending on player's class
        if (currentPlayer != null) {
            if(GameEngine.playerClass == 1) {
                player=player1;
            } else if(GameEngine.playerClass == 2) {
                player=player2;
            } else if(GameEngine.playerClass == 3) {
                player=player3;
            }
            g2.drawImage(player, currentPlayer.getX() * GameGUI.TILE_WIDTH, currentPlayer.getY() * GameGUI.TILE_HEIGHT, null);
            drawHealthBar(g2, currentPlayer);
            drawArmourBar(g2, currentPlayer);
            g.setColor(Color.white);
            g.setFont(new Font("TimesRoman", Font.BOLD, 13)); 
            // Prints monster dodging, critical damage to monster and damage to monster
            if (GameEngine.monsterDodged) {
                g.drawString("Monster dodged your attack!", 806, 530);
                GameEngine.monsterDodged = false;
            } else if (abs(GameEngine.lastDamageToMonster) > 0) {
                if (GameEngine.criticalHit) {
                    g.drawString("Critical damage to monster: " + Double.toString(abs(GameEngine.lastDamageToMonster)), 806, 530);
                    GameEngine.lastDamageToMonster = 0;
                    GameEngine.criticalHit = false;
                } else {
                    g.drawString("Damage to monster: " + Double.toString(abs(GameEngine.lastDamageToMonster)), 806, 530);
                    GameEngine.lastDamageToMonster = 0;
                }
            }
            // Prints infomrations about immortality, player's dodging attack, damage dealt to player's health and armour
            if(GameEngine.immortality & (GameEngine.immortalityTurns+1) > 0) {
                g.drawString("You are immortal for " + (GameEngine.immortalityTurns+1) + " turns", 806, 570);
            } else if (GameEngine.playerDodged) {
                g.drawString("You have dodged attack!", 806, 570);
                GameEngine.playerDodged = false;
            } 
            else if(abs(GameEngine.lastDamageToPlayer) > 0) {
                g.drawString("Damage to player health: " + Double.toString(abs(GameEngine.lastDamageToPlayer)), 806, 570);
                GameEngine.lastDamageToPlayer = 0;
                if(currentPlayer.getArmour() > 0) {
                    g.drawString("Damage to player armour: " + 1, 806, 550);

                }
            }
            
            // Prints information when reaching next depth level
            if(GameEngine.nextDepth) {
                g.setColor(Color.black);
                g.setFont(new Font("TimesgRoman", Font.BOLD, 50));
                g.drawString("Level: " + GameEngine.depth, 300, 300);
                GameEngine.nextDepth = false;
            }
            
            // Prints all informations about items dropped from chest, drinking potion, using fountain,
            // picking up coin, drop from monster, buying items from shop and having not enough money to buy
            if(GameEngine.increasedDmg) {
                g.drawString("Your damage increased by 1!", 806, 530);
                GameEngine.increasedDmg = false;
            } else if(GameEngine.increasedCritDmg) {
                g.drawString("Crit. dmg increased by 1!", 806, 530);
                GameEngine.increasedCritDmg = false;
            } else if(GameEngine.increasedCritDmgChance) {
                g.drawString("Crit. dmg chance inc. by 2%", 806, 530);
                GameEngine.increasedCritDmgChance = false;
            } else if(GameEngine.receivedHealthPotion) {
                g.drawString("You have found health potion", 806, 530);
                GameEngine.receivedHealthPotion = false; 
            } else if(GameEngine.increasedHealth) {
                g.drawString("You drinked health potion, +30HP", 806, 530);
                GameEngine.increasedHealth = false;
            } else if(GameEngine.increasedMaxHealth) {
                g.drawString("Your max health increased by 10!", 806, 530);
                GameEngine.increasedMaxHealth = false;
            } else if(GameEngine.increasedArmour) {
                g.drawString("Your armour increased by 30!", 806, 530);
                GameEngine.increasedArmour = false;
            } else if(GameEngine.monsterKill) {
                g.drawString("Monsters disappeared!", 806, 530);
                GameEngine.monsterKill = false;
            } else if(GameEngine.oneHitKill) {
                g.drawString("Now you kill with one hit!", 806, 510);
            } else if(GameEngine.increasedDodge) {
                g.drawString("Your dodging skill increased!", 806, 510);
                GameEngine.increasedDodge = false;
            } else if(GameEngine.fountainUsed) {
                g.drawString("You are now full health!", 806, 530);
                GameEngine.fountainUsed = false;
            } else if (GameEngine.lastCoinNumber != currentPlayer.getCoin() && GameEngine.monsterDropCoin == false && GameEngine.shopARBuy == false && GameEngine.shopHPBuy == false) {
                g.drawString("You picked up a coin", 806, 530);
                GameEngine.lastCoinNumber = currentPlayer.getCoin();
            } else if(GameEngine.monsterDropCoin) {
                g.drawString("Monster had a coin!", 806, 480);
                GameEngine.monsterDropCoin = false;
                GameEngine.lastCoinNumber = currentPlayer.getCoin();
            } else if(GameEngine.monsterDropHP) {
                g.drawString("Monster had health potion!", 806, 480);
                GameEngine.monsterDropHP = false;
            } else if(GameEngine.shopHPBuy) {
                g.drawString("Health potion bought", 806, 480);
                GameEngine.shopHPBuy = false;
                GameEngine.lastCoinNumber = currentPlayer.getCoin();
            } else if(GameEngine.shopARBuy) {
                g.drawString("30 of armour bought", 806, 480);
                GameEngine.shopARBuy = false;
                GameEngine.lastCoinNumber = currentPlayer.getCoin();
            } else if(GameEngine.shopNotEnough) {
                g.drawString("Not enough of coins", 806, 480);
                GameEngine.shopNotEnough = false;
            }
        }
        else if(GameEngine.playerClass != 0) { // Prints information when player is dead
            g.setColor(Color.black);
            g.setFont(new Font("TimesRoman", Font.BOLD, 24));
            g.drawString("You are dead", 350, 150);
            g.drawString("Score: " + Integer.toString(GameEngine.score) , 370, 190);
            g.drawString("Monsters killed: " + Integer.toString(GameEngine.monstersKilled), 330, 230);
            g.drawString("Depth level reached: " + Integer.toString(GameEngine.depth), 310, 270);
            g.drawString("Press any button to exit", 290, 310);
        }
    }
   
    /**
     * Draws a health bar for the given entity at the bottom of the tile that
     * the entity is located in.
     *
     * @param g2 The graphics object to use for drawing
     * @param e The entity that the health bar will be drawn for
     */
    private void drawHealthBar(Graphics2D g2, Entity e) {
        double remainingHealth = e.getHealth() / e.getMaxHealth();
        if(GameEngine.immortality && e.getType() == Entity.EntityType.PLAYER) { // checks if player is immortal
            g2.setColor(Color.YELLOW);
            g2.fill(new Rectangle2D.Double(e.getX() * GameGUI.TILE_WIDTH, e.getY() * GameGUI.TILE_HEIGHT + 29, GameGUI.TILE_WIDTH, GameGUI.HEALTH_BAR_HEIGHT));   
        } else {
            g2.setColor(Color.RED);
            g2.fill(new Rectangle2D.Double(e.getX() * GameGUI.TILE_WIDTH, e.getY() * GameGUI.TILE_HEIGHT + 29, GameGUI.TILE_WIDTH, GameGUI.HEALTH_BAR_HEIGHT));
            g2.setColor(Color.GREEN);
            g2.fill(new Rectangle2D.Double(e.getX() * GameGUI.TILE_WIDTH, e.getY() * GameGUI.TILE_HEIGHT + 29, GameGUI.TILE_WIDTH * remainingHealth, GameGUI.HEALTH_BAR_HEIGHT));
        }
    }
    
    /**
     * Draws an armour bar for the given PlayerUtilities object at the bottom of
     * the tile, under health bar, that the entity is located in.
     *
     * @param g2 The graphics object to use for drawing
     * @param e The entity that the health bar will be drawn for
     */
    private void drawArmourBar(Graphics2D g2, PlayerUtilities e) {
        double remainingArmour = (double) e.getArmour() / (double) e.getMaxArmour();
        if (GameEngine.immortality) { // checks if player is immortal
            g2.setColor(Color.YELLOW);
            g2.fill(new Rectangle2D.Double(e.getX() * GameGUI.TILE_WIDTH, e.getY() * GameGUI.TILE_HEIGHT + 35, GameGUI.TILE_WIDTH, GameGUI.HEALTH_BAR_HEIGHT));
        } else {
            g2.setColor(Color.RED);
            g2.fill(new Rectangle2D.Double(e.getX() * GameGUI.TILE_WIDTH, e.getY() * GameGUI.TILE_HEIGHT + 35, GameGUI.TILE_WIDTH, GameGUI.HEALTH_BAR_HEIGHT));
            g2.setColor(Color.WHITE);
            g2.fill(new Rectangle2D.Double(e.getX() * GameGUI.TILE_WIDTH, e.getY() * GameGUI.TILE_HEIGHT + 35, GameGUI.TILE_WIDTH * remainingArmour, GameGUI.HEALTH_BAR_HEIGHT));
        }
    }
}
