package com.example.threeseasons.autumn;


import com.example.threeseasons.data.DatabaseHelper;
import com.example.threeseasons.data.User;

class AutumnManager {

    interface OnGameProcessListener {
        /**
         * End the game
         */
        void endGame();

        /**
         * Pop a jade on the screen.
         */
        void popJade();

        /**
         * Save current user to the corresponding file in the database.
         *
         * @param fileName the name of the file.
         */
        void saveUserToFile(String fileName);
    }

    /**
     * The OnGameProcessListener for this manager.
     */
    private OnGameProcessListener onGameProcessListener;
    /**
     * Database that stores and user information
     */
    private DatabaseHelper database;
    /**
     * Current user
     */
    private User user;
    /**
     * Name of current game
     */
    private static final String GAME_NAME = "Autumn";
    /**
     * The time left of the 5-second count down timer
     */
    private int timeLeft = 5;
    /**
     * Current score of the user
     */
    private int score;
    /**
     * Current number of failures of the user
     */
    private int failure;
    /**
     * Current command for the game
     */
    private Command command;
    /**
     * Current number of successive correct answers of the user
     */
    private int streak;
    /**
     * Current max number of successive correct answers of the user
     */
    private int maxStreak;
    /**
     * Current number of stars of the user
     */
    private int jade;

    /**
     * Constructor of the interactor class
     *
     * @param database database that stores and user information
     * @param user current user
     */
    AutumnManager(DatabaseHelper database, User user) {
        this.database = database;
        this.user = user;
        setupData();
    }

    /**
     * Update the time of the count down timer
     *
     * @return time of count down timer
     */
    int updateTime() {
        int time = timeLeft;
        timeLeft -= 1;
        return time;
    }

    /**
     * Checks whether the count down timer is time up and returns whether the game is running.
     */
    void timeUp() {
        if (failure < 4) {
            timeLeft = 5;
            failure++;
            streak = 0;
        }
        else {
            onGameProcessListener.endGame();
        }
    }

    /**
     * Returns a new command with new color, shape and word.
     *
     * @return new command with new color, shape and word
     */
    Command newCommand() {
        command = new Command();
        return command;
    }

    /**
     * Checks whether the answer is correct, updates score, and return whether the game is
     * running.
     */
    void checkAnswer(int color) {
        if (failure < 4) {
            timeLeft = 5;
            if (command.commandColor() == color) {
                score += 1;
                streak += 1;
                updateMaxStreak();
            } else {
                failure += 1;
                streak = 0;
            }
        }
        else {
            userUpdateScore();
            onGameProcessListener.saveUserToFile(database.getUserFile(user.getUsername()));
            onGameProcessListener.endGame();
        }
    }

    /**
     * Check whether to pop a jade on the screen, and if true, pop a jade on the screen.
     */
    void generateJade(){
        if (this.streak % 3 == 0 && this.streak != 0) {
            onGameProcessListener.popJade();
        }
    }

    /**
     * Checks whether the current number of streaks is the max number of streaks, if true, update
     * the maxStreak.
     */
    private void updateMaxStreak() {
        if (this.streak > this.maxStreak){
            this.maxStreak = this.streak;
        }
    }

    /**
     * Update the number of jade.
     */
    void updateJade() {
        this.jade += 1;
    }

    /**
     * score getter
     *
     * @return the score of the current user.
     */
    int getScore() {
        return score;
    }

    /**
     * failure getter
     *
     * @return the number of failures of the current user.
     */
    int getFailure() {
        return failure;
    }

    /**
     * streak getter
     *
     * @return the number of streaks of the current user.
     */
    int getStreak() {
        return streak;
    }

    /**
     * maxStreak getter
     *
     * @return the max number of streaks of the current user.
     */
    int getMaxStreak() {
        return maxStreak;
    }

    /**
     * jade getter
     *
     * @return the number of jades of the current user.
     */
    int getJade() {
        return jade;
    }

    /**
     * Initiate user's data of this game if it does not exist in the database.
     */
    private void setupData() {
        if (!database.dataExists(user.getUsername(), GAME_NAME))
            database.addData(user.getUsername(), GAME_NAME);
    }

    /**
     * Updated the score of current user in both the user object and the database.
     */
    private void userUpdateScore() {
        boolean updated = user.updateScore(GAME_NAME, score);
        if (updated) {
            database.updateScore(user, GAME_NAME);
        }
    }

    /**
     * user getter
     *
     * @return the current user.
     */
    public User getUser() {
        return user;
    }

    /**
     * OnGameProcessListener setter
     *
     * @param onGameProcessListener OnGameProcessListener of the game
     */
    void setOnGameProcessListener(OnGameProcessListener onGameProcessListener) {
        this.onGameProcessListener = onGameProcessListener;
    }
}

