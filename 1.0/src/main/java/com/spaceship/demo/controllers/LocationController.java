package com.spaceship.demo.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.spaceship.demo.model.Location;
import com.spaceship.demo.repositories.LocationRepository;

@RestController
public class LocationController 
{

	@Autowired
	public LocationRepository locationRepo;

	//retrieving all the location
	@GetMapping (value = "/allloc")
	public List<Location> getAllLocation()
	{
		return locationRepo.findAll();

	}

	//creating the new location
	@PostMapping (value = "/createloc")
	public String createLocation(@RequestBody Location location)
	{


		if(!locationRepo.findById(location.getId()).isEmpty()) //check whether the location exists already
		{
			return "Location exists already";
		}

		Location insertedLocation = locationRepo.insert(location); //location was created and saved to mongoDB
		return "Location created "+insertedLocation.getId();
	}

	//deleting the location
	@RequestMapping (value = "/deleteloc/{Id}", method = RequestMethod.POST)
	public String deleteLocaion(@PathVariable("Id") long Id) 
	{

		if(locationRepo.findById(Id).isEmpty()) //check whether the location exists already
		{
			return "Cannot delete :( since Location ID is not found";
		}

		this.locationRepo.deleteById(Id);
		return "Successfully deleted";
	}




}
