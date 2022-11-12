package com.newsApp_be.entity;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public enum ERole {
    ROLE_USER("user"),
    ROLE_ADMIN("admin");
    private String shortName;
    private static Map<String, ERole> ROLE_MAP;

    static {
        ROLE_MAP = Arrays.stream(ERole.values()).collect(Collectors.toMap(ERole::getShortName, r -> r, (k1,k2) -> k1, HashMap::new));
    }

    private String getShortName() {
        return this.shortName;
    }

    ERole(String shortName) {
        this.shortName = shortName;
    }

    public static ERole getRoleFromShortName(String shortName) {
        return ROLE_MAP.get(shortName);
    }
}
