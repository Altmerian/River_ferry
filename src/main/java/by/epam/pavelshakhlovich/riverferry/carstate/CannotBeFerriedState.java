package by.epam.pavelshakhlovich.riverferry.carstate;

import by.epam.pavelshakhlovich.riverferry.car.Car;

public class CannotBeFerriedState extends CarState {

    public CannotBeFerriedState() {
        super("'Cannot be ferried!'");
    }

    @Override
    public void performAction(Car car) {
        logger.error("Car #{} cannot be ferried because of its size!", car.getId());
    }
}
