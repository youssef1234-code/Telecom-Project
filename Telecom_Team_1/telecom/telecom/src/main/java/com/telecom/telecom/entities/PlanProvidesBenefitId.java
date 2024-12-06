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
    private static final long serialVersionUID = -3657535747200595972L;
    @Column(name = "planID", nullable = false)
    private Integer planID;

    @Column(name = "benefitid", nullable = false)
    private Integer benefitid;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        PlanProvidesBenefitId entity = (PlanProvidesBenefitId) o;
        return Objects.equals(this.planID, entity.planID) &&
                Objects.equals(this.benefitid, entity.benefitid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(planID, benefitid);
    }

}