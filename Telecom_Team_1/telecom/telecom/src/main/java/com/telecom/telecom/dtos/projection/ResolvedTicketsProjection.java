package com.telecom.telecom.dtos.projection;

public interface ResolvedTicketsProjection {
    Integer getTicketId();
    String getMobileNo();
    String getIssueDescription();
    Integer getPriorityLevel();
    String getStatus();

}
