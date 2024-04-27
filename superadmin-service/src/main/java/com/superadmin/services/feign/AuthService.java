package com.superadmin.services.feign;

import com.superadmin.dto.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(value = "auth-service", url = "http://localhost:8081")
public interface AuthService {

	@PostMapping("/auth/make-admin/{id}")
    ResponseEntity<UserDto> makeAdmin(@PathVariable("id") Long id);

}
