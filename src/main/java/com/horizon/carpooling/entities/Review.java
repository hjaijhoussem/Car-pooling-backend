package com.horizon.carpooling.entities;


import jakarta.persistence.*;

@Entity
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private float Stars;

    @Column(nullable = false)
    private String comment;


    @ManyToOne()
    @JoinColumn(nullable = false)
    private User reviewer;

    @ManyToOne()
    @JoinColumn(nullable = false)
    private Ride ride;


}
