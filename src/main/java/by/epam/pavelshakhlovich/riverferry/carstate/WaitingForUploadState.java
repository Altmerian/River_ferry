package by.epam.pavelshakhlovich.riverferry.carstate;

import by.epam.pavelshakhlovich.riverferry.car.Car;
import by.epam.pavelshakhlovich.riverferry.ferry.FerryBoat;
import org.apache.logging.log4j.Level;

import java.util.concurrent.TimeUnit;

public class WaitingForUploadState extends CarState {

    public WaitingForUploadState() {
        super("'Waiting for upload'");
    }

    @Override
    public void performAction(Car car) {
        if (hasTooBigAreaOrWeight(car)) {
            car.setCurrentCarState(new CannotBeFerriedState());
            logger.error("{} is too big to be carried by the ferry boat.", car);
            FerryBoat.INSTANCE.checkpoint.arriveAndDeregister();
        }

        while (!hasUploadedToFerryBoat(car)) {
            logger.info("{} is waiting for upload to the ferry boat.", car);
            FerryBoat.INSTANCE.checkpoint.arriveAndAwaitAdvance();
        }
    }

    private boolean hasTooBigAreaOrWeight(Car car) {
        return car.getCarArea() > FerryBoat.INSTANCE.getLoadingArea() ||
        car.getCarWeight() > FerryBoat.INSTANCE.getCarryingCapacity();
    }

    private boolean hasUploadedToFerryBoat(Car car) {
        FerryBoat.INSTANCE.checkpointLock.lock();
        try {
            TimeUnit.MILLISECONDS.sleep(50); //trying to get on the ferry boat
            double reservedArea = FerryBoat.INSTANCE.getReservedArea();
            double reservedCapacity = FerryBoat.INSTANCE.getReservedCapacity();
            if (reservedArea + car.getCarArea() <= FerryBoat.INSTANCE.getLoadingArea()
                    && reservedCapacity + car.getCarWeight() <= FerryBoat.INSTANCE.getCarryingCapacity()) {
                FerryBoat.INSTANCE.setReservedArea(reservedArea + car.getCarArea());
                FerryBoat.INSTANCE.setReservedCapacity(reservedCapacity + car.getCarWeight());
                logger.info("{} has been uploaded to the ferry boat.", car);
                logger.printf(Level.INFO, "Remaining capacity: area-%.2f, weight-%.2f",
                        FerryBoat.INSTANCE.getLoadingArea() - FerryBoat.INSTANCE.getReservedArea(),
                        FerryBoat.INSTANCE.getCarryingCapacity() - FerryBoat.INSTANCE.getReservedCapacity());
                car.setCurrentCarState(new OnBoardState());
                return true;
            } else {
                return false;
            }
        } catch (InterruptedException e) {
            logger.error("Car #{} hasn't been uploaded to the ferry boat for some reason.", car.getId());
            return false;
        } finally {
            FerryBoat.INSTANCE.checkpointLock.unlock();
        }
    }
}
