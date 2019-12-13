package by.epam.pavelshakhlovich.riverferry.ferry;

import com.google.common.annotations.VisibleForTesting;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.Phaser;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class FerryBoat implements Runnable {

    public static final FerryBoat INSTANCE = new FerryBoat();
    private final static Logger logger = LogManager.getLogger();
    public final Lock checkpointLock = new ReentrantLock(true);
    public Phaser checkpoint;
    public double reservedArea;
    public double reservedCapacity;
    public Condition onTheWay = checkpointLock.newCondition();
    private double loadingArea;
    private double carryingCapacity;

    @VisibleForTesting
    FerryBoat() {
    }

    @Override
    public void run() {
        checkpoint.register();
        do {
            logger.info("The ferry boat arrives!");
            try {
                TimeUnit.SECONDS.sleep(2); //uploading
                checkpoint.arriveAndAwaitAdvance();
            } catch (InterruptedException e) {
                logger.error("The ferry boat has been stopped by unknown cause!");
            }
        } while (!checkpoint.isTerminated());

        checkpoint.arriveAndDeregister();
        logger.info("All cars have been ferried!");
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
