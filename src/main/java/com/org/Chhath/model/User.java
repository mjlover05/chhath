package com.org.Chhath.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;


@Entity
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    @Column(unique = true)
    private String email;
    private String phone;
    private LocalDate dateOfBirth;
    private String roomNumber;
    private String colonyName;
    private String password;
    private String role = "ROLE_USER";
    @OneToOne (mappedBy = "bookedBy")
    private Seat seat;



}
