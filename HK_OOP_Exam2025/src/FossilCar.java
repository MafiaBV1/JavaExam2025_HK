public class FossilCar extends Vehicle {
    private String fuelType;
    private int fuelAmount;

    public FossilCar(int vehicleId, String brand, String model, int yearModel, String registrationNumber,
                     String chassisNumber, boolean driveable, int numberOfSellableWheels, int scrapyardId,
                     String fuelType, int fuelAmount) {
        super(vehicleId, brand, model, yearModel, registrationNumber, chassisNumber, driveable, numberOfSellableWheels, scrapyardId);
        this.fuelType = fuelType;
        this.fuelAmount = fuelAmount;
    }

    @Override
    public String getType() {
        return "FossilCar";
    }


    @Override
    public String toString() {
        return super.toString() + String.format(", Drivstofftype: %s, Mengde: %dL", fuelType, fuelAmount);
    }


    // Getters
    public int getFuelAmount() {
        return fuelAmount;
    }
    public String getFuelType() {
        return fuelType;
    }
}
