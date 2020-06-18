package io.gitlab.jerrylum.iqtestapplication.Classes;

public class StopwatchTimer extends Timer{
    public long _initTicks = 0;

    public StopwatchTimer() {
        // empty
    }

    public void start() {
        if (!this.isRunning())
            this._startTick = System.currentTimeMillis();
    }

    @Override
    public void set(long ticks) {
        if (this.getStatus() == TimerStatus.PAUSE) {
            this._initTicks = ticks - 1;
            this._previousTicks = 1;
        } else {
            this._initTicks = ticks;
        }
    }

    @Override
    public long getDisplayTicks() {
        return this.getPassedTicks();
    }

    @Override
    public TimerStatus getStatus() {
        if (this._startTick == 0 && this._previousTicks == 0)
            return TimerStatus.INIT;
        else if (this._startTick == 0 && this._previousTicks != 0)
            return TimerStatus.PAUSE;
        else
            return TimerStatus.RUNNING;
    }

    public long getPassedTicks() {
        return Math.max(
                0,
                (
                    this._startTick != 0 ?
                        System.currentTimeMillis() - this._startTick :
                        0
                ) + this._initTicks + this._previousTicks
        );
    }

}
