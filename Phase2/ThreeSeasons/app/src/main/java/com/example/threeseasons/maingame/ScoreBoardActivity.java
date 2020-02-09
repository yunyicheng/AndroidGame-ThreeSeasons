package com.example.threeseasons.maingame;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.threeseasons.R;
import com.example.threeseasons.data.DatabaseHelper;
import com.example.threeseasons.data.User;

import java.util.List;

public class ScoreBoardActivity extends AppCompatActivity implements View.OnClickListener {

    /**
     * Database that stores user information.
     */
    private DatabaseHelper database;
    /**
     * User object that store the user information.
     */
    private User user;
    /**
     * The 2D list where the inner list contains statistics to display on the scoreboard.
     */
    private List<List<String>> dataList;
    /**
     * The TableLayout for displaying the scoreboard.
     */
    private TableLayout scoreboard;
    /**
     * String representing the type of scoreboard to display, either by user or by game.
     */
    private String scoreboardType;
    /**
     * Constant list of strings containing the title of the by-user scoreboard.
     */
    private static final String[] BY_USER_TITLE = new String[]{"Rank", "Game", "Score"};
    /**
     * Constant list of strings containing the title of the by-game scoreboard.
     */
    private static final String[] BY_GAME_TITLE = new String[]{"Rank", "User", "Score"};
    /**
     * Constant string representing the by-user type of scoreboard to display
     */
    private static final String BY_USER = "byUser";
    /**
     * Constant string representing the by-game type of scoreboard to display
     */
    private static final String BY_GAME = "byGame";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_records);
        setupInfo();
        generateDataList();
        addTable();
    }

    @Override
    public void onClick(View v) {
        Intent backToSG = new Intent(getApplicationContext(), NewGameActivity.class);
        backToSG.putExtra("user", user);
        startActivity(backToSG);
    }

    /**
     * Set up information needed for generating a data list
     */
    private void setupInfo() {
        database = new DatabaseHelper(this);
        scoreboard = findViewById(R.id.localRecordTable);
        scoreboardType = getIntent().getStringExtra("scoreBoardType");
        user = (User) getIntent().getSerializableExtra("user");
    }

    /**
     * Generate a 2D list where the inner list contains statistics to display on the scoreboard.
     */
    private void generateDataList() {
        String gameType = getIntent().getStringExtra("gameType");
        if (scoreboardType.equals(BY_USER))
            dataList = user.getScoreboardData(gameType);
        else
            dataList = database.getScoreByGame(gameType);
        System.out.println(dataList);
    }

    /**
     * Display the scoreboard table.
     */
    private void addTable() {
        setupTitle();
        TableRow row;
        TextView text;
        for (int rowNum = 0; rowNum < dataList.size(); rowNum++) {
            row = new TableRow(this);
            for (int colNum = 0; colNum < dataList.get(rowNum).size(); colNum++) {
                text = new TextView(this);
                text.setText(dataList.get(rowNum).get(colNum));
                text.setTextColor(Color.parseColor("#FFFFFF"));
                text.setGravity(Gravity.CENTER);
                row.addView(text);
            }
            scoreboard.addView(row);
        }
    }

    /**
     * Display the title of the scoreboard table.
     */
    private void setupTitle() {
        initializeColumnsWidth();
        String[] titles = setupTitleContent();
        addConfigTitles(titles);
        addLine();
    }

    /**
     * Decide which title to be used according to the type of the scoreboard.
     *
     * @return the titles of scoreboard columns.
     */
    private String[] setupTitleContent() {
        String[] titles;
        if (scoreboardType.equals(BY_USER))
            titles = BY_USER_TITLE;
        else
            titles = BY_GAME_TITLE;
        return titles;
    }

    /**
     * Add one row to the scoreboard.
     */
    private void addLine() {
        TableRow row = new TableRow(this);
        row.setMinimumHeight(5);
        TableLayout.LayoutParams params = new TableLayout.LayoutParams(
                TableLayout.LayoutParams.WRAP_CONTENT,
                TableLayout.LayoutParams.WRAP_CONTENT
        );
        params.setMargins(0, 10, 0, 10);
        row.setLayoutParams(params);
        scoreboard.addView(row);
    }

    /**
     * Add the row containing the title to the scoreboard.
     *
     * @param titles the titles of scoreboard columns.
     */
    private void addConfigTitles(String[] titles) {
        TextView text;
        TableRow row = new TableRow(this);
        for (String title : titles) {
            text = new TextView(this);
            text.setText(title);
            text.setTextColor(Color.parseColor("#FFFFFF"));
            text.setGravity(Gravity.CENTER);
            text.setTextSize(18);
            row.addView(text);
        }
        scoreboard.addView(row);
    }

    /**
     * Decide the number and size of columns of the scoreboard
     */
    private void initializeColumnsWidth() {
        TableRow newRow = new TableRow(this);
        int numCol = scoreboardType.equals(BY_GAME) ? 4 : 3;
        int colWidth = scoreboardType.equals(BY_USER) ? 300 : 250;
        for (int i = 0; i < numCol; i++) {
            TextView tmp = new TextView(this);
            tmp.setWidth(colWidth);
            newRow.addView(tmp);
        }
        scoreboard.addView(newRow);
    }

}
