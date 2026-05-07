package com.FYP.Fleet.Repository;

import com.FYP.Fleet.Models.Expense;
import com.FYP.Fleet.Models.Trip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TripRepository extends JpaRepository<Trip, Long> {

    @Query("""
        SELECT t FROM Trip t\s
        WHERE t.vehicle.id IN :vehicleIds\s
        AND t.status = com.FYP.Fleet.Enums.Status.COMPLETED
   \s""")
    List<Trip> findCompletedTripsByVehicleIds(@Param("vehicleIds") List<Long> vehicleIds);

    List<Expense> findByIdIn(List<Long> tripIds);

    @Query("""
        SELECT t FROM Trip t\s
        WHERE t.user.id = :userId\s
        AND t.status = com.FYP.Fleet.Enums.Status.COMPLETED
   \s""")
    List<Trip> findCompletedTripsByUserId(@Param("userId")Long userId);

    @Query("""
        SELECT t FROM Trip t\s
        JOIN Vehicle v\s
        ON v.id = t.vehicle.id\s
        WHERE t.user.id = :userId\s
        AND v.owner.id = :ownerId\s
        AND t.status = com.FYP.Fleet.Enums.Status.COMPLETED
   \s""")
    List<Trip> getCompletedTripsByOwnerIdAndUserId(@Param("ownerId") Long ownerId, @Param("userId") Long userId);
}
