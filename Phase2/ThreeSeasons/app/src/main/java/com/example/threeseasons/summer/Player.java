package com.example.threeseasons.summer;

/**
 * The player in this game
 */
class Player extends SummerItem {

    /**
     * The current velocity of player.
     */
    private int velocity = 0;

    /**
     * The gravity that is always acting on the player during the game.
     */
    private static final int GRAVITY = 11;

    /**
     * The y coordinate representing the lowest place that the player can be.
     */
    private int lowerBound = 0;

    /**
     * Construct the player object
     *
     * @param x      the x coordinate of the left line of the player's image
     * @param y      the y coordinate of the upper line of the player's image
     * @param width  the width of the player's image
     * @param height the height of the player's image
     */
    Player(int x, int y, int width, int height) {
        super(x, y, width, height);
    }

    /**
     * Change the velocity of the player.
     */
    void setVelocity(int velocity) {
        this.velocity = velocity;
    }

    /**
     * Make the player jump vertically
     */
    void move() {
        if (velocity < 0 | this.getY() < lowerBound) {
            setVelocity(velocity + GRAVITY);
            this.setY(this.getY() + velocity);
        }
    }

    /**
     * Change the lower bound of the player
     */
    void setLowerBound(int lowerBound) {
        this.lowerBound = lowerBound;
    }

    /**
     * Adjust the player's y location to prevent the player from jumping out of the screen or
     * dropping below the ground after movement
     */
    void adjustY() {
        if (getY() > lowerBound) { // To prevent the player from dropping below the ground
            this.setY(lowerBound);
        } else if (getY() < 0) { // To prevent the player from jumping out of the screen
            this.setY(0);
            this.setVelocity(0);
        }
    }
}
