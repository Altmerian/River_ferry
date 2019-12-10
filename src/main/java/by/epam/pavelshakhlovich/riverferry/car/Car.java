package by.epam.pavelshakhlovich.riverferry.car;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.Callable;

public class Car implements Callable<Boolean> {
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
    public Boolean call() throws Exception {
        try {
            carState = CarState.WAITING_FOR_UPLOAD;
            logger.info("Car #{} drove up to the ferry.\n", id);

            //BARRIER.await();
            carState = CarState.ALREADY_FERRIED;
            logger.info("Car #{} successfully ferried and continued driving.\n", id);
            return true;
        } catch (Exception e) {
            //InterruptedException
            //BrokenBarrierException

            return false;
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
