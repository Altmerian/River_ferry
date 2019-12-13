package by.epam.pavelshakhlovich.riverferry.car;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class CarCreator {
    private static final AtomicInteger idGenerator = new AtomicInteger(1);
    private static final double CAR_AREA_SQ_M = 8.0;
    private static final double CAR_WEIGHT_TONS = 1.5;

    public static Car createCar() {
        Random random = new Random();
        CarType carType = CarType.PASSENGER;
        if (random.nextInt(4) == 3) {
            carType = CarType.FREIGHT;
        }
        double carArea = (0.5 + random.nextDouble()) * CAR_AREA_SQ_M * carType.getAreaMultiplier();
        double carWeight = (0.5 + random.nextDouble()) * CAR_WEIGHT_TONS * carType.getWeightMultiplier();
        int id = idGenerator.getAndIncrement();
        return new Car(id, carType, carArea, carWeight);
    }


}
