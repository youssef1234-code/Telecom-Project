package com.telecom.telecom.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

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
}