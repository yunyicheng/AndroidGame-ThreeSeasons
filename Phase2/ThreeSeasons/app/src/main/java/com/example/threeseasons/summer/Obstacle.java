package com.example.threeseasons.summer;

/**
 * The obstacle in the game
 */
class Obstacle extends SummerItem {

    /**
     * The change in x coordinate each time the obstacle moves
     */
    private static final int MOVEMENT_CHANGE = -50;

    /**
     * Construct the obstacle object
     *
     * @param x      the x coordinate of the left line of the obstacle's image
     * @param y      the y coordinate of the upper line of the obstacle's image
     * @param width  the width of the obstacle's image
     * @param height the height of the obstacle's image
     */
    Obstacle(int x, int y, int width, int height) {
        super(x, y, width, height);
    }

    /**
     * Make the obstacle move horizontally
     */
    void move() {
        this.setX(this.getX() + MOVEMENT_CHANGE); // The obstacle moves to the left
    }

}
