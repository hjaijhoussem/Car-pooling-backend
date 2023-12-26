package com.horizon.carpooling.entities;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NotBlank()
    private float Stars;

    @Column(nullable = false)
    @NotBlank()
    private String comment;


    @ManyToOne()
    @JoinColumn(nullable = false)
    private User reviewer;

    @ManyToOne()
    @JoinColumn(nullable = false)
    private Ride ride;


}
