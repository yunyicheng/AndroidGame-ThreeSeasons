package com.example.threeseasons.autumn;

import android.graphics.Color;

import androidx.appcompat.app.AppCompatActivity;

public class Command extends AppCompatActivity {

    /**
     * Integer representing a color in android.graphics.Color
     */
    private int color;
    /**
     * Integer that represents the color that the word's meaning indicates
     */
    private int colorWord;
    /**
     * Word showing a color
     */
    private String word;
    /**
     * Integer that represents the shape
     */
    private int shapeInt;


    /**
     * Constructor of the Command class
     */
    Command() {
        this.color = chooseColor();
        this.word = setWord();
        this.shapeInt = setShape();
    }

    /**
     * Randomly choose the color of the command
     *
     * @return integer representing the color of the command
     */
    int chooseColor() {
        double d = Math.random();
        if (d < 0.3) {
            return Color.YELLOW;

        } else if (d >= 0.3 && d < 0.66) {
            return Color.GREEN;
        } else {
            return Color.RED;
        }

    }

    /**
     * Randomly choose the word of the command
     *
     * @return the word of the command
     */
    String setWord() {
        double d = Math.random();
        if (d < 0.3) {
            this.colorWord = Color.RED;
            return new String("Red");

        } else if (d >= 0.3 && d < 0.66) {
            this.colorWord = Color.YELLOW;
            return new String("Yellow");

        } else {
            this.colorWord = Color.GREEN;
            return new String("Green");

        }

    }

    /**
     * Randomly choose the shape of the command
     *
     * @return the integer representing the shape of the command, 0 is for round and 1 is for
     * rectangle.
     */
    int setShape() {
        double d = Math.random();
        if (d < 0.5) {
            return 0;
        } else {
            return 1;
        }

    }

    /**
     * Return the integer representing correct answer for this command, if the shape is round, the
     * correct answer is the background color; if the shape is rectangle, the correct answer is the
     * word.
     *
     * @return the integer representing correct answer for this command
     */
    int commandColor() {
        if (shapeInt == 0) {
            return color;
        } else {
            return colorWord;
        }
    }

    /**
     * Returns the word showing a color
     *
     * @return the word showing a color
     */
    String getWord() {
        return this.word;
    }

    /**
     * Returns the integer representing a color in android.graphics.Color
     *
     * @return the integer representing a color in android.graphics.Color
     */
    int getColor() {
        return this.color;
    }

    /**
     * Returns the integer that represents the shape
     *
     * @return the integer that represents the shape
     */
    int getShape() {
        return shapeInt;
    }
}

