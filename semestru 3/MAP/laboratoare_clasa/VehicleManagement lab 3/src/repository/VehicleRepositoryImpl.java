package repository;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import domain.Vehicle;

public class VehicleRepositoryImpl implements VehicleRepository {

	//TODO do we still need this variable since we are using List?

	//TODO replace the array with a list. Use List<Vehicle> interface and ArrayList as implementation
	private final List<Vehicle> vehicles;
	//TODO do we still need this variable since we are using List?

	//TODO refactor constructor
	public VehicleRepositoryImpl() {
		this.vehicles = new ArrayList<Vehicle>();
	}

	//TODO refactor the method based on the list
	public void addVehicle(Vehicle newVehicle) {
		if ( !newVehicle.isInactive()) {
			vehicles.addLast(newVehicle);
		} else {
			System.out.println("No vehicles can be added to the stock");
		}
	}

	//TODO refactor the method
	public Vehicle getVehicleAtPosition(int position) {
		return vehicles.get(position);
	}

	public int getNumberOfVehicles() { return  vehicles.size(); }

	//TODO refactor the method
	public Vehicle[] getVehicles() {
		return vehicles.toArray(new Vehicle[0]);
	}

	@Override
	public void deleteVehicle(Vehicle vehicle) {
		Integer currentYear = Calendar.getInstance().get(Calendar.YEAR);

		if ((currentYear - vehicle.getYear()) > 30) {
			// we will delete the vehicle, mark it as being inactive
			vehicle.setInactive();
		}
	}
}
