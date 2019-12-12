package by.epam.pavelshakhlovich.riverferry.carstate;

import by.epam.pavelshakhlovich.riverferry.car.Car;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class CarState {
    final static Logger logger = LogManager.getLogger();
    private String title;

    public CarState(String title) {
        this.title = title;
    }

    public abstract void performAction(Car car);

    public String getTitle() {
        return title;
    }
}
