package com.FYP.Fleet.Models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity(name = "User")
@NoArgsConstructor
@Getter
@Setter
@Builder
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq_gen")
    @SequenceGenerator(
            name = "user_seq_gen",
            sequenceName = "user_sequence",
            initialValue = 1,
            allocationSize = 50
    )

    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    @NotNull
    private String name;

    @Column(name = "phone")
    @NotNull
    private String phone;

    @Column(name = "password")
    @NotNull
    private String password;

    @OneToMany( mappedBy = "user", cascade = CascadeType.ALL)
    @JsonIgnoreProperties("user")
    @Builder.Default
    private List<Vehicle> vehicleList = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonIgnoreProperties("user")
    @Builder.Default
    private List<Driver> driverList = new ArrayList<>();

    @OneToMany(mappedBy = "owner")
    @Builder.Default
    private List<Trip> tripList = new ArrayList<>();


}
