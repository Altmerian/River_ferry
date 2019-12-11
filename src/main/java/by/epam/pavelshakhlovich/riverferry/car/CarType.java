package by.epam.pavelshakhlovich.riverferry.car;

public enum CarType {
    PASSENGER("Passenger", 1.0, 1.0),
    FREIGHT("Freight", 1.5, 2.0);

    private String title;
    private double areaMultiplier;
    private double weightMultiplier;

    CarType(String title, double areaMultiplier, double weightMultiplier) {
        this.title = title;
        this.areaMultiplier = areaMultiplier;
        this.weightMultiplier = weightMultiplier;
    }

    public String getTitle() {
        return title;
    }

    public double getAreaMultiplier() {
        return areaMultiplier;
    }

    public double getWeightMultiplier() {
        return weightMultiplier;
    }
}
