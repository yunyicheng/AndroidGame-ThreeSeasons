package com.example.threeseasons.maingame;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.threeseasons.R;
import com.example.threeseasons.data.DatabaseHelper;
import com.example.threeseasons.data.User;

import java.io.IOException;
import java.io.ObjectOutputStream;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {
    /**
     * Database that stores user information.
     */
    private DatabaseHelper database;
    /**
     * User object that stores the user information.
     */
    private User user;
    /**
     * Username that the player entered.
     */
    private String usernameInput;
    /**
     * Password that the player entered.
     */
    private String passwordInput1;
    /**
     * Password that the player re-entered.
     */
    private String passwordInput2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        database = new DatabaseHelper(this);
    }

    @Override
    public void onClick(View v) {

        getInput();

        switch (v.getId()) {
            case R.id.createaccount:
                CreateAccount();
                break;
            case R.id.Sign_In:
                Intent CreateNewIntent = new Intent(getApplicationContext(), SignInActivity.class);
                startActivity(CreateNewIntent);
        }
    }

    /**
     * Get username and password inputs from player.
     */
    private void getInput() {
        EditText username = findViewById(R.id.newusername);
        EditText password = findViewById(R.id.newpassword);
        EditText password_reentered = findViewById(R.id.reenterpassword);
        usernameInput = username.getText().toString();
        passwordInput1 = password.getText().toString();
        passwordInput2 = password_reentered.getText().toString();
    }

    /**
     * If the username and password the user enters are valid, add the user information to the
     * database and start the game.
     */
    private void CreateAccount() {
        boolean usernameValidity = checkUsername();
        boolean passwordValidity = checkPassword();
        if (usernameValidity && passwordValidity) {
            user = new User(usernameInput, passwordInput1);
            database.addUser(user);
            Toast.makeText(getApplication(), getResources().getString(R.string.successful_signup),
                    Toast.LENGTH_SHORT).show();
            saveUserToFile();
            Intent StartGameIntent = new Intent(getApplicationContext(), NewGameActivity.class);
            StartGameIntent.putExtra("username", usernameInput);
            StartGameIntent.putExtra("user", user);
            startActivity(StartGameIntent);
        }
    }

    /**
     * Check whether the username is valid.
     *
     * @return true if username is valid.
     */
    private boolean checkUsername() {
        if (usernameInput.equals("")) {
            Toast.makeText(getApplication(), getResources().getString(R.string.empty_username),
                    Toast.LENGTH_SHORT).show();
            return false;
        } else if (usernameInput.contains(" ")) {
            Toast.makeText(getApplication(), getResources().getString(R.string.contain_space),
                    Toast.LENGTH_SHORT).show();
            return false;
        } else if (database.userExists(usernameInput)) {
            Toast.makeText(getApplication(), getResources().getString(R.string.username_exist), Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    /**
     * Check whether the password matches the re-entered password.
     *
     * @return true if the password matches the re-entered password.
     */
    private boolean checkPassword() {
        if (!passwordInput1.equals(passwordInput2)) {
            Toast.makeText(getApplication(), getResources().getString(R.string.match_pw),
                    Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }


    /**
     * Save user to the corresponding file in the database.
     */
    public void saveUserToFile() {
        String fileName = database.getUserFile(usernameInput);
        try {
            ObjectOutputStream outputStream =
                    new ObjectOutputStream(this.openFileOutput(fileName, MODE_PRIVATE));

            outputStream.writeObject(user);
            outputStream.close();
        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }
}

