package com.telecom.telecom.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "Exclusive_offer", schema = "dbo")
public class ExclusiveOffer {
    @EmbeddedId
    private ExclusiveOfferId id;

    @MapsId("benefitID")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "benefitID", nullable = false)
    private Benefit benefitID;

    @Column(name = "internet_offered")
    private Integer internetOffered;

    @Column(name = "SMS_offered")
    private Integer smsOffered;

    @Column(name = "minutes_offered")
    private Integer minutesOffered;

}