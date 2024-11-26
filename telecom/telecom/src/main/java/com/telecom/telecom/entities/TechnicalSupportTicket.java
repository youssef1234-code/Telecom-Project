package com.telecom.telecom.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "Technical_Support_Ticket", schema = "dbo")
public class TechnicalSupportTicket {
    @EmbeddedId
    private TechnicalSupportTicketId id;

    @MapsId
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "mobileNo", nullable = false)
    private CustomerAccount mobileNo;

    @Column(name = "Issue_description", length = 50)
    private String issueDescription;

    @Column(name = "priority_level")
    private Integer priorityLevel;

    @Column(name = "status", length = 50)
    private String status;

}