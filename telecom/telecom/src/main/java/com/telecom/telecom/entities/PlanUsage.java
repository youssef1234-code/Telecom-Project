package com.telecom.telecom.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "Plan_Usage")
public class PlanUsage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "usageID", nullable = false)
    private Integer id;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(name = "data_consumption")
    private Integer dataConsumption;

    @Column(name = "minutes_used")
    private Integer minutesUsed;

    @Column(name = "SMS_sent")
    private Integer smsSent;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mobileNo")
    private com.telecom.telecom.entities.CustomerAccount mobileNo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "planID")
    private com.telecom.telecom.entities.ServicePlan planID;

}