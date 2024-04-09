package com.example.springoauth2clientsession.dto;

import lombok.AllArgsConstructor;

import java.util.Map;

@AllArgsConstructor
public class NaverResponse implements OAuth2Response {


    private final Map<String, Object> attribute;

    @Override
    public String getProvider() {
        return "naver";
    }

    @Override
    public String getProviderID() {
        return attribute.get("id").toString();
    }

    @Override
    public String getEmail() {
        return attribute.get("email").toString();
    }

    @Override
    public String getName() {
        return attribute.get("name").toString();
    }
}
