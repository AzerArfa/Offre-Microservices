package com.superadmin.services.superadmin;


import com.superadmin.dto.UserDto;

public interface SuperAdminService {

    UserDto makeAdmin(Long id);
}
