package com.spaceship.demo.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.spaceship.demo.model.SpaceShip;

@Repository
public interface SpaceShipRepository extends MongoRepository<SpaceShip,Long>
{

	

}



