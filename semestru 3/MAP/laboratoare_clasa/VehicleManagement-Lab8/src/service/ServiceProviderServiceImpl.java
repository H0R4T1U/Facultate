package service;

import java.util.Comparator;
import java.util.List;

import domain.GPSCoordinates;
import domain.ServiceProvider;
import repository.ServiceProviderRepository;

public class ServiceProviderServiceImpl implements ServiceProviderService {
	private DistanceCalculator distanceCalculator;
	private ServiceProviderRepository serviceProviderRepository;

	public ServiceProviderServiceImpl(DistanceCalculator distanceCalculator, ServiceProviderRepository repo) {
		this.distanceCalculator = distanceCalculator;
		this.serviceProviderRepository=repo;
	}

	@Override
	public ServiceProvider getNearestServiceProvider(GPSCoordinates customerBreakdown) {
		List<ServiceProvider>serviceProviders = serviceProviderRepository.getServiceProviders();
		
		serviceProviders.forEach(s->{
			Double distance = distanceCalculator.computeDistance(s.getCoordinates(), customerBreakdown);
			//TODO set computed distance for service provider
			s.setDistance(distance);

			
		});
		
		//TODO sort service providers based on distance descending
		Comparator<ServiceProvider> sort_by_dist_desc = Comparator.comparing(ServiceProvider::getDistance, Comparator.reverseOrder());
		serviceProviders = serviceProviders.stream().sorted(sort_by_dist_desc).toList();
		return serviceProviders.get(0);
	}
}
