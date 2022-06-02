package com.erisboxx.socialspace.payload;

import lombok.Data;

@Data
public class JwtAuthResponse {
    private String accessToken;
    private String tokenType = "Bearer";
    private String username;

    public JwtAuthResponse(String accessToken) {
        this.accessToken = accessToken;
    }


}
