package by.epam.pavelshakhlovich.riverferry.ferry;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.Phaser;
import java.util.concurrent.TimeUnit;

public class FerryBoat implements Runnable {
    public static final FerryBoat INSTANCE = new FerryBoat();
    private final static Logger logger = LogManager.getLogger();
    public Phaser checkpoint;
    public double reservedArea;
    public double reservedCapacity;
    private double loadingArea;
    private double carryingCapacity;

    private FerryBoat() {
    }

    @Override
    public void run() {
        checkpoint.register();
        do {
            try {
                TimeUnit.SECONDS.sleep(3);
                checkpoint.arriveAndAwaitAdvance();
            } catch (InterruptedException e) {
                logger.error("Ferry boat was stopped by unknown cause!");
            }
        } while (!checkpoint.isTerminated());

                checkpoint.arriveAndDeregister();
        logger.info("All cars were ferried");
    }

    public void setCheckpoint(Phaser checkpoint) {
        this.checkpoint = checkpoint;
    }

    public double getReservedArea() {
        return reservedArea;
    }

    public void setReservedArea(double reservedArea) {
        this.reservedArea = reservedArea;
    }

    public double getReservedCapacity() {
        return reservedCapacity;
    }

    public void setReservedCapacity(double reservedCapacity) {
        this.reservedCapacity = reservedCapacity;
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
