package com.telecom.telecom.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter@Setter
@Table(name = "Customer_profile", schema = "dbo")
public class CustomerProfile {
    @Id
    private int nationalID;

    private String first_name;
    private String last_name;
    private String email;
    private String address;

    @Temporal(TemporalType.DATE)
    private Date date_of_birth;
}
