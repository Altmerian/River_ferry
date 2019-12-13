package by.epam.pavelshakhlovich.riverferry.app;

import by.epam.pavelshakhlovich.riverferry.car.Car;
import by.epam.pavelshakhlovich.riverferry.car.CarCreator;
import by.epam.pavelshakhlovich.riverferry.ferry.Ferry;
import by.epam.pavelshakhlovich.riverferry.ferry.FerryAction;
import by.epam.pavelshakhlovich.riverferry.ferry.FerryBoat;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class Application {
    private final static Logger logger = LogManager.getLogger();

    public static void main(String[] args) throws Exception {
        logger.trace("Entry");
        Ferry ferry = FerryAction.setupFerry("data/ferry_data.json");
        FerryAction.createFerryBoat(ferry);

        ExecutorService es = Executors.newCachedThreadPool();
        es.submit(FerryBoat.INSTANCE);

        List<Future<Car>> list = new ArrayList<>();
        for (int i = 0; i < ferry.getCarsCountToFerry(); i++) {
            list.add(es.submit(CarCreator.createCar()));
        }
        es.shutdown();
        es.awaitTermination(25, TimeUnit.SECONDS);

        for (Future<Car> future : list) {
            logger.printf(Level.INFO, "The car's #%-2d state: %s",
                    future.get().getId(), future.get().getCurrentCarState().getTitle());
        }
        logger.trace("Exit");
    }

}
