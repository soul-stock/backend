package com.soulstock.backend.security.jwt;

public enum JwtTokenStatus {
    VALID,
    EXPIRED,
    INVALID_SIGNATURE,
    MALFORMED,
    UNSUPPORTED,
    EMPTY,
    UNKNOWN_ERROR
}