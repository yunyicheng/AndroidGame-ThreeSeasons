package com.example.threeseasons.autumn;

interface AutumnView {

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
