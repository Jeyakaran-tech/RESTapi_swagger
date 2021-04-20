package com.spaceship.demo.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.spaceship.demo.model.Location;
import com.spaceship.demo.model.SpaceShip;
import com.spaceship.demo.model.Travel;
import com.spaceship.demo.model.spaceshipStatus;
import com.spaceship.demo.repositories.LocationRepository;
import com.spaceship.demo.repositories.SpaceShipRepository;


@RestController
public class TravelController 
{
	@Autowired
	public LocationRepository locationRepo; //getting the object of Mongo Repository(Location)

	@Autowired
	public SpaceShipRepository spaceRepo; //getting the object of Mongo Repository(Spaceship)



	//Travel functionality
	@PostMapping (value = "/travel")
	public String travel(@RequestBody Travel travel)
	{
		Optional<SpaceShip> sh=spaceRepo.findById(travel.getShipID());
		Optional<Location> dloc=locationRepo.findById(travel.getDestID());
		Optional<Location> sloc=locationRepo.findById(travel.getSourceID());

		if(sh.isEmpty() || dloc.isEmpty() || sloc.isEmpty()) {
			return "No Destination/SpaceShip is found :(";
		}

		if(dloc.get().getId() == sh.get().getLocationId()) {
			return "The spaceship is already in the destined location. Change the destination";
		}

		Location destLoc=dloc.get();
		Location sourceLoc=sloc.get();
		SpaceShip sp=sh.get();

		if(destLoc.getCapacity() > 0 
				&& sp.getStatus().equals(spaceshipStatus.OPERATIONAL)) //checking whether the status is operational and capacity is greater than 0
		{
			
			destLoc.setCapacity(destLoc.getCapacity()-1); //decrementing the destination's capacity
			sourceLoc.setCapacity(sourceLoc.getCapacity()+1);//incrementing the source location's capacity

			sp.setLocation(destLoc.getCityName()+" "+destLoc.getPlanetName()); //changing the name to destination location
			sp.setLocationId(travel.getDestID()); //changing the ID to destination location

			//saving the data to mongoDB
			spaceRepo.save(sp);			 
			locationRepo.save(sourceLoc);
			locationRepo.save(destLoc);

			return "travel success";

		}

		return "Travel is not possible";		

	}
}
