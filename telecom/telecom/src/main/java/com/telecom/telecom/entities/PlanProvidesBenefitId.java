package com.telecom.telecom.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;

import java.util.Objects;

@Getter
@Setter
@Embeddable
public class PlanProvidesBenefitId implements java.io.Serializable {
    private static final long serialVersionUID = 67175802630019585L;
    @Column(name = "benefitID", nullable = false)
    private Integer benefitID;

    @Column(name = "planID", nullable = false)
    private Integer planID;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        PlanProvidesBenefitId entity = (PlanProvidesBenefitId) o;
        return Objects.equals(this.planID, entity.planID) &&
                Objects.equals(this.benefitID, entity.benefitID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(planID, benefitID);
    }

}