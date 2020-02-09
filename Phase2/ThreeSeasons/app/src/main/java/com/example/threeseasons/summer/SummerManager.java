package com.example.threeseasons.summer;

import android.graphics.Bitmap;

import com.example.threeseasons.data.DatabaseHelper;
import com.example.threeseasons.data.User;

import java.util.ArrayList;

/**
 * The first game's manager.
 */
class SummerManager {

    interface OnGameOverListener {

        /**
         * End the game
         */
        void gameOver();

        /**
         * Save the user to file
         */
        void saveUserToFile(String fileName);
    }

    /**
     * OnGameOverListener of this game
     */
    private OnGameOverListener onGameOverListener;
    /**
     * Database that stores game state and user information
     */
    private DatabaseHelper database;
    /**
     * Current user
     */
    private User user;
    /**
     * Game status
     */
    private boolean isGameOver = false;
    /**
     * The list of obstacles for this game
     */
    private ArrayList<Obstacle> obstacles;
    /**
     * The list of jade-pendants for this game.
     */
    private ArrayList<Jade> jades;
    /**
     * The player for this game
     */
    private Player player;
    /**
     * The data for this game
     */
    private SummerData data;
    /**
     * The inner counter that counts every time we refresh the screen, up to clockUpper
     */
    private int innerClock = 0;
    /**
     * The upper bound of innerClock
     */
    private static final int CLOCK_UPPER = 10;
    /**
     * Reward points for each obstacle that the player passes.
     */
    private static final int OBSTACLE_POINT = 10;
    /**
     * The running speed of the player for calculating statistics.
     */
    private static final int SPEED = 10;
    /**
     * The list of obstacles that the player has scored for.
     */
    private ArrayList<Obstacle> scored_obstacles;
    /**
     * The list of jade-pendants that the player has earned.
     */
    private ArrayList<Jade> earned_jades;
    /**
     * The cool down time between two jumps.
     */
    private static final int JUMP_COOLDOWN = 2800;
    /**
     * The velocity acceleration for one tap
     */
    private static final int JUMP_ACCELERATION = - 85;

    /**
     * Width and height of the obstacle image
     */
    private int obstacleWidth, obstacleHeight;
    /**
     * Width and height of the jade image
     */
    private int jadeWidth, jadeHeight;
    /**
     * The device's width and device's height
     */
    private int deviceWidth, deviceHeight;
    /**
     * The height of land in this game.
     */
    private int landHeight;
    /**
     * Name of current game
     */
    private static final String GAME_NAME = "Summer";

    /**
     * Enum which contains all types of the SummerItems (except for player) that we might
     * generate in the game.
     */
    enum Item{
        OBSTACLE, JADE
    }

    /**
     * Construct the game manager
     *
     * @param database Database that stores game state and user information
     * @param user Current user
     */
    SummerManager(DatabaseHelper database, User user) {
        this.database = database;
        this.user = user;
        this.obstacles = new ArrayList<>();
        this.scored_obstacles = new ArrayList<>();
        this.jades = new ArrayList<>();
        this.earned_jades = new ArrayList<>();
        this.data = new SummerData();
        this.landHeight = 0;
        setupData();
    }

    /**
     * Return whether or not the game is over.
     *
     * @return whether or not the game is over.
     */
    boolean getIsGameOver() {
        return this.isGameOver;
    }

    /**
     * Return the list of obstacles for this game
     *
     * @return the obstacles object
     */
    ArrayList<Obstacle> getObstacles() {
        return this.obstacles;
    }

    /**
     * Return the list of jades for this game
     *
     * @return the jades object
     */
    ArrayList<Jade> getJades() {
        return this.jades;
    }

    /**
     * Return the player of the game
     *
     * @return the player object
     */
    Player getPlayer() {
        return this.player;
    }

    /**
     * Return the data of the game
     *
     * @return the data object
     */
    SummerData getData() {
        return this.data;
    }

    /**
     * Updates and checks the following things: (1) whether to randomly create a new obstacle
     * (2) whether to randomly create a new jade
     * (3) move all the items (4) check collision against obstacles during the move
     * (5) record the statistics (6) check if the game is over
     */
    void update() {
        // Check whether to create a new obstacle or not
        if (obstacleGeneratePermit()) {
            this.obstacles.add((Obstacle) itemFactory("OBSTACLE"));
        }

        // Check whether to create a new jade or not
        if (Math.random() > 0.995) {
            this.jades.add((Jade) itemFactory("JADE"));
        }

        // Move the Player
        player.move();
        player.adjustY();

        // Move the obstacles
        moveItems(obstacles);

        // Move the jades
        moveItems(jades);

        // Check collision against the obstacles
        for (Obstacle enemy : this.obstacles) {
            if (isCollied(enemy, player)) {
                this.isGameOver = true;
            }
        }

        // Update statistics

        collectJades();
        scoreByObstacles();
        collectGarbage();
        updateTime(System.currentTimeMillis());
        updateDistance();

        // Check if the game is over
        if (this.isGameOver) {
            userUpdateScore();
            onGameOverListener.saveUserToFile(database.getUserFile(user.getUsername()));
            onGameOverListener.gameOver();
        }
    }

    /**
     * A helper method for generating the required items for the corresponding String item.
     *
     * @param item the SummerItem that we required.
     * @return SummerItem that we want.
     */
    private SummerItem itemFactory(String item){
        switch (Item.valueOf(item)){
            case OBSTACLE:
                return new Obstacle(deviceWidth, landHeight - obstacleHeight, obstacleWidth, obstacleHeight);
            case JADE:
                return new Jade(deviceWidth, deviceHeight / 3 - jadeHeight, jadeWidth, jadeHeight);
            default:
                return null;
        }
    }

    /**
     * A helper method for moving all the items in the given ArrayList as they should. Every item
     * inside has a move method since they are objects of SummerItem.
     *
     * @param items the ArrayList of item that we want to move.
     */
    private void moveItems(ArrayList items){
        for (int i = 0; i < items.size(); i++){
            ((SummerItem)items.get(i)).move();
        }
    }

    /**
     * Check whether or not the obstacle has collied with the player.
     *
     * @param item the item that is being checked
     * @param player the player that is being checked
     * @return whether or not there is a collision.
     */
    private boolean isCollied(SummerItem item, Player player){
        // Multiple cases where there is not a collision
        if (player.getY() + player.getHeight() <= item.getY()){
            // player is over the item
            return false;
        } else if (player.getY() >= item.getY() + item.getHeight()){
            // player is below the item
            return false;
        } else if (player.getX() + player.getWidth() <= item.getX()){
            // player is left to the item
            return false;
        } else if (player.getX() >= item.getX() + item.getWidth()) {
            // player is right to the item
            return false;
        } else {
            return true;
        }
    }

    /**
     * Check if the player can score by obstacles and if so, score some points.
     */
    private void scoreByObstacles() {
        for (Obstacle monster : obstacles) {
            if ((!this.isGameOver)
                    & (!scored_obstacles.contains(monster))
                    & (monster.getX() + monster.getWidth()) < player.getX()) {
                data.setScore(data.getScore() + OBSTACLE_POINT);
                scored_obstacles.add(monster);
            }
        }
    }

    /**
     * Check if the player can score by jades and if so, score some points.
     */
    private void collectJades() {
        int index = 0;
        while (index < jades.size()){
            if (isCollied(jades.get(index), player)) {
                earned_jades.add(jades.get(index));
                this.data.setNumberJadesEarned(data.getNumberJadesEarned() + 1);

                // Jade disappears once they are collected
                jades.remove(jades.get(index));
            } else {
                index ++;
            }
        }
    }

    /**
     * Delete useless non-player-characters that have moved too far away from the screen
     */
    private void collectGarbage() {
        ArrayList<Obstacle> garbage = new ArrayList<>();
        // Find out all obstacles that have been too far away from the screen
        for (Obstacle monster : scored_obstacles) {
            if (monster.getX() < (-obstacleWidth)) {
                garbage.add(monster);
            }
        }
        // Delete those obstacles
        for (Obstacle monster : garbage) {
            scored_obstacles.remove(monster);
            obstacles.remove(monster);
        }

        // Delete useless jades
        int index = 0;
        while (index < jades.size()){
            if (jades.get(index).getX() < (-jades.get(index).getWidth())) {
                jades.remove(jades.get(index));
            } else  {
                index ++;
            }
        }
    }

    /**
     * A helper method for updating the playing time of the game
     *
     * @param currentTime the current time of the game
     */
    private void updateTime(long currentTime) {
        if (!this.isGameOver) {
            data.setDuration((currentTime - data.getStartTime()) / 1000);
        }
    }

    /**
     * A helper method for updating the distance that the player has run for the game
     */
    private void updateDistance() {
        if (!this.isGameOver) {
            int time = Math.toIntExact(data.getDuration());
            data.setDistance(time * SPEED);
        }
    }

    /**
     * This is a clock that counts automatically in every thread refresh. It ensures that the
     * obstacles will not be generated sequentially.
     *
     * @return true if an obstacle can be generated and false otherwise
     */
    private boolean obstacleGeneratePermit() {
        boolean isTime = (innerClock == CLOCK_UPPER); // Whether it is the time to generate.
        boolean isGenerate = false; // Make it false by default, easier to return.
        if (isTime) {
            innerClock = 0; // Turn the counter back to 0.
            isGenerate = Math.random() > 0.5;
        } else {
            innerClock++; // Let the counter work.
        }
        return isTime & isGenerate;
    }

    /**
     * Record the time when the user finishes a tap
     */
    void actionUp() {
        data.setLastJumpTime(System.currentTimeMillis());
    }

    /**
     * Record the time when user initializes a tap and decides if this tap is legal (not tapping
     * during a short period of time) and if so, triggers a jump
     */
    void actionDown() {
        long thisJumpTime = System.currentTimeMillis();
        long tapTimeDifference = thisJumpTime - data.getLastJumpTime();

        if (tapTimeDifference >= JUMP_COOLDOWN) {
            player.setVelocity(JUMP_ACCELERATION);
        }
    }

    /**
     * Get the information about the image of obstacle for creating obstacles
     */
    void addObstacleInfo(Bitmap image) {
        obstacleWidth = image.getWidth();
        obstacleHeight = image.getHeight();
    }

    /**
     * Get the information about the image of jade for creating jades
     */
    void addJadeInfo(Bitmap image) {
        jadeWidth = image.getWidth();
        jadeHeight = image.getHeight();
    }

    /**
     * Get the information about the image of player and create a player of the game
     */
    void createPlayer(Bitmap image) {
        int playerWidth = image.getWidth();
        int playerHeight = image.getHeight();

        player = new Player(deviceWidth / 8, landHeight - playerHeight, playerWidth, playerHeight);
        player.setLowerBound(landHeight - playerHeight);
    }

    /**
     * Get the information about the device
     */
    void addDeviceInfo(int deviceWidth, int deviceHeight) {
        this.deviceWidth = deviceWidth;
        this.deviceHeight = deviceHeight;
        this.landHeight = (3 * deviceHeight) / 4;
    }

    /**
     * Return the score of the game
     *
     * @return the score of the game
     */
    int getScore(){
        return data.getScore();
    }

    /**
     * Set up the data for user if he is not in the database yet
     */
    private void setupData() {
        if (!database.dataExists(user.getUsername(), GAME_NAME))
            database.addData(user.getUsername(), GAME_NAME);
    }

    /**
     * Update the user's score in database
     */
    private void userUpdateScore() {
        boolean updated = user.updateScore(GAME_NAME, getScore());
        if (updated) {
            database.updateScore(user, GAME_NAME);
        }
    }

    /**
     * Return the user
     *
     * @return the user object
     */
    User getUser(){
        return user;
    }

    /**
     * Update this manager's OnGameOverListener
     *
     * @param onGameOverListener the OnGameOverListener to be set
     */
    void setOnGameOverListener(OnGameOverListener onGameOverListener){
        this.onGameOverListener = onGameOverListener;
    }
}
