package by.epam.pavelshakhlovich.riverferry.carstate;

import by.epam.pavelshakhlovich.riverferry.car.Car;

public class AlreadyFerriedState extends CarState {

    public AlreadyFerriedState() {
        super("'Already ferried'");
    }

    @Override
    public void performAction(Car car) {
        logger.error("Car #{} has been already ferried and finished all actions!", car.getId());
    }
}
