package net.cyberflame.viewmodel.util;

import org.jetbrains.annotations.Contract;

public class Stopwatch {

    private long time;

    @Contract(pure = true)
    public Stopwatch() {
        super();
        this.time = -1;
    }

    public final boolean passed(double ms)
    {
        return System.currentTimeMillis() - this.time >= ms;
    }

    public final void reset()
    {
        this.time = System.currentTimeMillis();
    }

    public final void resetTimeSkipTo(long p_MS)
    {
        this.time = System.currentTimeMillis() + p_MS;
    }

    @Contract(pure = true)
    @Deprecated
    public final long getTime() {
        return this.time;
    }

    public final long getTimeReal() {
        return System.currentTimeMillis() - this.time;
    }

    @Contract(mutates = "this")
    public final void setTime(long time)
    {
        this.time = time;
    }

}
