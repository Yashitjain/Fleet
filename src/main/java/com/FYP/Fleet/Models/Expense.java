package com.FYP.Fleet.Models;

import com.FYP.Fleet.Enums.ExpenseType;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity(name = "Expense")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Expense {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "expense_seq_gen")
    @SequenceGenerator(
            name = "expense_seq_gen",
            sequenceName = "expense_sequence",
            initialValue = 1,
            allocationSize = 50
    )
    private long id;

    @ManyToOne
    @JoinColumn(name = "trip_id")
    @JsonIgnoreProperties("expenseList")
    private Trip trip;

    @NotNull
    private ExpenseType expenseType;

    private String note;

    @NotNull
    private LocalDate date;
}
