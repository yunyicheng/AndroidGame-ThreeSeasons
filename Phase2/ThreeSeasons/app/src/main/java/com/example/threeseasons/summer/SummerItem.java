package com.example.threeseasons.summer;

/**
 * The abstract class of all game items.
 */
abstract class SummerItem {

    /**
     * The coordinate of the left line of this item
     */
    private int x;

    /**
     * The coordinate of the upper line of this item
     */
    private int y;

    /**
     * The width of the picture of this item
     */
    private int width;

    /**
     * The height of the picture of this item
     */
    private int height;

    /**
     * Construct the game item
     *
     * @param x      the x coordinate of the left line of this item's image
     * @param y      the y coordinate of the upper line of this item's image
     * @param width  the width of this item's image
     * @param height the height of this item's image
     */
    SummerItem(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    /**
     * Move the game item
     */
    abstract void move();

    /**
     * Return the x coordinate of this item.
     *
     * @return the item's x.
     */
    int getX() {
        return x;
    }

    /**
     * Change the x coordinate of this item.
     */
    void setX(int x) {
        this.x = x;
    }

    /**
     * Return the y coordinate of this item.
     *
     * @return the item's y.
     */
    int getY() {
        return y;
    }

    /**
     * Change the y coordinate of this item.
     */
    void setY(int y) {
        this.y = y;
    }

    /**
     * Return the width of the picture for this item.
     *
     * @return the item's width.
     */
    int getWidth() {
        return width;
    }

    /**
     * Change the width of the picture for this item.
     */
    void setWidth(int width) {
        this.width = width;
    }

    /**
     * Return the height of the picture for this item.
     *
     * @return the item's height.
     */
    int getHeight() {
        return height;
    }

    /**
     * Change the height of the picture for this item.
     */
    void setHeight(int height) {
        this.height = height;
    }
}
