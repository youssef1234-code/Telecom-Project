package com.telecom.telecom.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "plan_provides_benefits", schema = "dbo")
public class PlanProvidesBenefit {
    @EmbeddedId
    private PlanProvidesBenefitId id;

    @MapsId("planID")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "planID", nullable = false)
    private ServicePlan planID;

    @MapsId("benefitid")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "benefitid", nullable = false)
    private Benefit benefitid;

}