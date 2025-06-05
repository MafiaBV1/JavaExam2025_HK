public abstract class Vehicle {
    protected int vehicleId;
    protected String brand;
    protected String model;
    protected int yearModel;
    protected String registrationNumber;
    protected String chassisNumber;
    protected boolean driveable;
    protected int numberOfSellableWheels;
    protected int scrapyardId;

    public Vehicle(int vehicleId, String brand, String model, int yearModel, String registrationNumber,
                   String chassisNumber, boolean driveable, int numberOfSellableWheels, int scrapyardId) {
        this.vehicleId = vehicleId;
        this.brand = brand;
        this.model = model;
        this.yearModel = yearModel;
        this.registrationNumber = registrationNumber;
        this.chassisNumber = chassisNumber;
        this.driveable = driveable;
        this.numberOfSellableWheels = numberOfSellableWheels;
        this.scrapyardId = scrapyardId;
    }

    public abstract String getType(); // For eksempel "FossilCar"



    @Override
    public String toString() {
        return String.format("ID: %d, %s %s (%d), Reg#: %s, Chassis: %s, Kjørbar: %s, Hjul til salgs: %d, ScrapyardID: %d",
                vehicleId, brand, model, yearModel, registrationNumber, chassisNumber,
                driveable ? "Ja" : "Nei", numberOfSellableWheels, scrapyardId);
    }

}
