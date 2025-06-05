public class ElectricCar extends Vehicle {
    private int batteryCapacity;
    private int chargeLevel;

   public ElectricCar(int vehicleId, String brand, String model, int yearModel, String registrationNumber,
                      String chassisNumber, boolean driveable, int numberOfSellableWheels, int scrapyardId,
                      int batteryCapacity, int chargeLevel) {
       super(vehicleId, brand, model, yearModel, registrationNumber, chassisNumber, driveable, numberOfSellableWheels, scrapyardId);
       this.batteryCapacity = batteryCapacity;
       this.chargeLevel = chargeLevel;
   }

    @Override
    public String getType() {
        return "ElectricCar";
    }


    @Override
    public String toString() {
        return super.toString() + String.format(", Batteri: %dkWh, Lading: %d%%", batteryCapacity, chargeLevel);
    }



    // Getters
    public int getBatteryCapacity() {
        return batteryCapacity;
    }
    public int getChargeLevel() {
        return chargeLevel;
    }
}
