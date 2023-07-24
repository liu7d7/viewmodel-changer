package net.cyberflame.viewmodel.util;

public class Stopwatch {

    private long time;

    public Stopwatch() {
        super();
        time = -1;
    }

    public final boolean passed(double ms)
    {
        return System.currentTimeMillis() - this.time >= ms;
    }

    public final void reset()
    {
        this.time = System.currentTimeMillis();
    }

    public void resetTimeSkipTo(long p_MS)
    {
        this.time = System.currentTimeMillis() + p_MS;
    }

    @Deprecated
    public long getTime() {
        return time;
    }

    public long getTimeReal() {
        return System.currentTimeMillis() - this.time;
    }

    public void setTime(long time)
    {
        this.time = time;
    }

}
