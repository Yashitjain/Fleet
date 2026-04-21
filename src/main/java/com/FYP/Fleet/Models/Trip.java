package com.FYP.Fleet.Models;

import com.FYP.Fleet.Enums.Status;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "Trip")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Trip {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "trip_seq_gen")
    @SequenceGenerator(
            name = "trip_seq_gen",
            sequenceName = "trip_sequence",
            initialValue = 1,
            allocationSize = 50
    )
    private long id;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    @JsonManagedReference
    private User owner;

    @ManyToOne
    @JoinColumn(name = "driver_id")
    @JsonIgnoreProperties("user")
    private Driver driver;

    @ManyToOne
    @JoinColumn(name = "vehicle_id")
    @JsonIgnoreProperties("user")
    private Vehicle vehicle;

    @NotNull
    private String source;

    @NotNull
    private String destination;

    @NotNull
    private Integer freightPrice;

    @NotNull
    private LocalDate startDate;

    private LocalDate endDate;

    @NotNull
    private Status status;

    @OneToMany(mappedBy = "trip")
    @JsonIgnoreProperties("trip")
    @Builder.Default
    private List<Expense> expenseList = new ArrayList<>();


}
