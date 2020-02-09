package com.example.threeseasons.winter;

class StepManager {

    /**
     * Interactor for this game.
     */
    private WinterManager winterManager;
    /**
     * The picture to be animated in the step one to two transition.
     */
    private Picture oneToTwo;
    /**
     * The picture to be animated in the step two to three transition.
     */
    private Picture twoToThree;
    /**
     * The picture to be animated in the step four to five transition.
     */
    private Picture fourToFive;
    /**
     * Number of steps taken.
     */
    private int currentStep = 1;
    /**
     * Name of third ImageButton nonactive in game.
     */
    private static final String BUTTON_B3 = "B3";
    /**
     * Name of fourth ImageButton nonactive in game.
     */
    private static final String BUTTON_B4 = "B4";
    /**
     * Name of fifth ImageButton nonactive in game.
     */
    private static final String BUTTON_B5 = "B5";
    /**
     * Name of first ImageButton for step 3.
     */
    private static final String BUTTON3_1 = "3_1";
    /**
     * Name of first ImageButton for step 4.
     */
    private static final String BUTTON4_1 = "4_1";
    /**
     * Name of second ImageButton for step 4.
     */
    private static final String BUTTON4_2 = "4_2";
    /**
     * Name of ImageButton for step 5.
     */
    private static final String BUTTON5_1 = "5_1";


    /**
     * Constructor of the StepManager class.
     *
     * @param winterManager interactor for this game.
     * @param oneToTwo         the picture to be animated in the step one to two transition.
     * @param twoToThree       the picture to be animated in the step two to three transition.
     */
    StepManager(WinterManager winterManager, Picture oneToTwo, Picture twoToThree) {
        this.winterManager = winterManager;
        this.oneToTwo = oneToTwo;
        this.twoToThree = twoToThree;
    }

    /**
     * Figure out the which step to take.
     *
     * @param picture1 the first picture in the interaction
     * @param picture2 the second picture in the interaction
     */
    void chooseStep(Picture picture1, Picture picture2) {

        if (currentStep == 1) {
            stepOneToTwo(picture1, picture2);
        } else if (currentStep == 2) {
            stepTwoToThree(picture1, picture2);
        } else if (currentStep == 3) {
            stepThreeToFour(picture1, picture2);
        } else if (currentStep == 4) {
            stepFourToFive(picture1);
        }
        currentStep += 1;
        winterManager.setStep(currentStep);
    }

    /**
     * Transition between step one and two
     *
     * @param picture1 the first picture in the interaction
     * @param picture2 the second picture in the interaction
     */
    private void stepOneToTwo(Picture picture1, Picture picture2) {
        picture1.setActivePic(null);
        picture2.setActivePic(null);

        picture1.setCurrButtonName(BUTTON_B3);
        oneToTwo.nextStep();
        String b = oneToTwo.getPrevButtonName();
        String new_b = oneToTwo.getCurrButtonName();
        winterManager.setToSamePosition(new_b, b);
        winterManager.changeDisplay(b, new_b);
        picture2.setActivePic(oneToTwo);
        oneToTwo.setActivePic(picture2);
        picture2.setNextButtonName(BUTTON4_2);
        oneToTwo.setNextButtonName(BUTTON4_2);
    }

    /**
     * Transition between step two and three
     *
     * @param picture1 the first picture in the interaction
     * @param picture2 the second picture in the interaction
     */
    private void stepTwoToThree(Picture picture1, Picture picture2) {
        picture1.setActivePic(null);
        picture2.setActivePic(null);

        picture1.setCurrButtonName(BUTTON3_1);
        twoToThree.nextStep();
        fourToFive = picture2;
        picture2.setNextButtonName(BUTTON5_1);
        String b = twoToThree.getPrevButtonName();
        String new_b = twoToThree.getCurrButtonName();
        winterManager.setToSamePosition(new_b, b);
        winterManager.changeDisplay(b, new_b);
        picture1.setActivePic(twoToThree);
        twoToThree.setActivePic(picture1);
        picture1.setNextButtonName(BUTTON4_1);
        twoToThree.setNextButtonName(BUTTON4_1);
    }

    /**
     * Transition between step three and four
     *
     * @param picture1 the first picture in the interaction
     * @param picture2 the second picture in the interaction
     */
    private void stepThreeToFour(Picture picture1, Picture picture2) {
        picture1.setActivePic(null);
        picture2.setActivePic(null);

        picture1.setCurrButtonName(BUTTON_B4);
        picture2.setActivePic(fourToFive);
        fourToFive.setActivePic(picture2);
        picture2.setNextButtonName(BUTTON5_1);
        fourToFive.setNextButtonName(BUTTON5_1);
    }

    /**
     * Transition between step four and five
     *
     * @param picture the picture in the interaction
     */
    private void stepFourToFive(Picture picture) {
        picture.setCurrButtonName(BUTTON_B5);
    }
}
