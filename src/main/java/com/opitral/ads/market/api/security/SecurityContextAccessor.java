package com.opitral.ads.market.api.security;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.opitral.ads.market.api.domain.enums.PermissionsEnum;
import com.opitral.ads.market.api.exception.ForbiddenException;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SecurityContextAccessor {

    public static AuthenticatedUser getAuthenticateUser() {
        return (AuthenticatedUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    public static String getTelegramID() {
        return getAuthenticateUser().getTelegramID();
    }

    public static boolean isAuthenticated() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication != null && authentication.isAuthenticated() && authentication.getPrincipal() instanceof AuthenticatedUser;
    }

    public static boolean isNotAuthenticated() {
        return !isAuthenticated();
    }

    public static boolean hasPermission(PermissionsEnum permission) {
        return getAuthenticateUser().hasPermission(permission);
    }

    public static void requirePermission(PermissionsEnum permissionsEnum) {
        if (!hasPermission(permissionsEnum)) {
            throw new ForbiddenException();
        }
    }
}