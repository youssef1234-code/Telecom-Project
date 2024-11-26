package com.telecom.telecom.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
@Embeddable
public class TechnicalSupportTicketId implements java.io.Serializable {
    private static final long serialVersionUID = -5227920416256002614L;
    @Column(name = "ticketID", nullable = false)
    private Integer ticketID;

    @Column(name = "mobileNo", nullable = false, length = 11)
    private String mobileNo;

}