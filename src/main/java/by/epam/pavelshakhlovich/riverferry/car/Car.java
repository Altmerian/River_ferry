package by.epam.pavelshakhlovich.riverferry.car;

import by.epam.pavelshakhlovich.riverferry.carstate.AlreadyFerriedState;
import by.epam.pavelshakhlovich.riverferry.carstate.CarState;
import by.epam.pavelshakhlovich.riverferry.carstate.OnTheRoadState;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.Callable;

public class Car implements Callable<Car> {
    private final static Logger logger = LogManager.getLogger();
    private final CarType carType;
    private final int id;
    private double carArea;
    private double carWeight;
    private CarState currentCarState;


    public Car(int id, CarType carType, double carArea, double carWeight) {
        this.carType = carType;
        this.id = id;
        this.carArea = carArea;
        this.carWeight = carWeight;
        currentCarState = new OnTheRoadState();
    }

    /**
     * Doesn't throws any exceptions because all of them are handled inside appropriate {@link CarState#performAction}
     * methods, which are invoked by {@code Car#continueDriving} depending on the current {@code CarState}.
     *
     * @return this for future handling.
     */
    @Override
    public Car call() {
        while (!(currentCarState instanceof AlreadyFerriedState)) {
            continueDriving();
        }
        logger.printf(Level.INFO, "Car #%-2d has been successfully ferried and continued driving.", id);
        return this;
    }

    public void continueDriving() {
        currentCarState.performAction(this);
    }


    @Override
    public String toString() {
        return String.format("%s car [id=%d, area=%.2f, weight=%.2f]", carType.getTitle(), id, carArea, carWeight);
    }

    public CarState getCurrentCarState() {
        return currentCarState;
    }

    public void setCurrentCarState(CarState currentCarState) {
        this.currentCarState = currentCarState;
    }

    public int getId() {
        return id;
    }

    public double getCarArea() {
        return carArea;
    }

    public double getCarWeight() {
        return carWeight;
    }

    public CarType getCarType() {
        return carType;
    }

}
