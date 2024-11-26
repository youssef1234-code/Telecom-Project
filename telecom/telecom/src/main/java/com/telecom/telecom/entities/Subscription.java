package com.telecom.telecom.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "Subscription", schema = "dbo")
public class Subscription {
    @EmbeddedId
    private SubscriptionId id;

    @MapsId("mobileNo")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "mobileNo", nullable = false)
    private com.telecom.telecom.entities.CustomerAccount mobileNo;

    @MapsId("planID")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "planID", nullable = false)
    private ServicePlan planID;

    @Column(name = "subscription_date")
    private LocalDate subscriptionDate;

    @Column(name = "status", length = 50)
    private String status;

}