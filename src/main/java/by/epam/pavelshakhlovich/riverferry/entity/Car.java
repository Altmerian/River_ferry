package by.epam.pavelshakhlovich.riverferry.entity;

import java.util.concurrent.Callable;

public abstract class Car implements Callable<Boolean> {
    private double carArea;
    private double carWeight;

    public Car(double carArea, double carWeight) {
        this.carArea = carArea;
        this.carWeight = carWeight;
    }

    @Override
    public Boolean call() throws Exception {
        return true;
    }

    public double getCarArea() {
        return carArea;
    }

    public void setCarArea(double carArea) {
        this.carArea = carArea;
    }

    public double getCarWeight() {
        return carWeight;
    }

    public void setCarWeight(double carWeight) {
        this.carWeight = carWeight;
    }
}
