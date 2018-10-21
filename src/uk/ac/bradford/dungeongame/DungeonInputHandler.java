package uk.ac.bradford.dungeongame;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * This class handles keyboard events (key presses) captured by a GameGUI object
 * that are passed to an instance of this class. The class is responsible for
 * calling methods in the GameEngine class that will update tiles, players and
 * monsters for the various key presses that are handled.
 * @author prtrundl
 * @author sberk
 */
public class DungeonInputHandler implements KeyListener {

    GameEngine engine;      //GameEngine that this class calls methods from
    
    /**
     * Constructor that forms a connection between a DungeonInputHandler object and
     * a GameEngine object. The GameEngine object registered here is the one that will
     * have methods called to change player and monster positions etc.
     * @param eng The GameEngine object that this DungeonInputHandler is linked to
     */
    public DungeonInputHandler(GameEngine eng) {
        engine = eng;
    }
    
    /**
     * Unused method
     * @param e 
     */
    @Override
    public void keyTyped(KeyEvent e) {}

    /**
     * Method to handle key presses captured by the GameGUI. The method
     * currently calls the game engine to do a game turn if the up, down, left
     * or right arrow keys are pressed calls methods in the engine to update the
     * game by moving the player (and monsters if implemented) If H button is
     * pressed method calls a method to use a health potion. After starting game
     * method handles player class selection, if 1 or 2 or 3 is pressed method
     * will call corresponding method for class choice. If player is dead,
     * pressing any button makes game close.
     *
     * @param e A KeyEvent object generated when a keyboard key is pressed
     */
    @Override
    public void keyPressed(KeyEvent e) {
        if(engine.playerClass == 0) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_1: engine.playerClass = 1; engine.player = engine.spawnPlayer(); break;  //handle 1 button, choose coresponding class and spawns player
                case KeyEvent.VK_2: engine.playerClass = 2; engine.player = engine.spawnPlayer(); break;  //handle 2 button, choose coresponding class and spawns player
                case KeyEvent.VK_3: engine.playerClass = 3; engine.player = engine.spawnPlayer(); break;     //handle 3 button, choose coresponding class and spawns player
            }
        }
        if(engine.player != null) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_LEFT: engine.movePlayerSides(-1); engine.doTurn(); break;  //handle left arrow key
                case KeyEvent.VK_RIGHT: engine.movePlayerSides(+1); engine.doTurn(); break;//handle right arrow
                case KeyEvent.VK_UP: engine.movePlayerUD(-1); engine.doTurn(); break;      //handle up arrow
                case KeyEvent.VK_DOWN: engine.movePlayerUD(+1); engine.doTurn(); break;  //handle down arrow
                case KeyEvent.VK_H: if(engine.player.getPotionsNumber() > 0) { engine.player.useHealthPotion();  engine.doTurn(); break;}  //handle H button, use potion
            }
        }
        else if(engine.playerClass != 0){ // close game if player is dead
            switch (e.getKeyCode()) {
                default: engine.endGame();
            }
        }
    }
    /**
     * Unused method
     * @param e 
     */
    @Override
    public void keyReleased(KeyEvent e) {}
    
}
