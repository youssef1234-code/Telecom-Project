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
    private static final long serialVersionUID = -118431785912561746L;
    @Column(name = "pointID", nullable = false)
    private Integer pointID;

    @Column(name = "benefitID", nullable = false)
    private Integer benefitID;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        PointsGroupId entity = (PointsGroupId) o;
        return Objects.equals(this.pointID, entity.pointID) &&
                Objects.equals(this.benefitID, entity.benefitID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pointID, benefitID);
    }

}