package com.spaceship.demo.model;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Spaceship")
public class SpaceShip
{
	

	//all the instance variables
	@Id
	private long id;
	private long locationId =1000;
	private String name;
	private String model;
	private String location;
	private spaceshipStatus status;

	public long getLocationId() {
		return locationId;
	}

	public void setLocationId(long locationId) {
		this.locationId = locationId;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public spaceshipStatus getStatus() {
		return status;
	}

	public void setStatus(spaceshipStatus status) {
		this.status = status;
	}	

}



