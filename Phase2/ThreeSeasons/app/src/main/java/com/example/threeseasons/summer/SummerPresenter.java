package com.example.threeseasons.summer;

import android.graphics.Bitmap;
import com.example.threeseasons.data.User;
import java.util.ArrayList;

/**
 * The first game's presenter.
 */
class SummerPresenter implements SummerManager.OnGameOverListener{

    /**
     * Manager for the current game
     */
    private SummerManager summerManager;
    /**
     * View for the current game
     */
    private SummerView summerView;

    /**
     * Construct the presenter for this game
     *
     * @param summerView the view for this game
     * @param summerManager the manager for this game
     */
    SummerPresenter(SummerView summerView, SummerManager summerManager) {
        this.summerView = summerView;
        this.summerManager = summerManager;
        summerManager.setOnGameOverListener(this);
    }

    /**
     * Update the current game
     */
    void update() {
        summerManager.update();
    }

    /**
     * User taps up on the screen
     */
    void actionUp() {
        summerManager.actionUp();
    }

    /**
     * User taps down on the screen
     */
    void actionDown() {
        summerManager.actionDown();
    }

    /**
     * Pass information about obstacle's image for constructing obstacles
     */
    void addObstacleInfo(Bitmap image) {
        summerManager.addObstacleInfo(image);
    }

    /**
     * Pass information about jade's image for constructing jades
     */
    void addJadeInfo(Bitmap image) {
        summerManager.addJadeInfo(image);
    }

    /**
     * Pass information about player's image and create a player
     */
    void createPlayer(Bitmap image) {
        summerManager.createPlayer(image);
    }

    /**
     * Return the list of obstacles for this game
     *
     * @return the array list of obstacles of this game
     */
    ArrayList<Obstacle> getObstacles(){
        return summerManager.getObstacles();
    }

    /**
     * Return the list of jades for this game
     *
     * @return the array list of jades of this game
     */
    ArrayList<Jade> getJades(){
        return summerManager.getJades();
    }

    /**
     * Return the player of the game
     *
     * @return the player object of this game
     */
    Player getPlayer(){
        return summerManager.getPlayer();
    }

    /**
     * Return the data of the game
     *
     * @return the data object of this game
     */
    SummerData getData() {
        return summerManager.getData();
    }

    /**
     * Return whether or not the game is over.
     *
     * @return whether or not the game is over.
     */
    boolean getIsGameOver() {
        return summerManager.getIsGameOver();
    }

    /**
     * Return the user
     *
     * @return the user object
     */
    User getUser(){
        return summerManager.getUser();
    }

    /**
     * Pass information about the device for constructing game items
     */
    void addDeviceInfo(int deviceWidth, int deviceHeight) {
        summerManager.addDeviceInfo(deviceWidth, deviceHeight);
    }

    /**
     * Return the score of this game
     */
    int getScore() {
        return summerManager.getScore();
    }

    /**
     * End the game
     */
    @Override
    public void gameOver(){
        summerView.gameOver();
    }

    /**
     * Save the user to file
     */
    @Override
    public void saveUserToFile(String fileName){
        summerView.saveUserToFile(fileName);
    }
}
