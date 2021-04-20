package com.spaceship.demo.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.spaceship.demo.model.Location;


@Repository
public interface LocationRepository extends MongoRepository<Location,Long>{

}


