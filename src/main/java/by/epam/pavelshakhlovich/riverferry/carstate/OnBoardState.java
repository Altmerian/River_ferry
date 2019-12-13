package by.epam.pavelshakhlovich.riverferry.carstate;

import by.epam.pavelshakhlovich.riverferry.car.Car;
import by.epam.pavelshakhlovich.riverferry.ferry.FerryBoat;
import org.apache.logging.log4j.Level;

public class OnBoardState extends CarState {

    public OnBoardState() {
        super("'On the board'");
    }

    @Override
    public void performAction(Car car) {
        FerryBoat.INSTANCE.checkpoint.arriveAndDeregister();
        FerryBoat.INSTANCE.checkpointLock.lock();
        try {
            FerryBoat.INSTANCE.onTheWay.await(); //carried by the ferry boat
            logger.printf(Level.INFO, "Car #%-2d has been successfully ferried and continued driving.",
                    car.getId());

        } catch (InterruptedException e) {
            logger.error("Car #{} has been disappeared while carrying...", car.getId());
        } finally {
            FerryBoat.INSTANCE.checkpointLock.unlock();
        }
        car.setCurrentCarState(new AlreadyFerriedState());
    }
}
