package com.telecom.telecom.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
@Embeddable
public class SubscriptionId implements java.io.Serializable {
    private static final long serialVersionUID = 1L;
    @Column(name = "mobileNo", nullable = false, length = 11)
    private String mobileNo;

    @Column(name = "planID", nullable = false)
    private Integer planID;

}