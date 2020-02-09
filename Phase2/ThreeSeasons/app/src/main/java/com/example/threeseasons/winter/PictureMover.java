package com.example.threeseasons.winter;

class PictureMover {

    /**
     * Interactor for this game.
     */
    private WinterManager winterManager;
    /**
     * Manager for deciding behaviors for each step.
     */
    private StepManager stepManager;
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
     * Constant representing direction of leftward movement.
     */
    private static final String LEFT = "left";
    /**
     * Constant representing direction of rightward movement.
     */
    private static final String RIGHT = "right";
    /**
     * Constant representing direction of upward movement.
     */
    private static final String UP = "up";
    /**
     * Constant representing direction of downward movement.
     */
    private static final String DOWN = "down";


    /**
     * Constructor for class PictureMover which decides picture's actions
     *
     * @param winterManager interactor for this game.
     * @param stepManager      manager for deciding behaviors for each step.
     */
    PictureMover(WinterManager winterManager, StepManager stepManager) {
        this.winterManager = winterManager;
        this.stepManager = stepManager;
    }

    /**
     * Move picture in a direction, and decide whether this movement triggers the next step.
     *
     * @param picture   the picture to be moved.
     * @param direction the direction of this movement.
     */
    void move(Picture picture, String direction) {

        final Picture pictureInteract = resolveMove(picture, direction);

        if (pictureInteract != null) {

            if (pictureInteract == picture.getActivePic()) {
                nextStep(picture, pictureInteract, direction);
            } else {
                switchPosition(picture, pictureInteract, direction);
            }
            winterManager.addNumMoves();
        }
    }

    /**
     * Animate two pictures into the next step.
     *
     * @param picture         the picture of movement
     * @param pictureInteract the picture interacting with the one being moved
     * @param direction       the direction of this movement.
     */
    private void nextStep(Picture picture, Picture pictureInteract, String direction) {

        pictureInteract.nextStep();
        stepManager.chooseStep(picture, pictureInteract);

        String button = picture.getPrevButtonName();
        String buttonInteract = pictureInteract.getPrevButtonName();
        String newButton = picture.getCurrButtonName();
        String newButtonInteract = pictureInteract.getCurrButtonName();

        winterManager.animateMove(button, direction, 0);
        winterManager.animationDisappear(button, 0);
        winterManager.animationDisappear(buttonInteract, 0);
        winterManager.setPosition(newButton, picture.getPosition());
        winterManager.setPosition(newButtonInteract, pictureInteract.getPosition());
        winterManager.animationAppear(newButton, 500);
        winterManager.animationAppear(newButtonInteract, 500);
        winterManager.updateScore();
    }

    /**
     * Switch two picture's position when they cannot trigger the next step.
     *
     * @param picture         the picture of movement.
     * @param pictureInteract the picture interacting with the one being moved.
     * @param direction       the direction of this movement.
     */
    private void switchPosition(Picture picture, Picture pictureInteract, String direction) {
        String button = picture.getCurrButtonName();
        String buttonInteract = pictureInteract.getCurrButtonName();

        winterManager.animateMove(button, direction, 0);
        winterManager.animateMove(buttonInteract, opposite(direction), 0);

        int posPicture = picture.getPosition();
        int posPictureInteract = pictureInteract.getPosition();

        winterManager.setPicture(picture, posPictureInteract);
        winterManager.setPicture(pictureInteract, posPicture);
    }

    /**
     * Resolve the opposite direction of a direction.
     *
     * @param direction the input direction.
     * @return the opposite direction of the input direction.
     */
    private String opposite(String direction) {
        switch (direction) {
            case LEFT:
                return RIGHT;
            case RIGHT:
                return LEFT;
            case UP:
                return DOWN;
            case DOWN:
                return UP;
        }
        return null;
    }

    /**
     * Resolve the picture of interaction in a movement.
     *
     * @param picture   the picture being moved
     * @param direction the direction of movement.
     * @return the picture of interaction in the movement.
     */
    private Picture resolveMove(Picture picture, String direction) {

        if (picture.getPosition() == UP_LEFT) {
            if (direction.equals(RIGHT)) {
                return winterManager.getUpRightPicture();
            } else if (direction.equals(DOWN)) {
                return winterManager.getDownLeftPicture();
            }

        } else if (picture.getPosition() == UP_RIGHT) {
            if (direction.equals(LEFT)) {
                return winterManager.getUpLeftPicture();
            } else if (direction.equals(DOWN)) {
                return winterManager.getDownRightPicture();
            }

        } else if (picture.getPosition() == DOWN_LEFT) {
            if (direction.equals(RIGHT)) {

                return winterManager.getDownRightPicture();
            } else if (direction.equals(UP)) {
                return winterManager.getUpLeftPicture();
            }

        } else if (picture.getPosition() == DOWN_RIGHT) {
            if (direction.equals(LEFT)) {
                return winterManager.getDownLeftPicture();
            } else if (direction.equals(UP)) {
                return winterManager.getUpRightPicture();
            }
        }
        return null;
    }
}
