package com.example.threeseasons.winter;

/**
 * Picture objects are the fundamental objects that make up the puzzle.
 * A puzzle is consists of four pictures, each in one of four positions:
 * up-left, up-right, down-left, down-right.
 */

class Picture {

    /**
     * Integer that represents this picture's position.
     */
    private int position;
    /**
     * The picture that can trigger next step with this picture.
     */
    private Picture activePic;
    /**
     * The name of the previous ImageButton this picture stored.
     */
    private String prevButtonName;
    /**
     * The name of the ImageButton this picture currently stores.
     */
    private String currButtonName;
    /**
     * The name of the next ImageButton this picture should store.
     */
    private String nextButtonName;


    /**
     * Constructs the Picture object
     *
     * @param imageButtonName the ImageButton for this picture to store.
     */
    Picture(String imageButtonName) {
        this.currButtonName = imageButtonName;
    }

    /**
     * Stores the next ImageButton when game goes to the next step
     */
    void nextStep() {
        this.prevButtonName = this.currButtonName;
        this.currButtonName = this.nextButtonName;
    }

    /**
     * Get the position of this picture.
     *
     * @return the position of this picture
     */
    int getPosition() {
        return position;
    }

    /**
     * Set the position of this picture.
     *
     * @param position the position of this picture.
     */
    void setPosition(int position) {
        this.position = position;
    }

    /**
     * Get the name of the previous ImageButton this picture stored.
     *
     * @return the name of the previous ImageButton.
     */
    String getPrevButtonName() {
        return prevButtonName;
    }

    /**
     * Get the name of the ImageButton this picture currently stores.
     *
     * @return the name of the ImageButton this picture currently stores.
     */
    String getCurrButtonName() {
        return currButtonName;
    }

    /**
     * Set the name of the ImageButton this picture currently stores.
     *
     * @param imageButtonName the name of the ImageButton this picture currently stores.
     */
    void setCurrButtonName(String imageButtonName) {
        this.prevButtonName = this.currButtonName;
        this.currButtonName = imageButtonName;
    }

    /**
     * Set the name of the next ImageButton this picture should store.
     *
     * @param nextButton the name of the next ImageButton this picture should store.
     */
    void setNextButtonName(String nextButton) {
        this.nextButtonName = nextButton;
    }

    /**
     * Get the picture that can trigger next step with this picture.
     *
     * @return the picture that can trigger next step with this picture.
     */
    Picture getActivePic() {
        return activePic;
    }

    /**
     * Set the picture that can trigger next step with this picture.
     *
     * @param activePic the picture that can trigger next step with this picture.
     */
    void setActivePic(Picture activePic) {
        this.activePic = activePic;
    }

}
