package by.epam.pavelshakhlovich.riverferry.ferry;

public class Ferry {
    private double requiredLoadingArea;
    private double requiredCarryingCapacity;
    private int carsCountToFerry;

    public Ferry() {
    }

    public Ferry(double requiredLoadingArea, double requiredCarryingCapacity, int carsCountToFerry) {
        this.requiredLoadingArea = requiredLoadingArea;
        this.requiredCarryingCapacity = requiredCarryingCapacity;
        this.carsCountToFerry = carsCountToFerry;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Ferry)) {
            return false;
        }

        Ferry ferry = (Ferry) obj;

        if (Double.compare(ferry.requiredLoadingArea, requiredLoadingArea) != 0) {
            return false;
        }
        if (Double.compare(ferry.requiredCarryingCapacity, requiredCarryingCapacity) != 0) {
            return false;
        }
        return carsCountToFerry == ferry.carsCountToFerry;
    }

    @Override
    public int hashCode() {
        int result = Double.hashCode(requiredLoadingArea);
        result = 31 * result + Double.hashCode(requiredCarryingCapacity);
        result = 31 * result + carsCountToFerry;
        return result;
    }

    public double getRequiredLoadingArea() {
        return requiredLoadingArea;
    }

    public double getRequiredCarryingCapacity() {
        return requiredCarryingCapacity;
    }

    public int getCarsCountToFerry() {
        return carsCountToFerry;
    }
}
