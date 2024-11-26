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
public class CashbackId implements java.io.Serializable {
    private static final long serialVersionUID = 8084334535385749371L;
    @Column(name = "cashbackID", nullable = false)
    private Integer cashbackID;

    @Column(name = "benefitID", nullable = false)
    private Integer benefitID;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        CashbackId entity = (CashbackId) o;
        return Objects.equals(this.cashbackID, entity.cashbackID) &&
                Objects.equals(this.benefitID, entity.benefitID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cashbackID, benefitID);
    }

}