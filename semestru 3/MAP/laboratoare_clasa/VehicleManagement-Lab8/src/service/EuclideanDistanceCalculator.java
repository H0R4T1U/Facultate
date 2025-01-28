package service;

import domain.GPSCoordinates;

public class EuclideanDistanceCalculator implements DistanceCalculator{

	@Override
	public Double computeDistance(GPSCoordinates point1, GPSCoordinates point2) {
		// TODO PE CARE L AM STERS DIN GRESEALA
		double patrat_lat = Math.pow(point1.getLatitude() - point2.getLatitude(),2);
		double patrat_long = Math.pow(point1.getLongitude() - point2.getLongitude(),2);
		return Math.sqrt(patrat_lat+patrat_long);

	}

}
