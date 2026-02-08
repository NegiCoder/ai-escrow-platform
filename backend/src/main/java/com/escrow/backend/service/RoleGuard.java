package com.escrow.backend.service;

import com.escrow.backend.dto.Role;
import org.springframework.stereotype.Component;

@Component
public class RoleGuard {

    public void require(Role actual, Role... allowed) {
        for (Role role : allowed) {
            if (role == actual) return;
        }
        throw new RuntimeException("Access denied for role: " + actual);
    }
}
