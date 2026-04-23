package com.FYP.Fleet.Models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;
import java.util.ArrayList;

@Entity(name = "Vehicle")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "vehicle_seq_gen")
    @SequenceGenerator(
            name = "vehicle_seq_gen",
            sequenceName = "vehicle_sequence",
            initialValue = 1,
            allocationSize = 50
    )
    private long id;

    @Column(name = "number")
    @NotNull
    private String number;

    @ManyToOne()
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "vehicle")
    private List<Trip> tripList = new ArrayList<>();


}
