package com.example.threeseasons.summer;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

/**
 * Manages threading and updates.
 */
public class SummerThread extends Thread {

    /**
     * Where the game items are drawn.
     */
    private SummerGameView gameView;
    /**
     * The canvas container.
     */
    private final SurfaceHolder surfaceHolder;
    /**
     * Whether the thread is running.
     */
    private boolean isRunning;
    /**
     * The canvas on which to draw the game.
     */
    private static Canvas canvas;

    /**
     * Construct the thread.
     *
     * @param surfaceHolder the canvas container.
     * @param view          where the game items are drawn.
     */
    SummerThread(SurfaceHolder surfaceHolder, SummerGameView view) {
        this.surfaceHolder = surfaceHolder;
        this.gameView = view;
    }

    @Override
    public void run() {
        while (isRunning) {
            canvas = null;

            try {
                canvas = this.surfaceHolder.lockCanvas();
                synchronized (surfaceHolder) {
                    this.gameView.update();
                    this.gameView.draw(canvas);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (canvas != null) {
                    try {
                        surfaceHolder.unlockCanvasAndPost(canvas);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            try {
                sleep(300);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Change the state of the program
     */
    void setRunning(boolean isRunning) {
        this.isRunning = isRunning;
    }
}