package by.epam.pavelshakhlovich.riverferry.car;

import by.epam.pavelshakhlovich.riverferry.ferry.FerryBoat;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Car implements Callable<Boolean> {
    private final CarType carType;
    private final int id;
    private double carArea;
    private double carWeight;
    private CarState carState;
    private final static Logger logger = LogManager.getLogger();
    private final Lock checkpointLock = new ReentrantLock(true);


    public Car(int id, CarType carType, double carArea, double carWeight) {
        this.carType = carType;
        this.id = id;
        this.carArea = carArea;
        this.carWeight = carWeight;

    }

    @Override
    public Boolean call() throws Exception {
        try {
            driveUPtoTheFerry();

            while (!hasUploadedToFerryBoat()) {
                logger.info("Car #{} is waiting for upload to the ferry boat.", id);
                FerryBoat.INSTANCE.checkpoint.arriveAndAwaitAdvance();
            }

            FerryBoat.INSTANCE.checkpoint.arriveAndDeregister();
            TimeUnit.SECONDS.sleep(2);
            carState = CarState.ALREADY_FERRIED;
            logger.info("Car #{} successfully ferried and continued driving.", id);
            return true;
        } catch (InterruptedException e) {
            logger.error("Car #{} disappears", id);
            return false;
        }
    }

    private void driveUPtoTheFerry() throws InterruptedException {
        FerryBoat.INSTANCE.checkpoint.register();
        TimeUnit.MILLISECONDS.sleep(new Random().nextInt(1_000));
        logger.info("Car #{} drove up to the ferry.", id);
        carState = CarState.WAITING_FOR_UPLOAD;
    }

    private boolean hasUploadedToFerryBoat() throws InterruptedException {
        checkpointLock.lock();
        try {
            TimeUnit.MICROSECONDS.sleep(500);
            double reservedArea = FerryBoat.INSTANCE.getReservedArea();
            double reservedCapacity = FerryBoat.INSTANCE.getReservedCapacity();

            if (reservedArea + carArea <= FerryBoat.INSTANCE.getLoadingArea()
                    && reservedCapacity + carWeight <= FerryBoat.INSTANCE.getCarryingCapacity()) {
                FerryBoat.INSTANCE.setReservedArea(reservedArea + carArea);
                FerryBoat.INSTANCE.setReservedCapacity(reservedCapacity + carWeight);
                logger.info("Car #{} has been upload to the ferry boat.", id);
                return true;
            } else {
                return false;
            }
        } finally {
            checkpointLock.unlock();
        }
    }

    public int getId() {
        return id;
    }

    public CarType getCarType() {
        return carType;
    }

    public double getCarArea() {
        return carArea;
    }

    public double getCarWeight() {
        return carWeight;
    }

}
