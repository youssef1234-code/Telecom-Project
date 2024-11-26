package com.telecom.telecom.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "Benefits", schema = "dbo")
public class Benefit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @OneToMany(mappedBy = "benefitID")
    private Set<com.telecom.telecom.entities.Cashback> cashbacks = new LinkedHashSet<>();

    @OneToMany(mappedBy = "benefitID")
    private Set<com.telecom.telecom.entities.ExclusiveOffer> exclusiveOffers = new LinkedHashSet<>();

    @OneToMany(mappedBy = "benefitID")
    private Set<com.telecom.telecom.entities.PointsGroup> pointsGroups = new LinkedHashSet<>();

    @ManyToMany
    @JoinTable(name = "plan_provides_benefits",
            joinColumns = @JoinColumn(name = "benefitid"),
            inverseJoinColumns = @JoinColumn(name = "planID"))
    private Set<com.telecom.telecom.entities.ServicePlan> servicePlans = new LinkedHashSet<>();

}