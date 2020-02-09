package com.example.threeseasons.winter;

import android.os.CountDownTimer;

public abstract class CountUpTimer extends CountDownTimer {

    /**
     * Constant for the time interval of each tick.
     */
    private static final long INTERVAL_MS = 1000;
    /**
     * Duration of this timer.
     */
    private final long duration;

    /**
     * Construct for the count-up timer.
     *
     * @param durationMs duration of this timer in ms.
     */
    CountUpTimer(long durationMs) {
        super(durationMs, INTERVAL_MS);
        this.duration = durationMs;
    }

    /**
     * Determine program's behaviour after each tick.
     */
    public abstract void onTick(int second);

    @Override
    public void onTick(long msUntilFinished) {
        int second = (int) ((duration - msUntilFinished) / 1000);
        onTick(second);
    }

    @Override
    public void onFinish() {
        onTick(duration / 1000);
    }
}
