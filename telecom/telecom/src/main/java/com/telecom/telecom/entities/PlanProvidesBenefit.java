package com.telecom.telecom.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "Plan_Provides_Benefits", schema = "dbo")
public class PlanProvidesBenefit {
    @EmbeddedId
    private PlanProvidesBenefitId id;

    @MapsId("benefitID")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "benefitID", nullable = false)
    private Benefit benefitID;

    @MapsId("planID")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "planID", nullable = false)
    private com.telecom.telecom.entities.ServicePlan planID;

}