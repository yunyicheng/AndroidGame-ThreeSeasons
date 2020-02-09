package com.example.threeseasons.summer;

/**
 * The jade in the game
 */
class Jade extends SummerItem {
    /**
     * The change in x coordinate each time the jade moves
     */
    private static final int MOVEMENT_CHANGE = -20;

    /**
     * Construct the jade object
     *
     * @param x      the x coordinate of the left line of the jade's image
     * @param y      the y coordinate of the upper line of the jade's image
     * @param width  the width of the jade's image
     * @param height the height of the jade's image
     */
    Jade(int x, int y, int width, int height) {
        super(x, y, width, height);
    }

    /**
     * Make the jade move horizontally
     */
    void move() {
        this.setX(this.getX() + MOVEMENT_CHANGE); // The jade moves to the left
    }
}
