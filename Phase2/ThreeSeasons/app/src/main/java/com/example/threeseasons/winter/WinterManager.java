package com.example.threeseasons.winter;

import com.example.threeseasons.data.DatabaseHelper;
import com.example.threeseasons.data.User;


class WinterManager {

    /**
     * When game is on, it can choose to save user to file, pop jade, or end game.
     */
    interface OnGameProcessListener {

        void saveUserToFile(String fileName);

        void endGame();

        void popJade();
    }

    /**
     * When performing animation, it can perform any of the following methods about animation.
     */
    interface OnAnimationListener {

        void onAnimationMove(String buttonName, String direction, int delay);

        void onAnimationAppear(String buttonName, int delay);

        void onAnimationDisappear(String buttonName, int delay);

        void onSetToSamePosition(String buttonName1, String buttonName2);

        void onSetPosition(String buttonName, int position);

        void onChangeDisplay(String buttonName1, String buttonName2);
    }

    /**
     * The OnGameProcessListener for this manager.
     */
    private OnGameProcessListener onGameProcessListener;
    /**
     * The OnAnimationListener for this interactor.
     */
    private OnAnimationListener onAnimationListener;
    /**
     * The PictureMover which decides pictures' behaviors.
     */
    private PictureMover pictureMover;
    /**
     * Database that stores user information.
     */
    private DatabaseHelper database;
    /**
     * User object that stores the user information.
     */
    private User user;
    /**
     * Current step of the game.
     */
    private int step = 1;
    /**
     * The score earned by the player in level 3 so far
     */
    private int score;
    /**
     * The total number of moves the player has performed.
     */
    private int numMoves;
    /**
     * Time since game started.
     */
    private int time;
    /**
     * The number of jade pendant the player has performed.
     */
    private int jade;
    /**
     * The total number of moves the player has performed at last step.
     */
    private int lastStepMoves;
    /**
     * The time at the last step.
     */
    private int lastStepTime;
    /**
     * The name of the third game part Winter.
     */
    private static final String GAME_NAME = "Winter";
    /**
     * Constant string representing the name of the game.
     */
    private static final String TOTAL_GAME_NAME = "Three Seasons";
    /**
     * Constant representing the up-left position for pictures
     */
    private static final int UP_LEFT = 1;
    /**
     * Constant representing the up-right position for pictures
     */
    private static final int UP_RIGHT = 2;
    /**
     * Constant representing the down-left position for pictures
     */
    private static final int DOWN_LEFT = 3;
    /**
     * Constant representing the down-right position for pictures
     */
    private static final int DOWN_RIGHT = 4;
    /**
     * Name of first ImageButton for step 1.
     */
    private static final String BUTTON1_1 = "1_1";
    /**
     * Name of second ImageButton for step 1.
     */
    private static final String BUTTON1_2 = "1_2";
    /**
     * Name of first ImageButton nonactive in game.
     */
    private static final String BUTTON_B1 = "B1";
    /**
     * Name of second ImageButton nonactive in game.
     */
    private static final String BUTTON_B2 = "B2";
    /**
     * Name of first ImageButton for step 2.
     */
    private static final String BUTTON2_1 = "2_1";
    /**
     * Name of second ImageButton for step 2.
     */
    private static final String BUTTON2_2 = "2_2";
    /**
     * Name of second ImageButton for step 3.
     */
    private static final String BUTTON3_2 = "3_2";
    /**
     * Picture in up-left position.
     */
    private Picture upLeftPicture;
    /**
     * Picture in up-right position.
     */
    private Picture upRightPicture;
    /**
     * Picture in down-left position.
     */
    private Picture downLeftPicture;
    /**
     * Picture in down-right position.
     */
    private Picture downRightPicture;

    /**
     * Constructor for the manager class.
     *
     * @param database Database that stores user information.
     * @param user User object that stores the user information.
     */
    WinterManager(DatabaseHelper database, User user) {
        this.database = database;
        this.user = user;
        setupData();

        initPictures();

        StepManager stepManager = new StepManager(this, downLeftPicture, upRightPicture);
        this.pictureMover = new PictureMover(this, stepManager);
    }

    /**
     * Initiate the four pictures in their positions.
     */
    private void initPictures() {

        Picture p1 = new Picture(BUTTON1_1);
        Picture p2 = new Picture(BUTTON_B1);
        Picture p3 = new Picture(BUTTON_B2);
        Picture p4 = new Picture(BUTTON1_2);

        setUpLeftPicture(p1);
        setUpRightPicture(p2);
        setDownLeftPicture(p3);
        setDownRightPicture(p4);

        p1.setActivePic(p4);
        p1.setNextButtonName(BUTTON2_1);
        p4.setActivePic(p1);
        p4.setNextButtonName(BUTTON2_1);
        p3.setNextButtonName(BUTTON2_2);
        p2.setNextButtonName(BUTTON3_2);
    }

    /**
     * Set picture to a specified position.
     *
     * @param picture the picture to be set position.
     * @param position the position to be set to.
     */
    void setPicture(Picture picture, int position) {
        if (position == UP_LEFT) {
            setUpLeftPicture(picture);

        } else if (position == UP_RIGHT) {
            setUpRightPicture(picture);

        } else if (position == DOWN_LEFT) {
            setDownLeftPicture(picture);

        } else if (position == DOWN_RIGHT) {
            setDownRightPicture(picture);
        }
    }


    void move(Picture p, String direction) {
        pictureMover.move(p, direction);
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
        boolean updated = user.updateScore(GAME_NAME, getScore());
        if (updated) {
            database.updateScore(user, GAME_NAME);
        }
        boolean totalUpdated = user.updateScore(TOTAL_GAME_NAME, getScore());
        if (totalUpdated) {
            database.updateScore(user, TOTAL_GAME_NAME);
        }
    }

    /**
     * Return the current user.
     *
     * @return the current user.
     */
    User getUser() {
        return user;
    }

    /**
     * Return the number of moves.
     *
     * @return the number of moves.
     */
    int getNumMoves() {
        return numMoves;
    }

    /**
     * Increase the number of moves by one.
     */
    void addNumMoves() {
        numMoves++;
    }

    /**
     * Return the score this user has earned.
     *
     * @return the score this user has earned.
     */
    int getScore() {
        return score;
    }

    /**
     * Return the picture in the up-left position.
     *
     * @return the picture in the up-left position.
     */
    Picture getUpLeftPicture() {
        return upLeftPicture;
    }

    private void setUpLeftPicture(Picture upLeftPicture) {
        this.upLeftPicture = upLeftPicture;
        upLeftPicture.setPosition(UP_LEFT);
    }

    /**
     * Return the picture in the up-right position.
     *
     * @return the picture in the up-right position.
     */
    Picture getUpRightPicture() {
        return upRightPicture;
    }

    private void setUpRightPicture(Picture upRightPicture) {
        this.upRightPicture = upRightPicture;
        upRightPicture.setPosition(UP_RIGHT);
    }

    /**
     * Return the picture in the down-left position.
     *
     * @return the picture in the down-left position.
     */
    Picture getDownLeftPicture() {
        return downLeftPicture;
    }

    private void setDownLeftPicture(Picture downLeftPicture) {
        this.downLeftPicture = downLeftPicture;
        downLeftPicture.setPosition(DOWN_LEFT);
    }

    /**
     * Return the picture in the down-right position.
     *
     * @return the picture in the down-right position.
     */
    Picture getDownRightPicture() {
        return downRightPicture;
    }

    private void setDownRightPicture(Picture downRightPicture) {
        this.downRightPicture = downRightPicture;
        downRightPicture.setPosition(DOWN_RIGHT);
    }

    /**
     * Return the picture in the specified position.
     *
     * @param position the position
     * @return the picture in the up-left specified position.
     */
    Picture getPicture(int position) {
        switch (position) {
            case UP_LEFT:
                return upLeftPicture;
            case UP_RIGHT:
                return upRightPicture;
            case DOWN_LEFT:
                return downLeftPicture;
            case DOWN_RIGHT:
                return downRightPicture;
        }
        return null;
    }

    /**
     * Update the score on the screen and reports to the onGameProcessListener.
     */
    void updateScore() {
        int addScore = calculateScore();
        score += addScore;
        if (addScore >= 6 && step != 5) {
            onGameProcessListener.popJade();
        }
    }

    /**
     * Calculate the score for the player according to the time spent and moves made.
     *
     * @return the score for the player.
     */
    private int calculateScore() {

        int currentTime = time;
        int currentMoves = numMoves;
        int stepTime = currentTime - lastStepTime;
        int stepMoves = currentMoves - lastStepMoves;
        lastStepTime = currentTime;
        lastStepMoves = currentMoves;
        return (int) Math.ceil(12 / (stepTime + 1)) + (int) Math.ceil(8 / (stepMoves + 1));
    }

    /**
     * Set the current step for the player.
     *
     * @param step the step we want to set
     */
    void setStep(int step) {
        this.step = step;
        if (step == 5) {
            userUpdateScore();
            onGameProcessListener.saveUserToFile(database.getUserFile(user.getUsername()));
            onGameProcessListener.endGame();
        }
    }

    /**
     * Add time spent by player.
     */
    void addTime() {
        time += 1;
    }

    /**
     * Get time spent by player.
     *
     * @return time spent by player
     */
    int getTime() {
        return time;
    }

    /**
     * Add jade earned by player.
     */
    void addJade() {
        jade++;
    }

    /**
     * Get jades earned by player.
     *
     * @return jades earned by player
     */
    int getJade() {
        return jade;
    }

    /**
     * Set OnGameProcessListener.
     *
     * @param onGameProcessListener the OnGameProcessListener we want to set.
     */
    void setOnGameProcessListener(OnGameProcessListener onGameProcessListener) {
        this.onGameProcessListener = onGameProcessListener;
    }

    /**
     * Set OnAnimationListener.
     *
     * @param onAnimationListener the OnAnimationListener we want to set.
     */
    void setOnAnimationListener(OnAnimationListener onAnimationListener) {
        this.onAnimationListener = onAnimationListener;
    }

    /**
     * Animate ImageButton in certain direction with a delay.
     *
     * @param buttonName the name of the ImageButton to be animated.
     * @param direction  String representing the direction of the animation.
     * @param delay      the length of delay for this animation.
     */
    void animateMove(String buttonName, String direction, int delay) {
        onAnimationListener.onAnimationMove(buttonName, direction, delay);
    }

    /**
     * Animate ImageButton to appear on the screen.
     *
     * @param buttonName the name of the ImageButton to be animated.
     * @param delay      the length of delay for this animation.
     */
    void animationAppear(String buttonName, int delay) {
        onAnimationListener.onAnimationAppear(buttonName, delay);
    }

    /**
     * Animate ImageButton to disappear from the screen.
     *
     * @param buttonName the name of the ImageButton to be animated.
     * @param delay      the length of delay for this animation.
     */
    void animationDisappear(String buttonName, int delay) {
        onAnimationListener.onAnimationDisappear(buttonName, delay);
    }

    /**
     * Make an ImageButton disappear and the other appear.
     *
     * @param buttonName1 the name of the ImageButton to disappear.
     * @param buttonName2 the name of the ImageButton to appear.
     */
    void changeDisplay(String buttonName1, String buttonName2) {
        onAnimationListener.onChangeDisplay(buttonName1, buttonName2);
    }

    /**
     * Set an ImageButton to the same position as the other.
     *
     * @param buttonName1 the name of the ImageButton to be set position as the other.
     * @param buttonName2 the name of the ImageButton to get position from.
     */
    void setToSamePosition(String buttonName1, String buttonName2) {
        onAnimationListener.onSetToSamePosition(buttonName1, buttonName2);
    }

    /**
     * Set an ImageButton to a certain position.
     *
     * @param buttonName the name of the ImageButton.
     * @param position   the position the ImageButton should be set to.
     */
    void setPosition(String buttonName, int position) {
        onAnimationListener.onSetPosition(buttonName, position);
    }

}
