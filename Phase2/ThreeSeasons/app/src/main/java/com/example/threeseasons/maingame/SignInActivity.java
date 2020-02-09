package com.example.threeseasons.maingame;

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

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;

public class SignInActivity extends AppCompatActivity implements View.OnClickListener {
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
    private String passwordInput;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_signin);
        database = new DatabaseHelper(this);
    }

    @Override
    public void onClick(View v) {

        getInput();

        switch (v.getId()) {
            case R.id.signin:
                signIn();
                break;
            case R.id.createnew:
                Intent createNewIntent = new Intent(getApplicationContext(), SignUpActivity.class);
                startActivity(createNewIntent);
        }
    }

    /**
     * Get username and password inputs from player.
     */
    private void getInput() {
        EditText username = findViewById(R.id.username);
        EditText password = findViewById(R.id.password);
        usernameInput = username.getText().toString();
        passwordInput = password.getText().toString();
    }

    /**
     * Sign in if the username entered exists in the database and the password is correct;
     * otherwise, make toast about the error.
     */
    private void signIn() {
        if (database.userExists(usernameInput)) {
            if (loadUserFromFile()) {
                checkPassword();
            } else {
                Toast.makeText(SignInActivity.this,
                        getResources().getString(R.string.error),
                        Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(SignInActivity.this,
                    getResources().getString(R.string.username_error),
                    Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Check whether the password entered is correct. If so, start game;
     * otherwise, make toast about the error.
     */
    private void checkPassword() {
        if (user.checkPassword(passwordInput)) {
            Intent newGameIntent = new Intent(getApplication(), NewGameActivity.class);
            newGameIntent.putExtra("username", usernameInput);
            newGameIntent.putExtra("user", user);
            startActivity(newGameIntent);

        } else {
            Toast.makeText(SignInActivity.this,
                    getResources().getString(R.string.pw_error),
                    Toast.LENGTH_SHORT).show();
        }
    }


    /**
     * Load the user from the corresponding file in the database.
     *
     * @return true if successfully loaded user from file
     */
    private boolean loadUserFromFile() {

        String filename = database.getUserFile(usernameInput);
        try {
            InputStream inputStream = this.openFileInput(filename);
            if (inputStream != null) {
                ObjectInputStream input = new ObjectInputStream(inputStream);
                this.user = (User) input.readObject();
                inputStream.close();
                return true;
            }
        } catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        } catch (ClassNotFoundException e) {
            Log.e("login activity", "File contained unexpected data type: " + e.toString());
        }
        return false;
    }

}
