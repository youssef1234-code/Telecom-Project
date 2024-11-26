package com.telecom.telecom.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "Points_Group")
public class PointsGroup {
    @EmbeddedId
    private PointsGroupId id;

    @MapsId("benefitID")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "benefitID", nullable = false)
    private com.telecom.telecom.entities.Benefit benefitID;

    @Column(name = "pointsAmount")
    private Integer pointsAmount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PaymentID")
    private com.telecom.telecom.entities.Payment paymentID;

}