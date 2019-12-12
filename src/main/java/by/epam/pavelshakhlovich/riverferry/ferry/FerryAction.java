package by.epam.pavelshakhlovich.riverferry.ferry;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.util.concurrent.Phaser;
import java.util.concurrent.TimeUnit;

public class FerryAction {
    private final static Logger logger = LogManager.getLogger();

    public static void createFerryBoat(Ferry ferry) {
        FerryBoat.INSTANCE.setLoadingArea(ferry.getRequiredLoadingArea());
        FerryBoat.INSTANCE.setCarryingCapacity(ferry.getRequiredCarryingCapacity());
        Phaser phaser = new Phaser() {
            @Override
            public boolean onAdvance(int phase, int parties) {
                FerryBoat.INSTANCE.setReservedArea(0.00);
                FerryBoat.INSTANCE.setReservedCapacity(0.00);
                try {
                    TimeUnit.SECONDS.sleep(1); //sailing
                    FerryBoat.INSTANCE.checkpointLock.lock();
                    FerryBoat.INSTANCE.onTheWay.signalAll(); // ferried carrying cars
                    FerryBoat.INSTANCE.checkpointLock.unlock();
                    TimeUnit.SECONDS.sleep(1); //sailing back
                    logger.info("The ferry boat has made {} run and ferried the cars!",
                            getPhase() + 1);
                } catch (InterruptedException e) {
                    logger.error("The ferry boat was stopped by unknown cause!");
                }
                return getArrivedParties() <= 1;
            }
        };
        FerryBoat.INSTANCE.setCheckpoint(phaser);
    }

    /**
     * Sets up {@link Ferry} object by given parameters from file "data/ferry_data.json"
     *
     * @return {@code Ferry} object
     */
    public static Ferry setupFerry(String dataFilePath) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(new File(dataFilePath), Ferry.class);
        } catch (Exception e) {
            logger.error("File read error! Ferry was created with default parameters");
            return new Ferry(50.0, 15.0, 10);
        }
    }
}
