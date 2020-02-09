package com.example.threeseasons.winter;

interface WinterView {

    /**
     * Set pictures one to four to their corresponding postions on the screen.
     */
    void locatePictures();

    /**
     * Animate ImageButton in certain direction with a delay.
     *
     * @param buttonName the name of the ImageButton to be animated.
     * @param direction  String representing the direction of the animation.
     * @param delay      the length of delay for this animation.
     */
    void animateMove(String buttonName, String direction, int delay);

    /**
     * Animate ImageButton to appear on the screen.
     *
     * @param buttonName the name of the ImageButton to be animated.
     * @param delay      the length of delay for this animation.
     */
    void animationAppear(String buttonName, int delay);

    /**
     * Animate ImageButton to disappear from the screen.
     *
     * @param buttonName the name of the ImageButton to be animated.
     * @param delay      the length of delay for this animation.
     */
    void animationDisappear(String buttonName, int delay);

    /**
     * Make an ImageButton disappear and the other appear.
     *
     * @param buttonName1 the name of the ImageButton to disappear.
     * @param buttonName2 the name of the ImageButton to appear.
     */
    void changeDisplay(String buttonName1, String buttonName2);

    /**
     * Set an ImageButton to the same position as the other.
     *
     * @param buttonName1 the name of the ImageButton to be set position as the other.
     * @param buttonName2 the name of the ImageButton to get position from.
     */
    void setToSamePosition(String buttonName1, String buttonName2);

    /**
     * Set an ImageButton to a certain position.
     *
     * @param buttonName the name of the ImageButton.
     * @param position   the position the ImageButton should be set to.
     */
    void setPosition(String buttonName, int position);

    /**
     * Make the collection jade pendant appear on the screen.
     */
    void popJade();

    /**
     * Save current user to the corresponding file in the database.
     *
     * @param fileName the name of the file.
     */
    void saveUserToFile(String fileName);

    /**
     * End the game.
     */
    void endGame();
}
