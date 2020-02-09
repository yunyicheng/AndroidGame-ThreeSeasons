package com.example.threeseasons.winter;

import com.example.threeseasons.data.User;

/**
 * Presenter for Winter.
 */
class WinterPresenter implements WinterManager.OnGameProcessListener, WinterManager.OnAnimationListener{

    /**
     * View for Winter.
     */
    private WinterView winterView;

    /**
     * Model for this activity.
     */
    private WinterManager winterManager;

    /**
     * Constructor of the presenter class.
     *
     * @param winterView View for winter
     * @param winterManager  The manager for Winter.
     */
    WinterPresenter(WinterView winterView, WinterManager winterManager) {
        this.winterView = winterView;
        this.winterManager = winterManager;
        winterManager.setOnGameProcessListener(this);
        winterManager.setOnAnimationListener(this);
    }

    /**
     * Get the picture at certain position.
     *
     * @param position the position
     *
     * @return the picture at position
     */
    Picture getPicture(int position) {
        return winterManager.getPicture(position);
    }

    /**
     * Move the picture in certain direction
     *
     * @param picture the Picture ought to be moved
     * @param  direction the String represents the direction the picture should move
     */
    void move(Picture picture, String direction) {
        winterManager.move(picture, direction);
    }


    /**
     * Get scores earned in Winter.
     *
     * @return the score earned by player.
     */
    int getScore() {
        return winterManager.getScore();
    }

    /**
     * Add time spent by user.
     */
    void addTime() {
        winterManager.addTime();
    }

    /**
     * Get time spent by player.
     *
     * @return the time spent by player
     */
    int getTime() {
        return winterManager.getTime();
    }

    /**
     * Get number of moves for player
     *
     * @return the number of moves the player makes
     */
    int getNumMoves() {
        return winterManager.getNumMoves();
    }

    /**
     * Add jade for player.
     */
    void addJade() {
        winterManager.addJade();
    }

    /**
     * Get jades earned in Winter.
     *
     * @return the jades earned by player
     */
    int getJade() {
        return winterManager.getJade();
    }


    /**
     * Save current user to the corresponding file in the database.
     *
     * @param fileName the name of the file
     */
    public void saveUserToFile(String fileName) {
        winterView.saveUserToFile(fileName);
    }

    /**
     * Return the current user.
     *
     * @return the current user.
     */
    User getUser() {
        return winterManager.getUser();
    }

    /**
     * End this game and go to WinterOverActivity.
     */
    public void endGame(){
        if (winterView != null){
            winterView.endGame();
        }
    }

    /**
     * Make the collection jade pendant appear on the screen.
     */
    public void popJade(){
        if (winterView != null){
            winterView.popJade();
        }
    }

    /**
     * Animate ImageButton in certain direction with a delay.
     *
     * @param buttonName the name of the ImageButton to be animated.
     * @param direction  String representing the direction of the animation.
     * @param delay      the length of delay for this animation.
     */
    public void onAnimationMove(String buttonName, String direction, int delay){
        if (winterView != null){
            winterView.animateMove(buttonName, direction, delay);
        }
    }

    /**
     * Animate ImageButton to appear on the screen.
     *
     * @param buttonName the name of the ImageButton to be animated.
     * @param delay      the length of delay for this animation.
     */
    public void onAnimationAppear(String buttonName, int delay){
        if (winterView != null){
            winterView.animationAppear(buttonName, delay);
        }
    }

    /**
     * Animate ImageButton to disappear from the screen.
     *
     * @param buttonName the name of the ImageButton to be animated.
     * @param delay      the length of delay for this animation.
     */
    public void onAnimationDisappear(String buttonName, int delay){
        if (winterView != null){
            winterView.animationDisappear(buttonName, delay);
        }
    }

    /**
     * Set an ImageButton to the same position as the other.
     *
     * @param buttonName1 the name of the ImageButton to be set position as the other.
     * @param buttonName2 the name of the ImageButton to get position from.
     */
    public void onSetToSamePosition(String buttonName1, String buttonName2){
        if (winterView != null){
            winterView.setToSamePosition(buttonName1, buttonName2);
        }
    }

    /**
     * Set an ImageButton to a certain position.
     *
     * @param buttonName the name of the ImageButton.
     * @param position   the position the ImageButton should be set to.
     */
    public void onSetPosition(String buttonName, int position){
        if (winterView != null){
            winterView.setPosition(buttonName, position);
        }
    }

    /**
     * Make an ImageButton disappear and the other appear.
     *
     * @param buttonName1 the name of the ImageButton to disappear.
     * @param buttonName2 the name of the ImageButton to appear.
     */
    public void onChangeDisplay(String buttonName1, String buttonName2){
        if (winterView != null){
            winterView.changeDisplay(buttonName1, buttonName2);
        }
    }

}
