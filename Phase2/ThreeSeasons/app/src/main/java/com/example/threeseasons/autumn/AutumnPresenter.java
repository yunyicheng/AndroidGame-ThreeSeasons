package com.example.threeseasons.autumn;

import com.example.threeseasons.data.User;

class AutumnPresenter implements AutumnManager.OnGameProcessListener {

    /**
     * Game status, true if game is not over, false otherwise
     */
    private boolean gameRunning;
    /**
     * current manager of this game.
     */
    private AutumnManager autumnManager;
    /**
     * current AutumnView of this game.
     */
    private AutumnView autumnView;

    /**
     * Constructor of the presenter class
     *
     * @param autumnView    the view of this game.
     * @param autumnManager the interactor of this game.
     */
    AutumnPresenter(AutumnView autumnView, AutumnManager autumnManager) {
        this.autumnView = autumnView;
        this.autumnManager = autumnManager;
        autumnManager.setOnGameProcessListener(this);
        this.gameRunning = true;
    }

    /**
     * Returns the current time of count down timer.
     *
     * @return time of count down timer
     */
    String getTime() {
        String time = String.valueOf(autumnManager.updateTime());
        return time + "\"";
    }

    /**
     * Returns the current score of the user.
     *
     * @return current score of the user
     */
    int getScore() {
        return autumnManager.getScore();
    }

    /**
     * Returns the current number of failures of the user.
     *
     * @return current number of failures of the user
     */
    String getFailure() {
        return String.valueOf(autumnManager.getFailure());
    }

    /**
     * Returns the current number of streaks of the user.
     *
     * @return current number of streaks of the user
     */
    String getStreak() {
        return String.valueOf(autumnManager.getStreak());
    }

    /**
     * Returns the current max number of streaks of the user.
     *
     * @return current max number of streaks of the user
     */
    String getMaxStreak() {
        return String.valueOf(autumnManager.getMaxStreak());
    }

    /**
     * Returns the current number of jades of the user.
     *
     * @return current number of jades of the user
     */
    int getJade() {
        return autumnManager.getJade();
    }

    /**
     * Returns whether the count down timer is time up
     */
    void timeUp() {
        autumnManager.timeUp();
    }

    /**
     * Returns a new command with new color, shape and word.
     *
     * @return new command with new color, shape and word
     */
    Command newCommand() {
        return autumnManager.newCommand();
    }

    /**
     * Returns whether the game is running.
     */
    void checkAnswer(int color) {
        autumnManager.checkAnswer(color);
    }

    /**
     * Pop a jade on the screen.
     */
    void generateJade() {
        autumnManager.generateJade();
    }

    /**
     * Update the number of jades of the user.
     */
    void updateJade() {
        autumnManager.updateJade();
    }

    /**
     * Save the updated scoreHistory of current user to User
     */
    public void saveUserToFile(String fileName) {
        autumnView.saveUserToFile(fileName);
    }

    /**
     * End the game to show the score board for this turn, set gameRunning to false and stop the
     * count-down timer.
     */
    public void endGame() {
        autumnView.endGame();
    }

    /**
     * Pop a jade on the screen.
     */
    public void popJade() {
        autumnView.popJade();
    }

    /**
     * Returns whether the game is running.
     *
     * @return whether the game is running
     */
    boolean getGameRunning() {
        return this.gameRunning;
    }

    /**
     * Set gameRunning.
     *
     * @param newGameRunning new boolean indicating whether the game is running
     */
    void setGameRunning(boolean newGameRunning) {
        this.gameRunning = newGameRunning;
    }

    /**
     * Returns the current user.
     *
     * @return the current user.
     */
    User getUser() {
        return autumnManager.getUser();
    }
}
