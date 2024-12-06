package com.telecom.telecom.dtos.requests;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AdminLoginRequest implements Serializable {
    private static final long serialVersionUID = 1L;
    private String adminId;
    private String password;
}
