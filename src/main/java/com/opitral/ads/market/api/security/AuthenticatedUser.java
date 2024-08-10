package com.opitral.ads.market.api.security;

import java.util.Set;

import lombok.Builder;
import lombok.Data;

import com.opitral.ads.market.api.domain.enums.PermissionsEnum;
import com.opitral.ads.market.api.domain.enums.RolesEnum;


@Builder
@Data
public class AuthenticatedUser {

    private final String token;
    private final String telegramID;
    private final Set<PermissionsEnum> permissions;
    private final RolesEnum role;

    public boolean hasPermission(PermissionsEnum permission) {
        return permissions.contains(permission);
    }

}
