package com.codagis.agischool.service;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.io.Encoders;

public class KeyGenerator {
    public static void main(String[] args) {
        String key = Encoders.BASE64.encode(
            Keys.secretKeyFor(SignatureAlgorithm.HS512).getEncoded()
        );
        System.out.println("SUA_CHAVE_SEGURA: " + key);
    }
}