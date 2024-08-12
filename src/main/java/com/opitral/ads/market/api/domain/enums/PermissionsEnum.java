package com.opitral.ads.market.api.domain.enums;

import org.springframework.security.core.GrantedAuthority;

public enum PermissionsEnum implements GrantedAuthority {

    MANAGE_SUBJECTS, DELETE_SUBJECTS,
    MANAGE_CITIES, DELETE_CITIES;

    @Override
    public String getAuthority() {
        return name();
    }
}
