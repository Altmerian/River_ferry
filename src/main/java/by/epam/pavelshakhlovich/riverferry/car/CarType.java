package by.epam.pavelshakhlovich.riverferry.car;

public enum CarType {
    PASSENGER(1.0, 1.0),
    FREIGHT(1.5, 2.0);

    private double areaMultiplier;
    private double weightMultiplier;

    CarType(double areaMultiplier, double weightMultiplier) {
        this.areaMultiplier = areaMultiplier;
        this.weightMultiplier = weightMultiplier;
    }

    public double getAreaMultiplier() {
        return areaMultiplier;
    }

    public double getWeightMultiplier() {
        return weightMultiplier;
    }
}
