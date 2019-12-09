package by.epam.pavelshakhlovich.riverferry.ferry;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.TimeUnit;

public class FerryBoat implements Runnable {
    private final static Logger logger = LogManager.getLogger();

    @Override
    public void run() {
        try {
            TimeUnit.SECONDS.sleep(2);
            System.out.println("Ferry boat has ferried the cars!");
        } catch (InterruptedException e) {
            logger.error("Ferry boat was stopped by unknown cause!");
        }
    }
}
