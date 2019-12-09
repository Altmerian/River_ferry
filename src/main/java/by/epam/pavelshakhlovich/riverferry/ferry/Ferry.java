package by.epam.pavelshakhlovich.riverferry.ferry;

import by.epam.pavelshakhlovich.riverferry.car.Car;
import by.epam.pavelshakhlovich.riverferry.car.CarCreator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Ferry {
    private double loadingArea;
    private double carryingCapacity;
    private static final Ferry INSTANCE = new Ferry();
    Queue<Car> carQueue = new ConcurrentLinkedQueue<>();
    private final static Logger logger = LogManager.getLogger();

        public static void main(String[] args) throws Exception {
            CarCreator carCreator = new CarCreator();
            List<Future<Boolean>> list = new ArrayList<>();
            ExecutorService es = Executors.newFixedThreadPool(2);
            for (int i = 0; i < 7; i++) {
                list.add(es.submit(carCreator.createCar()));
            }
            es.shutdown();
            for (Future<Boolean> future : list) {
                System.out.println("The car has been successfully ferried " + future.get());
            }
        }



}
