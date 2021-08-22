package de.drjonah.ffa.utils;

import de.drjonah.ffa.Main;
import de.drjonah.ffa.kiteditor.listener.KitListener;
import org.bukkit.scheduler.BukkitRunnable;

public class Timer {

    private boolean running;
    private int time;


    public Timer(boolean running, int time) {
        this.running = running;
        this.time = time;

        run();
    }

    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }


    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public void run() {
        new BukkitRunnable() {
            @Override
            public void run() {

                if (!isRunning()) {
                    return;
                }

                if (getTime() == 0) {
                    setTime(1);
                    setRunning(false);
                    KitListener.isAllowed = true;
                }

                time--;
            }
        }.runTaskTimer(Main.getInstance(), 20, 20);
    }
}
