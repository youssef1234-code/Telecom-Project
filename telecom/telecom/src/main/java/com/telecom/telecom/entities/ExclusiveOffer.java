package com.telecom.telecom.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "Exclusive_Offer")
public class ExclusiveOffer {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "benefitID")
    private com.telecom.telecom.entities.Benefit benefitID;

    @Column(name = "internet_offered")
    private Integer internetOffered;

    @Column(name = "SMS_offered")
    private Integer smsOffered;

    @Column(name = "minutes_offered")
    private Integer minutesOffered;

}