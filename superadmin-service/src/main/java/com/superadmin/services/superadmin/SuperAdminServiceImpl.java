package com.superadmin.services.superadmin;

import com.superadmin.dto.UserDto;
import com.superadmin.services.feign.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SuperAdminServiceImpl implements SuperAdminService {

    private final AuthService authService;

    @Override
    public UserDto makeAdmin(Long id) {
        return authService.makeAdmin(id).getBody();
    }
}
