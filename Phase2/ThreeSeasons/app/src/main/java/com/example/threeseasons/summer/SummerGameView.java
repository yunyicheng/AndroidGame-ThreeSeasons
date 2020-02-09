package com.example.threeseasons.summer;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.view.Display;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.example.threeseasons.R;
import com.example.threeseasons.data.User;

import java.util.Random;

/**
 * The first game's view.
 */
@SuppressLint("ViewConstructor")
class SummerGameView extends SurfaceView implements SurfaceHolder.Callback {

    /**
     * The part of the program that manages time.
     */
    private SummerThread thread;

    /**
     * The background image for drawing
     */
    private Bitmap background;

    /**
     * The rectangle for scaling background image
     */
    private Rect rect;

    /**
     * The device's width and device's height
     */
    private int deviceWidth, deviceHeight;

    /**
     * The frame used to change movement of the character
     */
    private int playerFrame;

    /**
     * The list of obstacle images for drawing
     */
    private Bitmap[] obstacleFigure = new Bitmap[5];

    /**
     * The jade image for drawing
     */
    private Bitmap jadeBmp;

    /**
     * The list of player images for drawing
     */
    private Bitmap[] playerFigure = new Bitmap[5];

    /**
     * The environment of this game
     */
    private Context context;

    /**
     * The presenter for this game
     */
    private SummerPresenter presenter;

    /**
     * ID number of the first image for player in man mode
     */
    private static final int MAN_ID0 = R.drawable.man0;

    /**
     * ID number of the second image for player in man mode
     */
    private static final int MAN_ID1 = R.drawable.man1;

    /**
     * ID number of the third image for player in man mode
     */
    private static final int MAN_ID2 = R.drawable.man2;

    /**
     * ID number of the fourth image for player in man mode
     */
    private static final int MAN_ID3 = R.drawable.man3;

    /**
     * ID number of the fifth image for player in man mode
     */
    private static final int MAN_ID4 = R.drawable.man4;

    /**
     * ID number of the first image for player in shadow mode
     */
    private static final int SHADOW_ID0 = R.drawable.shadow0;

    /**
     * ID number of the second image for player in shadow mode
     */
    private static final int SHADOW_ID1 = R.drawable.shadow1;

    /**
     * ID number of the third image for player in shadow mode
     */
    private static final int SHADOW_ID2 = R.drawable.shadow2;

    /**
     * ID number of the fourth image for player in shadow mode
     */
    private static final int SHADOW_ID3 = R.drawable.shadow3;

    /**
     * ID number of the fifth image for player in shadow mode
     */
    private static final int SHADOW_ID4 = R.drawable.shadow4;

    /**
     * ID number of the first image for obstacle
     */
    private static final int OBSTACLE_ID0 = R.drawable.fire0;

    /**
     * ID number of the second image for obstacle
     */
    private static final int OBSTACLE_ID1 = R.drawable.fire1;

    /**
     * ID number of the third image for obstacle
     */
    private static final int OBSTACLE_ID2 = R.drawable.fire2;

    /**
     * ID number of the fourth image for obstacle
     */
    private static final int OBSTACLE_ID3 = R.drawable.fire3;

    /**
     * ID number of the fifth image for obstacle
     */
    private static final int OBSTACLE_ID4 = R.drawable.fire4;

    /**
     * ID number of the background image
     */
    private static final int BACKGROUND_ID = R.drawable.summer_background;

    /**
     * ID number of the jade image
     */
    private static final int JADE_ID = R.drawable.jade_cut;

    /**
     * Enum which contains all types of the characters that the user may use.
     */
    enum Character {
        MAN, SHADOW
    }

    /**
     * Create a new first game in the context environment given a mode.
     *
     * @param context   the environment.
     * @param character the mode
     */
    public SummerGameView(Context context, SummerPresenter presenter, String character, User user) {
        super(context);
        getHolder().addCallback(this);
        thread = new SummerThread(getHolder(), this);
        setFocusable(true);

        this.context = context;
        this.presenter = presenter;

        createGameElements(character);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        thread.setRunning(true);
        thread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        while (retry) {
            try {
                thread.setRunning(false);
                thread.join();

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            retry = false;
        }
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);

        // Draw the background
        canvas.drawBitmap(background, null, rect, null);

        // Draw a as-if running player
        drawRunningPlayer(canvas);

        // Draw every obstacle that is in the monsters at this moment
        for (int i = 0; i < presenter.getObstacles().size(); i++) {
            Random ran = new Random();
            canvas.drawBitmap(
                    obstacleFigure[ran.nextInt(5)],
                    presenter.getObstacles().get(i).getX(),
                    presenter.getObstacles().get(i).getY(),
                    null);
        }

        // Draw every obstacle that is in the jades at this moment
        for (int i = 0; i < presenter.getJades().size(); i++) {
            canvas.drawBitmap(
                    jadeBmp,
                    presenter.getJades().get(i).getX(),
                    presenter.getJades().get(i).getY(),
                    null);
        }

        drawStatistics(canvas);
    }

    /**
     * A helper method for drawing the statistics on the screen.
     *
     * @param canvas the canvas on which to draw.
     */
    private void drawStatistics(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setTextSize(70);

        // Draw Score
        String score = String.valueOf(presenter.getData().getScore());
        String str_score = context.getString(R.string.str_score);
        String textS = str_score + score;
        canvas.drawText(textS, deviceWidth / 20, deviceHeight / 9, paint);

        // Draw Distance
        String distance = String.valueOf(presenter.getData().getDistance());
        String str_distance = context.getString(R.string.str_distance);
        String textDis = str_distance + distance + " m";
        canvas.drawText(textDis, deviceWidth / 3, deviceHeight / 9, paint);
    }

    /**
     * Update the first game.
     */
    void update() {

        presenter.update();

        // Check if the game is over and if so, exit the game
        if (presenter.getIsGameOver()) {
            this.thread.setRunning(false);
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();

        if (action == MotionEvent.ACTION_DOWN) { // Tap detected on the screen
            presenter.actionDown();
        } else if (action == MotionEvent.ACTION_UP) {
            presenter.actionUp();
        }

        invalidate();
        return true;
    }

    /**
     * A helper method for drawing a running character.
     *
     * @param canvas the canvas on which to draw this character.
     */
    private void drawRunningPlayer(Canvas canvas) {
        int totalFrame = playerFigure.length - 1;
        playerFrame++;
        if (playerFrame > totalFrame) {
            playerFrame = 0;
        }

        canvas.drawBitmap(playerFigure[playerFrame], presenter.getPlayer().getX(),
                presenter.getPlayer().getY(), null);

    }

    /**
     * A helper method for scaling the background image.
     *
     * Create a rectangle with the device's width and height for scaling background's image
     * Code reference: https://www.youtube.com/watch?v=3BLESx44GL4
     */
    private void getScalingRect() {
        Display display = ((Activity) getContext()).getWindowManager().getDefaultDisplay();
        Point point = new Point();
        display.getSize(point);
        deviceWidth = point.x;
        deviceHeight = point.y;
        rect = new Rect(0, 0, deviceWidth, deviceHeight);
    }

    /**
     * A helper method for creating game elements.
     *
     * @param character the mode choice.
     */
    private void createGameElements(String character) {
        createBackGround();
        createObstacle();
        createJade();
        createPlayer(character);
    }

    /**
     * A helper method for creating background image.
     */
    private void createBackGround() {
        // The background image
        background = extractFigure(BACKGROUND_ID);
        getScalingRect();

        // Pass info of background's image for constructing game items
        presenter.addDeviceInfo(deviceWidth, deviceHeight);
    }

    /**
     * A helper method for creating obstacles images
     */
    private void createObstacle() {
        // Extract images for drawing
        obstacleFigure[0] = extractFigure(OBSTACLE_ID0);
        obstacleFigure[1] = extractFigure(OBSTACLE_ID1);
        obstacleFigure[2] = extractFigure(OBSTACLE_ID2);
        obstacleFigure[3] = extractFigure(OBSTACLE_ID3);
        obstacleFigure[4] = extractFigure(OBSTACLE_ID4);

        // Pass info of obstacle's image for constructing game items
        presenter.addObstacleInfo(obstacleFigure[0]);
    }

    /**
     * A helper method for creating jade images
     */
    private void createJade() {
        // Extract image for drawing
        jadeBmp = extractFigure(JADE_ID);
        // Pass info of jade's image for constructing game items
        presenter.addJadeInfo(jadeBmp);
    }

    /**
     * A helper method for creating player images.
     *
     * @param character the mode that user has selected.
     */
    private void createPlayer(String character) {
        // Get the images for drawing
        modeFactory(character);

        // Pass image's info for constructing game items
        presenter.createPlayer(playerFigure[0]);
    }

    /**
     * A helper method for creating character figures for the corresponding mode.
     *
     * @param character the mode that user has selected.
     */
    private void modeFactory(String character) {
        switch (Character.valueOf(character)) {
            case MAN:    // Images for the running man figure
                playerFigure[0] = extractFigure(MAN_ID0);
                playerFigure[1] = extractFigure(MAN_ID1);
                playerFigure[2] = extractFigure(MAN_ID2);
                playerFigure[3] = extractFigure(MAN_ID3);
                playerFigure[4] = extractFigure(MAN_ID4);
                break;
            case SHADOW:  // Images for the running shadow figure
                playerFigure[0] = extractFigure(SHADOW_ID0);
                playerFigure[1] = extractFigure(SHADOW_ID1);
                playerFigure[2] = extractFigure(SHADOW_ID2);
                playerFigure[3] = extractFigure(SHADOW_ID3);
                playerFigure[4] = extractFigure(SHADOW_ID4);
                break;
        }
    }

    /**
     * A helper method for extracting Bitmap from resource
     *
     * @param figureId the id of the Bitmap resource that is being extracted.
     * @return the Bitmap corresponding to the resource
     */
    private Bitmap extractFigure(int figureId) {
        return BitmapFactory.decodeResource(getResources(), figureId);
    }

}
