package com.example.threeseasons.winter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.threeseasons.R;
import com.example.threeseasons.data.DatabaseHelper;
import com.example.threeseasons.data.User;

import java.io.IOException;
import java.io.ObjectOutputStream;

public class WinterActivity extends AppCompatActivity implements WinterView {

    /**
     * Presenter for this activity.
     */
    WinterPresenter presenter;
    /**
     * The timer to count time since start of this activity.
     */
    CountUpTimer countUpTimer;
    /**
     * The first picture displayed on the screen.
     */
    private Picture picture1;
    /**
     * The second picture displayed on the screen.
     */
    private Picture picture2;
    /**
     * The third picture displayed on the screen.
     */
    private Picture picture3;
    /**
     * The fourth picture displayed on the screen.
     */
    private Picture picture4;
    /**
     * The initial X coordinates of a touch.
     */
    private double initX = 0;
    /**
     * The initial X coordinates of a touch.
     */
    private double initY = 0;
    /**
     * TextView for displaying number of moves.
     */
    private TextView movesDisplay;
    /**
     * TextView for displaying score.
     */
    private TextView scoreDisplay;
    /**
     * TextView for displaying time since start.
     */
    private TextView timeDisplay;
    /**
     * TextView for displaying number of jade.
     */
    private TextView jadeDisplay;
    /**
     * ImageButton for displaying the collection jade.
     */
    private ImageButton jade;
    /**
     * First ImageButton for step 1.
     */
    private ImageButton button1_1;
    /**
     * Second ImageButton for step 1.
     */
    private ImageButton button1_2;
    /**
     * First ImageButton nonactive in game.
     */
    private ImageButton button_b1;
    /**
     * Second ImageButton nonactive in game.
     */
    private ImageButton button_b2;
    /**
     * Third ImageButton nonactive in game.
     */
    private ImageButton button_b3;
    /**
     * Fourth ImageButton nonactive in game.
     */
    private ImageButton button_b4;
    /**
     * Fifth ImageButton nonactive in game.
     */
    private ImageButton button_b5;
    /**
     * First ImageButton for step 2.
     */
    private ImageButton button2_1;
    /**
     * Second ImageButton for step 2.
     */
    private ImageButton button2_2;
    /**
     * First ImageButton for step 3.
     */
    private ImageButton button3_1;
    /**
     * Second ImageButton for step 3.
     */
    private ImageButton button3_2;
    /**
     * First ImageButton for step 4.
     */
    private ImageButton button4_1;
    /**
     * Second ImageButton for step 4.
     */
    private ImageButton button4_2;
    /**
     * ImageButton for step 5.
     */
    private ImageButton button5_1;
    /**
     * Name of first ImageButton for step 1.
     */
    private static final String BUTTON1_1 = "1_1";
    /**
     * Name of second ImageButton for step 1.
     */
    private static final String BUTTON1_2 = "1_2";
    /**
     * Name of first ImageButton nonactive in game.
     */
    private static final String BUTTON_B1 = "B1";
    /**
     * Name of second ImageButton nonactive in game.
     */
    private static final String BUTTON_B2 = "B2";
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
     * Name of first ImageButton for step 2.
     */
    private static final String BUTTON2_1 = "2_1";
    /**
     * Name of second ImageButton for step 2.
     */
    private static final String BUTTON2_2 = "2_2";
    /**
     * Name of first ImageButton for step 3.
     */
    private static final String BUTTON3_1 = "3_1";
    /**
     * Name of second ImageButton for step 3.
     */
    private static final String BUTTON3_2 = "3_2";
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
     * Constant for X coordinate of a left position.
     */
    private static final int POS_LEFT = 415;
    /**
     * Constant for X coordinate of a right position.
     */
    private static final int POS_RIGHT = 895;
    /**
     * Constant for Y coordinate of a up position.
     */
    private static final int POS_UP = 0;
    /**
     * Constant for Y coordinate of a down position.
     */
    private static final int POS_DOWN = 480;
    /**
     * Constant for duration of animations.
     */
    private static final int DURATION = 800;
    /**
     * Constant for minimal length of scroll on the screen to trigger movements.
     */
    private static final int MIN_MAGNITUDE = 60;

    /*  ------------------------------------- Methods -------------------------------------  */

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_winter);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        findViews();
        setupPresenter();
        locatePictures();
        setOnTouchListeners();
        setTexts();
        setCountUpTimer();
    }

    /**
     * Set up the presenter for this activity.
     */
    private void setupPresenter() {
        User user = (User) getIntent().getSerializableExtra("user");
        DatabaseHelper database = new DatabaseHelper(this);
        WinterManager manager = new WinterManager(database, user);
        this.presenter = new WinterPresenter(this, manager);
    }

    /**
     * Find all ImageButtons and TextViews on the layout.
     */
    private void findViews() {
        movesDisplay = findViewById(R.id.numMove_sta);
        scoreDisplay = findViewById(R.id.score_sta);
        timeDisplay = findViewById(R.id.time_sta);
        jadeDisplay = findViewById(R.id.jade_sta);

        jade = findViewById(R.id.jade_pendant);

        button1_1 = findViewById(R.id.step1_1);
        button1_2 = findViewById(R.id.step1_2);
        button_b1 = findViewById(R.id.blank1);
        button_b2 = findViewById(R.id.blank2);
        button_b3 = findViewById(R.id.blank3);
        button_b4 = findViewById(R.id.blank4);
        button_b5 = findViewById(R.id.blank5);
        button2_1 = findViewById(R.id.step2_1);
        button2_2 = findViewById(R.id.step2_2);
        button3_1 = findViewById(R.id.step3_1);
        button3_2 = findViewById(R.id.step3_2);
        button4_1 = findViewById(R.id.step4_1);
        button4_2 = findViewById(R.id.step4_2);
        button5_1 = findViewById(R.id.step5_1);
    }

    /**
     * Set pictures one to four to their corresponding postions on the screen.
     */
    public void locatePictures() {
        picture1 = presenter.getPicture(UP_LEFT);
        picture2 = presenter.getPicture(UP_RIGHT);
        picture3 = presenter.getPicture(DOWN_LEFT);
        picture4 = presenter.getPicture(DOWN_RIGHT);
    }

    /**
     * Set up and start the timer.
     */
    private void setCountUpTimer() {
        countUpTimer =
                new CountUpTimer(300000000) {
                    public void onTick(int second) {
                        presenter.addTime();
                        String timeText = presenter.getTime() + "\"";
                        timeDisplay.setText(timeText);
                    }
                };
        countUpTimer.start();
    }

    /**
     * Set onTouchListeners for the four image buttons and the jade pendant
     * currently displaying on the screen.
     */
    @SuppressLint("ClickableViewAccessibility")
    private void setOnTouchListeners() {
        ImageButton b1 = findButtonByName(picture1.getCurrButtonName());
        ImageButton b2 = findButtonByName(picture2.getCurrButtonName());
        ImageButton b3 = findButtonByName(picture3.getCurrButtonName());
        ImageButton b4 = findButtonByName(picture4.getCurrButtonName());

        if (b1 != null) {
            b1.setOnTouchListener(
                    new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View view, MotionEvent motionEvent) {
                            return resolveTouch(picture1, motionEvent);
                        }
                    });
        }
        if (b2 != null) {
            b2.setOnTouchListener(
                    new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View view, MotionEvent motionEvent) {
                            return resolveTouch(picture2, motionEvent);
                        }
                    });
        }
        if (b3 != null) {
            b3.setOnTouchListener(
                    new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View view, MotionEvent motionEvent) {
                            return resolveTouch(picture3, motionEvent);
                        }
                    });
        }
        if (b4 != null) {
            b4.setOnTouchListener(
                    new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View view, MotionEvent motionEvent) {
                            return resolveTouch(picture4, motionEvent);
                        }
                    });
        }

        jade.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                        presenter.addJade();
                        String jadeText = String.valueOf(presenter.getJade());
                        jadeDisplay.setText(jadeText);
                        jade.animate()
                                .alpha(0)
                                .setDuration(1000)
                                .withEndAction(
                                        new Runnable() {
                                            @Override
                                            public void run() {
                                                jade.setVisibility(View.GONE);
                                            }
                                        });
                    }
                });
    }

    /**
     * Figure out the type of the touch and determine how pictures should behave.
     *
     * @param picture     the Picture object corresponding to the ImageButton being touched.
     * @param motionEvent the motion of current touch on the ImageButton.
     */
    public boolean resolveTouch(Picture picture, MotionEvent motionEvent) {

        int action = motionEvent.getActionMasked();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                initX = motionEvent.getX();
                initY = motionEvent.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                double finalX = motionEvent.getX();
                double finalY = motionEvent.getY();
                double deltaX = Math.abs(finalX - initX);
                double deltaY = Math.abs(finalY - initY);
                double moveMagnitude = Math.pow(Math.pow(deltaX, 2) + Math.pow(deltaY, 2), 0.5);

                if (moveMagnitude > MIN_MAGNITUDE) {
                    if (picture == picture4) {
                        System.out.println("picture 4");
                    }

                    if (deltaX > 2 * deltaY) {
                        if (finalX - initX > 0) {
                            presenter.move(picture, RIGHT);
                        } else {
                            presenter.move(picture, LEFT);
                        }
                    } else if (deltaY > 2 * deltaX) {
                        if (finalY - initY > 0) {
                            presenter.move(picture, DOWN);
                        } else {
                            presenter.move(picture, UP);
                        }
                    }
                    locatePictures();
                    setOnTouchListeners();
                    setTexts();
                }
        }
        return false;
    }

    /**
     * Set text for all the TextViews on the screen.
     */
    private void setTexts() {
        String movesText = String.valueOf(presenter.getNumMoves());
        String scoreText = String.valueOf(presenter.getScore());
        String jadeText = String.valueOf(presenter.getJade());
        movesDisplay.setText(movesText);
        scoreDisplay.setText(scoreText);
        jadeDisplay.setText(jadeText);
    }

    /**
     * Animate ImageButton in certain direction with a delay.
     *
     * @param buttonName the name of the ImageButton to be animated.
     * @param direction  String representing the direction of the animation.
     * @param delay      the length of delay for this animation.
     */
    public void animateMove(String buttonName, String direction, int delay) {
        int position = resolveDirection(direction);

        final ImageButton button = findButtonByName(buttonName);
        if (button != null) {
            if (position == POS_RIGHT || position == POS_LEFT) {
                button.animate().x(position).setDuration(DURATION).setStartDelay(delay);
            } else if (position == POS_UP || position == POS_DOWN) {
                button.animate().y(position).setDuration(DURATION).setStartDelay(delay);
            }
        }
    }

    /**
     * Animate ImageButton to disappear from the screen.
     *
     * @param buttonName the name of the ImageButton to be animated.
     * @param delay      the length of delay for this animation.
     */
    public void animationDisappear(String buttonName, int delay) {
        final ImageButton button = findButtonByName(buttonName);

        if (button != null) {
            button
                    .animate()
                    .alpha(0)
                    .setDuration(2 * DURATION)
                    .setStartDelay(delay)
                    .withEndAction(
                            new Runnable() {
                                @Override
                                public void run() {
                                    button.setX(0);
                                }
                            });
        }
    }

    /**
     * Animate ImageButton to appear on the screen.
     *
     * @param buttonName the name of the ImageButton to be animated.
     * @param delay      the length of delay for this animation.
     */
    public void animationAppear(String buttonName, int delay) {
        final ImageButton button = findButtonByName(buttonName);
        if (button != null) {
            button.animate().alpha(1).setDuration(2 * DURATION).setStartDelay(delay);
        }
    }

    /**
     * Make an ImageButton disappear and the other appear.
     *
     * @param buttonName1 the name of the ImageButton to disappear.
     * @param buttonName2 the name of the ImageButton to appear.
     */
    public void changeDisplay(String buttonName1, String buttonName2) {
        animationDisappear(buttonName1, DURATION);
        animationAppear(buttonName2, DURATION);
    }

    /**
     * Set an ImageButton to the same position as the other.
     *
     * @param buttonName1 the name of the ImageButton to be set position as the other.
     * @param buttonName2 the name of the ImageButton to get position from.
     */
    public void setToSamePosition(String buttonName1, String buttonName2) {
        final ImageButton button1 = findButtonByName(buttonName1);
        final ImageButton button2 = findButtonByName(buttonName2);
        if (button1 != null && button2 != null) {
            float posX = button2.getX();
            float posY = button2.getY();
            button1.setX(posX);
            button1.setY(posY);
        }
    }

    /**
     * Set an ImageButton to a certain position.
     *
     * @param buttonName the name of the ImageButton.
     * @param position   the position the ImageButton should be set to.
     */
    public void setPosition(String buttonName, int position) {
        final ImageButton button = findButtonByName(buttonName);
        float posX;
        float posY;
        switch (position) {
            case UP_LEFT:
                posX = POS_LEFT;
                posY = POS_UP;
                break;
            case UP_RIGHT:
                posX = POS_RIGHT;
                posY = POS_UP;
                break;
            case DOWN_LEFT:
                posX = POS_LEFT;
                posY = POS_DOWN;
                break;
            case DOWN_RIGHT:
                posX = POS_RIGHT;
                posY = POS_DOWN;
                break;
            default:
                posX = 0;
                posY = 0;
        }
        if (button != null) {
            button.setX(posX);
            button.setY(posY);
        }
    }

    /**
     * Make the collection jade pendant appear on the screen.
     */
    public void popJade() {
        jade.setVisibility(View.VISIBLE);
        jade.animate().alpha(1).setDuration(1000);
    }

    /**
     * End this activity and go to WinterOverActivity.
     */
    public void endGame() {
        countUpTimer.cancel();

        final Intent intent = new Intent(getApplicationContext(), WinterOverActivity.class);
        Bundle b = new Bundle();
        b.putInt("Score", presenter.getScore());
        b.putInt("Time", presenter.getTime());
        b.putInt("Moves", presenter.getNumMoves());
        intent.putExtras(b);
        intent.putExtra("user", presenter.getUser());
        intent.putExtra("summerScore",
                getIntent().getSerializableExtra("summerScore"));
        intent.putExtra("summerJade",
                getIntent().getSerializableExtra("summerJade"));
        intent.putExtra("autumnScore",
                getIntent().getSerializableExtra("autumnScore"));
        intent.putExtra("autumnJade",
                getIntent().getSerializableExtra("autumnJade"));
        intent.putExtra("winterScore", presenter.getScore());
        intent.putExtra("winterJade", presenter.getJade());


        Runnable r =
                new Runnable() {
                    @Override
                    public void run() {
                        startActivity(intent);
                        finish();
                    }
                };

        Handler h = new Handler();
        h.postDelayed(r, 2500);
    }

    /**
     * Resolve a direction to a specific coordinate on the screen.
     *
     * @param direction String representing the direction.
     */
    private int resolveDirection(String direction) {
        switch (direction) {
            case LEFT:
                return POS_LEFT;
            case RIGHT:
                return POS_RIGHT;
            case UP:
                return POS_UP;
            case DOWN:
                return POS_DOWN;
            default:
                return 0;
        }
    }

    /**
     * Return the ImageButton corresponding to the button name.
     *
     * @return the ImageButton corresponding to the button name.
     */
    private ImageButton findButtonByName(String buttonName) {
        switch (buttonName) {
            case BUTTON1_1:
                return button1_1;
            case BUTTON1_2:
                return button1_2;
            case BUTTON_B1:
                return button_b1;
            case BUTTON_B2:
                return button_b2;
            case BUTTON_B3:
                return button_b3;
            case BUTTON_B4:
                return button_b4;
            case BUTTON_B5:
                return button_b5;
            case BUTTON2_1:
                return button2_1;
            case BUTTON2_2:
                return button2_2;
            case BUTTON3_1:
                return button3_1;
            case BUTTON3_2:
                return button3_2;
            case BUTTON4_1:
                return button4_1;
            case BUTTON4_2:
                return button4_2;
            case BUTTON5_1:
                return button5_1;
        }
        return null;
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        setContentView(R.layout.activity_winter);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        findViews();

        setupPresenter();

        locatePictures();

        setOnTouchListeners();

        setTexts();

        setCountUpTimer();
    }

    /**
     * Save current user to the corresponding file in the database.
     *
     * @param fileName the name of the file.
     */
    public void saveUserToFile(String fileName) {
        try {
            ObjectOutputStream outputStream =
                    new ObjectOutputStream(openFileOutput(fileName, Context.MODE_PRIVATE));
            outputStream.writeObject(presenter.getUser());

            outputStream.close();
        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }
}
