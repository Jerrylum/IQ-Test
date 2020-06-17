package io.gitlab.jerrylum.iqtestapplication.Timer;

import android.os.health.TimerStat;

public abstract class Timer {
    public long _previousTicks = 0;
    public long _startTick = 0;

    public void stop() {
        if (this.isRunning()) {
            this._previousTicks += System.currentTimeMillis() - this._startTick;
            this._startTick = 0;
        }
    }

    public abstract void start();

    public abstract void set(long ticks);

    public void reset() {
        this._startTick = 0;
        this._previousTicks = 0;

        // no
    }

    public abstract long getDisplayTicks();

    public boolean isRunning() {
        return this.getStatus() == TimerStatus.RUNNING;
    }

    public abstract TimerStatus getStatus();
}
