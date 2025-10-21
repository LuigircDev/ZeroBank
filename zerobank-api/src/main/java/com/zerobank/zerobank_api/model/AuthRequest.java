package com.zerobank.zerobank_api.model;

import lombok.Data;

@Data
public class AuthRequest {
    private String email;
    private String password;
}
