package com.authon.saml;

public class SamlUserInfo {
    private String email;
    private String name;

    public SamlUserInfo(String email, String name) {
        this.email = email;
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "SamlUserInfo{" +
                "email='" + email + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
