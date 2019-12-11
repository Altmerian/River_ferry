package by.epam.pavelshakhlovich.riverferry.car;

import by.epam.pavelshakhlovich.riverferry.ferry.FerryBoat;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

public class Car implements Callable<Car> {
    private final static Logger logger = LogManager.getLogger();
    private final CarType carType;
    private final int id;
    private double carArea;
    private double carWeight;
    private CarState carState;


    public Car(int id, CarType carType, double carArea, double carWeight) {
        this.carType = carType;
        this.id = id;
        this.carArea = carArea;
        this.carWeight = carWeight;

    }

    @Override
    public Car call() throws Exception {
        carState = CarState.ON_THE_ROAD;
        try {
            driveUPtoTheFerry();
            while (!hasUploadedToFerryBoat()) {
                logger.info("{} is waiting for upload to the ferry boat.", this);
                FerryBoat.INSTANCE.checkpoint.arriveAndAwaitAdvance();
            }
            FerryBoat.INSTANCE.checkpoint.arriveAndDeregister();
            FerryBoat.INSTANCE.checkpointLock.lock();
            FerryBoat.INSTANCE.onTheWay.await(); //carried by the ferry boat
            FerryBoat.INSTANCE.checkpointLock.unlock();
            carState = CarState.ALREADY_FERRIED;
            logger.info("Car #{} has been successfully ferried and continued driving.", id);
        } catch (InterruptedException e) {
            logger.error("Car #{} has been disappeared", id);
        }
        return this;
    }

    private void driveUPtoTheFerry() throws InterruptedException {
        FerryBoat.INSTANCE.checkpoint.register();
        TimeUnit.MILLISECONDS.sleep(new Random().nextInt(1_500));
        logger.info("Car #{} drove up to the ferry.", id);
        carState = CarState.WAITING_FOR_UPLOAD;
    }

    private boolean hasUploadedToFerryBoat() throws InterruptedException {
        FerryBoat.INSTANCE.checkpointLock.lock();
        try {
            TimeUnit.MICROSECONDS.sleep(500);
            double reservedArea = FerryBoat.INSTANCE.getReservedArea();
            double reservedCapacity = FerryBoat.INSTANCE.getReservedCapacity();
            if (reservedArea + carArea <= FerryBoat.INSTANCE.getLoadingArea()
                    && reservedCapacity + carWeight <= FerryBoat.INSTANCE.getCarryingCapacity()) {
                FerryBoat.INSTANCE.setReservedArea(reservedArea + carArea);
                FerryBoat.INSTANCE.setReservedCapacity(reservedCapacity + carWeight);
                logger.info("{} has been uploaded to the ferry boat.", this);
                logger.printf(Level.INFO, "Remaining capacity: area-%.2f, weight-%.2f",
                        FerryBoat.INSTANCE.getLoadingArea() - FerryBoat.INSTANCE.getReservedArea(),
                        FerryBoat.INSTANCE.getCarryingCapacity() - FerryBoat.INSTANCE.getReservedCapacity());
                carState = CarState.ON_THE_BOARD;
                return true;
            } else {
                return false;
            }
        } finally {
            FerryBoat.INSTANCE.checkpointLock.unlock();
        }
    }

    @Override
    public String toString() {
        return String.format("%s car [id=%d, area=%.2f, weight=%.2f]", carType.getTitle(), id, carArea, carWeight);
    }

    public int getId() {
        return id;
    }

    public CarState getCarState() {
        return carState;
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
