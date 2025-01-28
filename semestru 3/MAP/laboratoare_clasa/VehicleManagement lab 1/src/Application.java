import domain.Vehicle;
import repository.VehicleRepository;

public class Application {

	public static void main(String[] args) {
		Vehicle vehicle;
		//TODO - instantiate a vehicle
		
		VehicleRepository repository = new VehicleRepository();
		repository.addVehicle(vehicle);
	
		for (int i = 0; i < repository.getNumberOfVehicles(); i++) {
			Vehicle retrievedVehicle = repository.getVehicleAtPosition(i);
			//TODO -print vehicle details for retrievedVehicle object
		}
	}

}
