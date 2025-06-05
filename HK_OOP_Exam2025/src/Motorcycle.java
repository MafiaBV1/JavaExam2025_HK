public class Motorcycle extends Vehicle {
    private boolean hasSidecar;
    private int engineCapacity;
    private boolean isModified;
    private int numberOfWheels;

    public Motorcycle(int vehicleId, String brand, String model, int yearModel, String registrationNumber,
                      String chassisNumber, boolean driveable, int numberOfSellableWheels, int scrapyardId,
                      boolean hasSidecar, int engineCapacity, boolean isModified, int numberOfWheels) {
        super(vehicleId, brand, model, yearModel, registrationNumber, chassisNumber, driveable, numberOfSellableWheels, scrapyardId);
        this.hasSidecar = hasSidecar;
        this.engineCapacity = engineCapacity;
        this.isModified = isModified;
        this.numberOfWheels = numberOfWheels;
    }

    @Override
    public String getType() {
        return "Motorcycle";
    }


    @Override
    public String toString() {
        return super.toString() + String.format(", Sidevogn: %s, Motorvolum: %dcc, Modifisert: %s, Antall hjul: %d",
                hasSidecar ? "Ja" : "Nei", engineCapacity, isModified ? "Ja" : "Nei", numberOfWheels);
    }


    // Getters
    public int getNumberOfWheels() {
        return numberOfWheels;
    }
    public boolean isModified() {
        return isModified;
    }
    public boolean isHasSidecar() {
        return hasSidecar;
    }
    public int getEngineCapacity() {
        return engineCapacity;
    }
}
