/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.bradford.dungeongame;

/**
 * The PlayerUtilities class stores state information for Player entity types in the game.
 * @author sberk
 */
public class PlayerUtilities extends Entity {
    
    /**
     * maxArmour stores the maximum possible armour for player
     */
    private int maxArmour = 100;
    
    /**
     * armour stores the current armour for player
     */
    private int armour;
    
    /**
     * playerCriticalDamageChance stores the chance of dealing critical damage to monster
     */
    private double playerCriticalDamageChance;
    
    /**
     * playerCriticalDamage stores the additional critical damage which player can deal to monster
     */
    private double playerCriticalDamage;
    
    /**
     * coin is the current amount of coins that player has
     */
    private int coin = 0;
    
    /**
     * potionsNumber is the current amount of health potions that player has
     */
    private int potionsNumber = 0;
    
    /**
     * This constructor is used to create an PlayerUtilities object to use in
     * the game for player
     *
     * @param maxHealth the maximum health of this Entity, also used to set its starting
     * health value
     * @param armour value of player's armour
     * @param dmg value of player's damage
     * @param dodgeChance percentage value of player's dodge chance
     * @param playerCriticalDamageChance percentage value of player's critical damage chance
     * @param playerCriticalDamage value of player's critical damage
     * @param x the X position of this Entity in the game
     * @param y the Y position of this Entity in the game
     * @param type They type of Entity
     */
    public PlayerUtilities(double maxHealth, int armour, double dmg, double dodgeChance, double playerCriticalDamageChance, double playerCriticalDamage, int x, int y, EntityType type) {
        super(maxHealth, dmg, dodgeChance, x, y, type);
        this.armour = armour;
        this.playerCriticalDamageChance = playerCriticalDamageChance;
        this.playerCriticalDamage = playerCriticalDamage;
    }
    
    /**
     * Changes the current armour value for Player, setting the armour to
     * maxArmour if the change would cause the armour attribute to exceed
     * maxArmour
     *
     * @param change An integer representing the change in armour for player.
     * Passing a positive value will increase the health, passing a negative
     * value will decrease the armour.
     */
    public void changeArmour(int change) {
        armour += change;
        if (armour > maxArmour)
            armour = maxArmour;
    }
    
    /**
     * Returns the current armour value for player
     * @return the value of the armour attribute for player
     */
    public int getArmour() {
        return armour;
    }
    
    /**
     * Returns the maxArmour value for player
     * @return the value of the maxPlayer attribute for this Entity
     */
    public double getMaxArmour() {
        return maxArmour;
    }
    
    /**
     * Returns the critical damage chance of the player
     * @return the value of player's critical damage chance
     */
    public double getPlayerCritDmgChance() {
        return playerCriticalDamageChance;
    }
    
    /**
     * Returns the critical damage value of the player
     * @return the value of player's critical damage
     */
    public double getPlayerCritDmg() {
        return playerCriticalDamage;
    }
    
     /**
     * Returns the amount of coins that player has
     * @return the amount of player's coins
     */
    public int getCoin() {
        return coin;
    }
    
    /**
     * Returns the number of potions that player has
     * @return the number of player's potions
     */
    public int getPotionsNumber() {
        return potionsNumber;
    }
    
    /**
     * Increases critical damage value of player
     * @param value value of critical damage incrementation
     */
    public void increasePlayerCritDmg(double value) {
        playerCriticalDamage += value;
    }
    
    /**
     * Increases critical damage chance value of player
     * @param value value of critical damage chance incrementation
     */
    public void increasePlayerCritDmgChance(double value) {
        playerCriticalDamageChance += value;
    }
    
    /**
     * Adds coin to player's inventory
     */
    public void addCoin() {
        GameEngine.score += 1;
        coin += 1;
    }
    
    /**
     * Removes given amount of coins from player's inventory
     * @param coins number of removed coins
     */
    public void removeCoin(int coins) {
        coin -= coins;
    }
    
    /**
     * Increases number of potions that player has
     */
    public void givePotion() {
        potionsNumber++;
    }
    
    /**
     * Increases player's health points by using potion
     */
    public void useHealthPotion() {
        if(potionsNumber > 0) {
            potionsNumber--;
            changeHealth(30);
            GameEngine.increasedHealth = true;
        }
    }
    
}
