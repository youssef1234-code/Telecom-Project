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
public class ExclusiveOfferId implements java.io.Serializable {
    private static final long serialVersionUID = -253829049826915032L;
    @Column(name = "offerID", nullable = false)
    private Integer offerID;

    @Column(name = "benefitID", nullable = false)
    private Integer benefitID;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        ExclusiveOfferId entity = (ExclusiveOfferId) o;
        return Objects.equals(this.offerID, entity.offerID) &&
                Objects.equals(this.benefitID, entity.benefitID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(offerID, benefitID);
    }

}