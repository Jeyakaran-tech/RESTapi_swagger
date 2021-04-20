package com.spaceship.demo.controllers;

import java.io.Console;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.spaceship.demo.model.Location;
import com.spaceship.demo.model.SpaceShip;
import com.spaceship.demo.repositories.LocationRepository;
import com.spaceship.demo.repositories.SpaceShipRepository;



@RestController
public class SpaceShipController 
{
	
	@Autowired
	public SpaceShipRepository spaceRepo; //getting the object of Mongo Repository(Spaceship)
	
	@Autowired
	public LocationRepository locationRepo; //getting the object of Mongo Repository(Location)
	

	
	// List out all the spaceships
	@GetMapping (value = "/all")
	public List<SpaceShip> getAllSpaceShips()
	{
		return spaceRepo.findAll();
		
	}
	
	
	// Create a spaceship 
	@PostMapping (value = "/create")
	public String createSpaceship(@RequestBody SpaceShip spaceship)
	{
		
		//condition to check the spaceship
		if(spaceRepo.findById(spaceship.getId()).isPresent())
		{
			return "Spaceship exists already";
		}
		
		//condition to check the Location
		Optional<Location> location=locationRepo.findById(spaceship.getLocationId());
		
		if(location.isEmpty()) 
		{
			return "Location doesnt exist";
		}
		
		Location foundLocation=location.get();
		
		if(foundLocation.getCapacity() > 0)
		{
			spaceship.setLocation(foundLocation.getCityName()+" "+foundLocation.getPlanetName()); //extracting the names from Location entity
			int cap = foundLocation.getCapacity(); //getting the capacity of the location
			cap-=1;
			foundLocation.setCapacity(cap); //reducing the capacity since the new spaceship is stationed there
			
			locationRepo.save(foundLocation); //saving the object to MongoDB
		
			SpaceShip insertedship = spaceRepo.insert(spaceship); //inserting the object into mongoDB
			return "Spaceship created "+insertedship.getName();
			
		}
		else
		{
			return "No capacity to station the Spaceship";
		}	
		
	
	}
	
	
	//Method to delete the particular spaceship by giving the ID
	@RequestMapping (value = "/delete/{Id}", method = RequestMethod.POST)
	public String deleteSpaceShip(@PathVariable("Id") long id) 
	{		
		if(spaceRepo.findById(id).isEmpty())
		{			
			return "Cannot delete :( since Spaceship ID is not found";
		}
		spaceRepo.deleteById(id);
	    return "Successfully deleted";
	}
	
	
	//function to update the status 
	//needs work - need to update location with this.
	@RequestMapping (value = "/update/{id}", method = RequestMethod.PUT)
	public String updateSpaceShip(@RequestBody SpaceShip spaceship) 
	{		
		if(spaceRepo.findById(spaceship.getId()).isEmpty())
		{			
			return "Cannot update :( since Spaceship ID is not found";
		}
		spaceship.setStatus(spaceship.getStatus());
		this.spaceRepo.save(spaceship);	
		
		return "Successfully updated";
	}



	

}
