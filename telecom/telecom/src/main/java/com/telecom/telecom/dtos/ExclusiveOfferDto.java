package com.telecom.telecom.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ExclusiveOfferDto implements Serializable {
    private static final long serialVersionUID = 1L;

    private int offerID;
    private int benefitID;
    private int internet_offered;
    private int SMS_offered;
    private int minutes_offered;

}
