package com.seng696.Seng696.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Component
@Data
public class ApplicationProperties {

    @Value("${jwt.token.expiration.in.mills}")
    private Integer jwtExpireTime;

    @Value("${jwt.token.secret}")
    private String jwtTokenSecret;


}
