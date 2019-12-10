package by.epam.pavelshakhlovich.riverferry.app;

import by.epam.pavelshakhlovich.riverferry.car.CarCreator;
import by.epam.pavelshakhlovich.riverferry.ferry.Ferry;
import by.epam.pavelshakhlovich.riverferry.ferry.FerryBoat;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Application {
    private final static Logger logger = LogManager.getLogger();

    public static void main(String[] args) throws Exception {

        Ferry ferry = setupFerry();
        createFerryBoat(ferry);

        ExecutorService es = Executors.newCachedThreadPool();
        es.submit(FerryBoat.INSTANCE);

        CarCreator carCreator = new CarCreator();
        List<Future<Boolean>> list = new ArrayList<>();
        for (int i = 0; i < ferry.getCarsCountToFerry(); i++) {
            list.add(es.submit(carCreator.createCar()));
        }
        es.shutdown();
        for (Future<Boolean> future : list) {
            System.out.println("The car has been successfully ferried " + future.get());
        }
    }

    private static void createFerryBoat(Ferry ferry) {
        FerryBoat.INSTANCE.setLoadingArea(ferry.getRequiredLoadingArea());
        FerryBoat.INSTANCE.setCarryingCapacity(ferry.getRequiredCarryingCapacity());
    }

    /**
     * Sets up {@link Ferry} object by given parameters from file "data/data.json"
     *
     * @return {@code Ferry} object
     */
    public static Ferry setupFerry() {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(new File("data/data.json"), Ferry.class);
        } catch (Exception e) {
            logger.error("File read error! Ferry was created with default parameters");
            return new Ferry(50.0, 15.0, 10);
        }
    }
}
