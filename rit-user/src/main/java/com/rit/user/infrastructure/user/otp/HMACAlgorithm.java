package com.rit.user.infrastructure.user.otp;

enum HMACAlgorithm {

    HMAC_SHA_512("HmacSHA512"),
    HMAC_SHA_256("HmacSHA256"),
    HMAC_SHA_1("HmacSHA1");

    private final String value;

    HMACAlgorithm(String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }
}
