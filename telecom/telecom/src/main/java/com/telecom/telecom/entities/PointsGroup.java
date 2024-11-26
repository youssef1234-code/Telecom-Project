package com.telecom.telecom.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "Points_Group", schema = "dbo")
public class PointsGroup {
    @EmbeddedId
    private PointsGroupId id;

    @MapsId("benefitID")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "benefitID", nullable = false)
    private Benefit benefitID;

    @Column(name = "pointsAmount")
    private Integer pointsAmount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PaymentID")  // Ensure column name matches field name
    private Payment paymentID;  // Ensure "paymentID" is consistent with the Payment entity
}