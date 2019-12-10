package by.epam.pavelshakhlovich.riverferry.ferry;

import com.google.common.util.concurrent.AtomicDouble;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.Phaser;
import java.util.concurrent.TimeUnit;

public class FerryBoat implements Runnable {
    public static final FerryBoat INSTANCE = new FerryBoat();
    private final static Logger logger = LogManager.getLogger();
    public Phaser checkpoint = new Phaser();
    public AtomicDouble freeArea;
    public AtomicDouble freeCapacity;
    private double loadingArea;
    private double carryingCapacity;

    private FerryBoat() {
    }

    @Override
    public void run() {
        checkpoint.register();
        while (checkpoint.getUnarrivedParties() > 0) {
            freeArea.set(loadingArea);
            freeCapacity.set(carryingCapacity);
            checkpoint.arriveAndAwaitAdvance();
            try {
                TimeUnit.SECONDS.sleep(2);
                logger.info("Ferry boat has made {} run and ferried the cars!", checkpoint.getPhase() + 1);
            } catch (InterruptedException e) {
                logger.error("Ferry boat was stopped by unknown cause!");
            }
        }

    }


    public AtomicDouble getFreeArea() {
        return freeArea;
    }

    public void setFreeArea(AtomicDouble freeArea) {
        this.freeArea = freeArea;
    }

    public AtomicDouble getFreeCapacity() {
        return freeCapacity;
    }

    public void setFreeCapacity(AtomicDouble freeCapacity) {
        this.freeCapacity = freeCapacity;
    }

    public double getLoadingArea() {
        return loadingArea;
    }

    public void setLoadingArea(double loadingArea) {
        this.loadingArea = loadingArea;
    }

    public double getCarryingCapacity() {
        return carryingCapacity;
    }

    public void setCarryingCapacity(double carryingCapacity) {
        this.carryingCapacity = carryingCapacity;
    }
}
