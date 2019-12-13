package by.epam.pavelshakhlovich.riverferry.ferry;

import by.epam.pavelshakhlovich.riverferry.car.Car;
import by.epam.pavelshakhlovich.riverferry.car.CarType;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class TestInstances {
    public FerryBoat ferryBoat;
    public Car passengerCar;
    public Car freightCar;


    @BeforeSuite
    public void createFerryBoatAndCars() {
        ferryBoat = new FerryBoat();
        ferryBoat.setLoadingArea(20.0);
        ferryBoat.setCarryingCapacity(10.0);
        ferryBoat.setCheckpoint(FerryAction.getPhaserRules());
        passengerCar = new Car(1, CarType.PASSENGER, 8.0, 2.5);
        freightCar = new Car(2, CarType.FREIGHT, 13.0, 5.5);
    }

    @Test
    public void testSetupFerry() {
        Ferry expected = new Ferry(70.0, 20.0, 15);
        Ferry actual = FerryAction.setupFerry("data/ferry_data.json");
        assertEquals(actual, expected);
    }

    public FerryBoat getFerryBoat() {
        return ferryBoat;
    }

    public Car getPassengerCar() {
        return passengerCar;
    }

    public Car getFreightCar() {
        return freightCar;
    }


}