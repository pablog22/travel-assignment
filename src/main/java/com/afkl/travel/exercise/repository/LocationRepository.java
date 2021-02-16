package com.afkl.travel.exercise.repository;

import com.afkl.travel.exercise.model.Location;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LocationRepository extends JpaRepository<Location, Integer> {

    Optional<Location> findByTypeAndCode(String type, String code);

}
