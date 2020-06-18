package io.gitlab.jerrylum.iqtestapplication.Timer;

import io.gitlab.jerrylum.iqtestapplication.API;

public class AndroidStopwatchTimer extends StopwatchTimer {
    public AndroidStopwatchTimer() {
        super();

        this._initTicks = API.getConfig().getLong("timer_initTicks", 0);
        this._startTick = API.getConfig().getLong("timer_startTick", 0);
        this._previousTicks = API.getConfig().getLong("timer_previousTicks", 0);
    }

    @Override
    public void start() {
        super.start();
        saveChange();
    }

    @Override
    public void set(long ticks) {
        super.set(ticks);
        saveChange();
    }

    @Override
    public void stop() {
        super.stop();
        saveChange();
    }

    private void saveChange() {
        API.setConfig().putLong("timer_initTicks", this._initTicks)
                       .putLong("timer_startTick", this._startTick)
                       .putLong("timer_previousTicks", this._previousTicks)
                       .commit();
    }
}
