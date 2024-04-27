package com.offer.entity;


import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Offer {

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

}
