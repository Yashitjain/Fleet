package com.FYP.Fleet.Models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity(name = "Driver")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Driver {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "driver_seq_gen")
    @SequenceGenerator(
            name = "driver_seq_gen",
            sequenceName = "driver_sequence",
            initialValue = 1,
            allocationSize = 50
    )
    private long id;

    @NotNull
    private String name;

    @NotNull
    private String phone;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnoreProperties("driverList")
    private User user;

    @OneToMany(mappedBy = "driver")
    @JsonIgnoreProperties("driver")
    @Builder.Default
    private List<Trip> tripList = new ArrayList<>();
}
