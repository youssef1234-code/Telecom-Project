package com.telecom.telecom.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "Service_Plan", schema = "dbo")
public class ServicePlan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "planID", nullable = false)
    private Integer id;

    @Column(name = "SMS_offered")
    private Integer smsOffered;

    @Column(name = "minutes_offered")
    private Integer minutesOffered;

    @Column(name = "data_offered")
    private Integer dataOffered;

    @Column(name = "name", length = 50)
    private String name;

    @Column(name = "price")
    private Integer price;

    @Column(name = "description", length = 50)
    private String description;

    @ManyToMany(mappedBy = "benefitID")
    private Set<Benefit> benefits = new LinkedHashSet<>();

    @OneToMany(mappedBy = "planID")
    private Set<PlanUsage> planUsages = new LinkedHashSet<>();

    @OneToMany(mappedBy = "planID")
    private Set<ProcessPayment> processPayments = new LinkedHashSet<>();

    @OneToMany(mappedBy = "planID")
    private Set<com.telecom.telecom.entities.Subscription> subscriptions = new LinkedHashSet<>();

}