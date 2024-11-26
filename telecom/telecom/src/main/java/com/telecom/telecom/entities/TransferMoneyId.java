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
public class TransferMoneyId implements java.io.Serializable {
    private static final long serialVersionUID = 4249385908787386917L;
    @Column(name = "walletID1", nullable = false)
    private Integer walletID1;

    @Column(name = "walletID2", nullable = false)
    private Integer walletID2;

    @Column(name = "transfer_id", nullable = false)
    private Integer transferId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        TransferMoneyId entity = (TransferMoneyId) o;
        return Objects.equals(this.transferId, entity.transferId) &&
                Objects.equals(this.walletID2, entity.walletID2) &&
                Objects.equals(this.walletID1, entity.walletID1);
    }

    @Override
    public int hashCode() {
        return Objects.hash(transferId, walletID2, walletID1);
    }

}