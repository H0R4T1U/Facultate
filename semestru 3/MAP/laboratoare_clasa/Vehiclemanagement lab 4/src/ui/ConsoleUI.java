package ui;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.util.List;

import domain.Customer;
import domain.Vehicle;
import exceptions.NoDataAvailableException;
import repository.CustomerRepository;
import service.VehicleService;

public class ConsoleUI {

	private static final String PATH_TO_CUSTOMER_FILE = "customerInitialLoadFile";

	private VehicleService vehicleService;
	private CustomerRepository customerRepository;

	public ConsoleUI(VehicleService vehicleService, CustomerRepository customerRepository) {
		this.vehicleService = vehicleService;
		this.customerRepository = customerRepository;
	}

	public void displayVehicles() {

		try {
			List<Vehicle> vehicles = vehicleService.getAll();

			System.out.println("The available vehicles in the system are:");

			for (Vehicle vehicle : vehicles) {
				vehicle.printVehicleDetails();
			}
		} catch (NoDataAvailableException e) {
			System.out.println(e.getMessage());
		}

	}

	public void addCustomer() {
		System.out.println("Give details about a customer:");
		//TODO read customer data from the keyboard

		try(BufferedReader scanner = new BufferedReader(new InputStreamReader(System.in))) {
			String pic = scanner.readLine();
			String name = scanner.readLine();
			int age = Integer.parseInt(scanner.readLine());
			Customer customer = new Customer(pic, name, age);

			customerRepository.addCustomer(customer);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

	public void displayCustomers() {
		List<Customer> customers = customerRepository.getAllCustomers(PATH_TO_CUSTOMER_FILE);

		if (customers.size() == 0) {
			System.out.println("There are no customers in the system!");
		} else {
			System.out.println("The available customers are:");
			for (Customer c : customers) {
				System.out.println(c.toString());
			}
		}
	}

}
