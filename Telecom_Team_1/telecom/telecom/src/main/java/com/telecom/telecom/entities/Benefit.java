package com.telecom.telecom.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "Benefits", schema = "dbo")
public class Benefit {
    @Id
    @Column(name = "benefitID", nullable = false)
    private Integer id;

    @Column(name = "description", length = 50)
    private String description;

    @Column(name = "validity_date")
    private LocalDate validityDate;

    @Column(name = "status", length = 50)
    private String status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mobileNo")
    private com.telecom.telecom.entities.CustomerAccount mobileNo;

}