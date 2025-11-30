package com.quizzy.utils;

import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;

import java.util.Locale;

public class GameTimer {
    public interface Listener {
        void onTick(long elapsedMillis);
    }

    private final long tickIntervalMs;
    private Listener listener;
    private final Handler handler = new Handler(Looper.getMainLooper());
    private long startTime = 0L;
    private long accumulatedBeforeStart = 0L;
    private boolean running = false;

    private final Runnable tickRunnable = new Runnable() {
        @Override
        public void run() {
            if (!running) return;
            long elapsed = SystemClock.elapsedRealtime() - startTime + accumulatedBeforeStart;
            if (listener != null) listener.onTick(elapsed);
            handler.postDelayed(this, tickIntervalMs);
        }
    };

    public GameTimer(long tickIntervalMs) {
        this.tickIntervalMs = tickIntervalMs;
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    public void start() {
        if (running) return;
        startTime = SystemClock.elapsedRealtime();
        running = true;
        handler.post(tickRunnable);
    }

    public void stop() {
        if (!running) return;
        long elapsed = SystemClock.elapsedRealtime() - startTime + accumulatedBeforeStart;
        accumulatedBeforeStart = elapsed;
        running = false;
        handler.removeCallbacks(tickRunnable);
        if (listener != null) listener.onTick(elapsed);
    }

    public boolean isRunning() {
        return running;
    }

    public static String formatTime(long millis) {
        long seconds = millis / 1000;
        long min = seconds / 60;
        long sec = seconds % 60;
        return String.format(Locale.US, "%02d:%02d", min, sec);
    }

    public long getElapsedMillis() {
        if (running) {
            return SystemClock.elapsedRealtime() - startTime + accumulatedBeforeStart;
        } else {
            return accumulatedBeforeStart;
        }
    }
}
