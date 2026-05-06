package com.FYP.Fleet.Repository;

import com.FYP.Fleet.Models.Trip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TripRepository extends JpaRepository<Trip, Long> {
    List<Trip> findAllByOwnerId(long ownerId);
}
