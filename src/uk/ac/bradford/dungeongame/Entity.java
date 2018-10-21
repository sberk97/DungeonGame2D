package uk.ac.bradford.dungeongame;

/**
 * The Entity class stores basic state information for both the Player and Monster
 * entity types in the game. The type of entity is stored as an EntityType, an
 * enumeration type local to this class.
 * 
 * @author prtrundl
 * @author sberk
 */
public class Entity {
    
    /**
     * EntityType is an enumeration type with two possible values, representing
     * either a Monster or the Player in the game.
     */
    public enum EntityType { PLAYER, MONSTER }
    
    /**
     * maxHealth stores the maximum possible health for this entity
     */
    private double maxHealth;
    
    /**
     * health stores the current health for this entity
     */
    private double health;
    
    /**
     * dodgeChance stores the current percentage chance of dodging attack for this entity
     */
    private double dodgeChance;
    
    /**
     * dmg stores the damage that this entity can deal
     */
    private double dmg;
    
    /**
     * xPos is the current x position in the game for this entity
     */
    private int xPos;
    
    /**
     * yPos is the current y position in the game for this entity
     */
    private int yPos;
    
    /**
     * type is used to distinguish between the player and monsters in the game
     */
    private EntityType type;
    
    
    /**
     * This constructor is used to create an Entity object to use in the game for both
     * the player and monsters
     * @param maxHealth the maximum health of this Entity, also used to set its starting
     * health value
     * @param dmg value of entity's damage
     * @param dodgeChance percentage value of entity's dodge chance
     * @param x the X position of this Entity in the game
     * @param y the Y position of this Entity in the game
     * @param type They type of Entity, either EntityType.PLAYER or EntityType.MONSTER
     */
    public Entity(double maxHealth, double dmg, double dodgeChance, int x, int y, EntityType type) {
        this.maxHealth = maxHealth;
        this.health = maxHealth;
        this.dmg = dmg;
        this.dodgeChance = dodgeChance;
        xPos = x;
        yPos = y;
        this.type = type;
    }
    
    /**
     * This method returns the current X position for this entity in the game
     * @return The X co-ordinate of this Entity in the game
     */
    public int getX() {
        return xPos;
    }
    
    /**
     * This method returns the current Y position for this entity in the game
     * @return The Y co-ordinate of this Entity in the game
     */
    public int getY() {
        return yPos;
    }
    
    /**
     * Sets the position of the Entity in the game
     * @param x The new X position for this Entity
     * @param y The new Y position for this Entity
     */
    public void setPosition (int x, int y) {
        xPos = x;
        yPos = y;
    }
    
    /**
     * Changes the current health value for this Entity, setting the health to
     * maxHealth if the change would cause the health attribute to exceed maxHealth
     * @param change An integer representing the change in health for this Entity.
     * Passing a positive value will increase the health, passing a negative value
     * will decrease the health.
     */
    public void changeHealth(double change) {
        health += change;
        if (health > maxHealth)
            health = maxHealth;
    }
     
     /**
     * Increases max health of player
     * @param value number of health to increase maxHealth
     */
    public void increaseMaxHealth(double value) {
        maxHealth += value;
    }
    
    /**
     * Returns the current health value for this Entity
     * @return the value of the health attribute for this Entity
     */
    public double getHealth() {
        return health;
    }
     
    /**
     * Returns the maxHealth value for this Entity
     * @return the value of the maxHealth attribute for this Entity
     */
    public double getMaxHealth() {
        return maxHealth;
    }
    
    /**
     * Returns the damage value of the entity
     * @return the value of entity's damage
     */
    public double getDmg() {
        return dmg;
    }
    
    /**
     * Returns the percentage chance of dodging attack by Entity
     * @return the value of dodgeChance 
     */
    public double getDodgeChance() {
        return dodgeChance;
    }
    
    /**
     * Increases damage value of Entity
     * @param value amount of damage to increase
     */
    public void increaseDmg(double value) {
        dmg += value;
    }
    
    /**
     * Increases dodgeChance value of Entity
     * @param value percentage amount to increase dodgeChance
     */
    public void increaseDodgeChance(double value) {
        dodgeChance += value;
    }
    
    /**
     * Returns the type of this Entity, either EntityType.PLAYER or EntityType.MONSTER
     * @return the EntityType of this entity
     */
    public EntityType getType() {
        return type;
    }
    
}
