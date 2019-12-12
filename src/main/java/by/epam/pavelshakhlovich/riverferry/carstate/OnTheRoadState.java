package by.epam.pavelshakhlovich.riverferry.carstate;

import by.epam.pavelshakhlovich.riverferry.car.Car;
import by.epam.pavelshakhlovich.riverferry.ferry.FerryBoat;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class OnTheRoadState extends CarState {


    public OnTheRoadState() {
        super("'On the road to the ferry'");
    }

    @Override
    public void performAction(Car car) {
        try {
            TimeUnit.MILLISECONDS.sleep(new Random().nextInt(1_500)); //driving to the ferry
            FerryBoat.INSTANCE.checkpoint.register();
            logger.info("Car #{} has driven up to the ferry.", car.getId());
            car.setCurrentCarState(new WaitingForUploadState());
        } catch (InterruptedException e) {
            logger.error("Car #{} hasn't driven up to the ferry for some reason.", car.getId());
        }
    }
}
