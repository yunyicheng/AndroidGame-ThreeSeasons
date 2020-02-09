package com.example.threeseasons.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class User implements Serializable {

    /**
     * Username of this user.
     */
    private String username;
    /**
     * Password of this user.
     */
    private String password;
    /**
     * A HashMap that records the highest three scores per game part.
     */
    private HashMap<String, ArrayList<Integer>> scoreHistory;
    /**
     * The name of the whole game.
     */
    private static final String TOTAL_GAME = "Three Seasons";
    /**
     * The name of the first game part Summer.
     */
    private static final String FIRST_GAME = "Summer";
    /**
     * The name of the second game part Autumn.
     */
    private static final String SECOND_GAME = "Autumn";
    /**
     * The name of the third game part Winter.
     */
    private static final String THIRD_GAME = "Winter";
    /**
     * Number of jades of this user.
     */
    private int jade = 0;


    /**
     * Construct a User object when signed up
     * Initialized with a username, a password and a HashMap which records the highest three
     * scores per game part
     *
     * @param username the username of the user.
     * @param password the password of the user.
     */
    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.scoreHistory = new HashMap<>();

        scoreHistory.put(TOTAL_GAME, new ArrayList<>(Arrays.asList(0, 0, 0)));
        scoreHistory.put(FIRST_GAME, new ArrayList<>(Arrays.asList(0, 0, 0)));
        scoreHistory.put(SECOND_GAME, new ArrayList<>(Arrays.asList(0, 0, 0)));
        scoreHistory.put(THIRD_GAME, new ArrayList<>(Arrays.asList(0, 0, 0)));
    }

    /**
     * Returns username.
     *
     * @return username of the user
     */
    public String getUsername() {
        return username;
    }

    /**
     * check if the entered password is correct and return either true or false
     *
     * @param password the password that user entered.
     * @return if the password is correct
     */
    public boolean checkPassword(String password) {
        return this.password.equals(password);
    }

    /**
     * Update score taking gameType and score as input.
     * update score only when the input score is higher than current highest score.
     *
     * @param gameType the type of the name.
     * @param score    the new score of the user.
     */
    public boolean updateScore(String gameType, Integer score) {
        ArrayList<Integer> history = this.scoreHistory.get(gameType);
        if (history != null) {
            if (history.get(2) < score) {
                history.set(2, score);
                Collections.sort(history);
                Collections.reverse(history);
                System.out.println(history);
                scoreHistory.put(gameType, history);
                return true;
            }
        }
        return false;
    }

    /**
     * Returns whether the input is higher than all scores stored in user for gameType.
     *
     * @param gameType the name of the game type.
     * @param newScore newest score achieved by this user
     * @return whether the input is higher than all scores stored in user for gameType.
     */
    public boolean highestScore(String gameType, int newScore) {
        ArrayList<Integer> history = this.scoreHistory.get(gameType);
        if (history != null) {
            return (history.get(0) < newScore);
        }
        return false;
    }

    /**
     * Returns the file address for a game part.
     *
     * @param gamePart the name of the game part.
     * @return the file name where the user's game state of the game part is stored
     */
    String getFile(String gamePart) {
        if (this.scoreHistory.containsKey(gamePart)) {
            return this.username + "_" + gamePart + "_data.ser";
        } else {
            return "DNE";
        }
    }

    /**
     * Return the list of this user's best three scores in a game part.
     *
     * @param game the name of the game part.
     * @return score the user got in the game part.
     */
    public Integer getScore(String game) {
        if (this.scoreHistory.containsKey(game)) {
            return this.scoreHistory.get(game).get(0);
        } else {
            return -1;
        }
    }

    /**
     * Return information needed for scoreboard.
     *
     * @return 2D ArrayList containing necessary information
     */
    public List<List<String>> getScoreboardData(String game_type) {
        List<List<String>> datalist = new ArrayList<>();
        int rank = 1;
        for (Integer score : scoreHistory.get(game_type)) {
            List<String> data = new ArrayList<>();
            data.add(String.valueOf(rank));
            data.add(game_type);
            data.add(score.toString());
            datalist.add(data);
            rank++;
        }
        return datalist;
    }

    /**
     * Update the number of jades of this user.
     */
    public void updateJade(int newJade) {
        this.jade += newJade;
    }

    /**
     * Return the number of jades of this user.
     *
     * @return the number of jades of this user.
     */
    public int getJade() {
        return this.jade;
    }
}
