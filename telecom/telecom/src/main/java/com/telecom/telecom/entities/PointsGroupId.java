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
public class PointsGroupId implements java.io.Serializable {
    private static final long serialVersionUID = -5605334265138247027L;
    @Column(name = "pointId", nullable = false)
    private Integer pointId;

    @Column(name = "benefitID", nullable = false)
    private Integer benefitID;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        PointsGroupId entity = (PointsGroupId) o;
        return Objects.equals(this.pointId, entity.pointId) &&
                Objects.equals(this.benefitID, entity.benefitID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pointId, benefitID);
    }

}