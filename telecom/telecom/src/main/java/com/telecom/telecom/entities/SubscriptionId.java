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
public class SubscriptionId implements java.io.Serializable {
    private static final long serialVersionUID = -9031446101738794742L;
    @Column(name = "mobileNo", nullable = false, length = 11)
    private String mobileNo;

    @Column(name = "planID", nullable = false)
    private Integer planID;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        SubscriptionId entity = (SubscriptionId) o;
        return Objects.equals(this.planID, entity.planID) &&
                Objects.equals(this.mobileNo, entity.mobileNo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(planID, mobileNo);
    }

}