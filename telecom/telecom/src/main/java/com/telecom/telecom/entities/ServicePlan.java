package com.telecom.telecom.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "Service_plan", schema = "dbo")
public class ServicePlan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "planID", nullable = false)
    private Integer id;

    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @Column(name = "price", nullable = false)
    private Integer price;

    @Column(name = "SMS_offered", nullable = false)
    private Integer smsOffered;

    @Column(name = "minutes_offered", nullable = false)
    private Integer minutesOffered;

    @Column(name = "data_offered", nullable = false)
    private Integer dataOffered;

    @Column(name = "description", length = 50)
    private String description;

    @OneToMany(mappedBy = "planID")
    private Set<PlanUsage> planUsages = new LinkedHashSet<>();

    @OneToMany(mappedBy = "planID")
    private Set<com.telecom.telecom.entities.Subscription> subscriptions = new LinkedHashSet<>();

    @ManyToMany(mappedBy = "servicePlans")
    private Set<Benefit> benefits = new LinkedHashSet<>();

    @OneToMany(mappedBy = "planID")
    private Set<com.telecom.telecom.entities.ProcessPayment> processPayments = new LinkedHashSet<>();

}