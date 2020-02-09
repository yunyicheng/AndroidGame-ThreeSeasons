package com.example.threeseasons.summer;

/**
 * The first game's data
 */
class SummerData {

    /**
     * Overall score for this game
     */
    private int score = 0;

    /**
     * Overall time the player has played for this game
     */
    private long duration = 0;

    /**
     * Overall distance the player has run for this game
     */
    private int distance = 0;

    /**
     * The starting time of the game
     */
    private long startTime;

    /**
     * The time when the player jumps for the last time
     */
    private long lastJumpTime = 0;

    /**
     * The number of jades collected during the game
     */
    private int numberJadesEarned = 0;

    /**
     * Construct the data storage for this game
     */
    SummerData() {
        this.startTime = System.currentTimeMillis();
    }

    /**
     * Return the score of the game
     *
     * @return the score object
     */
    int getScore() {
        return this.score;
    }

    /**
     * Return the playing time of the game
     *
     * @return the duration object
     */
    long getDuration() {
        return this.duration;
    }

    /**
     * Return how far the player has run.
     *
     * @return the distance object
     */
    int getDistance() {
        return this.distance;
    }

    /**
     * Return the starting time of the game
     *
     * @return the startTime object
     */
    long getStartTime() {
        return this.startTime;
    }

    /**
     * Return the player's last jump time.
     *
     * @return the lastJumpTime object
     */
    long getLastJumpTime() {
        return this.lastJumpTime;
    }

    /**
     * Change the score of this game.
     */
    void setScore(int score) {
        this.score = score;
    }

    /**
     * Change the duration of this game.
     */
    void setDuration(long duration) {
        this.duration = duration;
    }

    /**
     * Change the distance that the player has run for this game.
     */
    void setDistance(int distance) {
        this.distance = distance;
    }

    /**
     * Record the last jump time of the player.
     */
    void setLastJumpTime(long lastJumpTime) {
        this.lastJumpTime = lastJumpTime;
    }

    /**
     * Return the number of jades earned during this game.
     *
     * @return the getJadesEarned object
     */
    int getNumberJadesEarned() {
        return this.numberJadesEarned;
    }

    /**
     * Change the number of jades earned during this game.
     */
    void setNumberJadesEarned(int numJadesEarned) {
        this.numberJadesEarned = numJadesEarned;
    }

}
